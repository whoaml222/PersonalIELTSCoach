package com.personalieltscoach.data.seed

import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.local.entity.SentenceCardEntity
import com.personalieltscoach.data.local.entity.WordSource

/**
 * Offline vocabulary pack generated from https://www.ncego.com/books/words/nce1.
 * The source contains 1,108 lesson entries and 1,021 unique study words.
 * Repeated entries are merged so learners do not receive duplicate cards.
 */
object Nce1WordPack {
    const val SOURCE_ENTRY_COUNT = 1_108
    const val UNIQUE_WORD_COUNT = 1_021

    fun words(
        now: Long,
        practiceCards: List<SentenceCardEntity> = emptyList()
    ): List<WordItemEntity> = rows.lineSequence()
        .map(String::trim)
        .filter(String::isNotBlank)
        .mapIndexed { index, row ->
            val parts = row.split('\t')
            require(parts.size == 4) { "Invalid NCE1 word row" }
            val word = parts[0]
            val practiceCard = practiceCards.firstOrNull { containsTerm(it.sentence, word) }
            val contextualExample = Nce1ExampleFactory.create(
                word = word,
                meaning = parts[2],
                lesson = parts[3],
                ordinal = index
            )
            WordItemEntity(
                word = word,
                phonetic = parts[1],
                meaning = parts[2],
                example = practiceCard?.sentence ?: contextualExample.sentence,
                exampleTranslation = practiceCard?.translation ?: contextualExample.translation,
                level = parts[3],
                source = WordSource.NCE1,
                createdAt = now,
                updatedAt = now
            )
        }
        .toList()

    private fun containsTerm(sentence: String, term: String): Boolean =
        Regex(
            pattern = "(?<![A-Za-z])${Regex.escape(term)}(?![A-Za-z])",
            option = RegexOption.IGNORE_CASE
        ).containsMatchIn(sentence)

