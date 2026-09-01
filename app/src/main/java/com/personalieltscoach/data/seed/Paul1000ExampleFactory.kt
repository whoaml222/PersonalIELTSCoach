package com.personalieltscoach.data.seed

internal data class Paul1000Example(
    val sentence: String,
    val translation: String,
    val chunks: String,
    val note: String,
    val category: String
)

/**
 * Creates short, original examples intended for spoken English practice.
 * High-risk function words and irregular uses are written explicitly; content
 * words use small semantic groups instead of a single part-of-speech template.
 */
internal object Paul1000ExampleFactory {
    fun create(entry: Paul1000Entry, ordinal: Int): Paul1000Example {
        val key = entry.word.lowercase()
        val meaning = normalizedMeanings[key] ?: entry.meaning
        val gloss = primaryGloss(meaning)
        special(key)?.let { (sentence, translation) ->
            return example(entry.word, gloss, sentence, translation, "日常口语")
        }
        return when {
            meaning.startsWith("n.") -> noun(entry.word, gloss, key, ordinal)
            meaning.startsWith("adj.") -> adjective(entry.word, gloss, key, ordinal)
            meaning.startsWith("vt.") -> transitiveVerb(entry.word, gloss, key, ordinal)
            meaning.startsWith("vi.") -> intransitiveVerb(entry.word, gloss, key, ordinal)
            meaning.startsWith("v.") || meaning.startsWith("vbl.") -> verb(entry.word, gloss, key, ordinal)
            meaning.startsWith("adv.") || meaning.startsWith("neg.") -> adverb(entry.word, gloss, key, ordinal)
            else -> error("Paul1000 word needs a contextual example: ${entry.word} (${entry.meaning})")
        }
    }

    fun normalizedMeaning(entry: Paul1000Entry): String =
        normalizedMeanings[entry.word.lowercase()] ?: entry.meaning

    private fun noun(word: String, gloss: String, key: String, ordinal: Int): Paul1000Example = when {
        key in people -> pick(
            ordinal,
            example(word, gloss, "The $word is waiting outside.", "${gloss}正在外面等候。", "人物"),
            example(word, gloss, "I spoke to the $word this morning.", "我今天早上和${gloss}谈过了。", "人物"),
            example(word, gloss, "Can the $word help us with this?", "${gloss}能帮我们处理这件事吗？", "人物"),
            example(word, gloss, "The $word will call you back later.", "${gloss}稍后会给你回电话。", "人物")
        )
        key in places -> pick(
            ordinal,
            example(word, gloss, "Is there a $word near here?", "这附近有${gloss}吗？", "地点"),
            example(word, gloss, "I'll meet you outside the $word.", "我会在${gloss}外面等你。", "地点"),
            example(word, gloss, "How far is the $word from here?", "${gloss}离这里有多远？", "地点"),
            example(word, gloss, "The $word closes at six today.", "${gloss}今天六点关门。", "地点")
        )
        key in timeNouns -> pick(
            ordinal,
            example(word, gloss, "Do you have time this $word?", "你这个${gloss}有时间吗？", "时间"),
            example(word, gloss, "We can talk about it next $word.", "我们可以下个${gloss}再谈。", "时间"),
            example(word, gloss, "It happened around the same $word.", "事情大约发生在同一个${gloss}。", "时间"),
            example(word, gloss, "That was the best part of my $word.", "那是我这个${gloss}中最开心的部分。", "时间")
        )
        key in bodyParts -> pick(
            ordinal,
            example(word, gloss, "My $word still hurts a little.", "我的${gloss}还有点疼。", "身体健康"),
            example(word, gloss, "I hurt my $word at work.", "我工作时伤到了${gloss}。", "身体健康"),
            example(word, gloss, "Keep your $word away from the moving part.", "让你的${gloss}远离运动部件。", "身体健康")
        )
        key in foodAndDrink -> pick(
            ordinal,
            example(word, gloss, "Would you like some $word?", "你想来点${gloss}吗？", "饮食"),
            example(word, gloss, "We need more $word for dinner.", "晚饭还需要一些${gloss}。", "饮食"),
            example(word, gloss, "This $word tastes really good.", "这个${gloss}味道很好。", "饮食")
        )
        key in abstractNouns -> pick(
            ordinal,
            example(word, gloss, "We need to talk about the $word.", "我们需要谈谈这个${gloss}。", "工作与生活"),
            example(word, gloss, "The $word is important to me.", "这个${gloss}对我很重要。", "工作与生活"),
            example(word, gloss, "I understand your $word.", "我理解你的${gloss}。", "工作与生活"),
            example(word, gloss, "That $word makes a big difference.", "那个${gloss}会带来很大影响。", "工作与生活"),
            example(word, gloss, "Can you explain the $word again?", "你能再解释一下这个${gloss}吗？", "工作与生活")
        )
        key in massNouns || isPlural(key) -> pick(
            ordinal,
            example(word, gloss, "Do we have enough $word?", "我们的${gloss}够吗？", "日常物品"),
            example(word, gloss, "Where should we put the $word?", "我们应该把${gloss}放在哪里？", "日常物品"),
            example(word, gloss, "The $word is ready to use.", "${gloss}已经可以使用了。", "日常物品"),
            example(word, gloss, "I checked the $word this morning.", "我今天早上检查了${gloss}。", "日常物品")
        )
        else -> {
            val article = articleFor(word)
            pick(
                ordinal,
                example(word, gloss, "Do we need $article $word for this?", "这件事需要一个${gloss}吗？", "日常物品"),
                example(word, gloss, "Where did you put the $word?", "你把${gloss}放在哪里了？", "日常物品"),
                example(word, gloss, "I found $article $word in the cupboard.", "我在柜子里找到了一个${gloss}。", "日常物品"),
                example(word, gloss, "Can you bring me the $word?", "你能把${gloss}拿给我吗？", "日常物品"),
                example(word, gloss, "This $word looks fine to me.", "这个${gloss}我看没问题。", "日常物品"),
                example(word, gloss, "I bought $article $word yesterday.", "我昨天买了一个${gloss}。", "日常物品"),
                example(word, gloss, "Is this the $word you wanted?", "这是你想要的${gloss}吗？", "日常物品"),
                example(word, gloss, "We may need another $word.", "我们可能还需要一个${gloss}。", "日常物品"),
                example(word, gloss, "The $word is on the table.", "${gloss}在桌上。", "日常物品"),
                example(word, gloss, "I can't find the $word.", "我找不到${gloss}了。", "日常物品"),
                example(word, gloss, "The $word is in the bag.", "${gloss}在包里。", "日常物品")
            )
        }
    }

