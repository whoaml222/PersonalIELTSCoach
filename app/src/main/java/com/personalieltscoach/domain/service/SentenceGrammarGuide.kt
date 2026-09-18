package com.personalieltscoach.domain.service

data class ClauseGuide(val label: String, val tense: String, val predicate: String,
    val structure: String, val formation: String, val meaning: String, val changes: String,
    val comparison: String)
data class SentenceGuide(val clauses: List<ClauseGuide>, val phrases: List<String>, val limitation: String = "")

/** Offline teaching notes based on complete verb groups, never on one suffix alone. */
object SentenceGrammarGuide {
    private val clauseRelations = setOf("ROOT", "conj", "ccomp", "advcl", "relcl", "csubj")
    private val finiteTags = setOf("VBD", "VBP", "VBZ", "MD")
    private val auxRelations = setOf("aux", "auxpass", "cop")
    private val subjectRelations = setOf("nsubj", "nsubjpass", "csubj", "expl")

    fun explain(sentence: String, annotations: List<GrammarToken>): SentenceGuide {
        val tokens = annotations.flatMap { it.syntax }.distinctBy { it.start }.sortedBy { it.start }
        if (tokens.isEmpty() || tokens.any { it.start < 0 || it.end <= it.start || it.end > sentence.length }) {
            return SentenceGuide(emptyList(), emptyList(),
                "这句话暂没有新版离线结构标注，不能只看某一个词就确定时态。个人课程请导入新版课程包；单词释义仍可使用。")
        }
        fun text(t: SyntaxToken) = sentence.substring(t.start, t.end)
        fun children(t: SyntaxToken) = tokens.filter { it.head == t.start && it.start != t.start }
        fun group(t: SyntaxToken): List<SyntaxToken> {
            val direct = children(t).filter { it.dependency in auxRelations || it.dependency == "neg" }
            return (direct + t).sortedBy { it.start }
        }
        fun phrase(t: SyntaxToken): String {
            val selected = mutableSetOf(t.start)
            for (step in 0 until tokens.size.coerceAtMost(150)) {
                val next = tokens.filter { it.head in selected && it.dependency !in setOf("relcl", "advcl", "ccomp") }.map { it.start }
                if (selected.containsAll(next)) break
                selected.addAll(next)
            }
            val members = tokens.filter { it.start in selected }
            return sentence.substring(members.minOf { it.start }, members.maxOf { it.end }).take(180)
        }
        val roots = tokens.filter { token ->
            if (token.dependency !in clauseRelations || token.pos !in setOf("VERB", "AUX")) false
            else {
                val verbGroup = group(token)
                verbGroup.any { it.tag in finiteTags } ||
                    (token.dependency == "ROOT" && token.tag == "VB" &&
                        children(token).none { it.dependency in subjectRelations } &&
                        verbGroup.none { it.lemma == "to" })
            }
        }
        val clauses = roots.map { root ->
            val members = group(root)
            val auxiliary = members.filter { it.start != root.start && it.dependency in auxRelations }
            val memberStarts = members.map { it.start }.toSet()
            val related = tokens.filter { it.head in memberStarts && it.start !in memberStarts }
            val mark = related.firstOrNull { it.dependency == "mark" ||
                (it.dependency == "advmod" && it.lemma in setOf("when", "while", "where")) }?.let(::text)?.lowercase()
            val label = when (root.dependency) {
                "ROOT" -> "主句"
                "advcl" -> when (mark) {
                    "because", "since" -> "原因状语从句（since 还可能表示时间）"
                    "if", "unless" -> "条件状语从句"
                    "when", "while", "before", "after", "until", "once" -> "时间状语从句"
                    "although", "though", "even" -> "让步状语从句"
                    else -> "状语从句"
                }
                "relcl" -> "定语从句"
                "ccomp" -> "内容从句"
                "csubj" -> "主语从句"
                else -> "并列分句"
            }
            val subject = related.firstOrNull { it.dependency in subjectRelations }
                ?: if (root.dependency == "conj") tokens.firstOrNull { it.head == root.head && it.dependency in subjectRelations } else null
            val complement = children(root).firstOrNull { it.dependency in setOf("dobj", "obj", "attr", "acomp", "oprd") }
            val predicate = members.joinToString(" ", transform = ::text)
            val modal = auxiliary.firstOrNull { it.tag == "MD" }
            val have = auxiliary.firstOrNull { it.lemma == "have" }
            val be = auxiliary.filter { it.lemma == "be" }
            val passive = auxiliary.any { it.dependency == "auxpass" }
            val continuous = (root.tag == "VBG" && be.isNotEmpty()) ||
                (passive && be.any { it.tag == "VBG" })
            val past = members.any { it.tag == "VBD" }
            val imperative = root.dependency == "ROOT" && root.tag == "VB" && subject == null &&
                auxiliary.all { it.lemma == "do" }
            val type = when {
                modal != null && modal.lemma !in setOf("will", "shall") -> "modal"
                modal != null && have != null -> if (continuous) "futurePerfectContinuous" else "futurePerfect"
                modal != null -> if (continuous) "futureContinuous" else "future"
                have != null -> if (past) { if (continuous) "pastPerfectContinuous" else "pastPerfect" }
                    else { if (continuous) "presentPerfectContinuous" else "presentPerfect" }
                continuous -> if (past) "pastContinuous" else "presentContinuous"
                imperative -> "imperative"
                past -> "past"
                members.any { it.tag in setOf("VBP", "VBZ") } -> "present"
                else -> "unknown"
            }
            val info = forms.getValue(type)
            val voice = if (passive) " · 被动语态" else ""
            val shape = buildList {
                add(subject?.let { "${phrase(it)}［主语］" } ?: if (imperative) "(you)［省略的主语］" else "主语需结合前文")
                add("$predicate［谓语动词组］")
                complement?.let { add("${phrase(it)}［${if (it.dependency in setOf("dobj", "obj")) "宾语" else "表语／补足语"}］") }
            }.joinToString(" + ")
            val changes = members.filter { it.dependency != "neg" }.map { part ->
                val word = text(part)
                when {
                    part.tag == "MD" -> "$word 放在主要动词前，后面接原形；could/would 等不一定指过去。"
                    part.lemma == "do" && part.start != root.start ->
                        if (imperative) "$word 帮助构成否定祈使句，主要动词 ${text(root)} 保持原形，不表示过去。"
                        else "$word 承担时态或疑问／否定功能，主要动词 ${text(root)} 保持原形。"
                    part.lemma == "have" && part.start != root.start -> "$word 是完成结构的助动词，必须与后面的过去分词一起判断，不是单独加 have 就成完成时。"
                    part.lemma == "be" && part.start != root.start ->
                        "$word 是 be 在这里的形式，与 ${text(root)} 一起构成${info.name}$voice；不能把 $word 独立当成整句时态。"
                    part.tag == "VBG" -> "${part.lemma} → $word：动词 -ing 形式；本句与 be 配合表示进行，不是所有 -ing 都是进行时。"
                    part.tag == "VBN" -> "${part.lemma} → $word：过去分词；在本句与${if (have != null) " have/has/had" else " be"} 配合，不是独立的一般过去式。"
                    part.tag == "VBD" -> "${part.lemma} → $word：过去形式，规则动词通常加 -ed，不规则动词需记变化。"
                    part.tag == "VBZ" && part.lemma != "be" -> "${part.lemma} → $word：一般现在时的第三人称单数形式，与主语保持一致。"
                    part.tag == "VB" -> "$word 使用动词原形${if (imperative) "，向对方提出要求或建议" else "，与前面的助动词一起构成谓语"}。"
                    else -> "$word 是本句动词组的一部分，请按整个组合理解。"
                }
            }.joinToString("\n")
            ClauseGuide(label, info.name + voice, predicate, shape,
                (if (passive) passiveForms[type] ?: info.form else info.form) +
                    if (passive) "；主语是动作的承受者" else "",
                info.meaning + if (root.dependency == "advcl" && mark in setOf("if", "unless") && past)
                    " 条件句中的过去形式也可能表达假设，不一定是已经发生的过去事实。" else "",
                changes, if (passive) "Someone repaired the car. 有人修好了车（主语执行动作）。\nThe car was repaired. 车修好了（主语承受动作）。\n变化：把 the car 放在主语位置，使用 was + repaired；repaired 在这里是过去分词。"
                else info.comparison)
        }
        val phrases = tokens.filter { it.dependency == "prep" && it.lemma in setOf("before", "after", "during", "without", "by") }
            .map { prep ->
                val noun = children(prep).firstOrNull { it.dependency in setOf("pobj", "pcomp") }
                "${phrase(prep)}：${if (prep.lemma in setOf("before", "after", "during")) "时间" else "介词"}短语。" +
                    if (noun?.tag == "VBG") "介词后用 -ing，把动作当作一件事；这不是进行时。"
                    else "介词后是名词性成分，没有自己的完整主谓结构，不是状语从句，也不单独决定时态。"
            }
        return SentenceGuide(clauses, phrases.distinct(), if (clauses.isEmpty())
            "这句话可能是省略句或含尚未覆盖的结构，暂不强行标注时态。先找主语，再找完整的谓语动词组。" else
            "以上为离线结构提示；一句话可以含多个时态，时间词是线索，时态主要看动词组。")
    }

