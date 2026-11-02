package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.WordSource
import kotlinx.serialization.json.*
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.util.Locale

class Cet4WordPackTest {
    @Test fun everySourceWordIsPresentExactlyOnceInStableOrder() {
        val source = Json.parseToJsonElement(File("../content/cet4/vocabulary.json").readText())
            .jsonObject.getValue("entries").jsonArray
        val words = Cet4WordPack.words(123L)
        assertEquals(4458, words.size)
        assertEquals(Cet4WordPack.WORD_COUNT, words.size)
        assertEquals(words.size, words.map { it.word.lowercase(Locale.ROOT) }.distinct().size)
        assertEquals(source.map { it.jsonObject.getValue("word").jsonPrimitive.content }, words.map { it.word })
        assertTrue(words.all { it.source == WordSource.CET4 && it.status == "NEW" && it.meaning.isNotBlank() })
        assertTrue(words.all { it.createdAt == 123L && it.updatedAt == 123L })
        assertEquals(words.map { it.word }, Cet4WordPack.words(999).map { it.word })
    }

    @Test fun examplesAreExistingBilingualContentNotFabricatedFiller() {
        val originals = (Nce1WordPack.words(0) + Nce2WordPack.words(0) + Paul1000SentencePack.words(0))
            .map { it.example to it.exampleTranslation }.toSet()
        val words = Cet4WordPack.words(0)
        val examples = words.filter { it.example.isNotBlank() }
        assertTrue(examples.size > 1500)
        assertTrue(words.any { it.example.isBlank() })
        words.forEach { word ->
            assertEquals(word.word, word.example.isBlank(), word.exampleTranslation.isBlank())
            if (word.example.isNotBlank()) {
                assertTrue(word.word, (word.example to word.exampleTranslation) in originals)
                assertTrue(word.word, word.example.split(Regex("\\s+")).size <= 18)
                assertTrue(word.word, Regex("(?<![A-Za-z])${Regex.escape(word.word)}(?![A-Za-z])", RegexOption.IGNORE_CASE).containsMatchIn(word.example))
                assertFalse(word.example.contains("remember the word", ignoreCase = true))
            }
        }
        File("build/reports/content").mkdirs()
        File("build/reports/content/cet4-summary.json").writeText(
            """{"words":${words.size},"withExamples":${examples.size},"withoutExamples":${words.size - examples.size},"missingPhonetic":${words.count { it.phonetic.isBlank() }}}""")
    }
}
