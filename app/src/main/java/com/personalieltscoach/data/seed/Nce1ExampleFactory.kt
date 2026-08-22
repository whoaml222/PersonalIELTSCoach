package com.personalieltscoach.data.seed

internal data class Nce1Example(
    val sentence: String,
    val translation: String
)

/**
 * Original, offline examples for NCE1 vocabulary that is not already covered by
 * the fragment-sentence pack. The NCE lesson list is used for word order,
 * meanings and lesson context only; textbook sentences are not copied here.
 */
internal object Nce1ExampleFactory {
    fun create(word: String, meaning: String, lesson: String, ordinal: Int): Nce1Example {
        val key = word.lowercase()
        specialCases[key]?.let { return it }

        val gloss = primaryGloss(meaning)
        return when {
            key in countries -> country(word, gloss, ordinal)
            key in nationalities -> nationality(word, gloss, ordinal)
            key in vehicleBrands -> vehicleBrand(word, gloss, ordinal)
            meaning.startsWith("n.") -> noun(word, gloss, key, lesson, ordinal)
            meaning.startsWith("adj.") -> adjective(word, gloss, key, ordinal)
            meaning.startsWith("v.") || meaning.contains("；v.") -> verb(word, gloss, ordinal)
            meaning.startsWith("adv.") -> adverb(word, gloss, ordinal)
            else -> general(word, gloss, lesson, ordinal)
        }
    }

    private fun example(sentence: String, translation: String) =
        Nce1Example(sentence = sentence, translation = translation)

    private fun primaryGloss(meaning: String): String = meaning
        .substringAfter(". ", meaning)
        .substringBefore('；')
        .substringBefore('，')
        .trim()
        .ifBlank { meaning }

