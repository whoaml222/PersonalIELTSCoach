package com.personalieltscoach.reading

import android.content.Context
import android.net.Uri
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.data.local.database.CoachDatabase
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.repository.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ReadingRepositoryTest {
    private lateinit var context: Context
    private lateinit var db: CoachDatabase
    private lateinit var repository: ReadingRepository
    private val lesson = ReadingLesson("d001", "Test lesson", 1,
        listOf(ReadingSentence("s001", "The knife is blunt."), ReadingSentence("s002", "Let's get another one.")))
    @Before fun setup() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, CoachDatabase::class.java).allowMainThreadQueries().build()
        val coach = CoachRepository(db, object : SettingsProvider {
            override suspend fun current() = CoachSettings()
        }, Json)
        repository = ReadingRepository(context, db, coach, Json)
    }
    @After fun close() { db.close() }
    private fun register(course: ReadingCourse): Uri {
        val bytes = ByteArrayOutputStream()
        ZipOutputStream(bytes).use { zip ->
            zip.putNextEntry(ZipEntry("course.json"))
            zip.write(Json.encodeToString(course).toByteArray()); zip.closeEntry()
        }
        val uri = Uri.parse("content://test/course")
        shadowOf(context.contentResolver).registerInputStream(uri, bytes.toByteArray().inputStream())
        return uri
    }
    @Test fun reimportPreservesPositionsAndRejectsOlderRevision() = runTest {
        val course = ReadingCourse(id = "test", title = "Test", revision = 2, lessons = listOf(lesson))
        repository.importCourse(register(course))
        repository.savePosition("test", "d001", sentence = 1)
        repository.savePosition("test", "d001", audio = 12345)
        repository.importCourse(register(course.copy(title = "Revised title")))
        val saved = requireNotNull(db.readingDao().progress("test", "d001"))
        assertEquals(1, saved.sentenceIndex); assertEquals(12345, saved.audioPosition)
        assertFalse(saved.completed)
        try { repository.importCourse(register(course.copy(revision = 1))); fail("Downgrade accepted") }
        catch (_: IllegalArgumentException) { }
        assertEquals("Revised title", db.readingDao().course("test")?.title)
        assertEquals(saved, db.readingDao().progress("test", "d001"))
    }
    @Test fun collectingDoesNotLearnAndRepeatedRatingDoesNotInflateStreak() = runTest {
        repository.collect("test", "d001", "blunt", "钝的", "The knife is blunt.")
        repository.collect("test", "d001", "blunt", "钝的", "Another context.")
        val items = repository.vocabulary.first()
        assertEquals(1, items.size); assertEquals("NEW", items.single().status)
        repository.rate(items.single().id, true)
        val once = repository.vocabulary.first().single()
        repository.rate(items.single().id, true)
        assertEquals(once, repository.vocabulary.first().single())
        assertEquals(1, once.streak)
    }
    @Test fun splitSentenceReimportMapsStableIdsAndKeepsAudioCompletionAndVocabulary() = runTest {
        val course = ReadingCourse(id = "split-test", title = "Test", lessons = listOf(lesson, lesson.copy(id = "d002", order = 2)))
        repository.importCourse(register(course))
        repository.savePosition(course.id, "d001", sentence = 1, audio = 12345)
        repository.complete(course.id, course.lessons[1])
        repository.collect(course.id, "d001", "blunt", "钝的", lesson.sentences[0].text)
        val vocabulary = repository.vocabulary.first()
        val oldProgress = requireNotNull(db.readingDao().progress(course.id, "d001"))
        val updated = course.copy(revision = 2, lessons = course.lessons.map {
            it.copy(sentences = listOf(it.sentences[0], ReadingSentence("s001-part2", "It won't cut bread.", "它切不动面包。"), it.sentences[1]))
        })
        repository.importCourse(register(updated))
        assertEquals(oldProgress.copy(sentenceIndex = 2), db.readingDao().progress(course.id, "d001"))
        assertEquals(2, db.readingDao().progress(course.id, "d002")?.sentenceIndex)
        assertTrue(requireNotNull(db.readingDao().progress(course.id, "d002")).completed)
        assertEquals(vocabulary, repository.vocabulary.first())
    }
    @Test fun reimportRecoversMissingOldManifestWithoutLosingProgress() = runTest {
        val course = ReadingCourse(id = "recovery", title = "Test", lessons = listOf(lesson))
        repository.importCourse(register(course))
        repository.savePosition(course.id, lesson.id, sentence = 1, audio = 1000)
        val old = requireNotNull(db.readingDao().course(course.id))
        val manifest = java.io.File(context.filesDir, "reading_courses/${old.folder}/course.json")
        assertTrue(manifest.delete())
        repository.importCourse(register(course.copy(revision = 2)))
        assertEquals(1, db.readingDao().progress(course.id, lesson.id)?.sentenceIndex)
        assertEquals(1000, db.readingDao().progress(course.id, lesson.id)?.audioPosition)
        assertEquals(2, repository.loadCourse(requireNotNull(db.readingDao().course(course.id))).revision)
    }
    @Test fun sameSenseTracksOriginalProgressButDifferentSensesStaySeparate() = runTest {
        val id = db.wordDao().insert(WordItemEntity(word = "light", meaning = "轻的", phonetic = "/laɪt/",
            example = "This bag is light.", exampleTranslation = "这个包很轻。", level = "A1", createdAt = 1, updatedAt = 1))
        repository.collect("test", "d001", "light", "轻的", "This bag is light.")
        repository.collect("test", "d002", "light", "光线", "Let some light in.")
        val existing = requireNotNull(db.wordDao().getById(id))
        db.wordDao().upsert(existing.copy(status = "MASTERED", correctStreak = 4, nextReviewAt = 12345, updatedAt = 9000))
        val items = repository.vocabulary.first()
        assertEquals(2, items.size)
        assertEquals("MASTERED", items.single { it.meaning == "轻的" }.status)
        assertEquals("NEW", items.single { it.meaning == "光线" }.status)
    }
    @Test fun completingTwicePreservesOneCompletionAndPosition() = runTest {
        repository.savePosition("test", "d001", audio = 2345)
        repository.complete("test", lesson)
        val once = db.readingDao().progress("test", "d001")
        repository.complete("test", lesson)
        assertEquals(once, db.readingDao().progress("test", "d001"))
        assertTrue(once!!.completed); assertEquals(1, once.sentenceIndex); assertEquals(2345, once.audioPosition)
    }
    @Test fun largeCourseStaysOutOfDatabaseRowsAndLoadsFromPrivateFiles() = runTest {
        val content = ReadingCourse(id = "large", title = "Large course", lessons = (1..100).map { index ->
            ReadingLesson("d$index", "Lesson $index", index, sentences = (1..30).map { number ->
                ReadingSentence("s$number", "The knife is blunt.", "中文译文".repeat(200))
            })
        })
        assertTrue(Json.encodeToString(content).toByteArray().size > 2 * 1024 * 1024)
        repository.importCourse(register(content))
        val saved = requireNotNull(db.readingDao().course("large"))
        assertTrue(saved.manifest.length < 100)
        assertEquals(100, repository.outline(saved).lessonCount)
        assertEquals(content, repository.loadCourse(saved))
        try { repository.loadCourse(saved.copy(folder = "../outside")); fail("Unsafe folder accepted") }
        catch (_: IllegalArgumentException) { }
    }
}
