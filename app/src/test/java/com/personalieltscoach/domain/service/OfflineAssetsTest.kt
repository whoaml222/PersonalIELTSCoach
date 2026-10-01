package com.personalieltscoach.domain.service

import com.personalieltscoach.data.seed.Nce1WordPack
import com.personalieltscoach.data.seed.Nce2WordPack
import com.personalieltscoach.data.seed.Paul1000SentencePack
import com.personalieltscoach.data.seed.SeedData
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class OfflineAssetsTest {
    @Test fun shippedGrammarCoversEveryCurrentWordExampleWithValidTokenOffsets() {
        val grammar = Json.decodeFromString<Map<String, List<GrammarToken>>>(File("src/main/assets/context-grammar.json").readText())
        val sentences = (Nce1WordPack.words(0) + Nce2WordPack.words(0) + Paul1000SentencePack.words(0) + SeedData.words(0)).map { it.example }.distinct()
        sentences.forEach { sentence ->
            val annotations = requireNotNull(grammar[sentence]) { "Missing offline grammar: $sentence" }
            val spans = ContextDictionary.tokens.findAll(sentence).map { it.range.first to it.range.last + 1 }.toSet()
            assertEquals("Token coverage: $sentence", spans, annotations.map { it.start to it.end }.toSet())
            assertTrue(annotations.all { it.explanation.isNotBlank() })
            annotations.forEach { word ->
                assertTrue("Missing syntax: $sentence / ${word.lemma}", word.syntax.isNotEmpty())
                assertTrue("Invalid syntax span: $sentence / ${word.lemma}", word.syntax.all { it.start >= word.start && it.end <= word.end &&
                    it.end > it.start && it.head in sentence.indices })
            }
        }
    }
    @Test fun supplementalDictionaryHasLicenseAndNoEscapedLayoutNoise() {
        val words = Json.decodeFromString<Map<String, LocalDefinition>>(File("src/main/assets/offline-lexicon.json").readText())
        assertTrue(words.size > 20_000)
        assertTrue(words.values.all { it.meaning.isNotBlank() && !it.meaning.contains("\\r") && !it.meaning.contains("\\n") })
        assertTrue(File("src/main/assets/ECDICT-LICENSE.txt").readText().contains("Permission is hereby granted"))
    }
}