    private val specialCases = mapOf(
        "excuse" to example("Please excuse me for arriving a few minutes late.", "请原谅我迟到了几分钟。"),
        "mr." to example("Mr. Brown is waiting for you at reception.", "布朗先生正在前台等你。"),
        "mrs." to example("Mrs. Green called about the afternoon appointment.", "格林夫人打电话询问下午的预约。"),
        "sir" to example("Excuse me, sir, is this seat free?", "打扰一下，先生，这个座位有人吗？"),
        "suit" to example("This dark suit is suitable for the interview.", "这套深色西装适合面试。"),
        "miss" to example("Leave now, or you may miss the last train.", "现在就走，否则你可能错过末班火车。"),
        "customs" to example("Customs checked our passports at the airport.", "海关在机场检查了我们的护照。"),
        "scotch" to example("He ordered a Scotch with ice after dinner.", "他晚饭后点了一杯加冰的苏格兰威士忌。"),
        "same" to example("We work on the same shift every Friday.", "我们每个星期五上同一个班次。"),
        "daily" to example("A daily safety check takes about ten minutes.", "每日安全检查大约需要十分钟。"),
        "favourite" to example("This is my favourite café near the station.", "这是车站附近我最喜欢的咖啡馆。"),
        "favorite" to example("Blue is her favorite color.", "蓝色是她最喜欢的颜色。"),
        "choice" to example("The butcher showed us a choice piece of beef.", "肉店老板给我们看了一块上等牛肉。"),
        "pure" to example("The tap provides pure drinking water.", "这个水龙头提供纯净饮用水。"),
        "rich" to example("The sauce is too rich for me.", "这种酱汁对我来说太油腻了。"),
        "extra" to example("I took an extra shirt for the overnight trip.", "我为过夜旅行多带了一件衬衫。"),
        "fail" to example("I was worried that I might fail the driving test.", "我担心自己可能无法通过驾驶考试。"),
        "salt" to example("Could you pass me the salt, please?", "请把盐递给我好吗？"),
        "less" to example("We have less time than we expected.", "我们的时间比预想的少。"),
        "most" to example("Most buses stop near the town centre.", "大多数公交车都在市中心附近停。"),
        "least" to example("This route takes the least time in the morning.", "这条路线早上花费的时间最少。"),
        "worse" to example("The traffic is worse on Friday afternoons.", "星期五下午的交通更糟。"),
        "worst" to example("Monday morning is the worst time to drive there.", "星期一早上是开车去那里的最糟时间。"),
        "invite" to example("I'd like to invite my neighbours to dinner.", "我想邀请邻居来吃晚饭。"),
        "latest" to example("Have you seen the latest train timetable?", "你看过最新的火车时刻表吗？"),
        "yes" to example("Yes, I can start work at eight tomorrow.", "是的，我明天八点可以开始工作。"),
        "pardon" to example("Pardon, could you say the address again?", "不好意思，你能再说一遍地址吗？"),
        "very much" to example("I enjoyed the short trip very much.", "我非常喜欢这次短途旅行。"),
        "hello" to example("Hello, I'm calling about the room for rent.", "你好，我打电话是想问出租房间的事。"),
        "hi" to example("Hi, are you free for a quick coffee?", "嗨，你有空快速喝杯咖啡吗？"),
        "thanks" to example("Thanks, that makes the instructions much clearer.", "谢谢，这样说明就清楚多了。"),
        "goodbye" to example("Goodbye, and have a safe journey home.", "再见，祝你回家一路平安。"),
        "whose" to example("Whose jacket is hanging by the door?", "门边挂着的是谁的夹克？"),
        "perhaps" to example("Perhaps we can finish the repair after lunch.", "也许我们午饭后可以完成维修。"),
        "catch" to example("I leave early so I can catch the first bus.", "我早点出门，以便赶上第一班公交车。"),
        "his" to example("His toolbox is under the workbench.", "他的工具箱在工作台下面。"),
        "her" to example("Her new job starts next Monday.", "她的新工作下周一开始。"),
        "upstairs" to example("The manager is upstairs in the meeting room.", "经理在楼上的会议室里。"),
        "downstairs" to example("I'll wait downstairs near the front door.", "我会在楼下前门附近等。"),
        "sit down" to example("Please sit down while I check your details.", "我核对你的资料时请坐下。"),
        "close" to example("Could you close the window before you leave?", "你离开前能把窗户关上吗？"),
        "sweep" to example("We sweep the workshop floor at the end of each shift.", "我们每班结束时都会清扫车间地面。"),
        "sharpen" to example("I need to sharpen this pencil before the lesson.", "上课前我需要把这支铅笔削尖。"),
        "take off" to example("Please take off your wet coat at the door.", "请在门口脱下湿外套。"),
        "turn on" to example("Can you turn on the light above the workbench?", "你能打开工作台上方的灯吗？"),
        "climb" to example("Never climb that ladder without checking it first.", "没有先检查梯子，千万不要往上爬。"),
        "run" to example("I run for twenty minutes before breakfast.", "我早餐前跑二十分钟。"),
        "type" to example("Please type your name exactly as it appears on your passport.", "请按照护照上的写法准确输入姓名。"),
        "fly" to example("We fly to Auckland on Friday evening.", "我们星期五晚上飞往奥克兰。"),
        "shine" to example("The sun began to shine after the morning rain.", "早晨的雨后，太阳开始照耀。"),
        "walk" to example("I usually walk to the station after work.", "我下班后通常步行去车站。"),
        "hike" to example("We plan to hike in the hills this weekend.", "我们计划这个周末去山里徒步。"),
        "sleep" to example("I sleep better when the room is cool and quiet.", "房间凉爽安静时，我睡得更好。"),
        "shave" to example("He likes to shave before taking a shower.", "他喜欢在洗澡前刮脸。"),
        "cry" to example("The baby began to cry when the door slammed.", "门砰地关上时，婴儿开始哭。"),
        "jump" to example("Don't jump from the back of the truck.", "不要从卡车后面跳下来。"),
        "other" to example("The other key is in the top drawer.", "另一把钥匙在最上面的抽屉里。"),
        "along" to example("Walk along this road until you see the bank.", "沿着这条路走，直到看到银行。"),
        "belong" to example("These safety glasses belong to the new technician.", "这副护目镜属于新来的技术员。"),
        "swim" to example("The children swim at the local pool on Saturdays.", "孩子们星期六在当地泳池游泳。"),
        "build" to example("They plan to build more homes near the station.", "他们计划在车站附近建更多住房。"),
        "hard" to example("We worked hard to finish the job before dark.", "我们努力工作，争取天黑前完成任务。"),
        "paint" to example("We need to paint the wall after fixing the crack.", "修好裂缝后，我们需要粉刷墙面。"),
        "listen" to example("Listen carefully when the supervisor explains the risk.", "主管说明风险时要仔细听。"),
        "in front of" to example("The taxi is waiting in front of the hotel.", "出租车正在酒店前面等候。"),
        "drop" to example("Be careful not to drop the glass bottle.", "小心别把玻璃瓶掉在地上。"),
        "post" to example("Could you post this letter on your way home?", "你回家路上能帮我寄出这封信吗？"),
        "certainly" to example("I can certainly check that for you.", "我当然可以替你核对。"),
        "of course" to example("Of course you can use my phone.", "你当然可以用我的手机。"),
        "boil" to example("Let the water boil before you add the pasta.", "水烧开后再放意大利面。"),
        "look for" to example("I'll look for the missing receipt after lunch.", "我午饭后会寻找那张遗失的收据。"),
        "answer" to example("Could someone answer the phone at reception?", "能有人接一下前台的电话吗？"),
        "either" to example("Either route will take you to the city centre.", "两条路线中的任何一条都能带你到市中心。"),
        "neither" to example("Neither key will open this door.", "两把钥匙都打不开这扇门。"),
        "rain" to example("It may rain during the afternoon shift.", "下午班期间可能会下雨。"),
        "sometimes" to example("I sometimes study English on the train.", "我有时在火车上学英语。"),
        "snow" to example("It rarely begins to snow this early in the year.", "一年中这么早就开始下雪很少见。"),
        "best" to example("This is the best route during the morning rush.", "这是早高峰期间最好的路线。"),
        "rise" to example("Fuel prices often rise before a long holiday.", "长假前燃油价格经常上涨。"),
        "late" to example("The train was twenty minutes late this morning.", "今天早上火车晚点了二十分钟。"),
        "love" to example("I love having a quiet breakfast before work.", "我喜欢上班前安静地吃早餐。"),
        "lie" to example("You should lie down if you still feel dizzy.", "如果你仍然头晕，就应该躺下。"),
        "usually" to example("We usually check the equipment on Monday mornings.", "我们通常在星期一早上检查设备。"),
        "depart" to example("The coach will depart from platform six at noon.", "长途汽车将在中午从六号站台出发。"),
        "remember" to example("Remember to lock the back door tonight.", "今晚记得锁后门。"),
        "get up" to example("I get up at six when I have an early shift.", "上早班时我六点起床。"),
        "talk" to example("Can we talk about the schedule after the meeting?", "会议后我们能谈谈日程安排吗？"),
        "drive" to example("I don't drive when the roads are icy.", "路面结冰时我不开车。"),
        "so" to example("The box was so heavy that we needed a trolley.", "箱子太重了，我们需要一辆手推车。"),
        "lean out of" to example("Never lean out of a moving train window.", "千万不要把身体探出行驶中的火车窗外。"),
        "enjoy" to example("I enjoy practising English with my colleagues.", "我喜欢和同事练习英语。"),
        "yourself" to example("Give yourself enough time to catch the bus.", "给自己留出足够时间赶公交车。"),
        "ourselves" to example("We repaired the loose shelf ourselves.", "我们自己修好了松动的架子。"),
        "myself" to example("I made the booking myself this time.", "这次是我自己订的。"),
        "themselves" to example("The children packed their bags themselves.", "孩子们自己收拾了行李。"),
        "himself" to example("He introduced himself to the new team.", "他向新团队作了自我介绍。"),
        "herself" to example("She taught herself how to use the new tool.", "她自学了如何使用这个新工具。"),
        "spend" to example("I spend an hour on English after dinner.", "我晚饭后花一小时学英语。"),
        "stand" to example("Please stand behind the yellow safety line.", "请站在黄色安全线后面。"),
        "awfully" to example("I'm awfully sorry about the broken cup.", "我对打破杯子的事非常抱歉。"),
        "suddenly" to example("The lights suddenly went out during the storm.", "暴风雨期间灯突然灭了。"),
        "smile" to example("She gave me a smile as I entered the office.", "我走进办公室时，她对我笑了笑。"),
        "pleasantly" to example("The receptionist greeted us pleasantly.", "接待员愉快地向我们问候。"),
        "hurriedly" to example("He left the station hurriedly when his phone rang.", "电话响起时，他匆忙离开了车站。"),
        "thirstily" to example("The hikers drank thirstily after the long climb.", "徒步者长途攀登后口渴地喝着水。"),
        "greet" to example("I always greet the security guard on my way in.", "我进门时总会向保安打招呼。"),
        "ago" to example("I moved to this neighbourhood two months ago.", "我两个月前搬到这个街区。"),
        "buy" to example("I need to buy a warmer coat before winter.", "冬天前我需要买一件更暖和的外套。"),
        "till" to example("The café stays open till ten on Fridays.", "这家咖啡馆星期五营业到十点。"),
        "hope" to example("I hope the weather stays dry tomorrow.", "我希望明天天气保持干燥。"),
        "nearly" to example("It's nearly six, so the shop may be closed.", "快六点了，所以商店可能关门了。"),
        "pack" to example("Please pack the glasses in a separate box.", "请把玻璃杯装进单独的箱子。"),
        "pack up" to example("We pack up our tools at the end of the day.", "我们每天结束时收拾工具。"),
        "already" to example("I've already sent the address to your phone.", "我已经把地址发到你手机上了。"),
        "ever" to example("Have you ever worked an overnight shift?", "你上过通宵班吗？"),
        "believe" to example("I believe this bus stops near the airport.", "我认为这辆公交车在机场附近停。"),
        "how long" to example("How long does the journey to Wellington take?", "去惠灵顿的旅程需要多长时间？"),
        "since" to example("I've lived here since last September.", "我从去年九月起就住在这里。"),
        "sell" to example("They sell work boots on the ground floor.", "他们在一楼出售工作靴。"),
        "retire" to example("My supervisor plans to retire next year.", "我的主管计划明年退休。"),
        "worth" to example("This second-hand bike is worth the price.", "这辆二手自行车值这个价钱。"),
        "subway" to example("We took the subway because the roads were busy.", "因为路上很堵，我们乘了地铁。"),
        "describe" to example("Can you describe the person who took your bag?", "你能描述一下拿走你包的人吗？"),
        "ow" to example("Ow! I caught my finger in the door.", "哎哟！我的手指被门夹住了。"),
        "oops" to example("Oops, I sent the message to the wrong number.", "啊呀，我把信息发错号码了。"),
        "slip" to example("You could slip on the wet kitchen floor.", "你可能会在湿滑的厨房地板上滑倒。"),
        "fall" to example("Loose tools can fall from this shelf.", "松动的工具可能会从这个架子上掉下来。"),
        "hurt" to example("Does your shoulder still hurt when you lift the box?", "你抬箱子时肩膀还疼吗？"),
        "stand up" to example("Stand up slowly if you feel dizzy.", "如果感到头晕，请慢慢站起来。"),
        "at once" to example("Report any gas smell at once.", "闻到任何煤气味都要立即报告。"),
        "enough" to example("We have enough time for one more practice round.", "我们有足够时间再练习一轮。"),
        "respond" to example("Please respond to the email before Friday.", "请在星期五前回复这封邮件。"),
        "hate" to example("I hate waiting in traffic after a long shift.", "我讨厌长时间上班后还要堵在路上。"),
        "cheer" to example("A hot drink may cheer you up after the cold walk.", "冷天步行后，一杯热饮也许能让你振作起来。"),
        "spell" to example("Could you spell your family name for me?", "你能为我拼一下你的姓吗？"),
        "carry" to example("Use both hands to carry this heavy box.", "用双手搬这个重箱子。"),
        "as well" to example("Bring your passport and your booking email as well.", "带上护照，也要带上预订邮件。"),
        "a little" to example("Add a little milk to my coffee, please.", "请在我的咖啡里加一点牛奶。"),
        "instead" to example("The bus was full, so we walked instead.", "公交车满了，所以我们改为步行。"),
        "afford" to example("I can't afford a new car this year.", "我今年买不起新车。"),
        "none" to example("I checked three rooms, but none was available.", "我看了三个房间，但一个空房都没有。"),
        "get off" to example("Get off the bus at the next stop.", "在下一站下公交车。"),
        "get on" to example("We get on the train at platform four.", "我们在四号站台上火车。"),
        "except" to example("The office is open every day except Sunday.", "办公室除星期日外每天开放。"),
        "anyone" to example("Did anyone see where I left my keys?", "有人看见我把钥匙放在哪里了吗？"),
        "knock" to example("Please knock before entering the manager's office.", "进入经理办公室前请敲门。"),
        "everything" to example("Everything is ready for tomorrow's inspection.", "明天检查所需的一切都准备好了。"),
        "nothing" to example("There was nothing wrong with the second machine.", "第二台机器没有任何问题。"),
        "joke" to example("We often joke together during our lunch break.", "我们午休时经常一起开玩笑。"),
        "everybody" to example("Everybody must wear a helmet in this area.", "这个区域内每个人都必须戴安全帽。"),
        "nobody" to example("Nobody answered when I called the office.", "我打电话到办公室时没人接。"),
        "somebody" to example("Somebody left a wet umbrella by the door.", "有人把一把湿雨伞留在门边。"),
        "anybody" to example("Can anybody help me move this table?", "有人能帮我搬这张桌子吗？"),
        "something" to example("Something smells strange near the air conditioner.", "空调附近有东西闻起来很奇怪。"),
        "everywhere" to example("We looked everywhere for the missing document.", "我们到处寻找那份丢失的文件。"),
        "nowhere" to example("There was nowhere safe to park the van.", "那里没有安全的地方可以停货车。"),
        "somewhere" to example("Let's meet somewhere quiet after work.", "我们下班后找个安静的地方见面吧。"),
        "anywhere" to example("You can sit anywhere in this waiting area.", "你可以坐在这个等候区的任何位置。"),
        "swallow" to example("Take a drink of water if the tablet is hard to swallow.", "如果药片难以下咽，就喝一口水。"),
        "later" to example("I'll call you later when I know the result.", "知道结果后我晚些时候给你打电话。"),
        "ring" to example("The alarm will ring at six tomorrow morning.", "闹钟明天早上六点会响。"),
        "happen" to example("Accidents can happen when people rush.", "人们匆忙时可能会发生事故。"),
        "exercise" to example("I exercise for half an hour after work.", "我下班后锻炼半小时。"),
        "forget" to example("Don't forget to charge your phone tonight.", "今晚别忘了给手机充电。"),
        "serve" to example("This café will serve breakfast from seven.", "这家咖啡馆从七点开始供应早餐。"),
        "recognize" to example("I didn't recognize him in his new uniform.", "他穿着新制服，我没有认出他。"),
        "travel" to example("I travel by train when the weather is bad.", "天气不好时我乘火车出行。"),
        "offer" to example("The company may offer you some extra training.", "公司可能会为你提供额外培训。"),
        "guess" to example("Can you guess how much the repair will cost?", "你能猜出维修要花多少钱吗？"),
        "grow" to example("These plants grow well beside the kitchen window.", "这些植物在厨房窗边长得很好。"),
        "terribly" to example("I'm terribly sorry that I missed your call.", "非常抱歉我没接到你的电话。"),
        "at least" to example("Please arrive at least ten minutes early.", "请至少提前十分钟到达。"),
        "wave" to example("Wave to the driver so she knows where to stop.", "向司机招手，让她知道在哪里停车。"),
        "overtake" to example("Do not overtake on this narrow road.", "不要在这条狭窄的道路上超车。"),
        "dream" to example("I often dream about travelling around New Zealand.", "我经常梦想环游新西兰。"),
        "abroad" to example("She worked abroad for two years before coming home.", "她回国前在国外工作了两年。"),
        "worry" to example("Don't worry; the next bus arrives in ten minutes.", "别担心，下一班公交车十分钟后到。"),
        "wed" to example("They plan to wed in a small ceremony next spring.", "他们计划明年春天举行一个小型婚礼。"),
        "introduce" to example("Let me introduce you to our new supervisor.", "让我把你介绍给我们的新主管。"),
        "marry" to example("They hope to marry after saving enough money.", "他们希望攒够钱后结婚。"),
        "win" to example("Our local team could win the final tonight.", "我们当地的球队今晚可能赢得决赛。"),
        "curiously" to example("The child looked curiously at the old machine.", "孩子好奇地看着那台旧机器。"),
        "kindly" to example("The neighbour kindly offered to drive us home.", "邻居好心地提出开车送我们回家。"),
        "regularly" to example("Check the tyre pressure regularly.", "要定期检查轮胎气压。"),
        "surround" to example("Tall trees surround the small house.", "高大的树木环绕着这座小房子。"),
        "place" to example("Place the parcel on the desk by the window.", "把包裹放在窗边的桌子上。"),
        "throw" to example("Please don't throw batteries in the general waste bin.", "请不要把电池扔进普通垃圾桶。"),
        "count" to example("Count the boxes before the driver leaves.", "司机离开前数一下箱子。"),
        "among" to example("I found my keys among the papers on the desk.", "我在桌上的文件中找到了钥匙。"),
        "prosecute" to example("The police may prosecute drivers who ignore the limit.", "警方可能会依法处置无视限速的司机。")
    )

