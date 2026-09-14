package com.personalieltscoach.reading

import android.content.Context
import android.net.Uri
import androidx.room.withTransaction
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.repository.CoachRepository
import com.personalieltscoach.domain.service.ReviewScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.security.MessageDigest
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

class ReadingRepository(private val context: Context, private val db: CoachDatabase,
    private val coach: CoachRepository, private val json: Json) {
    private val dao = db.readingDao()
    private val importLock = Mutex()
    val courses = dao.observeCourses()
    val progress = dao.observeProgress()
    val vocabulary = combine(dao.observeVocabulary(), db.wordDao().observeAll()) { collected, words ->
        val indexed = words.associateBy { it.id }
        collected.map { item ->
            indexed[item.linkedWordId]?.let { linked -> item.copy(status = linked.status,
                streak = linked.correctStreak, dueAt = linked.nextReviewAt,
                lastStudiedAt = if (linked.status == "NEW") 0 else linked.updatedAt) } ?: item
        }
    }
    fun outline(entity: CourseEntity): CourseOutline = json.decodeFromString(entity.manifest)

    suspend fun loadCourse(entity: CourseEntity): ReadingCourse = withContext(Dispatchers.IO) {
        val root = File(context.filesDir, "reading_courses").canonicalFile
        val folder = File(root, entity.folder).canonicalFile
        require(folder.parentFile == root && folder.name.startsWith("import-"))
        val file = File(folder, "course.json")
        require(file.length() in 1..8L * 1024 * 1024)
        val manifest = file.readText()
        CoursePackageReader(json).validateJsonDepth(manifest)
        json.decodeFromString<ReadingCourse>(manifest).also {
            require(it.id == entity.id && it.revision == entity.revision)
        }
    }

    suspend fun importCourse(uri: Uri): ReadingCourse = withContext(Dispatchers.IO) { importLock.withLock {
        require(uri.scheme == "content") { "请使用系统文件选择器导入课程" }
        val root = File(context.filesDir, "reading_courses").apply { mkdirs() }
        val stage = File(root, "import-${UUID.randomUUID()}").apply { mkdirs() }
        var committed = false
        try {
            val input = context.contentResolver.openInputStream(uri) ?: error("无法打开所选文件")
            val course = input.use { CoursePackageReader(json).extract(it, stage) }
            val previous = dao.course(course.id)
            require(previous == null || course.revision >= previous.revision) { "不能用旧课程覆盖新内容" }
            db.withTransaction {
                dao.putCourse(CourseEntity(course.id, course.title, course.revision, stage.name,
                    // Large annotated courses exceed Android's CursorWindow row limit.
                    // Keep only a tiny outline in Room; the validated private JSON stays on disk.
                    json.encodeToString(CourseOutline(course.lessons.size, course.lessons.count { it.ready })), System.currentTimeMillis()))
                // Content replacement deliberately does not replace learning progress.
            }
            committed = true
            previous?.folder?.let { folder ->
                val old = File(root, folder).canonicalFile
                if (old.parentFile == root.canonicalFile && old != stage && old.name.startsWith("import-")) old.deleteRecursively()
            }
            course
        } finally {
            if (!committed) stage.deleteRecursively()
        }
    } }

    suspend fun savePosition(course: String, lesson: String, sentence: Int? = null, audio: Int? = null) = db.withTransaction {
        val old = dao.progress(course, lesson) ?: ReadingProgressEntity(course, lesson)
        dao.putProgress(old.copy(sentenceIndex = sentence?.coerceAtLeast(0) ?: old.sentenceIndex,
            audioPosition = audio?.coerceAtLeast(0) ?: old.audioPosition, updatedAt = System.currentTimeMillis()))
    }

    suspend fun complete(courseId: String, lesson: ReadingLesson) = db.withTransaction {
        require(lesson.ready && lesson.sentences.isNotEmpty())
        val old = dao.progress(courseId, lesson.id) ?: ReadingProgressEntity(courseId, lesson.id)
        if (!old.completed) {
            coach.recordReading(lesson.sentences.joinToString(" ") { it.text })
            dao.putProgress(old.copy(completed = true, sentenceIndex = lesson.sentences.lastIndex, updatedAt = System.currentTimeMillis()))
        }
    }

    suspend fun collect(course: String, lesson: String, word: String, meaning: String, sentence: String) = db.withTransaction {
        require(word.length in 1..100 && meaning.isNotBlank() && meaning.length <= 1000)
        val term = word.lowercase().trim()
        val normalizedMeaning = meaning.replace(Regex("\\s+"), "")
        val id = MessageDigest.getInstance("SHA-256").digest("$term|$normalizedMeaning".toByteArray()).joinToString("") { "%02x".format(it) }
        val existing = db.wordDao().find(term)
        // Matching only a lemma would merge unrelated senses of 'light', 'right', etc.
        val linked = existing?.takeIf { it.meaning.replace(Regex("\\s+"), "") == normalizedMeaning }
        dao.addVocabulary(ReadingVocabularyEntity(id, term, meaning, sentence, course, lesson,
            linkedWordId = linked?.id, status = linked?.status ?: "NEW", streak = linked?.correctStreak ?: 0,
            dueAt = linked?.nextReviewAt ?: 0, lastStudiedAt = linked?.takeIf { it.status != "NEW" }?.updatedAt ?: 0))
    }

    suspend fun rate(id: String, correct: Boolean) = db.withTransaction {
        val word = dao.vocabulary(id) ?: return@withTransaction
        val now = System.currentTimeMillis()
        val dayStart = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        if (word.lastStudiedAt >= dayStart) return@withTransaction
        val existing = word.linkedWordId?.let { db.wordDao().getById(it) }
        if (existing != null && existing.status != "NEW" && existing.updatedAt >= dayStart) {
            dao.updateVocabulary(word.copy(status = existing.status, streak = existing.correctStreak,
                dueAt = existing.nextReviewAt, lastStudiedAt = existing.updatedAt))
            return@withTransaction
        }
        val result = ReviewScheduler.next(correct, existing?.correctStreak ?: word.streak, existing?.wrongCount ?: 0, now)
        existing?.let { db.wordDao().upsert(it.copy(status = result.status, correctStreak = result.correctStreak,
            wrongCount = result.wrongCount, nextReviewAt = result.nextReviewAt, updatedAt = now,
            lastWrongAt = if (correct) it.lastWrongAt else now)) }
        dao.updateVocabulary(word.copy(status = result.status, streak = result.correctStreak, dueAt = result.nextReviewAt, lastStudiedAt = now))
    }

    fun audioFile(course: CourseEntity, lesson: ReadingLesson): File? {
        val relative = lesson.audio ?: return null
        if (!Regex("audio/[a-z0-9_-]{1,64}\\.mp3").matches(relative)) return null
        val root = File(context.filesDir, "reading_courses").canonicalFile
        val folder = File(root, course.folder).canonicalFile
        if (folder.parentFile != root) return null
        return File(folder, relative).takeIf { it.isFile }
    }
}
