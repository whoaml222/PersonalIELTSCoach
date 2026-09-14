package com.personalieltscoach.domain.service

import com.personalieltscoach.data.seed.Nce1WordPack
import com.personalieltscoach.data.seed.Paul1000SentencePack

data class WordContext(
    val word: String,
    val lemma: String,
    val phonetic: String,
    val meaning: String,
    val explanation: String,
    val contextual: Boolean = false
)

/** Local, conservative explanations. Unknown senses are labelled, never guessed by a remote call. */
object ContextDictionary {
    private val dictionary by lazy {
        (Paul1000SentencePack.words(0) + Nce1WordPack.words(0))
            .map { LocalDefinition(it.word, it.meaning, it.phonetic) }.associateBy { it.word.lowercase() }
    }
    val tokens = Regex("[A-Za-zÀ-ÖØ-öø-ÿ]+(?:['’-][A-Za-zÀ-ÖØ-öø-ÿ]+)*")
    private val irregular = mapOf(
        "was" to "be", "were" to "be", "been" to "be", "being" to "be",
        "went" to "go", "gone" to "go", "did" to "do", "done" to "do", "had" to "have",
        "bought" to "buy", "brought" to "bring", "thought" to "think", "taught" to "teach",
        "took" to "take", "taken" to "take", "made" to "make", "said" to "say", "told" to "tell",
        "felt" to "feel", "found" to "find", "left" to "leave", "gave" to "give", "given" to "give",
        "saw" to "see", "seen" to "see", "knew" to "know", "known" to "know", "got" to "get",
        "ate" to "eat", "eaten" to "eat", "drank" to "drink", "drunk" to "drink",
        "wrote" to "write", "written" to "write", "spoke" to "speak", "spoken" to "speak",
        "ran" to "run", "slept" to "sleep", "met" to "meet", "paid" to "pay", "kept" to "keep",
        "lost" to "lose", "won" to "win", "sat" to "sit", "stood" to "stand", "children" to "child",
        "men" to "man", "women" to "woman", "feet" to "foot", "teeth" to "tooth", "mice" to "mouse"
    )
    private val contractions = mapOf(
        "i'm" to "I am；我是／我正在", "you're" to "you are；你是／你正在",
        "we're" to "we are；我们是／我们正在", "they're" to "they are；他们是／他们正在",
        "don't" to "do not；不", "doesn't" to "does not；不（第三人称单数）", "didn't" to "did not；过去没有",
        "can't" to "cannot；不能", "couldn't" to "could not；不能／过去无法", "won't" to "will not；不会／不愿",
        "isn't" to "is not；不是／没有", "aren't" to "are not；不是／没有", "wasn't" to "was not；过去不是",
        "weren't" to "were not；过去不是", "haven't" to "have not；还没有", "hasn't" to "has not；还没有",
        "i've" to "I have；我已经…", "you've" to "you have；你已经…", "we've" to "we have；我们已经…",
        "i'll" to "I will；我会…", "you'll" to "you will；你会…", "we'll" to "we will；我们会…",
        "let's" to "let us；我们…吧", "it's" to "it is 或 it has；它是／它已经…",
        "that's" to "that is 或 that has；那是／那已经…", "there's" to "there is 或 there has；有／已经有…",
        "he's" to "he is 或 he has；他是／他已经…", "she's" to "she is 或 she has；她是／她已经…",
        "i'd" to "I would 或 I had；我会／我曾经…"
    )