    private fun country(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        11,
        example("A colleague of mine moved to $word last year.", "我的一位同事去年搬到了${gloss}。"),
        example("Have you ever travelled to $word in winter?", "你冬天去过${gloss}吗？"),
        example("Her flight to $word leaves on Saturday.", "她去${gloss}的航班星期六出发。"),
        example("We met a family from $word at the hotel.", "我们在酒店遇到了来自${gloss}的一家人。"),
        example("He is applying for a job in $word.", "他正在申请一份在${gloss}的工作。"),
        example("$word is on my list of places to visit.", "${gloss}在我想去的地方清单上。"),
        example("The parcel arrived from $word this morning.", "包裹今天早上从${gloss}寄到了。"),
        example("She spent two weeks in $word during the summer.", "她夏天在${gloss}待了两个星期。")
    )

    private fun nationality(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        17,
        example("Our new neighbour is $word.", "我们的新邻居是${gloss}。"),
        example("I work with a $word technician on the evening shift.", "我上晚班时和一位${gloss}技术员一起工作。"),
        example("She met a $word family while travelling.", "她旅行时遇到了一个${gloss}家庭。"),
        example("The $word visitor asked for directions to the station.", "那位${gloss}游客询问去车站的路。"),
        example("A $word colleague taught me this recipe.", "一位${gloss}同事教了我这个食谱。"),
        example("Is the new engineer $word?", "新来的工程师是${gloss}吗？")
    )

