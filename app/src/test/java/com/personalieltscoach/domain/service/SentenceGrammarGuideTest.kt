package com.personalieltscoach.domain.service

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class SentenceGrammarGuideTest {
    @Serializable data class Case(val text: String, val expected: String, val grammar: List<GrammarToken>)
    private val cases = Json.decodeFromString<List<Case>>(File("src/test/resources/grammar-fixtures.json").readText())
    private fun guide(text: String) = cases.single { it.text == text }.let { SentenceGrammarGuide.explain(text, it.grammar) }

    @Test fun verbGroupsDetermineTenseInsteadOfIndividualWords() {
        cases.forEach { case ->
            val result = SentenceGrammarGuide.explain(case.text, case.grammar)
            assertEquals(case.text, case.expected, result.clauses.firstOrNull { it.label == "主句" }?.tense)
            assertTrue(case.text, result.clauses.all { it.structure.isNotBlank() && it.changes.isNotBlank() && it.comparison.isNotBlank() })
        }
    }
    @Test fun heartShowsAuxiliaryIngChangesAndPrepositionalPhrase() {
        val result = guide("My heart was racing before the interview.")
        assertEquals("was racing", result.clauses.single().predicate)
        assertTrue(result.clauses.single().structure.contains("My heart［主语］"))
        assertTrue(result.clauses.single().changes.contains("race → racing"))
        assertTrue(result.phrases.single().contains("不是状语从句"))
    }
    @Test fun conjunctionAndNounPhraseAreDifferentAndClausesMayHaveDifferentTenses() {
        assertEquals(1, guide("I left before the interview.").clauses.size)
        assertEquals("时间状语从句", guide("I left before the interview started.").clauses.last().label)
        val combined = guide("She was cooking when I arrived.").clauses
        assertEquals(listOf("过去进行时", "一般过去时"), combined.map { it.tense })
        assertTrue(guide("I stayed home because it was raining.").clauses.any { it.label.startsWith("原因状语从句") })
    }
    @Test fun oldAnnotationsAndUnknownTextDoNotInventATense() {
        assertTrue(SentenceGrammarGuide.explain("Some unknown text.", emptyList()).clauses.isEmpty())
        val old = listOf(GrammarToken(0, 1, "I", "主语"))
        assertTrue(SentenceGrammarGuide.explain("I was running.", old).limitation.contains("新版"))
    }
    @Test fun passiveFormsAndNonFinitePhrasesAreNotTaughtAsSimplePastOrContinuous() {
        val passive = guide("The car was repaired yesterday.").clauses.single()
        assertTrue(passive.formation.startsWith("was/were + 过去分词"))
        assertTrue(passive.comparison.contains("主语承受动作"))
        assertTrue(guide("The car has been repaired.").clauses.single().formation.startsWith("have/has been"))
        assertTrue(guide("I left after finishing my work.").phrases.single().contains("这不是进行时"))
        assertTrue(guide("If it rains, we will stay inside.").clauses.any { it.label == "条件状语从句" && it.tense == "一般现在时" })
        assertTrue(guide("This is the book that I bought yesterday.").clauses.any { it.label == "定语从句" && it.tense == "一般过去时" })
    }
}
