package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.WordSource
import com.personalieltscoach.domain.service.ContextDictionary
import com.personalieltscoach.domain.service.WordPresentation
import org.junit.Assert.*
import org.junit.Test

class Nce2WordPackTest {
    @Test fun allNinetySixLessonsAreIncludedInFirstOccurrenceOrder() {
        val words = Nce2WordPack.words(123L)
        assertEquals(877, Nce2WordPack.SOURCE_ENTRY_COUNT)
        assertEquals(851, words.size)
        assertEquals(words.size, words.map { it.word.lowercase() }.distinct().size)
        assertEquals("private", words.first().word)
        assertEquals("guide", words.last().word)
        val lessons = Regex("NCE2 Lesson (\\d+)")
        assertEquals((1..96).toSet(), words.flatMap { word -> lessons.findAll(word.level).map { it.groupValues[1].toInt() }.toList() }.toSet())
        val firstLessons = words.map { lessons.find(it.level)!!.groupValues[1].toInt() }
        assertEquals(firstLessons.sorted(), firstLessons)
        assertEquals("NCE2 Lesson 2, NCE2 Lesson 70", words.first { it.word == "ring" }.level)
        assertTrue(words.all { it.source == WordSource.NCE2 && it.status == "NEW" && it.createdAt == 123L && it.updatedAt == 123L })
    }

    @Test fun everyCardHasAShortBilingualExampleContainingItsWord() {
        val words = Nce2WordPack.words(0)
        words.forEach { word ->
            assertTrue(word.word, word.meaning.isNotBlank())
            assertTrue(word.word, word.phonetic.startsWith("/") || word.meaning.contains("音标待核对"))
            assertTrue(word.word, word.exampleTranslation.any { it in '\u4e00'..'\u9fff' })
            assertTrue(word.word, word.example.split(Regex("\\s+")).size <= 18)
            assertTrue(word.word, Regex("(?<![A-Za-z])${Regex.escape(word.word)}(?![A-Za-z])", RegexOption.IGNORE_CASE).containsMatchIn(word.example))
            assertFalse(word.word, word.example.contains("remember the word", ignoreCase = true))
        }
        assertTrue(words.map { it.example }.distinct().size >= 840)
        val templates = words.map { it.example.lowercase().replace(it.word.lowercase(), "{word}") }.groupingBy { it }.eachCount()
        assertTrue(templates.toString(), templates.values.max() <= 5)
    }

    @Test fun secondBookSensesAndCorrectionsAreNotOverwrittenByFirstBookExamples() {
        val words = Nce2WordPack.words(0).associateBy { it.word.lowercase() }
        assertTrue(words.getValue("play").example.contains("see a play"))
        assertTrue(words.getValue("hand").example.contains("second hand"))
        assertTrue(words.getValue("bank").example.contains("open on Saturdays"))
        assertTrue(words.getValue("lift").meaning.startsWith("n."))
        assertTrue(words.getValue("advertiser").meaning.contains("广告"))
        assertEquals("/waɪnd/", words.getValue("wind").phonetic)
        assertEquals("/baʊ/", words.getValue("bow").phonetic)
        assertEquals("/kləʊs/", words.getValue("close").phonetic)
        assertEquals("/əbˈdʒekt/", words.getValue("object").phonetic)
        assertTrue(words.getValue("former").meaning.startsWith("adj."))
        assertTrue(words.getValue("boldly").meaning.startsWith("adv."))
    }

    @Test fun tappingTheHeadwordUsesItsOwnBookSenseWithoutApplyingPhrasesToEveryWord() {
        val play = Nce2WordPack.words(0).first { it.word == "play" }
        val meaning = WordPresentation.contextMeanings(play).getValue("play")
        val context = ContextDictionary.explain(play.example, play.example.indexOf("play"), meaning)!!
        assertEquals(play.meaning, context.meaning)
        assertTrue(context.meaning.contains("戏"))
        val phrase = Nce2WordPack.words(0).first { it.word == "spare part" }
        assertTrue(WordPresentation.contextMeanings(phrase).isEmpty())
        val unknownBeforeBookTwo = ContextDictionary.explain("The clavichord is old.", 4)!!
        assertTrue(unknownBeforeBookTwo.meaning.contains("古钢琴"))
        val close = Nce2WordPack.words(0).first { it.word == "close" }
        val closeContext = ContextDictionary.explain(close.example, close.example.indexOf("close"), close.meaning,
            contextualPhonetic = WordPresentation.contextPhonetics(close).getValue("close"))!!
        assertEquals("/kləʊs/", closeContext.phonetic)
    }
}
