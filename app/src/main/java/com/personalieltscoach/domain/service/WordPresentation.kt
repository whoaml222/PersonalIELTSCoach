package com.personalieltscoach.domain.service

import com.personalieltscoach.data.local.entity.WordItemEntity

object WordPresentation {
    // A book-specific sense must not silently turn into the other book's sense
    // when the learner taps its headword (e.g. NCE2 play = a theatre play).
    // Multiword phrases are not applied to their individual component words.
    fun contextMeanings(word: WordItemEntity): Map<String, String> =
        if (ContextDictionary.tokens.matches(word.word)) mapOf(word.word.lowercase() to word.meaning)
        else emptyMap()

    fun contextPhonetics(word: WordItemEntity): Map<String, String> =
        if (ContextDictionary.tokens.matches(word.word)) mapOf(word.word.lowercase() to word.phonetic)
        else emptyMap()

    private val typeNames = linkedMapOf(
        "modal v." to "情态动词",
        "aux." to "助动词",
        "interj." to "感叹词",
        "num." to "数词",
        "art." to "冠词",
        "pron." to "代词",
        "conj." to "连词",
        "prep." to "介词",
        "adj." to "形容词",
        "adv." to "副词",
        "det." to "限定词",
        "int." to "感叹词",
        "vt." to "及物动词",
        "vi." to "不及物动词",
        "v." to "动词",
        "n." to "名词"
    )

    fun meaningWithChineseType(meaning: String): String {
        val trimmed = meaning.trim()
        val entry = typeNames.entries.firstOrNull { (abbreviation, _) ->
            trimmed.startsWith("$abbreviation ", ignoreCase = true)
        } ?: return trimmed
        val definition = trimmed.substring(entry.key.length).trim()
        return "${entry.key} ${entry.value} · $definition"
    }
}
