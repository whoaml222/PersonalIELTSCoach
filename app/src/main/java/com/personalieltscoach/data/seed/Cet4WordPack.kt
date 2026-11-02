package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.local.entity.WordSource
import java.util.Locale

/** A vocabulary pack, not a second set of sentence progress records. */
object Cet4WordPack {
    const val WORD_COUNT = 4458
    private val templates by lazy {
        // Reuse complete, app-authored bilingual entries, including their matching sense/IPA.
        // No generated "remember this word" filler and no copyrighted textbook examples.
        val existing = (Nce1WordPack.words(0) + Paul1000SentencePack.words(0) + Nce2WordPack.words(0))
            .groupBy { it.word.lowercase(Locale.ROOT) }
        Cet4Vocabulary.entries.map { fields ->
            val (word, ipa, definition) = fields
            val known = existing[word.lowercase(Locale.ROOT)].orEmpty().firstOrNull {
                it.example.isNotBlank() && it.exampleTranslation.isNotBlank() &&
                    it.example.split(Regex("\\s+")).size <= 18 &&
                    Regex("(?<![A-Za-z])${Regex.escape(word)}(?![A-Za-z])", RegexOption.IGNORE_CASE).containsMatchIn(it.example)
            }
            WordItemEntity(
                word = word, phonetic = known?.phonetic?.takeIf(String::isNotBlank)
                    ?: ipa.takeIf(String::isNotBlank)?.let { "/${it.trim('/')}" + "/" }.orEmpty(),
                meaning = known?.meaning ?: definition,
                example = known?.example.orEmpty(), exampleTranslation = known?.exampleTranslation.orEmpty(),
                level = "CET4 四级词汇", source = WordSource.CET4, createdAt = 0, updatedAt = 0
            )
        }
    }

    fun words(now: Long): List<WordItemEntity> = templates.map { it.copy(createdAt = now, updatedAt = now) }
}