    private fun adjective(word: String, gloss: String, key: String, ordinal: Int): Paul1000Example = when {
        key in colours -> pick(
            ordinal,
            example(word, gloss, "I like the $word one better.", "我更喜欢${gloss}的那个。", "颜色外观"),
            example(word, gloss, "Can you see the $word sign?", "你能看到那个${gloss}标志吗？", "颜色外观"),
            example(word, gloss, "She wore a $word jacket today.", "她今天穿了一件${gloss}夹克。", "颜色外观")
        )
        key in feelings -> pick(
            ordinal,
            example(word, gloss, "I feel $word today.", "我今天感觉很${gloss}。", "感受"),
            example(word, gloss, "You look $word. Are you okay?", "你看起来很${gloss}，还好吗？", "感受"),
            example(word, gloss, "It's normal to feel $word sometimes.", "有时感到${gloss}很正常。", "感受")
        )
        key in sizeAndCondition -> pick(
            ordinal,
            example(word, gloss, "This one is too $word for me.", "这个对我来说太${gloss}了。", "尺寸状态"),
            example(word, gloss, "Is the box $word enough?", "这个箱子够${gloss}吗？", "尺寸状态"),
            example(word, gloss, "We need something less $word.", "我们需要一个不那么${gloss}的东西。", "尺寸状态"),
            example(word, gloss, "The $word one is easier to carry.", "那个${gloss}的更容易携带。", "尺寸状态")
        )
        key in weatherAdjectives -> pick(
            ordinal,
            example(word, gloss, "It's quite $word outside today.", "今天外面很${gloss}。", "天气"),
            example(word, gloss, "The road gets dangerous when it's $word.", "天气${gloss}时道路会变危险。", "天气"),
            example(word, gloss, "Tomorrow should be less $word.", "明天应该没这么${gloss}。", "天气")
        )
        key in classifyingAdjectives -> pick(
            ordinal,
            example(word, gloss, "Is this a $word issue?", "这是一个${gloss}问题吗？", "工作与社会"),
            example(word, gloss, "We need $word advice on this.", "这件事我们需要${gloss}建议。", "工作与社会"),
            example(word, gloss, "The $word rules are quite clear.", "相关的${gloss}规定很明确。", "工作与社会")
        )
        else -> pick(
            ordinal,
            example(word, gloss, "That sounds $word to me.", "我觉得那听起来很${gloss}。", "描述判断"),
            example(word, gloss, "The situation is still $word.", "情况仍然很${gloss}。", "描述判断"),
            example(word, gloss, "Is it really that $word?", "真的有那么${gloss}吗？", "描述判断"),
            example(word, gloss, "It seemed $word at first.", "起初看起来很${gloss}。", "描述判断"),
            example(word, gloss, "This option looks more $word.", "这个选择看起来更${gloss}。", "描述判断"),
            example(word, gloss, "Why is it so $word?", "为什么它这么${gloss}？", "描述判断"),
            example(word, gloss, "I didn't expect it to be this $word.", "我没想到它会这么${gloss}。", "描述判断"),
            example(word, gloss, "Do you think that's $word?", "你觉得那是${gloss}的吗？", "描述判断"),
            example(word, gloss, "Everything looks $word now.", "现在一切看起来都很${gloss}。", "描述判断"),
            example(word, gloss, "The result was surprisingly $word.", "结果出乎意料地${gloss}。", "描述判断")
        )
    }