    private fun vehicleBrand(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        23,
        example("We saw a used $word at the local garage.", "我们在当地车行看到了一辆二手${gloss}。"),
        example("Is that blue car a $word?", "那辆蓝色汽车是${gloss}吗？"),
        example("My neighbour drives an old $word to work.", "我的邻居开一辆旧${gloss}上班。"),
        example("The mechanic is checking a $word this afternoon.", "机械师今天下午正在检查一辆${gloss}。"),
        example("A new $word was parked outside the hotel.", "酒店外停着一辆新的${gloss}。")
    )

    private fun noun(
        word: String,
        gloss: String,
        key: String,
        lesson: String,
        ordinal: Int
    ): Nce1Example = when {
        key in months -> month(word, gloss, ordinal)
        key in weekdays -> weekday(word, gloss, ordinal)
        key in cities -> city(word, gloss, ordinal)
        key in landmarks -> landmark(word, gloss, ordinal)
        isPerson(gloss) -> person(word, gloss, ordinal)
        key in healthNouns -> health(word, gloss, ordinal)
        key in animals -> animal(word, gloss, ordinal)
        key in countFoods -> countFood(word, gloss, ordinal)
        key in foods -> food(word, gloss, ordinal)
        key in places -> placeNoun(word, gloss, ordinal)
        key in timeNouns -> timeNoun(word, gloss, ordinal)
        key in abstractNouns -> abstractNoun(word, gloss, ordinal)
        isPlural(word, meaningGloss = gloss) -> pluralNoun(word, gloss, ordinal)
        else -> objectNoun(word, gloss, lesson, ordinal)
    }

