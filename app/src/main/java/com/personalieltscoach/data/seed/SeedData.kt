package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.PlacementQuestionEntity
import com.personalieltscoach.data.local.entity.ReadingTextEntity
import com.personalieltscoach.data.local.entity.WordItemEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SeedData {
    fun words(now: Long): List<WordItemEntity> = wordRows.lineSequence()
        .map(String::trim)
        .filter(String::isNotBlank)
        .map { row ->
            val parts = row.split("|")
            require(parts.size == 5) { "Invalid seed word row: $row" }
            WordItemEntity(
                word = parts[0],
                phonetic = parts[1],
                meaning = parts[2],
                example = parts[3],
                exampleTranslation = parts[4],
                level = "A0-A1",
                createdAt = now,
                updatedAt = now
            )
        }.toList()

    fun questions(json: Json): List<PlacementQuestionEntity> {
        fun q(
            question: String,
            options: List<String>,
            answer: String,
            type: String,
            level: String
        ) = PlacementQuestionEntity(
            question = question,
            options = json.encodeToString(options),
            answer = answer,
            type = type,
            level = level
        )
        return listOf(
            q("book 的中文意思是？", listOf("书", "水", "门", "城市"), "书", "BASIC_WORD", "A0"),
            q("water 的中文意思是？", listOf("食物", "水", "工作", "学校"), "水", "BASIC_WORD", "A0"),
            q("happy 的中文意思是？", listOf("疲惫的", "开心的", "小的", "冷的"), "开心的", "BASIC_WORD", "A0"),
            q("read 的中文意思是？", listOf("阅读", "喝", "来", "听"), "阅读", "BASIC_WORD", "A0"),
            q("school 的中文意思是？", listOf("机场", "商店", "学校", "公园"), "学校", "BASIC_WORD", "A0"),
            q("morning 的中文意思是？", listOf("早晨", "夜晚", "明天", "星期"), "早晨", "BASIC_WORD", "A0"),
            q("teacher 的中文意思是？", listOf("学生", "朋友", "老师", "父亲"), "老师", "BASIC_WORD", "A0"),
            q("small 的反义词是？", listOf("old", "new", "big", "bad"), "big", "BASIC_WORD", "A0"),
            q("eat 的中文意思是？", listOf("吃", "写", "说", "看见"), "吃", "BASIC_WORD", "A0"),
            q("today 的中文意思是？", listOf("昨天", "现在", "今天", "明天"), "今天", "BASIC_WORD", "A0"),
            q("improve 的中文意思最接近？", listOf("提升", "忘记", "关闭", "等待"), "提升", "MEDIUM_WORD", "A1"),
            q("relaxed 的中文意思最接近？", listOf("紧张的", "放松的", "昂贵的", "安静的"), "放松的", "MEDIUM_WORD", "A1"),
            q("important 的中文意思是？", listOf("有趣的", "重要的", "困难的", "不同的"), "重要的", "MEDIUM_WORD", "A1"),
            q("practice 的中文意思最接近？", listOf("练习", "旅行", "购买", "选择"), "练习", "MEDIUM_WORD", "A1"),
            q("because 表示？", listOf("但是", "所以", "因为", "如果"), "因为", "MEDIUM_WORD", "A1"),
            q("usually 的中文意思是？", listOf("从不", "通常", "已经", "仍然"), "通常", "MEDIUM_WORD", "A1"),
            q("different 的中文意思是？", listOf("不同的", "相同的", "简单的", "年轻的"), "不同的", "MEDIUM_WORD", "A1"),
            q("answer 的中文意思最接近？", listOf("答案", "问题", "课程", "计划"), "答案", "MEDIUM_WORD", "A1"),
            q("language 的中文意思是？", listOf("习惯", "语言", "文化", "声音"), "语言", "MEDIUM_WORD", "A1"),
            q("remember 的中文意思是？", listOf("记得", "重复", "解释", "完成"), "记得", "MEDIUM_WORD", "A1"),
            q("I work at an airport. 的意思是？", listOf("我住在机场", "我在机场工作", "我去机场", "我喜欢机场"), "我在机场工作", "SENTENCE", "A1"),
            q("She drinks water every morning. 的意思是？", listOf("她每天早晨喝水", "她每天买水", "她早晨做饭", "她不喝水"), "她每天早晨喝水", "SENTENCE", "A1"),
            q("This book is very interesting. 的意思是？", listOf("这本书很有趣", "这本书很贵", "那本书很旧", "我正在写书"), "这本书很有趣", "SENTENCE", "A1"),
            q("Reading makes me feel relaxed. 的意思是？", listOf("阅读让我放松", "我阅读得很快", "我不喜欢阅读", "阅读很困难"), "阅读让我放松", "SENTENCE", "A2"),
            q("He studies English because he wants a new job. 的意思是？", listOf("他为了新工作学英语", "他在新工作教英语", "他不想学英语", "他因为工作停止学习"), "他为了新工作学英语", "SENTENCE", "A2"),
            q("I ___ a student.", listOf("am", "is", "are", "be"), "am", "GRAMMAR", "A0"),
            q("She ___ English every day.", listOf("study", "studies", "studying", "studied"), "studies", "GRAMMAR", "A1"),
            q("I like ___ books.", listOf("read", "reading", "reads", "to reading"), "reading", "GRAMMAR", "A1"),
            q("They ___ at home now.", listOf("is", "am", "are", "be"), "are", "GRAMMAR", "A1"),
            q("I was tired, ___ I went to bed early.", listOf("because", "but", "so", "or"), "so", "GRAMMAR", "A2")
        )
    }

    fun readings(now: Long): List<ReadingTextEntity> = listOf(
        ReadingTextEntity(
            title = "My Day",
            content = "I get up at seven every morning. I drink water and eat a simple breakfast. Then I study English for thirty minutes. I learn new words and read a short story. In the afternoon, I work and talk with my friends. At night, I review my words before I go to bed.",
            level = "A0-A1", wordCount = 55, createdAt = now
        ),
        ReadingTextEntity(
            title = "My Job",
            content = "I work at an airport. My job starts early in the morning. I help people find the right place and answer simple questions. I meet people from many cities. Sometimes the airport is very busy, but I like my job. I want to speak better English at work.",
            level = "A0-A1", wordCount = 52, createdAt = now
        ),
        ReadingTextEntity(
            title = "Reading Books",
            content = "I like reading books in my room. A good book makes me feel quiet and relaxed. I usually read for twenty minutes at night. When I see a new English word, I write it in my notebook. Reading every day helps me learn new ideas and improve my English.",
            level = "A0-A1", wordCount = 51, createdAt = now
        ),
        ReadingTextEntity(
            title = "Learning English",
            content = "English is difficult for me, but I study every day. I learn ten new words, listen to short sentences, and write simple answers. I do not need to be perfect today. I only need to make a little progress. My goal is to speak English with confidence in the future.",
            level = "A0-A1", wordCount = 53, createdAt = now
        ),
        ReadingTextEntity(
            title = "My Room",
            content = "My room is small but comfortable. There is a bed near the window and a table beside the door. My computer and English books are on the table. I study there every evening. The room is quiet, so it is a good place to read, write, and learn.",
            level = "A0-A1", wordCount = 50, createdAt = now
        )
    )

    val sampleSentences = listOf(
        "I like reading.",
        "I work at an airport.",
        "I want to learn English.",
        "This book is very interesting.",
        "Reading makes me feel relaxed.",
        "I study English every day.",
        "She drinks water every morning.",
        "He is my good friend.",
        "We live in a big city.",
        "They go to school together.",
        "My room is small but clean.",
        "I write new words in a notebook.",
        "English is important for my job.",
        "I feel tired after work.",
        "Please speak slowly.",
        "I do not understand this sentence.",
        "My teacher helps me a lot.",
        "I read a short story at night.",
        "Learning English takes time.",
        "Small steps make a big difference."
    )

    private val wordRows = """
        I|/aɪ/|pron. 我|I am happy.|我很开心。
        you|/juː/|pron. 你；你们|You are my friend.|你是我的朋友。
        he|/hiː/|pron. 他|He is a teacher.|他是一名老师。
        she|/ʃiː/|pron. 她|She likes reading.|她喜欢阅读。
        we|/wiː/|pron. 我们|We study together.|我们一起学习。
        they|/ðeɪ/|pron. 他们|They live in this city.|他们住在这个城市。
        it|/ɪt/|pron. 它|It is a good book.|这是一本好书。
        this|/ðɪs/|pron. 这个|This is my phone.|这是我的手机。
        that|/ðæt/|pron. 那个|That is our school.|那是我们的学校。
        what|/wɒt/|pron. 什么|What is your name?|你叫什么名字？
        who|/huː/|pron. 谁|Who is your teacher?|谁是你的老师？
        go|/ɡəʊ/|v. 去|I go to work at eight.|我八点去上班。
        come|/kʌm/|v. 来|Please come to my home.|请来我家。
        eat|/iːt/|v. 吃|We eat breakfast together.|我们一起吃早餐。
        drink|/drɪŋk/|v. 喝|I drink water every morning.|我每天早晨喝水。
        read|/riːd/|v. 阅读|I read a book every night.|我每天晚上读一本书。
        write|/raɪt/|v. 写|She writes an English sentence.|她写了一个英文句子。
        work|/wɜːk/|v. 工作|I work at an airport.|我在机场工作。
        study|/ˈstʌdi/|v. 学习|I study English every day.|我每天学习英语。
        learn|/lɜːn/|v. 学习；学会|We learn ten new words.|我们学习十个新单词。
        like|/laɪk/|v. 喜欢|I like reading books.|我喜欢读书。
        want|/wɒnt/|v. 想要|I want to improve my English.|我想提高英语。
        need|/niːd/|v. 需要|I need more time.|我需要更多时间。
        have|/hæv/|v. 有|I have a new computer.|我有一台新电脑。
        make|/meɪk/|v. 制作；使|Music makes me happy.|音乐让我开心。
        feel|/fiːl/|v. 感觉|I feel tired today.|我今天感觉很累。
        see|/siː/|v. 看见|I see my friend at school.|我在学校看见朋友。
        say|/seɪ/|v. 说|Please say it again.|请再说一遍。
        speak|/spiːk/|v. 说话|Can you speak slowly?|你能说慢一点吗？
        listen|/ˈlɪsən/|v. 听|I listen to English every day.|我每天听英语。
        can|/kæn/|modal v. 能；可以|I can speak a little English.|我会说一点英语。
        book|/bʊk/|n. 书|This book is interesting.|这本书很有趣。
        phone|/fəʊn/|n. 手机|My phone is on the table.|我的手机在桌上。
        water|/ˈwɔːtə/|n. 水|Please drink some water.|请喝一些水。
        food|/fuːd/|n. 食物|The food is very good.|食物很好吃。
        computer|/kəmˈpjuːtə/|n. 电脑|I study on my computer.|我用电脑学习。
        table|/ˈteɪbəl/|n. 桌子|The book is on the table.|书在桌子上。
        chair|/tʃeə/|n. 椅子|Please sit on this chair.|请坐在这把椅子上。
        door|/dɔː/|n. 门|Please close the door.|请关门。
        room|/ruːm/|n. 房间|My room is small and clean.|我的房间小而干净。
        home|/həʊm/|n. 家|I study English at home.|我在家学习英语。
        school|/skuːl/|n. 学校|The school is near my home.|学校在我家附近。
        airport|/ˈeəpɔːt/|n. 机场|The airport is very busy.|机场很繁忙。
        city|/ˈsɪti/|n. 城市|This is a big city.|这是一座大城市。
        shop|/ʃɒp/|n. 商店|I work in a small shop.|我在一家小商店工作。
        park|/pɑːk/|n. 公园|We walk in the park.|我们在公园散步。
        good|/ɡʊd/|adj. 好的|This is a good idea.|这是个好主意。
        bad|/bæd/|adj. 坏的|The weather is bad today.|今天天气不好。
        big|/bɪɡ/|adj. 大的|They live in a big house.|他们住在一所大房子里。
        small|/smɔːl/|adj. 小的|I take small steps every day.|我每天迈出一小步。
        happy|/ˈhæpi/|adj. 开心的|Learning makes me happy.|学习让我开心。
        tired|/ˈtaɪəd/|adj. 疲惫的|I am tired after work.|下班后我很累。
        new|/njuː/|adj. 新的|I learn a new word.|我学习一个新单词。
        old|/əʊld/|adj. 旧的；年老的|This is an old book.|这是一本旧书。
        hot|/hɒt/|adj. 热的|The water is hot.|水是热的。
        cold|/kəʊld/|adj. 冷的|It is cold this morning.|今天早晨很冷。
        easy|/ˈiːzi/|adj. 容易的|This question is easy.|这个问题很容易。
        difficult|/ˈdɪfɪkəlt/|adj. 困难的|English is difficult but interesting.|英语很难但很有趣。
        interesting|/ˈɪntrəstɪŋ/|adj. 有趣的|The story is interesting.|这个故事很有趣。
        important|/ɪmˈpɔːtənt/|adj. 重要的|Practice is important.|练习很重要。
        today|/təˈdeɪ/|n. 今天|I study ten words today.|我今天学习十个单词。
        tomorrow|/təˈmɒrəʊ/|n. 明天|I will review them tomorrow.|我明天会复习它们。
        yesterday|/ˈjestədeɪ/|n. 昨天|I was busy yesterday.|我昨天很忙。
        morning|/ˈmɔːnɪŋ/|n. 早晨|I read in the morning.|我早晨阅读。
        afternoon|/ˌɑːftəˈnuːn/|n. 下午|I work in the afternoon.|我下午工作。
        evening|/ˈiːvnɪŋ/|n. 晚上|We study in the evening.|我们晚上学习。
        night|/naɪt/|n. 夜晚|I read at night.|我晚上读书。
        now|/naʊ/|adv. 现在|I am studying now.|我现在正在学习。
        time|/taɪm/|n. 时间|Learning takes time.|学习需要时间。
        day|/deɪ/|n. 天|I practice every day.|我每天练习。
        week|/wiːk/|n. 星期|I read five books this week.|我这周读五本书。
        friend|/frend/|n. 朋友|He is my good friend.|他是我的好朋友。
        family|/ˈfæməli/|n. 家人|My family helps me.|我的家人帮助我。
        mother|/ˈmʌðə/|n. 母亲|My mother likes tea.|我妈妈喜欢茶。
        father|/ˈfɑːðə/|n. 父亲|My father reads every morning.|我爸爸每天早晨阅读。
        teacher|/ˈtiːtʃə/|n. 老师|My teacher speaks slowly.|我的老师说话很慢。
        student|/ˈstjuːdənt/|n. 学生|I am an English student.|我是一名英语学习者。
        name|/neɪm/|n. 名字|My name is Li Ming.|我的名字叫李明。
        job|/dʒɒb/|n. 工作|English is useful for my job.|英语对我的工作有用。
        English|/ˈɪŋɡlɪʃ/|n. 英语|I want to speak English.|我想说英语。
        Chinese|/ˌtʃaɪˈniːz/|n. 中文|Please explain it in Chinese.|请用中文解释。
        yes|/jes/|adv. 是|Yes, I understand.|是的，我明白了。
        no|/nəʊ/|adv. 不；没有|No, I do not know.|不，我不知道。
        please|/pliːz/|adv. 请|Please read this sentence.|请读这个句子。
        thanks|/θæŋks/|n. 谢谢|Thanks for your help.|谢谢你的帮助。
        hello|/həˈləʊ/|int. 你好|Hello, nice to meet you.|你好，很高兴认识你。
        goodbye|/ˌɡʊdˈbaɪ/|int. 再见|Goodbye, see you tomorrow.|再见，明天见。
        and|/ænd/|conj. 和；并且|I read and write every day.|我每天阅读和写作。
        but|/bʌt/|conj. 但是|It is difficult but useful.|它很难但很有用。
        because|/bɪˈkɒz/|conj. 因为|I study because I want to improve.|我学习，因为我想进步。
        so|/səʊ/|conj. 所以|I was tired, so I went home.|我累了，所以回家了。
        in|/ɪn/|prep. 在……里面|The book is in my room.|书在我的房间里。
        on|/ɒn/|prep. 在……上面|The phone is on the table.|手机在桌上。
        at|/æt/|prep. 在|I work at an airport.|我在机场工作。
        with|/wɪð/|prep. 和；用|I study with my friend.|我和朋友一起学习。
        for|/fɔː/|prep. 为了；给|This book is for beginners.|这本书适合初学者。
        from|/frɒm/|prep. 从；来自|I am from China.|我来自中国。
        to|/tuː/|prep. 到；向|I go to school by bus.|我乘公交去学校。
        very|/ˈveri/|adv. 非常|This lesson is very useful.|这节课非常有用。
        every|/ˈevri/|det. 每个|I learn English every day.|我每天学习英语。
    """.trimIndent()
}
