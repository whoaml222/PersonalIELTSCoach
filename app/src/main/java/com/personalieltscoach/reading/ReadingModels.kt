package com.personalieltscoach.reading

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable data class ReadingCourse(val schemaVersion: Int = 1, val id: String,
    val title: String, val revision: Int = 1, val description: String = "", val lessons: List<ReadingLesson>, val attribution: String = "")
@Serializable data class ReadingLesson(val id: String, val title: String, val order: Int,
    val sentences: List<ReadingSentence> = emptyList(), val translation: String = "",
    val vocabulary: List<ReadingGloss> = emptyList(), val audio: String? = null,
    val note: String = "", val ready: Boolean = true)
@Serializable data class ReadingSentence(val id: String, val text: String, val translation: String = "",
    val grammar: List<com.personalieltscoach.domain.service.GrammarToken> = emptyList())
@Serializable data class ReadingGloss(val word: String, val meaning: String, val phonetic: String = "")
@Serializable data class CourseOutline(val lessonCount: Int, val readyCount: Int)
data class ReadingBook(val entity: CourseEntity, val outline: CourseOutline)
data class ReadingLibrary(val loaded: Boolean = false, val books: List<ReadingBook> = emptyList(), val error: String? = null)

@Entity(tableName = "reading_courses")
data class CourseEntity(@PrimaryKey val id: String, val title: String, val revision: Int,
                        val folder: String, val manifest: String, val importedAt: Long)
@Entity(tableName = "reading_progress", primaryKeys = ["courseId", "lessonId"])
data class ReadingProgressEntity(val courseId: String, val lessonId: String,
    val sentenceIndex: Int = 0, val audioPosition: Int = 0, val completed: Boolean = false, val updatedAt: Long = 0)
@Entity(tableName = "reading_vocabulary")
data class ReadingVocabularyEntity(@PrimaryKey val id: String, val word: String, val meaning: String,
    val sentence: String, val courseId: String, val lessonId: String,
    val linkedWordId: Long? = null, val status: String = "NEW", val streak: Int = 0,
    val dueAt: Long = 0, val lastStudiedAt: Long = 0)

@Dao interface ReadingDao {
    @Query("SELECT * FROM reading_courses ORDER BY importedAt DESC") fun observeCourses(): Flow<List<CourseEntity>>
    @Query("SELECT * FROM reading_courses WHERE id = :id") suspend fun course(id: String): CourseEntity?
    @Upsert suspend fun putCourse(course: CourseEntity)
    @Query("SELECT * FROM reading_progress") fun observeProgress(): Flow<List<ReadingProgressEntity>>
    @Query("SELECT * FROM reading_progress WHERE courseId = :course AND lessonId = :lesson")
    suspend fun progress(course: String, lesson: String): ReadingProgressEntity?
    @Upsert suspend fun putProgress(progress: ReadingProgressEntity)
    @Query("SELECT * FROM reading_vocabulary ORDER BY lastStudiedAt, word") fun observeVocabulary(): Flow<List<ReadingVocabularyEntity>>
    @Query("SELECT * FROM reading_vocabulary WHERE id = :id") suspend fun vocabulary(id: String): ReadingVocabularyEntity?
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun addVocabulary(word: ReadingVocabularyEntity)
    @Update suspend fun updateVocabulary(word: ReadingVocabularyEntity)
    @Query("DELETE FROM reading_courses") suspend fun clearCourses()
    @Query("DELETE FROM reading_progress") suspend fun clearProgress()
    @Query("DELETE FROM reading_vocabulary") suspend fun clearVocabulary()
}