    private val rows = """
excuse	/ɪkˈskjuːs , ɪkˈskjuːz/	v. 原谅	NCE1 Lesson 1&2
me	/miː/	pron. 我(宾格)	NCE1 Lesson 1&2
yes	/jes/	adv. 是的	NCE1 Lesson 1&2
is	/ɪz/	v. be动词现在时第三人称单数	NCE1 Lesson 1&2
this	/ðɪs/	pron. 这	NCE1 Lesson 1&2
your	/jɔː(r)/	pron. 你的，你们的	NCE1 Lesson 1&2
handbag	/ˈhændbæɡ/	n. (女用)手提包	NCE1 Lesson 1&2
pardon	/ˈpɑːd(ə)n/	interj. 原谅，请再说一遍	NCE1 Lesson 1&2
it	/ɪt/	pron. 它	NCE1 Lesson 1&2
thank	/θæŋk/	v. 感谢	NCE1 Lesson 1&2
you	/juː/	pron. 你(们)	NCE1 Lesson 1&2
very	/ˈveri/	adv. 非常地	NCE1 Lesson 1&2
much	/mʌtʃ/	adv. 多地	NCE1 Lesson 1&2
pen	/pen/	n. 钢笔	NCE1 Lesson 1&2
pencil	/ˈpens(ə)l/	n. 铅笔	NCE1 Lesson 1&2
book	/bʊk/	n. 书	NCE1 Lesson 1&2
watch	/wɒtʃ/	n. 手表	NCE1 Lesson 1&2
coat	/kəʊt/	n. 上衣，外衣	NCE1 Lesson 1&2
dress	/dres/	n. 连衣裙	NCE1 Lesson 1&2
skirt	/skɜːt/	n. 裙子	NCE1 Lesson 1&2
shirt	/ʃɜːt/	n. 衬衣	NCE1 Lesson 1&2
car	/kɑː(r)/	n. 小汽车	NCE1 Lesson 1&2
house	/haʊs/	n. 房子	NCE1 Lesson 1&2
thank you	/ˈθæŋk juː/	interj. 感谢你(们)	NCE1 Lesson 1&2
very much	/ˈveri mʌtʃ/	interj. 非常地	NCE1 Lesson 1&2
umbrella	/ʌmˈbrelə/	n. 伞	NCE1 Lesson 3&4
please	/pliːz/	interj. 请	NCE1 Lesson 3&4
here	/hɪə(r)/	adv. 这里	NCE1 Lesson 3&4
my	/maɪ/	pron. 我的	NCE1 Lesson 3&4
ticket	/ˈtɪkɪt/	n. 票	NCE1 Lesson 3&4
number	/ˈnʌmbə(r)/	n. 号码	NCE1 Lesson 3&4
five	/faɪv/	num. 五	NCE1 Lesson 3&4
sorry	/ˈsɒri/	interj. 对不起的	NCE1 Lesson 3&4
sir	/sɜː(r)/	n. 先生	NCE1 Lesson 3&4
cloakroom	/ˈkləʊkruːm/	n. 衣帽存放处	NCE1 Lesson 3&4
suit	/suːt/	n. 一套衣服；v. 适于	NCE1 Lesson 3&4, NCE1 Lesson 107&108
school	/skuːl/	n. 学校	NCE1 Lesson 3&4
teacher	/ˈtiːtʃə(r)/	n. 老师	NCE1 Lesson 3&4
son	/sʌn/	n. 儿子	NCE1 Lesson 3&4
daughter	/ˈdɔːtə(r)/	n. 女儿	NCE1 Lesson 3&4
Mr.	/ˈmɪstər/	n. 先生	NCE1 Lesson 5&6
miss	/mɪs/	n. 小姐；v. 想念，思念；v. 错过	NCE1 Lesson 5&6, NCE1 Lesson 091&92, NCE1 Lesson 095&96
good	/ɡʊd/	adj. 好	NCE1 Lesson 5&6
morning	/ˈmɔːnɪŋ/	n. 早晨	NCE1 Lesson 5&6, NCE1 Lesson 055&56
new	/njuː/	adj. 新的	NCE1 Lesson 5&6
student	/ˈstjuːd(ə)nt/	n. 学生	NCE1 Lesson 5&6
French	/frentʃ/	n. 法国人	NCE1 Lesson 5&6
German	/'dʒɜːmən/	n. 德国人	NCE1 Lesson 5&6
nice	/naɪs/	adj. 美好的	NCE1 Lesson 5&6
meet	/miːt/	v. 遇见	NCE1 Lesson 5&6
too	/tuː/	adj. 也	NCE1 Lesson 5&6
Japanese	/dʒæpə'ni:z/	n. 日本人	NCE1 Lesson 5&6
Korean	/kə'riən/	n. 韩国人	NCE1 Lesson 5&6
Chinese	/tʃaɪˈniːz/	n. 中国人	NCE1 Lesson 5&6
make	/meɪk/	n. (产品的)牌号；v. 做	NCE1 Lesson 5&6, NCE1 Lesson 037&38
Sweden	/ˈswiːd(ə)n/	n. 瑞典	NCE1 Lesson 5&6, NCE1 Lesson 051&52
England	/ˈɪŋɡlənd/	n. 英国	NCE1 Lesson 5&6, NCE1 Lesson 051&52
English	/'ɪŋɡlɪʃ/	adj. 英国的	NCE1 Lesson 5&6
America	/ə'merɪkə/	n. 美国	NCE1 Lesson 5&6
American	/əˈmerɪkən/	adj. 美国的	NCE1 Lesson 5&6
Italy	/'ɪtəlɪ/	n. 意大利	NCE1 Lesson 5&6, NCE1 Lesson 051&52
Swedish	/'swi:dɪʃ/	adj. 瑞典的	NCE1 Lesson 5&6
Italian	/ɪˈtælɪən/	adj. 意大利的	NCE1 Lesson 5&6
Volvo	/'vɔlvəu/	n. 沃尔沃	NCE1 Lesson 5&6
Peugeot	/pjuːˈʒoʊ/	n. 标致	NCE1 Lesson 5&6
Mercedes	/məˈsɪdi:z/	n. 梅赛德斯	NCE1 Lesson 5&6
Toyota	/tɔɪˈəʊtə/	n. 丰田	NCE1 Lesson 5&6
Daewoo	/´da:wu:/	n. 大宇	NCE1 Lesson 5&6
mini	/ˈmɪni/	n. 迷你	NCE1 Lesson 5&6
ford	/fɔːd/	n. 福特	NCE1 Lesson 5&6
fiat	/ˈfiːæt/	n. 菲亚特	NCE1 Lesson 5&6
I	/aɪ/	pron. 我	NCE1 Lesson 7&8
am	/æm/	v. be 动词现在时第一人称单数	NCE1 Lesson 7&8
are	/ɑː(r)/	v. be动词现在时复数	NCE1 Lesson 7&8
name	/neɪm/	n. 名字	NCE1 Lesson 7&8
what	/wɒt/	pron. 什么	NCE1 Lesson 7&8
nationality	/ˌnæʃəˈnæləti/	n. 国籍	NCE1 Lesson 7&8
job	/dʒɒb/	n. 工作	NCE1 Lesson 7&8, NCE1 Lesson 123&124
keyboard	/ˈkiːbɔːd/	n. 电脑键盘	NCE1 Lesson 7&8
operator	/ˈɒpəreɪtə(r)/	n. 操作人员	NCE1 Lesson 7&8
engineer	/ˌendʒɪˈnɪə(r)/	n. 工程师	NCE1 Lesson 7&8, NCE1 Lesson 139&140
policeman	/pəˈliːsmən/	n. 警察	NCE1 Lesson 7&8
policewoman	/pəˈliːswʊmən/	n. 女警察	NCE1 Lesson 7&8
taxi driver	/ˈtæksi ˈdraɪvə(r)/	n. 出租汽车司机	NCE1 Lesson 7&8
air hostess	/eə(r) ˈhəʊstəs/	n. 空中小姐	NCE1 Lesson 7&8
postman	/ˈpəʊstmən/	n. 邮递员	NCE1 Lesson 7&8
nurse	/nɜːs/	n. 护士	NCE1 Lesson 7&8
mechanic	/məˈkænɪk/	n. 机械师	NCE1 Lesson 7&8
hairdresser	/ˈheədresə(r)/	n. 理发师	NCE1 Lesson 7&8
housewife	/ˈhaʊswaɪf/	n. 家庭妇女	NCE1 Lesson 7&8
housework	/ˈhaʊswɜːk/	n. 家务	NCE1 Lesson 7&8, NCE1 Lesson 055&56
milkman	/ˈmɪlkmən/	n. 送牛奶的人	NCE1 Lesson 7&8
hello	/həˈləʊ/	interj. 喂(表示问候)	NCE1 Lesson 9&10
Hi	/haɪ/	interj. 喂，嗨	NCE1 Lesson 9&10
how	/haʊ/	adv. 怎样	NCE1 Lesson 9&10
today	/təˈdeɪ/	adv. 今天	NCE1 Lesson 9&10
well	/wel/	adj. 身体好	NCE1 Lesson 9&10
fine	/faɪn/	adj. 美好的	NCE1 Lesson 9&10
thanks	/θæŋks/	interj. 谢谢	NCE1 Lesson 9&10
goodbye	/ˌɡʊdˈbaɪ/	interj. 再见	NCE1 Lesson 9&10
see	/siː/	v. 见	NCE1 Lesson 9&10
fat	/fæt/	adj. 胖的	NCE1 Lesson 9&10
woman	/ˈwʊmən/	n. 女人	NCE1 Lesson 9&10
thin	/θɪn/	adj. 瘦的	NCE1 Lesson 9&10
tall	/tɔːl/	adj. 高的	NCE1 Lesson 9&10
short	/ʃɔːt/	adj. 矮的	NCE1 Lesson 9&10
dirty	/ˈdɜːti/	adj. 脏的	NCE1 Lesson 9&10
clean	/kliːn/	adj. 干净的；v. 清洗	NCE1 Lesson 9&10, NCE1 Lesson 031&32
hot	/hɒt/	adj. 热的	NCE1 Lesson 9&10
cold	/kəʊld/	adj. 冷的；n. 感冒	NCE1 Lesson 9&10, NCE1 Lesson 061&62
old	/əʊld/	adj. 老的	NCE1 Lesson 9&10
young	/jʌŋ/	adj. 年轻的	NCE1 Lesson 9&10, NCE1 Lesson 101&102
busy	/ˈbɪzi/	adj. 忙的	NCE1 Lesson 9&10
lazy	/ˈleɪzi/	adj. 懒的	NCE1 Lesson 9&10
whose	/huːz/	pron. 谁的	NCE1 Lesson 11&12
blue	/bluː/	adj. 蓝色的	NCE1 Lesson 11&12
perhaps	/pəˈhæps/	adv. 大概	NCE1 Lesson 11&12
white	/waɪt/	adj. 白色的	NCE1 Lesson 11&12
catch	/kætʃ/	v. 抓住；v. 赶上	NCE1 Lesson 11&12, NCE1 Lesson 095&96
father	/ˈfɑːðə(r)/	n. 父亲	NCE1 Lesson 11&12
mother	/ˈmʌðə(r)/	n. 母亲	NCE1 Lesson 11&12
blouse	/blaʊz/	n. 女衬衫	NCE1 Lesson 11&12
sister	/ˈsɪstə(r)/	n. 姐，妹	NCE1 Lesson 11&12
tie	/taɪ/	n. 领带	NCE1 Lesson 11&12
brother	/ˈbrʌðə(r)/	n. 兄，弟	NCE1 Lesson 11&12
his	/hɪz/	pron. 他的	NCE1 Lesson 11&12
her	/hɜː(r)/	pron. 她的	NCE1 Lesson 11&12
colour	/ˈkʌlə(r)/	n. 颜色(英)	NCE1 Lesson 13&14
color	/ˈkʌlə(r)/	n. 颜色(美)	NCE1 Lesson 13&14
green	/ɡriːn/	n. 绿色	NCE1 Lesson 13&14
come	/kʌm/	v. 来	NCE1 Lesson 13&14
upstairs	/ˌʌpˈsteəz/	adv. 楼上	NCE1 Lesson 13&14
downstairs	/ˌdaʊnˈsteəz/	adv. 楼下；adv. 下楼	NCE1 Lesson 13&14, NCE1 Lesson 099&100
smart	/smɑːt/	adj. 时髦的，巧妙的；adj. 聪明的	NCE1 Lesson 13&14, NCE1 Lesson 103&104
hat	/hæt/	n. 帽子	NCE1 Lesson 13&14
same	/seɪm/	adj. 相同的	NCE1 Lesson 13&14
lovely	/ˈlʌvli/	adj. 可爱的，秀丽的	NCE1 Lesson 13&14
case	/keɪs/	n. 箱子	NCE1 Lesson 13&14
carpet	/ˈkɑːpɪt/	n. 地毯	NCE1 Lesson 13&14
blanket	/ˈblæŋkɪt/	n. 毯子	NCE1 Lesson 13&14
dog	/dɒɡ/	n. 狗	NCE1 Lesson 13&14
puppy	/ˈpʌpi/	n. 小狗	NCE1 Lesson 13&14
Customs	/ˈkʌstəmz/	n. 海关	NCE1 Lesson 15&16
officer	/ˈɒfɪsə(r)/	n. 官员	NCE1 Lesson 15&16
girl	/ɡɜːl/	n. 女孩，姑娘	NCE1 Lesson 15&16
Danish	/ˈdeɪnɪʃ/	n. 丹麦人	NCE1 Lesson 15&16
Denmark	/ˈdenmɑːk/	n. 丹麦	NCE1 Lesson 15&16, NCE1 Lesson 069&70
friend	/frend/	n. 朋友	NCE1 Lesson 15&16
Norwegian	/nɔːˈwiːdʒən/	n. 挪威人	NCE1 Lesson 15&16
passport	/ˈpɑːspɔːt/	n. 护照	NCE1 Lesson 15&16
brown	/braʊn/	adj. 棕色的	NCE1 Lesson 15&16
tourist	/ˈtʊərɪst/	n. 旅游者	NCE1 Lesson 15&16
Russian	/ˈrʌʃ(ə)n/	n. 俄罗斯人	NCE1 Lesson 15&16
Russia	/ˈrʌʃə/	n. 俄罗斯	NCE1 Lesson 15&16, NCE1 Lesson 051&52
Dutch	/dʌtʃ/	n. 荷兰人	NCE1 Lesson 15&16
Holland	/ˈhɒlənd/	n. 荷兰	NCE1 Lesson 15&16, NCE1 Lesson 051&52
these	/ðiːz/	pron. 这些(this的复数)	NCE1 Lesson 15&16
red	/red/	adj. 红色的	NCE1 Lesson 15&16
grey	/ɡreɪ/	adj. 灰色的	NCE1 Lesson 15&16
yellow	/ˈjeləʊ/	adj. 黄色的	NCE1 Lesson 15&16
black	/blæk/	adj. 黑色的	NCE1 Lesson 15&16
orange	/ˈɒrɪndʒ/	adj. 橘黄色的；n. 橙	NCE1 Lesson 15&16, NCE1 Lesson 047&48
employee	/ɪmˈplɔɪiː/	n. 雇员	NCE1 Lesson 17&18
hard-working	/ˌhɑːd ˈwɜːkɪŋ/	adj. 勤奋的	NCE1 Lesson 17&18
sales reps	/ˈseɪlz reps/	n. 推销员	NCE1 Lesson 17&18
sale	/seɪl/	n. 销售；n. 出售	NCE1 Lesson 17&18, NCE1 Lesson 089&90
man	/mæn/	n. 男人	NCE1 Lesson 17&18
office	/ˈɒfɪs/	n. 办公室	NCE1 Lesson 17&18
assistant	/əˈsɪstənt/	n. 助手	NCE1 Lesson 17&18
matter	/ˈmætə(r)/	n. 事情	NCE1 Lesson 19&20
children	/ˈtʃɪldrən/	n. 孩子们(child 的复数)	NCE1 Lesson 19&20
child	/tʃaɪld/	n. 孩子	NCE1 Lesson 19&20
kid	/kɪd/	n. 小孩儿	NCE1 Lesson 19&20
tired	/ˈtaɪəd/	adj. 累，疲乏	NCE1 Lesson 19&20
boy	/bɔɪ/	n. 男孩	NCE1 Lesson 19&20
thirsty	/ˈθɜːsti/	adj. 渴	NCE1 Lesson 19&20
mum	/mʌm/	n. 妈妈(儿语)	NCE1 Lesson 19&20
sit down	/ˈsɪt daʊn/	v. 坐下	NCE1 Lesson 19&20
right	/raɪt/	adj. 好，可以；n. 右边	NCE1 Lesson 19&20, NCE1 Lesson 025&26
ice cream	/ˈaɪs kriːm/	n. 冰淇淋	NCE1 Lesson 19&20
ice	/aɪs/	n. 冰	NCE1 Lesson 19&20
cream	/kriːm/	n. 脂	NCE1 Lesson 19&20
big	/bɪɡ/	adj. 大的	NCE1 Lesson 19&20, NCE1 Lesson 21&22
small	/smɔːl/	adj. 小的	NCE1 Lesson 19&20, NCE1 Lesson 21&22
open	/ˈəʊpən/	adj. 开着的；v. 打开	NCE1 Lesson 19&20, NCE1 Lesson 029&30
shut	/ʃʌt/	adj. 关着的；v. 关门	NCE1 Lesson 19&20, NCE1 Lesson 029&30
close	/kləʊz/	v. 关	NCE1 Lesson 19&20
light	/laɪt/	adj. 轻的	NCE1 Lesson 19&20
heavy	/ˈhevi/	adj. 重的	NCE1 Lesson 19&20
long	/lɒŋ/	adj. 长的	NCE1 Lesson 19&20
shoe	/ʃuː/	n. 鞋子	NCE1 Lesson 19&20
grandfather	/ˈɡrænfɑːðə(r)/	n. 祖父，外祖父	NCE1 Lesson 19&20
grandmother	/ˈɡrænmʌðə(r)/	n. 祖母，外祖	NCE1 Lesson 19&20
grand	/ɡrænd/	n. 大	NCE1 Lesson 19&20
give	/ɡɪv/	v. 给	NCE1 Lesson 21&22
one	/wʌn/	pron. 一个	NCE1 Lesson 21&22
which	/wɪtʃ/	pron. 哪一个	NCE1 Lesson 21&22
empty	/ˈempti/	adj. 空的；v. 倒空，使…变空	NCE1 Lesson 21&22, NCE1 Lesson 029&30
full	/fʊl/	adj. 满的	NCE1 Lesson 21&22
large	/lɑːdʒ/	adj. 大的	NCE1 Lesson 21&22
little	/ˈlɪt(ə)l/	adj. 小的；adv. 少得几乎没有	NCE1 Lesson 21&22, NCE1 Lesson 109&110
sharp	/ʃɑːp/	adj. 尖的，锋利的	NCE1 Lesson 21&22
blunt	/blʌnt/	adj. 钝的	NCE1 Lesson 21&22
box	/bɒks/	n. 盒子，箱子	NCE1 Lesson 21&22
glass	/ɡlɑːs/	n. 杯子	NCE1 Lesson 21&22
cup	/kʌp/	n. 茶杯；n. .杯子	NCE1 Lesson 21&22, NCE1 Lesson 025&26
bottle	/ˈbɒt(ə)l/	n. 瓶子	NCE1 Lesson 21&22
tin	/tɪn/	n. 罐头	NCE1 Lesson 21&22
knife	/naɪf/	n. 刀子	NCE1 Lesson 21&22
fork	/fɔːk/	n. 叉子	NCE1 Lesson 21&22
chopsticks	/ˈtʃɒpstɪks/	n. 筷子	NCE1 Lesson 21&22
glasses	/ˈɡlɑːsɪz/	n. 玻璃杯；n. 眼镜	NCE1 Lesson 23&24, NCE1 Lesson 115&116
on	/ɒn/	prep. 在…之上	NCE1 Lesson 23&24
shelf	/ʃelf/	n. 架子，搁板	NCE1 Lesson 23&24
desk	/desk/	n. 课桌	NCE1 Lesson 23&24
table	/ˈteɪb(ə)l/	n. 桌子	NCE1 Lesson 23&24
plate	/pleɪt/	n. 盘子	NCE1 Lesson 23&24
dish	/dɪʃ/	n. 盘子、一盘食物；n. 盘子，碟子	NCE1 Lesson 23&24, NCE1 Lesson 037&38
cupboard	/ˈkʌbəd/	n. 食橱	NCE1 Lesson 23&24
cigarette	/ˌsɪɡəˈret/	n. 香烟；n. 烟	NCE1 Lesson 23&24, NCE1 Lesson 041&42
television	/ˈtelɪvɪʒ(ə)n/	n. 电视机	NCE1 Lesson 23&24
floor	/flɔː(r)/	n. 地板	NCE1 Lesson 23&24
dressing table	/ˈdresɪŋ teɪbl/	n. 梳妆台	NCE1 Lesson 23&24
dressing	/ˈdresɪŋ/	n. 打扮	NCE1 Lesson 23&24
magazine	/ˌmæɡəˈziːn/	n. 杂志	NCE1 Lesson 23&24
bed	/bed/	n. 床	NCE1 Lesson 23&24
newspaper	/ˈnjuːzpeɪpə(r)/	n. 报纸	NCE1 Lesson 23&24
stereo	/ˈsteriəʊ/	n. 立体声音响	NCE1 Lesson 23&24
radio	/ˈreɪdiəʊ/	n. 收音机	NCE1 Lesson 23&24
Mrs.	/'misiz/	n. 夫人	NCE1 Lesson 025&26
kitchen	/ˈkɪtʃɪn/	n. 厨房	NCE1 Lesson 025&26
refrigerator	/rɪˈfrɪdʒəreɪtə(r)/	n. 电冰箱	NCE1 Lesson 025&26
electric	/ɪˈlektrɪk/	adj. 带电的，可通电的	NCE1 Lesson 025&26
left	/left/	n. 左边	NCE1 Lesson 025&26
cooker	/ˈkʊkə(r)/	n. 炉子，炊具	NCE1 Lesson 025&26
chef	/ʃef/	n. 厨师	NCE1 Lesson 025&26
cook	/kʊk/	v. 煮；v. 做(饭菜)	NCE1 Lesson 025&26, NCE1 Lesson 031&32
of	/ɒv/	prep. (属于)…的	NCE1 Lesson 025&26
room	/ruːm/	n. 房间	NCE1 Lesson 025&26
where	/weə(r)/	adv. 在哪里	NCE1 Lesson 025&26
in	/ɪn/	prep. 在…里	NCE1 Lesson 025&26
living room	/'lɪvɪŋ ru:m/	n. 客厅	NCE1 Lesson 027&28
near	/nɪə(r)/	prep. 靠近	NCE1 Lesson 027&28
window	/ˈwɪndəʊ/	n. 窗户	NCE1 Lesson 027&28
armchair	/ˈɑːmtʃeə(r)/	n. 手扶椅	NCE1 Lesson 027&28
door	/dɔː(r)/	n. 门	NCE1 Lesson 027&28, NCE1 Lesson 115&116
picture	/ˈpɪktʃə(r)/	n. 图画	NCE1 Lesson 027&28
image	/ˈɪmɪdʒ/	n. 图片	NCE1 Lesson 027&28
photo	/ˈfəʊtəʊ/	n. 照片	NCE1 Lesson 027&28
wall	/wɔːl/	n. 墙	NCE1 Lesson 027&28
The Great Wall	/ðə ɡreɪt wɔːl/	n. 长城	NCE1 Lesson 027&28
trousers	/ˈtraʊzəz/	n. 〔复数〕长裤	NCE1 Lesson 027&28
shorts	/ʃɔːts/	n. 短裤	NCE1 Lesson 027&28
jeans	/dʒiːnz/	n. 牛仔裤	NCE1 Lesson 027&28
pants	/pænts/	n. 裤子	NCE1 Lesson 027&28
bedroom	/ˈbedruːm/	n. 卧室	NCE1 Lesson 029&30
untidy	/ʌnˈtaɪdi/	adj. 乱，不整齐	NCE1 Lesson 029&30
tidy	/ˈtaɪdi/	adj. 整齐的	NCE1 Lesson 029&30, NCE1 Lesson 143&144
must	/mʌst/	v. 必须，应该；v. 必须	NCE1 Lesson 029&30, NCE1 Lesson 061&62
air	/eə(r)/	v. 使…通风，换换空气	NCE1 Lesson 029&30
put	/pʊt/	v. 放置	NCE1 Lesson 029&30
clothes	/kləʊðz/	n. 衣服	NCE1 Lesson 029&30
wardrobe	/ˈwɔːdrəʊb/	n. 大衣柜	NCE1 Lesson 029&30
closet	/ˈklɒzɪt/	n. 衣柜	NCE1 Lesson 029&30
dust	/dʌst/	v. 掸掉灰尘	NCE1 Lesson 029&30
sweep	/swiːp/	v. 扫	NCE1 Lesson 029&30
read	/riːd/	v. 读；v. 通过阅读得知	NCE1 Lesson 029&30, NCE1 Lesson 081&82, NCE1 Lesson 127&128
sharpen	/ˈʃɑːpən/	v. 削尖，使锋利	NCE1 Lesson 029&30
put on	/pʊt ɒn/	v. 穿上	NCE1 Lesson 029&30, NCE1 Lesson 075&76
take off	/teɪk ɒf/	v. 脱掉；v. 脱下	NCE1 Lesson 029&30, NCE1 Lesson 075&76
turn on	/tɜ:n ɒn/	v. 开(电灯)	NCE1 Lesson 029&30
turn off	/tɜ:n ɒf/	v. 关(电灯)	NCE1 Lesson 029&30
garden	/ˈɡɑːd(ə)n/	n. 花园	NCE1 Lesson 031&32
under	/ˈʌndə(r)/	prep. 在…之下	NCE1 Lesson 031&32
tree	/triː/	n. 树	NCE1 Lesson 031&32
climb	/klaɪm/	v. 爬，攀登	NCE1 Lesson 031&32
who	/huː/	pron. 谁	NCE1 Lesson 031&32
run	/rʌn/	v. 跑	NCE1 Lesson 031&32
grass	/ɡrɑːs/	n. 草，草地	NCE1 Lesson 031&32
after	/ˈɑːftə(r)/	prep. 在…之后	NCE1 Lesson 031&32
cat	/kæt/	n. 猫	NCE1 Lesson 031&32
type	/taɪp/	v. 打字	NCE1 Lesson 031&32
letter	/ˈletə(r)/	n. 信	NCE1 Lesson 031&32
basket	/ˈbɑːskɪt/	n. 篮子	NCE1 Lesson 031&32
basketball	/ˈbɑːskɪtbɔːl/	n. 篮球	NCE1 Lesson 031&32
eat	/iːt/	v. 吃	NCE1 Lesson 031&32
bone	/bəʊn/	n. 骨头	NCE1 Lesson 031&32
tooth	/tuːθ/	n. 牙齿	NCE1 Lesson 031&32
milk	/mɪlk/	n. 牛奶	NCE1 Lesson 031&32
meal	/miːl/	n. 饭，一顿饭	NCE1 Lesson 031&32
drink	/drɪŋk/	v. 喝	NCE1 Lesson 031&32
tap	/tæp/	n. (水)龙头	NCE1 Lesson 031&32
across	/əˈkrɒs/	prep. (从平面)穿过；prep. (平面)跨越；prep. 横过	NCE1 Lesson 031&32, NCE1 Lesson 033&34, NCE1 Lesson 035&36
day	/deɪ/	n. 日子	NCE1 Lesson 033&34
daily	/ˈdeɪli/	adj. 每天的	NCE1 Lesson 033&34
fly	/flaɪ/	v. 飞；v. 飞行	NCE1 Lesson 033&34, NCE1 Lesson 093&94
cloud	/klaʊd/	n. 云	NCE1 Lesson 033&34
sky	/skaɪ/	n. 天空	NCE1 Lesson 033&34
sun	/sʌn/	n. 太阳	NCE1 Lesson 033&34
shine	/ʃaɪn/	v. 照耀	NCE1 Lesson 033&34
with	/wɪð/	prep. 和…在一起	NCE1 Lesson 033&34
family	/ˈfæməli/	n. 家庭(成员)	NCE1 Lesson 033&34
walk	/wɔːk/	v. 走路，步行	NCE1 Lesson 033&34
hike	/haɪk/	v. 徒步	NCE1 Lesson 033&34
over	/ˈəʊvə(r)/	prep. 跨越，在…之上	NCE1 Lesson 033&34
bridge	/brɪdʒ/	n. 桥	NCE1 Lesson 033&34
boat	/bəʊt/	n. 船	NCE1 Lesson 033&34
ship	/ʃɪp/	n. 轮船	NCE1 Lesson 033&34
river	/ˈrɪvə(r)/	n. 河	NCE1 Lesson 033&34
aeroplane	/ˈeərəpleɪn/	n. 飞机(英)	NCE1 Lesson 033&34
airplane	/ˈeəpleɪn/	n. 飞机(美)	NCE1 Lesson 033&34
sleep	/sliːp/	v. 睡觉	NCE1 Lesson 033&34
asleep	/əˈsliːp/	adj. 睡着的；adj. 睡觉，睡着(用作表语)	NCE1 Lesson 033&34, NCE1 Lesson 115&116
shave	/ʃeɪv/	v. 刮脸	NCE1 Lesson 033&34
cry	/kraɪ/	v. 哭，喊	NCE1 Lesson 033&34
wash	/wɒʃ/	v. 洗	NCE1 Lesson 033&34
wait	/weɪt/	v. 等	NCE1 Lesson 033&34
jump	/dʒʌmp/	v. 跳	NCE1 Lesson 033&34
photograph	/ˈfəʊtəɡrɑːf/	n. 照片	NCE1 Lesson 035&36
village	/ˈvɪlɪdʒ/	n. 村庄	NCE1 Lesson 035&36
valley	/ˈvæli/	n. 山谷	NCE1 Lesson 035&36
country	/ˈkʌntri/	n. 乡村；n. 国家	NCE1 Lesson 035&36, NCE1 Lesson 051&52, NCE1 Lesson 067&68
between	/bɪˈtwiːn/	prep. 在…之间	NCE1 Lesson 035&36
hill	/hɪl/	n. 小山	NCE1 Lesson 035&36
mountain	/ˈmaʊntən/	n. 大山	NCE1 Lesson 035&36
another	/əˈnʌðə(r)/	prep. 另一个	NCE1 Lesson 035&36
other	/ˈʌðə(r)/	prep. 另外的	NCE1 Lesson 035&36
wife	/waɪf/	n. 妻子	NCE1 Lesson 035&36
along	/əˈlɒŋ/	prep. 沿着	NCE1 Lesson 035&36
belong	/bɪˈlɒŋ/	v. 属于	NCE1 Lesson 035&36, NCE1 Lesson 097&98
bank	/bæŋk/	n. 河岸	NCE1 Lesson 035&36
water	/ˈwɔːtə(r)/	n. 水；v. 浇水	NCE1 Lesson 035&36, NCE1 Lesson 125&126
swim	/swɪm/	v. 游泳	NCE1 Lesson 035&36
building	/ˈbɪldɪŋ/	n. 大楼，建筑物	NCE1 Lesson 035&36
build	/bɪld/	v. 建设	NCE1 Lesson 035&36
park	/pɑːk/	n. 公园	NCE1 Lesson 035&36
parking	/ˈpɑːkɪŋ/	n. 停车场	NCE1 Lesson 035&36
into	/ˈɪntuː/	prep. 进入	NCE1 Lesson 035&36
enter	/ˈentə(r)/	v. 进入	NCE1 Lesson 035&36, NCE1 Lesson 119&120
beside	/bɪˈsaɪd/	prep. 在…旁	NCE1 Lesson 035&36
off	/ɒf/	prep. 离开	NCE1 Lesson 035&36
work	/wɜːk/	v. 工作	NCE1 Lesson 037&38, NCE1 Lesson 123&124
hard	/hɑːd/	adv. 努力地；adj. 硬的	NCE1 Lesson 037&38, NCE1 Lesson 103&104
bookcase	/ˈbʊkkeɪs/	n. 书橱，书架	NCE1 Lesson 037&38
hammer	/ˈhæmə(r)/	n. 锤子	NCE1 Lesson 037&38
paint	/peɪnt/	v. 上漆，涂	NCE1 Lesson 037&38
pink	/pɪŋk/	adj. 粉红色	NCE1 Lesson 037&38
favourite	/ˈfeɪvərɪt/	adj. 最喜欢的(英)	NCE1 Lesson 037&38
favorite	/ˈfeɪvərɪt/	adj. 最喜欢的(美)	NCE1 Lesson 037&38
homework	/ˈhəʊmwɜːk/	n. 作业	NCE1 Lesson 037&38
listen	/ˈlɪs(ə)n/	v. 听	NCE1 Lesson 037&38
front	/frʌnt/	n. 前面	NCE1 Lesson 039&40
in front of	/ɪn frʌnt əv/	prep. 在…之前	NCE1 Lesson 039&40
careful	/ˈkeəf(ə)l/	adj. 小心的，仔细的	NCE1 Lesson 039&40
be careful	/bi ˈkeəf(ə)l/	v. 小心	NCE1 Lesson 039&40
drop	/drɒp/	v. 掉下	NCE1 Lesson 039&40
vase	/vɑːz/	n. 花瓶	NCE1 Lesson 039&40
flower	/ˈflaʊə(r)/	n. 花	NCE1 Lesson 039&40
show	/ʃəʊ/	v. 给…看	NCE1 Lesson 039&40
send	/send/	v. 送给	NCE1 Lesson 039&40
post	/pəʊst/	v. 寄送	NCE1 Lesson 039&40
take	/teɪk/	v. 带给	NCE1 Lesson 039&40
bring	/brɪŋ/	v. 带来；v. 带来，送来	NCE1 Lesson 039&40, NCE1 Lesson 087&88
cheese	/tʃiːz/	n. 乳酪，干酪	NCE1 Lesson 041&42
bread	/bred/	n. 面包	NCE1 Lesson 041&42
soap	/səʊp/	n. 肥皂	NCE1 Lesson 041&42
chocolate	/ˈtʃɒklət/	n. 巧克力	NCE1 Lesson 041&42
sugar	/ˈʃʊɡə(r)/	n. 糖	NCE1 Lesson 041&42
candy	/ˈkændi/	n. 糖果	NCE1 Lesson 041&42
coffee	/ˈkɒfi/	n. 咖啡	NCE1 Lesson 041&42
caffeine	/ˈkæfiːn/	n. 咖啡因	NCE1 Lesson 041&42
tea	/tiː/	n. 茶	NCE1 Lesson 041&42
tobacco	/təˈbækəʊ/	n. 烟草，烟丝	NCE1 Lesson 041&42
smoking	/ˈsməʊkɪŋ/	n. 吸烟	NCE1 Lesson 041&42
loaf	/ləʊf/	n. 块	NCE1 Lesson 041&42
certainly	/ˈsɜːt(ə)nli/	adv. 当然	NCE1 Lesson 041&42, NCE1 Lesson 063&64
bird	/bɜːd/	n. 鸟	NCE1 Lesson 041&42
any	/ˈeni/	adv. 一些；pron. 任何一个	NCE1 Lesson 041&42, NCE1 Lesson 115&116
some	/sʌm/	adv. 一些；adv. 若干	NCE1 Lesson 041&42, NCE1 Lesson 115&116
of course	/ɒv kɔːs/	adv. 当然	NCE1 Lesson 043&44
kettle	/ˈket(ə)l/	n. 水壶	NCE1 Lesson 043&44
teapot	/ˈtiːpɒt/	n. 茶壶	NCE1 Lesson 043&44
behind	/bɪˈhaɪnd/	prep. 在…后面；prep. 在…之后	NCE1 Lesson 043&44, NCE1 Lesson 069&70
now	/naʊ/	adv. 现在，此刻	NCE1 Lesson 043&44
find	/faɪnd/	v. 找到	NCE1 Lesson 043&44
boil	/bɔɪl/	v. 沸腾，开	NCE1 Lesson 043&44
look for	/ˈlʊk fɔː(r)/	v. 寻找	NCE1 Lesson 043&44
oil	/ɔɪl/	n. 油	NCE1 Lesson 043&44
pot	/pɒt/	n. 锅	NCE1 Lesson 043&44
hot pot	/hɒt pɒt/	n. 火锅	NCE1 Lesson 043&44
can	/kæn/	v. 能够	NCE1 Lesson 045&46
boss	/bɒs/	n. 老板，上司	NCE1 Lesson 045&46
minute	/ˈmɪnɪt/	n. 分(钟)	NCE1 Lesson 045&46
second	/ˈsekənd/	n. 秒	NCE1 Lesson 045&46
hour	/ˈaʊə(r)/	n. 时	NCE1 Lesson 045&46
ask	/ɑːsk/	v. 请求，要求	NCE1 Lesson 045&46
handwriting	/ˈhændraɪtɪŋ/	n. 书写	NCE1 Lesson 045&46
terrible	/ˈterəb(ə)l/	adj. 糟糕的，可怕的	NCE1 Lesson 045&46
answer	/ˈɑːnsə(r)/	v. 回答；v. 接(电话)	NCE1 Lesson 045&46, NCE1 Lesson 071&72, NCE1 Lesson 103&104
lift	/lɪft/	v. 拿起，搬起，举起	NCE1 Lesson 045&46
cake	/keɪk/	n. 饼，蛋糕	NCE1 Lesson 045&46
biscuit	/ˈbɪskɪt/	n. 饼干	NCE1 Lesson 045&46
like	/laɪk/	v. 喜欢，想要	NCE1 Lesson 047&48
want	/wɒnt/	v. 想	NCE1 Lesson 047&48
fresh	/freʃ/	adj. 新鲜的	NCE1 Lesson 047&48, NCE1 Lesson 103&104
egg	/eɡ/	n. 鸡蛋	NCE1 Lesson 047&48
butter	/ˈbʌtə(r)/	n. 黄油	NCE1 Lesson 047&48
pure	/pjʊə(r)/	adj. 纯净的	NCE1 Lesson 047&48
honey	/ˈhʌni/	n. 蜂蜜	NCE1 Lesson 047&48
ripe	/raɪp/	adj. 成熟的	NCE1 Lesson 047&48
banana	/bəˈnɑːnə/	n. 香蕉	NCE1 Lesson 047&48
jam	/dʒæm/	n. 果酱	NCE1 Lesson 047&48
sweet	/swiːt/	adj. 甜的	NCE1 Lesson 047&48, NCE1 Lesson 103&104
scotch whisky	/skɒtʃ ˈwɪski/	n. 苏格兰威士忌	NCE1 Lesson 047&48
Scotch	/skɒtʃ/	n. 苏格兰	NCE1 Lesson 047&48
whisky	/ˈwɪski/	n. 威士忌	NCE1 Lesson 047&48
choice	/tʃɔɪs/	adj. 上等的，精选的	NCE1 Lesson 047&48
apple	/ˈæp(ə)l/	n. 苹果	NCE1 Lesson 047&48
wine	/waɪn/	n. 酒，果酒	NCE1 Lesson 047&48
beer	/bɪə(r)/	n. 啤酒	NCE1 Lesson 047&48
blackboard	/ˈblækbɔːd/	n. 黑板	NCE1 Lesson 047&48
liquor	/ˈlɪkə(r)/	n. 烈性酒	NCE1 Lesson 047&48
alcohol	/ˈælkəhɒl/	n. 酒精	NCE1 Lesson 047&48
butcher	/ˈbʊtʃə(r)/	n. 卖肉的	NCE1 Lesson 049&50
meat	/miːt/	n. (食用)肉	NCE1 Lesson 049&50
mince	/mɪns/	n. 肉馅，绞肉	NCE1 Lesson 049&50
beef	/biːf/	n. 牛肉	NCE1 Lesson 049&50
steak	/steɪk/	n. 牛排	NCE1 Lesson 049&50
lamb	/læm/	n. 羔羊肉	NCE1 Lesson 049&50
mutton	/ˈmʌtn/	n. 羊肉	NCE1 Lesson 049&50
husband	/ˈhʌzbənd/	n. 丈夫	NCE1 Lesson 049&50
chicken	/ˈtʃɪkɪn/	n. 鸡	NCE1 Lesson 049&50
tell	/tel/	v. 告诉	NCE1 Lesson 049&50
truth	/truːθ/	n. 实情	NCE1 Lesson 049&50
either	/ˈaɪðə(r)/	adv. 也(用于否定句)；adv. （两者之中）任意一个	NCE1 Lesson 049&50, NCE1 Lesson 113&114
neither	/ˈnaɪðə(r)/	adv. 也不（用于否定句后)；adv. 也不	NCE1 Lesson 049&50, NCE1 Lesson 113&114
tomato	/təˈmɑːtəʊ/	n. 西红柿	NCE1 Lesson 049&50
potato	/pəˈteɪtəʊ/	n. 土豆	NCE1 Lesson 049&50
cabbage	/ˈkæbɪdʒ/	n. 卷心菜	NCE1 Lesson 049&50
lettuce	/ˈletɪs/	n. 莴苣	NCE1 Lesson 049&50
pea	/piː/	n. 豌豆	NCE1 Lesson 049&50
bean	/biːn/	n. 豆角	NCE1 Lesson 049&50
pear	/peə(r)/	n. 梨	NCE1 Lesson 049&50
grape	/ɡreɪp/	n. 葡萄	NCE1 Lesson 049&50
peach	/piːtʃ/	n. 桃	NCE1 Lesson 049&50
Greece	/ɡriːs/	n. 希腊	NCE1 Lesson 051&52
climate	/ˈklaɪmət/	n. 气候	NCE1 Lesson 051&52
pleasant	/ˈplez(ə)nt/	adj. 宜人的	NCE1 Lesson 051&52
weather	/ˈweðə(r)/	n. 天气	NCE1 Lesson 051&52
spring	/sprɪŋ/	n. 春季	NCE1 Lesson 051&52
windy	/ˈwɪndi/	adj. 有风的	NCE1 Lesson 051&52
warm	/wɔːm/	adj. 温暖的	NCE1 Lesson 051&52
rain	/reɪn/	v. 下雨	NCE1 Lesson 051&52
sometimes	/ˈsʌmtaɪmz/	adv. 有时	NCE1 Lesson 051&52
summer	/ˈsʌmə(r)/	n. 夏天	NCE1 Lesson 051&52
autumn	/ˈɔːtəm/	n. 秋天	NCE1 Lesson 051&52
winter	/ˈwɪntə(r)/	n. 冬天	NCE1 Lesson 051&52
snow	/snəʊ/	v. 下雪	NCE1 Lesson 051&52
January	/ˈdʒænjuəri/	n. 一月	NCE1 Lesson 051&52
February	/ˈfebruəri/	n. 二月	NCE1 Lesson 051&52
march	/mɑːtʃ/	n. 三月	NCE1 Lesson 051&52
April	/ˈeɪprəl/	n. 四月	NCE1 Lesson 051&52
may	/may/	n. 五月(May)；v. (用于请求许可)可以	NCE1 Lesson 051&52, NCE1 Lesson 089&90
June	/dʒuːn/	n. 六月	NCE1 Lesson 051&52
July	/dʒʊˈlaɪ/	n. 七月	NCE1 Lesson 051&52
August	/ɔːˈɡʌst/	n. 八月	NCE1 Lesson 051&52
September	/sepˈtembə(r)/	n. 九月	NCE1 Lesson 051&52
October	/ɒkˈtəʊbə(r)/	n. 十月	NCE1 Lesson 051&52
November	/nəʊˈvembə(r)/	n. 十一月	NCE1 Lesson 051&52
December	/dɪˈsembə(r)/	n. 十二月	NCE1 Lesson 051&52
the U.S.	/ði ju:ˈes/	n. 美国	NCE1 Lesson 051&52
Brazil	/brəˈzɪl/	n. 巴西	NCE1 Lesson 051&52
France	/frɑːns/	n. 法国	NCE1 Lesson 051&52
Germany	/ˈdʒɜːməni/	n. 德国	NCE1 Lesson 051&52
Norway	/ˈnɔːweɪ/	n. 挪威	NCE1 Lesson 051&52
Spain	/speɪn/	n. 西班牙	NCE1 Lesson 051&52
mild	/maɪld/	adj. 温和的，温暖的	NCE1 Lesson 053&54
always	/ˈɔːlweɪz/	adv. 总是	NCE1 Lesson 053&54
north	/nɔːθ/	n. 北方	NCE1 Lesson 053&54
south	/saʊθ/	n. 南方	NCE1 Lesson 053&54
east	/iːst/	n. 东方	NCE1 Lesson 053&54
west	/west/	n. 西方	NCE1 Lesson 053&54
wet	/wet/	adj. 潮湿的	NCE1 Lesson 053&54
season	/ˈsiːz(ə)n/	n. 季节	NCE1 Lesson 053&54
best	/best/	adv. 最；adj. (good的最高级)最好的	NCE1 Lesson 053&54, NCE1 Lesson 109&110
night	/naɪt/	n. 夜晚；n. 夜间	NCE1 Lesson 053&54, NCE1 Lesson 055&56
rise	/raɪz/	v. 升起	NCE1 Lesson 053&54
set	/set/	v. (太阳)落下去	NCE1 Lesson 053&54
early	/ˈɜːrli/	adv. 早	NCE1 Lesson 053&54
late	/leɪt/	adv. 晚，迟	NCE1 Lesson 053&54
interesting	/ˈɪntrəstɪŋ/	adj. 有趣的，有意思的	NCE1 Lesson 053&54
subject	/ˈsʌbdʒɪkt/	n. 话题	NCE1 Lesson 053&54
conversation	/ˌkɒnvəˈseɪʃ(ə)n/	n. 谈话	NCE1 Lesson 053&54
Australia	/ɒˈstreɪliə/	n. 澳大利亚	NCE1 Lesson 053&54
Australian	/ɒˈstreɪliən/	n. 澳大利亚人	NCE1 Lesson 053&54
Austria	/ˈɒstriə/	n. 奥地利	NCE1 Lesson 053&54
Austrian	/ˈɒstriən/	n. 奥地利人	NCE1 Lesson 053&54
Canada	/ˈkænədə/	n. 加拿大	NCE1 Lesson 053&54
Canadian	/kəˈneɪdiən/	n. 加拿大人	NCE1 Lesson 053&54
China	/ˈtʃaɪnə/	n. 中国	NCE1 Lesson 053&54
Finland	/ˈfɪnlənd/	n. 芬兰	NCE1 Lesson 053&54
Finnish	/ˈfɪnɪʃ/	n. 芬兰人	NCE1 Lesson 053&54
India	/ˈɪndiə/	n. 印度	NCE1 Lesson 053&54
Indian	/ˈɪndiən/	n. 印度人	NCE1 Lesson 053&54
Japan	/dʒə'pæn/	n. 日本	NCE1 Lesson 053&54
Nigeria	/naɪˈdʒɪəriə/	n. 尼日利亚	NCE1 Lesson 053&54
Nigerian	/naɪˈdʒɪəriən/	n. 尼日利亚人	NCE1 Lesson 053&54
Turkey	/ˈtɜːki/	n. 土耳其	NCE1 Lesson 053&54
Turkish	/ˈtɜːkɪʃ/	n. 土耳其人	NCE1 Lesson 053&54
Korea	/kəˈriə/	n. 韩国	NCE1 Lesson 053&54
Polish	/ˈpəʊlɪʃ/	n. 波兰人	NCE1 Lesson 053&54
Poland	/ˈpəʊlənd/	n. 波兰	NCE1 Lesson 053&54
Thai	/taɪ/	n. 泰国人	NCE1 Lesson 053&54
Thailand	/ˈtaɪlænd/	n. 泰国	NCE1 Lesson 053&54
live	/lɪv/	v. 住，生活	NCE1 Lesson 055&56
love	/lʌv/	v. 爱	NCE1 Lesson 055&56
lie	/laɪ/	v. 躺	NCE1 Lesson 055&56
living	/ˈlɪvɪŋ/	n. 生活	NCE1 Lesson 055&56
stay	/steɪ/	v. 呆在，停留	NCE1 Lesson 055&56
say	/seɪ/	v. 说	NCE1 Lesson 055&56, NCE1 Lesson 071&72
home	/həʊm/	adv. 在家，到家	NCE1 Lesson 055&56
lunch	/lʌntʃ/	n. 午饭	NCE1 Lesson 055&56
afternoon	/ˌɑːftəˈnuːn/	n. 下午	NCE1 Lesson 055&56
usually	/ˈjuːʒuəli/	adv. 通常	NCE1 Lesson 055&56
together	/təˈɡeðə(r)/	adv. 一起	NCE1 Lesson 055&56
evening	/ˈiːvnɪŋ/	n. 晚上	NCE1 Lesson 055&56
arrive	/əˈraɪv/	v. 到达	NCE1 Lesson 055&56
depart	/dɪˈpɑːt/	v. 出发	NCE1 Lesson 055&56
o'clock	/əˈklɒk/	adv. 点钟	NCE1 Lesson 057&58
clock	/klɒk/	n. 时钟	NCE1 Lesson 057&58
shop	/ʃɒp/	n. 商店	NCE1 Lesson 057&58
mall	/mɔːl; mɑːl/	n. 商场	NCE1 Lesson 057&58
store	/stɔː(r)/	n. 仓储商店	NCE1 Lesson 057&58
moment	/ˈməʊmənt/	n. 片刻，瞬间	NCE1 Lesson 057&58
envelope	/ˈenvələʊp/	n. 信封	NCE1 Lesson 059&60
writing paper	/ˈraɪtɪŋ peɪpə(r)/	n. 信纸	NCE1 Lesson 059&60
writing	/ˈraɪtɪŋ/	n. 写作	NCE1 Lesson 059&60
paper	/ˈpeɪpə/	n. 纸；n. 考卷	NCE1 Lesson 059&60, NCE1 Lesson 103&104
page	/peɪdʒ/	n. 页码	NCE1 Lesson 059&60
shop assistant	/ˈʃɒp əsɪstənt/	n. 售货员	NCE1 Lesson 059&60
size	/saɪz/	n. 尺寸，尺码，大小	NCE1 Lesson 059&60
pad	/pæd/	n. 信笺簿	NCE1 Lesson 059&60
glue	/ɡluː/	n. 胶水	NCE1 Lesson 059&60
chalk	/tʃɔːk/	n. 粉笔	NCE1 Lesson 059&60
change	/tʃeɪndʒ/	n. 零钱，找给的钱；v. 兑换(钱)	NCE1 Lesson 059&60, NCE1 Lesson 113&114
feel	/fiːl/	v. 感觉	NCE1 Lesson 061&62
look	/lʊk/	v. 看(起来)	NCE1 Lesson 061&62
call	/kɔːl/	v. 叫，请	NCE1 Lesson 061&62
doctor	/ˈdɒktə(r)/	n. 医生	NCE1 Lesson 061&62
telephone	/ˈtelɪfəʊn/	n. 电话；v. 打电话	NCE1 Lesson 061&62, NCE1 Lesson 071&72
remember	/rɪˈmembə(r)/	v. 记得，记住	NCE1 Lesson 061&62
mouth	/maʊθ/	n. 嘴	NCE1 Lesson 061&62, NCE1 Lesson 117&118
tongue	/tʌŋ/	n. 舌头	NCE1 Lesson 061&62
bad	/bæd/	adj. 坏的，严重的	NCE1 Lesson 061&62
news	/njuːz/	n. 消息	NCE1 Lesson 061&62
headache	/ˈhedeɪk/	n. 头痛	NCE1 Lesson 061&62
aspirin	/ˈæsprɪn/	n. 阿斯匹林	NCE1 Lesson 061&62
earache	/ˈɪəreɪk/	n. 耳痛	NCE1 Lesson 061&62
toothache	/ˈtuːθeɪk/	n. 牙痛	NCE1 Lesson 061&62
dentist	/ˈdentɪst/	n. 牙医	NCE1 Lesson 061&62
stomach ache	/ˈstʌmək eɪk/	n. 胃痛	NCE1 Lesson 061&62
medicine	/ˈmedsn/	n. 药	NCE1 Lesson 061&62
temperature	/ˈtemprətʃə(r)/	n. 温度	NCE1 Lesson 061&62
flu	/fluː/	n. 流行性感冒	NCE1 Lesson 061&62
measles	/ˈmiːz(ə)lz/	n. 麻疹	NCE1 Lesson 061&62
mumps	/mʌmps/	n. 腮腺炎	NCE1 Lesson 061&62
pharmacy	/ˈfɑːməsi/	n. 药店	NCE1 Lesson 061&62
better	/ˈbetə(r)/	adj. 形容词well的比较级	NCE1 Lesson 063&64
get up	/ɡet ʌp/	v. 起床	NCE1 Lesson 063&64
yet	/jet/	adv. 还，仍	NCE1 Lesson 063&64
rich	/rɪtʃ/	adj. 油腻的	NCE1 Lesson 063&64
food	/fuːd/	n. 食物	NCE1 Lesson 063&64
remain	/rɪˈmeɪn/	v. 保持，继续	NCE1 Lesson 063&64
play	/pleɪ/	v. 玩	NCE1 Lesson 063&64
match	/mætʃ/	n. 火柴	NCE1 Lesson 063&64
talk	/tɔːk/	v. 谈话	NCE1 Lesson 063&64
library	/ˈlaɪbrəri/	n. 图书馆	NCE1 Lesson 063&64
drive	/draɪv/	v. 开车	NCE1 Lesson 063&64
so	/səʊ/	adv. 如此地	NCE1 Lesson 063&64
quickly	/ˈkwɪkli/	adv. 快地	NCE1 Lesson 063&64
lean out of	/liːn aʊt ɒv/	v. 身体探出	NCE1 Lesson 063&64
break	/breɪk/	v. 打破	NCE1 Lesson 063&64
dad	/dæd/	n. 爸(儿语)	NCE1 Lesson 065&66
key	/kiː/	n. 钥匙	NCE1 Lesson 065&66
baby	/ˈbeɪbi/	n. 婴儿	NCE1 Lesson 065&66
hear	/hɪə(r)/	v. 听见	NCE1 Lesson 065&66
enjoy	/ɪnˈdʒɔɪ/	v. 玩得快活	NCE1 Lesson 065&66
yourself	/jɔːˈself/	pron. 你自己	NCE1 Lesson 065&66
ourselves	/ɑːˈselvz/	pron. 我们自己	NCE1 Lesson 065&66
myself	/maɪˈself/	pron. 我自己	NCE1 Lesson 065&66
themselves	/ðəmˈselvz/	pron. 他们自己	NCE1 Lesson 065&66
himself	/hɪmˈself/	pron. 他自己	NCE1 Lesson 065&66
herself	/hɜːˈself/	pron. 她自己	NCE1 Lesson 065&66
greengrocer	/ˈɡriːnɡrəʊsə(r)/	n. 蔬菜水果零售商	NCE1 Lesson 067&68
grocer	/ˈɡrəʊsə/	n. 食品杂货商	NCE1 Lesson 067&68
absent	/ˈæbsənt/	adj. 缺席的	NCE1 Lesson 067&68
Monday	/ˈmʌndeɪ/	n. 星期一	NCE1 Lesson 067&68
Tuesday	/ˈtjuːzdeɪ/	n. 星期二	NCE1 Lesson 067&68
Wednesday	/ˈwenzdeɪ/	n. 星期三	NCE1 Lesson 067&68
Thursday	/ˈθɜːzdeɪ/	n. 星期四	NCE1 Lesson 067&68
Friday	/ˈfraɪdeɪ/	n. 星期五	NCE1 Lesson 067&68
Saturday	/ˈsætədeɪ/	n. 星期六	NCE1 Lesson 067&68
Sunday	/ˈsʌndeɪ/	n. 星期日	NCE1 Lesson 067&68
keep	/kiːp/	v. (身体健康)处于(状况)	NCE1 Lesson 067&68
spend	/spend/	v. .度过	NCE1 Lesson 067&68
weekend	/ˌwiːkˈend/	n. 周末	NCE1 Lesson 067&68
weekday	/ˈwiːkdeɪ/	n. 平日	NCE1 Lesson 067&68
lucky	/ˈlʌki/	adj. 幸运的	NCE1 Lesson 067&68
church	/tʃɜːtʃ/	n. 教堂	NCE1 Lesson 067&68
dairy	/ˈdeəri/	n. 乳品店	NCE1 Lesson 067&68
baker	/ˈbeɪkə(r)/	n. 面包师傅	NCE1 Lesson 067&68
year	/jɪə(r)/	n. 年	NCE1 Lesson 069&70
race	/reɪs/	n. 比赛	NCE1 Lesson 069&70
town	/taʊn/	n. 城镇	NCE1 Lesson 069&70
down town	/daʊn taʊn/	n. 市中心	NCE1 Lesson 069&70
crowd	/kraʊd/	n. 人群	NCE1 Lesson 069&70
stand	/stænd/	v. 站立	NCE1 Lesson 069&70
exciting	/ɪkˈsaɪtɪŋ/	adj. 使人激动的	NCE1 Lesson 069&70
exit	/ˈeksɪt/	n. 出口	NCE1 Lesson 069&70
just	/dʒʌst/	adv. 正好，恰好	NCE1 Lesson 069&70
finish	/ˈfɪnɪʃ/	n. 结尾，结束	NCE1 Lesson 069&70
done	/dʌn/	v. 完成	NCE1 Lesson 069&70
winner	/ˈwɪnə(r)/	n. 获胜者；n. 赢家	NCE1 Lesson 069&70, NCE1 Lesson 137&138
way	/weɪ/	n. 路途	NCE1 Lesson 069&70
stationer	/ˈsteɪʃənə(r)/	n. 文具店	NCE1 Lesson 069&70
awful	/ˈɔːf(ə)l/	adj. 让人讨厌的，坏的	NCE1 Lesson 071&72
awfully	/ˈɔːfli/	adv. 可怕地	NCE1 Lesson 071&72
time	/taɪm/	n. 次(数)	NCE1 Lesson 071&72
last	/lɑːst/	adv. 最后的，前一次的	NCE1 Lesson 071&72
phone	/fəʊn/	n. 电话(=telephone)	NCE1 Lesson 071&72
again	/əˈɡen/	adv. 又一次地	NCE1 Lesson 071&72
week	/wiːk/	n. 周	NCE1 Lesson 073&74
London	/ˈlʌndən/	n. 伦敦	NCE1 Lesson 073&74
suddenly	/ˈsʌd(ə)nli/	adv. 突然地	NCE1 Lesson 073&74
bus stop	/ˈbʌs stɒp/	n. 公共汽车车站	NCE1 Lesson 073&74
bus	/bʌs/	n. 公共汽车	NCE1 Lesson 073&74
stop	/stɒp/	v. 停	NCE1 Lesson 073&74
smile	/smaɪl/	v. 微笑	NCE1 Lesson 073&74, NCE1 Lesson 141&142
pleasantly	/ˈplezntli/	adv. 愉快地	NCE1 Lesson 073&74
understand	/ˌʌndəˈstænd/	v. 懂，明白	NCE1 Lesson 073&74
speak	/spiːk/	v. 讲，说	NCE1 Lesson 073&74
hand	/hænd/	n. 手	NCE1 Lesson 073&74
pocket	/ˈpɒkɪt/	n. 衣袋	NCE1 Lesson 073&74
phrasebook	/ˈfreizbuk/	n. 短语手册，常用语手册	NCE1 Lesson 073&74
phrase	/freɪz/	n. 短语	NCE1 Lesson 073&74
slowly	/ˈsləʊli/	adv. 缓慢地	NCE1 Lesson 073&74
hurriedly	/ˈhʌrɪdli/	adv. 匆忙地	NCE1 Lesson 073&74
cut	/kʌt/	v. 割，切	NCE1 Lesson 073&74
thirstily	/ˈθɜːstɪli/	adv. 口渴地	NCE1 Lesson 073&74
go	/ɡəʊ/	v. 走	NCE1 Lesson 073&74
greet	/ɡriːt/	v. 问候，打招呼	NCE1 Lesson 073&74
ago	/əˈɡəʊ/	adv. 以前	NCE1 Lesson 075&76
buy	/baɪ/	v. 买	NCE1 Lesson 075&76
pair	/peə(r)/	n. 双，对	NCE1 Lesson 075&76
fashion	/ˈfæʃ(ə)n/	n. (服装的)流行式样	NCE1 Lesson 075&76
uncomfortable	/ʌnˈkʌmftəb(ə)l/	adj. 不舒服的	NCE1 Lesson 075&76
comfortable	/ˈkʌmftəb(ə)l/	adj. 舒适的	NCE1 Lesson 075&76
wear	/weə(r)/	v. 穿着 wore	NCE1 Lesson 075&76
appointment	/əˈpɔɪntmənt/	n. 约会，预约	NCE1 Lesson 077&78
booking	/ˈbʊkɪŋ/	n. 预定	NCE1 Lesson 077&78
date	/deɪt/	n. 约会	NCE1 Lesson 077&78
till	/tɪl/	prep. 直到…为止	NCE1 Lesson 077&78
still	/stɪl/	adv. 仍然；adv. 还，仍旧	NCE1 Lesson 077&78, NCE1 Lesson 091&92
until	/ʌnˈtɪl/	prep. 直到……为止	NCE1 Lesson 077&78
til	/tɪl/	n. 芝麻	NCE1 Lesson 077&78
urgent	/ˈɜːdʒənt/	adj. 紧急的，急迫的	NCE1 Lesson 077&78
shopping	/ˈʃɒpɪŋ/	n. 购物	NCE1 Lesson 079&80
list	/lɪst/	n. 单子	NCE1 Lesson 079&80
vegetable	/ˈvedʒtəb(ə)l/	n. 蔬菜	NCE1 Lesson 079&80
need	/niːd/	v. 需要	NCE1 Lesson 079&80
hope	/həʊp/	v. 希望	NCE1 Lesson 079&80
thing	/θɪŋ/	n. 事情	NCE1 Lesson 079&80
money	/ˈmʌni/	n. 钱	NCE1 Lesson 079&80
groceries	/ˈɡrəʊsəriz/	n. 食品杂货	NCE1 Lesson 079&80
fruit	/fruːt/	n. 水果	NCE1 Lesson 079&80
stationery	/ˈsteɪʃənri/	n. 文具	NCE1 Lesson 079&80
newsagent	/ˈnjuːzeɪdʒənt/	n. 报刊零售人	NCE1 Lesson 079&80
chemist	/ˈkemɪst/	n. 化剂师，化学家	NCE1 Lesson 079&80
bath	/bɑːθ/	n. 洗澡	NCE1 Lesson 081&82
birth	/bɜːθ/	n. 出生	NCE1 Lesson 081&82
nearly	/ˈnɪəli/	adv. 几乎，将近	NCE1 Lesson 081&82
ready	/ˈredi/	adj. 准备好的，完好的；adj. 准备好的	NCE1 Lesson 081&82, NCE1 Lesson 083&84
dinner	/ˈdɪnə(r)/	n. 正餐，晚餐	NCE1 Lesson 081&82
supper	/ˈsʌpə(r)/	n. 晚餐	NCE1 Lesson 081&82
restaurant	/ˈrestrɒnt/	n. 饭馆，餐馆	NCE1 Lesson 081&82
roast	/rəʊst/	n. 烤的	NCE1 Lesson 081&82
grill	/ɡrɪl/	n. 烧烤	NCE1 Lesson 081&82
barbecue	/ˈbɑːbɪkjuː/	n. 烤肉	NCE1 Lesson 081&82
breakfast	/ˈbrekfəst/	n. 早餐	NCE1 Lesson 081&82
fast	/fɑːst/	n. 斋戒	NCE1 Lesson 081&82
haircut	/ˈheəkʌt/	n. 理发	NCE1 Lesson 081&82
party	/ˈpɑːti/	n. 聚会	NCE1 Lesson 081&82
holiday	/ˈhɒlədeɪ/	n. 假日	NCE1 Lesson 081&82
vacation	/veɪˈkeɪʃ(ə)n/	n. 假期	NCE1 Lesson 081&82
mess	/mes/	n. 杂乱，凌乱	NCE1 Lesson 083&84
disorderly	/dɪsˈɔːdəli/	adj. 混乱的	NCE1 Lesson 083&84
pack	/pæk/	v. 包装，打包，装箱	NCE1 Lesson 083&84
pack up	/pæk ʌp/	v. 打包	NCE1 Lesson 083&84
suitcase	/ˈsuːtkeɪs/	n. 手提箱	NCE1 Lesson 083&84
leave	/liːv/	v. 离开；v. 遗留	NCE1 Lesson 083&84, NCE1 Lesson 097&98
already	/ɔːlˈredi/	adv. 已经	NCE1 Lesson 083&84
Paris	/ˈpærɪs/	n. 巴黎	NCE1 Lesson 085&86
cinema	/ˈsɪnəmə/	n. 电影院	NCE1 Lesson 085&86
film	/fɪlm/	n. 电影(英)	NCE1 Lesson 085&86
movie	/ˈmuːvi/	n. 电影(美)	NCE1 Lesson 085&86
beautiful	/ˈbjuːtɪf(ə)l/	adj. 漂亮的	NCE1 Lesson 085&86
beauty	/ˈbjuːti/	n. 美女	NCE1 Lesson 085&86
city	/ˈsɪti/	n. 城市	NCE1 Lesson 085&86, NCE1 Lesson 143&144
never	/ˈnevə(r)/	adv. 从来没有	NCE1 Lesson 085&86
ever	/ˈevə(r)/	adv. 在任何时候	NCE1 Lesson 085&86
attendant	/əˈtendənt/	n. 接待员	NCE1 Lesson 087&88
garage	/ˈɡærɑːʒ/	n. 车库，汽车修理厂	NCE1 Lesson 087&88
crash	/kræʃ/	n. 碰撞	NCE1 Lesson 087&88
lamp-post	/ˈlæmp pəʊst/	n. 灯杆	NCE1 Lesson 087&88
repair	/rɪˈpeə(r)/	v. 修理	NCE1 Lesson 087&88
try	/traɪ/	v. 努力，设法	NCE1 Lesson 087&88
lamp	/læmp/	n. 灯	NCE1 Lesson 087&88
believe	/bɪˈliːv/	v. 相信，认为	NCE1 Lesson 089&90
how long	/haʊ lɒŋ/	adv. 多长	NCE1 Lesson 089&90
since	/sɪns/	prep. 自从	NCE1 Lesson 089&90
why	/waɪ/	adv. 为什么	NCE1 Lesson 089&90
sell	/sel/	v. 卖，出售	NCE1 Lesson 089&90
because	/bɪˈkəz; bɪˈkɒz/	conj. 因为	NCE1 Lesson 089&90
retire	/rɪˈtaɪə(r)/	v. 退休	NCE1 Lesson 089&90
tire	/ˈtaɪə(r)/	n. 轮胎	NCE1 Lesson 089&90
cost	/kɒst/	v. 花费	NCE1 Lesson 089&90
pound	/paʊnd/	n. 英镑	NCE1 Lesson 089&90
worth	/wɜːθ/	prep. 值…钱	NCE1 Lesson 089&90
penny	/ˈpeni/	n. 便士	NCE1 Lesson 089&90, NCE1 Lesson 097&98
cent	/sent/	n. 分	NCE1 Lesson 089&90
move	/muːv/	v. 搬家	NCE1 Lesson 091&92
neighbour	/ˈneɪbə(r)/	n. 邻居(英)	NCE1 Lesson 091&92
neighbor	/ˈneɪbə(r)/	n. 邻居(美)	NCE1 Lesson 091&92
person	/ˈpɜːs(ə)n/	n. 人	NCE1 Lesson 091&92
people	/ˈpiːp(ə)l/	n. 人们	NCE1 Lesson 091&92
poor	/pɔː(r)/	adj. 可怜的；adj. 贫穷的	NCE1 Lesson 091&92, NCE1 Lesson 137&138
pilot	/ˈpaɪlət/	n. 飞行员	NCE1 Lesson 093&94
return	/rɪˈtɜːn/	v. 返回；n. 往返	NCE1 Lesson 093&94, NCE1 Lesson 095&96
New York	/njuː jɔːk/	n. 纽约	NCE1 Lesson 093&94
Tokyo	/ˈtəʊkiəʊ/	n. 东京	NCE1 Lesson 093&94
Madrid	/məˈdrɪd/	n. 马德里	NCE1 Lesson 093&94
Athens	/ˈæθɪnz/	n. 雅典	NCE1 Lesson 093&94
Berlin	/bɜːˈlɪn/	n. 柏林	NCE1 Lesson 093&94
Bahrain	/bɑːˈreɪn/	n. 巴林	NCE1 Lesson 093&94
Bombay	/bɒmˈbeɪ/	n. 孟买	NCE1 Lesson 093&94
Geneva	/dʒɪˈniːvə/	n. 日内瓦	NCE1 Lesson 093&94
Moscow	/ˈmɒskəʊ/	n. 莫斯科	NCE1 Lesson 093&94
Rome	/rəʊm/	n. 罗马	NCE1 Lesson 093&94
Seoul	/səʊl/	n. 汉城	NCE1 Lesson 093&94
Stockholm	/ˈstɒkhəʊm/	n. 斯德哥尔摩	NCE1 Lesson 093&94
Sydney	/ˈsɪdni/	n. 悉尼	NCE1 Lesson 093&94
train	/treɪn/	n. 火车	NCE1 Lesson 095&96
platform	/ˈplætfɔːm/	n. 站台	NCE1 Lesson 095&96
station	/ˈsteɪʃ(ə)n/	n. 车站，火车站	NCE1 Lesson 095&96
plenty	/ˈplenti/	n. 大量	NCE1 Lesson 095&96
bar	/bɑː(r)/	n. 酒吧	NCE1 Lesson 095&96
metro	/ˈmetrəʊ/	n. 地铁(法国)	NCE1 Lesson 095&96
subway	/ˈsʌbweɪ/	v. 地铁(美)	NCE1 Lesson 095&96
describe	/dɪˈskraɪb/	v. 描述	NCE1 Lesson 097&98
zip	/zɪp/	n. 拉链	NCE1 Lesson 097&98
label	/ˈleɪb(ə)l/	n. 标签	NCE1 Lesson 097&98
handle	/ˈhænd(ə)l/	n. 提手，把手	NCE1 Lesson 097&98
address	/əˈdres/	n. 地址	NCE1 Lesson 097&98
pence	/pens/	n. penny的复数形式	NCE1 Lesson 097&98
ow	/aʊ/	interj. 哎哟	NCE1 Lesson 099&100
oops	/ʊps/	interj. 啊呀	NCE1 Lesson 099&100
slip	/slɪp/	v. 滑倒，滑了一脚	NCE1 Lesson 099&100
fall	/fɔːl/	v. 落下，跌倒	NCE1 Lesson 099&100
hurt	/hɜːt/	v. 伤，伤害，疼痛	NCE1 Lesson 099&100
back	/bæk/	n. 背	NCE1 Lesson 099&100
stand up	/ˈstænd ʌp/	v. 起立，站起来	NCE1 Lesson 099&100
help	/help/	v. 帮助	NCE1 Lesson 099&100
at once	/æt wʌns/	adv. 立即	NCE1 Lesson 099&100
sure	/ʃʊə(r)/	adj. 一定的，确信的	NCE1 Lesson 099&100
x-ray	/ˈeks reɪ/	n. X光透视	NCE1 Lesson 099&100
Scotland	/ˈskɒtlənd/	n. 苏格兰(英国)	NCE1 Lesson 101&102
card	/kɑːd/	n. 明信片	NCE1 Lesson 101&102
youth	/juːθ/	n. 青年	NCE1 Lesson 101&102
youngster	/ˈjʌŋstə(r)/	n. 少年	NCE1 Lesson 101&102
hostel	/ˈhɒst(ə)l/	n. 招待所，旅馆	NCE1 Lesson 101&102
hotel	/həʊˈtel/	n. 酒店；n. 饭店	NCE1 Lesson 101&102, NCE1 Lesson 135&136
association	/əˌsəʊsiˈeɪʃn;/	n. 协会	NCE1 Lesson 101&102
soon	/suːn/	adv. 不久	NCE1 Lesson 101&102
write	/raɪt/	v. 写	NCE1 Lesson 101&102
exam	/ɪɡˈzæm/	n. 考试	NCE1 Lesson 103&104
test	/test/	n. 测试	NCE1 Lesson 103&104
text	/tekst/	n. 文本	NCE1 Lesson 103&104
pass	/pɑːs/	v. 及格，通过	NCE1 Lesson 103&104
mathematics	/ˌmæθəˈmætɪks/	n. 数学	NCE1 Lesson 103&104
compute	/kəmˈpjuːt/	n. 计算	NCE1 Lesson 103&104
math	/mæθ/	n. 数学	NCE1 Lesson 103&104
question	/ˈkwestʃən/	n. 问题	NCE1 Lesson 103&104
easy	/ˈiːzi/	adj. 容易的	NCE1 Lesson 103&104
enough	/ɪˈnʌf/	adv. 足够地	NCE1 Lesson 103&104
extra	/ˈekstrə/	adj. 额外的	NCE1 Lesson 103&104, NCE1 Lesson 139&140
fail	/feɪl/	adj. 未及格，失败	NCE1 Lesson 103&104
failure	/ˈfeɪljə(r)/	n. 失败	NCE1 Lesson 103&104
respond	/rɪˈspɒnd/	v. 响应	NCE1 Lesson 103&104
response	/rɪˈspɒns/	n. 回复	NCE1 Lesson 103&104
mark	/mɑːk/	n. 分数	NCE1 Lesson 103&104
score	/skɔː(r)/	n. 得分	NCE1 Lesson 103&104
rest	/rest/	n. 其他的东西	NCE1 Lesson 103&104
difficult	/ˈdɪfɪkəlt/	adj. 困难的	NCE1 Lesson 103&104
hate	/heɪt/	v. 讨厌	NCE1 Lesson 103&104
low	/ləʊ/	adj. 低的	NCE1 Lesson 103&104
cheer	/tʃɪə(r)/	v. 振作，振奋	NCE1 Lesson 103&104
guy	/ɡaɪ/	n. 家伙，人	NCE1 Lesson 103&104
top	/tɒp/	n. 上方，顶部	NCE1 Lesson 103&104
clever	/ˈklevə(r)/	adj. 聪明的	NCE1 Lesson 103&104
intelligent	/ɪnˈtelɪdʒənt/	adj. 有智力的；adj. 聪明的，有智慧的	NCE1 Lesson 103&104, NCE1 Lesson 105&106
stupid	/ˈstjuːpɪd/	adj. 笨的	NCE1 Lesson 103&104
foolish	/ˈfuːlɪʃ/	adj. 愚蠢的	NCE1 Lesson 103&104
fool	/fuːl/	n. 笨蛋	NCE1 Lesson 103&104
cheap	/tʃiːp/	adj. 便宜的	NCE1 Lesson 103&104
expensive	/ɪkˈspensɪv/	adj. 贵的	NCE1 Lesson 103&104
inexpensive	/ˌɪnɪkˈspensɪv/	adj. 不贵的	NCE1 Lesson 103&104
stale	/steɪl/	adj. 变馊的	NCE1 Lesson 103&104
sour	/ˈsaʊə(r)/	adj. 酸味的	NCE1 Lesson 103&104
loud	/laʊd/	adj. 大声的	NCE1 Lesson 103&104
high	/haɪ/	adj. 高的	NCE1 Lesson 103&104
soft	/sɒft/	adj. 软的	NCE1 Lesson 103&104
salt	/sɔːlt/	adj. 咸的	NCE1 Lesson 103&104
spell	/spel/	v. 拼写	NCE1 Lesson 105&106
mistake	/mɪˈsteɪk/	n. 错误	NCE1 Lesson 105&106
error	/ˈerə(r)/	n. 错误	NCE1 Lesson 105&106
fault	/fɔːlt/	v. 失误	NCE1 Lesson 105&106
inaccuracy	/ɪnˈækjərəsi/	n. 不精确	NCE1 Lesson 105&106
present	/ˈprez(ə)nt/	n. 礼物	NCE1 Lesson 105&106
gift	/ɡɪft/	n. 礼物	NCE1 Lesson 105&106
dictionary	/ˈdɪkʃən(ə)ri/	n. 词典	NCE1 Lesson 105&106
carry	/ˈkæri/	v. 携带	NCE1 Lesson 105&106
correct	/kəˈrekt/	v. 改正，纠正	NCE1 Lesson 105&106
madam	/ˈmædəm/	n. 夫人，女士(对妇女的尊称)	NCE1 Lesson 107&108
as well	/æz wel/	adv. 同样	NCE1 Lesson 107&108
pretty	/ˈprɪti/	adj. 漂亮的	NCE1 Lesson 107&108
idea	/aɪˈdɪə/	n. 主意	NCE1 Lesson 109&110
a little	/ə ˈlɪtl/	adv. 少许(用于不可数名词之前)	NCE1 Lesson 109&110
teaspoonful	/ˈtiːspuːnfʊl/	n. 一满茶匙	NCE1 Lesson 109&110
spoon	/spuːn/	n. 茶匙	NCE1 Lesson 109&110
less	/les/	adj. (little的比较级)校少的，更小的	NCE1 Lesson 109&110
a few	/ə fjuː/	adv. 几个(用于可数名词之前)	NCE1 Lesson 109&110
pity	/ˈpɪti/	n. 遗憾	NCE1 Lesson 109&110
instead	/ɪnˈsted/	adv. 代替	NCE1 Lesson 109&110
advice	/ədˈvaɪs/	n. 建议，忠告	NCE1 Lesson 109&110
voice	/vɔɪs/	n. (人的)声音；n. (说话的)声音	NCE1 Lesson 109&110, NCE1 Lesson 119&120
most	/məʊst/	adj. (many,much的最高级)最多的	NCE1 Lesson 109&110
least	/liːst/	adj. (little的最高级)最小的，最少的	NCE1 Lesson 109&110
worse	/wɜːs/	adj. (bad的比较级)更坏的	NCE1 Lesson 109&110
worst	/wɜːst/	adj. (bad的最高级)最坏的	NCE1 Lesson 109&110
model	/ˈmɒd(ə)l/	n. 型号，式样	NCE1 Lesson 111&112
afford	/əˈfɔːd/	v. 付得起(钱)	NCE1 Lesson 111&112
afraid	/əˈfreɪd/	adj. 害怕的	NCE1 Lesson 111&112
deposit	/dɪˈpɒzɪt/	n. 预付定金	NCE1 Lesson 111&112
instalment	/ɪnˈstɔːlmənt/	n. 分期付款	NCE1 Lesson 111&112
price	/praɪs/	n. 价格	NCE1 Lesson 111&112
millionaire	/ˌmɪljəˈneə(r)/	n. 百万富翁	NCE1 Lesson 111&112
billionaire	/ˌbɪljəˈneə(r)/	n. 亿万富翁	NCE1 Lesson 111&112
pay	/peɪ/	v. paid paid 付	NCE1 Lesson 111&112
payment	/ˈpeɪmənt/	n. 支付	NCE1 Lesson 111&112
conductor	/kənˈdʌktə(r)/	n. 售票员	NCE1 Lesson 113&114
fare	/feə(r)/	n. 车费，车票	NCE1 Lesson 113&114
note	/nəʊt/	n. 纸币；n. 便条	NCE1 Lesson 113&114, NCE1 Lesson 117&118
coin	/kɔɪn/	n. 硬币	NCE1 Lesson 113&114, NCE1 Lesson 117&118
passenger	/ˈpæsɪndʒə(r)/	n. 乘客	NCE1 Lesson 113&114
none	/nʌn/	pron. 没有任何东西	NCE1 Lesson 113&114
get off	/ɡet ɒf/	adv. 下车	NCE1 Lesson 113&114
get on	/ɡet ɒn/	adv. 上车；n. 登上	NCE1 Lesson 113&114, NCE1 Lesson 141&142
tramp	/træmp/	n. 流浪汉	NCE1 Lesson 113&114
vagrant	/ˈveɪɡrənt/	n. 流浪者	NCE1 Lesson 113&114
except	/ɪkˈsept/	prep. 除…外	NCE1 Lesson 113&114
anyone	/ˈeniwʌn/	pron. (用于疑问句，否定式)任何人	NCE1 Lesson 115&116
knock	/nɒk/	v. 敲，打	NCE1 Lesson 115&116
everything	/ˈevriθɪŋ/	pron. 一切事物	NCE1 Lesson 115&116
quiet	/ˈkwaɪət/	adj. 宁静的，安静的	NCE1 Lesson 115&116
impossible	/ɪmˈpɒsəb(ə)l/	adj. 不可能的	NCE1 Lesson 115&116
invite	/ɪnˈvaɪt/	adj. 邀请	NCE1 Lesson 115&116
anything	/ˈeniθɪŋ/	pron. 任何东西	NCE1 Lesson 115&116
nothing	/ˈnʌθɪŋ/	pron. 什么也没有	NCE1 Lesson 115&116
lemonade	/ˌleməˈneɪd/	n. 柠檬水	NCE1 Lesson 115&116
joke	/dʒəʊk/	v. 开玩笑	NCE1 Lesson 115&116
awake	/əˈweɪk/	adj. 醒着的	NCE1 Lesson 115&116
alive	/əˈlaɪv/	adj. 活着的	NCE1 Lesson 115&116
everybody	/ˈevribɒdi/	pron. 每个人	NCE1 Lesson 115&116
nobody	/ˈnəʊbədi/	pron. 没有人	NCE1 Lesson 115&116
somebody	/ˈsʌmbədi/	pron. 某人	NCE1 Lesson 115&116
anybody	/ˈenibɒdi/	pron. 任何人	NCE1 Lesson 115&116
something	/ˈsʌmθɪŋ/	pron. 某事	NCE1 Lesson 115&116
everywhere	/ˈevriweə(r)/	adv. 到处	NCE1 Lesson 115&116
nowhere	/ˈnəʊweə(r)/	adv. 无处	NCE1 Lesson 115&116
somewhere	/ˈsʌmweə(r)/	adv. 在某处	NCE1 Lesson 115&116
anywhere	/ˈeniweə(r)/	adv. 在任何地方	NCE1 Lesson 115&116
every	/ˈevri/	adv. 每个	NCE1 Lesson 115&116
no	/nəʊ/	adv. 不	NCE1 Lesson 115&116
everyone	/ˈevriwʌn/	pron. 每个人	NCE1 Lesson 115&116
no one	/ˈnəʊ wʌn/	n. 没有人	NCE1 Lesson 115&116
someone	/ˈsʌmwʌn/	pron. 有人	NCE1 Lesson 115&116
back door	/ˌbæk ˈdɔː(r)/	n. 后门	NCE1 Lesson 115&116
dining room	/ˈdaɪnɪŋ ruːm/	n. 饭厅	NCE1 Lesson 117&118
swallow	/ˈswɒləʊ/	v. 吞下	NCE1 Lesson 117&118
later	/ˈleɪtə(r)/	adv. 后来	NCE1 Lesson 117&118
toilet	/ˈtɔɪlət/	n. 厕所，盥洗室	NCE1 Lesson 117&118
bathroom	/bɑːθruːm/	n. 盥洗室	NCE1 Lesson 117&118
restroom	/ˈrestrʊm/	n. 洗手间	NCE1 Lesson 117&118
washroom	/ˈwɒʃruːm/	n. 洗手间	NCE1 Lesson 117&118
loo	/luː/	n. 厕所	NCE1 Lesson 117&118
men's room	/menz ruːm/	n. 男厕所	NCE1 Lesson 117&118
ladies' room	/ˈleɪdɪz ruːm/	n. 女厕所	NCE1 Lesson 117&118
ring	/rɪŋ/	v. 响 rang, rung	NCE1 Lesson 117&118
story	/ˈstɔːri/	n. 故事	NCE1 Lesson 119&120
happen	/ˈhæpən/	v. 发生	NCE1 Lesson 119&120
thief	/θiːf/	n. 贼	NCE1 Lesson 119&120
dark	/dɑːk/	adj. 黑暗的	NCE1 Lesson 119&120
torch	/tɔːtʃ/	n. 手电筒	NCE1 Lesson 119&120
parrot	/ˈpærət/	n. 鹦鹉	NCE1 Lesson 119&120
exercise book	/ˈeksəsaɪz bʊk/	n. 练习本	NCE1 Lesson 119&120
exercise	/ˈeksəsaɪz/	v. 锻炼	NCE1 Lesson 119&120
customer	/ˈkʌstəmə(r)/	n. .顾客	NCE1 Lesson 121&122
forget	/fəˈɡet/	v. 忘记	NCE1 Lesson 121&122
manager	/ˈmænɪdʒə(r)/	n. 经理	NCE1 Lesson 121&122
serve	/sɜːv/	v. 照应，服务，接待	NCE1 Lesson 121&122
counter	/ˈkaʊntə(r)/	n. 柜台	NCE1 Lesson 121&122
recognize	/ˈrekəɡnaɪz/	v. 认识	NCE1 Lesson 121&122
road	/rəʊd/	n. 路	NCE1 Lesson 121&122
street	/striːt/	n. 街道	NCE1 Lesson 121&122
gentleman	/ˈdʒentlmən/	n. 绅士	NCE1 Lesson 121&122
during	/ˈdjʊərɪŋ/	prep. 在…期间	NCE1 Lesson 123&124
trip	/trɪp/	n. 旅行	NCE1 Lesson 123&124
travel	/ˈtræv(ə)l/	v. 旅行	NCE1 Lesson 123&124
offer	/ˈɒfə(r)/	v. 提供	NCE1 Lesson 123&124
guess	/ɡes/	v. 猜	NCE1 Lesson 123&124
grow	/ɡrəʊ/	v. 长，让…生长	NCE1 Lesson 123&124
beard	/bɪəd/	n. (下巴上的)胡子，络腮胡子	NCE1 Lesson 123&124
kitten	/ˈkɪt(ə)n/	n. 小猫	NCE1 Lesson 123&124
terribly	/ˈterəbli/	adv. 非常	NCE1 Lesson 125&126
dry	/draɪ/	adj. 干燥的，干的	NCE1 Lesson 125&126
nuisance	/ˈnjuːsns/	n. 讨厌的东西或人	NCE1 Lesson 125&126
mean	/miːn/	v. 意味着，意思是	NCE1 Lesson 125&126
surprise	/səˈpraɪz/	n. 惊奇，意外的事	NCE1 Lesson 125&126
immediately	/ɪˈmiːdiətli/	adv. 立即	NCE1 Lesson 125&126
famous	/ˈfeɪməs/	adj. 著名的	NCE1 Lesson 127&128
actress	/ˈæktrəs/	n. 女演员	NCE1 Lesson 127&128
at least	/æt liːst/	adv. 至少	NCE1 Lesson 127&128
actor	/ˈæktə(r)/	n. 男演员	NCE1 Lesson 127&128
wave	/weɪv/	v. 招手	NCE1 Lesson 129&130
track	/træk/	n. 跑道	NCE1 Lesson 129&130
mile	/maɪl/	n. 英里	NCE1 Lesson 129&130
overtake	/ˌəʊvəˈteɪk/	v. 从后面超越，超车	NCE1 Lesson 129&130
speed	/spiːd/	n. 限速	NCE1 Lesson 129&130
dream	/driːm/	v. 做梦，思想不集中	NCE1 Lesson 129&130
sign	/saɪn/	n. 标记，牌子	NCE1 Lesson 129&130
driving licence	/ˈdraɪvɪŋ laɪs(ə)ns/	n. 驾驶执照	NCE1 Lesson 129&130
charge	/tʃɑːdʒ/	n. 罚款	NCE1 Lesson 129&130
darling	/ˈdɑːlɪŋ/	n. 亲爱的(用作表示称呼)	NCE1 Lesson 129&130
Egypt	/ˈiːdʒɪpt/	n. 埃及	NCE1 Lesson 131&132
abroad	/əˈbrɔːd/	adv. 国外	NCE1 Lesson 131&132
worry	/ˈwʌri/	v. 担忧	NCE1 Lesson 131&132
Egyptian	/iˈdʒɪpʃn/	n. 埃及人	NCE1 Lesson 131&132
problem	/ˈprɒbləm/	n. 麻烦	NCE1 Lesson 131&132
reporter	/rɪˈpɔːtə(r)/	n. 记者	NCE1 Lesson 133&134
sensational	/senˈseɪʃən(ə)l/	adj. 爆炸性的，耸人听闻的	NCE1 Lesson 133&134
mink coat	/mɪŋk kəʊt/	n. 貂皮大衣	NCE1 Lesson 133&134
mink	/mɪŋk/	n. 貂皮	NCE1 Lesson 133&134
journalist	/ˈdʒɜːnəlɪst/	n. 新闻工作者	NCE1 Lesson 133&134
report	/rɪˈpɔːt/	n. 报告	NCE1 Lesson 133&134
future	/ˈfjuːtʃə(r)/	n. 未来的	NCE1 Lesson 135&136
feature	/ˈfiːtʃə(r)/	n. 特点	NCE1 Lesson 135&136
get married	/ɡet ˈmærid/	n. 结婚	NCE1 Lesson 135&136
wed	/wed/	v. 结婚	NCE1 Lesson 135&136
latest	/ˈleɪtɪst/	adj. 最新的	NCE1 Lesson 135&136
introduce	/ˌɪntrəˈdjuːs/	v. 介绍	NCE1 Lesson 135&136
description	/dɪˈskrɪpʃn/	n. 描述	NCE1 Lesson 135&136
married	/ˈmærid/	adj. 已婚的	NCE1 Lesson 135&136
marry	/ˈmæri/	v. 结婚	NCE1 Lesson 135&136
football	/ˈfʊtbɔːl/	n. 足球(美)	NCE1 Lesson 137&138
soccer	/ˈsɒkə(r)/	n. 足球(英)	NCE1 Lesson 137&138
pool	/puːl/	n. 赌注	NCE1 Lesson 137&138
win	/wɪn/	v. 赢	NCE1 Lesson 137&138
world	/wɜːld/	n. 世界	NCE1 Lesson 137&138
word	/wɜːd/	n. 单词	NCE1 Lesson 137&138
depend	/dɪˈpend/	v. 依靠(on)	NCE1 Lesson 137&138
overseas	/ˌəʊvəˈsiːz/	adj. 海外的，国外的	NCE1 Lesson 139&140
engineering	/ˌendʒɪˈnɪərɪŋ/	n. 工程	NCE1 Lesson 139&140
company	/ˈkʌmpəni/	n. 公司	NCE1 Lesson 139&140
line	/laɪn/	n. 线路	NCE1 Lesson 139&140
excited	/ɪkˈsaɪtɪd/	adj. 兴奋的	NCE1 Lesson 141&142
middle-aged	/ˌmɪd(ə)l ˈeɪdʒd/	adj. 中年的	NCE1 Lesson 141&142
opposite	/ˈɒpəzɪt/	prep. 在…对面	NCE1 Lesson 141&142
curiously	/ˈkjʊəriəsli/	adv. 好奇地	NCE1 Lesson 141&142
funny	/ˈfʌni/	adj. 可笑的，滑稽的	NCE1 Lesson 141&142
powder	/ˈpaʊdə(r)/	n. 香粉	NCE1 Lesson 141&142
compact	/kəmˈpækt/	n. 带镜的化妆盒	NCE1 Lesson 141&142
kindly	/ˈkaɪndli/	adv. 和蔼地	NCE1 Lesson 141&142
ugly	/ˈʌɡli/	adj. 丑陋的	NCE1 Lesson 141&142
amused	/əˈmjuːzd/	adj. 有趣的	NCE1 Lesson 141&142
embarrassed	/ɪmˈbærəst/	adj. 尴尬的，窘迫	NCE1 Lesson 141&142
worried	/ˈwʌrid/	adj. 担心，担忧	NCE1 Lesson 141&142
regularly	/ˈreɡjələli/	adv. 经常地，定期地	NCE1 Lesson 141&142
surround	/səˈraʊnd/	v. 包围	NCE1 Lesson 143&144
round	/raʊnd/	adj. 圆形的	NCE1 Lesson 143&144
wood	/wʊd/	n. 树林	NCE1 Lesson 143&144
forest	/ˈfɒrɪst/	n. 森林	NCE1 Lesson 143&144
jungle	/ˈdʒʌŋɡ(ə)l/	n. 丛林	NCE1 Lesson 143&144
beauty spot	/ˈbjuːti spɒt/	n. 风景点	NCE1 Lesson 143&144
spot	/spɒt/	n. 地点	NCE1 Lesson 143&144
hundred	/ˈhʌndrəd/	num. 百	NCE1 Lesson 143&144
through	/θruː/	prep. 穿过	NCE1 Lesson 143&144
visitor	/ˈvɪzɪtə(r)/	n. 参观者，游客，来访者	NCE1 Lesson 143&144
guest	/ɡest/	n. 来宾	NCE1 Lesson 143&144
litter	/ˈlɪtə(r)/	n. 杂乱的东西	NCE1 Lesson 143&144
litter basket	/ˈlɪtə(r) ˈbɑːskɪt/	n. 废物筐	NCE1 Lesson 143&144
place	/pleɪs/	v. 放	NCE1 Lesson 143&144
throw	/θrəʊ/	v. 扔，抛	NCE1 Lesson 143&144
rubbish	/ˈrʌbɪʃ/	n. 垃圾	NCE1 Lesson 143&144
garbage	/ˈɡɑːbɪdʒ/	n. 垃圾箱	NCE1 Lesson 143&144
count	/kaʊnt/	v. 数，点	NCE1 Lesson 143&144
cover	/ˈkʌvə(r)/	v. 覆盖	NCE1 Lesson 143&144
piece	/piːs/	n. 碎片	NCE1 Lesson 143&144
tyre	/ˈtaɪə(r)/	n. 轮胎	NCE1 Lesson 143&144
rusty	/ˈrʌsti/	adj. 生锈的	NCE1 Lesson 143&144
among	/əˈmʌŋ/	prep. 在…之间	NCE1 Lesson 143&144
prosecute	/ˈprɒsɪkjuːt/	v. 依法处置	NCE1 Lesson 143&144
    """.trimIndent()
}