    private fun month(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        29,
        example("My training course begins in $word.", "我的培训课程在${gloss}开始。"),
        example("The weather usually changes in $word.", "天气通常在${gloss}发生变化。"),
        example("We plan to move house in $word.", "我们计划在${gloss}搬家。"),
        example("Her visa expires at the end of $word.", "她的签证在${gloss}底到期。"),
        example("I have a medical appointment in $word.", "我在${gloss}有一个医疗预约。")
    )

    private fun weekday(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        31,
        example("The workshop is closed on $word.", "车间在${gloss}关闭。"),
        example("Can we meet after work on $word?", "我们能在${gloss}下班后见面吗？"),
        example("My early shift starts on $word.", "我的早班从${gloss}开始。"),
        example("The next delivery arrives on $word morning.", "下一批货在${gloss}早上到达。"),
        example("I usually practise speaking on $word evenings.", "我通常在${gloss}晚上练习口语。")
    )

    private fun city(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        37,
        example("The train to $word leaves from platform three.", "去${gloss}的火车从三号站台出发。"),
        example("She found a short-term job in $word.", "她在${gloss}找到了一份短期工作。"),
        example("Have you booked a hotel in $word yet?", "你订好${gloss}的酒店了吗？"),
        example("Our flight arrived in $word before noon.", "我们的航班中午前抵达了${gloss}。"),
        example("A friend showed us around $word last weekend.", "一位朋友上周末带我们游览了${gloss}。"),
        example("How long does it take to get to $word?", "去${gloss}需要多长时间？")
    )

    private fun landmark(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        41,
        example("We took a photo beside $word.", "我们在${gloss}旁边拍了照片。"),
        example("Our guide told us the history of $word.", "导游向我们讲了${gloss}的历史。"),
        example("You can see $word from the hill.", "从山上可以看到${gloss}。")
    )

    private fun person(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        43,
        example("The $word helped me find the correct platform.", "${gloss}帮我找到了正确的站台。"),
        example("I spoke to the $word at reception.", "我在前台和${gloss}谈过了。"),
        example("Our $word arrived before the meeting began.", "我们的${gloss}在会议开始前到了。"),
        example("The $word showed us how to use the equipment safely.", "${gloss}向我们演示了如何安全使用设备。"),
        example("A $word called the office this morning.", "一位${gloss}今天早上打电话到办公室。"),
        example("Could you ask the $word to wait outside?", "你能请${gloss}在外面等吗？"),
        example("The $word gave me some useful advice.", "${gloss}给了我一些有用的建议。"),
        example("I met the $word during my first week at work.", "我上班第一周遇到了${gloss}。"),
        example("The $word is speaking with a customer now.", "${gloss}现在正在和一位顾客交谈。"),
        example("We thanked the $word before leaving.", "我们离开前向${gloss}道了谢。")
    )

    private fun health(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        47,
        example("I've had a $word since this morning.", "我从今天早上起就有${gloss}。"),
        example("The nurse asked me about my $word.", "护士询问了我的${gloss}。"),
        example("This medicine should help with the $word.", "这种药应该能缓解${gloss}。"),
        example("Tell the doctor if the $word gets worse.", "如果${gloss}变严重，请告诉医生。"),
        example("I stayed home because of the $word.", "我因为${gloss}留在家里。")
    )

    private fun animal(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        53,
        example("The $word waited quietly beside the gate.", "${gloss}安静地等在大门旁。"),
        example("We saw a $word near the road this morning.", "我们今天早上在路边看到了一只${gloss}。"),
        example("The children gave the $word some water.", "孩子们给了${gloss}一些水。"),
        example("A $word was sleeping under the tree.", "一只${gloss}正在树下睡觉。"),
        example("Please keep the $word away from the kitchen.", "请不要让${gloss}靠近厨房。")
    )

    private fun countFood(word: String, gloss: String, ordinal: Int): Nce1Example {
        val firstLetter = word.firstOrNull()?.lowercaseChar()
        val article = if (firstLetter != null && firstLetter in "aeiou") "an" else "a"
        return pick(
            ordinal,
            59,
            example("I packed $article $word for my lunch break.", "我为午休带了一个${gloss}。"),
            example("Would you like $article $word with your breakfast?", "你早餐想吃一个${gloss}吗？"),
            example("She bought $article $word at the market.", "她在市场买了一个${gloss}。"),
            example("There is $article $word in the fridge.", "冰箱里有一个${gloss}。"),
            example("He cut the $word into small pieces.", "他把${gloss}切成了小块。")
        )
    }

    private fun food(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        61,
        example("We bought fresh $word at the market.", "我们在市场买了新鲜的${gloss}。"),
        example("Would you like some $word with lunch?", "你午饭想吃些${gloss}吗？"),
        example("There isn't much $word left in the kitchen.", "厨房里剩下的${gloss}不多了。"),
        example("This $word tastes better when it is warm.", "这种${gloss}热着吃味道更好。"),
        example("Please put the $word in the fridge.", "请把${gloss}放进冰箱。"),
        example("I usually have $word after work.", "我通常下班后吃${gloss}。"),
        example("The café serves $word until two o'clock.", "这家咖啡馆供应${gloss}到两点。")
    )

