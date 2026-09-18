package com.personalieltscoach.reading

import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.util.zip.ZipInputStream

/** Data-only import. Never extract arbitrary paths, executable code or HTML. */
class CoursePackageReader(private val json: Json = Json { ignoreUnknownKeys = true }) {
    fun extract(input: InputStream, emptyDirectory: File): ReadingCourse {
        require(emptyDirectory.isDirectory && emptyDirectory.listFiles().orEmpty().isEmpty()) { "导入目录必须为空" }
        val seen = mutableSetOf<String>()
        var total = 0L
        ZipInputStream(input.buffered()).use { zip ->
            while (true) {
                val entry = zip.nextEntry ?: break
                require(!entry.isDirectory) { "课程包不能包含额外目录条目" }
                require(seen.size < 512 && seen.add(entry.name)) { "课程包条目重复或过多" }
                require(entry.name == "course.json" || Regex("audio/[a-z0-9_-]{1,64}\\.mp3").matches(entry.name)) { "课程包包含非法路径或文件类型" }
                val destination = File(emptyDirectory, entry.name).canonicalFile
                require(destination.path.startsWith(emptyDirectory.canonicalPath + File.separator)) { "课程文件越过导入目录" }
                val limit = if (entry.name == "course.json") 8L * 1024 * 1024 else 12L * 1024 * 1024
                require(entry.size < 0 || entry.size <= limit) { "单个课程文件过大" }
                destination.parentFile?.mkdirs()
                var length = 0L
                destination.outputStream().use { output ->
                    val buffer = ByteArray(8192)
                    while (true) {
                        val count = zip.read(buffer)
                        if (count < 0) break
                        length += count; total += count
                        require(length <= limit && total <= 128L * 1024 * 1024) { "课程解压大小超出安全上限" }
                        output.write(buffer, 0, count)
                    }
                }
                require(length > 0) { "课程包中存在空文件" }
                zip.closeEntry()
            }
        }
        require("course.json" in seen) { "这不是阅读课程包，请选择 .ieltscourse 文件" }
        val manifest = File(emptyDirectory, "course.json").readText()
        validateJsonDepth(manifest)
        val course = json.decodeFromString<ReadingCourse>(manifest)
        validate(course, seen)
        return course
    }
    internal fun validate(course: ReadingCourse, files: Set<String>) {
        fun validId(id: String) = Regex("[a-z0-9][a-z0-9_-]{0,63}").matches(id)
        require(course.schemaVersion == 1 && validId(course.id) && course.revision in 1..1_000_000) { "不支持的课程版本或编号" }
        require(course.title.isNotBlank() && course.title.length <= 200 && course.description.length <= 2000 && course.attribution.length <= 2000)
        require(course.lessons.size in 1..500 && course.lessons.map { it.id }.distinct().size == course.lessons.size) { "课文编号重复或数量无效" }
        require(course.lessons.map { it.order }.distinct().size == course.lessons.size) { "课文顺序重复" }
        course.lessons.forEach { lesson ->
            require(validId(lesson.id) && lesson.order > 0 && lesson.title.isNotBlank() && lesson.title.length <= 200)
            require(lesson.sentences.size <= 300 && (!lesson.ready || lesson.sentences.isNotEmpty()))
            require(lesson.sentences.map { it.id }.distinct().size == lesson.sentences.size)
            lesson.sentences.forEach { sentence ->
                require(validId(sentence.id) && sentence.text.isNotBlank() && sentence.text.length <= 3000 && sentence.translation.length <= 3000)
                val spans = com.personalieltscoach.domain.service.ContextDictionary.tokens.findAll(sentence.text).map { it.range }.toSet()
                require(sentence.grammar.size <= spans.size && sentence.grammar.map { it.start }.distinct().size == sentence.grammar.size)
                sentence.grammar.forEach { token ->
                    require(token.end > token.start && token.start until token.end in spans &&
                        token.lemma.length in 1..100 && token.explanation.length in 1..2000)
                    require(token.syntax.size <= 12 && token.syntax.map { it.start }.distinct().size == token.syntax.size)
                    token.syntax.forEach { part ->
                        require(part.start >= token.start && part.end <= token.end && part.end > part.start &&
                            part.head in sentence.text.indices && part.lemma.length in 1..100 &&
                            part.pos.length <= 20 && part.tag.length <= 20 && part.dependency.length <= 30)
                    }
                }
            }
            require(lesson.translation.length <= 30_000 && lesson.note.length <= 2000 && lesson.vocabulary.size <= 300)
            lesson.vocabulary.forEach { require(it.word.isNotBlank() && it.word.length <= 100 && it.meaning.length in 1..1000 && it.phonetic.length <= 200) }
            lesson.audio?.let { require(Regex("audio/[a-z0-9_-]{1,64}\\.mp3").matches(it) && it in files) { "课程引用的音频不存在" } }
        }
        require(files == course.lessons.mapNotNull { it.audio }.toSet() + "course.json") { "课程包包含未引用文件" }
    }

    internal fun validateJsonDepth(text: String) {
        var depth = 0; var quoted = false; var escaped = false
        text.forEach { character ->
            if (quoted) {
                if (escaped) escaped = false
                else if (character == '\\') escaped = true
                else if (character == '"') quoted = false
            } else when (character) {
                '"' -> quoted = true
                '{', '[' -> { depth++; require(depth <= 32) { "课程结构嵌套过深" } }
                '}', ']' -> { depth--; require(depth >= 0) { "课程结构无效" } }
            }
        }
        require(!quoted && depth == 0) { "课程结构不完整" }
    }
}