    private data class Form(val name: String, val form: String, val meaning: String, val comparison: String)
    private val passiveForms = mapOf(
        "present" to "am/is/are + 过去分词", "past" to "was/were + 过去分词",
        "presentContinuous" to "am/is/are being + 过去分词", "pastContinuous" to "was/were being + 过去分词",
        "presentPerfect" to "have/has been + 过去分词", "pastPerfect" to "had been + 过去分词",
        "future" to "will be + 过去分词", "futurePerfect" to "will have been + 过去分词",
        "modal" to "情态动词 + be + 过去分词；也可用 情态动词 + have been + 过去分词"
    )
    private val forms = mapOf(
        "present" to Form("一般现在时", "主语 + 动词原形／第三人称单数；be 用 am/is/are", "常说明习惯、事实或目前状态，不等于‘此刻正在做’。", "I work here. 我在这里工作（通常情况）。\nI am working here today. 我今天正在这里工作。"),
        "past" to Form("一般过去时", "主语 + 动词过去式；疑问／否定常用 did + 动词原形", "通常说明过去的动作或状态；did 已承担过去时变化，后面的实义动词不能再变过去式。", "I work here. 我在这里工作。\nI worked here last year. 我去年在这里工作。\nI didn't work yesterday. 我昨天没上班。"),
        "presentContinuous" to Form("现在进行时", "am/is/are + 动词 -ing", "说明现在或这一阶段正在进行的动作，也可结合语境表示已安排的将来。", "I work here. 我在这里工作。\nI am working now. 我现在正在工作。"),
        "pastContinuous" to Form("过去进行时", "was/were + 动词 -ing", "把视角放在过去某个时刻，强调当时正在进行的动作。", "My heart is racing now. 我现在心跳很快。\nMy heart was racing before the interview. 面试前我当时心跳得很快。\n变化：is → was；racing 保留 -ing。"),
        "presentPerfect" to Form("现在完成时", "have/has + 过去分词", "把过去的经历、结果或延续情况与现在联系起来，不只表示过去发生过。", "I lost my keys yesterday. 我昨天丢了钥匙（叙述过去）。\nI have lost my keys. 我把钥匙弄丢了（现在还受影响）。\n变化：lost → have lost；lost 在第二句是过去分词。"),
        "pastPerfect" to Form("过去完成时", "had + 过去分词", "说明在过去另一个时间点或动作之前已经发生的事，先建立那个过去参照点。", "When I arrived, she had left. 我到时，她已经走了。\nhad left 先发生，arrived 后发生。"),
        "presentPerfectContinuous" to Form("现在完成进行时", "have/has been + 动词 -ing", "强调从过去延续到现在或刚刚结束的过程／持续时间。", "I am waiting. 我正在等。\nI have been waiting for an hour. 我已经等了一个小时。"),
        "pastPerfectContinuous" to Form("过去完成进行时", "had been + 动词 -ing", "强调截至过去某个时刻，动作已经持续了一段时间。", "I had been waiting for an hour when she arrived. 她到时，我已经等了一个小时。"),
        "future" to Form("will 将来表达", "will + 动词原形", "常表示预测、临时决定或意愿；要结合语境，不是看到将来时间词就必须加 will。", "I work here. 我在这里工作。\nI will call you tomorrow. 我明天会给你打电话。"),
        "futureContinuous" to Form("将来进行时", "will be + 动词 -ing", "表示将来某个时刻正在进行的动作。", "I will be working at ten tomorrow. 明天十点我会正在工作。"),
        "futurePerfect" to Form("将来完成时", "will have + 过去分词", "表示截至将来某个时间点已经完成。", "I will have finished by Friday. 到周五我就完成了。"),
        "futurePerfectContinuous" to Form("将来完成进行时", "will have been + 动词 -ing", "强调截至将来某个时间点已经持续多久。", "By June, I will have been working here for a year. 到六月，我在这里就工作满一年了。"),
        "modal" to Form("情态表达", "情态动词 + 原形；后面也可组合完成／进行／被动结构", "can/could/would 等表达能力、可能性、请求或假设。不能仅凭 could/would 判断为一般过去时。", "Can you help me? 你能帮我吗？\nCould you help me? 你能帮帮我吗（更委婉，不是在问过去）？"),
        "imperative" to Form("祈使句", "(you) + 动词原形；否定常用 don't + 原形", "主语 you 通常省略，用来提出要求、建议或指令，不强行归成某个过去／现在时。", "Open the door, please. 请开门。\nDon't open the door. 不要开门。"),
        "unknown" to Form("时态待核对", "先找完整动词组", "当前离线标注不足，不根据单个词尾猜测。", "先找谁／什么，再找动作，最后看时间、地点等补充信息。")
    )
}
