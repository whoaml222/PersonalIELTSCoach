package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.SentenceCardEntity
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.local.entity.WordSource

object Paul1000SentencePack {
    const val LEVEL = "Paul1000"

    fun cards(
        now: Long,
        nceWords: List<WordItemEntity> = Nce1WordPack.words(now, emptyList())
    ): List<SentenceCardEntity> = studyItems(nceWords).mapIndexed { index, item ->
        SentenceCardEntity(
            id = cardId(index),
            sentence = item.example.sentence,
            translation = item.example.translation,
            chunks = item.example.chunks,
            note = item.example.note,
            level = "核心口语",
            category = item.example.category,
            createdAt = now
        )
    }

    fun words(
        now: Long,
        nceWords: List<WordItemEntity> = Nce1WordPack.words(now, emptyList())
    ): List<WordItemEntity> = studyItems(nceWords).map { item ->
        WordItemEntity(
            word = item.entry.word,
            phonetic = item.entry.phonetic.takeIf(String::isNotBlank)?.let { "/$it/" }.orEmpty(),
            meaning = item.entry.meaning,
            example = item.example.sentence,
            exampleTranslation = item.example.translation,
            level = LEVEL,
            source = WordSource.PAUL1000,
            createdAt = now,
            updatedAt = now
        )
    }

    fun focusWord(cardId: String): String? {
        val index = cardId.removePrefix(CARD_PREFIX).toIntOrNull()?.minus(1) ?: return null
        return Paul1000WordPack.entries.getOrNull(index)?.word
    }

    private fun studyItems(nceWords: List<WordItemEntity>): List<StudyItem> {
        val nceByWord = nceWords.associateBy { it.word.lowercase() }
        return Paul1000WordPack.entries.mapIndexed { index, sourceEntry ->
            val nce = nceByWord[sourceEntry.word.lowercase()]
            val entry = sourceEntry.copy(
                phonetic = nce?.phonetic?.trim('/')?.takeIf(String::isNotBlank) ?: sourceEntry.phonetic,
                meaning = Paul1000ExampleFactory.normalizedMeaning(sourceEntry)
            )
            StudyItem(entry, Paul1000ExampleFactory.create(entry, index))
        }
    }

    private fun cardId(index: Int): String = "$CARD_PREFIX${(index + 1).toString().padStart(4, '0')}"

    private data class StudyItem(
        val entry: Paul1000Entry,
        val example: Paul1000Example
    )

    private const val CARD_PREFIX = "paul-"
}
