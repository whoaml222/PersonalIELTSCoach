package com.personalieltscoach.domain.service

import org.junit.Assert.*
import org.junit.Test

class ContextDictionaryTest {
    @Test fun bluntExplainsKnifeAndTooToConstruction() {
        val sentence = "This knife is too blunt to cut bread."
        val result = requireNotNull(ContextDictionary.explain(sentence, sentence.indexOf("blunt")))
        assertTrue(result.meaning.contains("不锋利"))
        assertTrue(result.explanation.contains("too +"))
        assertTrue(result.contextual)
    }
    @Test fun findsInflectedWordsWithoutInventingDefinitions() {
        val result = requireNotNull(ContextDictionary.explain("I bought some apples.", 3))
        assertEquals("buy", result.lemma)
        assertFalse(result.meaning.startsWith("本地词库暂无"))
        assertTrue(requireNotNull(ContextDictionary.explain("Zzzunknown!", 1)).meaning.startsWith("本地词库暂无"))
    }
    @Test fun apostropheAndHyphenStayInsideOneToken() {
        assertEquals(listOf("I'm", "a", "hard-working", "student"), ContextDictionary.tokens.findAll("I'm a hard-working student.").map { it.value }.toList())
    }
    @Test fun sourceGlossOverridesAmbiguousDictionarySenses() {
        val result = requireNotNull(ContextDictionary.explain("This case is light.", 14, "轻的；不重"))
        assertEquals("轻的；不重", result.meaning)
        assertFalse("A glossary is a reference, not guaranteed sense disambiguation", result.contextual)
        assertTrue(result.explanation.contains("词表"))
    }
    @Test fun punctuationIsNotAWord() { assertNull(ContextDictionary.explain("Hi!", 2)) }
    @Test fun accentedNamesAreNotSplitAndExtraDictionaryWorksOffline() {
        assertEquals(listOf("Léon", "likes", "cafés"), ContextDictionary.tokens.findAll("Léon likes cafés.").map { it.value }.toList())
        val extra = mapOf("platypus" to LocalDefinition("platypus", "鸭嘴兽"))
        assertEquals("鸭嘴兽", ContextDictionary.explain("A platypus swims.", 3, extraDictionary = extra)?.meaning)
    }
    @Test fun knifeElsewhereDoesNotForceLiteralBluntSense() {
        val sentence = "He was blunt about the price of the knife."
        assertFalse(requireNotNull(ContextDictionary.explain(sentence, sentence.indexOf("blunt"))).contextual)
    }
    @Test fun sentenceGrammarUsesExactOffsets() {
        val sentence = "The knife is blunt."
        val grammar = listOf(GrammarToken(13, 18, "blunt", "在这里作表语，说明 knife 的状态。"))
        assertTrue(requireNotNull(ContextDictionary.explain(sentence, 14, grammar = grammar)).explanation.contains("作表语"))
    }
}
