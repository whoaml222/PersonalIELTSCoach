package com.personalieltscoach.reading

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class CoursePackageReaderTest {
    @get:Rule val temporary = TemporaryFolder()
    private val reader = CoursePackageReader()
    private fun manifest(lesson: ReadingLesson = ReadingLesson("d001", "First lesson", 1,
        listOf(ReadingSentence("s001", "This knife is blunt.")))) = ReadingCourse(id = "test-course", title = "Test", lessons = listOf(lesson))
    private fun archive(vararg entries: Pair<String, ByteArray>): ByteArray = ByteArrayOutputStream().also { bytes ->
        ZipOutputStream(bytes).use { zip -> entries.forEach { (name, value) ->
            zip.putNextEntry(ZipEntry(name)); zip.write(value); zip.closeEntry()
        } }
    }.toByteArray()
    private fun rejects(bytes: ByteArray) {
        try { reader.extract(bytes.inputStream(), temporary.newFolder()); fail("Unsafe course accepted") }
        catch (_: IllegalArgumentException) { }
    }
    @Test fun importsDataOnlyCourse() {
        val expected = manifest()
        val bytes = archive("course.json" to Json.encodeToString(expected).toByteArray())
        assertEquals(expected, reader.extract(bytes.inputStream(), temporary.newFolder()))
    }
    @Test fun rejectsTraversalAndExecutableFiles() {
        listOf("../outside", "/course.json", "audio/../course.json", "audio\\d001.mp3", "page.html", "audio/a.exe").forEach {
            rejects(archive(it to "unsafe".toByteArray()))
        }
        assertFalse(temporary.root.resolve("outside").exists())
    }
    @Test fun rejectsMissingAudioAndDuplicateChapterIds() {
        val item = manifest().lessons.single()
        assertThrows(IllegalArgumentException::class.java) { reader.validate(manifest(item.copy(audio = "audio/missing.mp3")), setOf("course.json")) }
        assertThrows(IllegalArgumentException::class.java) { reader.validate(manifest().copy(lessons = listOf(item, item)), setOf("course.json")) }
    }
    @Test fun rejectsUnreferencedAudioAndUnsupportedVersions() {
        assertThrows(IllegalArgumentException::class.java) { reader.validate(manifest(), setOf("course.json", "audio/d001.mp3")) }
        assertThrows(IllegalArgumentException::class.java) { reader.validate(manifest().copy(schemaVersion = 100), setOf("course.json")) }
    }
    @Test fun stopsHighlyCompressedOversizedManifest() {
        val bytes = archive("course.json" to ByteArray(8 * 1024 * 1024 + 1) { 32 })
        assertTrue(bytes.size < 50_000)
        rejects(bytes)
    }
    @Test fun allowsExplicitlyUnavailableChapterWithoutFabricatingText() {
        reader.validate(manifest(ReadingLesson("d081", "Missing source", 81, ready = false)), setOf("course.json"))
        assertThrows(IllegalArgumentException::class.java) {
            reader.validate(manifest(ReadingLesson("d081", "Missing source", 81)), setOf("course.json"))
        }
    }
    @Test fun rejectsDeepJsonButAllowsBracketsInsideStrings() {
        assertThrows(IllegalArgumentException::class.java) { reader.validateJsonDepth("[".repeat(33) + "]".repeat(33)) }
        reader.validateJsonDepth("{\"text\":\"[[[ text \\\"quote\\\" ]]]\"}")
        assertThrows(IllegalArgumentException::class.java) { reader.validateJsonDepth("{\"text\":[]") }
    }
    @Test fun rejectsGrammarOffsetsOutsideWords() {
        val item = manifest().lessons.single()
        val bad = item.sentences.single().copy(grammar = listOf(
            com.personalieltscoach.domain.service.GrammarToken(99, 101, "knife", "test")))
        assertThrows(IllegalArgumentException::class.java) { reader.validate(manifest(item.copy(sentences = listOf(bad))), setOf("course.json")) }
    }
    @Test fun validatesPrivateCourseWhenProvided() {
        val path = System.getenv("IELTS_PRIVATE_COURSE_TEST")
        org.junit.Assume.assumeTrue(!path.isNullOrBlank())
        val course = java.io.File(requireNotNull(path)).inputStream().use { reader.extract(it, temporary.newFolder()) }
        assertEquals(104, course.lessons.size)
        assertEquals(103, course.lessons.count { it.ready })
        assertEquals(102, course.lessons.count { it.audio != null })
        assertTrue(course.lessons.filter { it.ready }.all { it.translation.isNotBlank() && it.sentences.all { s -> s.grammar.isNotEmpty() } })
        if (course.revision >= 2) {
            assertEquals(825, course.lessons.sumOf { it.sentences.size })
            assertTrue(course.lessons.flatMap { it.sentences }.all { sentence ->
                sentence.translation.isNotBlank() && sentence.grammar.all { it.syntax.isNotEmpty() }
            })
            val qingming = course.lessons.single { it.id == "d002" }
            assertTrue(qingming.sentences.size > 3)
            assertTrue(qingming.sentences.all { it.translation != qingming.translation })
        }
    }

    @Test fun rejectsSyntaxOutsideWordAndInvalidHeadsWhileAcceptingOldMetadata() {
        val item = manifest().lessons.single()
        val token = com.personalieltscoach.domain.service.GrammarToken(0, 4, "this", "主语")
        fun validate(part: com.personalieltscoach.domain.service.SyntaxToken?) {
            val annotated = item.sentences.single().copy(grammar = listOf(token.copy(syntax = listOfNotNull(part))))
            reader.validate(manifest(item.copy(sentences = listOf(annotated))), setOf("course.json"))
        }
        validate(null)
        val valid = com.personalieltscoach.domain.service.SyntaxToken(0, 4, "this", "DET", "DT", "det", 5)
        validate(valid)
        listOf(valid.copy(start = -1), valid.copy(end = 5), valid.copy(head = -1),
            valid.copy(head = 9999), valid.copy(dependency = "x".repeat(31))).forEach {
            assertThrows(IllegalArgumentException::class.java) { validate(it) }
        }
    }
}
