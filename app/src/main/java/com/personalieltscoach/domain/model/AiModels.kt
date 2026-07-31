package com.personalieltscoach.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WordExplanation(
    val word: String,
    val meaning: String,
    val role: String
)

@Serializable
data class PhraseExplanation(
    val phrase: String,
    val meaning: String
)

@Serializable
data class SentenceAnalysisResult(
    val translation: String,
    val wordExplanation: List<WordExplanation>,
    val phraseExplanation: List<PhraseExplanation>,
    val sentenceStructure: String,
    val grammarPoint: String,
    val imitationExample: String,
    val imitationExampleTranslation: String
)

@Serializable
data class TranslationResult(val translation: String)

@Serializable
data class GrammarExplanationResult(
    val sentenceStructure: String,
    val grammarPoint: String
)

@Serializable
data class WritingMistake(
    val original: String,
    val corrected: String,
    val reason: String
)

@Serializable
data class WritingCorrectionResult(
    val correctedText: String,
    val chineseTranslation: String,
    val mistakes: List<WritingMistake>,
    val betterExpression: String,
    val nextPracticeSuggestion: String
)

@Serializable
data class UserProgressSummary(
    val level: String,
    val completedTasks: Int,
    val totalTasks: Int,
    val wrongWords: Int,
    val studiedWords: Int,
    val studiedSentences: Int
)

@Serializable
data class DailySuggestionResult(val suggestion: String)

data class PlacementResult(
    val level: String,
    val estimatedVocabulary: Int,
    val weakSkills: String,
    val route: String
)

data class DailyStats(
    val minutes: Int = 0,
    val newWords: Int = 0,
    val reviewedWords: Int = 0,
    val wrongWords: Int = 0,
    val sentences: Int = 0,
    val readingWords: Int = 0,
    val writingSentences: Int = 0
)