    private fun transitiveVerb(word: String, gloss: String, key: String, ordinal: Int): Paul1000Example = when {
        key in communicationVerbs -> pick(
            ordinal,
            example(word, gloss, "Can you $word me after work?", "你下班后能${gloss}我吗？", "沟通"),
            example(word, gloss, "Please $word the customer first.", "请先${gloss}顾客。", "沟通"),
            example(word, gloss, "I need to $word them today.", "我今天需要${gloss}他们。", "沟通")
        )
        key in movementVerbs -> pick(
            ordinal,
            example(word, gloss, "Can you $word this box?", "你能${gloss}这个箱子吗？", "动作"),
            example(word, gloss, "Please $word it over here.", "请把它${gloss}到这里。", "动作"),
            example(word, gloss, "We need to $word the table first.", "我们需要先${gloss}这张桌子。", "动作")
        )
        key in thinkingVerbs -> pick(
            ordinal,
            example(word, gloss, "I don't $word that at all.", "我完全不${gloss}那件事。", "想法"),
            example(word, gloss, "Do you $word what happened?", "你${gloss}发生了什么吗？", "想法"),
            example(word, gloss, "Please $word this for a moment.", "请${gloss}一下这件事。", "想法")
        )
        else -> pick(
            ordinal,
            example(word, gloss, "We need to $word this today.", "我们今天需要${gloss}这件事。", "常用动作"),
            example(word, gloss, "Can you $word it for me?", "你能帮我${gloss}它吗？", "常用动作"),
            example(word, gloss, "Could you $word this one first?", "你能先${gloss}这个吗？", "常用动作"),
            example(word, gloss, "I can $word that after lunch.", "我可以午饭后${gloss}那件事。", "常用动作"),
            example(word, gloss, "Let's $word this before lunch.", "我们午饭前${gloss}这件事吧。", "常用动作"),
            example(word, gloss, "They asked us to $word it.", "他们让我们${gloss}它。", "常用动作"),
            example(word, gloss, "I need help to $word this.", "我需要别人帮忙${gloss}这件事。", "常用动作"),
            example(word, gloss, "Could we $word that tomorrow?", "我们明天能${gloss}那件事吗？", "常用动作")
        )
    }

    private fun intransitiveVerb(word: String, gloss: String, key: String, ordinal: Int): Paul1000Example = when {
        key in movementIntransitive -> pick(
            ordinal,
            example(word, gloss, "We can $word after lunch.", "我们可以午饭后${gloss}。", "动作"),
            example(word, gloss, "I usually $word around six.", "我通常六点左右${gloss}。", "动作"),
            example(word, gloss, "Please don't $word yet.", "请先不要${gloss}。", "动作")
        )
        key in eventVerbs -> pick(
            ordinal,
            example(word, gloss, "It could $word again tomorrow.", "明天可能会再次${gloss}。", "变化"),
            example(word, gloss, "Things can $word very quickly.", "事情可能很快${gloss}。", "变化"),
            example(word, gloss, "What made it $word?", "是什么让它${gloss}的？", "变化")
        )
        else -> pick(
            ordinal,
            example(word, gloss, "I hope we can $word soon.", "我希望我们很快能${gloss}。", "常用动作"),
            example(word, gloss, "It may $word later today.", "今天晚些时候它可能会${gloss}。", "常用动作"),
            example(word, gloss, "They often $word at the same time.", "他们经常同时${gloss}。", "常用动作")
        )
    }

    private fun verb(word: String, gloss: String, key: String, ordinal: Int): Paul1000Example = when (key) {
        "has" -> example(word, gloss, "She has a meeting at ten.", "她十点有个会议。", "基础语法")
        else -> pick(
            ordinal,
            example(word, gloss, "I usually $word after work.", "我通常下班后${gloss}。", "常用动作"),
            example(word, gloss, "Can we $word now?", "我们现在可以${gloss}吗？", "常用动作"),
            example(word, gloss, "They $word together every week.", "他们每周一起${gloss}。", "常用动作")
        )
    }

    private fun adverb(word: String, gloss: String, key: String, ordinal: Int): Paul1000Example = when {
        key in frequencyAdverbs -> pick(
            ordinal,
            example(word, gloss, "I $word take the bus to work.", "我${gloss}坐公交车上班。", "频率"),
            example(word, gloss, "We $word eat lunch together.", "我们${gloss}一起吃午饭。", "频率"),
            example(word, gloss, "She $word calls after work.", "她${gloss}下班后打电话。", "频率")
        )
        key in timeAdverbs -> pick(
            ordinal,
            example(word, gloss, "I'll call you $word.", "我会${gloss}给你打电话。", "时间"),
            example(word, gloss, "We can finish it $word.", "我们可以${gloss}完成。", "时间"),
            example(word, gloss, "Are you free $word?", "你${gloss}有空吗？", "时间")
        )
        key in placeAdverbs -> pick(
            ordinal,
            example(word, gloss, "I left my bag $word.", "我把包落在${gloss}了。", "地点方向"),
            example(word, gloss, "Can we meet $word?", "我们能在${gloss}见面吗？", "地点方向"),
            example(word, gloss, "There's a small café $word.", "${gloss}有一家小咖啡馆。", "地点方向")
        )
        else -> pick(
            ordinal,
            example(word, gloss, "Please check it $word.", "请${gloss}检查一下。", "表达方式"),
            example(word, gloss, "She explained it $word.", "她${gloss}解释了这件事。", "表达方式"),
            example(word, gloss, "We finished the job $word.", "我们${gloss}完成了工作。", "表达方式")
        )
    }