    private fun placeNoun(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        67,
        example("The $word is just around the corner from here.", "${gloss}就在离这里不远的拐角处。"),
        example("I'll meet you outside the $word at six.", "我六点在${gloss}外面见你。"),
        example("Is there a $word near the bus station?", "公交车站附近有${gloss}吗？"),
        example("We stopped at the $word on our way home.", "我们回家路上在${gloss}停了一下。"),
        example("The new $word opens early on weekdays.", "新的${gloss}工作日很早开门。"),
        example("She works in the $word across the road.", "她在马路对面的${gloss}工作。"),
        example("How far is the $word from your flat?", "${gloss}离你的公寓有多远？"),
        example("Turn left when you reach the $word.", "到${gloss}时向左转。")
    )

    private fun timeNoun(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        71,
        example("I don't have much $word before my next shift.", "下一班工作前，我没有多少${gloss}。"),
        example("We agreed on a $word for the next meeting.", "我们为下次会议约定了${gloss}。"),
        example("The $word passed quickly while we were busy.", "我们忙碌时，${gloss}过得很快。"),
        example("Please write the $word on the form.", "请把${gloss}写在表格上。"),
        example("That $word works well for everyone.", "那个${gloss}对每个人都合适。")
    )

    private fun abstractNoun(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        73,
        example("The manager mentioned $word during the morning briefing.", "经理在早会上提到了${gloss}。"),
        example("I asked a colleague about $word before lunch.", "我午饭前向同事询问了${gloss}。"),
        example("We need more information about $word.", "我们需要更多关于${gloss}的信息。"),
        example("The course gave me a better understanding of $word.", "这门课程让我更好地理解了${gloss}。"),
        example("There was a short note about $word on the noticeboard.", "公告栏上有一则关于${gloss}的简短通知。"),
        example("Could we discuss $word after the meeting?", "我们能在会后讨论${gloss}吗？"),
        example("Her advice about $word was very practical.", "她关于${gloss}的建议很实用。"),
        example("$word became important when we planned the job.", "我们规划工作时，${gloss}变得很重要。"),
        example("I wrote down the main point about $word.", "我记下了关于${gloss}的要点。"),
        example("The customer had a question about $word.", "顾客有一个关于${gloss}的问题。")
    )

    private fun pluralNoun(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        79,
        example("The $word are ready for tomorrow's delivery.", "${gloss}已经为明天的配送准备好了。"),
        example("Could you put the $word beside the back door?", "你能把${gloss}放在后门旁边吗？"),
        example("We counted the $word before closing the box.", "封箱前我们数了${gloss}。"),
        example("The $word arrived earlier than expected.", "${gloss}比预计更早到达。"),
        example("I checked the $word for damage this morning.", "我今天早上检查了${gloss}是否损坏。"),
        example("Where should we store the $word overnight?", "我们晚上应该把${gloss}存放在哪里？")
    )

    private fun objectNoun(
        word: String,
        gloss: String,
        lesson: String,
        ordinal: Int
    ): Nce1Example {
        val context = practicalContexts[Math.floorMod(lesson.hashCode() + ordinal, practicalContexts.size)]
        return pick(
            ordinal,
            83,
            example("I left the $word beside the $context.", "我把${gloss}放在了${contextTranslations.getValue(context)}旁边。"),
            example("Could you bring the $word to the $context before the customer arrives?", "顾客到来前你能把${gloss}带到${contextTranslations.getValue(context)}吗？"),
            example("We checked the $word beside the $context at the start of the shift.", "我们在开班时检查了${contextTranslations.getValue(context)}旁的${gloss}。"),
            example("Do you know where the $word near the $context is?", "你知道${contextTranslations.getValue(context)}附近的${gloss}在哪里吗？"),
            example("The $word is still near the $context.", "${gloss}仍然在${contextTranslations.getValue(context)}附近。"),
            example("Please keep the $word somewhere dry, away from the $context.", "请把${gloss}放在干燥且远离${contextTranslations.getValue(context)}的地方。"),
            example("I found the $word in the cupboard beside the $context.", "我在${contextTranslations.getValue(context)}旁的橱柜里找到了${gloss}。"),
            example("Someone moved the $word away from the $context during lunch.", "有人在午休期间把${gloss}从${contextTranslations.getValue(context)}旁移走了。"),
            example("The $word beside the $context was easier to use than I expected.", "${contextTranslations.getValue(context)}旁的${gloss}比我预想的更容易使用。"),
            example("Can you show me how this $word at the $context works?", "你能向我展示${contextTranslations.getValue(context)}处的这个${gloss}怎么用吗？"),
            example("We need the $word from the $context for tomorrow's job.", "我们明天的工作需要${contextTranslations.getValue(context)}处的${gloss}。"),
            example("I put a label on the $word near the $context this morning.", "我今天早上在${contextTranslations.getValue(context)}附近的${gloss}上贴了标签。")
        )
    }

    private fun adjective(word: String, gloss: String, key: String, ordinal: Int): Nce1Example = when {
        key in colours -> colourAdjective(word, gloss, ordinal)
        key in weatherAdjectives -> weatherAdjective(word, gloss, ordinal)
        key in personalAdjectives -> personalAdjective(word, gloss, ordinal)
        key in foodAdjectives -> foodAdjective(word, gloss, ordinal)
        key in sizeAndShapeAdjectives -> sizeAdjective(word, gloss, ordinal)
        else -> qualityAdjective(word, gloss, ordinal)
    }

