package com.personalieltscoach.ai

import com.personalieltscoach.domain.model.*

interface AIService {
    suspend fun analyzeSentence(sentence: String): SentenceAnalysisResult
    suspend fun translateSentence(sentence: String): TranslationResult
    suspend fun explainGrammar(sentence: String): GrammarExplanationResult
    suspend fun correctWriting(
        promptChinese: String,
        text: String,
        userLevel: String
    ): WritingCorrectionResult
    suspend fun generateDailySuggestion(progress: UserProgressSummary): DailySuggestionResult
    suspend fun testConnection(): Boolean
}

enum class AIProviderType { GPT, DEEPSEEK, GEMINI, CLAUDE, DOUBAO, QWEN }