    private fun special(key: String): Pair<String, String>? = when (key) {
        "a" -> "I need a minute." to "我需要一分钟。"
        "an" -> "Can I have an apple?" to "我可以吃一个苹果吗？"
        "the" -> "The bus is here." to "公交车到了。"
        "i" -> "I work nearby." to "我在附近工作。"
        "you" -> "Are you ready?" to "你准备好了吗？"
        "he" -> "He starts work at eight." to "他八点开始工作。"
        "she" -> "She lives near the station." to "她住在车站附近。"
        "we" -> "We can go together." to "我们可以一起去。"
        "they" -> "They are waiting outside." to "他们正在外面等。"
        "me" -> "Please call me later." to "请稍后给我打电话。"
        "him" -> "I saw him this morning." to "我今天早上见到他了。"
        "her" -> "Can you help her?" to "你能帮帮她吗？"
        "us" -> "Could you show us the way?" to "你能给我们指路吗？"
        "them" -> "I'll speak to them tomorrow." to "我明天会和他们谈。"
        "it" -> "It looks fine to me." to "我觉得它看起来没问题。"
        "this" -> "Is this seat free?" to "这个座位有人吗？"
        "that" -> "That sounds like a good idea." to "那听起来是个好主意。"
        "these" -> "Are these yours?" to "这些是你的吗？"
        "my" -> "My phone is in my bag." to "我的手机在包里。"
        "your" -> "What's your name?" to "你叫什么名字？"
        "his" -> "His car is outside." to "他的车在外面。"
        "its" -> "The dog hurt its leg." to "那只狗伤到了腿。"
        "our" -> "Our train leaves at six." to "我们的火车六点出发。"
        "their" -> "Their office is upstairs." to "他们的办公室在楼上。"
        "myself" -> "I fixed it myself." to "我自己把它修好了。"
        "yourself" -> "Please take care of yourself." to "请照顾好自己。"
        "himself" -> "He made it himself." to "那是他自己做的。"
        "herself" -> "She introduced herself first." to "她先作了自我介绍。"
        "ourselves" -> "We did it ourselves." to "这是我们自己做的。"
        "themselves" -> "They paid for it themselves." to "这是他们自己付的钱。"
        "itself" -> "The door closed by itself." to "门自己关上了。"
        "who" -> "Who are you waiting for?" to "你在等谁？"
        "whom" -> "Whom should I ask?" to "我应该问谁？"
        "whose" -> "Whose keys are these?" to "这些是谁的钥匙？"
        "what" -> "What do you mean?" to "你是什么意思？"
        "which" -> "Which one do you prefer?" to "你更喜欢哪一个？"
        "anyone" -> "Does anyone need help?" to "有人需要帮助吗？"
        "everyone" -> "Is everyone ready?" to "大家都准备好了吗？"
        "nobody" -> "Nobody answered the phone." to "没有人接电话。"
        "anything" -> "Do you need anything else?" to "你还需要别的吗？"
        "everything" -> "Everything is ready now." to "现在一切都准备好了。"
        "nothing" -> "There's nothing in the box." to "箱子里什么也没有。"
        "something" -> "I need to tell you something." to "我有件事要告诉你。"
        "at" -> "Meet me at the station." to "在车站和我见面。"
        "in" -> "The keys are in my bag." to "钥匙在我的包里。"
        "on" -> "Your phone is on the table." to "你的手机在桌上。"
        "by" -> "Please finish it by Friday." to "请在星期五前完成。"
        "for" -> "This seat is for you." to "这个座位是给你的。"
        "from" -> "I'm from China." to "我来自中国。"
        "to" -> "I'm going to work." to "我要去上班。"
        "with" -> "Would you like tea with milk?" to "你想喝加牛奶的茶吗？"
        "without" -> "Don't leave without your coat." to "别没带外套就走。"
        "before" -> "Call me before you leave." to "你离开前给我打电话。"
        "behind" -> "The car park is behind the building." to "停车场在楼后面。"
        "beside" -> "Sit beside me." to "坐在我旁边。"
        "between" -> "The café is between the bank and the hotel." to "咖啡馆在银行和酒店之间。"
        "under" -> "Your bag is under the chair." to "你的包在椅子下面。"
        "above" -> "The clock is above the door." to "时钟在门的上方。"
        "across" -> "The shop is across the road." to "商店在马路对面。"
        "through" -> "Walk through the main gate." to "从正门走进去。"
        "into" -> "Put the milk into the fridge." to "把牛奶放进冰箱。"
        "off" -> "Please turn the light off." to "请把灯关掉。"
        "about" -> "Can we talk about this later?" to "我们能晚点谈这件事吗？"
        "against" -> "Don't lean against the door." to "不要靠在门上。"
        "during" -> "Please keep quiet during the meeting." to "会议期间请保持安静。"
        "despite" -> "We went out despite the rain." to "尽管下雨，我们还是出门了。"
        "within" -> "I'll reply within two days." to "我会在两天内回复。"
        "among" -> "This café is popular among students." to "这家咖啡馆很受学生欢迎。"
        "beyond" -> "The bus stop is just beyond the bridge." to "公交站就在桥的另一边。"
        "of" -> "Would you like a cup of tea?" to "你想喝杯茶吗？"
        "per" -> "The room costs eighty dollars per night." to "这个房间每晚八十美元。"
        "toward" -> "She walked toward the station." to "她朝车站走去。"
        "and" -> "I bought bread and milk." to "我买了面包和牛奶。"
        "but" -> "I'm tired, but I'm okay." to "我很累，但我没事。"
        "or" -> "Would you like tea or coffee?" to "你想喝茶还是咖啡？"
        "because" -> "I left early because I felt sick." to "我因为不舒服提前离开了。"
        "if" -> "Call me if you need help." to "如果需要帮助就给我打电话。"
        "when" -> "Text me when you arrive." to "你到了以后给我发消息。"
        "while" -> "I'll cook while you set the table." to "你摆桌子时我来做饭。"
        "until" -> "I'll wait here until six." to "我会在这里等到六点。"
        "unless" -> "Don't go unless I call you." to "除非我给你打电话，否则别去。"
        "although" -> "Although it was late, we kept working." to "虽然很晚了，我们仍继续工作。"
        "whether" -> "I don't know whether he's coming." to "我不知道他会不会来。"
        "as" -> "Call me as soon as you arrive." to "你一到就给我打电话。"
        "neither" -> "Neither option works for me." to "两个选项我都不合适。"
        "since" -> "I've lived here since 2024." to "我从2024年起就住在这里。"
        "than" -> "This route is faster than the other one." to "这条路线比另一条快。"
        "whenever" -> "Call me whenever you need help." to "无论何时需要帮助都可以给我打电话。"
        "could" -> "Could you say that again?" to "你能再说一遍吗？"
        "would" -> "Would you like some water?" to "你想喝点水吗？"
        "should" -> "You should get some rest." to "你应该休息一下。"
        "must" -> "You must wear a seat belt." to "你必须系安全带。"
        "may" -> "It may rain later." to "晚些时候可能会下雨。"
        "had" -> "We had lunch together." to "我们一起吃了午饭。"
        "shall" -> "Shall we leave now?" to "我们现在走好吗？"
        "eight" -> "The shop opens at eight." to "商店八点开门。"
        "four" -> "We need four chairs." to "我们需要四把椅子。"
        "seven" -> "I'll meet you at seven." to "我七点和你见面。"
        "six" -> "The last bus leaves at six." to "末班公交车六点出发。"
        "ten" -> "It only takes ten minutes." to "只需要十分钟。"
        "third" -> "My room is on the third floor." to "我的房间在三楼。"
        "are" -> "Are you free this afternoon?" to "你今天下午有空吗？"
        "be" -> "Please be careful." to "请小心。"
        "is" -> "Is the shop still open?" to "商店还开着吗？"
        "was" -> "It was busy this morning." to "今天早上很忙。"
        "will" -> "I will call you tomorrow." to "我明天会给你打电话。"
        "affect" -> "Will the delay affect your plans?" to "这次延误会影响你的计划吗？"
        "choose" -> "Please choose the blue one." to "请选择蓝色的那个。"
        "correct" -> "Could you correct this mistake?" to "你能改正这个错误吗？"
        "create" -> "We can create a new account." to "我们可以创建一个新账户。"
        "debate" -> "We should debate this issue first." to "我们应该先讨论这个问题。"
        "design" -> "She helped design this room." to "她参与设计了这个房间。"
        "establish" -> "We need to establish the facts." to "我们需要查明事实。"
        "except" -> "Everyone came except John." to "除了约翰，大家都来了。"
        "find" -> "I can't find my keys." to "我找不到钥匙了。"
        "give" -> "Please give me a minute." to "请给我一分钟。"
        "hurt" -> "Did you hurt your back?" to "你伤到背了吗？"
        "invest" -> "I'd like to invest some money." to "我想投资一些钱。"
        "keep" -> "You can keep the change." to "零钱不用找了。"
        "look" -> "Look at this photo." to "看看这张照片。"
        "manage" -> "Can you manage the shop alone today?" to "你今天能独自管理这家店吗？"
        "meet" -> "Let's meet after work." to "我们下班后见吧。"
        "offer" -> "Can I offer you a drink?" to "我可以请你喝点东西吗？"
        "own" -> "Do you own this house?" to "这套房子是你的吗？"
        "process" -> "We'll process your application today." to "我们今天会处理你的申请。"
        "protect" -> "This cover will protect your phone." to "这个保护套会保护你的手机。"
        "receive" -> "Did you receive my message?" to "你收到我的消息了吗？"
        "reduce" -> "We need to reduce the cost." to "我们需要降低成本。"
        "relate" -> "I can relate to that." to "我能理解那种感受。"
        "require" -> "This job will require some training." to "这份工作需要一些培训。"
        "sample" -> "You can sample the soup first." to "你可以先尝尝汤。"
        "serve" -> "Do they serve breakfast here?" to "这里供应早餐吗？"
        "show" -> "Can you show me the way?" to "你能给我指路吗？"
        "solve" -> "We can solve this together." to "我们可以一起解决这件事。"
        "spend" -> "I spend an hour studying every night." to "我每晚花一小时学习。"
        "try" -> "Can I try this on?" to "我可以试穿一下吗？"
        "want" -> "I want a cup of tea." to "我想要一杯茶。"
        "yes" -> "Yes, that works for me." to "好的，我没问题。"
        "no" -> "No, I haven't finished yet." to "没有，我还没做完。"
        "many" -> "How many people are coming?" to "有多少人要来？"
        "none" -> "None of these keys fit the door." to "这些钥匙一把也打不开这扇门。"
        "one" -> "I'll take the blue one." to "我要蓝色的那个。"
        "hello" -> "Hello, how can I help?" to "你好，我能帮你什么？"
        "bye" -> "Bye, see you tomorrow." to "再见，明天见。"
        "oh" -> "Oh, I left my keys at home." to "哦，我把钥匙落在家里了。"
        "why" -> "Why are you in such a hurry?" to "你为什么这么着急？"
        "okay" -> "Okay, I'll do it now." to "好的，我现在就做。"
        "actually" -> "Actually, I'm free this afternoon." to "其实我今天下午有空。"
        "after" -> "We went for coffee after work." to "下班后我们去喝了咖啡。"
        "afterward" -> "We had dinner and went home afterward." to "我们吃过晚饭，之后就回家了。"
        "again" -> "Could you try again?" to "你能再试一次吗？"
        "ago" -> "I moved here two years ago." to "我两年前搬到了这里。"
        "almost" -> "I'm almost ready." to "我快准备好了。"
        "along" -> "Can I bring a friend along?" to "我能带个朋友一起去吗？"
        "already" -> "I've already paid for it." to "我已经付过钱了。"
        "also" -> "She also works on Saturdays." to "她星期六也上班。"
        "always" -> "I always check the door before bed." to "我睡前总会检查门。"
        "anymore" -> "I don't use that phone anymore." to "我已经不用那部手机了。"
        "anywhere" -> "You can sit anywhere." to "你坐哪里都可以。"
        "apart" -> "The two shops are five minutes apart." to "两家商店相隔五分钟路程。"
        "around" -> "I'll be there around six." to "我大约六点到那里。"
        "anyway" -> "Anyway, we need to leave now." to "总之，我们现在得走了。"
        "away" -> "The station is only ten minutes away." to "车站离这里仅十分钟。"
        "below" -> "Please write your name below." to "请在下面写下你的名字。"
        "besides" -> "Besides, we don't have enough time." to "而且，我们的时间也不够。"
        "certainly" -> "I can certainly help with that." to "那件事我当然可以帮忙。"
        "clearly" -> "Please speak clearly and slowly." to "请说得清楚、慢一点。"
        "completely" -> "I completely forgot about the meeting." to "我把会议忘得一干二净。"
        "currently" -> "The lift is currently out of service." to "电梯目前暂停使用。"
        "differently" -> "I see the problem differently." to "我对这个问题有不同看法。"
        "directly" -> "Please send the file directly to me." to "请把文件直接发给我。"
        "down" -> "Please sit down." to "请坐下。"
        "easily" -> "You can easily walk there from here." to "你从这里很容易就能走到那里。"
        "else" -> "Would you like anything else?" to "你还想要别的吗？"
        "enough" -> "Do we have enough time?" to "我们的时间够吗？"
        "especially" -> "It's busy here, especially on Fridays." to "这里很忙，尤其是星期五。"
        "eventually" -> "We eventually found the right address." to "我们最终找到了正确地址。"
        "everywhere" -> "I've looked everywhere for my keys." to "我到处都找过钥匙了。"
        "ever" -> "Have you ever been to Australia?" to "你去过澳大利亚吗？"
        "exactly" -> "That's exactly what I need." to "那正是我需要的。"
        "extra" -> "Could I get an extra towel?" to "可以再给我一条毛巾吗？"
        "finally" -> "The bus finally arrived." to "公交车终于到了。"
        "first" -> "Let me check the address first." to "让我先核对一下地址。"
        "furthermore" -> "The room is small; furthermore, it's noisy." to "房间很小，而且还很吵。"
        "here" -> "You can wait here." to "你可以在这里等。"
        "highly" -> "This restaurant is highly recommended." to "这家餐厅非常值得推荐。"
        "honestly" -> "Honestly, I don't know the answer." to "说实话，我不知道答案。"
        "how" -> "How did you get here?" to "你是怎么到这里的？"
        "however" -> "It's cheap; however, it's too far away." to "它很便宜，不过离得太远。"
        "immediately" -> "Please call me immediately." to "请立刻给我打电话。"
        "instead" -> "Let's walk instead." to "我们改成走路吧。"
        "just" -> "I just got home." to "我刚到家。"
        "later" -> "I'll explain it later." to "我稍后解释。"
        "largely" -> "The delay was largely caused by traffic." to "延误主要是交通造成的。"
        "less" -> "I have less time this week." to "我这周时间更少。"
        "mainly" -> "I use this app mainly for work." to "我主要在工作中使用这个应用。"
        "maybe" -> "Maybe we can meet tomorrow." to "也许我们明天可以见面。"
        "meanwhile" -> "Meanwhile, I'll make some coffee." to "与此同时，我去煮点咖啡。"
        "more" -> "Could I have some more water?" to "可以再给我一点水吗？"
        "most" -> "This is the most useful option." to "这是最实用的选项。"
        "mostly" -> "The café is mostly empty in the morning." to "这家咖啡馆早上大多是空的。"
        "much" -> "How much does it cost?" to "这个多少钱？"
        "nearly" -> "We're nearly there." to "我们快到了。"
        "nevertheless" -> "It was raining; nevertheless, we went out." to "虽然下雨，我们还是出门了。"
        "never" -> "I never drive after drinking." to "我喝酒后绝不开车。"
        "next" -> "What should we do next?" to "我们接下来该做什么？"
        "not" -> "I'm not ready yet." to "我还没准备好。"
        "now" -> "We need to leave now." to "我们现在得走了。"
        "nowhere" -> "There's nowhere to park here." to "这里没有地方停车。"
        "often" -> "I often walk to work." to "我经常步行上班。"
        "once" -> "I've only been there once." to "我只去过那里一次。"
        "only" -> "I only need five minutes." to "我只需要五分钟。"
        "otherwise" -> "Leave now; otherwise, you'll miss the bus." to "现在就走，不然你会错过公交车。"
        "out" -> "Would you like to eat out tonight?" to "你今晚想出去吃饭吗？"
        "over" -> "Come over when you finish work." to "你下班后过来吧。"
        "perhaps" -> "Perhaps we should ask for help." to "也许我们应该寻求帮助。"
        "probably" -> "I'll probably be late." to "我可能会迟到。"
        "quickly" -> "Please come quickly." to "请快点过来。"
        "quite" -> "The room is quite small." to "这个房间相当小。"
        "rather" -> "I'd rather stay home tonight." to "今晚我宁愿待在家里。"
        "really" -> "Do you really need it today?" to "你今天真的需要它吗？"
        "recently" -> "I moved here recently." to "我最近搬到了这里。"
        "relatively" -> "The test was relatively easy." to "这次测试相对简单。"
        "seriously" -> "Are you seriously thinking of leaving?" to "你真的在考虑离开吗？"
        "simply" -> "Simply press this button to start." to "只要按这个按钮就能开始。"
        "so" -> "I'm tired, so I'm going home." to "我累了，所以要回家了。"
        "somehow" -> "We'll find a way somehow." to "我们总会想出办法的。"
        "sometimes" -> "I sometimes work on weekends." to "我有时周末上班。"
        "somewhere" -> "Let's meet somewhere quiet." to "我们找个安静的地方见面吧。"
        "soon" -> "I hope you feel better soon." to "希望你很快好起来。"
        "still" -> "Are you still at work?" to "你还在上班吗？"
        "suddenly" -> "The lights suddenly went out." to "灯突然灭了。"
        "then" -> "Finish this, then take a break." to "做完这个，然后休息一下。"
        "there" -> "I'll meet you there." to "我会在那里和你见面。"
        "therefore" -> "The road is closed; therefore, we need another route." to "道路封闭了，所以我们得换条路。"
        "though" -> "It's expensive, though." to "不过，它很贵。"
        "thus" -> "The shop was closed, thus we went home." to "商店关门了，因此我们回家了。"
        "today" -> "Are you working today?" to "你今天上班吗？"
        "together" -> "Let's go together." to "我们一起去吧。"
        "too" -> "This bag is too heavy." to "这个包太重了。"
        "up" -> "Please stand up." to "请站起来。"
        "usually" -> "I usually start work at eight." to "我通常八点开始工作。"
        "where" -> "Where did you park the car?" to "你把车停在哪里了？"
        "wherever" -> "Sit wherever you like." to "你喜欢坐哪里就坐哪里。"
        "well" -> "I don't feel well today." to "我今天感觉不太舒服。"
        "widely" -> "This app is widely used at work." to "这个应用在工作中被广泛使用。"
        "wrong" -> "Sorry, I called the wrong number." to "抱歉，我打错电话了。"
        "yet" -> "Have you eaten yet?" to "你吃过了吗？"
        else -> null
    }