    private fun colourAdjective(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        89,
        example("She chose a $word jacket for the trip.", "她为旅行选了一件${gloss}夹克。"),
        example("The $word label marks the correct cable.", "${gloss}标签标出了正确的电缆。"),
        example("I left my $word bag on the back seat.", "我把${gloss}包落在后座上了。"),
        example("Can you see the $word sign near the gate?", "你能看到大门附近的${gloss}标志吗？"),
        example("The room has a $word door and white walls.", "这个房间有一扇${gloss}门和白色墙壁。")
    )

    private fun weatherAdjective(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        97,
        example("It was $word when I left for work.", "我出门上班时天气${gloss}。"),
        example("The afternoon may stay $word near the coast.", "海岸附近下午可能保持${gloss}。"),
        example("Wear a jacket because it feels $word outside.", "穿件夹克吧，因为外面感觉${gloss}。"),
        example("The road can be dangerous in $word weather.", "${gloss}天气下道路可能很危险。"),
        example("Tomorrow should be less $word than today.", "明天应该没有今天这么${gloss}。")
    )

    private fun personalAdjective(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        101,
        example("Our new colleague is $word and easy to work with.", "我们的新同事很${gloss}，也很好相处。"),
        example("I felt $word before my first job interview.", "第一次求职面试前我感到${gloss}。"),
        example("She looked $word after the long journey.", "长途旅行后她看起来很${gloss}。"),
        example("The children were $word when the bus arrived.", "公交车到达时孩子们很${gloss}。"),
        example("He sounded $word when he called this morning.", "他今天早上打电话时听起来很${gloss}。"),
        example("It's normal to feel $word in a new workplace.", "在新的工作场所感到${gloss}是正常的。")
    )

    private fun foodAdjective(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        102,
        example("The soup tastes $word today.", "今天的汤尝起来很${gloss}。"),
        example("This fruit is too $word to eat now.", "这种水果现在太${gloss}，不适合吃。"),
        example("The bread became $word after two days.", "面包两天后变得${gloss}。"),
        example("Would you like something less $word?", "你想吃点不那么${gloss}的东西吗？"),
        example("The market sells $word fruit in the morning.", "市场早上出售${gloss}的水果。")
    )

    private fun sizeAdjective(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        103,
        example("We need a $word box for the spare parts.", "我们需要一个${gloss}箱子装备件。"),
        example("Is this shelf $word enough for the toolbox?", "这个架子够${gloss}，能放工具箱吗？"),
        example("The $word table will fit beside the window.", "这张${gloss}桌子能放在窗边。"),
        example("They moved the $word cabinet with a trolley.", "他们用手推车移动了${gloss}柜子。"),
        example("Choose the $word one if you are travelling alone.", "如果你独自旅行，请选择${gloss}的那个。")
    )

    private fun qualityAdjective(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        107,
        example("The room felt $word after we opened the window.", "我们打开窗户后，房间感觉很${gloss}。"),
        example("This option looks $word for a short trip.", "这个选择看起来适合短途旅行，而且很${gloss}。"),
        example("The customer said the service was $word.", "顾客说这项服务很${gloss}。"),
        example("The old machine is still $word after the repair.", "这台旧机器修理后仍然很${gloss}。"),
        example("It became $word once the sun went down.", "太阳下山后，情况变得很${gloss}。"),
        example("We found a $word way to finish the task.", "我们找到了一种${gloss}方法来完成任务。"),
        example("The instructions were $word at first.", "这些说明一开始很${gloss}。"),
        example("Is the water $word enough to use now?", "这水现在够${gloss}，可以使用了吗？"),
        example("The new chair looks $word but feels comfortable.", "这把新椅子看起来${gloss}，但坐着很舒服。"),
        example("The situation was less $word than we expected.", "情况没有我们预想的那么${gloss}。")
    )

    private fun verb(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        109,
        example("Please $word carefully before the next step.", "进行下一步前，请仔细${gloss}。"),
        example("I need to $word before the afternoon shift.", "下午班前我需要${gloss}。"),
        example("Can you $word while I check the instructions?", "我检查说明时，你能${gloss}吗？"),
        example("We usually $word together after lunch.", "我们通常午饭后一起${gloss}。"),
        example("The supervisor showed me how to $word safely.", "主管向我演示了如何安全地${gloss}。")
    )

    private fun adverb(word: String, gloss: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        113,
        example("She explained the next step $word.", "她${gloss}说明了下一步。"),
        example("The driver answered $word and opened the door.", "司机${gloss}回答并打开了门。"),
        example("We finished the safety check $word.", "我们${gloss}完成了安全检查。"),
        example("He looked at the notice $word before leaving.", "他离开前${gloss}看了看通知。")
    )

    private fun general(word: String, gloss: String, lesson: String, ordinal: Int): Nce1Example = pick(
        ordinal,
        lesson.hashCode(),
        example("I heard $word during a real conversation at work.", "我在工作中的真实对话里听到了${gloss}。"),
        example("The teacher used $word in a short, practical example.", "老师在一个简短实用的例子中使用了${gloss}。"),
        example("A colleague used $word while explaining the job.", "一位同事在说明工作时使用了${gloss}。")
    )

    private fun isPerson(gloss: String): Boolean = personMarkers.any(gloss::contains)

    private fun isPlural(word: String, meaningGloss: String): Boolean =
        meaningGloss.contains("复数") ||
            word.lowercase() in knownPluralNouns ||
            (word.endsWith('s', ignoreCase = true) && word.lowercase() !in singularEndingInS)

    private fun pick(ordinal: Int, salt: Int, vararg choices: Nce1Example): Nce1Example =
        choices[Math.floorMod(ordinal * 31 + salt, choices.size)]

    private val countries = setOf(
        "sweden", "england", "america", "italy", "denmark", "russia", "holland",
        "greece", "brazil", "france", "germany", "norway", "spain", "australia",
        "austria", "canada", "china", "finland", "india", "japan", "nigeria",
        "turkey", "korea", "poland", "thailand", "scotland", "egypt", "bahrain"
    )

