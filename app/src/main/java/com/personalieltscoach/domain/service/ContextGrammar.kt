package com.personalieltscoach.domain.service

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable data class GrammarToken(val start: Int, val end: Int, val lemma: String,
                                     val explanation: String,
                                     val syntax: List<SyntaxToken> = emptyList())
/** UTF-16 spans; contractions may have more than one syntactic part. */
@Serializable data class SyntaxToken(val start: Int, val end: Int, val lemma: String,
    val pos: String, val tag: String, val dependency: String, val head: Int)
@Serializable data class LocalDefinition(val word: String, val meaning: String, val phonetic: String = "")

/** Static annotations prepared offline; never sends a learner's sentence anywhere. */
object ContextGrammar {
    private val lock = Mutex()
    private var cache: Map<String, List<GrammarToken>>? = null
    private var lexicon: Map<String, LocalDefinition>? = null
    suspend fun dictionary(context: Context): Map<String, LocalDefinition> = withContext(Dispatchers.IO) {
        lock.withLock {
            lexicon ?: context.assets.open("offline-lexicon.json").bufferedReader().use {
                Json.decodeFromString<Map<String, LocalDefinition>>(it.readText())
            }.also { lexicon = it }
        }
    }
    suspend fun lookup(context: Context, sentence: String): List<GrammarToken> = withContext(Dispatchers.IO) {
        lock.withLock {
            val data = cache ?: context.assets.open("context-grammar.json").bufferedReader().use {
                Json.decodeFromString<Map<String, List<GrammarToken>>>(it.readText())
            }.also { cache = it }
            data[sentence].orEmpty()
        }
    }
}