    private fun example(
        word: String,
        gloss: String,
        sentence: String,
        translation: String,
        category: String
    ) = Paul1000Example(
        sentence = sentence,
        translation = translation,
        chunks = "$word~$gloss^$sentence~$translation",
        note = "$word 在这里表示“$gloss”。",
        category = category
    )

    private fun pick(ordinal: Int, vararg choices: Paul1000Example): Paul1000Example =
        choices[Math.floorMod(ordinal, choices.size)]

    private fun primaryGloss(meaning: String): String = meaning
        .substringAfter('.', meaning)
        .substringBefore('；')
        .substringBefore('，')
        .trim()
        .ifBlank { meaning }

    private fun articleFor(word: String): String =
        if (word.firstOrNull()?.lowercaseChar()?.let { it in "aeiou" } == true) "an" else "a"

    private fun isPlural(word: String): Boolean =
        word in knownPlurals || (word.endsWith('s') && word !in singularEndingInS)

    private val normalizedMeanings = mapOf(
        "are" to "v. 是；在（用于 you、we、they）",
        "be" to "v. 是；成为；存在",
        "has" to "v. 有（have 的第三人称单数）",
        "is" to "v. 是；在（用于 he、she、it）",
        "man" to "n. 男人；人",
        "no" to "adv. 不；没有",
        "on" to "prep. 在……上；处于……状态",
        "to" to "prep. 向；到；用于动词不定式",
        "was" to "v. 是；在（am、is 的过去式）",
        "will" to "aux. 将；会；愿意",
        "wrong" to "adj. 错误的；不合适的"
    )