    private val nationalities = setOf(
        "french", "german", "japanese", "korean", "chinese", "english", "american",
        "swedish", "italian", "danish", "norwegian", "russian", "dutch", "australian",
        "austrian", "canadian", "finnish", "indian", "nigerian", "turkish", "polish",
        "thai", "egyptian"
    )

    private val vehicleBrands = setOf(
        "volvo", "peugeot", "mercedes", "toyota", "daewoo", "mini", "ford", "fiat"
    )

    private val months = setOf(
        "january", "february", "march", "april", "may", "june", "july", "august",
        "september", "october", "november", "december"
    )

    private val weekdays = setOf(
        "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"
    )

    private val cities = setOf(
        "london", "paris", "new york", "tokyo", "madrid", "athens", "berlin", "bombay",
        "geneva", "moscow", "rome", "seoul", "stockholm", "sydney"
    )

    private val landmarks = setOf("the great wall")

    private val personMarkers = listOf(
        "人", "人员", "员", "师", "先生", "小姐", "夫人", "丈夫", "妻子", "父亲", "母亲",
        "儿子", "女儿", "男孩", "女孩", "朋友", "客人", "顾客", "孩子", "婴儿", "邻居",
        "老板", "主任", "经理", "乘客", "青年", "警察", "官员", "助手", "秘书", "国王", "王后"
    )

    private val healthNouns = setOf(
        "headache", "earache", "toothache", "stomach ache", "temperature", "flu", "measles",
        "mumps", "cold", "medicine", "pill", "aspirin", "pain", "health"
    )

    private val animals = setOf(
        "dog", "puppy", "cat", "bird", "chicken", "hen", "cock", "turkey", "duck",
        "goose", "goat", "sheep", "lamb", "cow", "bull", "horse", "monkey", "snake",
        "tiger", "lion", "elephant", "camel", "rabbit", "fish"
    )

    private val countFoods = setOf(
        "apple", "orange", "banana", "pear", "grape", "peach", "tomato", "potato", "egg",
        "biscuit", "sandwich", "bean", "pea"
    )

    private val foods = setOf(
        "steak", "lamb", "mutton", "chicken", "beef", "mince", "bread", "milk", "coffee",
        "tea", "beer", "wine", "whisky", "water", "cheese", "butter", "honey", "jam",
        "cake", "soup", "food", "meal", "breakfast", "lunch", "dinner", "ice cream", "fish",
        "roast beef", "vegetable", "fruit", "meat", "salt", "sugar", "flour", "rice",
        "cabbage", "lettuce"
    )

    private val places = setOf(
        "school", "cloakroom", "office", "station", "airport", "railway station", "bus stop",
        "hotel", "restaurant", "café", "cafe", "shop", "supermarket", "market", "bank",
        "post office", "hospital", "church", "cinema", "theatre", "museum", "library", "garage",
        "factory", "workshop", "kitchen", "bedroom", "bathroom", "living room", "dining room",
        "garden", "park", "street", "road", "bridge", "village", "town", "city", "country",
        "building", "house", "flat", "room", "hall", "platform", "port", "harbour", "square"
    )

    private val timeNouns = setOf(
        "time", "date", "hour", "minute", "second", "morning", "afternoon", "evening", "night",
        "day", "week", "month", "year", "weekend", "holiday", "season", "spring", "summer",
        "autumn", "winter", "century", "appointment", "schedule"
    )

    private val abstractNouns = setOf(
        "name", "nationality", "job", "work", "housework", "matter", "idea", "advice", "news",
        "weather", "climate", "conversation", "subject", "truth", "change", "money", "price",
        "sale", "cost", "pocket money", "luck", "exam", "question", "answer", "mistake", "fault",
        "lesson", "language", "English", "music", "sport", "football", "tennis", "problem",
        "experience", "future", "past", "dream", "story", "joke", "reason", "meaning", "result",
        "competition", "race", "speed", "limit", "danger", "safety", "rule", "law", "service",
        "business", "company", "address", "telephone number", "number", "colour", "color", "make",
        "size", "shape", "way", "distance", "journey", "trip", "travel", "return", "fare",
        "attention", "help", "love", "hope", "fear", "plan", "decision", "choice", "permission",
        "mathematics"
    ).map(String::lowercase).toSet()

    private val knownPluralNouns = setOf(
        "children", "people", "men", "women", "feet", "teeth", "sales reps", "clothes", "stairs",
        "police", "glasses", "goods", "surroundings"
    )

    private val singularEndingInS = setOf(
        "glass", "dress", "bus", "class", "grass", "news", "customs", "physics", "mess", "mathematics"
    )

    private val colours = setOf(
        "blue", "white", "brown", "red", "grey", "gray", "yellow", "black", "orange", "pink", "green"
    )

    private val weatherAdjectives = setOf(
        "hot", "cold", "windy", "warm", "mild", "wet", "dry", "fine", "pleasant", "dark"
    )

    private val personalAdjectives = setOf(
        "well", "fat", "thin", "tall", "short", "old", "young", "busy", "lazy", "smart",
        "hard-working", "tired", "thirsty", "asleep", "careful", "absent", "lucky", "uncomfortable",
        "comfortable", "ready", "poor", "sure", "clever", "intelligent", "stupid", "foolish",
        "afraid", "awake", "alive", "married", "excited", "middle-aged", "amused", "embarrassed",
        "worried"
    )

    private val foodAdjectives = setOf("ripe", "sweet", "stale", "sour")

    private val sizeAndShapeAdjectives = setOf(
        "big", "small", "light", "heavy", "long", "large", "little", "sharp", "blunt", "low",
        "high", "soft", "round", "full", "empty"
    )

    private val practicalContexts = listOf(
        "front door", "reception desk", "workbench", "back seat", "office cupboard", "kitchen window"
    )

    private val contextTranslations = mapOf(
        "front door" to "前门",
        "reception desk" to "接待台",
        "workbench" to "工作台",
        "back seat" to "后座",
        "office cupboard" to "办公室橱柜",
        "kitchen window" to "厨房窗户"
    )
}
