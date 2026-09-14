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
 * Every word has an explicit editorial example. Missing entries fail validation.
 */
internal object Paul1000ExampleFactory {
    fun create(entry: Paul1000Entry, ordinal: Int): Paul1000Example {
        val key = entry.word.lowercase()
        ReviewedExamples.find(key)?.let {
            return example(entry.word, primaryGloss(normalizedMeaning(entry)), it.sentence, it.translation, "日常口语")
        }
        val meaning = normalizedMeaning(entry)
        val gloss = primaryGloss(meaning)
        special(key)?.let { (sentence, translation) ->
            return example(entry.word, gloss, sentence, translation, "日常口语")
        }
        Nce1ExampleFactory.literalExample(key)?.let {
            return example(entry.word, gloss, it.sentence, it.translation, "日常口语")
        }
        error("Missing reviewed example: ${entry.word}")
    }

    fun normalizedMeaning(entry: Paul1000Entry): String =
        ReviewedMeanings.forWord(entry.word, normalizedMeanings[entry.word.lowercase()] ?: entry.meaning)

    internal fun literalExample(key: String): Pair<String, String>? = special(key)

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
        note = "先理解整句，再点击单词查看它在句中的用法。",
        category = category
    )

    private fun primaryGloss(meaning: String): String = meaning
        .substringAfter('.', meaning)
        .substringBefore('；')
        .substringBefore('，')
        .trim()
        .ifBlank { meaning }

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

}