    private val people = setOf(
        "adult", "baby", "boy", "brother", "child", "customer", "daughter", "doctor",
        "employee", "father", "friend", "girl", "guest", "husband", "lady", "manager",
        "man", "member", "mother", "neighbor", "parent", "partner", "patient", "person",
        "police", "president", "sister", "son", "staff", "student", "teacher", "team",
        "visitor", "wife", "woman", "worker"
    )
    private val places = setOf(
        "area", "bank", "building", "capital", "center", "church", "city", "club", "country",
        "court", "department", "field", "floor", "home", "hospital", "hotel", "house", "kitchen",
        "market", "office", "park", "place", "restaurant", "road", "room", "school", "shop",
        "station", "street", "town", "village"
    )
    private val timeNouns = setOf(
        "afternoon", "age", "century", "date", "day", "evening", "hour", "minute", "moment",
        "month", "morning", "night", "period", "season", "second", "time", "week", "weekend", "year"
    )
    private val bodyParts = setOf(
        "arm", "back", "blood", "body", "brain", "ear", "eye", "face", "finger", "foot",
        "hair", "hand", "head", "heart", "leg", "mouth", "neck", "shoulder", "skin", "tooth"
    )
    private val foodAndDrink = setOf(
        "beer", "bread", "breakfast", "coffee", "dinner", "drink", "fish", "food", "fruit",
        "lunch", "meal", "meat", "milk", "rice", "tea", "water", "wine"
    )
    private val abstractNouns = setOf(
        "ability", "action", "advice", "agreement", "attention", "behavior", "benefit", "business",
        "care", "case", "cause", "chance", "change", "choice", "condition", "control", "cost",
        "culture", "damage", "decision", "difference", "direction", "education", "effect", "effort",
        "energy", "event", "evidence", "experience", "fact", "faith", "fear", "feeling", "force",
        "freedom", "future", "goal", "growth", "health", "help", "history", "hope", "idea",
        "importance", "income", "industry", "information", "interest", "job", "knowledge", "language",
        "law", "level", "life", "love", "meaning", "method", "movement", "nature", "need", "news",
        "opinion", "option", "order", "permission", "plan", "policy", "position", "power", "problem",
        "process", "program", "progress", "purpose", "quality", "question", "reason", "relationship",
        "research", "result", "risk", "rule", "safety", "service", "situation", "skill", "society",
        "strength", "success", "support", "system", "technology", "theory", "thought", "training",
        "truth", "value", "view", "work"
    )
    private val massNouns = setOf(
        "air", "clothing", "data", "equipment", "furniture", "glass", "gold", "grass", "ice",
        "light", "money", "music", "paper", "rain", "snow", "space", "traffic", "weather", "wood"
    )
    private val knownPlurals = setOf("children", "clothes", "goods", "people", "things")
    private val singularEndingInS = setOf("business", "class", "glass", "news", "process", "series")
    private val colours = setOf("black", "blue", "brown", "green", "red", "white", "yellow")
    private val feelings = setOf(
        "afraid", "alone", "angry", "comfortable", "confident", "glad", "happy", "nervous",
        "ready", "sad", "serious", "sorry", "surprised", "tired", "worried"
    )
    private val sizeAndCondition = setOf(
        "big", "clean", "deep", "dry", "empty", "flat", "full", "heavy", "high", "large", "light",
        "little", "long", "low", "narrow", "new", "old", "open", "rough", "short", "small", "soft",
        "strong", "thick", "thin", "tight", "wide", "young"
    )
    private val weatherAdjectives = setOf("bright", "cold", "cool", "dark", "hot", "warm", "wet")
    private val classifyingAdjectives = setOf(
        "central", "cultural", "economic", "educational", "environmental", "financial", "foreign",
        "global", "international", "legal", "local", "medical", "military", "national", "natural",
        "physical", "political", "private", "professional", "public", "religious", "social", "technical"
    )
    private val communicationVerbs = setOf(
        "advise", "answer", "ask", "call", "contact", "invite", "mention", "question", "remind", "tell", "warn"
    )
    private val movementVerbs = setOf(
        "bring", "carry", "catch", "draw", "drive", "drop", "hold", "lift", "move", "pass", "pull",
        "push", "put", "raise", "send", "throw", "transfer", "turn"
    )
    private val thinkingVerbs = setOf(
        "accept", "believe", "consider", "expect", "forget", "imagine", "know", "notice", "realize",
        "recognize", "remember", "suppose", "trust", "understand"
    )
    private val movementIntransitive = setOf(
        "arrive", "come", "dance", "fall", "fly", "go", "jump", "leave", "run", "sit", "sleep",
        "stand", "stay", "swim", "travel", "wait", "walk", "work"
    )
    private val eventVerbs = setOf(
        "appear", "begin", "change", "continue", "develop", "die", "end", "exist", "grow", "happen",
        "increase", "occur", "remain", "rise", "start", "stop"
    )
    private val frequencyAdverbs = setOf("always", "never", "often", "sometimes", "usually")
    private val timeAdverbs = setOf(
        "afterward", "ago", "currently", "eventually", "immediately", "later", "next", "now",
        "recently", "soon", "today", "yesterday"
    )
    private val placeAdverbs = setOf(
        "anywhere", "around", "away", "below", "down", "everywhere", "here", "nowhere", "out",
        "over", "somewhere", "there", "up"
    )
}