    fun explain(sentence: String, offset: Int, contextualMeaning: String = "", grammar: List<GrammarToken> = emptyList(),
                extraDictionary: Map<String, LocalDefinition> = emptyMap()): WordContext? {
        val matches = tokens.findAll(sentence).toList()
        val index = matches.indexOfFirst { offset in it.range }
        if (index < 0) return null
        val word = matches[index].value
        val key = word.lowercase().replace('’', '\'')
        val previous = matches.getOrNull(index - 1)?.value?.lowercase().orEmpty()
        val next = matches.getOrNull(index + 1)?.value?.lowercase().orEmpty()
        val annotation = grammar.firstOrNull { it.start == matches[index].range.first && it.end == matches[index].range.last + 1 }
        val candidates = buildList {
            add(key)
            annotation?.lemma?.lowercase()?.let(::add)
            irregular[key]?.let(::add)
            if (key.endsWith("'s")) add(key.dropLast(2))
            if (key.endsWith("ies")) add(key.dropLast(3) + "y")
            if (key.endsWith("s")) { add(key.dropLast(1)); add(key.dropLast(2)) }
            for (suffix in listOf("ing", "ed")) if (key.endsWith(suffix)) {
                val stem = key.dropLast(suffix.length)
                add(stem); add(stem + "e")
                if (stem.length > 2 && stem.last() == stem[stem.lastIndex - 1]) add(stem.dropLast(1))
                if (stem.endsWith("i")) add(stem.dropLast(1) + "y")
            }
        }
        val entry = candidates.firstNotNullOfOrNull { dictionary[it] ?: extraDictionary[it] }
        val notes = mutableListOf<String>()
        var meaning = contextualMeaning.ifBlank { contractions[key] ?: entry?.meaning.orEmpty() }
        var exact = key in contractions
        if (contextualMeaning.isNotBlank()) notes += "这里附有课文词表释义；若列出多个义项，请结合整句选择，词表本身不等于逐句消歧结果。"
        if (key in contractions) notes += "这是口语中的缩写。展开后理解句子，不要把它当作一个新的动词时态。"
        when (key) {
            "a", "an" -> {
                meaning = "一个／一位（不定冠词，常不必逐字翻译）"; exact = true
                notes += "这里引出一个单数可数名词。a/an 按后面单词的起始读音选择，不只看字母；例如 a university、an hour。"
            }
            "the" -> { meaning = "这个／那个；特指双方能识别的事物"; exact = true; notes += "the 表示特指，往往因为前面提过、现场可见，或后面的修饰已经说明是哪一个。中文通常可以不译。" }
            "my", "your", "his", "our", "their" -> notes += "这里表示所属关系，后面接名词（中间可以有形容词），不是单独作主语的人称代词。"
            "this", "that", "these", "those" -> notes += "可以指代人或物，也可放在名词前作限定词。this/that 通常指单数，these/those 指复数；若 that 引出完整分句，则可能是连词，需结合后文。"
            "am", "is", "are", "was", "were", "be", "been" -> {
                notes += when {
                    next.endsWith("ing") -> "如果后面是动词的 -ing 形式，be 可能与它构成进行时；但 morning 等名词也以 -ing 结尾，不能只凭拼写判断。结合上方句法提示和整句意思区分。"
                    else -> "be 可连接主语和身份、状态或位置；也可与过去分词构成被动语态。它不是每次都要译成‘是’。"
                }
            }
            "can", "could", "will", "would", "should", "must", "may", "might" -> notes += "这是情态动词，后面通常接动词原形，不加第三人称 -s。could/would 在请求中常用于语气更礼貌，不一定表示过去。"
            "to" -> notes += if (previous in setOf("go", "went", "going", "come", "drive", "travel", "move"))
                "这里要看 to 后面的内容：接地点时表示方向‘去／到’；接动词原形时常表示目的。"
                else "to 后接动词原形时构成不定式；接名词时是介词，常表示方向或对象。不能把所有 to 都逐字译成‘去’。"
            "in", "on", "at" -> notes += when {
                key == "at" && next in setOf("work", "home", "school", "reception") -> "${key} ${next} 是常见地点表达，表示处于这个地点或活动中，不是贴着物体表面。"
                key == "on" && next in setOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday") -> "具体星期前用 on；表示一段较大的时间如月份、年份时通常用 in，具体钟点通常用 at。"
                else -> "这是介词，用来连接位置、时间或其他关系。in 常表示范围内，on 常表示表面或具体日期，at 常表示位置点或时刻；具体搭配以整句为准。"
            }
            "not", "never" -> notes += "这里是否定表达。not 通常与 be、助动词或情态动词搭配；never 表示‘从不’，本身已经含否定意思。"
            "and", "but", "or", "because", "if", "when", "although" -> notes += "这里连接词语或分句，帮助说明并列、转折、选择、原因、条件或时间关系；要把两边内容一起理解。"
        }
        if (previous in setOf("can", "could", "will", "would", "should", "must", "may", "might"))
            notes += "前面的 $previous 是情态动词，后面的主要动词通常用原形；两者之间也可以插入副词，不能把紧跟的每个词都当作动词。"
        if (key.endsWith("ing") && previous in setOf("of", "for", "without", "about", "by", "after", "before"))
            notes += "前面是介词 $previous，后面用动词的 -ing 形式，把动作当作一件事；这不等于进行时。"
        if (key == "blunt" && Regex("\\b(?:blunt knife|knife (?:is|was|seems|looks) (?:too |very |quite )?blunt)\\b", RegexOption.IGNORE_CASE).containsMatchIn(sentence)) {
            meaning = "钝的；刀刃不锋利"; exact = true
            notes += "blunt 在这里描述刀刃不锋利；与形状、大小不同，它通常用来描述刀、剪刀等刃口。"
            if (Regex("\\btoo blunt to\\b", RegexOption.IGNORE_CASE).containsMatchIn(sentence))
                notes += "too blunt to… 表示‘太钝了，以至于不能…’：too + 形容词 + to + 动词原形。"
        }
        if (key != entry?.word?.lowercase() && entry != null && key !in contractions)
            notes += "词库对应形式：${entry.word}。这里保留原句中的 $word；不同词形和不同词义不要混成同一条规则。"
        if (annotation != null) {
            notes.add(0, annotation.explanation)
            notes += "句法提示来自离线分析，供理解结构参考；多义词仍需结合整句选择含义。"
        }
        if (notes.isEmpty()) notes += "先结合整句理解这个词与周围词语的搭配。当前离线规则尚未覆盖这一处的完整语法，下面的词库释义不代表所有义项都适用于本句。"
        if (meaning.isBlank()) meaning = "本地词库暂无可靠释义；不会自动联网或编造解释。"
        return WordContext(word, entry?.word ?: word, entry?.phonetic.orEmpty(), meaning, notes.joinToString("\n\n"), exact)
    }
}
