package com.personalieltscoach.data.seed

/** Generated from content/cet4/vocabulary.json; no lesson prose or audio. */
internal object Cet4Vocabulary {
    const val COUNT = 4458
    val entries: List<List<String>> by lazy {
        rows.flatMap { chunk -> chunk.lineSequence().map(String::trim)
            .filter(String::isNotBlank).map { it.split('\t').also { fields -> require(fields.size == 3) } }.toList() }
    }
    private val rows = listOf(
        """
excuse	ɪkˈskjuːs	n. 理由；借口
me	miː	pron. 我（宾格）
yes	jes	adv. 是
this	ðɪs	pron. 这；这个；这里
your	jɔː(r)	pron. 你的，你们的
pardon	ˈpɑːd(ə)n	n. 原谅；宽恕；赦免
it	ɪt	pron. 它
thank	θæŋk	vt. 感谢
very	ˈveri	adj. 十足的；恰好是，正是；甚至；特有的
you	juː	pron. 你；你们
much	mʌtʃ	adv. 非常，很
which	wɪtʃ	pron. 哪 / 那一个；哪 / 那一些
hello	həˈləʊ	int. 喂；哈罗
absent	ˈæbsənt	adj. 缺席的；缺少的；心不在焉的；茫然的
hand	hænd	n. 手，手艺；指针；插手；帮助
color	ˈkʌlə(r)	n. 颜色；脸色；肤色；颜料
nuclear	ˈnjuːkliə(r)	adj. 原子能的；中心的；细胞核的；原子核的
glass	ɡlɑːs	n. 玻璃；镜子；玻璃制品
I	aɪ	pron. 我
no	nəʊ	adv. 不
be	biː	prep. 在，存在；是
the	ðə	art. 这；那； adv. 更加（用于比较级，最高级前）
these	ðiːz	pron. 这些
that	ðæt	pron. 那；那个
those	ðəʊz	adj. 那些的
a	ə	art. 一；任一；每一
many	ˈmeni	pron. 许多；许多人
pen	pen	n. 钢笔；围栏；作家
pencil	ˈpens(ə)l	n. 铅笔；笔状物
book	bʊk	n. 书籍；帐簿；卷；名册；工作簿
watch	wɒtʃ	vt. 注视；观察；看守；警戒
coat	kəʊt	n. 外套
dress	dres	vt. 给 … 穿衣
skirt	skɜːt	n. 裙子
shirt	ʃɜːt	n. 衬衫；汗衫，内衣
car	kɑː(r)	n. 汽车；车厢
house	haʊs	n. 住宅；家庭；某种用途的建筑物；机构；议会
housewife	ˈhaʊswaɪf	n. 家庭主妇
household	ˈhaʊshəʊld	adj. 家庭的；日常的；王室的
umbrella	ʌmˈbrelə	n. 雨伞；庇护；保护伞；伞形结构
please	pliːz	vt. 使高兴，使满意；使喜欢
here	hɪə(r)	adv. 在这里；此时
there	ðeə(r)	adv. 在那里；在那点上；在那边
where	weə(r)	adv. 在哪里
my	maɪ	pron. 我的
ticket	ˈtɪkɪt	n. 票；入场券，标签；证明书；（美）候选人名单； [ 口 ] 交通罚款单
number	ˈnʌmbə(r)	n. 号码；数字；数；算术；（杂志等的）期
five	faɪv	n. 五，五个； [ 口 ] 五美元钞票
sorry	ˈsɒri	adj. 对不起的，抱歉的；遗憾的
sir	sɜː(r)	n. 阁下，先生
suit	suːt	vt. 适合；使适应
school	skuːl	n. 学校；学派；学院；鱼群
teacher	ˈtiːtʃə(r)	n. 教师；导师
son	sʌn	n. 儿子；男性后裔；孩子（对年轻人的称呼）
daughter	ˈdɔːtə(r)	n. 女儿；子代
teach	tiːtʃ	vt. 教；教授；教导
sun	sʌn	n. 太阳
university	ˌjuːnɪˈvɜːsəti	n. 大学；综合性大学；大学校舍
college	ˈkɒlɪdʒ	n. 学院；学会；大学
miss	mɪs	vt. 漏掉；错过；想念
good	ɡʊd	adj. 愉快的；好的；虔诚的；优良的
morning	ˈmɔːnɪŋ	n. 早晨； [ 诗 ] 初期；黎明
new	njuː	adj. 新的，新鲜的；更新的；初见的
student	ˈstjuːd(ə)nt	n. 学生；学者
French	frentʃ	adj. 法国的；法语的；法国人的
German	'dʒɜːmən	adj. 德国的；德语的，德国人的
nice	naɪs	adj. 精密的；美好的；细微的；和蔼的
meet	miːt	vt. 遇见；满足；对付
too	tuː	adv. 也；太；非常；还；过度；很
Japan	dʒə'pæn	n.日本
Japanese	dʒæpə'ni:z	adj. 日本（人）的；日语的
China	ˈtʃaɪnə	n. 中国
Chinese	tʃaɪˈniːz	n. 中文，汉语；中国人
Germany	ˈdʒɜːməni	n.德国
France	frɑːns	n.法国,法兰西
make	meɪk	vt. 制造；构成；使得；获得；引起；进行；布置，准备，整理；认为；安排；形成
England	ˈɪŋɡlənd	n.英格兰(英国的主要部分),英格兰和威尔士,英国
English	'ɪŋɡlɪʃ	adj. 英国人的；英国的；英文的
America	ə'merɪkə	n.<美>美国,美洲(包括北美和南美洲)
American	əˈmerɪkən	n. 美国人
        """,
        """
Italian	ɪˈtælɪən	adj. 意大利的；意大利文化的；意大利语的
to	tuː	adv. 向前；（门等）关上
news	njuːz	n. 新闻，消息；新闻报导
goods	ɡʊdz	n.货物（仅复数）,<美俚>工具
anyone	ˈeniwʌn	pron. 任何人；任何一个
knock	nɒk	vi. 敲；敲击；打
everything	ˈevriθɪŋ	pron. 每件事物；一切事物
quiet	ˈkwaɪət	adj. 安静的；安定的；温顺的；不动的
impossible	ɪmˈpɒsəb(ə)l	adj. 不可能的；不可能存在的；难以忍受的；不真实的
invite	ɪnˈvaɪt	vt. 邀请，招待；招致
anything	ˈeniθɪŋ	pron. 任何事
nothing	ˈnʌθɪŋ	neg. 没什么；毫不
joke	dʒəʊk	n. 玩笑，笑话；笑柄
asleep	əˈsliːp	adj. 睡着的；麻木的；长眠的
awake	əˈweɪk	vi. 觉醒，意识到；醒来；被唤起
alive	əˈlaɪv	adj. 活着的；活泼的；有生气的
any	ˈeni	adj. 任何的；所有的；丝毫
everybody	ˈevribɒdi	pron. 人人；每个人
nobody	ˈnəʊbədi	pron. 无人，没有人；没有任何人
somebody	ˈsʌmbədi	n. 大人物；重要人物
anybody	ˈenibɒdi	pron. 任何人
something	ˈsʌmθɪŋ	pron. 某事；某物
everywhere	ˈevriweə(r)	adv. 到处
nowhere	ˈnəʊweə(r)	adv. 无处；任何地方都不；毫无结果
somewhere	ˈsʌmweə(r)	adv. 在某处；到某处
anywhere	ˈeniweə(r)	adv. 无论何处；在任何地方
every	ˈevri	adj. 每一的，每个的；每隔 … 的
some	sʌm	adj. 一些；大约；某一
everyone	ˈevriwʌn	pron. 人人；每个人
someone	ˈsʌmwʌn	pron. 有人，某人
each	iːtʃ	adj. 每；各自的
sleep	sliːp	vi. 睡，睡觉
dead	ded	adj. 无生命的；废弃了的；呆板的
possible	ˈpɒsəb(ə)l	adj. 可能的；合理的；合适的
door	dɔː(r)	n. 门；家，户；门口；通道
leave	liːv	vt. 离开；留下；委托；遗忘
left	left	n. 左边；左派
leaf	liːf	n. 叶子；（书籍等的）一张；扇页
coin	kɔɪn	vt. 杜撰，创造；铸造（货币）
mouth	maʊθ	n. 口，嘴；河口
swallow	ˈswɒləʊ	vt. 忍受；吞没
later	ˈleɪtə(r)	adv. 随后；稍后；后来
toilet	ˈtɔɪlət	n. 厕所，盥洗室；梳妆，打扮
bathroom	bɑːθruːm	n. 浴室；厕所；盥洗室
ring	rɪŋ	vi. 按铃；敲钟；回响；成环形
room	ruːm	n. 房间；空间；机会；余地
latter	ˈlætə(r)	adj. 后者的；较后的；近来的；后面的
last	lɑːst	n. 末尾，最后；上个；鞋楦（做鞋的模型）
early	ˈɜːrli	adj. 早期的；早熟的
former	ˈfɔːmə(r)	adj. 从前的，前者的；前任的
ladder	ˈlædə(r)	n. 阶梯；梯状物；途径
first	fɜːst	adv. 第一；首先；优先；宁愿
mouse	maʊs	n. 鼠标；老鼠；胆小羞怯的人(as poor as a church mouse)
note	nəʊt	n. 笔记；注解；便笺；照会；音符；调子；票据；纸币
money	ˈmʌni	n. 钱；货币；财富
restaurant	ˈrestrɒnt	n. 饭店；餐馆
story	ˈstɔːri	n. 故事；来历；新闻报道；小说； [ 口 ] 假话
happen	ˈhæpən	vi. 发生；碰巧；偶然遇到
thief	θiːf	n. 小偷，贼
enter	ˈentə(r)	vt. 进入；开始；参加
dark	dɑːk	adj. 黑暗的，深色的；无知的；模糊的；忧郁的
torch	tɔːtʃ	n. 火把，火炬；手电筒；启发之物
voice	vɔɪs	n. 声音；愿望；嗓音；发言权
exit	ˈeksɪt	n. 出口，通道；退场
daring	ˈdeərɪŋ	adj. 大胆的，勇敢的
touch	tʌtʃ	vt. 接触；触动；使轻度受害
sound	saʊnd	vt. 使发声；听（诊）；测量，测 … 深；宣告；试探
noise	nɔɪz	n. 响声；杂音；噪音
history	ˈhɪst(ə)ri	n. 历史，历史学；历史记录；来历
exercise	ˈeksəsaɪz	n. 练习；运动；运用；操练；典礼；礼拜
customer	ˈkʌstəmə(r)	n. 顾客（买东西的）； [ 口 ] 家伙
forget	fəˈɡet	vt. 忘记；忽略
manager	ˈmænɪdʒə(r)	n. 经理；管理人员
serve	sɜːv	vt. 招待，供应；为 … 服务；对 … 有用；可作 … 用
counter	ˈkaʊntə(r)	n. 柜台；计数器，计算器；计算者
recognize	ˈrekəɡnaɪz	vt. 承认；认出，识别
service	ˈsɜːvɪs	n. 服务，服侍；服役；仪式
count	kaʊnt	vt. 计算；认为
road	rəʊd	n. 道路；公路，马路；手段
street	striːt	n. 街道
        """,
        """
read	riːd	vt. 阅读；读懂，理解
way	weɪ	n. 道路；方法；方向；习惯；行业
during	ˈdjʊərɪŋ	prep. 在 … 的时候，在 … 的期间
trip	trɪp	vi. 绊倒；犯错误；远足；轻快地走
travel	ˈtræv(ə)l	vi. 旅行；步行；行进； [ 口 ] 交往
offer	ˈɒfə(r)	vt. 提供；试图；出价
job	dʒɒb	n. 工作；职业
work	wɜːk	n. 工作；职业；产品；行为；文学、音乐或艺术作品；工厂；操作； [ 物 ] 功
guess	ɡes	vt. 猜测；推测；猜中； [ 美口 ] 认为
grow	ɡrəʊ	vi. 生长；发展；渐渐变得 …
beard	bɪəd	vt. 公然反对；抓 … 的胡须
while	waɪl	conj. 当…的时候；虽然；然而
whilst	waɪlst	conj. 当 … 的时候；时时，有时；同时
cat	kæt	n. 猫；猫科动物
water	ˈwɔːtə(r)	n. 水；雨水；海水；海域，大片的水
dry	draɪ	adj. 干的；口渴的；禁酒的；枯燥无味的
nuisance	ˈnjuːsns	n. 麻烦事；损害；讨厌的人；讨厌的东西
mean	miːn	adj. 低劣的；平均的；卑鄙的
surprise	səˈpraɪz	n. 惊奇，诧异；突然袭击
immediately	ɪˈmiːdiətli	adv. 直接地；立即，立刻
awfully	ˈɔːfli	adv. 非常；很；十分；可怕地
awful	ˈɔːf(ə)l	adj. 可怕的，吓人的；庄严的，威严的；糟糕的，糟透的（口语）
wet	wet	adj. 潮湿的；有雨的
wave	weɪv	vi. 波动；起伏；摇动；呈波形；挥手示意
track	træk	n. 足迹，踪迹；轨道；小道
mile	maɪl	n.英里,较大的距离
overtake	ˌəʊvəˈteɪk	vt. 赶上；压倒；突然来袭
speed	spiːd	vi. 加速，迅速前行；超速，加速；兴隆
dream	driːm	vi. 做梦，梦见；梦想；想到
sign	saɪn	n. 符号；记号；迹象；手势
charge	tʃɑːdʒ	n. 费用；掌管；控告；负载；电荷；命令
darling	ˈdɑːlɪŋ	n. 心爱的人；亲爱的
famous	ˈfeɪməs	adj. 著名的； [ 口 ] 极好的，非常令人满意的
actress	ˈæktrəs	n. 女演员
actor	ˈæktə(r)	n. 行动者；男演员；作用物
horse	hɔːs	n. 马；骑兵；脚架； [ 俚 ] 海洛因
name	neɪm	n. 名称，名字；姓名；名誉
what	wɒt	pron. 什么；多么；多少
nationality	ˌnæʃəˈnæləti	n. 国籍，国家；民族；部落
keyboard	ˈkiːbɔːd	n. 键盘
operator	ˈɒpəreɪtə(r)	n. 操作员；话务员；行家；经营者
engineer	ˌendʒɪˈnɪə(r)	n. 工程师；工兵；火车司机
policeman	pəˈliːsmən	n. 警察，警员；淀帚（橡皮头玻璃搅棒）
postman	ˈpəʊstmən	n.邮差
nurse	nɜːs	vt. 看护，护理；培养；照顾；给 … 喂奶
mechanic	məˈkænɪk	n. 技工，机修工
Hi	haɪ	int. 嗨！（表示问候或用以唤起注意）
how	haʊ	adv. 多么；多少；如何
today	təˈdeɪ	adv. 今天；现今
well	wel	adv. 满意地；适当地；很好地；充分地
fine	faɪn	adj. 好的；优良的；晴朗的；健康的；细小的，精美的
goodbye	ˌɡʊdˈbaɪ	int. 再见
see	siː	vt. 看见；领会；理解
whose	huːz	pron. 谁的（疑问代词）
blue	bluː	adj. 蓝色的；忧郁的，沮丧的；下流的
perhaps	pəˈhæps	adv. 也许；可能
white	waɪt	adj. 白色的；白种的；纯洁的
catch	kætʃ	vt. 赶上；抓住；感染；了解
father	ˈfɑːðə(r)	n. 父亲，爸爸；神父；祖先；前辈
mother	ˈmʌðə(r)	n. 母亲；大娘；女修道院院长
sister	ˈsɪstə(r)	n. 姐妹；修女；护士
tie	taɪ	vt. 系；打结；约束；与 … 成平局
brother	ˈbrʌðə(r)	n. 兄弟；战友；同事
his	hɪz	pron. 他的
her	hɜː(r)	pron. 她的；她
woman	ˈwʊmən	n. 女性；妇女；成年女子
thin	θɪn	adj. 薄的；瘦的；稀薄的；微弱的
tall	tɔːl	adj. 高的；长的；夸大的；过分的
short	ʃɔːt	adj. 短的；不足的；急速的；唐突的
dirty	ˈdɜːti	adj. 污秽的；下流的，卑鄙的；恶劣的；暗淡的
clean	kliːn	adj. 清洁的，干净的；清白的
hot	hɒt	adj. 热的；辣的；热情的；激动的；紧迫的
cold	kəʊld	adj. 寒冷的；冷淡的，不热情的；失去知觉的
old	əʊld	adj. 年老的；陈旧的，古老的
young	jʌŋ	adj. 年轻的；初期的；没有经验的
busy	ˈbɪzi	adj. 忙碌的；热闹的；正被占用的
lazy	ˈleɪzi	adj. 懒惰的；怠惰的；懒洋洋的；慢吞吞的
green	ɡriːn	adj. 绿色的；青春的
come	kʌm	vi. 来；出现；到达；变成；开始；发生
upstairs	ˌʌpˈsteəz	adv. 在楼上，向楼上；往楼上；上楼
        """,
        """
downstairs	ˌdaʊnˈsteəz	adv. 在楼下
smart	smɑːt	n. 痛苦；刺痛
hat	hæt	n. 帽子
same	seɪm	adj. 相同的；同一的；无变化的；上述的（通常与 the 连用）
lovely	ˈlʌvli	adj. 可爱的；令人愉快的
case	keɪs	n. 情况；实例；箱
carpet	ˈkɑːpɪt	vt. 在 … 上铺地毯，把地毯铺在 … 上；斥责
blanket	ˈblæŋkɪt	n. 毛毯，毯子；毯状物，覆盖层
dog	dɒɡ	n. 狗；； [ 美俚 ] 丑女人；卑鄙的人；（俚）朋友； vt. 跟踪；尾随
officer	ˈɒfɪsə(r)	n. 军官，警官；公务员，政府官员；船长
girl	ɡɜːl	n. 女孩，姑娘；女儿；女朋友
friend	frend	n. 朋友；赞助者；助手
passport	ˈpɑːspɔːt	n. 护照，通行证；手段
brown	braʊn	adj. 棕色的，褐色的；太阳晒黑的
tourist	ˈtʊərɪst	n. 旅行者，观光客
Russian	ˈrʌʃ(ə)n	adj. 俄国的；俄语的
red	red	n. 红色，红颜料；赤字
grey	ɡreɪ	adj. 灰色的；（头发）灰白的
yellow	ˈjeləʊ	adj. 黄色的；黄皮肤的
black	blæk	adj. 黑色的；黑人的；邪恶的
orange	ˈɒrɪndʒ	adj. 橘色的；橙色的
employee	ɪmˈplɔɪiː	n. 雇员；从业员工
sale	seɪl	n. 出售；销售额；拍卖；销售；廉价出售
man	mæn	n. 雇工；男人；人；人类；丈夫
office	ˈɒfɪs	n. 办公室；营业处；官职；政府机关
assistant	əˈsɪstənt	n. 助手，助理，助教
matter	ˈmætə(r)	n. 物质；事件；原因
child	tʃaɪld	n. 儿童，孩子；子孙；产物
kid	kɪd	n. 小孩；小山羊
tired	ˈtaɪəd	adj. 疲倦的；厌倦的，厌烦的
boy	bɔɪ	n. 男孩； [ 美口 ] 男人
thirsty	ˈθɜːsti	adj. 口渴的，口干的；渴望的，热望的
right	raɪt	adj. 正确的；右方的；直接的
ice	aɪs	n. 冰；冰淇淋；矜持；（俚）钻石； vt. 冰镇；结冰； vi. 结冰； adj. 冰的
cream	kriːm	n. 奶油，乳脂；乳酪；精华；面霜
big	bɪɡ	adj. 大的；重要的；量大的
small	smɔːl	adj. 少的，小的；不重要的；几乎没有的；微弱的；幼小的
open	ˈəʊpən	adj. 营业着的；敞开的；空旷的；公开的；坦率的
shut	ʃʌt	vt. 关闭；停业；幽禁
close	kləʊz	adj. 亲密的；亲近的；紧密的
light	laɪt	n. 光，光亮；灯
heavy	ˈhevi	adj. 沉重的；繁重的，巨大的；阴沉的
long	lɒŋ	n. 长时间；长音节
shoe	ʃuː	n. 鞋；蹄铁；外胎
grandfather	ˈɡrænfɑːðə(r)	n. 祖父；始祖
grandmother	ˈɡrænmʌðə(r)	n. 祖母；女祖先
grand	ɡrænd	adj. 宏伟的；豪华的；极重要的
give	ɡɪv	vt. 给；授予；让步；产生；举办
one	wʌn	pron. 任何人；一个人
empty	ˈempti	adj. 空的；无意义的；徒劳的；无知的
full	fʊl	adj. 完全的，完整的；满的，充满的；丰满的；丰富的；详尽的；完美的
large	lɑːdʒ	adj. 大的；广博的；多数的
little	ˈlɪt(ə)l	adj. 小的；短暂的；很少的；小巧可爱的
sharp	ʃɑːp	adj. 急剧的；锋利的；刺耳的；敏捷的；强烈的
box	bɒks	n. 箱，盒子；包厢；一拳
cup	kʌp	n. 杯子；奖杯；酒杯
bottle	ˈbɒt(ə)l	n. 瓶子；一瓶的容量
tin	tɪn	n. 锡；罐头，罐；马口铁
knife	naɪf	n. 刀；匕首
fork	fɔːk	n. 餐叉；叉；耙
on	ɒn	adv. 向前地；继续着；作用中，行动中
shelf	ʃelf	n. 搁板；架子；搁板状物
desk	desk	n. 办公桌；服务台；编辑部；（美）讲道台；乐谱架
table	ˈteɪb(ə)l	n. 桌子；表格；平地层
plate	pleɪt	n. 金属板；碟；金属牌；感光底片
dish	dɪʃ	n. 碟，盘；一道菜
cupboard	ˈkʌbəd	n. 食橱；碗柜
cigarette	ˌsɪɡəˈret	n. 香烟；纸烟
television	ˈtelɪvɪʒ(ə)n	n. 电视，电视机；电视业
floor	flɔː(r)	n. 地板，地面；楼层；基底；议员席
magazine	ˌmæɡəˈziːn	n. 杂志；弹药库；胶卷盒
bed	bed	n. 床；基础；河底， 海底
newspaper	ˈnjuːzpeɪpə(r)	n. 报纸
loudspeaker	ˌlaʊdˈspiːkə(r)	n. 喇叭，扬声器；扩音器
speaker	ˈspiːkə(r)	n. 扬声器；说话者；演讲者；说某种语言的人
radio	ˈreɪdiəʊ	n. 收音机；无线电广播设备
kitchen	ˈkɪtʃɪn	n. 炊具；厨房；炊事人员
refrigerator	rɪˈfrɪdʒəreɪtə(r)	n. 冰箱，冷藏库
fridge	frɪdʒ	n. 电冰箱
electric	ɪˈlektrɪk	adj. 电的；导电的；电动的；发电的；令人震惊的
        """,
        """
cook	kʊk	vt. 烹调，煮
of	ɒv	prep. 属于；关于； … 的；由 … 组成的
in	ɪn	prep. 在 … 之内；从事于；按照（表示方式）
near	nɪə(r)	adj. 近的；近似的；亲近的
window	ˈwɪndəʊ	n. 窗；窗口；窗户
picture	ˈpɪktʃə(r)	n. 照片，图画；景色；影片；化身
image	ˈɪmɪdʒ	n. 影像；肖像；想象；偶像
photograph	ˈfəʊtəɡrɑːf	vt. 为 … 拍照；使深深印入
wall	wɔːl	n. 墙壁，围墙；似墙之物
trousers	ˈtraʊzəz	n. 裤子，长裤
tidy	ˈtaɪdi	adj. 整齐的； [ 口 ] 相当大的
must	mʌst	aux. 必须，一定；很可能；可以，应当
air	eə(r)	n. 空气，大气；曲调；天空；样子
put	pʊt	vt. 放；表达；移动；安置；赋予
clothes	kləʊðz	n. 衣服
dust	dʌst	n. 灰尘；尘埃；尘土
sweep	swiːp	vt. 扫除；猛拉；掸去
sharpen	ˈʃɑːpən	vt. 削尖；使敏捷；加重；磨快
garden	ˈɡɑːd(ə)n	n. 菜园；花园
under	ˈʌndə(r)	prep. 低于，少于；在 ... 之下
tree	triː	n. 树；树状物；木料
climb	klaɪm	vi. 攀登；爬；上升
who	huː	pron. 谁
run	rʌn	vi. 奔跑；运转；经营
grass	ɡrɑːs	n. 草；草地，草坪
after	ˈɑːftə(r)	adv. 后来，以后
type	taɪp	n. 类型，品种；样式；模范
letter	ˈletə(r)	n. 字母，文字；信；字面意义；证书；文学，学问
basket	ˈbɑːskɪt	n. 篮子；（篮球比赛的）得分；一篮之量；篮筐； vt. 装入篮
basketball	ˈbɑːskɪtbɔːl	n. 篮球；篮球运动
eat	iːt	vt. 吃，喝；腐蚀；烦扰
bone	bəʊn	n. 骨；骨骼；香烟；一首歌； vt. 剔去 ... 的骨；施骨肥于； vi. 苦学；专心致志
tooth	tuːθ	n. 牙齿
milk	mɪlk	n. 牛奶；乳状物
meal	miːl	n. 一餐，一顿饭；膳食
drink	drɪŋk	vt. 喝，饮；吸收；举杯庆贺
tap	tæp	vt. 轻敲；轻打；装上嘴子
day	deɪ	n. 一天；白昼；时期
daily	ˈdeɪli	adj. 每日的；日常的
fly	flaɪ	vi. 飞；飘扬；驾驶飞机
cloud	klaʊd	n. 云；云状物；一大群；阴云；黑斑
sky	skaɪ	n. 天空；顶点
shine	ʃaɪn	vi. 照耀；显露；出众
with	wɪð	prep. 用；支持；随着；和 … 在一起
family	ˈfæməli	n. 家庭；家族；家属；亲属；子女；僚属
walk	wɔːk	n. 步行，走；散步
over	ˈəʊvə(r)	adv. 结束；越过；从头到尾
across	əˈkrɒs	prep. 穿过；横穿
bridge	brɪdʒ	n. 桥；桥牌；船桥；桥接器
boat	bəʊt	n. 小船；轮船
ship	ʃɪp	vt. 运送，乘船；以船运送
river	ˈrɪvə(r)	n. 河，江
brook	brʊk	n. 布鲁克
aeroplane	ˈeərəpleɪn	n. <英> 飞机
airplane	ˈeəpleɪn	n. 飞机
shave	ʃeɪv	vt. 剃；修面；掠过
cry	kraɪ	vt. 叫喊；哭出；大声说
wash	wɒʃ	n. 洗涤；洗的衣服；冲积物；化妆水
wait	weɪt	vt. 等候；推迟；延缓
jump	dʒʌmp	n. 跳跃；暴涨；惊跳
village	ˈvɪlɪdʒ	n. 村庄；村民；（动物的）群落
valley	ˈvæli	n. 流域；山谷；溪谷
country	ˈkʌntri	n. 国家；故乡
between	bɪˈtwiːn	prep. 在 … 之间
hill	hɪl	n. 小山；丘陵；斜坡；山冈
mountain	ˈmaʊntən	n. 山脉；山
another	əˈnʌðə(r)	adj. 另外的；不同的；又一，另一
other	ˈʌðə(r)	adj. 其他的，另外的
wife	waɪf	n. 妻子，已婚妇女；夫人
along	əˈlɒŋ	adv. 向前；一起；来到
belong	bɪˈlɒŋ	vi. 属于，应归入；适宜；应被放置；居住
bank	bæŋk	n. 银行；岸；储库；浅滩
swim	swɪm	vi. 游泳；浸；漂浮；眩晕
building	ˈbɪldɪŋ	n. 建筑；建筑物
build	bɪld	vt. 建筑；建立
park	pɑːk	n. 公园；停车场
into	ˈɪntuː	prep. 到 … 里；深入 … 之中；成为 … 状况；进入到 … 之内
beside	bɪˈsaɪd	prep. 在旁边；与 … 相比；和 … 无关
off	ɒf	prep. 离开；脱落
hard	hɑːd	adj. 困难的；硬的；猛烈的；确实的；努力的；辛苦的；冷酷无情的；严厉的
        """,
        """
hammer	ˈhæmə(r)	vi. 敲打；锤击；重复
paint	peɪnt	vt. 油漆；描绘；装饰；绘画
pink	pɪŋk	n. 粉红色；石竹花；头面人物；化身，典范
favourite	ˈfeɪvərɪt	adj. 特别受喜爱的
listen	ˈlɪs(ə)n	vi. 听，倾听；听从，听信
front	frʌnt	n. 前面；前线；正面
careful	ˈkeəf(ə)l	adj. 仔细的，小心的
drop	drɒp	vt. 滴；使降低；随口漏出；使终止
vase	vɑːz	n. 花瓶；瓶
flower	ˈflaʊə(r)	n. 花；精华；开花植物
show	ʃəʊ	vt. 显示；演出；展出；说明
send	send	vt. 发送，寄；派遣；发射；使进入
post	pəʊst	n. 岗位；邮件；标杆
take	teɪk	vt. 拿，取；接受；采取；吃
bring	brɪŋ	vt. 带来；引起；促使；使某人处于某种情况或境地
cheese	tʃiːz	n. 奶酪；干酪； [ 美俚 ] 要人
bread	bred	n. 生计；面包
soap	səʊp	n. 肥皂
chocolate	ˈtʃɒklət	n. 巧克力，巧克力糖；巧克力色
sugar	ˈʃʊɡə(r)	n. 糖；食糖；甜言蜜语
candy	ˈkændi	vt. 用糖煮；使结晶为砂糖；美化
coffee	ˈkɒfi	n. 咖啡；咖啡豆；咖啡色
tea	tiː	n. 茶叶；茶树；茶点
tobacco	təˈbækəʊ	n. 烟草，烟叶；烟草制品；抽烟
loaf	ləʊf	n. 块；条，一条面包；游荡
certainly	ˈsɜːt(ə)nli	adv. 当然；必定；行（用于回答）
bird	bɜːd	n. 鸟；家伙；羽毛球
kettle	ˈket(ə)l	n. 壶；罐；鼓；釜
behind	bɪˈhaɪnd	prep. 支持；落后于；晚于
now	naʊ	adv. 现在；立刻；如今
find	faɪnd	vt. 发现；认为；感到；获得
boil	bɔɪl	vi. 煮沸，沸腾；激动，激昂
can	kæn	n. 罐头，一罐；金属容器
oil	ɔɪl	n. 油；石油；油画颜料
pot	pɒt	n. 罐；壶；盆
boss	bɒs	n. 老板；工头；首领
minute	ˈmɪnɪt	n. 片刻，一会儿；分，分钟；备忘录，笔记；会议记录
second	ˈsekənd	n. 秒；瞬间；二等品；第二名
hour	ˈaʊə(r)	n. 小时；钟头；课时； … 点钟
ask	ɑːsk	vt. 问，询问；邀请；要求；需要；讨价
handwriting	ˈhændraɪtɪŋ	n. 书法；笔迹；书写；手稿
terrible	ˈterəb(ə)l	adj. 可怕的；很糟的；令人讨厌的
answer	ˈɑːnsə(r)	vt. 符合；回答
lift	lɪft	vt. 举起；提升；空运；鼓舞； [ 口 ] 抄袭
cake	keɪk	n. 蛋糕；块状物；利益总额
biscuit	ˈbɪskɪt	n. 小点心，饼干
like	laɪk	vt. 喜欢；愿意；想
want	wɒnt	vt. 需要；缺少；应该；希望
fresh	freʃ	adj. 新鲜的；无经验的；淡水的；清新的
egg	eɡ	n. 蛋；卵子； [ 俚 ] 家伙
butter	ˈbʌtə(r)	vt. 涂黄油于； [ 口 ] 讨好
pure	pjʊə(r)	adj. 纯的；纯洁的；清白的；纯粹的；纯理论的
honey	ˈhʌni	n. 蜂蜜；甜蜜；宝贝
ripe	raɪp	adj. 熟的，成熟的；时机成熟的
banana	bəˈnɑːnə	n. 香蕉；喜剧演员；大鹰钩鼻
jam	dʒæm	n. 果酱；拥挤；困境； [ 篮球 ] 扣篮； vt. 使堵塞；挤进，使塞满；混杂；压碎； vi. 堵塞；轧住
sweet	swiːt	adj. 甜的；悦耳的；芳香的；亲切的； n. 糖果；乐趣；芳香；宝贝；（俚）酷毙了
whisky	ˈwɪski	n. 威士忌酒
choice	tʃɔɪs	n. 选择；选择权；精选品
apple	ˈæp(ə)l	n. 苹果； [ 俚 ] 家伙
wine	waɪn	n. 酒，葡萄酒；紫红色
beer	bɪə(r)	n. 啤酒
blackboard	ˈblækbɔːd	n. 黑板
liquor	ˈlɪkə(r)	n. 酒，含酒精饮料；液体；烈酒；溶液
alcohol	ˈælkəhɒl	n. 酒精，乙醇
butcher	ˈbʊtʃə(r)	vt. 屠杀
meat	miːt	n. 肉，肉类（食用）
beef	biːf	n. 牛肉；食用牛；肌肉；牢骚
lamb	læm	n. 羔羊，小羊；羔羊肉
mutton	ˈmʌtn	n.羊肉,<谑>绵羊,<俚>女性生殖器,性交
husband	ˈhʌzbənd	n.丈夫
chicken	ˈtʃɪkɪn	n. 小鸡；鸡肉；胆小鬼，懦夫
tell	tel	vt. 告诉，说；吩咐；辨别；断定
truth	truːθ	n. 事实；真理；实质；诚实
either	ˈaɪðə(r)	adj. 两者之中任一的；两者之中每一的
neither	ˈnaɪðə(r)	conj. 既不；也不
tomato	təˈmɑːtəʊ	n. 番茄，西红柿
potato	pəˈteɪtəʊ	n. 土豆，马铃薯
cabbage	ˈkæbɪdʒ	n. 卷心菜，甘蓝菜，（俚）脑袋
pea	piː	n. [ 植 ] 豌豆
        """,
        """
bean	biːn	n. 豆； [ 动 ] 嘴峰； [ 美口 ] 毫无价值的东西
pear	peə(r)	n. 梨树；梨子
grape	ɡreɪp	n. 葡萄；葡萄树；葡萄色；葡萄酒
peach	piːtʃ	n. 桃子；桃树；桃红色； [ 美俚 ] 受人喜欢的人（或物）
climate	ˈklaɪmət	n. 气候；风气；思潮；风土
pleasant	ˈplez(ə)nt	adj. 令人愉快的，舒适的；讨人喜欢的，和蔼可亲的
weather	ˈweðə(r)	n. 天气；气象；处境；气候
spring	sprɪŋ	n. 春天；弹簧；泉水；跳跃；活力
warm	wɔːm	adj. 温暖的；热情的
rain	reɪn	n. 雨；雨天；雨季；下雨
sometimes	ˈsʌmtaɪmz	adv. 有时，间或
summer	ˈsʌmə(r)	n. 夏季；全盛时期
autumn	ˈɔːtəm	n. 秋天；成熟期；渐衰期，凋落期
winter	ˈwɪntə(r)	n. 冬季；年岁；萧条期
snow	snəʊ	n. 雪，积雪；下雪
January	ˈdʒænjuəri	n.一月(略作Jan)
February	ˈfebruəri	n.二月(略作Feb)
march	mɑːtʃ	n. 进行曲；行军，进军；示威游行
April	ˈeɪprəl	n.四月(略作Apr)
may	may	aux. 可能，可以；愿意
June	dʒuːn	n.六月(略作Jun)
July	dʒʊˈlaɪ	n.七月(略作Jul)
august	ɔːˈɡʌst	adj.令人敬畏的,威严的n.八月(略作Aug) August 八月
September	sepˈtembə(r)	n.九月(略作Sep)
October	ɒkˈtəʊbə(r)	n.十月(略作Oct)
November	nəʊˈvembə(r)	n.十一月(略作Nov)
December	dɪˈsembə(r)	n.十二月(略作Dec)
mild	maɪld	adj. 温和的；轻微的；淡味的；文雅的；不含有害物质的的
always	ˈɔːlweɪz	adv. 总是；永远，一直；常常
north	nɔːθ	n. 北，北方
south	saʊθ	n. 南方，南边；南部
east	iːst	n. 东方；东方国家；东风
west	west	n. 西；西方；西部
season	ˈsiːz(ə)n	n. 时期；季节；赛季； vt. 给 … 调味；使适应； vi. 变得成熟；变干燥
best	best	n. 最好的人，最好的事物；最佳状态
night	naɪt	n. 夜晚，晚上；黑暗，黑夜
rise	raɪz	vi. 上升；起立；高耸；增强
set	set	n. 集合；一套；布景；装置；趋势
late	leɪt	adj. 迟的；晚的；已故的；最近的
interesting	ˈɪntrəstɪŋ	adj. 有趣的；引起兴趣的，令人关注的
subject	ˈsʌbdʒɪkt	n. 主题；科目；国民；主语
conversation	ˌkɒnvəˈseɪʃ(ə)n	n. 会话；交谈；社交
Australia	ɒˈstreɪliə	n.澳洲,澳大利亚
Australian	ɒˈstreɪliən	adj. 澳大利亚的，澳大利亚人的
Canada	ˈkænədə	n.加拿大
Canadian	kəˈneɪdiən	adj. 加拿大（人）的
India	ˈɪndiə	n.印度(南亚国家),南亚次大陆(即印巴次大陆,简称次大陆,含印度、巴基斯坦、孟加拉国、尼泊尔、不丹、锡金等国)
Indian	ˈɪndiən	adj. 印度的；印第安人的；印第安语的
Turkey	ˈtɜːki	n. 火鸡；失败之作；笨蛋
Polish	ˈpəʊlɪʃ	n. 磨光，擦亮；上光剂，擦亮剂；优雅，精良
live	lɪv	adj. 活的；实况转播的；精力充沛的；生动的
love	lʌv	n. 恋爱；酷爱；亲爱的；喜爱的事物
lie	laɪ	vi. 说谎；位于；躺；展现
living	ˈlɪvɪŋ	adj. 活的；现存的；活跃的；逼真的
stay	steɪ	vi. 停留；暂住；停下；坚持
say	seɪ	vt. 讲；说明；指明；假设；例如；声称
home	həʊm	n. 家，住宅；家乡；产地；避难所
lunch	lʌntʃ	n. 午餐
afternoon	ˌɑːftəˈnuːn	n. 午后，下午
usually	ˈjuːʒuəli	adv. 通常，经常
together	təˈɡeðə(r)	adv. 一起；总共；同时；连续地；相互
evening	ˈiːvnɪŋ	n. 傍晚；晚上；后期；（联欢性的）晚会
arrive	əˈraɪv	vi. 到达；成功；出生；达成
depart	dɪˈpɑːt	vi. 离开；出发，起程；违反；去世
shop	ʃɒp	n. 商店；店铺
moment	ˈməʊmənt	n. 瞬间；重要时刻；指定时刻；重要，契机
clock	klɒk	n. 时钟；计时器
store	stɔː(r)	n. 储备，贮藏；仓库；商店
envelope	ˈenvələʊp	n. 信封，封皮； [ 生 ] 包膜； [ 天 ] 包层； [ 数 ] 包迹
writing	ˈraɪtɪŋ	n. 笔迹；作品；书写；著作
paper	ˈpeɪpə	n. 纸；文件；报纸；论文
page	peɪdʒ	n. 页；大事件，时期；记录；男侍者
size	saɪz	n. 大小；尺寸
pad	pæd	n. 衬垫；护具；便笺簿；填补； vi. 步行；放轻脚步走； vt. 填补；走
glue	ɡluː	vt. 粘合；似胶般固着于
chalk	tʃɔːk	vt. 用粉笔写；用白垩粉擦；记录；规划
change	tʃeɪndʒ	vt. 改变；交换
feel	fiːl	vt. 感觉；试探；触摸；认为
look	lʊk	vt. 看；期待；注意；面向；看上去像
call	kɔːl	vi. 呼叫；拜访；叫牌
        """,
        """
doctor	ˈdɒktə(r)	n. 医生；博士
telephone	ˈtelɪfəʊn	n. （美）电话；电话机；电话耳机
remember	rɪˈmembə(r)	vt. 记得；牢记；纪念；代 … 问好
tongue	tʌŋ	n. 语言；舌头
bad	bæd	adj. 坏的；严重的；劣质的
headache	ˈhedeɪk	n. 头痛；麻烦；令人头痛之事
medicine	ˈmedsn	n. 药；医学；内科；巫术
temperature	ˈtemprətʃə(r)	n. 温度
flu	fluː	n. 流感
better	ˈbetə(r)	n. 较好者；打赌的人（等于 bettor ）；长辈
yet	jet	adv. 还；但是；已经
rich	rɪtʃ	adj. 富有的；肥沃的；昂贵的
food	fuːd	n. 食物；养料
remain	rɪˈmeɪn	vi. 保持；依然；留下；剩余；逗留；残存
play	pleɪ	vt. 演奏；播放；游戏；扮演；同 … 比赛
match	mætʃ	vt. 使相配；与 … 竞争；相配；使比赛；敌得过，比得上
talk	tɔːk	vt. 说；谈话；讨论
library	ˈlaɪbrəri	n. 图书馆，藏书室；文库
drive	draɪv	vi. 开车；猛击；飞跑
so	səʊ	adv. 如此，这么；确是如此
quickly	ˈkwɪkli	adv. 很快地；迅速地
break	breɪk	n. 休息，中断；破裂处
key	kiː	n. 钥匙；关键；（打字机等的）键
baby	ˈbeɪbi	n. 婴儿，婴孩；孩子气的人
hear	hɪə(r)	vt. 审理；听说；听到，听
enjoy	ɪnˈdʒɔɪ	vt. 喜爱；欣赏，享受；使过得快活
yourself	jɔːˈself	pron. 你自己
ourselves	ɑːˈselvz	pron. 我们自己；我们亲自
myself	maɪˈself	pron. 我自己；我亲自；我的正常的健康状况和正常情绪
themselves	ðəmˈselvz	pron. 他们自己；他们亲自
himself	hɪmˈself	pron. 他自己；他亲自，他本人
herself	hɜːˈself	pron. 她自己（ she 的反身代词）；她亲自
grocer	ˈɡrəʊsə	n. 杂货店；食品商
Monday	ˈmʌndeɪ	n.星期一
Tuesday	ˈtjuːzdeɪ	n.星期二
Wednesday	ˈwenzdeɪ	n.星期三
Thursday	ˈθɜːzdeɪ	n.星期四
Friday	ˈfraɪdeɪ	n.星期五
Saturday	ˈsætədeɪ	n.星期六
Sunday	ˈsʌndeɪ	n.星期日
keep	kiːp	vt. 保持；遵守；经营；饲养
spend	spend	vt. 花费；度过，消磨（时光）；用尽；浪费
weekend	ˌwiːkˈend	n. 周末，周末休假；周末聚会
weekday	ˈwiːkdeɪ	n. 平日，工作日
lucky	ˈlʌki	adj. 幸运的；侥幸的
church	tʃɜːtʃ	n. 教堂；礼拜；教派
dairy	ˈdeəri	n. 乳品店；制酪场；牛奶及乳品业；乳牛；奶制品
year	jɪə(r)	n. 年；历年；年度
race	reɪs	n. 种族，人种；家庭，门弟；属，种
town	taʊn	n. 城镇，市镇；市内商业区
crowd	kraʊd	vt. 挤满；将 … 塞进；催逼，催促
stand	stænd	vi. 位于；站立；停滞
exciting	ɪkˈsaɪtɪŋ	adj. 使人激动的；令人兴奋的
just	dʒʌst	adv. 正好，恰好；只是，仅仅；刚才，刚刚；实在；刚要
finish	ˈfɪnɪʃ	vt. 完成；结束；用完
winner	ˈwɪnə(r)	n. 胜利者
time	taɪm	n. 时间；次数；时代；节拍；倍数
phone	fəʊn	n. 电话；耳机，听筒
again	əˈɡen	adv. 再一次；又，此外
week	wiːk	n. 周，星期
suddenly	ˈsʌd(ə)nli	adv. 忽然；突然地
bus	bʌs	n. 公共汽车
stop	stɒp	vt. 堵塞；停止；断绝
smile	smaɪl	vi. 微笑
understand	ˌʌndəˈstænd	vt. 懂；理解；获悉；推断；省略
speak	spiːk	vi. 说话；演讲；陈述；表明
pocket	ˈpɒkɪt	n. 口袋；钱；容器
phrase	freɪz	n. 成语；习语；措词
slowly	ˈsləʊli	adv. 缓慢地，慢慢地
cut	kʌt	n. 伤口；切口；削减；（服装等的）式样； [ 体育 ] 削球；切入； vt. 切割；削减；缩短；刺痛； vi. 切割；相交；切牌；停拍；不出席； adj. 割下的；雕过的；缩减的
go	ɡəʊ	vi. 走；趋于；达到；运转
greet	ɡriːt	vt. 欢迎，迎接；致敬，致意；映入眼帘
ago	əˈɡəʊ	adv. 以前，以往
buy	baɪ	vt. 购买；获得；贿赂
pair	peə(r)	n. 一对，一双，一副
fashion	ˈfæʃ(ə)n	n. 样式；时尚；时装；时髦人物
uncomfortable	ʌnˈkʌmftəb(ə)l	adj. 不安的；不舒服的
comfortable	ˈkʌmftəb(ə)l	adj. 舒适的，舒服的
wear	weə(r)	vt. 穿着，戴；磨损
appointment	əˈpɔɪntmənt	n. 任命；约定；任命的职位
        """,
        """
date	deɪt	n. 日期；约会；年代；枣椰子
till	tɪl	n. 放钱的抽屉；备用现金；冰碛
still	stɪl	adv. 更；仍然；静止地
until	ʌnˈtɪl	conj. 在 … 以前；直到 … 时
shopping	ˈʃɒpɪŋ	n. 购物，买东西
list	lɪst	n. 列表；目录；清单
vegetable	ˈvedʒtəb(ə)l	n. 蔬菜；植物；植物人
need	niːd	n. 需要，要求；必要之物；缺乏
hope	həʊp	n. 希望；信心；期望
thing	θɪŋ	n. 东西；事物；事情；情况
fruit	fruːt	n. 水果；产物
chemist	ˈkemɪst	n. 药剂师；化学家
bath	bɑːθ	n. 浴室；沐浴；浴盆
birth	bɜːθ	n. 出生；血统，出身；起源
nearly	ˈnɪəli	adv. 差不多，几乎；密切地
ready	ˈredi	adj. 准备好；情愿的；现成的；迅速的；快要 … 的
dinner	ˈdɪnə(r)	n. 宴会；正餐；晚餐，晚宴
supper	ˈsʌpə(r)	n. 晚餐，晚饭
roast	rəʊst	vt. 烤，焙；烘，烘烤；暴露于某种热力下以得温暖
breakfast	ˈbrekfəst	n. 早餐；早饭
fast	fɑːst	adj. 快速的，迅速的；紧的，稳固的
haircut	ˈheəkʌt	n. 理发；发型
party	ˈpɑːti	n. 聚会，派对；政党，党派； [ 律 ] 当事人
holiday	ˈhɒlədeɪ	n. 假日；节日；休息日
vacation	veɪˈkeɪʃ(ə)n	n. 假期；（房屋）搬出
mess	mes	n. 食堂，伙食团；混乱；脏乱的东西；困境
pack	pæk	n. 包裹；一副；一群；包装；背包
already	ɔːlˈredi	adv. 已经，早已；先前
cinema	ˈsɪnəmə	n. 电影院；电影；电影业，电影制作术
film	fɪlm	n. 胶卷；电影；薄膜；轻烟
movie	ˈmuːvi	n. 电影；电影院；电影业
beautiful	ˈbjuːtɪf(ə)l	adj. 美丽的
beauty	ˈbjuːti	n. 美；美人；美好的东西；美丽
city	ˈsɪti	n. 城市，都市
never	ˈnevə(r)	adv. 决不；从未
ever	ˈevə(r)	adv. 曾经；究竟；永远
garage	ˈɡærɑːʒ	n. 车库；汽车修理厂；飞机库
crash	kræʃ	n. 碰撞；崩溃；坠落
repair	rɪˈpeə(r)	vi. 修复；修理
try	traɪ	vt. 试验；审判；考验；试图，努力
lamp	læmp	n. 灯；照射器
believe	bɪˈliːv	vi. 信任；料想；笃信宗教
since	sɪns	conj. 既然；因为；由于；自 … 以来；自 … 以后
why	waɪ	int. 哎呀！什么？
sell	sel	vt. 销售；出卖；推销；欺骗
because	bɪˈkəz; bɪˈkɒz	conj. 因为
retire	rɪˈtaɪə(r)	vi. 退休；撤退；退却
tire	ˈtaɪə(r)	vi. 疲劳；厌倦
cost	kɒst	vt. 花费；使付出；使花许多钱
pound	paʊnd	n. 英镑；兽栏；重击，重击声；拘留所
worth	wɜːθ	adj. 值 … 的
penny	ˈpeni	n.便士,<美> 分
cent	sent	n.(货币单位)分,分币
move	muːv	n. 迁居；步骤；移动
neighbour	ˈneɪbə(r)	n.邻居(美作:neighbor)
person	ˈpɜːs(ə)n	n. 人； [ 语法 ] 人称；身体；容貌，外表
people	ˈpiːp(ə)l	n. 人；人类；民族；公民
poor	pɔː(r)	adj. 可怜的；贫穷的；贫乏的；卑鄙的
pilot	ˈpaɪlət	n. 领航员；飞行员
return	rɪˈtɜːn	vt. 返回；报答； n. 返回；归还；回球； vi. 返回；报答； adj. 报答的；回程的；返回的
train	treɪn	n. 火车；行列；长队；裙裾
platform	ˈplætfɔːm	n. 月台，站台；讲台；平台；坛
station	ˈsteɪʃ(ə)n	n. 站；地位；身分；驻地
plenty	ˈplenti	n. 丰富，大量；充足
bar	bɑː(r)	n. 酒吧；条，棒；障碍
subway	ˈsʌbweɪ	n. 地铁；地道
describe	dɪˈskraɪb	vt. 描述，形容；描绘
label	ˈleɪb(ə)l	vt. 标注；贴标签于
handle	ˈhænd(ə)l	n. 柄；把手；手感；口实
address	əˈdres	vt. 写姓名地址；向 … 致辞；演说；从事；忙于
slip	slɪp	vi. 犯错；滑倒；减退；滑动；失足
fall	fɔːl	vi. 落下；变成；来临；减弱
hurt	hɜːt	vt. 使受伤；使疼痛；使痛心； [ 口 ] 损害
back	bæk	n. 背部；后面；靠背；足球等的后卫；书报等的末尾
help	help	vt. 帮助；治疗；促进；补救
sure	ʃʊə(r)	adj. 可靠的；必定的；确信的
x-ray	ˈeks reɪ	vt. 用 X 光线检查
card	kɑːd	n. 卡片；纸牌；明信片
youth	juːθ	n. 青春；青年；青少年时期；年轻
hotel	həʊˈtel	n. 旅馆，饭店；客栈
        """,
        """
association	əˌsəʊsiˈeɪʃn;	n. 协会，联盟，社团；联合；联想
soon	suːn	adv. 快；立刻；不久，一会儿；宁愿
write	raɪt	vi. 写，写字；写作，作曲；写信
exam	ɪɡˈzæm	n. 测验；考试
test	test	n. 检验；试验
text	tekst	n. 文本；课文；主题； vt. 发短信
pass	pɑːs	n. 及格；经过；护照；途径； [ 体 ] 传球； vi. 经过；传递；变化；终止； vt. 通过；经过；传递
mathematics	ˌmæθəˈmætɪks	n. 数学；数学运算
compute	kəmˈpjuːt	vt. 计算；估算；用计算机计算
question	ˈkwestʃən	n. 问题，疑问；询问；疑问句
easy	ˈiːzi	adj. 容易的；舒适的
enough	ɪˈnʌf	adv. 足够地，充足地
extra	ˈekstrə	adv. 特别地，非常；另外;n.群众演员
fail	feɪl	vi. 失败，不及格；衰退；缺乏；破产
failure	ˈfeɪljə(r)	n. 失败；故障；失败者；破产
respond	rɪˈspɒnd	vi. 回答；作出反应；承担责任
response	rɪˈspɒns	n. 响应；反应；回答
mark	mɑːk	n. 马克；符号；痕迹；标志
score	skɔː(r)	n. 刻痕；配乐；分数；二十
rest	rest	vt. 使休息，使轻松；把 … 寄托于
difficult	ˈdɪfɪkəlt	adj. 困难的；不随和的；执拗的
hate	heɪt	vt. 憎恨；厌恶；遗憾
low	ləʊ	adj. 低的，浅的；粗俗的；卑贱的；消沉的
cheer	tʃɪə(r)	vt. 欢呼；使高兴；为 … 加油
gay	ɡeɪ	adj. 快乐的；放荡的；艳丽的
top	tɒp	n. 顶部，顶端；上部；首席；陀螺
clever	ˈklevə(r)	adj. 聪明的；机灵的；熟练的
intelligent	ɪnˈtelɪdʒənt	adj. 聪明的；智能的；理解力强的
stupid	ˈstjuːpɪd	adj. 愚蠢的；麻木的；乏味的
foolish	ˈfuːlɪʃ	adj. 愚蠢的；傻的
fool	fuːl	vi. 开玩笑；欺骗；戏弄
cheap	tʃiːp	adj. 便宜的； [ 口 ] 小气的；不值钱的
expensive	ɪkˈspensɪv	adj. 昂贵的；花钱的
inexpensive	ˌɪnɪkˈspensɪv	adj. 便宜的
stale	steɪl	adj. 不新鲜的；陈腐的
sour	ˈsaʊə(r)	adj. 酸的；酸臭的；讨厌的；刺耳的；发酵的
loud	laʊd	adj. 大声的，高声的；不断的；喧吵的
high	haɪ	adj. 高的；高级的；高音调的；崇高的
soft	sɒft	adj. 温柔的，温和的；软的，柔软的；笨的；软弱的
salt	sɔːlt	n. 盐；风趣，刺激性
spell	spel	vt. 拼，拼写；拼成；意味着；迷住；招致；轮值
mistake	mɪˈsteɪk	n. 错误；误会；过失
error	ˈerə(r)	n. 错误；误差；过失
fault	fɔːlt	n. 故障；毛病；缺点；错误； [ 地 ] 断层；（网球等）发球失误
present	ˈprez(ə)nt	vt. 呈现；介绍；提出；赠送
gift	ɡɪft	n. 礼物；赠品；天赋
dictionary	ˈdɪkʃən(ə)ri	n. 字典；词典
carry	ˈkæri	vt. 拿，扛；搬运；携带；支持
correct	kəˈrekt	vt. 改正；告诫
madam	ˈmædəm	n. 夫人；女士；鸨母
pretty	ˈprɪti	adj. 漂亮的；可爱的；优美的
idea	aɪˈdɪə	n. 主意；概念；想法
spoon	spuːn	n. 匙，勺子；一杓的量
less	les	adv. 较少地；较小地；更小地
pity	ˈpɪti	n. 怜悯，同情；遗憾
instead	ɪnˈsted	adv. 代替；反而
advice	ədˈvaɪs	n. 通知；忠告；建议；劝告
most	məʊst	adv. 最；最多；非常，极其；几乎
least	liːst	adj. 最小的；最少的（ little 的最高级）
worse	wɜːs	n.(人或事)较坏者,更坏的事,更恶劣的事,败北adj.更坏的,更恶劣的adv.更坏地,更恶劣地
worst	wɜːst	adj.最坏的,最差的adv.最坏,最差
model	ˈmɒd(ə)l	n. 模型；模范；模特儿；典型；样式
afford	əˈfɔːd	vt. 给予，提供；买得起
afraid	əˈfreɪd	adj. 害怕的；担心的；恐怕
deposit	dɪˈpɒzɪt	n. 存款；保证金；沉淀物
price	praɪs	n. 价格；代价；价值
pay	peɪ	vt. 支付，付；偿还，补偿
payment	ˈpeɪmənt	n. 付款，支付；报酬，报答；惩罚，报应；偿还
conductor	kənˈdʌktə(r)	n. 导体；售票员；领导者；管理人
fare	feə(r)	vi. 进展；过活；经营；遭遇
passenger	ˈpæsɪndʒə(r)	n. 乘客；旅客；过路人； [ 俚 ] 碍手碍脚的人
none	nʌn	pron. 没有人；没有任何东西；一个也没有
tramp	træmp	vi. 脚步沉重地行走；践踏，踩；流浪
except	ɪkˈsept	vt. 不计；把 … 除外
abroad	əˈbrɔːd	adv. 到海外；在国外
worry	ˈwʌri	n. 烦恼；担心；撕咬 v. (使)担心
reporter	rɪˈpɔːtə(r)	n. 记者
future	ˈfjuːtʃə(r)	n. 未来；期货；前途；将来时
feature	ˈfiːtʃə(r)	n. 容貌；特色，特征；特写或专题节目
carrier	ˈkæriə(r)	n. 载体；货架；运送者；带菌者
        """,
        """
introduce	ˌɪntrəˈdjuːs	vt. 介绍；引进；采用；提出
description	dɪˈskrɪpʃn	n. 描述，描写；说明书；类型
football	ˈfʊtbɔːl	n. 足球，橄榄球
soccer	ˈsɒkə(r)	n. 英式足球，足球
pool	puːl	n. 联营；水塘；撞球；共同资金
win	wɪn	vt. 赢得；在 … 中获胜；劝诱
world	wɜːld	n. 世界；全人类；世俗；领域；宇宙；物质生活
word	wɜːd	n. 单词；话语；消息；诺言；命令
depend	dɪˈpend	vi. 依赖，依靠；相信，信赖；取决于
overseas	ˌəʊvəˈsiːz	adv. 在海外，海外
engineering	ˌendʒɪˈnɪərɪŋ	n. 工程，工程学
company	ˈkʌmpəni	n. 公司；陪伴，同伴；连队
line	laɪn	n. 绳；排；路线，航线
opposite	ˈɒpəzɪt	adj. 相反的；对面的；对立的
funny	ˈfʌni	adj. 有趣的；奇异的；滑稽的
powder	ˈpaʊdə(r)	n. 粉末；粉；火药；尘土
ugly	ˈʌɡli	adj. 丑陋的；令人厌恶的；邪恶的
regularly	ˈreɡjələli	adv. 有规律地；定期地；整齐地；匀称地
surround	səˈraʊnd	vt. 围绕；包围
round	raʊnd	n. 圆形物；一回合；圆；循环
wood	wʊd	n. 木材；树林；木制品
forest	ˈfɒrɪst	vt. 植树于，使成为森林
jungle	ˈdʒʌŋɡ(ə)l	n. 丛林，密林；危险地带
spot	spɒt	n. 斑点；地点
hundred	ˈhʌndrəd	n. 一百；许多
through	θruː	prep. 穿过；通过；凭借
visitor	ˈvɪzɪtə(r)	n. 访问者，参观者；视察者；候鸟
guest	ɡest	n. 客人，宾客；顾客
place	pleɪs	n. 地方；住所；座位
throw	θrəʊ	vt. 抛；投；掷
rubbish	ˈrʌbɪʃ	n. 垃圾，废物；废话
garbage	ˈɡɑːbɪdʒ	n. 垃圾；废物
cover	ˈkʌvə(r)	vt. 包括；涉及；采访，报导
piece	piːs	n. 件；篇；块；硬币
tyre	ˈtaɪə(r)	n. 轮胎；轮箍；（Tyre）提尔
rusty	ˈrʌsti	adj. 生锈的，腐蚀的；铁锈色的，锈色的；迟钝的
among	əˈmʌŋ	prep. 在 … 中间；在 … 之中
signature	sɪɡnətʃə(r)	n. 署名；签名；信号
dear	dɪə(r)	adj. 亲爱的；昂贵的；尊敬的
deer	dɪə(r)	n. 鹿
limit	ˈlɪmɪt	n. 限度；限制；界线
license	ˈlaɪs(ə)ns	n. 执照，许可证；特许
problem	ˈprɒbləm	n. 难题；引起麻烦的人
broad	brɔːd	adj. 宽的，辽阔的；显著的；大概的
board	bɔːd	n. 木板；甲板；膳食；董事会
report	rɪˈpɔːt	n. 报告；报道；成绩单
journalist	ˈdʒɜːnəlɪst	n. 记日志者；新闻工作者；报人
noun	naʊn	n. [ 语 ] 名词
police	pəˈliːs	n. 警察，警方；治安
hostess	ˈhəʊstəs	n. 女主人，女老板；舞女；女服务员；女房东
lady	ˈleɪdi	n. 女士，夫人；小姐；妻子
feather	ˈfeðə(r)	n. 羽毛
cousin	ˈkʌz(ə)n	n. 堂兄弟姊妹；表兄弟姊妹
he	hiː	n. 它（雄性动物）；男孩，男人
she	ʃiː	pron. 她（主格）；它（用来指雌性动物或国家、船舶、地球、月亮等）
married	ˈmærid	adj. 已婚的，有配偶的；婚姻的，夫妇的；密切结合的
marry	ˈmæri	vt. 娶；嫁；与 … 结婚
introduction	ˌɪntrəˈdʌkʃn	n. 介绍；传入；入门；采用；引进
dependent	dɪˈpendənt	adj. 依靠的；从属的；取决于 … 的
lose	luːz	vt. 使沉溺于；浪费；遗失；使迷路；错过
excite	ɪkˈsaɪt	vt. 刺激 … ，使 … 兴奋；激起
amuse	əˈmjuːz	vt. 使发笑；消遣；娱乐；使愉快
laugh	lɑːf	n. 笑；引人发笑的事或人
embarrass	ɪmˈbærəs	vt. 使局促不安；使困窘；阻碍
curious	ˈkjʊəriəs	adj. 好奇的，有求知欲的；古怪的；爱挑剔的
conversion	kənˈvɜːʃ(ə)n	n. 转换；变换；改变信仰；兑换
gray	ɡreɪ	adj. 灰色的；灰白头发的；苍白的；阴郁的
staff	stɑːf	n. 职员；支撑；参谋；棒
visit	ˈvɪzɪt	n. 访问；参观；逗留
pull	pʊl	vt. 拉；拖；拔
push	pʊʃ	vt. 推动；推行；逼迫；增加
sport	spɔːt	n. 运动；运动会；游戏；娱乐；玩笑
rust	rʌst	n. 锈；生锈；锈病
desert	ˈdezət	vt. 遗弃；放弃；逃跑
shift	ʃɪft	n. 手段；移动；轮班；变化
plant	plɑːnt	n. 工厂，车间；植物；庄稼；设备
planet	ˈplænɪt	n. 行星
star	stɑː(r)	n. 星，恒星；明星；星形物
smoke	sməʊk	n. 烟；抽烟；无常的事物
smog	smɒɡ	n. 烟雾
        """,
        """
ceiling	ˈsiːlɪŋ	n. 天花板；上限
whom	huːm	pron. 谁（ who 的宾格）
before	bɪˈfɔː(r)	prep. 在 … 之前，先于
alone	əˈləʊn	adj. 单独的；孤独的；独自的
entry	ˈentri	n. 入口；进入；登记；条目；对土地的侵占； [ 商 ] 报关手续
head	hed	n. 头；上端；最前的部分；头痛；理解力
print	prɪnt	n. 印刷业；印刷字体；印记；印花布；印章
careless	ˈkeələs	adj. 粗心的；无忧无虑的；淡漠的
choose	tʃuːz	vt. 选择，决定
chess	tʃes	n. 国际象棋，西洋棋
private	ˈpraɪvət	adj. 私人的；私有的；私下的
theatre	ˈθɪətə(r)	n. 剧场，戏院
seat	siːt	n. 座位；所在地；职位
angry	ˈæŋɡri	adj. 生气的；愤怒的；狂暴的；（伤口等）发炎的
attention	əˈtenʃ(ə)n	n. 注意力；关心；立正！（口令）
bear	beə(r)	vi. 结果实；承受
business	ˈbɪznəs	n. 商业；生意；交易；事情
outside	ˌaʊtˈsaɪd	adj. 外面的，外部的；外来的
aunt	ɑːnt	n. 伯母；阿姨；舅妈；姑妈
repeat	rɪˈpiːt	vt. 重复；复制；背诵
inside	ˌɪnˈsaɪd	n. 内部；里面；内脏；内情
ant	ænt	n. 蚂蚁
sheep	ʃiːp	n. 羊，绵羊；胆小鬼
spoil	spɔɪl	vt. 溺爱；糟蹋；掠夺
museum	mjuˈziːəm	n. 博物馆
public	ˈpʌblɪk	adj. 公用的；公众的；公立的；政府的
friendly	ˈfrendli	adj. 友好的；亲切的；支持的；融洽的，和睦的
waiter	ˈweɪtə(r)	n. 服务员，侍者
lend	lend	vt. 贷；增添，提供；把 … 借给
decision	dɪˈsɪʒ(ə)n	n. 决定，决心；决议
whole	həʊl	adj. 完整的；纯粹的
single	ˈsɪŋɡ(ə)l	adj. 单一的；单身的；单程的
borrow	ˈbɒrəʊ	vi. 借；借用；从其他语言中引入
double	ˈdʌb(ə)l	n. 两倍； [ 计 ] 双精度型
receive	rɪˈsiːv	vt. 收到；接待；接纳
firm	fɜːm	adj. 坚定的；结实的；牢固的；严格的
different	ˈdɪfrənt	adj. 不同的；个别的，与众不同的
centre	ˈsentə(r)	n. （地理位置）中间，中心点
get	ɡet	vt. 获得；变成；使得；受到
farm	fɑːm	vi. 种田，务农；经营农场
form	fɔːm	n. 形式，形状；形态，外形；表格；方式
from	frɒm	prep. 来自，从；今后；由于
pigeon	ˈpɪdʒɪn	n. 鸽子
message	ˈmesɪdʒ	n. 消息；预言；启示；广告词；差使
distance	ˈdɪstəns	n. 距离；远方；疏远；间隔
request	rɪˈkwest	n. 请求；需要
beggar	ˈbeɡə(r)	n. 乞丐；穷人； [ 英口 ] 家伙
bag	bæɡ	n. 袋；猎获物；（俚）一瓶啤酒； vt. 猎获；把 … 装入袋中； [ 口 ] 占据，私吞；使膨大； vi. 松垂
beg	beɡ	vt. 恳求；乞讨；回避正题
view	vjuː	n. 风景；意见；视野；观察
airport	ˈeəpɔːt	n. 机场；航空站
expect	ɪkˈspekt	vt. 预料；期望；指望； [ 口 ] 认为
valuable	ˈvæljuəb(ə)l	adj. 贵重的；有价值的；可估价的
parcel	ˈpɑːs(ə)l	n. 包裹，小包
diamond	ˈdaɪmənd	n. 钻石，金刚石；菱形；方块牌
steal	stiːl	v.偷,窃取,偷窃,偷盗
main	meɪn	n. 主要部分，要点；体力；总管道
guard	ɡɑːd	n. 警戒；守卫；护卫队；防护装置
precious	ˈpreʃəs	adj. 宝贵的；珍贵的；矫揉造作的
stone	stəʊn	n. 石头；宝石；结石
sand	sænd	n. 沙；沙子；沙滩；沙地；沙洲
object	ˈɒbdʒɪkt	n. 宾语；客体；目标；物体
driver	ˈdraɪvə(r)	n. 驾驶员；驱动程序；起子；传动器
brake	breɪk	vi. 刹车
competition	ˌkɒmpəˈtɪʃ(ə)n	n. 竞争；比赛，竞赛
neat	niːt	adj. 整洁的；未搀水的；优雅的；灵巧的；齐整的；平滑的
path	pɑːθ	n. 小路；道路；轨道
wooden	ˈwʊdn	adj. 木制的；僵硬的，呆板的
pond	pɒnd	vt. 筑成池塘
keen	kiːn	adj. 敏锐的，敏捷的；热心的；锐利的；渴望的；强烈的
lean	liːn	vi. 倾斜；倚靠；倾向；依赖
annual	ˈænjuəl	adj. 年度的；每年的
yearly	ˈjɪəli	adj. 每年的
welcome	ˈwelkəm	adj. 受欢迎的
gather	ˈɡæðə(r)	vt. 收集；使 … 聚集；收割；使 … 皱起
shout	ʃaʊt	vi. 呼喊；喊叫；大声说
refuse	rɪˈfjuːz	n. 废物；垃圾
jazz	dʒæz	n. 爵士乐，爵士舞；喧闹
musical	ˈmjuːzɪk(ə)l	adj. 音乐的；悦耳的
instrument	ˈɪnstrəmənt	n. 仪器；器械；工具；手段；乐器
        """,
        """
recently	ˈriːs(ə)ntli	adv. 最近；新近
damage	ˈdæmɪdʒ	vi. 损害；损毁；赔偿金
string	strɪŋ	n. 线，细绳；一串，一行
shock	ʃɒk	n. 打击；震动；震惊；休克；禾束堆
allow	əˈlaʊ	vt. 允许；认可；给予
justice	ˈdʒʌstɪs	n. 正义；司法，法律制裁；法官，审判员
destroy	dɪˈstrɔɪ	vt. 毁坏；破坏；消灭
ruin	ˈruːɪn	n. 毁坏；灭亡；废墟
turn	tɜːn	vt. 转动，使旋转；转弯；兑换；翻过来
deserve	dɪˈzɜːv	vi. 应受，应得
lawyer	ˈlɔɪə(r)	n. 律师；法学家
salary	ˈsæləri	vt. 给 ... 加薪；给 ... 薪水
luck	lʌk	n. 运气；幸运；带来好运的东西
captain	ˈkæptɪn	n. 船长；队长，首领；上尉；海军上校
sail	seɪl	vi. 航行；启航，开船
harbour	ˈhɑːbə(r)	n. 港口；避难所，避风港
proud	praʊd	adj. 自豪的；自负的；得意的
important	ɪmˈpɔːt(ə)nt	adj. 重要的，重大的；有地位的；有权力的
great	ɡreɪt	adj. 伟大的，重大的；极好的，好的；主要的
importance	ɪmˈpɔːt(ə)ns	n. 价值；重要；重大；傲慢
group	ɡruːp	n. 组；团体
club	klʌb	n. 俱乐部，社团；夜总会；（扑克牌中的）梅花；棍棒
performance	pəˈfɔːməns	n. 性能；表演；执行；绩效
occasion	əˈkeɪʒ(ə)n	n. 场合；时机，机会；理由
computer	kəmˈpjuːtə(r)	n. 电脑；计算机；电子计算机
issue	ˈɪʃuː	n. 流出；发行物；问题；期号
occasionally	əˈkeɪʒnəli	adv. 偶而，间或
experience	ɪkˈspɪəriəns	n. 经验；经历；体验
reply	rɪˈplaɪ	vi. 回答；回击；答辩
language	ˈlæŋɡwɪdʒ	n. 语言；语言文字；表达能力
journey	ˈdʒɜːni	n. 旅行；行程
urgent	ˈɜːdʒənt	adj. 紧急的；急迫的
think	θɪŋk	vt. 想；认为；想像；想起；打算
wing	wɪŋ	n. 翅膀；翼；飞翔；派别
wind	wɪnd	vt. 缠绕；上发条；吹号角；使弯曲；绕住或缠住某人
born	bɔːn	v. 出世（ bear 的过去分词）
tag	tæɡ	n. 标签；结束语；附属物；名称
secretary	ˈsekrət(ə)ri	n. 秘书；书记；大臣；部长
nervous	ˈnɜːvəs	adj. 神经的；紧张不安的；强健有力的
weak	wiːk	adj. 疲软的；虚弱的；无力的；不牢固的
interrupt	ˌɪntəˈrʌpt	vt. 中断；妨碍；插嘴；打断
secret	ˈsiːkrət	n. 秘密；秘诀；机密
nerve	nɜːv	n. 神经；勇气； [ 植 ] 叶脉
height	haɪt	n. 高度；高地；身高；顶点
bottom	ˈbɒtəm	n. 底部；臀部；末端；尽头
traffic	ˈtræfɪk	n. 交通；贸易；运输；通信量
area	ˈeəriə	n. 面积；区域，地区；范围
obey	əˈbeɪ	vt. 服从，听从；按照 …… 行动
remind	rɪˈmaɪnd	vt. 提醒；使想起
appear	əˈpɪə(r)	vi. 出现；似乎；显得； [ 法 ] 出庭
stage	steɪdʒ	n. 舞台；戏剧；驿站；阶段
bright	braɪt	adj. 明亮的，鲜明的；聪明的；愉快的
stocking	ˈstɒkɪŋ	n. 长袜
stock	stɒk	n. 股份，股票；血统；树干；库存
sock	sɒk	n. 短袜；一击
state	steɪt	n. 情形；国家；州
strange	streɪndʒ	adj. 陌生的；奇怪的；外行的
stranger	ˈstreɪndʒə(r)	n. 陌生人；外地人；局外人
disappear	ˌdɪsəˈpɪə(r)	vi. 消失；失踪；不复存在
appearance	əˈpɪərəns	n. 外貌，外观；出现，露面
landlord	ˈlændlɔːd	n. 房东，老板；地主
bill	bɪl	n. 帐单；法案；广告；钞票；票据；清单
hurry	ˈhʌri	n. 匆忙，急忙
exclaim	ɪkˈskleɪm	vi. 呼喊，惊叫；大声叫嚷
sadly	ˈsædli	adv. 悲哀地；悲痛地
fisherman	ˈfɪʃəmən	n. 渔夫；渔人
boot	buːt	vt. 使穿靴；引导；踢；解雇
waste	weɪst	n. 废物；浪费；荒地；损耗；地面风化物
realize	ˈriːəlaɪz	vt. 实现；了解；将某物卖得；认识到
real	ˈriːəl	adj. 真实的；实在的；实际的
fish	fɪʃ	vi. 捕鱼，钓鱼；用钩捞取
sad	sæd	adj. 悲哀的，令人悲痛的；难过的；凄惨的，阴郁的（形容颜色）
mad	mæd	adj. 疯狂的；愚蠢的；着迷的；发疯的
reason	ˈriːz(ə)n	n. 理由；理性；动机
sum	sʌm	n. 总数；金额
mud	mʌd	vt. 弄脏；用泥涂
age	eɪdʒ	n. 年龄；时代；阶段；寿命，使用年限
channel	ˈtʃæn(ə)l	vt. 引导，开导；形成河道
complete	kəmˈpliːt	adj. 完全的；完整的；彻底的
modern	ˈmɒd(ə)n	adj. 现代的，近代的；时髦的
        """,
        """
district	ˈdɪstrɪkt	n. 区域；行政区；地方
upset	ˌʌpˈset	vt. 颠覆；扰乱；使心烦
sympathetic	ˌsɪmpəˈθetɪk	adj. 同情的；赞同的；和谐的；合意的； [ 物 ] 共鸣的； [ 解 ] 交感神经的
complain	kəmˈpleɪn	vi. 发牢骚；投诉；诉说
wicked	ˈwɪkɪd	adj. 邪恶的；顽皮的；恶劣的；不道德的
contain	kənˈteɪn	vt. 包含；容纳；控制；牵制（敌军）
honesty	ˈɒnəsti	n. 诚实，正直
include	ɪnˈkluːd	vt. 包含，包括
honest	ˈɒnɪst	adj. 诚实的，实在的；可靠的；坦率的
container	kənˈteɪnə(r)	n. 容器；集装箱
railway	ˈreɪlweɪ	n. （英）铁路；铁道部门；轨道
porter	ˈpɔːtə(r)	n. 门房；守门人；服务员；行李搬运工
foreigner	ˈfɒrənə(r)	n. 外地人，外国人
wonder	ˈwʌndə(r)	n. 惊奇；奇迹；惊愕
railroad	ˈreɪlrəʊd	vt. 由铁道运输；铺设铁路； [ 俚 ] 以捏造不实之罪使入狱
wonderful	ˈwʌndəf(ə)l	adj. 奇妙的；极好的
several	ˈsevrəl	adj. 几个的；各自的
foreign	ˈfɒrən	adj. 外国的； [ 医 ] 异质的；不相关的；外交的
art	ɑːt	n. 艺术；美术；艺术品
critic	ˈkrɪtɪk	n. 批评家，评论家；爱挑剔的人
pretend	prɪˈtend	vt. 假装；伪称；假扮
pattern	ˈpæt(ə)n	n. 模式；样品；图案
curtain	ˈkɜːt(ə)n	n. 窗帘；幕
material	məˈtɪəriəl	adj. 物质的，实质性的；重要的；肉体的
appreciate	əˈpriːʃieɪt	vt. 欣赏(appreciate music)；感激；鉴别(appreciate wine)；领会
notice	ˈnəʊtɪs	n. 通知，布告；注意；公告
whether	ˈweðə(r)	conj. 是否；不论
hang	hæŋ	vt. 绞死；悬挂，垂下；使悬而未决；装饰
tent	tent	n. 帐篷；住处；帷幕
field	fiːld	n. 领域；牧场；旷野；战场；运动场
smell	smel	vi. 嗅，闻；有 … 气味（异味、怪味）
creep	kriːp	vi. 爬行；慢慢地移动；起鸡皮疙瘩；蔓延
leap	liːp	vi. 跳，跳跃
heavily	ˈhevɪli	adv. 沉重地；沉闷地；猛烈地
stream	striːm	n. 溪流；流动；光线；潮流
rare	reə(r)	adj. 半熟的；稀有的；稀薄的
ancient	ˈeɪnʃənt	adj. 古代的；古老的，过时的；年老的
trouble	ˈtrʌb(ə)l	n. 麻烦；动乱；烦恼；故障
effect	ɪˈfekt	n. 效果；作用；影响
affect	əˈfekt	vt. 影响；假装；感动；感染
steam	stiːm	vt. 蒸，散发；用蒸汽处理
taste	teɪst	n. 味道；品味；审美
taxi	ˈtæksi	vi. 乘出租车；滑行
land	lænd	n. 陆地；地面；国土
plough	plaʊ	v. 耕，犁；（用或仿佛用犁）划出（沟或线）
lonely	ˈləʊnli	adj. 寂寞的；偏僻的
roof	ruːf	n. 屋顶；最高处，顶部；最高限度
block	blɒk	n. 块；街区；障碍物；大厦
flat	flæt	adj. 平坦的；单调的；浅的；扁平的
apartment	əˈpɑːtmənt	n. 房间；公寓
row	rəʊ	n. 行，排；吵闹；划船；街道
kick	kɪk	vt. 踢；反冲，朝后座
sight	saɪt	n. 视力；眼界；景象；见解
column	ˈkɒləm	n. 圆柱，柱形物；纵队，列；专栏
straight	streɪt	adj. 直的；正直的；整齐的；连续的；笔直的
bicycle	ˈbaɪsɪk(ə)l	n. 自行车
save	seɪv	vt. 节省；保存；解救；储蓄
workshop	ˈwɜːkʃɒp	n. 工场；车间；讲习班；研讨会
employ	ɪmˈplɔɪ	vt. 使用，采用；雇用；使忙于，使从事于
grandson	ˈɡrænsʌn	n. 孙子；外孙
bike	baɪk	n. 自行车；脚踏车
once	wʌns	adv. 一次；曾经
temptation	tempˈteɪʃ(ə)n	n. 引诱；诱惑物
article	ˈɑːtɪk(ə)l	n. 物品；文章；冠词；条款
wrap	ræp	vt. 包；隐藏；缠绕；掩护
simply	ˈsɪmpli	adv. 简单地；朴素地；简直；仅仅；坦白地
arrest	əˈrest	vt. 吸引；逮捕；阻止
explain	ɪkˈspleɪn	v. 说明；解释
coast	kəʊst	vi. 沿岸航行；滑行
storm	stɔːm	n. 暴风雨；大动荡
rock	rɒk	n. 岩石；暗礁；摇滚乐
shore	ʃɔː(r)	vt. 用支柱撑住；支撑，使稳住
ahead	əˈhed	adj. 向前；在前的；领先； adv. 向前的；领先的
cliff	klɪf	n. 绝壁；悬崖
struggle	ˈstrʌɡ(ə)l	vi. 挣扎；奋斗，努力
hospital	ˈhɒspɪt(ə)l	n. 医院
regret	rɪˈɡret	n. 遗憾；抱歉；悲叹
far	fɑː(r)	adv. 很；到很远的距离；遥远地；到很深的程度；久远地
rush	rʌʃ	n. 匆促；急流；冲进；灯心草
act	ækt	vt. 扮演；装作，举动像
        """,
        """
shortly	ˈʃɔːtli	adv. 立刻；简短地；唐突地
record	ˈrekɔːd	vt. 记录，记载；将 ... 录音；标明
strong	strɒŋ	adj. 强壮的；擅长的；坚强的；牢固的
succeed	səkˈsiːd	vi. 成功；继承；继任；兴旺
intend	ɪnˈtend	vt. 打算；意指；想要
solid	ˈsɒlɪd	adj. 固体的；结实的；可靠的；一致的；立体的
hold	həʊld	vt. 持有；保存；拥有；拘留；约束或控制
government	ˈɡʌvənmənt	n. 政府；管辖；政体
immense	ɪˈmens	adj. 巨大的，广大的；无边无际的； [ 口 ] 非常好的
stadium	ˈsteɪdiəm	n. 露天大型运动场；体育场
standard	ˈstændəd	n. 标准；度量衡标准；旗；水准
capital	ˈkæpɪt(ə)l	n. 首都，省会；大写字母；资金；资本家
design	dɪˈzaɪn	vt. 设计；计划；构思
Mediterranean	ˌmedɪtəˈreɪniən	n. 地中海
bitterly	ˈbɪtəli	adv. 残酷地；怨恨地；苦涩地，悲痛的
sunshine	ˈsʌnʃaɪn	n. 阳光；愉快；晴天；快活
operation	ˌɒpəˈreɪʃ(ə)n	n. 操作；手术；经营；运算
successful	səkˈsesf(ə)l	adj. 成功的；一帆风顺的
following	ˈfɒləʊɪŋ	adj. 其次的，接着的；下面的
patient	ˈpeɪʃ(ə)nt	adj. 有耐性的，能容忍的
exchange	ɪksˈtʃeɪndʒ	n. 交换；兑换；交易所；交流
inquire	ɪnˈkwaɪə(r)	vt. 询问；问明；查究
certain	ˈsɜːt(ə)n	adj. 某一；确信；无疑的；有把握的；必然的
relative	ˈrelətɪv	adj. 相对的；有关系的；成比例的
tight	taɪt	adj. 紧的；绷紧的；没空的；密封的；严厉的；麻烦的；吝啬的
fix	fɪks	vt. 安装；修理；使固定；准备
globe	ɡləʊb	n. 地球仪；地球；球体
despair	dɪˈspeə(r)	n. 绝望；令人绝望的人或事
rude	ruːd	adj. 粗鲁的；无礼的；狂暴的；未开化的
mirror	ˈmɪrə(r)	n. 镜子；真实的写照；榜样
hole	həʊl	n. 洞，孔；洞穴，穴；突破口
remark	rɪˈmɑːk	n. 言辞；注意
market	ˈmɑːkɪt	n. 集市；市场；商店；销路；股票市场；行情；市面
pipe	paɪp	n. 烟斗；管；笛
tune	tjuːn	n. 和谐；曲调；心情
glimpse	ɡlɪmps	n. 一瞥(无意中)，一看
snake	sneɪk	n. 蛇；阴险的人
movement	ˈmuːvmənt	n. 运动；乐章；活动；运转
continue	kənˈtɪnjuː	vi. 仍旧，连续；继续，延续
dance	dɑːns	n. 舞蹈；舞曲；舞会
obviously	ˈɒbviəsli	adv. 明显地
difference	ˈdɪfrəns	n. 差异；不同；争执
pole	pəʊl	n. 杆；极点；电极
flight	flaɪt	n. 飞行；班机；逃走
serious	ˈsɪəriəs	adj. 严肃的，严重的；认真的；庄重的；危急的
point	pɔɪnt	n. 要点；尖端；得分；标点
seem	siːm	vi. 似乎；像是；装作
sack	sæk	n. 麻布袋；洗劫
clear	klɪə(r)	adj. 清楚的；清澈的；晴朗的；无罪的
aircraft	ˈeəkrɑːft	n. 飞机，航空器
endless	ˈendləs	adj. 无止境的；环状的；连续的；漫无目的的
plain	pleɪn	adj. 平的；朴素的；简单的；清晰的
risk	rɪsk	n. 危险；风险；冒险
picnic	ˈpɪknɪk	n. 野餐
edge	edʒ	n. 边缘；刀刃；优势；锋利
strap	stræp	vt. 用带捆绑；用皮条抽打；约束
possession	pəˈzeʃ(ə)n	n. 财产；拥有；领地；自制；着迷
breath	breθ	n. 呼吸，气息；一口气，（呼吸的）一次；微风；瞬间，瞬息；迹象； [ 语 ] 无声音，气音
mend	mend	vt. 修理，修补；改善；修改
conscience	ˈkɒnʃəns	n. 道德心，良心
wallet	ˈwɒlɪt	n. 钱包，皮夹
unload	ˌʌnˈləʊd	vt. 卸；摆脱 … 之负担；倾销
extremely	ɪkˈstriːmli	adv. 非常，极其；极端地
occur	əˈkɜː(r)	vi. 发生；出现；存在
astonish	əˈstɒnɪʃ	vt. 使惊讶
pile	paɪl	n. 堆；大量；建筑群
woollen	ˈwʊlən	n.毛织品adj.羊毛制的
discover	dɪˈskʌvə(r)	vt. 发现；发觉
admit	ədˈmɪt	vt. 承认；准许进入；可容纳
confine	kənˈfaɪn	n. 界限，边界
normal	ˈnɔːm(ə)l	adj. 正常的；正规的，标准的
ghost	ɡəʊst	n. 鬼，幽灵
furniture	ˈfɜːnɪtʃə(r)	n. 家具；设备；储藏物
suggest	səˈdʒest	vt. 提议，建议；启发；使人想起
shake	ʃeɪk	vt. 摇动；动摇；震动
accept	əkˈsept	vt. 接受；承认；承担；承兑；容纳
collect	kəˈlekt	vt. 收集；募捐
collection	kəˈlekʃ(ə)n	n. 收藏品；募捐；征收；采集，聚集
nod	nɒd	n. 点头；打盹；摆动
meanwhile	ˈmiːnwaɪl	adv. 同时，其间
        """,
        """
success	səkˈses	n. 成功，成就；胜利；大获成功的人或事物
successfully	səkˈsesfəli	adv. 顺利地；成功地
employer	ɪmˈplɔɪə(r)	n. 雇主，老板
simple	ˈsɪmp(ə)l	adj. 简单的；单纯的；天真的
decide	dɪˈsaɪd	vt. 决定；判决；解决
explanation	ˌekspləˈneɪʃ(ə)n	n. 说明，解释；辩解
interpreter	ɪnˈtɜːprətə(r)	n. 口译者；解释者； [ 机 ] 注释器
interpretation	ɪnˌtɜːprəˈteɪʃ(ə)n	n. 解释；翻译；演出
interpret	ɪnˈtɜːprət	vt. 说明；口译
translation	trænzˈleɪʃ(ə)n	n. 翻译；译文；转化；调任
transformer	trænsˈfɔːmə(r)	n. 变压器；促使变化的人
pour	pɔː(r)	n. 倾泻；流出；骤雨
vocabulary	vəˈkæbjələri	n. 词汇；词表
action	ˈækʃ(ə)n	n. 行动；活动；功能；情节；战斗
fight	faɪt	vi. 打架；打仗；搏斗，斗争
battery	ˈbætri	n. 电池，蓄电池
anxious	ˈæŋkʃəs	adj. 渴望的；担忧的；焦虑的；急切的
liquid	ˈlɪkwɪd	adj. 液体的；清澈的；明亮的；易变的
gas	ɡæs	n. 气体；汽油；毒气；瓦斯
intention	ɪnˈtenʃ(ə)n	n. 意图；目的；意向； [ 医 ] 愈合
stable	ˈsteɪb(ə)l	n. 马厩；牛棚
recorder	rɪˈkɔːdə(r)	n. 录音机；记录器；记录员；八孔直笛
follow	ˈfɒləʊ	vt. 跟随；追求；遵循；密切注意
explore	ɪkˈsplɔː(r)	vt. 探测；探索；探险
discovery	dɪˈskʌvəri	n. 发现，发觉；被发现的事物
plan	plæn	n. 平面图；计划
dangerous	ˈdeɪndʒərəs	adj. 危险的
danger	ˈdeɪndʒə(r)	n. 危险；危险物，威胁
huge	hjuːdʒ	adj. 巨大的；庞大的；无限的
vast	vɑːst	adj. 巨大的；广阔的；大量的；巨额的
tiny	ˈtaɪni	adj. 微小的；很少的
enormous	ɪˈnɔːməs	adj. 庞大的，巨大的；凶暴的，极恶的
tremendous	trəˈmendəs	adj. 极大的，巨大的；惊人的
content	ˈkɒntent	n. 内容，目录；容量；满足
farmer	ˈfɑːmə(r)	n. 农夫，农民
administration	ədˌmɪnɪˈstreɪʃ(ə)n	n. 管理；行政；行政机构；实施
bitter	ˈbɪtə(r)	adj. 苦的；痛苦的；充满仇恨的；尖刻的
playground	ˈpleɪɡraʊnd	n. 运动场，操场
gymnasium	dʒɪmˈneɪziəm	n. 体育馆；健身房
countryside	ˈkʌntrisaɪd	n. 农村，乡下；乡下的全体居民
thirty	ˈθɜːti	n. 三十年代
relation	rɪˈleɪʃ(ə)n	n. 关系；亲属关系；叙述；故事
kilometer	kɪˈlɒmɪtə(r)	n.[物]千米,公里
kind	kaɪnd	n. 种类；性质
tiger	ˈtaɪɡə(r)	n. 老虎；凶暴的人
host	həʊst	n. 主人；主机；主持人；许多
earth	ɜːθ	n. 地球；泥土；地线；尘世；陆地
observe	əbˈzɜːv	vt. 注意到；观察；遵守；说；评论
music	ˈmjuːzɪk	n. 音乐，乐曲
raise	reɪz	vt. 提高；筹集；养育；升起
rose	rəʊz	n. 玫瑰，蔷薇；玫瑰红
clearly	ˈklɪəli	adv. 明净地；清晰地；无疑地；明显地
differ	ˈdɪfə(r)	vt. 使 … 相异；使 … 不同
lay	leɪ	vt. 搁放；躺下；产卵
strike	straɪk	vi. 罢工；打，打击；敲，敲击；抓；穿透；打动
breathe	briːð	vi. 呼吸；低语；松口气；（风）轻拂
purse	pɜːs	n. 钱包，小钱袋；金钱，财源
rob	rɒb	vt. 抢劫；使 … 丧失；非法剥夺
detect	dɪˈtekt	vt. 发现；察觉；探测
load	ləʊd	n. 负载，负荷；装载量；工作量
wool	wʊl	n. 羊毛；毛线；绒线；毛织品；毛料衣物
fact	fækt	n. 事实；实际；真相
theory	ˈθɪəri	n. 理论；原理；学说；推测
suggestion	səˈdʒestʃən	n. 示意；建议；微量，细微的迹象
brandy	ˈbrændi	n. 白兰地酒
owner	ˈəʊnə(r)	n. 所有者；物主
glance	ɡlɑːns	n. 一瞥；一滑；闪光
ride	raɪd	vi. 骑马；乘车；漂浮；依靠
excursion	ɪkˈskɜːʃn	n. 短程旅行；远足；离题；偏移；游览圆
reward	rɪˈwɔːd	n. 报酬；酬谢；报答
virtue	ˈvɜːtʃuː	n. 美德；优点；贞操；功效
forbid	fəˈbɪd	vt. 禁止；妨碍，阻止
strict	strɪkt	adj. 严格的；精确的；绝对的；详细的
inch	ɪntʃ	n. 英寸；身高；少许
space	speɪs	n. 空间；距离；太空
actually	ˈæktʃuəli	adv. 实际上；事实上
fireman	ˈfaɪəmən	n. 消防队员；救火队员；锅炉工
cause	kɔːz	n. 原因；事业；目标
examine	ɪɡˈzæmɪn	vt. 调查；考试；检查； [ 计算机 ] 检测
remains	rɪˈmeɪnz	n. 残余；遗骸
        """,
        """
wire	ˈwaɪə(r)	n. 金属丝；电线；电报
volt	vəʊlt	n.(马术中的)环骑,[击剑]闪避,[电工]伏特
solve	sɒlv	vt. 解决；解答；溶解
mystery	ˈmɪst(ə)ri	n. 神秘，秘密；神秘的事物；奥秘
spark	spɑːk	n. 火花；闪光；朝气
sticky	ˈstɪki	adj. 粘的；粘性的
finger	ˈfɪŋɡə(r)	n. 手指；指针，指状物
pie	paɪ	n. 馅饼；饼图；爱说话的人
mix	mɪks	vt. 使混和；配制；混淆；使结交
receiver	rɪˈsiːvə(r)	n. 接收器；接受者；收信机；收款员，接待者
persuade	pəˈsweɪd	vt. 说服，劝说；使某人相信；劝某人做（不做）某事
register	ˈredʒɪstə(r)	vt. 记录；注册；登记；把 … 挂号；挂号邮寄；正式提出
gold	ɡəʊld	n. 金，黄金；金币；金色
mine	maɪn	n. 矿山，矿井；矿，矿藏；地雷，水雷
treasure	ˈtreʒə(r)	n. 财富，财产；财宝；珍品
invent	ɪnˈvent	vt. 发明；创造；虚构
bury	ˈberi	vt. 埋葬；隐藏
cave	keɪv	vt. 使凹陷，使塌落；在 … 挖洞穴
arm	ɑːm	n. 手臂；袖子；武器；装备
soil	sɔɪl	n. 土壤；土地；国家；温床；务农；粪便
entrance	ˈentrəns	n. 入口；进入
finally	ˈfaɪnəli	adv. 终于；最后；决定性地
worthless	ˈwɜːθləs	adj. 无价值的；卑微的；不值钱的
trunk	trʌŋk	n. 树干；躯干；象鼻；汽车车尾的行李箱
confident	ˈkɒnfɪdənt	adj. 自信的；确信的
value	ˈvæljuː	n. 价值；价格；重要性；值；确切涵义
handsome	ˈhænsəm	adj. （男子）英俊的；大方的，慷慨的；可观的；健美而端庄的
wheel	wiːl	n. 车轮；方向盘；转动
explosion	ɪkˈspləʊʒ(ə)n	n. 爆炸；爆发；激增
course	kɔːs	n. 进程；过程；道路；一道菜
rival	ˈraɪv(ə)l	n. 竞争者；对手
hesitate	ˈhezɪteɪt	vi. 踌躇，犹豫；不愿
punish	ˈpʌnɪʃ	vt. 严厉对待；惩罚； [ 口 ] 贪婪地吃喝
fur	fɜː(r)	n. 毛皮；皮，皮子；软毛
eager	ˈiːɡə(r)	adj. 渴望的；热心的；热切的
disguise	dɪsˈɡaɪz	vt. 假装；掩饰；隐瞒
possess	pəˈzes	vt. 持有；控制；迷住；使掌握
increase	ɪnˈkriːs	n. 增加，增长；提高
evil	ˈiːv(ə)l	adj. 邪恶的；有害的；不幸的；讨厌的
reputation	ˌrepjuˈteɪʃ(ə)n	n. 声望；名声，名誉
claim	kleɪm	vi. 提出要求
victim	ˈvɪktɪm	n. 牺牲者；受害人；牺牲品
source	sɔːs	n. 来源；原始资料；水源
income	ˈɪnkʌm	n. 收入，收益；所得
bark	bɑːk	vt. 咆哮；吠叫； [ 口 ] 咳嗽
press	pres	vt. 压；按；紧抱；逼迫
paw	pɔː	vt. 抓，扒；亲昵地抚摸
expert	ˈekspɜːt	adj. 熟练的；内行的；老练的
develop	dɪˈveləp	vt. 开发；使成长；进步；使显影
habit	ˈhæbɪt	n. 习惯，习性；嗜好
remove	rɪˈmuːv	vt. 移动，迁移；开除；调动
fair	feə(r)	adj. 公平的；美丽的，白皙的；晴朗的
crystal	ˈkrɪst(ə)l	n. 水晶；结晶，晶体；水晶饰品
telescope	ˈtelɪskəʊp	vt. 压缩；使套叠
launch	lɔːntʃ	vt. 发射（导弹、火箭等）；发起，发动；使 … 下水
billion	ˈbɪljən	n. 十亿；大量
faulty	ˈfɔːlti	adj. 有缺点的；有错误的
astronaut	ˈæstrənɔːt	n. 宇航员，航天员；太空旅行者
atmosphere	ˈætməsfɪə(r)	n. 气氛；大气；空气
distant	ˈdɪstənt	adj. 遥远的；冷漠的；远隔的
universe	ˈjuːnɪvɜːs	n. 宇宙；世界；领域
control	kənˈtrəʊl	n. 控制；管理；抑制；操纵装置
threaten	ˈθret(ə)n	vt. 威胁；恐吓；预示
destruction	dɪˈstrʌkʃ(ə)n	n. 破坏，毁灭；摧毁
flood	flʌd	vt. 淹没；充满；溢出
authority	ɔːˈθɒrəti	n. 权威；权力；当局
spray	spreɪ	n. 水沫；喷雾；喷雾器
quantity	ˈkwɒntəti	n. 量，数量；总量；大量
root	ruːt	n. 根；根源；祖先；词根
century	ˈsentʃəri	n. 世纪，百年；（板球）一百分
patch	pætʃ	n. 眼罩；斑点；碎片；小块土地
circle	ˈsɜːk(ə)l	n. 圆；圆形物；循环，周期；圈子
admire	ədˈmaɪə(r)	vt. 钦佩；赞美
wedding	ˈwedɪŋ	n. 结婚；婚礼，婚宴；结合
reception	rɪˈsepʃ(ə)n	n. 接待；接收；招待会；感受；反应
sort	sɔːt	n. 种类；方式；品质
tunnel	ˈtʌn(ə)l	n. 隧道；坑道；洞穴通道
port	pɔːt	n. 港口，口岸；左舷；舱门；（计算机的）端口
chimney	ˈtʃɪmni	n. 烟囱
fear	fɪə(r)	n. 害怕；担心；恐惧；敬畏
        """,
        """
invasion	ɪnˈveɪʒ(ə)n	n. 侵犯；入侵，侵略；侵袭
connect	kəˈnekt	vt. 连接；联合；关连
European	ˌjʊərəˈpiːən	adj. 欧洲的；欧洲人的
continent	ˈkɒntɪnənt	n. 大陆，洲，陆地
Christmas	ˈkrɪsməs	n. 圣诞节
accompany	əˈkʌmpəni	vt. 陪伴，伴随；伴奏
approach	əˈprəʊtʃ	n. 接近；方法；途径
ought	ɔːt	aux. 应该，应当；大概
weigh	weɪ	vt. 权衡；考虑；称 … 重量
fortunate	ˈfɔːtʃənət	adj. 幸运的；侥幸的；带来幸运的；吉祥的
remote	rɪˈməʊt	adj. 遥远的；偏僻的；疏远的
Pacific	pəˈsɪfɪk	n. 太平洋
wreck	rek	n. 失事；残骸；破坏； [ 口 ] 失去健康的人
survey	ˈsɜːveɪ	n. 调查；测量；纵览；审视
rescue	ˈreskjuː	vt. 营救；援救
package	ˈpækɪdʒ	n. 包，包裹；套装软件，程序包
restore	rɪˈstɔː(r)	vt. 修复；恢复；归还
imagine	ɪˈmædʒɪn	vt. 想像；猜想；臆断
colony	ˈkɒləni	n. 殖民地；移民队
bee	biː	n. 蜜蜂，蜂；勤劳的人(as busy as a bee)
preserve	prɪˈzɜːv	vt. 保存；保护；维持；禁猎；腌
volcano	vɒlˈkeɪnəʊ	n. 火山
active	ˈæktɪv	adj. 主动的；有效的；积极的；活跃的；现役的
manage	ˈmænɪdʒ	vt. 管理；经营；设法；控制
brilliant	ˈbrɪliənt	adj. 灿烂的，闪耀的；杰出的；有才气的
escape	ɪˈskeɪp	vt. 逃避，避免；被忘掉
avoid	əˈvɔɪd	vt. 避免；避开，躲避；消除
insist	ɪnˈsɪst	vt. 坚持，强调
murder	ˈmɜːdə(r)	vt. 谋杀，凶杀
instruct	ɪnˈstrʌkt	vt. 命令；教授；指导；通知
acquire	əˈkwaɪə(r)	vt. 获得；学到；取得；捕获
confidence	ˈkɒnfɪdəns	n. 信心；信任；秘密
suppose	səˈpəʊz	vt. 假设；认为；推想；让（虚拟语气）
react	riˈækt	vi. 反应；影响；反抗；起反作用
drunk	drʌŋk	v. 喝酒（ drink 的过去分词）
wander	ˈwɒndə(r)	vi. 徘徊；漫步；离题；迷路
bull	bʊl	n. 公牛；看好股市者；粗壮如牛的人； [ 俚 ] 胡说八道；印玺
sensitive	ˈsensətɪv	adj. 敏感的；感光的；灵敏的；易受伤害的
criticism	ˈkrɪtɪsɪzəm	n. 批评；苛求；考证
bow	bəʊ	n. 弓；鞠躬；船首
safety	ˈseɪfti	n. 安全；保险；安全设备；保险装置；安打
parliament	ˈpɑːləmənt	n. 议会，国会
erect	ɪˈrekt	vt. 使竖立；建造；安装
accurate	ˈækjərət	adj. 精确的
official	əˈfɪʃ(ə)l	adj. 正式的；官方的；公务的
check	tʃek	vi. 证明无误；核对无误；将军（象棋）
microphone	ˈmaɪkrəfəʊn	n. 扩音器，麦克风
tower	ˈtaʊə(r)	n. 塔；高楼；堡垒
per	pə(r)	prep. 每；每一；经；按照
horsepower	ˈhɔːspaʊə(r)	n. 马力（功率单位）
burst	bɜːst	vi. 爆炸；爆发，突发
average	ˈævərɪdʒ	n. 平均；平均数； [ 商 ] 海损
footstep	ˈfʊtstep	n. 脚步声；脚步；足迹
meantime	ˈmiːntaɪm	n. 其时，其间
lorry	ˈlɒri	n.<古>卡车,铁路货车
border	ˈbɔːdə(r)	n. 边界；边境；国界
precaution	prɪˈkɔːʃ(ə)n	n. 预防，警惕；预防措施
fan	fæn	vt. 煽动；吹拂；刺激
shady	ˈʃeɪdi	adj. 成荫的；阴暗的；名声不好的
thick	θɪk	n. 最拥挤部份；活动最多部份；事物的粗大浓密部份
signal	ˈsɪɡnəl	n. 信号；暗号；导火线
stamp	stæmp	n. 邮票；印记；跺脚；标志
helicopter	ˈhelɪkɒptə(r)	n. 直升飞机
scene	siːn	n. 情景；景象；场面；事件
announcer	əˈnaʊnsə(r)	n. 广播员；宣告者
leading	ˈliːdɪŋ	adj. 主要的；领导的
splendid	ˈsplendɪd	adj. 辉煌的；极好的；灿烂的；杰出的
process	ˈprəʊses	vt. 处理；加工
champion	ˈtʃæmpiən	n. 冠军；拥护者；战士
own	əʊn	vt. 拥有；承认
really	ˈriːəli	adv. 实际上，事实上；真正地，真实地；真的吗？（表语气）
miracle	ˈmɪrək(ə)l	n. 奇迹，奇迹般的人或物；惊人的事例
fountain	ˈfaʊntən	n. 喷泉，泉水；源泉
mat	mæt	n. 垫；垫子；衬边
cushion	ˈkʊʃ(ə)n	n. 垫子；起缓解作用之物；（猪等的）臀肉；银行储蓄
breeze	briːz	n. 微风；轻而易举的事；煤屑；焦炭渣；小风波
blow	bləʊ	n. 吹；殴打；打击
bowl	bəʊl	n. 碗；木球；大酒杯
crack	kræk	vt. 使破裂；打开；变声
court	kɔːt	n. 法院；朝廷；球场；奉承
        """,
        """
yard	jɑːd	n. 码（英制中丈量长度单位， 1 码 =3 英尺）； [ 海 ] 帆桁；院子；庭院
voyage	ˈvɔɪɪdʒ	n. 航行；航程；旅行记
tour	tʊə(r)	n. 旅游，旅行；巡回演出
sightseeing	ˈsaɪtsiːɪŋ	n. 观光；游览
guilty	ˈɡɪlti	adj. 有罪的；内疚的
occasional	əˈkeɪʒən(ə)l	adj. 偶然的；临时的；特殊场合的
merit	ˈmerɪt	n. 优点，价值；功绩；功过
shortcoming	ˈʃɔːtkʌmɪŋ	n. 缺点；短处
foot	fʊt	n. 脚；英尺；末尾；步调
quite	kwaɪt	adv. 很；相当；完全
quit	kwɪt	vt. 停止；放弃；离开；使 … 解除
wireless	ˈwaɪələs	adj. 无线的；无线电的
slave	sleɪv	n. 奴隶；从动装置
piano	piˈænəʊ	n. 钢琴
reveal	rɪˈviːl	vt. 揭露；显示；透露；泄露
invention	ɪnˈvenʃ(ə)n	n. 发明；发明物；发明才能；虚构
army	ˈɑːmi	n. 陆军，军队
completely	kəmˈpliːtli	adv. 完全地，彻底地；完整地
mineral	ˈmɪnərəl	n. 矿物； [ 化 ] 无机物；（英）矿泉水；苏打水（常用复数表示）
golden	ˈɡəʊldən	adj. 金色的，黄金般的；金制的；珍贵的
explosive	ɪkˈspləʊsɪv	adj. 爆炸的；爆炸性的；爆发性的
explode	ɪkˈspləʊd	vi. 爆炸，爆发；激增
bomb	bɒm	vt. 轰炸，投弹于
punishment	ˈpʌnɪʃmənt	n. 惩罚；严厉对待，虐待
significant	sɪɡˈnɪfɪkənt	adj. 重大的；有意义的；有效的；值得注意的；意味深长的
vital	ˈvaɪt(ə)l	adj. 生死攸关的；至关重要的；有活力的
essential	ɪˈsenʃ(ə)l	adj. 必要的；基本的；本质的；精华的
gain	ɡeɪn	n. 收获；增加；利润
earn	ɜːn	vt. 获得，挣得；赚，赚得；博得；使得到
pick	pɪk	vi. 挑选；采摘；挖； vt. 拾取；精选；采摘；掘； n. 选择；鹤嘴锄；挖； [ 篮球 ] 掩护
bless	bles	vt. 祝福；保佑；赞美
sauce	sɔːs	n. 调味汁；沙司；酱油
resource	rɪˈsɔːs	n. 资源，财力；办法；智谋
rent	rent	n. 租金
interest	ˈɪntrəst	n. 兴趣，爱好；利息；趣味；同行
universal	ˌjuːnɪˈvɜːs(ə)l	adj. 通用的；普遍的；全体的；全世界的
robot	ˈrəʊbɒt	n. 机器人；遥控设备，自动机械；机械般工作的人
diary	ˈdaɪəri	n. 日记簿；日志，日记
surroundings	səˈraʊndɪŋz	n. 环境；周围的事物
construct	kənˈstrʌkt	vt. 建造，构造；创立
construction	kənˈstrʌkʃn	n. 建筑物；建设；解释；造句
quality	ˈkwɒləti	n. 质量，品质；特性；才能
weight	weɪt	n. 重量，重力；砝码；重要性；负担
Europe	ˈjʊərəp	n.欧洲
fortunately	ˈfɔːtʃənətli	adv. 幸运地
unfortunate	ʌnˈfɔːtʃənət	adj. 不幸的；令人遗憾的；不成功的
unfortunately	ʌnˈfɔːtʃənətli	adv. 不幸地
fortune	ˈfɔːtʃuːn	n. 运气；财富；命运
Atlantic	ətˈlæntɪk	n.大西洋 adj.大西洋的
investigation	ɪnˌvestɪˈɡeɪʃ(ə)n	n. 调查；调查研究
have	hæv	vt. 有；让；从事；允许；拿
cow	kaʊ	n. 奶牛，母牛；母兽
passive	ˈpæsɪv	adj. 被动的，消极的；被动语态的
violence	ˈvaɪələns	n. 激烈；暴力；侵犯；歪曲
violent	ˈvaɪələnt	adj. 猛烈的；暴力的
persist	pəˈsɪst	vi. 持续，固执；存留，坚持
instruction	ɪnˈstrʌkʃ(ə)n	n. 指令，命令；指示；用法说明；教导
rainbow	ˈreɪnbəʊ	n. 彩虹；五彩缤纷的排列；幻想
aware	əˈweə(r)	adj. 知道的；意识到的；有 … 方面知识的；懂世故的
imagination	ɪˌmædʒɪˈneɪʃ(ə)n	n. 空想；想象力；幻想物
shame	ʃeɪm	n. 羞耻，羞愧；憾事，带来耻辱的人
ashamed	əˈʃeɪmd	adj. 惭愧的，感到难为情的；耻于 … 的
survive	səˈvaɪv	vt. 比 ... 活得长；幸免于；幸存；生还
even	ˈiːv(ə)n	adj. 偶数的；相等的；平坦的
temple	ˈtemp(ə)l	n. 庙宇；寺院；神殿；太阳穴
disease	dɪˈziːz	n. 病，疾病；弊病
prove	pruːv	vt. 证明；检验；显示
skin	skɪn	n. 皮肤；外皮
section	ˈsekʃ(ə)n	n. 章节；地区；截面；部门
figure	ˈfɪɡə(r)	n. 图形；数字；（人的）体形；人物；画像；价格
normally	ˈnɔːməli	adv. 正常地；通常地，一般地
announce	əˈnaʊns	vt. 宣布；述说；预示；播报
entitle	ɪnˈtaɪt(ə)l	vt. 称做 … ；定名为 … ；给 … 称号；使 … 有权利
calm	kɑːm	adj. 静的，平静的；沉着的
concentration	ˌkɒns(ə)nˈtreɪʃ(ə)	n. 集中；浓度；专心；集合；浓缩
suffer	ˈsʌfə(r)	vt. 忍受；遭受；经历
temper	ˈtempə(r)	n. 脾气；（钢等）回火；性情；倾向
appetite	ˈæpɪtaɪt	n. 食欲；嗜好
produce	prəˈdjuːs	vt. 生产；引起；创作
urge	ɜːdʒ	vt. 力劝，催促；驱策，推进
        """,
        """
satisfaction	ˌsætɪsˈfækʃ(ə)n	n. 满意，满足；赔偿；乐事； [ 宗 ] 赎罪
concentrate	ˈkɒns(ə)ntreɪt	vi. 集中；浓缩；聚集；全神贯注
satisfactory	ˌsætɪsˈfæktəri	adj. 满意的；符合要求的；赎罪的
delight	dɪˈlaɪt	n. 高兴
glad	ɡlæd	adj. 高兴的；乐意的；令人高兴的；灿烂美丽的
happy	ˈhæpi	adj. 高兴的；幸福的；巧妙的
parent	ˈpeərənt	n. 父母亲；父亲（或母亲）；根源
palace	ˈpæləs	n. 宫殿；豪华住宅；宅邸
extraordinary	ɪkˈstrɔːd(ə)n(ə)ri	adj. 特别的；非凡的；特派的；离奇的；临时的
exhibition	ˌeksɪˈbɪʃ(ə)n	n. 展览，显示；展览会；展览品
iron	ˈaɪən	n. 熨斗；坚强；烙铁
various	ˈveəriəs	adj. 各种各样的；多方面的
display	dɪˈspleɪ	n. 显示；炫耀
profit	ˈprɒfɪt	n. 利润；利益
prisoner	ˈprɪz(ə)nə(r)	n. 囚犯，犯人；刑事被告；俘虏
bush	bʊʃ	n.矮树丛,(机械)衬套
rapidly	ˈræpɪdli	adv. 迅速地；很快地；立即
uniform	ˈjuːnɪfɔːm	adj. 一致的；统一的；相同的；始终如一的；均衡的
rifle	ˈraɪf(ə)l	n. 步枪；来复枪
shoulder	ˈʃəʊldə(r)	n. 肩，肩膀；肩部
blaze	bleɪz	vt. 在树皮上刻路标；公开宣布
salute	səˈluːt	n. 敬礼；致敬，欢迎
sailor	ˈseɪlə(r)	n. 水手，海员；乘船者
creature	ˈkriːtʃə(r)	n. 动物，生物；人；创造物
peculiar	pɪˈkjuːliə(r)	adj. 特殊的；罕见的；奇怪的；独特的
election	ɪˈlekʃ(ə)n	n. 选举；当选；选择权； [ 宗 ] 上帝的选拔
defeat	dɪˈfiːt	vt. 击败，战胜；使 … 失败；挫败
opponent	əˈpəʊnənt	n. 对手；敌手；反对者
progressive	prəˈɡresɪv	adj. 进步的；先进的
agreement	əˈɡriːmənt	n. 协议；同意，一致
relieve	rɪˈliːv	vt. 解除，减轻；使不单调乏味；换 … 的班；解围；使放心
pressure	ˈpreʃə(r)	n. 压力；压迫，压强
extent	ɪkˈstent	n. 范围；程度；长度
gratitude	ˈɡrætɪtjuːd	n. 感谢的心情
inform	ɪnˈfɔːm	vt. 通知；告诉；报告
headmaster	ˌhedˈmɑːstə(r)	n. 校长
contribute	kənˈtrɪbjuːt	vt. 捐献；贡献，出力；投稿
patience	ˈpeɪʃ(ə)ns	n. 耐性，耐心；忍耐，容忍
farewell	ˌfeəˈwel	n. 告别，辞别；再会；再见
honour	ˈɒnə(r)	n.尊敬,敬意,荣誉,光荣vt.尊敬,给以荣誉
total	ˈtəʊt(ə)l	adj. 全部的；整个的；完全的
devote	dɪˈvəʊt	vt. 奉献；致力于
swing	swɪŋ	n. 摇摆；秋千；摆动；音律；涨落
companion	kəmˈpænjən	n. 同伴；朋友；手册；指南
petrol	ˈpetrəl	n. <英>汽油；深蓝绿色
drift	drɪft	n. 漂流，漂移；漂流物；趋势
gently	ˈdʒentli	adv. 轻轻地；温柔地，温和地
commit	kəˈmɪt	vt. 把 ... 交托给；指派 … 作战；使 … 承担义务；犯罪，做错事
confirm	kənˈfɜːm	vt. 确认；批准；证实；确定；使巩固
trap	træp	vt. 诱捕；使 … 受限制；使 … 陷入困境
surface	ˈsɜːfɪs	n. 表面；外观；表层
vibration	vaɪˈbreɪʃ(ə)n	n. 振动；犹豫；心灵感应
collapse	kəˈlæps	vi. 倒塌；瓦解；暴跌
drill	drɪl	n. 钻子；钻孔机；播种机；训练
layer	ˈleɪə(r)	n. 层，阶层；地层
beneath	bɪˈniːθ	prep. 在 … 之下
lower	ˈləʊə(r)	vt. 放下，降下；减弱，减少；贬低
progress	ˈprəʊɡres	n. 进步，发展；前进
smoothly	ˈsmuːðli	adv. 平稳地，平滑地；流畅地，流利地
queue	kjuː	n. 队列；长队；辫子
dull	dʌl	adj. 钝的；呆滞的；阴暗的；迟钝的；无趣的
giant	ˈdʒaɪənt	n. 巨人；巨大的动物；伟人
wit	wɪt	n. 智力；才智；智慧
cage	keɪdʒ	n. 笼，兽笼；牢房，监狱
variety	vəˈraɪəti	n. 种类；多样；杂耍
skate	skeɪt	vi. 滑冰；滑过
factor	ˈfæktə(r)	n. 因素；因数；要素；代理人
crew	kruː	n. 全体人员，全体船员；队，组
special	ˈspeʃ(ə)l	n. 专车；特刊；特价商品；特色菜；特使，特派人员
balloon	bəˈluːn	vi. 激增；膨胀如气球
royal	ˈrɔɪəl	adj. 皇家的；盛大的；高贵的；女王的；第一流的
spy	spaɪ	vt. 侦察；发现；暗中监视
shed	ʃed	vt. 散发；流出；摆脱；倾吐
tone	təʊn	n. 音调；音色；语气；色调
noble	ˈnəʊb(ə)l	adj. 贵族的；高尚的；宏伟的；惰性的
monument	ˈmɒnjumənt	n. 纪念碑；历史遗迹；不朽的作品
statue	ˈstætʃuː	n. 雕像，塑像
liberty	ˈlɪbəti	n. 自由；许可；冒失
actual	ˈæktʃuəl	adj. 真实的，实际的；现行的，目前的
copper	ˈkɒpə(r)	n. 铜；铜币； [ 俚 ] 警察
        """,
        """
support	səˈpɔːt	vt. 支持，支撑，支援；赡养，供养；扶持，帮助
framework	ˈfreɪmwɜːk	n. 结构，构架；框架，骨架
transport	ˈtrænspɔːt	n. 运输；运输机；狂喜；流放犯
site	saɪt	n. 地点；场所；位置
reluctant	rɪˈlʌktənt	adj. 勉强的；不情愿的；［罕］顽抗的
compete	kəmˈpiːt	vi. 竞争；比赛；对抗
gasp	ɡɑːsp	vi. 喘息；喘气；渴望
heaven	ˈhev(ə)n	n. 天堂；天空；极乐
definitely	ˈdefɪnətli	adv. 明确地，肯定地；清楚地，当然
shot	ʃɒt	n. 发射；炮弹；射手
aim	eɪm	vt. 把 … 对准；目的在于；引导
festival	ˈfestɪvl	n. 节日；庆祝，纪念活动；欢乐
lantern	ˈlæntən	n. 灯笼；提灯；灯笼式天窗
spectacle	ˈspektək(ə)l	n. 景象，奇观；眼镜；引人羡慕的人
guide	ɡaɪd	n. 指南；向导；入门书
frighten	ˈfraɪt(ə)n	vt. 使惊吓；吓唬 …
curiosity	ˌkjʊəriˈɒsəti	n. 好奇，好奇心；珍品，古董，古玩
machine	məˈʃiːn	n. 机械，机器；机械般工作的人；机构
also	ˈɔːlsəʊ	adv. 也；同样；而且
suitable	ˈsuːtəb(ə)l	adj. 适当的；相配的
usual	ˈjuːʒuəl	adj. 通常的，惯例的；平常的
ordinary	ˈɔːd(ə)n(ə)ri	adj. 普通的；平常的；平凡的
highly	ˈhaɪli	adv. 非常；高度地；非常赞许地
easily	ˈiːzəli	adv. 容易地；无疑地
prison	ˈprɪz(ə)n	n. 监狱；拘留所；监禁
bold	bəʊld	adj. 大胆的，英勇的；厚颜无耻的；险峻的；黑体的
elder	ˈeldə(r)	n. 年长者；长辈；老人；父辈
telegram	ˈtelɪɡræm	n. 电报
about	əˈbaʊt	prep. 关于；大约
though	ðəʊ	adv. 可是，虽然；然而；不过
thought	θɔːt	n. 思想；关心；想法；思考
as	æz	conj. 因为；依照；当 … 时；随着；虽然
all	ɔːl	adj. 全部的
down	daʊn	adv. 向下，下去；在下面
up	ʌp	adv. 向上；上涨；起来
onto	ˈɒntə	prep. 在 … 之上；对 … 了解； [ 数 ] 映射到 … 上
out	aʊt	adv. 在外；出现；出声地；不流行地；出局
for	fɔː(r)	prep. 为，为了；给；因为；对于；适合于；至于
within	wɪˈðɪn	prep. 在 … 之内
without	wɪˈðaʊt	prep. 没有；在 … 外面；超过
by	baɪ	prep. 被；经由；在 … 之前；在附近；依据；通过
at	æt	prep. 在（表示存在或出现的地点、场所、位置、空间）；向；朝；因为；忙于；以（某种价格、速度等）
could	kʊd	aux. 能够
nail	neɪl	vt. 钉；揭露；使固定
such	sʌtʃ	adj. 这样的，如此的
bathe	beɪð	vt. 沐浴；用水洗
gentleman	ˈdʒentlmən	n. 先生；绅士；有身分的人
elect	ɪˈlekt	adj. 选出的；当选的；卓越的
suspect	səˈspekt	n. 嫌疑犯
pain	peɪn	n. 疼痛；努力
plane	pleɪn	n. 平面；飞机；程度，水平
will	wɪl	n. 意志；意图；心愿；情感；遗嘱
hobby	ˈhɒbi	n. 嗜好；业余爱好
management	ˈmænɪdʒmənt	n. 管理；管理部门；经营手段；管理人员；操纵
establishment	ɪˈstæblɪʃmənt	n. 确立，制定；公司；设施
pavement	ˈpeɪvmənt	n. 人行道
equipment	ɪˈkwɪpmənt	n. 设备，装备；器材
fragment	ˈfræɡmənt	n. 碎片；片断或不完整部分
unjust	ˌʌnˈdʒʌst	adj. 不公平的，不公正的；非正义的
uncover	ʌnˈkʌvə(r)	vt. 揭露；发现；揭开
agree	əˈɡriː	vt. 同意；赞成；承认
information	ˌɪnfəˈmeɪʃ(ə)n	n. 信息，资料；通知；情报；知识
impatient	ɪmˈpeɪʃ(ə)nt	adj. 焦躁的；不耐心的
encourage	ɪnˈkʌrɪdʒ	vt. 鼓励，怂恿；激励；支持
hell	hel	n. 地狱；训斥；黑暗势力；究竟（作加强语气词）
inspect	ɪnˈspekt	vt. 检查；视察；检阅
employment	ɪmˈplɔɪmənt	n. 雇用；职业；使用
smooth	smuːð	adj. 光滑的；顺利的；平稳的
tragedy	ˈtrædʒədi	n. 悲剧；灾难；惨案
advertisement	ədˈvɜːtɪsmənt	n. 广告，宣传
and	ænd	conj. 和，与；而且；然后；就；但是
do	duː	n. 要求；规定； C 大调音阶中的第一音
they	ðeɪ	pron. 他们；她们；它们
but	bʌt	conj. 但是；然而；而是
we	wiː; wi	pron. 我们（主格）； [ 古 ] 朕，寡人；笔者，本人（作者或演讲人使用）
not	nɒt	adv. 不（用于否定句）
or	ɔː(r)	conj. 或，或者；还是
their	ðeə(r)	pron. 他们的，她们的；它们的
if	ɪf	conj. （表条件）如果；（表假设）假如；是否；即使
would	wʊd	aux. 将，将要；愿意
        """,
        """
know	nəʊ	vt. 知道；认识；懂得
when	wen	conj. 既然；当 … 时；考虑到
them	ðem; ðəm	pron. 他们；她们；它们
him	hɪm	pron. 他（宾格）
than	ðæn	conj. 比（用于形容词、副词的比较级之后）；除 … 外（用于 other 等之后）；与其 … （用于 rather 等之后）；一 … 就（用于 no sooner 等之后）
then	ðen	adv. 当时；然后；那么；于是；此外
its	ɪts	pron. 它的
our	ˈaʊə(r)	pron. 我们的
two	tuː	n. 两个
more	mɔː(r)	adv. 更多；更大程度地；此外
use	juːz	n. 使用；用途；发挥
only	ˈəʊnli	adv. 只，仅仅；不料
us	ʌs	pron. 我们
life	laɪf	n. 寿命；生活，生存
should	ʃʊd	aux. 应该；将要；可能；就
three	θriː	n. 三，三个
become	bɪˈkʌm	vi. 变成；变得；成为
let	let	vt. 允许，让；出租；假设；妨碍
begin	bɪˈɡɪn	vt. 开始
start	stɑːt	vt. 开始；启动
might	maɪt	n. 力量；势力；威力
part	pɑːt	n. 部分；角色；零件
against	əˈɡenst	prep. 反对，违反；靠；倚；防备
few	fjuː	adj. 很少的；几乎没有的
system	ˈsɪstəm	n. 系统；制度，体制；方法
program	ˈprəʊɡræm	n. 程序；计划；大纲
next	nekst	adv. 其次；然后；下次
million	ˈmɪljən	n. 百万；无数
national	ˈnæʃ(ə)nəl	adj. 国家的；国民的；民族的；国立的
month	mʌnθ	n. 月，一个月的时间
lot	lɒt	n. 抽签；命运；一堆；一块地；份额
study	ˈstʌdi	n. 学习，研究；学问；课题；书房
eye	aɪ	n. 眼睛；视力；见解，观点；眼光
side	saɪd	n. 方面；侧面；旁边
four	fɔː(r)	num. 四；四个
both	bəʊθ	adj. 两者的；两个的
provide	prəˈvaɪd	vt. 规定；提供；准备；装备
around	əˈraʊnd	adv. 到处；大约；在附近
sit	sɪt	vi. 坐；位于
away	əˈweɪ	adv. 离去，离开；在远处
power	ˈpaʊə(r)	n. 能力；力量；功率；幂；势力；政权
game	ɡeɪm	n. 游戏；比赛
often	ˈɒf(ə)n	adv. 常常，时常
political	pəˈlɪtɪk(ə)l	adj. 政治的；党派的
end	end	n. 结束；末端；目标；死亡；尽头
however	haʊˈevə(r)	adv. 无论如何；不管怎样
member	ˈmembə(r)	n. 成员；会员；议员
law	lɔː	n. 法律；法学；诉讼；法治；司法界；规律
almost	ˈɔːlməʊst	adv. 差不多，几乎
community	kəˈmjuːnəti	n. 社区；团体；共同体；群落
president	ˈprezɪdənt	n. 总统；校长；董事长
learn	lɜːn	vt. 学习；认识到；得知
team	tiːm	n. 队；组
body	ˈbɒdi	n. 身体；主体；团体；主要部分；大量
lead	liːd	n. 铅；领导；导线；榜样；石墨
social	ˈsəʊʃ(ə)l	adj. 社会的，社交的；群居的
face	feɪs	n. 脸；面容；表面；面子；威信；外观
create	kriˈeɪt	vt. 造成；创造，创作
level	ˈlev(ə)l	n. 水平；水平面；标准
add	æd	vi. 加；增加；加起来；做加法
health	helθ	n. 健康；兴旺；卫生；保健
war	wɔː(r)	n. 战争，斗争；冲突，对抗，竞争；军事，战术
result	rɪˈzʌlt	n. 结果；成绩；答案；比赛结果
research	rɪˈsɜːtʃ	n. 研究；调查
force	fɔːs	n. 力量；武力；魄力；军队
education	ˌedʒuˈkeɪʃ(ə)n	n. 教育；教育学；培养
although	ɔːlˈðəʊ	conj. 虽然，尽管
maybe	ˈmeɪbi	adv. 大概；也许；可能
able	ˈeɪb(ə)l	adj.能...的,有才能的,能干的,能够的
policy	ˈpɒləsi	n. 政策，方针；保险单
consider	kənˈsɪdə(r)	vt. 认为；考虑；细想；考虑到
probably	ˈprɒbəbli	adv. 大概；或许；很可能
human	ˈhjuːmən	adj. 人的；人类的
die	daɪ	vi. 死亡；凋零；熄灭
sense	sens	n. 感觉，官能；观念；理智；道理
Oh	əʊ	int. 哦；哎呀（表示惊讶或恐惧等）
nation	ˈneɪʃ(ə)n	n. 国家；民族；国民
death	deθ	n. 死；死亡；死神；毁灭
reach	riːtʃ	vi. 达到；延伸；传开；伸出手
local	ˈləʊk(ə)l	n. 当地居民；本地新闻；局部
        """,
        """
kill	kɪl	vt. 杀死；扼杀；使终止；抵消
six	sɪks	num. 六，六个
class	klɑːs	n. 班级；阶级；种类
care	keə(r)	n. 照料；忧虑；关怀；谨慎
else	els	adv. 另外；其他；否则
major	ˈmeɪdʒə(r)	adj. 主要的；主修的；重要的；较多的
require	rɪˈkwaɪə(r)	vt. 要求；需要；命令
development	dɪˈveləpmənt	n. 发展；开发；显影；发育；住宅小区（专指由同一开发商开发的）
role	rəʊl	n. 角色；任务
economic	ˌiːkəˈnɒmɪk	adj. 经济的，经济上的；经济学的
effort	ˈefət	n. 成就；努力
rate	reɪt	n. 比率，率；速度；等级；价格
heart	hɑːt	n. 心脏；要点；感情；勇气；心形
drug	drʌɡ	n. 毒品；药；滞销货；麻醉药
leader	ˈliːdə(r)	n. 领导者；首领；指挥者
mind	maɪnd	n. 智力；理智，精神；意见；记忆力
free	friː	adj. 自由的，不受约束的；免费的；游离的
military	ˈmɪlətri	adj. 军事的；军人的；适于战争的
relationship	rɪˈleɪʃ(ə)nʃɪp	n. 关联；关系
federal	ˈfedərəl	adj. 联邦的；同盟的
international	ˌɪntəˈnæʃ(ə)nəl	n. 国际比赛；国际性组织
join	dʒɔɪn	vt. 参加；连接；结合
society	səˈsaɪəti	n. 社会；社交界；交往；社团
tax	tæks	vt. 向 … 课税；使负重担
director	dəˈrektə(r)	n. 人事助理；导演；主任，主管
position	pəˈzɪʃ(ə)n	n. 位置，方位；职位，工作；姿态；站位； vt. 安置；把 … 放在适当位置
player	ˈpleɪə(r)	n. 演员；演奏者，表演者；运动员，比赛者；游戏者，做游戏的人
especially	ɪˈspeʃəli	adv. 特别；尤其；格外
ground	ɡraʊnd	n. 地面；土地；范围；战场
event	ɪˈvent	n. 事件，大事；结果；项目
couple	ˈkʌp(ə)l	n. 数个；对；夫妇
project	ˈprɒdʒekt; prəˈdʒekt	vi. 设计；表达；计划；投射
hit	hɪt	vt. 袭击；碰撞；打击；偶然发现；伤 … 的感情
base	beɪs	n. 底部；垒；基础
activity	ækˈtɪvəti	n. 活动；活跃；行动
half	hɑːf	n. 一半；半场；半学年
situation	ˌsɪtʃuˈeɪʃ(ə)n	n. 位置；形势；情况；处境
industry	ˈɪndəstri	n. 工业；产业；勤勉
itself	ɪtˈself	pron. 它本身；它自己
data	ˈdeɪtə	n. 资料；数据（ datum 的复数）
practice	ˈpræktɪs	n. 练习；实践；惯例
recent	ˈriːs(ə)nt	adj. 最近的；近代的
product	ˈprɒdʌkt	n. 产品；结果；作品；乘积
worker	ˈwɜːkə(r)	n. 工人；劳动者；职蚁
personal	ˈpɜːsən(ə)l	adj. 个人的；身体的；亲自的
third	θɜːd	num. 第三；三分之一
technology	tekˈnɒlədʒi	n. 工艺；术语；技术
step	step	n. 步，脚步；步伐；步骤；梯级
draw	drɔː	vt. 画；拉；吸引
Republican	rɪˈpʌblɪkən	adj. 共和国的；共和政体的；共和主义的；拥护共和政体的
organization	ˌɔːɡənaɪˈzeɪʃn	n. 组织；机构；团体；体制
hair	heə(r)	n. 头发；毛发；些微
evidence	ˈevɪdəns	n. 证据，证明；迹象；明显
culture	ˈkʌltʃə(r)	n. 文化，文明；修养；栽培
chance	tʃɑːns	n. 可能性；机会，际遇；运气，侥幸
energy	ˈenədʒi	n. 精力；能量；活力；精神
period	ˈpɪəriəd	n. 周期，期间；时期；课时；月经
available	əˈveɪləb(ə)l	adj. 可利用的；有效的，可得的；空闲的
likely	ˈlaɪkli	adj. 很可能的；合适的；有希望的
opportunity	ˌɒpəˈtjuːnəti	n. 时机，机会
term	tɜːm	n. 学期；术语；条款；期限
condition	kənˈdɪʃn	n. 条件；情况；环境；身分
rule	ruːl	vi. 统治；裁定；管辖
Congress	ˈkɒŋɡres	n. 国会；会议；代表大会；社交
campaign	kæmˈpeɪn	vi. 作战；参加活动；参加竞选
population	ˌpɒpjuˈleɪʃ(ə)n	n. 人口；种群，群体；全体居民
economy	ɪˈkɒnəmi	n. 节约；经济；理财
medical	ˈmedɪk(ə)l	adj. 医学的；内科的；药的
thousand	ˈθaʊz(ə)nd	n. 一千；一千个；许许多多
current	ˈkʌrənt	adj. 现在的；最近的；草写的；流通的，通用的
fire	ˈfaɪə(r)	n. 火；火灾；炉火；热情；炮火；激情；磨难
wrong	rɒŋ	adv. 错误地；邪恶的，不正当地
involve	ɪnˈvɒlv	vt. 包含；牵涉；使陷于；潜心于
security	sɪˈkjʊərəti	n. 安全；抵押品；证券；保证
seek	siːk	vt. 寻找；探索；寻求；搜索
behavior	bɪˈheɪvjə(r)	n. 行为，举止；态度；反应
deal	diːl	vt. 发牌；处理；给予；分配；买卖，交易
past	pɑːst	n. 过去；往事
goal	ɡəʊl	n. 目标；终点；球门，得分数
order	ˈɔːdə(r)	n. 命令；顺序；定单；规则
        """,
        """
author	ˈɔːθə(r)	n. 作者；作家；创始人
fill	fɪl	vt. 装满，使充满；满足；堵塞；任职
represent	ˌreprɪˈzent	vt. 表现；描绘；代表；回忆；再赠送
focus	ˈfəʊkəs	n. 焦点；焦距；清晰；中心
blood	blʌd	n. 血，血液；血统
upon	əˈpɒn	prep. 在 …之上；接近；根据
agency	ˈeɪdʒənsi	n. 代理，中介；代理处，经销处
nature	ˈneɪtʃə(r)	n. 自然；性质；种类；本性
reduce	rɪˈdjuːs	vt. 减少；降低；把 … 分解；使处于
share	ʃeə(r)	vt. 分享，分担；分配
common	ˈkɒmən	adj. 普通的；共同的；通常的；一般的
natural	ˈnætʃ(ə)rəl	adj. 自然的；不做作的；天生的；物质的
concern	kənˈsɜːn	vt. 涉及，关系到；使担心
series	ˈsɪəriːz	n. 系列，连续；丛书(a series of books)； [ 电 ] 串联； [ 数 ] 级数
similar	ˈsɪmələ(r)	adj. 相似的
animal	ˈænɪm(ə)l	n. 动物
decade	ˈdekeɪd	n. 十年，十年期；十
shoot	ʃuːt	vt. 拍摄；给 … 注射；射击，射中；发芽；使爆炸
seven	ˈsev(ə)n	num. 七个，七
artist	ˈɑːtɪst	n. 艺术家；美术家（尤指画家）；大师
career	kəˈrɪə(r)	n. 事业，职业；生涯
despite	dɪˈspaɪt	prep. 尽管，不管
central	ˈsentrəl	adj. 中心的；主要的；中枢的
eight	eɪt	num. 八个；八；第八
thus	ðʌs	adv. 因此；这样；从而；如此
treatment	ˈtriːtmənt	n. 治疗，疗法；处理；对待
beyond	bɪˈjɒnd	prep. 超过；越过；在 ... 较远的一边；那一边
exactly	ɪɡˈzæktli	adv. 精确地；正确地；正是；恰好地
protect	prəˈtekt	vt. 保护，防卫；警戒
fund	fʌnd	n. 资金；基金；存款
individual	ˌɪndɪˈvɪdʒuəl	adj. 个别的；个人的；独特的
identify	aɪˈdentɪfaɪ	vt. 识别；确定；使参与；把 … 看成一样
meeting	ˈmiːtɪŋ	n. 会议；集会；会见；汇合点
determine	dɪˈtɜːmɪn	vt. 决定；使 … 下定决心；判决
prepare	prɪˈpeə(r)	vt. 准备；使适合；装备；起草
whatever	wɒtˈevə(r)	adj. 不管什么样的
argue	ˈɑːɡjuː	vi. 争论，辩论；提出理由
particularly	pəˈtɪkjələli	adv. 特别地，独特地；详细地，具体地；明确地，细致地
amount	əˈmaʊnt	vi. 总计，合计；共计；相当于；产生 … 结果
ability	əˈbɪləti	n. 能力，能耐；才能
indicate	ˈɪndɪkeɪt	vt. 指出；表明；象征；预示
character	ˈkærəktə(r)	n. 字符；特性；角色；性格，品质
growth	ɡrəʊθ	n. 生长；增长；发展；种植
loss	lɒs	n. 遗失；亏损；失败；减少
degree	dɪˈɡriː	n. 程度，等级；度；学位；阶层
attack	əˈtæk	n. 攻击；抨击；疾病发作
region	ˈriːdʒən	n. 地区；范围；部位
training	ˈtreɪnɪŋ	n. 训练；培养；瞄准；整枝
trade	treɪd	n. 贸易，交易；行业；职业
physical	ˈfɪzɪk(ə)l	adj. 身体的(physical labor)；物质的；物理的
general	ˈdʒen(ə)rəl	adj. 一般的，普通的；综合的；大体的
feeling	ˈfiːlɪŋ	n. 感觉，触觉；感情，情绪；同情
analysis	əˈnæləsɪs	n. 分析；分解；验定
benefit	ˈbenɪfɪt	n. 利益，好处；救济金
sex	seks	n. 性别；性；色情；性行为
forward	ˈfɔːwəd	adj. 早的；向前的；迅速的
skill	skɪl	n. 技能，技巧；本领，技术
professor	prəˈfesə(r)	n. 教授；教师；公开表示信仰的人
financial	faɪˈnænʃ(ə)l	adj. 财政的，财务的；金融的
crime	kraɪm	n. 罪行，犯罪；罪恶；犯罪活动
compare	kəmˈpeə(r)	vt. 比较；对照；比喻为
ten	ten	num. 十个，十
knowledge	ˈnɒlɪdʒ	n. 知识，学问；知道，认识； [ 古 ] 学科
gun	ɡʌn	n. 枪枝；枪状物； [ 美俚 ] 持枪歹徒
strategy		n. 战略，策略
discuss	dɪˈskʌs	vt. 讨论；论述，辩论
indeed	ɪnˈdiːd	adv. 真正地；的确；甚至；实在
song	sɒŋ	n. 歌曲；鸣声；歌唱；诗歌
example	ɪɡˈzɑːmp(ə)l	n. 例子；榜样
democratic	ˌdeməˈkrætɪk	adj. 民主的；民主政治的；大众的
environment	ɪnˈvaɪrənmənt	n. 环境，外界
leg	leɡ	n. 腿；支柱
rather	ˈrɑːðə(r)	adv. 宁可，宁愿；相当
executive	ɪɡˈzekjətɪv	adj. 执行的，经营管理的；行政的；经营的
entire	ɪnˈtaɪə(r)	adj. 全部的，整个的；全体的
network	ˈnetwɜːk	n. 网络；网状物；广播网
legal	ˈliːɡ(ə)l	adj. 法定的；法律的；合法的
religious	rɪˈlɪdʒəs	adj. 宗教的；虔诚的；严谨的；修道的
final	ˈfaɪn(ə)l	adj. 最终的；决定性的；不可更改的
science	ˈsaɪəns	n. 科学；理科；技术；学科
        """,
        """
memory	ˈmeməri	n. 记忆，记忆力；回忆；内存，存储器
above	əˈbʌv	prep. 在…上面；在…之上；超过
cell	sel	n. 细胞；电池；蜂房的巢室；单人小室
establish	ɪˈstæblɪʃ	vt. 建立；安置；创办
trial	ˈtraɪəl	n. 试验；磨炼；审讯；努力
tonight	təˈnaɪt	adv. 在今晚
ball	bɔːl	n. 球；舞会
impact	ˈɪmpækt	vt. 撞击；冲突；影响；压紧
statement	ˈsteɪtmənt	n. 声明；陈述，叙述；报表，清单
maintain	meɪnˈteɪn	vt. 维持；维修；供养；继续；主张
popular	ˈpɒpjələ(r)	adj. 流行的，通俗的；受欢迎的；大众的；普及的
traditional	trəˈdɪʃən(ə)l	adj. 传统的；惯例的
direction	dəˈrekʃ(ə)n	n. 方向；指导；用法说明；趋势
weapon	ˈwepən	n. 武器，兵器
peace	piːs	n. 和平；和睦；平静；秩序
apply	əˈplaɪ	vt. 应用；申请；涂，敷
measure	ˈmeʒə(r)	n. 措施；程度；测量；尺寸
wide	waɪd	adj. 宽的，广阔的；张大的；远离目标的；广泛的
interview	ˈɪntəvjuː	n. 面试，面谈；接见，采访
chair	tʃeə(r)	n. 椅子；（会议的）主席位；大学教授的职位；讲座
particular	pəˈtɪkjələ(r)	adj. 特别的；独有的；挑剔的；详细的
camera	ˈkæm(ə)rə	n. 照相机；摄影机
structure	ˈstrʌktʃə(r)	n. 构造；结构；建筑物
politics	ˈpɒlətɪks	n. 政治，政治学；政治活动；政纲
perform	pəˈfɔːm	vt. 执行；完成；演奏
bit	bɪt	n. 少量；马嚼子；比特（二进位制信息单位）；辅币； [ 口 ] 老一套
candidate	ˈkændɪdət	n. 候选人，候补者；应试者
production	prəˈdʌkʃ(ə)n	n. 成果；产品；生产；作品
treat	triːt	vt. 对待；治疗；探讨；视为
conference	ˈkɒnfərəns	n. 会议；协商；讨论； [ 篮 ] 联盟
unit	ˈjuːnɪt	n. 单位，单元；部件；装置；部队
style	staɪl	n. 风格；类型；时尚；字体
adult	ˈædʌlt; əˈdʌlt	adj. 成熟的；成年的
range	reɪndʒ	n. 范围；山脉；排；幅度
mention	ˈmenʃ(ə)n	vt. 说起；提到，谈到；提及，论及
deep	diːp	n. 深处；深渊
specific	spəˈsɪfɪk	adj. 特殊的，特定的；明确的；详细的；具有特效的
writer	ˈraɪtə(r)	n. 作家；作者
necessary	ˈnesəsəri	adj. 必要的；必然的；必需的
throughout	θruːˈaʊt	adv. 自始至终，到处；全部
challenge	ˈtʃælɪndʒ	n. 挑战；怀疑
institution	ˌɪnstɪˈtjuːʃ(ə)n	n. 制度；（社会或宗教等）公共机构；习俗；建立
middle	ˈmɪd(ə)l	adj. 中间的，中部的；中级的，中等的
sea	siː	n. 海洋；海；许多；大量
property	ˈprɒpəti	n. 财产；性质，性能；所有权
improve	ɪmˈpruːv	vt. 改善，增进；提高 … 的价值
stuff	stʌf	n. 材料；东西；填充物；素材资料
detail	ˈdiːteɪl	n. 细节，详情
method	ˈmeθəd	n. 方法；条理； [ 计 ] 类函数
soldier	ˈsəʊldʒə(r)	n. 军人；兵蚁；懒汉； [ 口 ] 一片烤面包
reflect	rɪˈflekt	vt. 反射，照出；反省；反映
heat	hiːt	n. 热度；高温；压力；热烈
marriage	ˈmærɪdʒ	n. 结婚；婚姻生活；密切结合，合并
tough	tʌf	adj. 坚韧的，牢固的；艰苦的，困难的；强壮的，结实的；坚强的，不屈不挠的
sing	sɪŋ	vi. 唱歌；鸣叫；歌颂；呼号
purpose	ˈpɜːpəs	n. 目的；用途；意志
exist	ɪɡˈzɪst	vi. 存在；生存；生活；继续存在
agent	ˈeɪdʒənt	n. 代理人，代理商；药剂；特工
generation	ˌdʒenəˈreɪʃ(ə)n	n. 产生；一代；一代人；生殖
commercial	kəˈmɜːʃ(ə)l	adj. 商业的；营利的；靠广告收入的
cancer	ˈkænsə(r)	n. 癌症；恶性肿瘤
item	ˈaɪtəm	n. 条款，项目；一则
reality	riˈæləti	n. 现实；真实；实际
coach	kəʊtʃ	n. 教练；旅客车厢；长途公车；四轮大马车
beat	biːt	vt. 打；打败
tend	tend	vi. 趋向，倾向；照料，照顾
investment	ɪnˈvestmənt	n. 投资；投入； [ 军 ] 封锁
discussion	dɪˈskʌʃ(ə)n	n. 讨论，议论
task	tɑːsk	vt. 分派任务
partner	ˈpɑːtnə(r)	n. 合伙人；伙伴；配偶
positive	ˈpɒzətɪv	adj. 确定的，肯定的；积极的；正的，阳性的；绝对的；实际的，真实的
civil	ˈsɪv(ə)l	adj. 公民的；民间的；文职的；有礼貌的；根据民法的
wish	wɪʃ	n. 希望；祝福；心愿
painting	ˈpeɪntɪŋ	n. 油画；绘画；着色
scientist	ˈsaɪəntɪst	n. 科学家
safe	seɪf	adj. 安全的；可靠的；平安的
nor	nɔː(r)	conj. 也不；也不是
threat	θret	n. 威胁，恐吓；凶兆
responsibility	rɪˌspɒnsəˈbɪləti	n. 责任，职责；义务
account	əˈkaʊnt	n. 帐目，帐单；理由；帐户；解释
        """,
        """
audience	ˈɔːdiəns	n. 观众；听众；接见；读者
vote	vəʊt	n. 投票，选举；选票；得票数
western	ˈwestən	adj. 西方的，西部的；有西方特征的
relate	rɪˈleɪt	vt. 叙述；使 …有联系
debate	dɪˈbeɪt	vt. 辩论，争论，讨论
prevent	prɪˈvent	vt. 预防，防止；阻止
citizen	ˈsɪtɪz(ə)n	n. 公民；市民；老百姓
majority	məˈdʒɒrəti	n. 多数； [ 律 ] 成年
senior	ˈsiːniə(r)	adj. 高级的；年长的；地位较高的；年资较深的，资格较老的
assume	əˈsjuːm	vt. 假定；承担；呈现；采取
professional	prəˈfeʃən(ə)l	adj. 职业的；专业的；职业性的
mission	ˈmɪʃ(ə)n	n. 使命，任务；布道；代表团
speech	spiːtʃ	n. 演说；演讲；讲话；语音
option		n. 选择权；选项； [ 经 ] 买卖的特权
southern	ˈsʌðən	adj. 南方的；南的
eventually	ɪˈventʃuəli	adv. 最后，终于
video	ˈvɪdiəʊ	n. 电视；录像，录像机；视频
Senate		n. 参议院，上院；（古罗马的）元老院
reform	rɪˈfɔːm	n. 改革，改良；改正
access	ˈækses	vt. 存取；接近；使用
judge	dʒʌdʒ	vt. 判断；审判
publish	ˈpʌblɪʃ	vt. 发表；出版；公布
release	rɪˈliːs	vt. 释放；发射；让与；允许发表
opinion	əˈpɪnjən	n. 意见；主张
credit	ˈkredɪt	n. 信用，信誉；信任；贷款；声望；学分
critical	ˈkrɪtɪk(ə)l	adj. 批评的，爱挑剔的；决定性的；危险的；临界的；鉴定的；评论的
corner	ˈkɔːnə(r)	n. 角落，拐角处；困境，窘境；地区，偏僻处
recall	rɪˈkɔːl	vt. 回想起，记起；召回；取消
version	ˈvɜːʃ(ə)n	n. 译文；版本； [ 医 ] 倒转术
stare	steə(r)	vi. 凝视，盯着看；显眼
effective	ɪˈfektɪv	adj. 有效的，起作用的；给人深刻印象；实际的，实在的
original	əˈrɪdʒən(ə)l	n. 原物；原作；原型；原件
troop	truːp	n. 军队；群；多数；组
directly	dəˈrektli	adv. 直接地；立即；马上；坦率地；正好地
basic	ˈbeɪsɪk	adj. 基本的；基础的
freedom	ˈfriːdəm	n. 自由，自主；直率
absolutely	ˈæbsəluːtli	adv. 绝对地；完全地
achieve		vt. 完成；达到
attitude	ˈætɪtjuːd	n. 态度；姿势；看法；意见
refer	rɪˈfɜː(r)	vi. 涉及；查阅；提到；参考
concept	ˈkɒnsept	n. 观念，概念
powerful	ˈpaʊəf(ə)l	adj. 强大的；强有力的
perfect	ˈpɜːfɪkt	adj. 完美的；最好的；精通的
nine	naɪn	n. 九，九个
therefore	ˈðeəfɔː(r)	adv. 所以；因此
conduct	kənˈdʌkt	vi. 带领；导电
attend	əˈtend	vt. 出席；上（大学等）；照料；招待；陪伴
researcher	rɪˈsɜːtʃə(r); ˈriːsɜːtʃə(r)	n. 研究员
conflict	ˈkɒnflɪkt	n. 冲突，矛盾；争执；斗争
spirit	ˈspɪrɪt	n. 精神；心灵；志气；烈酒；情绪
replace	rɪˈpleɪs	vt. 取代，代替；替换，更换；归还，偿还；把 … 放回原处
British	ˈbrɪtɪʃ	adj. 英国的；英国人的；大不列颠的
argument	ˈɑːɡjumənt	n. 争吵；论据；内容提要；论证
camp	kæmp	vi. 扎营；露营
brain	breɪn	n. 脑袋；头脑，智力
dozen	ˈdʌz(ə)n	n. 十二个，一打
possibility	ˌpɒsəˈbɪləti	n. 可能性；可能发生的事物
insurance	ɪnˈʃʊərəns	n. 保险；保险费；保险契约；赔偿金
department	dɪˈpɑːtmənt	n. 部门；系；科；部；局
battle	ˈbæt(ə)l	n. 战役；斗争
beginning	bɪˈɡɪnɪŋ	n. 开始；起点
generally	ˈdʒen(ə)rəli	adv. 通常；普遍地，一般地
African		adj. 非洲的，非洲人的
crisis	ˈkraɪsɪs	n. 危机；决定性时刻；危险期
stick	stɪk	vt. 粘贴；刺，戳；伸出
define	dɪˈfaɪn	vt. 定义；使明确；规定
element	ˈelɪmənt	n. 元素；成分；要素；原理；自然环境
vision	ˈvɪʒn	n. 视力；眼力；幻象；美景；想象力
status	ˈsteɪtəs	n. 地位；情形；状态；重要身分
solution	səˈluːʃ(ə)n	n. 解决方案；溶液；溶解；解答
scale	skeɪl	n. 刻度；比例；数值范围；天平；规模；鳞
attempt	əˈtempt	n. 企图，试图；攻击
lack	læk	vt. 缺乏；没有；需要；不足
truck	trʌk	n. 卡车(主美)；手推车；交易
tradition	trəˈdɪʃ(ə)n	n. 惯例，传统；传说
Soviet		adj. 苏维埃的；苏联的；苏联人的
roll	rəʊl	vt. 滚动，转动；卷；辗
communication	kəˌmjuːnɪˈkeɪʃ(ə)n	n. 通讯，通信；交流
screen	skriːn	n. 屏，幕；屏风
resident	ˈrezɪdənt	adj. 定居的；居住的；住院医师
        """,
        """
hide	haɪd	vt. 隐藏；隐瞒；鞭打
potential	pəˈtenʃ(ə)l	n. 可能性；潜能；电势
presence	ˈprez(ə)ns	n. 出席；参加；风度；仪态；存在
independent	ˌɪndɪˈpendənt	adj. 独立的；单独的；不受约束的；无党派的
shape	ʃeɪp	n. 形状；具体化；模型；身材
reader	ˈriːdə(r)	n. 读者；读物；阅读器
contract	ˈkɒntrækt; kənˈtrækt	vi. 感染；订约；收缩
Christian	ˈkrɪstʃən	n. 基督徒，信徒
express	ɪkˈspres	vt. 表达；快递
willing	ˈwɪlɪŋ	adj. 乐意的；心甘情愿的；自愿的
strength	streŋθ	n. 力量；力气；长处；兵力
previous	ˈpriːviəs	adj. 以前的；早先的；过早的
band	bænd	n. 带；松紧带；传送带；乐队；一帮
target	ˈtɑːɡɪt	n. 目标；靶子
demand	dɪˈmɑːnd	vt. 要求；需要；查询
deliver	dɪˈlɪvə(r)	vt. 发表；递送；释放；给予（打击）；交付；给 … 接生
tool		n. 工具，用具；器械，机床；手段
wild	waɪld	adj. 野生的；野蛮的；狂热的；荒凉的
vehicle	ˈviːəkl	n. 车辆（总称）；交通工具；运载工具；媒介物；工具；传播媒介
facility	fəˈsɪləti	n. 设备；灵巧；容易；设施
understanding	ˌʌndəˈstændɪŋ	n. 谅解，理解；理解力；协议
emerge	ɪˈmɜːdʒ	vi. 浮现；暴露；摆脱
advantage	ədˈvɑːntɪdʒ	n. 优势；利益；有利条件
quick	kwɪk	adj.迅速的，快的；持续时间很短的，短暂的
leadership	ˈliːdəʃɪp	n. 领导阶层；领导能力
basis		n. 底部；基础；主要成分；基本原则或原理
operate	ˈɒpəreɪt	vi. 运转；动手术；起作用
sample	ˈsɑːmp(ə)l	vt. 取样；抽样检查；尝试
protection	prəˈtekʃ(ə)n	n. 保护；防卫；护照
settle	ˈset(ə)l	vi. 定居；下陷；解决；沉淀
feed	fiːd	vt. 喂养；放牧；供给；抚养（家庭等）；靠 … 为生
additional	əˈdɪʃən(ə)l	adj. 附加的，额外的
title	ˈtaɪt(ə)l	n. 标题；头衔；冠军；权利；字幕
mostly	ˈməʊstli	adv. 主要地；多半地；通常
lesson	ˈles(ə)n	n. 课；教训
faith	feɪθ	n. 信任；信念；信仰；忠实
promote	prəˈməʊt	vt. 促进；提升；发扬；推销
unless	ənˈles	conj. 除非，如果不
tomorrow	təˈmɒrəʊ	n. 明天；未来
technique	tekˈniːk	n. 技巧，技术；手法
ear	ɪə(r)	n. 耳朵；听觉；倾听；穗
folk	fəʊk	n. 人们；民族；亲属（复数）
principle	ˈprɪnsəp(ə)l	n. 原理，原则；主义，道义；本质，本义；根源，源泉
fit	fɪt	vt. 安装；使 … 适应；使 … 合身；与 … 相符
associate	əˈsəʊsieɪt	vi. 交往；结交
aspect	ˈæspekt	n. 方面；外貌；形势；方向
comment	ˈkɒment	n. 评论；意见；批评
responsible	rɪˈspɒnsəb(ə)l	adj. 有责任的；负责的，可靠的
affair	əˈfeə(r)	n. 事情；事务；私事；（尤指关系不长久的）风流韵事 love affairs
procedure	prəˈsiːdʒə(r)	n. 步骤；程序，手续
regular	ˈreɡjələ(r)	adj. 整齐的；定期的；有规律的；合格的
spread	spred	vi. 传播；伸展
chairman	ˈtʃeəmən	n. 主席，会长；董事长
ignore	ɪɡˈnɔː(r)	vt. 驳回诉讼；忽视；不理睬
belief	bɪˈliːf	n. 相信，信赖；教义；信仰
demonstrate	ˈdemənstreɪt	vt. 证明；论证；展示
religion	rɪˈlɪdʒən	n. 宗教信仰；宗教
review	rɪˈvjuː	n. 评论；回顾；复习；检讨；检阅
editor	ˈedɪtə(r)	n. 编者，编辑；社论撰写人；编辑装置
engage	ɪnˈɡeɪdʒ	vt. 雇佣；吸引，占用；使订婚；使参加；预定
document	ˈdɒkjumənt	n. 文件，公文；文档；证件
cross	krɒs	n. 十字架，十字形物；交叉，十字
influence	ˈɪnfluəns	n. 影响；势力；感化；有影响的人或事
anyway	ˈeniweɪ	adv. 无论如何，不管怎样；总之
female	ˈfiːmeɪl	adj. 女性的；雌性的；柔弱的，柔和的
quarter	ˈkwɔːtə(r)	n. 四分之一；地区；季度；一刻钟；两角五分； [ 篮球 ] 节； vi. 住宿；驻扎； vt. 将 … 四等分；供某人住宿； num. 四分之一
background	ˈbækɡraʊnd	n. 背景；隐蔽的位置
native	ˈneɪtɪv	adj. 本国的；土著的；与生俱来的；天然的；天赋的
deny	dɪˈnaɪ	vt. 否定，否认；拒绝给予；拒绝 … 的要求
slightly		adv. 些微地，轻微地；纤细地
reaction	riˈækʃn	n. 反应，感应；反作用；反动，复古
twice	twaɪs	adv. 两倍；两次
perspective	pəˈspektɪv	n. 透视图；远景；观点
intelligence		n. 智力；理解力；情报工作；情报机关
connection	kəˈnekʃn	n. 连接；关系；连接件
burn	bɜːn	vt. 燃烧；烧毁，灼伤；激起 … 的愤怒
grade	ɡreɪd	n. 等级；年级；阶段；级别；成绩
committee	kəˈmɪti	n. 委员会
location	ləʊˈkeɪʃn	n. 位置（形容词 locational ）；地点；外景拍摄场地
promise	ˈprɒmɪs	n. 许诺，允诺；希望
        """,
        """
function	ˈfʌŋkʃ(ə)n	n. 功能；函数；职责；盛大的集会
extend	ɪkˈstend	vt. 延伸；扩大；伸出；给予；推广；使竭尽全力； [ 律 ] 对 … 估价
chief	tʃiːf	n. 酋长；首领；主要部分
combine	kəmˈbaɪn	vt. 使联合，使结合；使化合
below	bɪˈləʊ	adv. 在下面，在较低处；在本页下面
cool	kuːl	adj. 冷静的；凉爽的； [ 口 ] 出色的
learning	ˈlɜːnɪŋ	n. 学习；学问
moral	ˈmɒrəl	adj. 道德的；精神上的；品性端正的
relatively	ˈrelətɪvli	adv. 相对地，比较地；相当地
victory		n. 胜利；克服；成功
academic	ˌækəˈdemɪk	adj. 学院的；学术的；理论的
healthy	ˈhelθi	adj. 健康的，健全的；有益于健康的
negative		adj. 消极的；否定的；负的；阴性的
historical	hɪˈstɒrɪk(ə)l	adj. 历史的；史学的；基于史实的
finding	ˈfaɪndɪŋ	n. 发现；发现物；裁决
direct	dəˈrekt	adj. 直接的；亲身的；恰好的；直系的
classroom	ˈklɑːsruːm	n. 教室
contact	ˈkɒntækt	n. 接触，联系
participate		vi. 参与，参加；分享
knee	niː	n. 膝盖，膝
tape	teɪp	n. 磁带；带子；卷尺；胶带
hire	ˈhaɪə(r)	n. 租金，工钱；雇用，租用
familiar	fəˈmɪliə(r)	adj. 熟悉的；常见的；亲近的
appropriate	əˈprəʊpriət	adj. 适当的
supply	səˈplaɪ	n. 供给，补给；供应品
search	sɜːtʃ	vi. 搜寻；调查；探求
democracy	dɪˈmɒkrəsi	n. 民主，民主主义；民主政治
eastern	ˈiːstən	adj. 东方的；朝东的；东洋的
primary	ˈpraɪməri	adj. 主要的；初级的；基本的
yesterday	ˈjestədeɪ	n. 昨天；往昔
device	dɪˈvaɪs	n. 装置；策略；图案
island	ˈaɪlənd	n. 岛；岛屿；安全岛；岛状物
colleague	ˈkɒliːɡ	n. 同事，同僚
application	ˌæplɪˈkeɪʃ(ə)n	n. 应用；申请；敷用；应用程序
neck	nek	n. 脖子；衣领；海峡
plastic	ˈplæstɪk	adj. 可塑的；塑料的；（外科）造型的
otherwise	ˈʌðəwaɪz	adv. 另外；在其他方面；否则
male	meɪl	adj. 雄性的；男性的；有力的
expression	ɪkˈspreʃ(ə)n	n. 表达，表示；表情；表现力；措辞
abuse	əˈbjuːs	n. 滥用；辱骂；虐待；弊端；恶习，陋习
map	mæp	vt. 绘制地图；映射；计划； [ 生物 ] 确定基因在染色体中的位置
session		n. 会议；（法庭的）开庭；（议会等的）开会；学期；讲习会
domestic	dəˈmestɪk	adj. 国内的；家庭的；驯养的；一心只管家务的
literature	ˈlɪtrətʃə(r); ˈlɪtərətʃə	n. 文学；文艺；文献；著作
desire	dɪˈzaɪə(r)	n. 欲望；要求，心愿；性欲
injury	ˈɪndʒəri	n. 伤害，损害；受伤处
respect	rɪˈspekt	n. 尊敬，尊重；方面；敬意
northern	ˈnɔːðən	adj. 北方的；北部的
fuel		vi. 得到燃料
engine	ˈendʒɪn	n. 引擎，发动机；机车，火车头；工具
fourth	fɔːθ	adj. 四分之一的；第四的，第四个的
expand	ɪkˈspænd	vt. 扩张；使膨胀；详述
metal	ˈmet(ə)l	n. 金属；合金
lip	lɪp	n. 嘴唇；边缘
reading	ˈriːdɪŋ	n. 阅读，朗读；读物；读数
mental	ˈment(ə)l	adj. 精神的；脑力的(mental  labor)；疯的
increasingly	ɪnˈkriːsɪŋli	adv. 渐增地；越来越多地
obtain	əbˈteɪn	vi. 获得；流行
enemy	ˈenəmi	n. 敌人，仇敌；敌军
complex	ˈkɒmpleks	adj. 复杂的；合成的
athlete		n. 运动员，体育家；身强力壮的人
soul	səʊl	n. 灵魂；精神；心灵；鬼魂
scientific	ˌsaɪənˈtɪfɪk	adj. 科学的，系统的
panel	ˈpæn(ə)l	n. 仪表板；嵌板；座谈小组，全体陪审员
meaning	ˈmiːnɪŋ	n. 意义；含义；意图
predict	prɪˈdɪkt	vt. 预报，预言；预知
emotional	ɪˈməʊʃən(ə)l	adj. 情绪的；易激动的；感动人的
supreme	suːˈpriːm; sjuːˈpriːm	adj. 最高的；至高的；最重要的
proposal	prəˈpəʊz(ə)l	n. 提议，建议；求婚
consequence	ˈkɒnsɪkwəns	n. 结果；重要性；推论
balance	ˈbæləns	n. 平衡；匀称；余额
adopt	əˈdɒpt	vt. 收养；采取；接受；正式通过
minority	maɪˈnɒrəti	n. 少数民族；少数派；未成年
teaching	ˈtiːtʃɪŋ	n. 教学；教义
aid		n. 帮助；援助；助手；帮助者
okay	əʊˈkeɪ	adv. 可以；对；很好地
trail	treɪl	vt. 拖；追踪；蔓延；落后于
novel	ˈnɒv(ə)l	adj. 新奇的；异常的
code	kəʊd	n. 代码，密码；法典；编码
somehow	ˈsʌmhaʊ	adv. 莫明其妙地；以某种方法
        """,
        """
organize	ˈɔːɡənaɪz	vt. 组织；使有系统化；给予生机；组织成立工会等
jury		n. 陪审团；评判委员会
breast	brest	n. 乳房，胸部；胸怀；心情
union	ˈjuːniən	n. 联合；联盟，协会；工会
conclusion	kənˈkluːʒn	n. 结论；推论；结局
prime	praɪm	adj. 基本的；最好的；主要的
shadow	ˈʃædəʊ	n. 影子；阴影；庇护；幽灵；隐蔽处
conclude	kənˈkluːd	vt. 结束；推断；决定，作结论
regulation	ˌreɡjuˈleɪʃ(ə)n	n. 规则；管理；校准
being	ˈbiːɪŋ	n. 存在；生命；本质；品格
largely	ˈlɑːdʒli	adv. 主要地；大量地；大部分
locate	ləʊˈkeɪt	vt. 位于；查找 … 的地点
county		n. 郡，县
difficulty	ˈdɪfɪkəlti	n. 困难，困境
recommend	ˌrekəˈmend	vt. 劝告；推荐，介绍；使受欢迎；托付
obvious	ˈɒbviəs	adj. 明显的；显著的；平淡无奇的
basically	ˈbeɪsɪkli	adv. 主要地，基本上
generate	ˈdʒenəreɪt	vt. 使形成；生殖；发生
propose	prəˈpəʊz	vt. 建议；打算，计划；求婚
possibly	ˈpɒsəbli	adv. 可能地；大概；也许
trend	trend	n. 趋势，倾向；走向
loan		n. 贷款；借款
accident	ˈæksɪdənt	n. 事故；机遇；意外事件；意外
muscle	ˈmʌs(ə)l	n. 肌肉；力量
capture	ˈkæptʃə(r)	vt. 俘获；夺得
prefer	prɪˈfɜː(r)	vt. 宁愿；更喜欢；提出；提升
truly	ˈtruːli	adv. 真实地，不假；真诚地
chest	tʃest	n. 胸，胸部；箱子；衣柜；金库
cash	kæʃ	n. 现款，现金
emergency	ɪˈmɜːdʒənsi	n. 紧急情况；突发事件；非常时刻
unique	juˈniːk	adj. 唯一的，独一无二的；独特的，稀罕的
internal		adj. 国内的；内部的；内在的
link	lɪŋk	n. 联系，关系；链环，环节
stress	stres	n. 压力；紧张；重要性；强调；重读
select	sɪˈlekt	vt. 挑选；选拔
nose	nəʊz	n. 鼻子；嗅觉；突出的部分；探问
declare	dɪˈkleə(r)	vt. 宣布，声明；断言，宣称
hardly	ˈhɑːdli	adv. 几乎不，简直不；刚刚
setting	ˈsetɪŋ	n. 环境；布置；安装；沉落
file	faɪl	n. 文件；档案；锉刀；文件夹
sick	sɪk	n. 病人
outcome	ˈaʊtkʌm	n. 结果，结局；成果
ad		n. 公元
defend	dɪˈfend	vt. 防护；辩护
duty	ˈdjuːti	n. 责任；职务；关税
sheet	ʃiːt	n. 薄片，纸张；床单；薄板
ensure	ɪnˈʃʊə(r)	vt. 保证，确保；使安全
component	kəmˈpəʊnənt	adj. 组成的，构成的
slow	sləʊ	adj. 慢的；减速的；迟钝的
contrast	ˈkɒntrɑːst	vi. 对比；形成对照
zone	zəʊn	n. 地带；地区； [ 体 ] 联防； vi. 分成区； vt. 使分成地带；环绕
wake	weɪk	vi. 醒来；唤醒；警觉
warn	wɔːn	vt. 警告，提醒；通知
ultimately		adv. 最后；根本；基本上
capacity	kəˈpæsəti	n. 能力；容量；生产力；资格，地位
circumstance	ˈsɜːkəmstəns	n. 环境，情况；事件；境遇
politician	ˌpɒləˈtɪʃ(ə)n	n. 政治家，政客
pursue	pəˈsjuː	vt. 从事；追赶；继续；纠缠
percentage	pəˈsentɪdʒ	n. 百分率，百分数；百分比
surgery		n. 外科；外科手术；手术室；诊疗室
Jewish		adj. 犹太人的；犹太族的
blame	bleɪm	vt. 责备；归咎于
estimate	ˈestɪmət	vi. 估计，估价
due	djuː	adj. 到期的；应得的；应付的；预期的
golf	ɡɒlf	n. 高尔夫球；高尔夫球运动
investigate	ɪnˈvestɪɡeɪt	v. 调查；研究
crazy	ˈkreɪzi	adj. 疯狂的；狂热的，着迷的
chain	tʃeɪn	n. 链；束缚；枷锁
branch		vt. 分支；出现分歧
combination	ˌkɒmbɪˈneɪʃ(ə)n	n. 结合；化合；联合；组合
frequently	ˈfriːkwəntli	adv. 时常，屡次；频繁地，经常地
governor	ˈɡʌvənə(r)	n. 统治者，管理者；调节器；地方长官；主管人员
relief	rɪˈliːf	n. 减轻，解除；救济；安慰
user	ˈjuːzə(r)	n. 用户；使用者
manner	ˈmænə(r)	n. 方式；习惯；种类；规矩；风俗
silence	ˈsaɪləns	n. 寂静；无声状态；不谈；缄默；沉默
motion	ˈməʊʃn	n. 动作；请求；手势；移动；意向
fee		n. 酬金；小费；费用
used	juːst	adj. 习惯的；二手的，使用过的
equal	ˈiːkwəl	adj. 相等的；胜任的；平等的
        """,
        """
forth	fɔːθ	adv. 向前，向外；自 … 以后
frame	freɪm	n. 框架；结构；画面
typical		adj. 典型的；特有的；象征性的
conservative	kənˈsɜːvətɪv	adj. 保守的
eliminate	ɪˈlɪmɪneɪt	vt. 消除；排除
hall	hɔːl	n. 门厅，走廊；会堂；食堂；学生宿舍
trust	trʌst	n. 信任，信赖； [ 经济学 ] 托拉斯；责任
ocean	ˈəʊʃ(ə)n	n. 海洋；广阔； [ 口 ] 大量
division	dɪˈvɪʒ(ə)n	n. 除法；部门；分割；师（军队）； [ 体 ] 赛区
appeal	əˈpiːl	vi. 呼吁，恳求；有吸引力，迎合爱好；诉诸，求助；上诉；（体育比赛中）诉诸裁判
length	leŋkθ	n. 长度，长；时间的长短；（语）音长
rely	rɪˈlaɪ	vi. 信赖；依靠
topic	ˈtɒpɪk	n. 题目；主题（等于 theme ）；一般规则； [ 逻 ] 总论
variable	ˈveəriəb(ə)l	adj. 易变的，多变的；可变的； [ 生 ] 变异的，畸变的； [ 数 ] 变量的
attract	əˈtrækt	vt. 吸引；引起
debt	det	n. 债务；借款；罪过
tank	tæŋk	n. 坦克；水槽；池塘
aside	əˈsaɪd	adv. 在旁边；离开，撇开
bond	bɒnd	n. 结合；粘合剂；债券；约定
pleasure	ˈpleʒə(r)	n. 快乐；娱乐；希望；令人高兴的事
master	ˈmɑːstə(r)	vt. 控制；精通；征服
era	ˈɪərə	n. 时代；年代；纪元
requirement	rɪˈkwaɪəmənt	n. 要求；必需品；必要条件
fun	fʌn	n. 乐趣；玩笑；有趣的人或事
expectation	ˌekspekˈteɪʃ(ə)n	n. 期待；预期；指望
separate	ˈseprət	vt. 使分离；使分居；使分开
somewhat	ˈsʌmwɒt	n. 几分；某物
stir	stɜː(r)	n. 轰动；搅拌
reference	ˈrefrəns	n. 参考，参照；涉及，提及；参考书目；介绍信；证明书
tear	teə(r)	vt. 撕破，撕裂；使分裂；使精神不安；折磨
doubt	daʊt	n. 怀疑；疑问；疑惑
grant	ɡrɑːnt	vt. 授予；承认；允许
seriously	ˈsɪəriəsli	adv. 认真地；严重地，严肃地
minister	ˈmɪnɪstə(r)	n. 部长；大臣；牧师
hero	ˈhɪərəʊ	n. 英雄；男主角，男主人公
industrial	ɪnˈdʌstriəl	adj. 工业的，产业的；供工业用的；从事工业的；来自勤劳的
stretch	stretʃ	vt. 伸展，张开； vi. 伸展； adj. 可伸缩的； n. 伸展，延伸
volume	ˈvɒljuːm	n. 体积；卷；册；音量；大量；量
seed	siːd	n. 种子；萌芽；原由；根据；子孙；精液
pepper	ˈpepə(r)	n. 胡椒粉；辣椒；胡椒
copy	ˈkɒpi	vi. 复制；复印；抄袭
tip	tɪp	vi. 倾覆；翻倒；给小费
welfare		n. 福利；福利事业；幸福；安宁
beach	biːtʃ	n. 海滩；湖滨
improvement	ɪmˈpruːvmənt	n. 改进，改善
opening	ˈəʊpənɪŋ	n. 开始；通路；机会；空缺的职位
overall	ˌəʊvərˈɔːl	adj. 全部的；全体的；一切在内的
divide	dɪˈvaɪd	vt. 分开；划分；除；使产生分歧
initial	ɪˈnɪʃ(ə)l	adj. 最初的；字首的
oppose	əˈpəʊz	vt. 反对；对抗，抗争
contemporary	kənˈtemp(ə)rəri	n. 同时代的人；同时期的东西
route	ruːt	vt. 按某路线发送
multiple	ˈmʌltɪp(ə)l	adj. 多样的；许多的；多重的
league	liːɡ	n. 联盟；社团；范畴
criminal	ˈkrɪmɪn(ə)l	n. 罪犯
core	kɔː(r)	n. 核心；果心；要点；磁心
upper	ˈʌpə(r)	adj. 上面的，上部的；较高的
necessarily	ˌnesəˈserəli	adv. 必要地；必定地，必然地
resolution	ˌrezəˈluːʃ(ə)n	n. 决议；决心；分辨率；解决
apart	əˈpɑːt	adv. 分离着；相距；与众不同地
witness	ˈwɪtnəs	n. 目击者；证人；证据
representative	ˌreprɪˈzentətɪv	adj. 典型的，有代表性的；代议制的
incident	ˈɪnsɪdənt	n. 事件，事变；插曲
limited	ˈlɪmɪtɪd	adj. 有限的
flow	fləʊ	vi. 流动，涌流；飘扬；川流不息
faculty	ˈfæk(ə)lti	n. 能力；全体教员；科，系
merely		adv. 仅仅，只不过；只是
mass	mæs	n. 大量，众多；群众，民众；块，团
emphasize	ˈemfəsaɪz	vt. 强调，着重
experiment	ɪkˈsperɪmənt	vi. 进行实验；尝试
liberal	ˈlɪbərəl	adj. 慷慨的；不拘泥的；宽大的；自由主义的
invest	ɪnˈvest	vt. 投资；耗费；授予；覆盖；包围
cable	ˈkeɪb(ə)l	n. 电缆；海底电报
expose	ɪkˈspəʊz	vt. 使曝光；揭露，揭发；显示
rural	ˈrʊərəl	adj. 农村的，乡下的；田园的，有乡村风味的
narrow	ˈnærəʊ	adj. 狭窄的，有限的；度量小的；精密的；勉强的
gate	ɡeɪt	n. 大门；出入口；门道
celebrate	ˈselɪbreɪt	vt. 庆祝；赞美；祝贺；举行
achievement		n. 成就；达到；完成
accuse	əˈkjuːz	vt. 控告，指控；谴责；归咎于
        """,
        """
useful	ˈjuːsf(ə)l	adj. 有用的，有益的；有帮助的
reject	rɪˈdʒekt	vt. 拒绝；抵制；丢弃；排斥
talent	ˈtælənt	n. 才能；天资；天才
characteristic	ˌkærəktəˈrɪstɪk	adj. 特有的；典型的；表示特性的
cast	kɑːst	vt. 投，抛；投射（光、影、视线等）；浇铸；计算
sentence	ˈsentəns	n. 宣判，判决；句子，命题
unusual	ʌnˈjuːʒuəl	adj. 与众不同的；不寻常的；不平常的
closely	ˈkləʊsli	adv. 接近地；严密地；紧密地；亲近地
convince	kənˈvɪns	vt. 使确信，使信服；说服
physician		n. 医师；内科医师
assess	əˈses	vt. 评定；估价；对 … 征税
virtually	ˈvɜːtʃuəli	adv. 事实上，几乎；实质上
addition	əˈdɪʃ(ə)n	n. 添加；增加物；加法
creative	kriˈeɪtɪv	adj. 创造性的
approve	əˈpruːv	vt. 批准；赞成；为 … 提供证据
campus	ˈkæmpəs	n. （大学）校园；大学，大学生活；校园内的草地
proper	ˈprɒpə(r)	adj. 适当的；特有的；本身的；正派的
technical	ˈteknɪk(ə)l	adj. 技术上的；工艺的，科技的；专门的
plus	plʌs	n. 正号，加号；附加额；好处
immigrant	ˈɪmɪɡrənt	adj. 移民的；迁入的
illegal	ɪˈliːɡ(ə)l	adj. 非法的；违法的；违反规则的
alternative	ɔːlˈtɜːnətɪv	adj.(只做定语)供选择的；选择性的；交替的
interaction	ˌɪntərˈækʃ(ə)n	n. 交互作用；相互作用
forever	fərˈevə(r)	adv. 永远；常常；不断地
regard	rɪˈɡɑːd	n. 注意；尊重；凝视；问候
twenty	ˈtwenti	n. 二十；二十年代
lab	læb	n. [ 口 ] 实验室，研究室
spiritual	ˈspɪrɪtʃuəl	adj.精神的 n. 圣歌（尤指美国南部黑人的）
prayer	preə(r)	n. 恳求；祈祷，祷告；祈祷文
scholar		n. 学者；奖学金获得者
observation	ˌɒbzəˈveɪʃ(ə)n	n. 观察；监视；观察报告
childhood	ˈtʃaɪldhʊd	n. 童年时期；幼年时代
permit	pəˈmɪt	vi. 许可；允许
definition	ˌdefɪˈnɪʃ(ə)n	n. 定义；清晰度；解说
graduate	ˈɡrædʒuət	vt. 授予 … 学位；分等级；标上刻度
emotion	ɪˈməʊʃ(ə)n	n. 情感；情绪
scream	skriːm	vi. 尖叫；发出尖锐刺耳的声音；呼啸；令人触目惊心
dramatic	drəˈmætɪk	adj. 戏剧的；引人注目的；激动人心的
gap	ɡæp	n. 缺口；间隙；空白
excellent	ˈeksələnt	adj. 极好的；卓越的；杰出的
drag	dræɡ	vt. 拖拉；拖累；缓慢而吃力地行进
airline		n. 航空公司；航线
recover	rɪˈkʌvə(r)	vt. 恢复；重新获得；弥补
factory	ˈfæktri;	n. 工厂；制造厂；代理店
selection	sɪˈlekʃ(ə)n	n. 选择，挑选；选集；精选品
primarily	praɪˈmerəli	adv. 主要地，根本上；首先
unable	ʌnˈeɪb(ə)l	adj. 不会的，不能的；无能力的；不能胜任的
expense	ɪkˈspens	n. 开支；消费；损失，代价
schedule	ˈʃedjuːl	vt. 安排，计划；编制目录；将 … 列入计划表
brief	briːf	adj. 简短的，简洁的；短暂的，草率的
purchase	ˈpɜːtʃəs	n. 购买；紧握；起重装置
steel	stiːl	n. 钢铁；钢制品；坚固
regarding	rɪˈɡɑːdɪŋ	prep. 关于，至于
visual	ˈvɪʒuəl	adj. 视觉的，视力的；栩栩如生的
fairly	ˈfeəli	adv. 公平地；相当地；简直
silent	ˈsaɪlənt	adj. 沉默的；寂静的；无记载的
self	self	n. 自己，自我；本质；私心
perceive	pəˈsiːv	vt. 察觉，感觉；理解；认知
comparison	kəmˈpærɪs(ə)n	n. 比喻；比较；对照；比较关系
settlement	ˈset(ə)lmənt	n. 解决，处理；殖民；结算；沉降
slide	slaɪd	n. 幻灯片；滑动；滑梯；雪崩
widely	ˈwaɪdli	adv. 广泛地
portion		n. 部分；一份；命运
territory	ˈterətri	n. 领土，领域；版图；地域；范围
immediate	ɪˈmiːdiət	adj. 直接的；立即的；最接近的
abandon	əˈbændən	n. 狂热；放任
lake	leɪk	n. 湖；深红色颜料；胭脂红
transform	trænsˈfɔːm	vt. 改变，使 … 变形；转换
bother	ˈbɒðə(r)	vt. 烦扰，打扰；使 …… 不安；使 …… 恼怒
consist	ˈkɒnsɪst	vi. 组成；在于；符合
enable	ɪˈneɪb(ə)l	vt. 使能够，使成为可能；授予权利或方法
bend	bend	vt. 使弯曲；使屈服；使致力；使朝向
saving	ˈseɪvɪŋ	n. 存款；节约；挽救
shall	ʃæl	aux. 将；必须；会；应
Spanish	ˈspænɪʃ	adj. 西班牙的；西班牙人的；西班牙语的
passage	ˈpæsɪdʒ	n. 通路；走廊；一段（文章）
formal	ˈfɔːm(ə)l	adj. 正式的；拘谨的；有条理的
existence	ɪɡˈzɪstəns	n. 存在，实在；生存，生活；存在物，实在物
arrange	əˈreɪndʒ	vt. 排列；安排；整理
joint	dʒɔɪnt	n. 关节；接合处，接合点；接缝；（牛，羊等的腿）大块肉
        """,
        """
cycle	ˈsaɪk(ə)l	n. 循环；周期；整套；自行车；一段时间
lock	lɒk	vt. 锁，锁上；隐藏
consistent	kənˈsɪstənt	adj. 始终如一的，一致的；坚持的
resistance	rɪˈzɪstəns	n. 抵抗；抵抗力；反抗；电阻；阻力
exposure	ɪkˈspəʊʒə(r)	n. 暴露；曝光；陈列；揭露
tale	teɪl	n. (编造的)故事；传说；叙述；流言蜚语
resolve	rɪˈzɒlv	vt. 决定；使 … 分解；决心要做 … ；溶解
racial	ˈreɪʃ(ə)l	adj. 种族的；人种的
phase	feɪz	n. 相；位相；阶段
manufacturer	ˌmænjuˈfæktʃərə(r)	n. 制造商；厂商
philosophy	fəˈlɒsəfi	n. 哲学；哲理；人生观
foundation	faʊnˈdeɪʃ(ə)n	n. 基础；地基；基金会；创立；根据
quote	kwəʊt	vt. 引述；举证；报价
jacket	ˈdʒækɪt	n. 夹克；短上衣
reduction	rɪˈdʌkʃ(ə)n	n. 减少；下降；缩小
rarely	ˈreəli	adv. 很少地；难得；罕有地
numerous		adj. 许多的，很多的
substance	ˈsʌbstəns	n. 物质；实质；主旨；资产
discipline	ˈdɪsəplɪn	n. 纪律；学科；训练；惩罚
elsewhere		adv. 在别处；到别处
practical	ˈpræktɪkl	adj. 实际的；实用性的
moreover	mɔːrˈəʊvə(r)	adv. 此外；而且
passion	ˈpæʃ(ə)n	n. 激情；热情；酷爱；盛怒
independence	ˌɪndɪˈpendəns	n. 独立性，自立性；自主
priest	priːst	n. 牧师；神父；教士
intense	ɪnˈtens	adj. 强烈的(intense heat / cold / pain)；紧张的；热情的；非常的
advance	ədˈvɑːns	n. 前进；预付款；发展；增长
inspire	ɪnˈspaɪə(r)	vt. 鼓舞；激发；启示；产生；使生灵感
adjust	əˈdʒʌst	vt. 校准；调整，使 … 适合
visible	ˈvɪzəb(ə)l	adj. 明显的；看得见的；可得到的；现有的
kiss	kɪs	vt. 吻；（风等）轻拂
illness	ˈɪlnəs	n. 疾病；病
cap	kæp	n. 帽子；盖
juice		n. 果汁；（水果）汁，液
previously	ˈpriːviəsli	adv. 以前；预先；仓促地
whenever	wenˈevə(r)	conj. 每当；无论何时
transfer	trænsˈfɜː(r)	n. 转移；转让；过户；换乘(地铁、公交)
attach	əˈtætʃ	vt. 系上；贴上；使依附；使依恋
disaster	dɪˈzɑːstə	n. 不幸；灾难，灾祸
prospect	ˈprɒspekt	n. 前途；预期；景色
complaint	kəmˈpleɪnt	n. 抱怨；诉苦；疾病；委屈
fundamental	ˌfʌndəˈment(ə)l	adj. 基本的，根本的
severe	sɪˈvɪə(r)	adj. 严厉的；苛刻的；剧烈的；严峻的
impose	ɪmˈpəʊz	vi. 欺骗；利用；施加影响
poverty	ˈpɒvəti	n. 贫困；缺少；低劣；困难
king	kɪŋ	n. 国王；最有势力者；王棋
evaluate	ɪˈvæljueɪt	vt. 评价；估价；求 … 的值
symbol	ˈsɪmb(ə)l	n. 符号；象征；标志
mood	muːd	n. 心境；情绪，语气；气氛
accomplish	əˈkʌmplɪʃ	vt. 完成；实现；达到
emphasis	ˈemfəsɪs	n. 强调；重点；加强语气
illustrate	ˈɪləstreɪt	vt. 阐明，举例说明；图解
monitor		n. 监视器；监听器；监控器；班长
Asian	ˈeɪʒ(ə)n	n. 亚洲人； adj. 亚洲的；亚洲人的
commander	kəˈmɑːndə(r)	n. 指挥官；司令官
digital	ˈdɪdʒɪt(ə)l	adj. 数字的；手指的
arrangement	əˈreɪndʒmənt	n. 准备；布置；整理
anger	ˈæŋɡə(r)	n. 怒，愤怒；忿怒
psychological	ˌsaɪkəˈlɒdʒɪk(ə)l	adj. 心理的；心理学的；精神上的
peak	piːk	n. 顶点；山峰；最高点；帽舌
approximately		adv. 大约，近似地；近于
disorder	dɪsˈɔːdə(r)	n. 混乱；骚乱
missile	ˈmɪsaɪl	n. 导弹；投射物
vary	ˈveəri	vi. 变化；违反； [ 生 ] 变异
distribution		n. 分配；分布
transportation	ˌtrænspɔːˈteɪʃ(ə)n	n. 运输；运输工具；运输系统；流放
holy	ˈhəʊli	adj. 圣洁的，神圣的；至善的； n. 神圣的东西；（俚）太棒了
twin	twɪn	vt. 使成对
command	kəˈmɑːnd	vi. 命令，指挥；控制
commission	kəˈmɪʃ(ə)n	n. 犯；佣金；委任；委任状；委员会
strongly	ˈstrɒŋli	adv. 坚强地；激烈地；强有力地；气味浓地
so-called		adj. 号称的；所谓的
constant		adj. 不变的；恒定的；经常的
veteran		n. 老兵；老手；富有经验的人；老运动员； adj. 经验丰富的；老兵的
capable	ˈkeɪpəb(ə)l	adj. 能干的，能胜任的；有才华的
toss	tɒs	n. 投掷；摇荡；投掷的距离；掷币赌胜负
pray	preɪ	vt. 祈祷；恳求；央求
exception	ɪkˈsepʃ(ə)n	n. 例外；异议
objective	əbˈdʒektɪv	adj. 客观的；目标的； [ 语法学 ] 宾格的
electronic	ɪˌlekˈtrɒnɪk	adj. 电子的
        """,
        """
mixture	ˈmɪkstʃə(r)	n. 混合；混合物；混合剂
surely	ˈʃʊəli	adv. 稳当地；坚定地；无疑； [ 口 ] 当然
dimension	daɪˈmenʃ(ə)n	n. 维；尺寸；次元；容积
toy	tɔɪ	n. 玩具；小装饰品；不值钱的东西
fellow	ˈfeləʊ	n. 同事；家伙；朋友；会员
nearby	ˌnɪəˈbaɪ	adj. 附近的，邻近的
personnel	ˌpɜːsəˈnel	n. 全体人员；人事部门
belt	belt	n. 地带；带；腰带
birthday		n. 生日，诞辰；诞生的日子
implication	ˌɪmplɪˈkeɪʃn	n. 暗示；牵连，卷入；含义
perfectly	ˈpɜːfɪktli	adv. 完美地；完全地；无瑕疵地
silver	ˈsɪlvə(r)	n. 银；银器；餐具；银币；银质奖章；银灰色
teenager	ˈtiːneɪdʒə(r)	n. 十三岁到十九岁的少年；十几岁的青少年
recognition	ˌrekəɡˈnɪʃn	n. 承认，认出；赞誉；重视；识别；公认
flag	flæɡ	vi. 标记；枯萎；衰退
recovery	rɪˈkʌvəri	n. 恢复，复原；痊愈；重获
whisper	ˈwɪspə(r)	n. 私语；谣传；飒飒的声音
corn	kɔːn	n. （美）玉米；（英）谷物；鸡眼
moon	muːn	n. 月亮；月球；月光；卫星
inner	ˈɪnə(r)	adj. 内部的；内心的；精神的
junior	ˈdʒuːniə	adj. 年少的；后进的；下级的
throat		n. 喉咙；窄路；嗓子，嗓音
observer	əbˈzɜːvə(r)	n. 遵守者；观察者；观测者
publication	ˌpʌblɪˈkeɪʃn	n.出版物,出版,发行,公布,发表
crop	krɒp	n. 农作物；庄稼；产量；平头
dig	dɪɡ	vt. 挖，掘；探究
permanent	ˈpɜːmənənt	adj. 永久的，永恒的；不变的
phenomenon	fəˈnɒmɪnən	n. 现象；奇迹；杰出的人才
anxiety		n. 焦虑；挂念；渴望；令人焦虑的事
unlike	ˌʌnˈlaɪk	adj. 不同的，不相似的
resist	rɪˈzɪst	vi. 抵抗，抗拒；忍耐
convention	kənˈvenʃ(ə)n	n. 大会；协定；惯例；习俗；约定
embrace	ɪmˈbreɪs	vt. 包含；信奉，皈依；拥抱
assist	əˈsɪst	n. 帮助；助攻； vi. 参加；出席； vt. 帮助；促进
pan	pæn	n. 平锅，盘子； [ 电影 ] 摇镜头
mayor	meə(r)	n. 市长
consideration	kənˌsɪdəˈreɪʃ(ə)n	n. 考虑；报酬；原因；关心
secure		adj. 安全的；无虑的；有把握的；稳当的
poem	ˈpəʊɪm	n. 诗
bind	baɪnd	vi. 装订；结合；过紧；有约束力
fifth	fɪfθ	adj. 第五的；五分之一的
stomach	ˈstʌmək	n. 胃；胃口；腹部
apparent	əˈpærənt	adj. 显然的；表面上的
install	ɪnˈstɔːl	vt. 任命；安装；安顿
mail	meɪl	n.邮件(信与附件),邮政,盔甲 vt.邮寄,给...穿盔甲
reasonable	ˈriːznəbl	adj. 合理的，公道的；通情达理的
preparation	ˌprepəˈreɪʃ(ə)n	n. 预备；准备
elementary	ˌelɪˈmentri	adj. 基本的；初级的；元素的
concert	ˈkɒnsət	n. 音乐会；和谐；一致
aggressive	əˈɡresɪv	adj. 好斗的；侵略性的；有进取心的；有闯劲的
extreme	ɪkˈstriːm	adj. 极端的；极度的；偏激的；尽头的
tube	tjuːb	n. 管；电子管；隧道；电视机
drawing	ˈdrɔːɪŋ	n. 图画；牵引；素描术
protein	ˈprəʊtiːn	n. 蛋白质；朊
absence	ˈæbsəns	n. 缺席；缺乏；没有；不注意
Latin		adj. 拉丁语的；拉丁人的
jail	dʒeɪl	n. 监狱；监牢；拘留所
pace	peɪs	n. 步速；步法；一步
impression	ɪmˈpreʃ(ə)n	n. 印象；感想；压痕，印记；效果，影响
essay	ˈeseɪ	n. 散文；随笔；试图
substantial	səbˈstænʃ(ə)l	adj. 大量的；实质的；内容充实的
pop	pɒp	n. 砰然声； [ 口 ] 汽水；枪击；流行音乐
specialist	ˈspeʃəlɪst	n. 专家；专门医师
origin	ˈɒrɪdʒɪn	n. 出身；原点；起源；开端
approval	əˈpruːv(ə)l	n. 赞成；批准；认可
advise	ədˈvaɪz	vt. 劝告，忠告；通知；警告；建议
conventional	kənˈvenʃən(ə)l	adj. 常见的；惯例的；符合习俗的，传统的
depth	depθ	n. 深度；深奥
wealth	welθ	n. 财富；大量；富有
shell	ʃel	n. 壳，贝壳；外形；炮弹
criticize	ˈkrɪtɪsaɪz	vt. 批评；非难；评论
onion	ˈʌnjən	n. 洋葱；洋葱头
brand	brænd	vt. 打烙印于；印 … 商标于；铭刻于，铭记
assure	əˈʃʊə(r)	vt. 保证；担保；使确信；弄清楚
award	əˈwɔːd	vt. 授予；判定
via	ˈvaɪə	prep. 取道，通过；经由
utility	juːˈtɪləti	n. 效用；实用；功用；公共设施
arise	əˈraɪz	vi. 出现；起立；上升
nevertheless	ˌnevəðəˈles	adv. 然而，不过；虽然如此
highway	ˈhaɪweɪ	n. 公路，大路；捷径
        """,
        """
routine	ruːˈtiːn	n. 例行公事；日常工作；程序
wage	weɪdʒ	vi. 进行；发动；从事
stake	steɪk	n. 桩，棍子；火刑；赌注；奖金
ultimate		adj. 最终的；根本的；极限的
switch	swɪtʃ	vt. 转换；用鞭子等抽打； vi. 转换；抽打； [ 体 ] 换防； n. 开关；转换；鞭子
corporation	ˌkɔːpəˈreɪʃ(ə)n	n. 公司；社团；法人（团体）； [ 口 ] 大腹便便；市政当局
barrier	ˈbæriə(r)	n. 障碍物，屏障（the language barrier）；界线
minor	ˈmaɪnə(r)	adj. 较小的；次要的；未成年的； [ 音 ] 小调的；二流的
provision	prəˈvɪʒ(ə)n	n. 供应品；准备；条款；规定
assign	əˈsaɪn	vt. 分配；指派；赋值
gang	ɡæŋ	n. 群；一组；一伙
chemical	ˈkemɪk(ə)l	n. 化学制品，化学药品
index	ˈɪndeks	n. 指数；索引；指标；指针
draft	drɑːft	n. 草稿；汇票；草图； [ 篮球 ] 选秀； vt. 起草，设计；为 ... 打样
rough	rʌf	adj. 粗糙的；粗野的；未经加工的；粗略的；艰苦的
yell		vi. 大叫，叫喊
drama	ˈdrɑːmə	n. 戏剧，戏剧艺术；剧本；戏剧性事件
satellite		n. 人造卫星；卫星；随从；卫星国家
spin	spɪn	vi. 纺纱；吐丝；旋转；晕眩
button	ˈbʌt(ə)n	n. [ 计 ] 按钮；钮扣
bell	bel	n. 铃，钟；钟声，铃声；钟状物
rank	ræŋk	n. 等级；队列；排；军衔
clothing	ˈkləʊðɪŋ	n. （总称）服装； [ 航海 ] 帆装
super	ˈsuːpə(r)	adj. 极好的；特级的
yield	jiːld	vt. 出产；屈服；放弃
fence	fens	n. 栅栏；围墙；剑术
portrait	ˈpɔːtreɪt	n. 肖像；半身雕塑像；描写
roughly	ˈrʌfli	adv. 粗糙地；概略地
bunch	bʌntʃ	n. 串；群；突出物
found	faʊnd	vt. 创立，建立；创办
burden		n. 负担；责任；船的载货量
chamber	ˈtʃeɪmbə(r)	n. （身体或器官内的）室，膛；房间；会所
ceremony	ˈserəməni	n. 典礼，仪式；礼节，礼仪；客套，虚礼
communicate	kəˈmjuːnɪkeɪt	vi. 通讯，传达；相通；感染
cheek	tʃiːk	n. 面颊，脸颊； [ 俚 ] 臀部
disagree	ˌdɪsəˈɡriː	vi. 不一致；不适宜；不同意；争执
resort		n. 凭借，手段；常去之地；度假胜地
unlikely	ʌnˈlaɪkli	adj. 不太可能的；没希望的
tissue	ˈtɪʃuː	n. 纸巾；薄纱；一套
pant		vi. 渴望；喘息；气喘吁吁地说出某事
cabinet	ˈkæbɪnət	n. 内阁；橱柜；展览艺术品的小陈列室
broken	ˈbrəʊkən	adj. 坏掉的；破碎的
proceed	prəˈsiːd	vi. 开始；继续进行；行进；发生
bet	bet	n. 打赌，赌注；被打赌的事物
literary	ˈlɪtərəri	adj. 文学的；精通文学的；书面的
dispute	dɪˈspjuːt	vt. 辩论；阻止；抗拒；怀疑
overcome	ˌəʊvəˈkʌm	vt. 克服；胜过
remarkable	rɪˈmɑːkəb(ə)l	adj. 卓越的；非凡的；值得注意的
occupy	ˈɒkjupaɪ	vt. 占据，占领；使忙碌；居住
encounter	ɪnˈkaʊntə(r)	vt. 遭遇，邂逅；遇到
wipe		vt. 擦；涂上；消除
blind	blaɪnd	adj. 瞎的；盲目的
electricity	ɪˌlekˈtrɪsəti	n. 电力；电流；强烈的紧张情绪
retain	rɪˈteɪn	vt. 保持；雇；记住
translate	trænzˈleɪt	vt. 翻译；调动；解释；转化；转变为
terror	ˈterə(r)	n. 恐怖；恐怖行动；恐怖时期；可怕的人
expansion	ɪkˈspænʃ(ə)n	n. 膨胀；阐述；扩张物
dirt	dɜːt	n. 污垢，泥土；灰尘，尘土；下流话
odd	ɒd	adj. 奇数的；古怪的；临时的；剩余的；零散的
rice	raɪs	n. 稻；米饭
bullet	ˈbʊlɪt	n. 子弹；只选某党全部候选人的投票； [ 美俚 ] 豆子
Bible		n. 圣经
chart	tʃɑːt	n. 图表；海图；图纸
solar	ˈsəʊlə(r)	adj. 太阳的；日光的；利用太阳光的；与太阳相关的
square	skweə(r)	adj. 直角的；正方形的；正直的；平方的
complicated	ˈkɒmplɪkeɪtɪd	adj. 难懂的，复杂的
reflection		n. 反射；映象；沉思
revolution	ˌrevəˈluːʃn	n. 革命；旋转；运行；循环
strip	strɪp	vt. 剥去；剥夺；脱去衣服
fiction	ˈfɪkʃ(ə)n	n. 小说；虚构，编造；谎言[总称]
lifetime	ˈlaɪftaɪm	n. 一生；寿命；终生；使用期
recommendation		n. 推荐；推荐信；建议
salad	ˈsæləd	n. 色拉；尤指莴苣
guarantee	ˌɡærənˈtiː	n. 保证书；抵押品；保证；担保；保证人
innocent	ˈɪnəs(ə)nt	adj. 无罪的；无辜的；无知的
boundary	ˈbaʊndri	n. 分界线；边界；范围
pause		n. 暂停；间歇
journal	ˈdʒɜːn(ə)l	n. 日报，杂志；日记； [ 经 ] 分类账
bench	bentʃ	n. 长凳；工作台；替补队员； vt. 给 … 以席位；为 … 设置条凳
lover	ˈlʌvə(r)	n. 爱人，恋人；爱好者
        """,
        """
raw	rɔː	adj. 生的；未加工的；阴冷的；刺痛的；擦掉皮的；无经验的；（在艺术等方面）不成熟的
surprising	səˈpraɪzɪŋ	adj. 令人惊讶的；意外的
withdraw	wɪðˈdrɔː	vt. 撤消；收回；撤退；拉开
deck	dek	n. 甲板；行李仓；露天平台
similarly	ˈsɪmələli	adv. 同样地；类似于
newly	ˈnjuːli	adv. 最近；重新；以新的方式
mode	məʊd	n. 方式；模式；风格；时尚
imply	ɪmˈplaɪ	vt. 暗示；意味；隐含
naturally	ˈnætʃ(ə)rəli	adv. 自然地
mutual		adj. 相互的，彼此的；共同的
advanced	ədˈvɑːnst	adj. 高级的；先进的；晚期的；年老的
pride	praɪd	n. 骄傲；自豪；自尊心
dismiss	dɪsˈmɪs	vt. 让 ... 离开；开除；解散；解雇
delivery	dɪˈlɪvəri	n. 交付；递送；分娩
mainly	ˈmeɪnli	adv. 主要地，大体上
bake	beɪk	vt. 烤，烘焙
freeze	friːz	vi. 僵硬；冻结；冷冻
finance	ˈfaɪnæns	n. 财政，财政学；金融
sink	sɪŋk	vi. 下沉；消沉；渗透
attractive	əˈtræktɪv	adj. 吸引人的；有魅力的；引人注目的
diverse	daɪˈvɜːs	adj. 不同的；变化多的；多种多样的
relevant		adj. 有关的；中肯的；有重大作用的
ideal	aɪˈdiːəl	adj. 理想的；完美的；想象的；不切实际的
joy	dʒɔɪ	n. 欢乐，快乐；乐趣；高兴
singer	ˈsɪŋə(r)	n. 歌手，歌唱家
evolve	ɪˈvɒlv	vt. 发展，进化；进化；使逐步形成；推断出
partly	ˈpɑːtli	adv. 在一定程度上；部分地
unknown	ˌʌnˈnəʊn	adj. 未知的；陌生的，默默无闻的
justify	ˈdʒʌstɪfaɪ	vi. 证明合法；整理版面
protest	ˈprəʊtest	vi. 抗议；断言
craft	krɑːft	n. 手艺；工艺；太空船
treaty	ˈtriːti	n. 条约，协议；谈判
extensive	ɪkˈstensɪv	adj. 广泛的；广阔的；大量的
loose	luːs	adj. 不牢固的；不精确的；宽松的；散漫的
considerable	kənˈsɪdərəb(ə)l	adj. 重要的，值得考虑的；相当大的
prior	ˈpraɪə(r)	adj. 在先的，在前的；优先的
intellectual	ˌɪntəˈlektʃuəl	adj. 智力的；聪明的；理智的
relax	rɪˈlæks	vi. 放松，休息；松懈，松弛；变从容；休养
stair	steə(r)	n. 楼梯，阶梯；梯级
adventure	ədˈventʃə(r)	n. 冒险；冒险精神；投机活动
external	ɪkˈstɜːn(ə)l	adj. 外部的；表面的；外面的； [ 药 ] 外用的；外国的
proof	pruːf	n. 证据；证明；校样；考验；验证；试验
headquarters		n. 总部；司令部；指挥部
sudden	ˈsʌd(ə)n	adj. 突然的，意外的；快速的
shelter	ˈʃeltə(r)	n. 避难所；遮盖物；庇护
rub	rʌb	vt. 擦；摩擦；惹怒
properly	ˈprɒpəli	adv. 适当地；正确地；恰当地
fade	feɪd	vi. 褪色；凋谢；逐渐消失
net	net	n. 网；净利；网络；实价
funeral	ˈfjuːnərəl	n. 葬礼； [ 口 ] 麻烦事
profession	prəˈfeʃn	n. 职业，专业；声明，宣布，表白
alter	ˈɔːltə(r)	vt. 改变，更改
squeeze		vt. 挤；勒索；紧握
mask	mɑːsk	n. 掩饰；面具；口罩
convert	kənˈvɜːt	vt. 使转变；转换 … ；使 … 改变信仰
comprehensive	ˌkɒmprɪˈhensɪv	adj. 广泛的；综合的；有理解力的
prominent	ˈprɒmɪnənt	adj. 突出的，显著的；杰出的；卓越的
regardless	rɪˈɡɑːdləs	adj. 不顾；不管；不注意
split	splɪt	vt. 劈开；分离；使分离；离开
pollution	pəˈluːʃ(ə)n	n. 污染
steady	ˈstedi	adj. 稳定的；不变的；沉着的
fate	feɪt	n. 命运
oven	ˈʌv(ə)n	n. 炉，灶；烤炉，烤箱
distinction	dɪˈstɪŋkʃn	n. 差别；区别；特性；荣誉、勋章
poet	ˈpəʊɪt	n. 诗人
mere	mɪə(r)	adj. 仅仅的；只不过的
variation	ˌveəriˈeɪʃ(ə)n	n. 变异，变种；变化
comfort	ˈkʌmfət	n. 安慰；舒适；安慰者
adapt	əˈdæpt	vt. 改编；使适应
correspondent	ˌkɒrəˈspɒndənt	n. 通信者；通讯记者； [ 经 ] 客户；代理商行
pale	peɪl	adj. 苍白的；暗淡的；无力的
musician	mjuˈzɪʃ(ə)n	n. 音乐家
significance	sɪɡˈnɪfɪkəns	n. 重要性；意义；意思
vessel	ˈves(ə)l	n. 容器，器皿；船（总称），舰；脉管，血管
storage	ˈstɔːrɪdʒ	n. 存储；仓库；贮藏所
leather	ˈleðə(r)	n. 皮革；皮革制品
distribute		vt. 分配；散布；分开；把 … 分类
evolution	ˌiːvəˈluːʃ(ə)n	n. 进展；演变；进化论
ill	ɪl	adj. 坏的；生病的；邪恶的；不吉利的
tribe	traɪb	n. 部落；宗族；一伙； [ 生 ] 族
        """,
        """
lawn	lɔːn	n. 草坪；草地
wisdom	ˈwɪzdəm	n. 智慧，才智；明智；至理名言；学识
council	ˈkaʊns(ə)l	n. 理事会；会议；委员会；顾问班子；地方议会
instance	ˈɪnstəns	n. 实例；情况；建议
poetry	ˈpəʊətri	n. 诗；诗意，诗情；诗歌艺术
gradually	ˈɡrædʒuəli	adv. 渐渐地；逐步地
stability	stəˈbɪləti	n. 稳定性；坚定，恒心
plot	plɒt	n. 阴谋；情节；图
gesture		n. 姿态；手势
chapter	ˈtʃæptə(r)	n. 章，回；人生或历史上的重要时期；（俱乐部、协会等的）分会
divorce	dɪˈvɔːs	vt. 使离婚，使分离；与 … 离婚
owe	əʊ	vt. 感激；欠；应给予；应该把 …… 归功于
slight	slaɪt	adj. 轻微的，少量的；脆弱的；细长的；不重要的
shade	ʃeɪd	n. 树荫；阴影；阴凉处；遮阳物；（照片等的）明暗度；少量、些微；细微的差别
tail	teɪl	n. 尾部；踪迹；辫子；燕尾服
sustain	səˈsteɪn	vt. 支撑，承担；维持；忍受；供养；证实
mount	maʊnt	vt. 爬上；增加；使骑上马
angle	ˈæŋɡ(ə)l	n.[数]角,角落 vi.钓鱼,追逐
palm	pɑːm	n. 手掌；棕榈树；掌状物
custom	ˈkʌstəm	n. 风俗；习惯
fifteen	ˌfɪfˈtiːn	n. 十五；十五个；十五人组成的橄榄球队
soup	suːp	n. 汤，羹；马力
efficient	ɪˈfɪʃ(ə)nt	adj. 有效率的；生效的；有能力的
composition	ˌkɒmpəˈzɪʃ(ə)n	n. 作文，作曲；构成；合成物
satisfy	ˈsætɪsfaɪ	vi. 令人满意；令人满足
carbon	ˈkɑːbən	n. 碳；碳棒；复写纸
consume	kənˈsjuːm	vt. 消耗，消费；使 … 著迷；挥霍
scheme	skiːm	n. 计划；组合；体制；诡计
frequency	ˈfriːkwənsi	n.频率,周率,发生次数
besides	bɪˈsaɪdz	adv. 而且；此外
wealthy	ˈwelθi	adj. 富有的；充分的；丰裕的
ski	skiː	n. 滑雪橇
limitation	ˌlɪmɪˈteɪʃ(ə)n	n. 限制；限度；极限； [ 律 ] 追诉时效；有效期限
trace	treɪs	vi. 追溯；沿路走
preference	ˈprefrəns	n. 偏爱，倾向；优先权
meter	ˈmiːtə(r)	n. 公尺；仪表；韵律；米
rapid	ˈræpɪd	adj. 迅速的，急促的；飞快的；险峻的
admission	ədˈmɪʃ(ə)n	n. 承认；入场费；进入许可；坦白；录用
educate	ˈedʒukeɪt	vt. 教育；培养；训练
painful	ˈpeɪnf(ə)l	adj. 疼痛的；痛苦的；令人不快的
friendship	ˈfrendʃɪp	n. 友谊；友善；友爱
infant	ˈɪnfənt	n. 婴儿；幼儿； [ 律 ] 未成年人
calculate	ˈkælkjuleɪt	vi. 计算；作打算；以为
fifty		n. 五十；五十个；编号为 50 的东西
rid	rɪd	vt. 使去掉；使摆脱
porch		n. 门廊；走廊
tendency	ˈtendənsi	n. 倾向，趋势；癖好
formation	fɔːˈmeɪʃ(ə)n	n. 形成；编队；构造
scholarship		n. 学识，学问；奖学金
efficiency	ɪˈfɪʃ(ə)nsi	n. 效率；功效；效能
qualify	ˈkwɒlɪfaɪ	vt. 使具有资格；证明 … 合格；限制
derive		vt. 得自；源于
helpful	ˈhelpf(ə)l	adj. 有益的；有帮助的
impress	ɪmˈpres	vt. 盖印；强征；传送；给予某人深刻印象
heel	hiːl	n. 脚后跟；踵
resemble	rɪˈzemb(ə)l	vt. 类似，像
fabric	ˈfæbrɪk	n. 织物；构造；建筑物；组织；布
contest	ˈkɒntest	vt. 争辩；提出质疑
proportion	prəˈpɔːʃ(ə)n	n. 比例；部分；均衡；面积
maintenance	ˈmeɪntənəns	n. 维护，维修；保持；生活费用
trick	trɪk	n. 诡计；恶作剧；窍门；骗局；欺诈；花招
organic	ɔːˈɡænɪk	adj. 有机的；器官的；组织的；根本的
examination	ɪɡˌzæmɪˈneɪʃ(ə)n	n. 考试；检查；查问
strengthen	ˈstreŋθn	vt. 加强；巩固
sophisticated	səˈfɪstɪkeɪtɪd	adj. 久经世故的；富有经验的；精致的；复杂的
tennis		n. 网球（运动）
barrel		vt. 把 …… 装入桶内
ratio		n. 比率，比例
menu	ˈmenjuː	n. 菜单
desperate	ˈdespərət	adj. 令人绝望的；不顾一切的；极度渴望的
glove		n. 手套
sufficient	səˈfɪʃ(ə)nt	adj. 足够的；充分的
modest	ˈmɒdɪst	adj. 谦虚的，谦逊的；适度的；端庄的；羞怯的
delay	dɪˈleɪ	vi. 耽搁；延期
stroke		n. 打击；中风；笔画；冲程；尝试；轻抚
scope		n. 视野；眼界；范围；余地；导弹射程
handful		n. 一把；少数；棘手事
badly	ˈbædli	adv. 严重地，厉害地；恶劣地；非常，很
horizon	həˈraɪz(ə)n	n. 地平线；眼界；范围；视野
prompt	prɒmpt	adj. 敏捷的，迅速的；立刻的
        """,
        """
absorb	əbˈzɔːb	vt. 吸收；吸引；承受；理解；使 … 全神贯注
principal	ˈprɪnsəp(ə)l	n. 校长；资本；委托人，当事人；主犯
assignment	əˈsaɪnmənt	n. 分配；任务；作业；功课
restriction	rɪˈstrɪkʃ(ə)n	n. 限制；约束；束缚
laboratory	ləˈbɒrətri	n. 实验室，研究室
auto	ˈɔːtəʊ	n. 自动； [ 美口 ] 汽车（等于 automobile ）
romantic		adj. 浪漫的；空想的；多情的
cotton	ˈkɒtn	n. 棉花；棉线；棉布
motor	ˈməʊtə(r)	n. 发动机，马达；汽车
overlook	ˌəʊvəˈlʊk	vt. 俯瞰；忽略；远眺；检查；高耸于 … 之上
float	fləʊt	vt. 使漂浮；实行
undergo	ˌʌndəˈɡəʊ	vt. 经历，经受；忍受
sequence	ˈsiːkwəns	n. 序列；顺序；续发事件
jet	dʒet	n. 喷射，喷嘴；黑玉；喷气式飞机
consumption	kənˈsʌmpʃ(ə)n	n. 消费；消耗；肺痨
blade		n. 刀片，刀锋；叶片；剑
temporary	ˈtemprəri	adj. 暂时的，临时的
cabin	ˈkæbɪn	n. 小屋；客舱；船舱
bite	baɪt	vt. 咬；刺痛
edition	ɪˈdɪʃ(ə)n	n. 版本
yours	jɔːz	pron. 你们的，你的
pitch		vi. 投掷；倾斜；坠落；搭帐篷
pine	paɪn	vi. 憔悴；渴望，痛苦
absolute	ˈæbsəluːt	adj. 绝对的；完全的；专制的
appoint	əˈpɔɪnt	vt. 任命；指定；约定
God	ɡɒd	n. 神；（大写首字母时）上帝； vt. 膜拜，崇拜
lemon	ˈlemən	adj. 柠檬色的
hungry	ˈhʌŋɡri	adj. 饥饿的；渴望的；荒年的；不毛的
submit	səbˈmɪt	vt. 使服从；呈递；主张
naked	ˈneɪkɪd	adj. 裸体的；无装饰的；无证据的；直率的
anticipate	ænˈtɪsɪpeɪt	vt. 预期，期望；占先，抢先；提前使用
nut	nʌt	n. 坚果；螺母，螺帽；难对付的人，难解的问题
extension	ɪkˈstenʃ(ə)n	n. 伸展；扩大；延长；延期；电话分机
arrival	əˈraɪv(ə)l	n. 到达；到达者；到来
cope		vi. 处理；竞争；对付
flame	fleɪm	n. 火焰；热情；光辉
wound	wuːnd	n. 创伤，伤口
shower	ˈʃaʊə(r)	n. 淋浴；阵雨；（倾泻般出现的）一阵，一大批
flesh	fleʃ	n. 肉体；肉
furthermore	ˌfɜːðəˈmɔː(r)	adv. 此外；而且
twelve	twelv	n. 十二；十二个
weekly	ˈwiːkli	adj. 每周的；一周一次的；周刊的
grain	ɡreɪn	n. 粮食；颗粒；谷物；纹理
brush	brʌʃ	n. 刷子；画笔；毛笔；争吵
consciousness	ˈkɒnʃəsnəs	n. 意识；知觉；感觉；觉悟
measurement	ˈmeʒəmənt	n. 测量；尺寸；度量；量度制
province	ˈprɒvɪns	n. 省；领域；职权
ease	iːz	vt. 减轻，缓和；使安心
seize	siːz	vt. 抓住；夺取；逮捕；理解
permission	pəˈmɪʃ(ə)n	n. 允许，许可
wise	waɪz	adj. 聪明的；明智的；博学的
acid	ˈæsɪd	n. 酸； [俚] 迷幻药
medium	ˈmiːdiəm	adj. 中间的，中等的；半生熟的
distinguish	dɪˈstɪŋɡwɪʃ	vt. 辨别；区分；使杰出，使表现突出
repeatedly	rɪˈpiːtɪdli	adv. 反复地；屡次地；再三地
lung	lʌŋ	n. 肺；呼吸器
distinct	dɪˈstɪŋkt	adj. 清楚的；明显的；独特的；有区别的
artistic	ɑːˈtɪstɪk	adj. 艺术的；有美感的；风雅的
impressive	ɪmˈpresɪv	adj. 感人的；令人钦佩的；给人以深刻印象的
moderate		adj. 适度的，中等的；有节制的；稳健的，温和的
subsequent		adj. 后来的，随后的
frequent	ˈfriːkwənt	adj. 时常发生的；频繁的；惯常的
pet	pet	n. 宠物；受宠爱的人；生气
exceed	ɪkˈsiːd	vt. 胜过；超过
rhythm	ˈrɪðəm	n. 节奏；韵律
widespread	ˈwaɪdspred	adj. 普遍的，广泛的；分布广的
sin	sɪn	n. 过失；罪恶；罪孽
charity	ˈtʃærəti	n. 施舍；慈善；慈善团体；施舍物；宽容
transformation	ˌtrænsfəˈmeɪʃ(ə)n	n. 转化；转换；变形；改革
everyday	ˈevrideɪ	adj. 每天的，日常的
headline		n. 大标题；内容提要；栏外标题；头版头条新闻
venture	ˈventʃə(r)	vt. 敢于
adequate	ˈædɪkwət	adj. 适当的；胜任的；充足的
grocery	ˈɡrəʊsəri	n. 食品杂货店
intensity	ɪnˈtensəti	n. 强烈；强度；亮度；紧张
exhibit	ɪɡˈzɪbɪt	vt. 显示；展览；提出（证据等）
margin	ˈmɑːdʒɪn	n. 边缘；利润，余裕；页边的空白
rope	rəʊp	n. 绳，绳索
concrete	ˈkɒŋkriːt	adj. 实在的，具体的；有形的；混凝土的
chase	tʃeɪs	vt. 追逐；追捕；试图赢得；雕镂
        """,
        """
brick	brɪk	n. 砖，砖块；砖形物； [ 口 ] 心肠好的人
horror	ˈhɒrə(r)	n. 惊骇；惨状；极端厌恶；令人恐怖的事物
painter	ˈpeɪntə(r)	n. 画家；油漆匠
sake		n. 理由；日本米酒；利益；目的
gaze	ɡeɪz	vi. 注视；凝视
courage	ˈkʌrɪdʒ	n. 勇气；胆量
swear	sweə(r)	vt. 发誓；咒骂
clue	kluː	n. 线索；（故事等的）情节
reinforce	ˌriːɪnˈfɔːs	vt. 加强，加固；补充；强化
confusion	kənˈfjuːʒn	n. 混淆，混乱；困惑
slice		n. 薄片；部分；菜刀，火铲
occupation	ˌɒkjuˈpeɪʃ(ə)n	n. 职业；占有；消遣；占有期
coal	kəʊl	vi. 加煤；上煤
sacred	ˈseɪkrɪd	adj. 神圣的；庄严的；宗教的；神的
formula		n. 公式，准则；配方；婴儿食品
collective	kəˈlektɪv	adj. 集体的；集体主义的；共同的； [ 语 ] 集合的
exact	ɪɡˈzækt	adj. 精确的；准确的，精密的
uncle	ˈʌŋk(ə)l	n. 伯父；姨丈；叔叔；舅父；姑父；伯伯
sigh	saɪ	vi. 叹息，叹气
attribute	əˈtrɪbjuːt	n. 属性；特质
dare	deə(r)	n. 挑战；挑动
gallery	ˈɡæləri	n. 走廊；画廊；地道；旁听席
lap		n. 一圈；膝盖；下摆；山坳
grave	ɡreɪv	adj. 严肃的；重大的；黯淡的
toe	təʊ	n. 脚趾；足尖
inquiry	ɪnˈkwaɪəri	n. 调查；质询；探究
surprisingly	səˈpraɪzɪŋli	adv. 惊人地；出人意外地
decrease	dɪˈkriːs	n. 减少，减小；减少量
indication		n. 指示，指出；迹象；象征
rail		n. 横杆；扶手；围栏；铁轨
compose	kəmˈpəʊz	vt. 使平静；构成；写作；排 … 的版
legend	ˈledʒənd	n. 传奇；说明；图例；刻印文字
regulate	ˈreɡjuleɪt	vt. 调节，规定；有系统的管理；控制；校准
resign		vt. 辞职；放弃；使听从；委托
gentle	ˈdʒent(ə)l	adj. 温和的；文雅的
organ	ˈɔːɡən	n. 风琴；器官；机构；管风琴；嗓音
lightly	ˈlaɪtli	adv. 轻轻地；轻松地；容易地；不费力地
concerning	kənˈsɜːnɪŋ	prep. 关于；就 … 而言
laughter	ˈlɑːftə(r)	n. 笑声；笑
northwest	ˌnɔːθˈwest	adj. 西北的；来自西北的；向西北的
insect	ˈɪnsekt	n. 昆虫；卑鄙的人
magic	ˈmædʒɪk	n. 巫术；魔法；戏法
superior	suːˈpɪəriə(r)	adj. 上级的；高傲的；优秀的，出众的
stem	stem	n. 茎；干；船首；血统
surgeon	ˈsɜːdʒən	n. 外科医生
acceptable	əkˈseptəb(ə)l	adj. 可接受的；可忍受的；合意的
physics	ˈfɪzɪks	n. 物理学；物理现象
hunt	hʌnt	vt. 搜索；打猎
continuous	kənˈtɪnjuəs	adj. 连续的，持续的；继续的；连绵不断的
log	lɒɡ	vi. 伐木
echo		vt. 重复；反射
pill	pɪl	n. 药丸；口服避孕药；弹丸，子弹
compound	ˈkɒmpaʊnd	vt. 混合；合成；和解妥协；搀合
flour	ˈflaʊə(r)	n. 面粉；粉状物质
bare	beə(r)	adj. 空的；赤裸的，无遮蔽的
slope		n. 倾斜；斜率；斜坡；扛枪姿势
greatly	ˈɡreɪtli	adv. 非常；很，大大地
acceptance	əkˈseptəns	n. 接受；承兑；赞同；接纳
pump	pʌmp	vt. 用抽水机抽 … ；打气
medal		n. 勋章，奖章；纪念章
sponsor		n. 保证人；赞助者；主办者
secondary		adj. 次要的；第二的；中级的；中等的
slam	slæm	vt. 砰地关上；猛力抨击
export	ɪkˈspɔːt; ˈekspɔːt	n. 输出，出口；出口商品
experimental	ɪkˌsperɪˈment(ə)l	adj. 实验的；试验性的；根据实验的
melt	melt	vi. 熔化，溶解；渐混
midnight	ˈmɪdnaɪt	n. 午夜，半夜 12 点钟
curve	kɜːv	n. 曲线；弯曲；曲线球；曲线图表
evident	ˈevɪdənt	adj. 明显的；明白的
logic	ˈlɒdʒɪk	n. 逻辑学；逻辑性；逻辑
exclude	ɪkˈskluːd	vt. 排除；排斥；拒绝接纳；逐出
harsh	hɑːʃ	adj. 粗糙的；刺耳的；严厉的；严酷的；刺目的
interior	ɪnˈtɪəriə(r)	n. 内部；本质
corridor		n. 走廊
weakness	ˈwiːknəs	n. 弱点；软弱；嗜好
pig	pɪɡ	n. 猪肉；猪；警察
classical	ˈklæsɪk(ə)l	adj. 古典的；经典的；第一流的；传统的
civilization	ˌsɪvəlaɪˈzeɪʃn	n. 文明；文化
fold	fəʊld	vt. 折叠；合拢；抱住；笼罩
reverse	rɪˈvɜːs	n. 背面；相反；倒退；失败； vt. 颠倒；倒转； adj. 反面的；颠倒的； [ 篮球 ] 反身的； vi. 倒退； [ 桥牌 ] 逆叫
        """,
        """
missing	ˈmɪsɪŋ	adj. 失踪的；缺少的
flash	flæʃ	vt. 使闪光；反射
reliable	rɪˈlaɪəb(ə)l	adj. 可靠的；可信赖的
Roman	ˈrəʊmən	n. 罗马人；古罗马语
architecture	ˈɑːkɪtektʃə(r)	n. 建筑学；建筑式样；建筑风格
elbow	ˈelbəʊ	n. 弯头；肘部；扶手
deadly	ˈdedli	adj. 致命的；死一般的；非常的
confuse	kənˈfjuːz	vt. 使混乱；使困惑
monthly	ˈmʌnθli	adj. 每月的，每月一次的；有效期为一个月的
duck	dʌk	n. 鸭子；鸭肉；（英）宝贝儿； [ 版 ] 零分
dose		n. 剂量；一剂，一服
lecture	ˈlektʃə(r)	n. 演讲；讲稿；教训
van	væn	n. 先锋；厢式货车； [ 计 ] 增值网
sixth	sɪksθ	n. 六分之一；月的第六日，（与 the 连用的）第六个
bay	beɪ	n. 海湾；狗吠声
suburb	ˈsʌbɜːb	n. 郊区；边缘
sandwich	ˈsænwɪtʃ	vt. 夹入；挤进；把 ... 做成三明治
motivate	ˈməʊtɪveɪt	vt. 刺激；使有动机；激发 … 的积极性
render	ˈrendə(r)	vt. 提出；致使；实施；以 … 回报；着色
restrict	rɪˈstrɪkt	vt. 限制；限定；约束
cloth		n. 布，织物；餐巾
enforce	ɪnˈfɔːs	vt. 强迫，强制；实施，执行
lens		n. 透镜；镜头；晶状体
assemble	əˈsemb(ə)l	vt. 集合，聚集；装配；收集
rat	ræt	n. 鼠；卑鄙小人，叛徒
inevitable	ɪnˈevɪtəb(ə)l	adj. 必然的，不可避免的
southwest	ˌsaʊθˈwest	n. 西南方
unexpected	ˌʌnɪkˈspektɪd	adj. 意外的，想不到的
obstacle	ˈɒbstək(ə)l	n. 障碍，干扰；妨害物
motive	ˈməʊtɪv	n. 动机，目的；主题
ownership	ˈəʊnəʃɪp	n. 所有权；物主身份
inspection	ɪnˈspekʃ(ə)n	n. 视察，检查
consult	kənˈsʌlt	vt. 商量；查阅；向 … 请教
forgive	fəˈɡɪv	vt. 原谅；免除（债务、义务等）
drain	dreɪn	vi. 排水；流干
maximum	ˈmæksɪməm	n. 极大，最大限度；最大量
bacteria	bækˈtɪəriə	n. 细菌(复数)
privilege	ˈprɪvəlɪdʒ	n. 特权；优待；基本权利
outdoor	ˈaʊtdɔː(r)	adj. 户外的；露天的；野外的（等于 out-of-door ）
suck	sʌk	vt. 吸吮；吸取
broadcast	ˈbrɔːdkɑːst	vt. （无线电或电视）广播；播撒（种子）；播送，播放
wrist	rɪst	n. 腕关节；手腕
cattle	ˈkæt(ə)l	n. [ 总称 ] 牛；家畜；牲畜（骂人的话）；无价值的人
minimum	ˈmɪnɪməm	n. 最小量；最小值；最小化；最低限度
dump	dʌmp	vt. 倾倒；倾卸
hence	hens	adv. 因此；今后
execute	ˈeksɪkjuːt	vt. 执行；实行；处死
limb		n. 肢，臂；枝干；分支
harm	hɑːm	n. 伤害；损害
endure	ɪnˈdjʊə(r)	vt. 忍耐；容忍
radiation	ˌreɪdiˈeɪʃn	n. 辐射；发光；放射物
horn	hɔːn	n. 角；喇叭，号角
peaceful	ˈpiːsf(ə)l	adj. 和平的，爱好和平的；平静的
strain	streɪn	n. 张力；拉紧；血缘；负担；扭伤
behave	bɪˈheɪv	vi. 表现；举止端正；（机器等）运转；（事物）起某种作用
transmission	trænzˈmɪʃ(ə)n	n. 传送；播送；传递；传动装置，变速器
await	əˈweɪt	vt. 等候，等待；期待
spill	spɪl	vt. 使溢出，使流出；使摔下
grateful	ˈɡreɪtf(ə)l	adj. 感谢的；令人愉快的，宜人的
grace	ɡreɪs	n. 优雅；恩惠；慈悲；魅力
towel	ˈtaʊəl	n. 毛巾，手巾；纸巾
modify		vt. 修改，修饰；更改
particle	ˈpɑːtɪk(ə)l	n. 颗粒；质点；极小量；小品词
prize	praɪz	n. 奖品；奖赏；战利品
landing	ˈlændɪŋ	n. 登陆；楼梯平台；码头
bat	bæt	n. 蝙蝠；球棒；批处理文件的扩展名
alarm	əˈlɑːm	n. 警报，警告器；惊慌
grip	ɡrɪp	n. 紧握；柄；支配；握拍方式；拍柄绷带； vt. 紧握；夹紧； vi. 抓住
sweat	swet	vi. 流出；使出汗；使干苦活；剥削；藉出汗减轻；焦急地期待
outer	ˈaʊtə(r)	adj. 远离中心的；外面的，外部的
separation	ˌsepəˈreɪʃn	n. 分离，分开；分居；间隔，距离；缺口
govern	ˈɡʌv(ə)n	vt. 统治；支配；控制；管理
southeast	ˌsaʊθˈiːst	adj. 东南的；来自东南的
wherever	weərˈevə(r)	adv. 无论什么地方；究竟在哪里
vitamin	ˈvɪtəmɪn	n. 维生素；维他命
enthusiasm	ɪnˈθjuːziæzəm	n. 热心，热忱，热情
accommodate	əˈkɒmədeɪt	vt. 供应；使适应；调解；容纳
praise	preɪz	n. 称赞；赞扬；崇拜；荣耀
injure	ˈɪndʒə(r)	vt. 伤害，损害
respectively	rɪˈspektɪvli	adv. 分别地；各自地，独自地
        """,
        """
mechanical	məˈkænɪkl	adj. 机械的；呆板的；力学的；无意识的；手工操作的
canvas	ˈkænvəs	n. 帆布
forty	ˈfɔːti	n. 四十
reserve	rɪˈzɜːv	n. 储备，储存；预备队；储备金；自然保护区
beam	biːm	n. 横梁；船宽；电波；光线；秤杆
apologize	əˈpɒlədʒaɪz	vi. 道歉；辩解；赔不是
genuine	ˈdʒenjuɪn	adj. 真实的，真正的；诚恳的
thumb	θʌm	vt. 以拇指拨弄；作搭车手势；笨拙地摆弄；翻阅
unity	ˈjuːnəti	n. 联合；团结；一致；个体
compromise		vt. 妥协；危害
scatter	ˈskætə(r)	vi. 分散，散开；散射
commonly	ˈkɒmənli	adv. 一般地；通常地；普通地
convey	kənˈveɪ	vt. 传达；让与；运输
twist	twɪst	vt. 捻；拧；扭伤；编织；使苦恼
disk		n. 磁盘，磁碟片；圆盘，盘状物；唱片
suspicion	səˈspɪʃ(ə)n	n. 怀疑；疑心；一点儿；嫌疑
residence	ˈrezɪdəns	n. 居住；住宅，住处
suspend	səˈspend	vt. 延缓，推迟；使暂停；使悬浮； vi. 悬浮； [ 体 ] 禁赛
rebel	ˈreb(ə)l; rɪˈbel	vi. 造反；反叛；反抗
fluid		adj. 流动的；不固定的；流畅的
pension	ˈpenʃ(ə)n	n. 津贴；退休金，抚恤金；膳宿费
resume	rɪˈzjuːm	n. 摘要；履历；个人简历
theoretical	ˌθɪəˈretɪk(ə)l	adj. 理论的；假设的；理论上的；推理的
delicate	ˈdelɪkət	adj. 微妙的；易碎的；清淡可口的；柔和的；精美的，雅致的；纤弱的
forehead	ˈfɔːhed; ˈfɒrɪd	n. 额，前额
bounce	baʊns	n. 弹力；活力；跳
electrical	ɪˈlektrɪk(ə)l	adj. 有关电的；电气科学的
hook	hʊk	n. 挂钩，吊钩
attraction	əˈtrækʃ(ə)n	n. 吸引，吸引力；吸引人的事物； [ 物 ] 引力
altogether		adv. 总而言之；完全地；总共
carve	kɑːv	vt. 雕刻；切开；开创
needle	ˈniːd(ə)l	n. 针；指针；针状物；刺激
scare	skeə(r)	vt. 惊吓；把 …吓跑
invisible	ɪnˈvɪzəb(ə)l	adj. 无形的，看不见的；不显眼的，暗藏的；无形的
ankle	ˈæŋk(ə)l	n. 踝关节，踝
rider	ˈraɪdə(r)	n. 附文；骑手；扶手
noon	nuːn	n. 中午；正午；全盛期
identical		adj. 同一的；完全相同的
precise	prɪˈsaɪs	adj. 精确的；明确的；严格的
structural	ˈstrʌktʃərəl	adj. 结构的；建筑的
crawl	krɔːl	vi. 爬行；匍匐行进
provided	prəˈvaɪdɪd	conj. 倘若；假如
promising	ˈprɒmɪsɪŋ	adj. 有希望的，有前途的
conscious	ˈkɒnʃəs	adj. 意识到的；故意的；神志清醒的
determination	dɪˌtɜːmɪˈneɪʃ(ə)n	n. 决心；果断
hers	hɜːz	pron. 她的（所有格）
opera		n. 歌剧；歌剧团；歌剧院
pit	pɪt	n. 深坑；矿井；陷阱；（英国剧场的）正厅后排；（物体或人体表面上的）凹陷；正厅后排的观众
departure	dɪˈpɑːtʃə(r)	n. 离开；出发；违背
elevator	ˈelɪveɪtə(r)	n. 电梯；升降舵；升降机；起卸机
guidance	ˈɡaɪd(ə)ns	n. 指导，引导；领导
happiness	ˈhæpinəs	n. 幸福(live in happiness)
pursuit	pəˈsjuːt	n. 职业，工作；追赶，追求
decent		adj. 得体的；正派的；相当好的
oral	ˈɔːrəl	adj. 口头的，口述的
clerk	klɑːk	n. 职员，办事员；店员；书记；记帐员
destination	ˌdestɪˈneɪʃn	n. 目的地，终点
fist	fɪst	n. 拳头；掌握；笔迹
generous	ˈdʒenərəs	adj. 慷慨的，大方的；宽宏大量的；有雅量的
thereby	ˌðeəˈbaɪ	adv. 从而，因此；在那附近；在那方面
sunlight		n. 日光
feedback	ˈfiːdbæk	n. 反馈；回复；成果，资料
purple	ˈpɜːp(ə)l	adj. 紫色的；帝王的；华而不实的
laser		n. 激光
practically	ˈpræktɪkli	adv. 几乎；事实上；实际地
hint	hɪnt	n. 暗示；线索
sharply	ˈʃɑːpli	adv. 急剧地；锐利地
parade	pəˈreɪd	n. 游行；阅兵；行进；炫耀；阅兵场
realm	relm	n. 领域，范围；王国
cancel	ˈkæns(ə)l	vt. 取消；删去
blend	blend	vt. 混合
peel	piːl	v. 剥，剥落；削
candle	ˈkænd(ə)l	n. 蜡烛；烛光；烛形物
servant	ˈsɜːvənt	n. 仆人，佣人；公务员；雇工
vanish	ˈvænɪʃ	vi. 消失；突然不见； [ 数 ] 成为零
chin		n. 下巴；聊天；引体向上动作
necessity	nəˈsesəti	n. 需要；必需品；必然性
confess	kənˈfes	vt. 承认；坦白；供认；忏悔
casual	ˈkæʒuəl	adj. 偶然的；随便的；临时的；非正式的
gravity	ˈɡrævəti	n. 重力，地心引力；庄严；严重性
        """,
        """
chop	tʃɒp	n. 砍；排骨；商标； [ 网 ] 削球； vt. 剁碎；砍；（俚）丑人
isolate		vt. 使孤立；使绝缘；使隔离
productive	prəˈdʌktɪv	adj. 多产的；富有成效的；能生产的；生产的，生产性的
assembly	əˈsembli	n. 装配；集会，集合
silk	sɪlk	n. 蚕丝；丝绸；丝织物
steep	stiːp	adj. 陡峭的；夸大的；不合理的；急剧升降的
dynamic	daɪˈnæmɪk	adj. 动力的；动力学的；动态的；有活力的
equation		n. 方程式，等式；相等；反应式
angel	ˈeɪndʒl	n. 天使；守护神；善人
rage	reɪdʒ	n. 愤怒；狂暴，肆虐；情绪激动
prohibit	prəˈhɪbɪt	vt. 阻止，禁止
tender	ˈtendə(r)	adj. 柔软的；温柔的；脆弱的；幼稚的；难对付的
outstanding	aʊtˈstændɪŋ	adj. 杰出的；显著的；未偿付的；未解决的
stumble	ˈstʌmb(ə)l	vi. 踌躇，蹒跚；失足；犯错
automobile	ˈɔːtəməbiːl	n. 汽车
artificial	ˌɑːtɪˈfɪʃ(ə)l	adj. 人造的；仿造的；虚伪的；非原产地的；武断的
dawn	dɔːn	n. 黎明；开端
abstract	ˈæbstrækt	n. 抽象；摘要；抽象的概念
descend	dɪˈsend	vi. 下降；下来；遗传；屈尊；下去
silly	ˈsɪli	adj. 愚蠢的
tide	taɪd	n. 潮汐；趋势，潮流
readily	ˈredɪli	adv. 容易地；乐意地；无困难地
cooperate	kəʊˈɒpəreɪt	vi. 合作，配合；协力
revolutionary	ˌrevəˈluːʃənəri	adj. 革命的；旋转的；大变革的
hardware	ˈhɑːdweə(r)	n. 计算机硬件；五金器具
pillow	ˈpɪləʊ	n. 枕头
seal	siːl	n. 密封；封条；印章；海豹；标志
circuit	ˈsɜːkɪt	n. 电路，回路；环道；一圈；巡回
shortage	ˈʃɔːtɪdʒ	n. 不足；缺乏，缺少
lately	ˈleɪtli	adv. 近来，不久前
scan	skæn	vt. 扫描；细看；浏览；详细调查；标出格律
rear		vt. 培养；树立；栽种
undertake	ˌʌndəˈteɪk	vt. 承担，保证；从事；同意；试图
softly	ˈsɒftli	adv. 温柔地；柔和地；柔软地；静静地
ridiculous		adj. 可笑的；荒谬的
automatic		adj. 自动的；无意识的；必然的
import	ˈɪmpɔːt	n. 输入；进口，进口货；意思，含义；重要性
cart	kɑːt	n. 二轮运货马车
mate	meɪt	n. 同事；助手，大副；配偶；配对物
Greek	ɡriːk	n. 希腊语；希腊人
genius	ˈdʒiːniəs	n. 天才，天赋；精神
sacrifice	ˈsækrɪfaɪs	n. 牺牲；供奉；祭品
invitation	ˌɪnvɪˈteɪʃ(ə)n	n. 邀请；引诱
accuracy	ˈækjərəsi	n. 精确度，准确性
loyalty	ˈlɔɪəlti	n. 忠心；忠诚；忠实
gasoline	ˈɡæsəliːn	n. 汽油
stiff		adj. 严厉的；呆板的；坚硬的；拘谨的；艰难的
output	ˈaʊtpʊt	n. 产量；输出，输出量；出产
jaw		n. 颌；下巴；狭窄入口；唠叨
mysterious	mɪˈstɪəriəs	adj. 神秘的；不可思议的；难解的
rocket	ˈrɒkɪt	vi. 飞驰，飞快地移动；迅速增加
ours	ˈaʊəz	pron. 我们的
bubble	ˈbʌb(ə)l	n. 气泡，泡沫，泡状物；透明圆形罩，圆形顶
barn		n. 谷仓；车库；畜棚；靶（核反应截面单位）
sword	sɔːd	n. 刀，剑；武力，战争
seventh	ˈsev(ə)nθ	n. 第七；七分之一
glory	ˈɡlɔːri	n. 光荣，荣誉；赞颂
protective	prəˈtektɪv	adj. 防护的；关切保护的；保护贸易的
tuck	tʌk	vt. 卷起；挤进；用某物舒适地裹住；使有褶裥；收拢；大口吃；使隐藏
drum	drʌm	vt. 击鼓；大力争取
faint	feɪnt	adj. 头晕的；虚弱的；模糊的；衰弱的
queen	kwiːn	n. 女王，王后；（纸牌中的）皇后；（蜜蜂等的）蜂王
input	ˈɪnpʊt	n. 投入；输入电路
specialize	ˈspeʃəlaɪz	vi. 专门从事；详细说明； [ 生 ] 特化
northeast	ˌnɔːθˈiːst	adj. 东北的；来自东北的；向东北的
shallow	ˈʃæləʊ	adj. 浅的；肤浅的
merchant	ˈmɜːtʃənt	n. 商人，批发商；店主
nest	nest	n. 巢，窝；安乐窝；温床
lane	leɪn	n. 小巷；航线；车道； [ 篮球 ] 罚球区
ancestor	ˈænsestə(r)	n. 始祖，祖先；被继承人
condemn	kənˈdem	vt. 判刑，定罪；谴责；声讨
accent	ˈæksent	n. 口音；重音；重音符号；强调；特点
optimistic	ˌɒptɪˈmɪstɪk	adj. 乐观的；乐观主义的
unite	juˈnaɪt	vt. 使 … 混合；使 … 联合；使 … 团结
equip	ɪˈkwɪp	vt. 装备，配备
rack	ræk	n. 行李架；拷问台；齿条
wolf	wʊlf	n. 狼；残忍贪婪之人；色狼
agriculture	ˈæɡrɪkʌltʃə(r)	n. 农业；农艺，农学
hostile	ˈhɒstaɪl	adj. 敌对的，敌方的；怀敌意的
instantly	ˈɪnstəntli	adv. 即刻地；立即地；马上地
        """,
        """
orbit	ˈɔːbɪt	n. 轨道；眼眶；势力范围；生活常规
grasp		n. 理解；控制；抓住
tropical	ˈtrɒpɪk(ə)l	adj. 热带的；酷热的；热情的
afterward	ˈɑːftəwəd	adv. 以后，后来
reliability	rɪˌlaɪəˈbɪləti	n. 可靠性
drawer	drɔː(r)	n. 抽屉；开票人
discourage	dɪsˈkʌrɪdʒ	vt. 使气馁；阻止
runner	ˈrʌnə(r)	n. 走私者；跑步者；推销员；送信人
intimate	ˈɪntɪmət	adj. 亲密的；私人的；精通的； [ 婉 ] 有性关系的
empire	ˈempaɪə(r)	n. 帝国；帝王统治，君权
neutral		adj. 中立的，中性的；中立国的；非彩色的
biology	baɪˈɒlədʒi	n. 生物学；（一个地区全部的）生物
flexible	ˈfleksəb(ə)l	adj. 灵活的；柔韧的；易弯曲的
republic	rɪˈpʌblɪk	n. 共和国；共和政体
terrific	təˈrɪfɪk	adj. 极好的；极其的，非常的；可怕的
instinct	ˈɪnstɪŋkt	n. 本能，直觉；天性
whoever	huːˈevə(r)	pron. 无论谁；任何人
ministry	ˈmɪnɪstri	n. （政府的）部门
loop		vi. 打环；翻筋斗
whip		vt. 抽打；煽动；搅打（蛋，奶油）； [ 口 ] 彻底击败
puzzle	ˈpʌz(ə)l	vt. 使 … 困惑；使 … 为难；苦思而得出
mushroom	ˈmʌʃrʊm	n. 蘑菇，伞菌；蘑菇形物体；暴发户
jar	dʒɑː(r)	n. 广口瓶；震动；刺耳声；罐
earthquake	ˈɜːθkweɪk	n. 地震；大动荡
pork	pɔːk	n. 猪肉
safely	ˈseɪfli	adv. 安全地
sympathy	ˈsɪmpəθi	n. 同情；赞同；慰问
eleven	ɪˈlev(ə)n	n. 十一；十一个
bureau	ˈbjʊərəʊ	n. 衣柜；局，处；办公桌
ambition	æmˈbɪʃ(ə)n	n. 野心，雄心；抱负，志向
utilize		vt. 利用
costly	ˈkɒstli	adj. 昂贵的；代价高的
influential		adj. 有影响的；有势力的
uncertain		adj. 含糊的；无常的；靠不住的；迟疑不决的
dissolve		vt. 使溶解；使液化；使分解
statistical	stəˈtɪstɪk(ə)l	adj. 统计的；统计学的
insert	ɪnˈsɜːt	vt. 插入；嵌入
booth		n. 货摊；公用电话亭
helmet	ˈhelmɪt	n. 钢盔，头盔
waist		n. 腰，腰部
lion	ˈlaɪən	n. 狮子；名人；勇猛的人(as brave as a lion)；社交场合的名流
crush		vt. 压碎；弄皱，变形；使 … 挤入
cord	kɔːd	n. 束缚；绳索
tolerate	ˈtɒləreɪt	vt. 忍受；默许；宽恕
decorate	ˈdekəreɪt	vt. 装饰；布置；授勋给
pulse	pʌls	n. 脉搏；脉冲
equivalent		adj. 等价的，相等的；同意义的
midst		n. 当中，中间
punch	pʌntʃ	n. 冲压机；打洞器；钻孔机
pin	pɪn	n. 大头针，针；栓；琐碎物
elephant	ˈelɪfənt	n. 象；大号图画纸
consequently	ˈkɒnsɪkwəntli	adv. 所以；因此；结果
cheat	tʃiːt	vt. 欺骗；骗取
heal	hiːl	vt. 治愈，痊愈；和解
fever	ˈfiːvə(r)	n. 发烧，发热；狂热
filter	ˈfɪltə(r)	vi. 慢慢传开；滤过；渗入
rabbit	ˈræbɪt	n. 兔子，野兔
exploit		vt. 开发，开拓；剥削；开采
organism	ˈɔːɡənɪzəm	n. 有机体；生物体；微生物
partially	ˈpɑːʃəli	adv. 部分地；偏袒地
conservation	ˌkɒnsəˈveɪʃ(ə)n	n. 保存，保持；保护
stove	stəʊv	n. 火炉；窑；温室
consent	kənˈsent	vi. 同意；答应；赞成
comprise	kəmˈpraɪz	vt. 包含；由 … 组成
alike	əˈlaɪk	adj. 相同的；相似的
dam		v. 筑坝；控制
well-known	ˌwel ˈnəʊn	adj. 著名的；众所周知的；清楚明白的
preliminary	prɪˈlɪmɪnəri	n. 初步措施；预赛；准备
shy	ʃaɪ	adj. 害羞的；畏缩的，胆怯的
gross	ɡrəʊs	adj. 总共的；粗野的；恶劣的；显而易见的
frontier	ˈfrʌntɪə(r)	n. 边界；国境；前沿
disclose	dɪsˈkləʊz	vt. 揭露；公开
beast	biːst	n. 野兽；畜生，人面兽心的人
interfere	ˌɪntəˈfɪə(r)	vi. 干涉；打扰；妨碍
oak	əʊk	n. [ 植 ] 橡树；橡木家具；橡木色
eighth	eɪtθ	adj. 第八的；八分之一的
notebook	ˈnəʊtbʊk	n. 笔记本，笔记簿；手册
outline	ˈaʊtlaɪn	n. 轮廓；大纲；概要；略图
behalf	bɪˈhɑːf	n. 利益；代表
mill	mɪl	vi. 乱转；被碾磨
        """,
        """
installation	ˌɪnstəˈleɪʃ(ə)n	n. 安装，装置；就职
stimulate		vt. 刺激；鼓舞，激励
vertical	ˈvɜːtɪk(ə)l	adj. 垂直的，直立的；头顶的，顶点的
fleet	fliːt	adj. 快速的，敏捷的
catalog		n. 目录；登记
compel	kəmˈpel	vt. （源自外界）强迫，迫使；强使发生
instant	ˈɪnstənt	adj. 立即的；紧急的；紧迫的
proclaim		vt. 声明；宣告，公布；表明；赞扬
constitution	ˌkɒnstɪˈtjuːʃ(ə)n	n. 宪法；体质；章程；构造
scent	sent	n. 气味；痕迹；嗅觉；察觉能力
stack	stæk	n. 堆；堆叠
cease	siːs	vi. 停止；终了
inherit	ɪnˈherɪt	vt. 继承；遗传而得
tray	treɪ	n. 托盘；文件盒；隔底匣；（无线电的）发射箱
pioneer	ˌpaɪəˈnɪə(r)	n. 拓荒者；先锋
textbook	ˈtekstbʊk	n. 教科书，课本
nasty		adj. 下流的；肮脏的；脾气不好的；险恶的； n. 令人不快的事物；性的吸引力
shrink		vi. 收缩；畏缩
dot	dɒt	n. 点，圆点；嫁妆
wheat	wiːt	n. 小麦；小麦色
fierce	fɪəs	adj. 凶猛的；暴躁的；猛烈的
rational	ˈræʃ(ə)nəl	adj. 理性的；合理的
kingdom	ˈkɪŋdəm	n. 王国；领域；界
weaken	ˈwiːkən	vt. 减少；使变弱；使变淡
exclusively	ɪkˈskluːsɪvli	adv. 专有地；排外地；唯一地
unfair		adj. 不公平的，不公正的
invade	ɪnˈveɪd	vt. 侵略；侵袭；侵扰；涌入
classify	ˈklæsɪfaɪ	vt. 分类；分等
density		n. 密度
dip		vt. 浸，泡，蘸；舀取；把伸入
likewise	ˈlaɪkwaɪz	adv. 也；同样地
sphere	sfɪə	n. 范围；球体、领域
boast	bəʊst	vt. 以有 … 而自豪；夸口说，自吹自擂说
pat	pæt	adj. 恰好的；熟练的；合适的
sole	səʊl	n. 鞋底；脚底；基础；鳎目鱼
strictly	ˈstrɪktli	adv. 严格地；完全地；确实地
partial	ˈpɑːʃ(ə)l	adj. 局部的；不公平的； [ 口 ] 偏爱的
collar	ˈkɒlə(r)	n. 衣领；颈圈
weed	wiːd	vt. 除草；铲除
monkey	ˈmʌŋki	n. 猴子；顽童
listener	ˈlɪsənə(r)	n. 听众
lightning		adj. 快速的；闪电的
sleeve	sliːv	n. 袖子，袖套；套筒，套管
disturb	dɪˈstɜːb	vt. 打扰；妨碍；弄乱；使不安；使恼怒
rib	rɪb	n. 肋骨；排骨；肋状物
excessive	ɪkˈsesɪv	adj. 过分的；过多的，极度的
rod	rɒd	n. 棒；枝条；惩罚；权力
logical	ˈlɒdʒɪk(ə)l	adj. 逻辑学的；合逻辑的，合理的
ash	æʃ	n. 灰；灰烬
slavery		n. 奴役；奴隶制度；奴隶身分
blank	blæŋk	adj. 空白的；空虚的；单调的
cure	kjʊə(r)	vt. 治愈；治疗；加工处理；使硫化
hunger	ˈhʌŋɡə(r)	n. 渴望；饿，饥饿
dying	ˈdaɪɪŋ	adj. 临终的，垂死的
spare	speə(r)	vt. 节约，吝惜；饶恕；分出，分让
equality	iˈkwɒləti	n. 平等；相等； [ 数 ] 等式
fame	feɪm	n. 名声，名望； [ 古 ] 传闻，传说
carrot	ˈkærət	n. 胡萝卜
plunge	plʌndʒ	n. 投入；跳进
chill	tʃɪl	n. 寒冷；寒心；寒意
refuge	ˈrefjuːdʒ	n. 庇护；避难；避难所
steer	stɪə(r)	vt. 驾驶（steer the boat）；控制，引导
outlet	ˈaʊtlet	n. 出口，排放孔；发泄的方法；销路；电源插座；批发商店
thrive	θraɪv	vi. 繁荣，兴旺；茁壮成长
chew	tʃuː	n. 咀嚼；咀嚼物
elaborate	ɪˈlæbərət	adj. 精心制作的；详尽的；煞费苦心的
triumph	ˈtraɪʌmf	n. 胜利，凯旋；欢欣
calculation	ˌkælkjuˈleɪʃ(ə)n	n. 计算；估计；深思熟虑；计算的结果
interval	ˈɪntəv(ə)l	n. 间隔；幕间休息；间距
scratch	skrætʃ	n. 抓痕；擦伤；刮擦声；乱写
lid	lɪd	n. 盖子；眼睑；限制
dumb	dʌm	adj. 哑的，无说话能力的；不说话的，无声音的
bulk		n. 体积，容量；大块；大多数，大部分
sprinkle		n. 少量；撒，洒
liver	ˈlɪvə(r)	n. 肝脏；生活者，居民
harmony	ˈhɑːməni	n. 协调；融洽；调和；和睦
solely	ˈsəʊlli	adv. 单独地，唯一地
tolerance	ˈtɒlərəns	n. 公差；容忍；宽容；公差
goat	ɡəʊt	n. 山羊；替罪羊（美俚）；色鬼（美俚）
peasant	ˈpez(ə)nt	n. 农民(佃农)；乡下人
        """,
        """
supermarket		n. 超级市场；自助售货商店
mobile	ˈməʊbaɪl	adj. 易变的，；非固定的；机动的
calendar	ˈkælɪndə(r)	n. 日历(perpetual calendar)；历法；日程表
widow		vt. 使成寡妇
voluntary		adj. 志愿的；自愿的；故意的；自发的
valid		adj. 正当的；有效的，有根据的
removal		n. 移动；免职；排除；搬迁
thread		n. 线；线状物；玻璃纤维；思路；路线； [ 机 ] 螺纹； [ 美俚 ] 衣服
unhappy		adj. 不幸福的；不快乐的；不适当的
fraction	ˈfrækʃn	n. 小部分；部分；稍微； [ 数 ] 分数
straw	strɔː	n. 稻草；吸管；一文不值的东西
exhaust	ɪɡˈzɔːst	vt. 耗尽；排出；使精疲力尽；彻底探讨
objection	əbˈdʒekʃ(ə)n	n. 异议，反对；妨碍；缺陷，缺点；拒绝的理由
chemistry	ˈkemɪstri	n. 化学；化学过程
blast		n. 爆炸；冲击波；一阵
prevail	prɪˈveɪl	vi. 盛行，流行；战胜，获胜
overnight	ˌəʊvəˈnaɪt	adv. 通宵；昨晚；突然
screw	skruː	vt. 旋，拧；压榨；强迫
warmth	wɔːmθ	n. 温暖；热情；激动
undergraduate		n. 大学生；大学肄业生
cruel		adj. 残酷的，残忍的；使人痛苦的
cottage	ˈkɒtɪdʒ	n. 村舍；小屋；（农舍式的）小别墅
loyal	ˈlɔɪəl	adj. 忠诚的，忠心的；忠贞的
clay	kleɪ	n. 泥土；粘土；肉体；似黏土的东西
radar	ˈreɪdɑː(r)	n. 雷达，无线电探测器
frown	fraʊn	vi. 皱眉；不同意
sweater	ˈswetə(r)	n. 毛线衣，运动衫；大量出汗的人，发汗剂
rubber	ˈrʌbə(r)	n. 橡胶；橡皮；合成橡胶；按摩师
retreat	rɪˈtriːt	n. 撤退；休息寓所；撤退
molecule	ˈmɒlɪkjuːl	n. 分子；微小颗粒，微粒
freely	ˈfriːli	adv. 自由地；慷慨地；直率地；免费地；大量地
steadily	ˈstedəli	adv. 稳定地；稳固地；有规则地
fatigue	fəˈtiːɡ	n. 疲劳，疲乏；杂役
bucket	ˈbʌkɪt	n. 铲斗；桶，水桶；一桶的量
marble	ˈmɑːb(ə)l	n. 大理石；大理石制品；弹珠
magnetic	mæɡˈnetɪk	adj. 有吸引力的；有磁性的；地磁的
technician	tekˈnɪʃ(ə)n	n. 技师，技术员；技巧纯熟的人
mutter		vi. 咕哝；喃喃自语
arrow		n. 箭状物；箭头记号；箭，箭头
soak		vt. 吸收，吸入；使 …… 上下湿透；沉浸在（工作或学习中）
ribbon	ˈrɪbən	n. 缎带；带状物；带；（勋章等的）绶带； [ 军 ] 勋表
transmit	trænzˈmɪt	vt. 传达；遗传；传输；传播；发射
harvest	ˈhɑːvɪst	n. 收获；产量；结果
coordinate	kəʊˈɔːdɪneɪt	n. 坐标；同等的人或物
severely	sɪˈvɪəli	adv. 严格地，严厉地；严重地；纯朴地
ridge		n. 山脉；山脊；屋脊
brave	breɪv	adj. 勇敢的；华丽的
specify	ˈspesɪfaɪ	vt. 指定；列举；详细说明；把 … 列入说明书
dense		adj. 浓厚的；稠密的；愚钝的
electronics	ɪˌlekˈtrɒnɪks	n. 电子学；电子工业
sunny	ˈsʌni	adj. 阳光充足的，和煦的；快活的；性情开朗的
swell	swel	vi. 膨胀； [ 病理 ] 肿胀；隆起
hazard	ˈhæzəd	vt. 赌运气；冒 … 的危险，使遭受危险
seldom	ˈseldəm	adv. 很少，不常
twentieth	ˈtwentiəθ	n. 二十分之一
convenience	kənˈviːniəns	n. 便利；便利的事物；厕所
weave	wiːv	vt. 编织；编排；使迂回前进
timber		n. 木料；木材
bulb		n. 电灯泡；球状物； [ 植 ] 鳞茎
devil	ˈdev(ə)l	n. 撒旦；魔鬼；恶棍；家伙
bolt	bəʊlt	n. 闪电；螺栓；门闩；弩箭
cargo	ˈkɑːɡəʊ	n. 货物，船货
seller	ˈselə(r)	n. 卖方，售货员
skilled	skɪld	adj. 熟练的；有技能的；需要技能的
marine	məˈriːn	adj. 海生的；航海的，海运的；船舶的；海产的
dock	dɒk	n. 船坞；码头；被告席；尾巴的骨肉部分
fog	fɒɡ	n. 雾；烟雾，尘雾；迷惑
sometime	ˈsʌmtaɪm	adv. 来日；改天；在某一时候
summary	ˈsʌməri	adj. 扼要的；简易的
fatal	ˈfeɪt(ə)l	adj. 致命的；重大的；毁灭性的；命中注定的
trim		vt. 修剪；整理；装点
accelerate	əkˈseləreɪt	vt. 使 …… 加快；使 …… 增速
butterfly	ˈbʌtəflaɪ	n. 蝴蝶；蝶泳；举止轻浮的人；追求享乐的人
bronze	brɒnz	n. 青铜；青铜制品；古铜色
drown	draʊn	vt. 淹没；把 … 淹死
spit	spɪt	vi. 吐口水；吐痰；发出劈啪声
kneel		vi. 跪下，跪
vacuum	ˈvækjuːm	n. 真空；真空吸尘器；空间
dictate	dɪkˈteɪt	vt. 命令；口述；使听写
laundry	ˈlɔːndri	n. 洗衣店，洗衣房；洗熨；洗好的衣服；要洗的衣服
        """,
        """
manual	ˈmænjuəl	adj. 体力的；手工的
pistol		n. 手枪；信号枪
naval	ˈneɪv(ə)l	adj. 海军的；军舰的
apology	əˈpɒlədʒi	n. 道歉；谢罪；辩护；勉强的替代物
bore	bɔː(r)	vi. 钻孔
beloved	bɪˈlʌvɪd	adj. 心爱的；挚爱的
entertain	ˌentəˈteɪn	vt. 招待；娱乐；怀抱；容纳
devise	dɪˈvaɪz	vt. 设计；发明；想出；图谋；遗赠给
bargain	ˈbɑːɡən	n. 交易；契约；特价商品
brass	brɑːs	n. 黄铜；黄铜制品；厚脸皮；铜管乐器
anchor	ˈæŋkə(r)	n. 锚；靠山；新闻节目主播；抛锚停泊
zero	ˈzɪərəʊ	n. 零点，零度
intensive	ɪnˈtensɪv	adj. 集中的；加强的；透彻的； [ 语法 ] 加强语气的
betray	bɪˈtreɪ	vt. 背叛；出卖；泄露（秘密）；露出 … 迹象
delicious	dɪˈlɪʃəs	adj. 美味的；可口的
aboard	əˈbɔːd	adv. 在火车上；在飞机上；在船上
reproduce		vt. 复制；再生；生殖；使 … 在脑海中重现
worm	wɜːm	n. 虫，蠕虫；蜗杆；螺纹；小人物
beneficial	ˌbenɪˈfɪʃ(ə)l	adj. 有益的，有利的；可享利益的
awkward	ˈɔːkwəd	adj. 笨拙的；尴尬的；棘手的；不合适的
faithful	ˈfeɪθf(ə)l	adj. 忠实的，忠诚的；如实的；准确可靠的
crude	kruːd	adj. 粗糙的；粗鲁的；天然的，未加工的
vague		adj. 不明确的；含糊的；模糊的；暧昧的
scar	skɑː(r)	vt. 给留下伤痕；伤害
surrender		vt. 放弃；交出；使投降；听任
ray	reɪ	n. 射线；光线
sketch	sketʃ	n. 素描；略图；梗概
clarify	ˈklærəfaɪ	vt. 澄清；阐明
ninth	naɪnθ	num. 第九
pupil	ˈpjuːp(ə)l	n. 学生；瞳孔；未成年人
neglect	nɪˈɡlekt	vt. 疏忽，忽视；忽略
bang	bæŋ	n. 重击；刘海；突然巨响
knot	nɒt	n. （绳等的）结；海里 / 小时（航速单位）；节瘤，疙瘩
dragon	ˈdræɡən	n. 龙；凶暴的人，凶恶的人；严厉而有警觉性的女人
prosperity	prɒˈsperəti	n. 繁荣，成功
accumulate	əˈkjuːmjəleɪt	vi. 累积；积聚
rug	rʌɡ	n. 小地毯；毛皮地毯； [ 美俚 ] 男子假发
raid	reɪd	n. 袭击；突袭；搜捕；抢劫
glow	ɡləʊ	vi. 发热；洋溢；绚丽夺目
crown	kraʊn	n. 王冠；王权；花冠；顶点
illustration	ˌɪləˈstreɪʃ(ə)n	n. 插图；说明；例证；图解
worthy	ˈwɜːði	adj. 有价值的；值得的；可尊敬的；应 … 的；配得上的，相称的
Hydrogen	ˈhaɪdrədʒən	n. 氢
probability	ˌprɒbəˈbɪləti	n. 机率；可能性；或然率
vinegar	ˈvɪnɪɡə(r)	n. 醋
remedy	ˈremədi	vt. 补救；治疗；纠正
hopeful	ˈhəʊpf(ə)l	adj. 有希望的；有前途的
fancy	ˈfænsi	n. 想像力；爱好；幻想
dive	daɪv	vi. 跳水；潜水；俯冲；急剧下降
purely	ˈpjʊəli	adv. 纯粹地；贞淑地；清洁地；完全地；仅仅，只不过
soda	ˈsəʊdə	n. 苏打；碳酸水
manufacture	ˌmænjuˈfæktʃə(r)	n. 制造；制造业；产品
affection	əˈfekʃn	n. 喜爱，感情；影响；感染
mature	məˈtʃʊə(r)	adj. 成熟的；到期的；成年人的；充分考虑的
philosopher	fəˈlɒsəfə(r)	n. 哲学家；哲人
conceal	kənˈsiːl	vt. 隐藏；隐瞒
curl	kɜːl	vt. 使 … 卷曲；使卷起来
goodness	ˈɡʊdnəs	n. 善良，仁慈；真谛；优秀
geography	dʒiˈɒɡrəfi	n. 地理；地形
leak	liːk	n. 漏洞，裂缝；泄漏
herd	hɜːd	n. 兽群，畜群；放牧人
greenhouse	ˈɡriːnhaʊs	n. 温室
pipeline		n. 管道；传递途径；输油管
atom	ˈætəm	n. 原子
ending		n. 结局；结尾
charming	ˈtʃɑːmɪŋ	adj. 迷人的；可爱的
supplement	ˈsʌplɪmənt	n. 补充，补遗；补充物；附录
ink	ɪŋk	vt. 涂墨水于；签署
distress	dɪˈstres	n. 危难，不幸；贫困；悲痛
memorial	məˈmɔːriəl	n. 纪念物；纪念碑，纪念馆；纪念仪式
infect	ɪnˈfekt	vt. 感染，传染
backward	ˈbækwəd	adj. 向后的；反向的；发展迟缓的
offend	əˈfend	vt. 冒犯；使 … 不愉快
bleed	bliːd	vt. 使出血；榨取
choke		vt. 使窒息；呛；阻塞；扑灭；抑制
saint	seɪnt	n. 圣徒；圣人；道德崇高的人
tremble	ˈtremb(ə)l	vi. 战栗；焦虑；发抖；摇晃
rotation	rəʊˈteɪʃ(ə)n	n. 旋转；循环，轮流
prejudice	ˈpredʒədɪs	n. 偏见；侵害
certificate	səˈtɪfɪkət	vt. 发给证明书；以证书形式授权给 … ；用证书批准
        """,
        """
robe	rəʊb	n. 长袍，礼服；制服
mercy		n. 仁慈，宽容；怜悯；幸运；善行
classmate	ˈklɑːsmeɪt	n. 同班同学
acute		adj. 严重的，急性的；激烈的；敏锐的；尖声的
hatred	ˈheɪtrɪd	n. 憎恨；怨恨；敌意
diameter	daɪˈæmɪtə(r)	n.直径
gown		n. 长袍，长外衣；法衣；睡袍；礼服
halt	hɔːlt	vi. 停止；踌躇，犹豫；立定
roar	rɔː(r)	n. 吼；咆哮；轰鸣
torture	ˈtɔːtʃə(r)	vt. 折磨；拷问；歪曲
revenge	rɪˈvendʒ	n. 复仇；报复
compile	kəmˈpaɪl	vt. 编译；编辑；汇编；编制
commerce	ˈkɒmɜːs	n. 贸易，商业
desirable	dɪˈzaɪərəb(ə)l	adj. 值得要的；令人满意的
attain	əˈteɪn	vt.达到,获得v.达到
hut	hʌt	n. 小屋；临时营房
sixteen	ˌsɪksˈtiːn	adj. 十六的，十六个的
pronounce	prəˈnaʊns	vt. 发音；断言；宣判
banner		n. 旗帜，横幅；标语
underground	ˌʌndəˈɡraʊnd	adv. 在地下；秘密地
orchestra		n. 管弦乐队；乐队演奏处
drought	draʊt	n. 干旱；缺乏
convenient	kənˈviːniənt	adj. 方便的
accommodation	əˌkɒməˈdeɪʃ(ə)n	n. 调节；住处，膳宿；和解；预订铺位
frog	frɒɡ	n. 青蛙；饰扣；辙叉
grind	ɡraɪnd	vt. 磨碎；磨快
prescribe	prɪˈskraɪb	vi. 规定；开药方
penetrate	ˈpenətreɪt	vt. 渗透；穿透；洞察
canal		n. 运河；水道；灌溉水渠；管道
unnecessary	ʌnˈnesɪsəri	adj. 不必要的；多余的，无用的
rehearsal		n. 排演；预演；练习；叙述；训练
discard	dɪˈskɑːd	vt. 丢弃；抛弃；放弃
tub	tʌb	n. 桶；浴盆
restraint	rɪˈstreɪnt	n. 抑制，克制；约束
correspond	ˌkɒrəˈspɒnd	vi. 符合，一致；相应；通信
theirs	ðeəz	pron. 他们的；她们的；它们的
murderer	ˈmɜːdərə(r)	n. 凶手；谋杀犯
duration		n. 持续
lime	laɪm	n. 石灰；酸橙；绿黄色
atomic	əˈtɒmɪk	adj. 原子的，原子能的；微粒子的
portable		adj. 轻便的；手提的，便携式的
strawberry	ˈstrɔːbəri	n. 草莓；草莓色
moisture	ˈmɔɪstʃə(r)	n. 潮湿；水分；降雨量；湿度
contradiction		n. 矛盾；反驳；否认
widen	ˈwaɪd(ə)n	vt. 放宽
startle		vt. 使吓一跳；使惊奇
prince	prɪns	n. 王子，国君；亲王；贵族
separately	ˈseprətli	adv. 分离地；分别地；个别地
precision	prɪˈsɪʒ(ə)n	n. 精确；精度，精密度
rigid	ˈrɪdʒɪd	adj. 严格的；僵硬的，死板的；坚硬的；精确的
semester		n. 学期；半年
tumble	ˈtʌmb(ə)l	vi. 倒塌；滚动；摔倒；打滚；仓惶地行动
eighteen	ˌeɪˈtiːn	n. 十八，十八个
rotate	rəʊˈteɪt	vi. 旋转；循环
magnificent	mæɡˈnɪfɪs(ə)nt	adj. 壮丽的；华丽的；高尚的；宏伟的
polite	pəˈlaɪt	adj. 有礼貌的，客气的；文雅的；上流的；优雅的
contrary	ˈkɒntrəri	adj. 相反的；对立的
scrape		n. 刮掉；擦痕；困境；刮擦声
optical	ˈɒptɪk(ə)l	adj. 光学的；眼睛的，视觉的
fourteen	ˌfɔːˈtiːn	num. 十四
ambulance	ˈæmbjələns	n. 救护车；战时流动医院
sadness	ˈsædnəs	n. 悲哀
enthusiastic	ɪnˌθjuːziˈæstɪk	adj. 热心的；热情的；狂热的
namely		adv. 即是；也就是；换句话说
renew	rɪˈnjuː	vt. 使更新；复兴；重申；续借
overhead	ˌəʊvəˈhed	adv. 在头顶上；在空中；在高处
goose	ɡuːs	n. 鹅；雌鹅；鹅肉；傻瓜
vivid	ˈvɪvɪd	adj. 鲜明的；鲜艳的；生动的
furious	ˈfjʊəriəs	adj. 狂怒的；激烈的；热烈兴奋的；喧闹的
poison	ˈpɔɪz(ə)n	vt. 使中毒，放毒于；败坏；污染；阻碍
ruler	ˈruːlə(r)	n. 尺；划线板，划线的人；统治者
undoubtedly		adv. 确实地，无庸置疑地
tenant	ˈtenənt	n. 房客；承租人；佃户；居住者
omit	əˈmɪt	vt. 省略；遗漏；删除；疏忽
spider	ˈspaɪdə(r)	n. 蜘蛛；设圈套者；三脚架
damp	dæmp	vt. 使潮湿；使沮丧，抑制；使阻尼
certainty	ˈsɜːt(ə)nti	n. 确实；必然；确实的事情
electron	ɪˈlektrɒn	n. 电子
physicist	ˈfɪzɪsɪst	n. 物理学家；唯物论者
salesman		n. 推销员；售货员
        """,
        """
cultivate		vt. 培养；耕作；陶冶
cherry	ˈtʃeri	n. 樱桃；樱桃树；如樱桃的鲜红色； [ 俚，鄙 ] 处女膜，处女
luxury	ˈlʌkʃəri	n. 奢侈，奢华；奢侈品；享受
summarize	ˈsʌməraɪz	vt. 总结；概述
meadow	ˈmedəʊ	n. 草地；牧场
disposal	dɪˈspəʊz(ə)l	n. 清理；处理；支配；安排
accordingly	əˈkɔːdɪŋli	adv. 因此，于是；相应地；照著
plug	plʌɡ	n. 栓；插头；塞子
socialist	ˈsəʊʃəlɪst	n. 社会主义者；社会党党员
tempt	tempt	vt. 诱惑；引起；冒 … 的风险；使感兴趣
axis	ˈæksɪs	n. 轴；轴线；轴心国
academy	əˈkædəmi	n. 学会；学院；研究院；专科院校
shield	ʃiːld	n. 盾；防护物；保护者
triangle	ˈtraɪæŋɡ(ə)l	n. 三角（形）；三角形之物；三人一组；三角关系
substitute		n. 代替者；代用品
textile		n. 纺织品，织物
collision	kəˈlɪʒ(ə)n	n. 冲突；碰撞
verify	ˈverɪfaɪ	vt. 核实；查证
nursery	ˈnɜːsəri	n. 托儿所；苗圃；温床
sixty	ˈsɪksti	num. 六十；六十个
thirteen	ˌθɜːˈtiːn	n. 十三；十三个；十三岁
crust	krʌst	n. 面包皮；外壳；坚硬外皮
useless	ˈjuːsləs	adj. 无用的；无效的
unconscious	ʌnˈkɒnʃəs	adj. 失去知觉的；不省人事的；未发觉的；无意识的
mosquito	məˈskiːtəʊ	n. 蚊子
vine		n. [ 植 ] 葡萄树； [ 植 ] 藤本植物； [ 植 ] 攀缘植物； [ 植 ] 藤
thoughtful	ˈθɔːtf(ə)l	adj. 深思的；体贴的；关切的
permanently	ˈpɜːmənəntli	adv. 永久地，长期不变地
optional		adj. 可选择的，随意的
miner	ˈmaɪnə(r)	n. 矿工；开矿机
aloud	əˈlaʊd	adv. 大声地；出声地
breed	briːd	vi. 繁殖；饲养；产生
delegation		n. 代表团；委托；授权
weep	wiːp	vi. 哭泣；流泪；哀悼；滴落；渗出液体
span	spæn	n. 跨度，跨距；范围
cube	kjuːb	n. 立方体；立方；骰子
Swiss		adj. 瑞士的；瑞士人的；瑞士风格的
underneath	ˌʌndəˈniːθ	prep. 在…的下面；在…的形式下；在…的支配下
injection	ɪnˈdʒekʃ(ə)n	n. 注射；注射剂；充血；射入轨道
plantation	plɑːnˈteɪʃ(ə)n	n. 大农场；栽植；殖民
dim	dɪm	adj. 模糊的，看不清的；暗淡的，昏暗的；悲观的，怀疑的
sunset	ˈsʌnset	n. 日落，傍晚
excess	ɪkˈses	n. 无节制；过度，过量；超过，超额
gardener	ˈɡɑːdnə(r)	n. 花匠；园丁；园艺家
curse	kɜːs	n. 咒骂；诅咒
resignation		n. 辞职；放弃；顺从；辞职书
deed		n. 行动；证书； [ 法 ] 契据
miserable	ˈmɪzrəbl	adj. 悲惨的；痛苦的；卑鄙的
lively	ˈlaɪvli	adj. 活泼的；生动的；真实的；生气勃勃的
institute	ˈɪnstɪtjuːt	vt. 制定；创立；开始（调查）；提起（诉讼）
terminal	ˈtɜːmɪn(ə)l	n. 终端机；终点；末端；极限
competent		adj. 能干的；足够的；胜任的；有能力的
primitive	ˈprɪmətɪv	adj. 原始的，远古的；简单的，粗糙的
brow	braʊ	n. 眉，眉毛；表情；额
harmful	ˈhɑːmf(ə)l	adj. 能造成损害的；有害的
worship	ˈwɜːʃɪp	n. 礼拜；尊敬；崇拜
repetition	ˌrepəˈtɪʃ(ə)n	n. 重复；副本；背诵
tuition		n. 学费；讲授
revise		vt. 校订；修正；复习
generator	ˈdʒenəreɪtə(r)	n. 发电机；发生器；生产者
stab	stæb	vt. 刺；刺穿；刺伤；戳；直入
cafe	ˈkæfeɪ	n. 咖啡馆；小餐馆
castle	ˈkɑːs(ə)l	n. 城堡；象棋中的车
parallel	ˈpærəlel	n. 平行线；对比
fulfil	fʊlˈfɪl	vt. 履行；实践；满足；完成
thorough	ˈθʌrə	adj. 彻底的；十分的；周密的
specimen	ˈspesɪmən	n. 标本；样品，样本
thrill	θrɪl	n. 激动；震颤；紧张
outlook	ˈaʊtlʊk	n. 观点；景色；展望
nitrogen		n. [ 化 ] 氮
transparent	trænsˈpærənt	adj. 透明的；显然的；易懂的；坦率的
respective	rɪˈspektɪv	adj. 分别的，各自的
circulate	ˈsɜːkjəleɪt	vi. 循环；流通；传播，流传
forecast	ˈfɔːkɑːst	vt. 预报，预测；预示
classification	ˌklæsɪfɪˈkeɪʃ(ə)n	n. 分类；类别，等级
paragraph	ˈpærəɡrɑːf	n. 段落；短评；段落符号
thrust	θrʌst	n. 推力；刺
feast	fiːst	vt. 款待，宴请；享受
inhabitant	ɪnˈhæbɪtənt	n. 居民；居住者
sway	sweɪ	vt. 使摇动；统治；影响
        """,
        """
refusal	rɪˈfjuːz(ə)l	n. 拒绝；优先取舍权；推却；取舍权
flush	flʌʃ	n. 激动，洋溢；面红；萌芽；旺盛；奔流
lick		vt. 舔；卷过；鞭打
sufficiently	səˈfɪʃntli	adv. 充分地；足够地
defect	ˈdiːfekt	n. 缺点，缺陷；不足之处
owl	aʊl	n. [ 动 ] 猫头鹰；枭；惯于晚上活动的人
static	ˈstætɪk	adj. 静态的；静力的； [ 物 ] 静电的
mist	mɪst	n. 薄雾；视线模糊不清；模糊不清之物
correction	kəˈrekʃn	n. 改正，修正
nonsense	ˈnɒns(ə)ns	n. 胡说；废话
gum	ɡʌm	n. 树胶；橡皮；口香糖
complicate	ˈkɒmplɪkeɪt	vt. 使恶化；使复杂化；使卷入
stool		n. 凳子；粪便；厕所
horizontal	ˌhɒrɪˈzɒnt(ə)l	adj. 水平的；地平线的；同一阶层的
daylight	ˈdeɪlaɪt	n. 日光；白天；黎明；公开
bundle	ˈbʌnd(ə)l	n. 捆；束
linen	ˈlɪnɪn	n. 亚麻布，亚麻线；亚麻制品
robbery	ˈrɒbəri	n. 抢劫，盗窃；抢掠
drip	drɪp	vi. 滴下；充满；漏下
loosen		vt. 放松；松开
hedge		vt. 用树篱笆围住；避免作正面答复
heating		n. 加热；供暖；暖气设备
appliance		n. 器具；器械；装置
maid	meɪd	n. 女仆；少女
gauge		n. 计量器；标准尺寸；容量规格
inhabit	ɪnˈhæbɪt	vt. 居住于；占据；栖息
lieutenant		n. 中尉；助理人员；副官
slogan	ˈsləʊɡən	n. 标语；呐喊声
accord	əˈkɔːd	n. 一致；符合；协议；自愿
jewel	ˈdʒuːəl	n. 宝石；珠宝
viewpoint	ˈvjuːpɔɪnt	n. 观点，看法；视角
dial	ˈdaɪəl	n. 钟面；转盘；刻度盘
avenue	ˈævənjuː	n. 林荫大道；大街
berry	ˈberi	n. 浆果（葡萄，番茄等）
mug	mʌɡ	n. 脸；杯子；苦读者
exert	ɪɡˈzɜːt	vt. 运用，发挥；施以影响
quilt		n. 棉被；被子
sensible	ˈsensəb(ə)l	adj. 明智的；意识到的；通晓事理的；明显的
operational	ˌɒpəˈreɪʃən(ə)l	adj. 操作的；运作的
scarf	skɑːf	n. 嵌接，嵌接处；头巾领巾；围巾
lump	lʌmp	n. 块，块状；肿块；瘤；很多；笨人
imitate	ˈɪmɪteɪt	vt. 模仿，仿效；仿造，仿制
multiply	ˈmʌltɪplaɪ	vt. 乘；使相乘；使增加；使繁殖
upward	ˈʌpwəd	adj. 向上的；上升的
cough	kɒf	n. 咳嗽，咳嗽声；咳嗽病
velvet	ˈvelvɪt	n. 天鹅绒，丝绒；天鹅绒似的东西
roller		n. 滚筒；滚轴；辊子；滚转机
toast	təʊst	n. 烤面包；吐司；干杯
basin	ˈbeɪs(ə)n	n. 盆地；盆；水池；流域
heroic		adj. 英勇的；英雄的；记叙英雄及其事迹的；夸张的
heir	eə(r)	n. 继承人；后嗣；嗣子
tomb	tuːm	n. 坟墓；死亡
insure	ɪnˈʃʊə(r)	vt. 确保，保证；给 … 保险
sausage	ˈsɒsɪdʒ	n. 香肠；腊肠；装香肠的碎肉
glorious	ˈɡlɔːriəs	adj. 光荣的；辉煌的；极好的
fearful	ˈfɪəf(ə)l	adj. 可怕的；严重的；担心的
shiver	ˈʃɪvə(r)	n. 颤抖，战栗；碎片
flourish	ˈflʌrɪʃ	n. 兴旺；茂盛；挥舞；炫耀；华饰
discharge	dɪsˈtʃɑːdʒ	vt. 卸下；放出；解雇；免除
mainland		n. 大陆；本土
standpoint	ˈstændpɔɪnt	n. 立场；观点
turning	ˈtɜːnɪŋ	n. 旋转；转向；回转；转弯处
detection	dɪˈtekʃ(ə)n	n. 察觉；侦查，探测；发觉，发现
occurrence	əˈkʌrəns	n. 事件；发生；出现；发现
saddle		n. 鞍，鞍状物；车座；拖具
liberation	ˌlɪbəˈreɪʃ(ə)n	n. 释放，解放
abundant		adj. 充裕的；丰富的；盛产
unwilling	ʌnˈwɪlɪŋ	adj. 不愿意的；勉强的；不情愿的
packet	ˈpækɪt	n. 数据包，信息包；小包，小捆
vigorous	ˈvɪɡərəs	adj. 有力的；精力充沛的
fond		adj. 喜欢的；温柔的；宠爱的
photographic	ˌfəʊtəˈɡræfɪk	adj. 摄影的；逼真的；（尤指记忆）详细准确的
eagle	ˈiːɡ(ə)l	n. 鹰；鹰状标饰
sandy	ˈsændi	adj. 含沙的；多沙的；沙地的
tense	tens	adj. 拉紧的；紧张的
leisure	ˈleʒə(r)	n. 闲暇；空闲；安逸
emit	iˈmɪt	vt. 发出(give off)，放射；发行；发表
unusually	ʌnˈjuːʒəli	adv. 异乎寻常地；非常；显著地
acquaintance	əˈkweɪntəns	n. 熟人；了解；知道；相识
comparative	kəmˈpærətɪv	adj. 相当的；比较的
        """,
        """
magnet	ˈmæɡnət	n. 磁铁；磁石；磁体
contempt	kənˈtempt	n. 轻视，蔑视（hold ... in contempt）；耻辱
zoo	zuː	n. 动物园
disadvantage	ˌdɪsədˈvɑːntɪdʒ	n. 不利条件；缺点；损失
stripe	straɪp	n. 条纹，斑纹；种类
foremost		adj. 最先的；最重要的
sorrow	ˈsɒrəʊ	n. 悲伤；伤心事；懊悔
exaggerate		vt. 使增大；使扩大
seventeen	ˌsev(ə)nˈtiːn	n. 十七，十七个
greeting	ˈɡriːtɪŋ	n. 问候，招呼；祝贺
jealous	ˈdʒeləs	adj. 妒忌的；唯恐失去的；猜疑的；戒备的
swamp	swɒmp	n. 沼泽；湿地
rebellion	rɪˈbeljən	n. 叛乱；反抗；谋反；不服从
dash	dæʃ	n. 破折号；冲撞
Communist	ˈkɒmjənɪst	n. 共产主义者；共产党员
sore	sɔː(r)	adj. 剧烈的，极度的；疼痛的，痛心的；恼火的，发怒的；厉害的，迫切的
usage	ˈjuːsɪdʒ	n. 用法；使用；惯例
nephew	ˈnefjuː	n. 侄子；外甥
glare		n. 刺眼；耀眼的光；受公众注目
fertilizer	ˈfɜːtəlaɪzə(r)	n. 肥料；受精媒介物；促进发展者
altitude	ˈæltɪtjuːd	n. 高度；高地； [ 数 ] 顶垂线；（等级和地位等的）高级
handy	ˈhændi	adj. 手边的，就近的；便利的；容易取得的；敏捷的
succession	səkˈseʃ(ə)n	n. 连续；继位；继承权； [ 农业 ] 轮栽
cement		vt. 巩固，加强；用水泥涂；接合
recite		vt. 背诵；叙述；列举
interference	ˌɪntəˈfɪərəns	n. 干涉；干扰，冲突
blossom		vi. 开花；兴旺；发展成
accessory	əkˈsesəri	n. 附件；配件；从犯
indirect	ˌɪndəˈrekt	adj. 间接的；迂回的；非直截了当的
spacecraft	ˈspeɪskrɑːft	n. 宇宙飞船，航天器
spur		n. 马刺；鼓舞，刺激；山坡
whistle		n. 口哨；啸啸声；汽笛
sew	səʊ	vt. 缝合，缝上；缝纫
unpleasant	ʌnˈplez(ə)nt	adj. 使人不愉快的；讨厌的
muddy	ˈmʌdi	adj. 泥泞的；模糊的；混乱的
imaginary	ɪˈmædʒɪnəri	adj. 虚构的，假想的；想像的；虚数的
mathematical	ˌmæθəˈmætɪk(ə)l	adj. 数学的，数学上的；精确的
pillar		n. 柱子，柱形物；栋梁；墩
receipt	rɪˈsiːt	n. 收到；收据；收入
uneasy		adj. 不舒服的；心神不安的；不稳定的
carriage	ˈkærɪdʒ	n. 四轮马车；客车厢；运费；运输；举止
messenger	ˈmesɪndʒə(r)	n. 报信者，送信者；先驱
moist	mɔɪst	adj. 潮湿的；多雨的；含泪的
tractor	ˈtræktə(r)	n. 拖拉机；牵引机
postpone	pəˈspəʊn	vt. 使 … 延期；把 … 放在次要地位；把 … 放在后面
helpless	ˈhelpləs	adj. 无助的；没用的；无能的
velocity		n. 速率；迅速；周转率
noisy	ˈnɔɪzi	adj. 嘈杂的；喧闹的；聒噪的
insult	ɪnˈsʌlt; ˈɪnsʌlt	vt. 侮辱；辱骂；损害
spontaneous		adj. 自发的；自然的；无意识的
bloom	bluːm	n. 花；青春；旺盛
slender		adj. 微薄的；苗条的；细长的
necklace	ˈnekləs	n. 项链
perfume	ˈpɜːfjuːm	n. 香水；香味
flock	flɒk	n. 群；棉束（等于 floc ）
refine	rɪˈfaɪn	vt. 精炼，提纯；使 … 文雅；改善
canoe	kəˈnuː	n. 独木舟；轻舟
swift	swɪft	n. 褐雨燕
undo	ʌnˈduː	vt. 解开；取消；破坏；扰乱
lace		n. 花边；鞋带；饰带；少量烈酒
lord	lɔːd	n. 上帝；主、对贵族（noble / aristocrat） 的尊称
violin	ˌvaɪəˈlɪn	n. 小提琴；小提琴手
resistant	rɪˈzɪstənt	adj. 抵抗的，反抗的；顽固的
rag	ræɡ	n. 破布；碎屑
liar	ˈlaɪə(r)	n. 说谎的人
vest		n. 背心；汗衫
oblige	əˈblaɪdʒ	vt. 强制；赐，施恩惠；迫使；责成
splash		vt. 溅，泼；用 ... 使液体飞溅
stain	steɪn	vt. 沾污；败坏；给 … 着色
clap	klæp	vi. 鼓掌，拍手；啪地关上
deepen	ˈdiːpən	vt. 使加深；使强烈；使低沉
conquer	ˈkɒŋkə(r)	vt. 战胜，征服；攻克，攻取
width	wɪdθ	n. 宽度；广度
fetch	fetʃ	vt. 接来；到达；取来；吸引
circular	ˈsɜːkjələ(r)	adj. 圆形的；循环的；间接的
weary	ˈwɪəri	adj. 疲倦的；厌烦的；令人厌烦的
vacant		adj. 空的；空闲的；空缺的；空虚的；茫然的
deaf	def	adj. 聋的
hillside		n. 山坡，山腹；山腰
mourn	mɔːn	v. 哀悼；服丧；忧伤
        """,
        """
scarce	skeəs	adj. 缺乏的，不足的；稀有的
probable	ˈprɒbəb(ə)l	adj. 很可能的；可信的
definite	ˈdefɪnət	adj. 确切的；一定的
gradual	ˈɡrædʒuəl	adj. 逐渐的；平缓的
nowadays	ˈnaʊədeɪz	adv. 现今；时下
navy	ˈneɪvi	n. 海军
dislike	dɪsˈlaɪk	vt. 不喜欢，厌恶
synthetic		adj. 合成的，人造的；综合的
paradise	ˈpærədaɪs	n. 天堂、伊甸园
restrain	rɪˈstreɪn	vt. 抑制，控制；制止；约束
wholly	ˈhəʊlli	adv. 完全地；全部；统统
lodge	lɒdʒ	n. 门房；旅馆；集会处；山林小屋
pierce	pɪəs	vt. 刺穿；洞察；响彻；深深地打动
atmospheric		adj. 大气的，大气层的
infinite	ˈɪnfɪnət	adj. 无限的，无穷的；无数的；极大的
intermediate	ˌɪntəˈmiːdiət	vi. 起媒介作用
sincere	sɪnˈsɪə(r)	adj. 真诚的；诚挚的；真实的
pluck	plʌk	n. 勇气；内脏；快而猛的拉
utter	ˈʌtə(r)	vt. 发出，表达；发射
submarine	ˌsʌbməˈriːn	n. 潜水艇；海底生物
ditch		vt. 在 … 上掘沟；把 … 开入沟里； [ 俚 ] 丢弃
secondly		adv. 其次；第二
dispose	dɪˈspəʊz	vt. 处理；处置；安排
princess	ˌprɪnˈses	n. 王妃；公主；女巨头
squirrel	ˈskwɪrəl	n. 松鼠；松鼠毛皮
hardship	ˈhɑːdʃɪp	n. 困苦；苦难；艰难险阻
claw	klɔː	n. 爪；螯，钳；爪形器具
marvelous		adj.引起惊异的,不可思议的,非凡的
reliance	rɪˈlaɪəns	n. 信心；信赖；受信赖的人或物
camel	ˈkæm(ə)l	n. 骆驼；打捞浮筒；工作作风官僚
nucleus		n. 核，核心；原子核
worthwhile	ˌwɜːθˈwaɪl	adj. 值得做的，值得花时间的
bud	bʌd	n. 芽，萌芽；蓓蕾
apparatus	ˌæpəˈreɪtəs	n. 装置，设备；器官；仪器
withstand		vt. 抵挡；反抗；禁得起
scarcely	ˈskeəsli	adv. 简直没有；几乎不，简直不
lumber	ˈlʌmbə(r)	vi. 伐木；喧闹地向前走；笨重地行动，缓慢地移动
earnest		adj. 认真的，热心的；重要的
simplicity	sɪmˈplɪsəti	n. 朴素；简易；天真；愚蠢
staircase	ˈsteəkeɪs	n. 楼梯
emperor	ˈempərə(r)	n. 皇帝，君主
cafeteria	ˌkæfəˈtɪəriə	n. 自助餐厅
heap	hiːp	n. 累积；堆；许多
glide	ɡlaɪd	n. 滑翔；滑行；滑移；滑音
pave		vt. 铺设；安排；作铺设之用
communism	ˈkɒmjənɪzəm	n. 共产主义
preceding	prɪˈsiːdɪŋ	adj. 在前的；前述的
fry	fraɪ	n. 鱼苗；油炸食物
unstable		adj. 不稳定的；动荡的；易变的
scout		n. 侦察机；侦察员；搜索，侦察
insufficient	ˌɪnsəˈfɪʃ(ə)nt	adj. 不足的，不充足的
arouse	əˈraʊz	vt. 引起；鼓励；唤醒
groan	ɡrəʊn	vi. 呻吟；抱怨；发吱嘎声
hay	heɪ	n. 干草
mistress		n. 情妇；女主人；主妇；女教师；女能人
pearl		n. 珍珠；杰出者；珍珠色；珍品
dusk		n. 黄昏，薄暮；幽暗，昏暗
wax	wæks	n. 蜡；蜡状物
sob		vi. 啜泣，呜咽；（风等）发出呜咽声
socialism	ˈsəʊʃəlɪz(ə)m	n. 社会主义
graceful	ˈɡreɪsf(ə)l	adj. 优美的；优雅的
puppet		n. 傀儡；木偶；受他人操纵的人
throne		n. 王座；君主；王权
mankind	mænˈkaɪnd	n. 人类；男性
incorrect	ˌɪnkəˈrekt	adj. 错误的，不正确的；不真实的；不适当的
turbine	ˈtɜːbaɪn	n. 涡轮；涡轮机
baggage	ˈbæɡɪdʒ	n. 辎重（军队的）；行李 (不可数)
veil		n. 面纱；面罩；遮蔽物；托词
restless	ˈrestləs	adj. 焦躁不安的；不安宁的；得不到满足的
sunrise	ˈsʌnraɪz	n. 日出；黎明
cheerful	ˈtʃɪəf(ə)l	adj. 高兴的；快乐的；愉快的
diagram	ˈdaɪəɡræm	n. 图表；图解
comb	kəʊm	n. 鸡冠；梳子；蜂巢
luggage	ˈlʌɡɪdʒ	n. (不可数)行李；皮箱
elimination	ɪˌlɪmɪˈneɪʃn	n. 除去；消除；淘汰
friction	ˈfrɪkʃ(ə)n	n. 摩擦，摩擦力
furnish		vt. 供应；提供；装备
prosperous	ˈprɒspərəs	adj. 繁荣的；兴旺的
ignorant	ˈɪɡnərənt	adj. 无知的；愚昧的
tick	tɪk	vt. 标记号于；滴答地记录
        """,
        """
hatch	hætʃ	n. 舱口；孵化
sideways	ˈsaɪdweɪz	adv. 向侧面地；向一旁
cathedral	kəˈθiːdrəl	n. 大教堂
selfish	ˈselfɪʃ	adj. 自私的；利己主义的
keeper	ˈkiːpə(r)	n. 看守人；监护人；管理人；饲养员
sickness	ˈsɪknəs	n. 疾病；呕吐；弊病
hollow	ˈhɒləʊ	adj. 空的；凹的；虚伪的；中空的，空腹的
sober		adj. 冷静的，清醒的；未醉的
sleepy	ˈsliːpi	adj. 困乏的；欲睡的；不活跃的
saw	sɔː	vt. 锯；锯开；锯成
youthful	ˈjuːθfl	adj. 年轻的
comrade	ˈkɒmreɪd	n. 同志；伙伴
merry	ˈmeri	adj. 愉快的；微醉的；嬉戏作乐的
corresponding	ˌkɒrəˈspɒndɪŋ	adj. 通信的；一致的；相当的，相应的
nap	næp	n. 小睡，打盹儿；细毛；孤注一掷
presently	ˈprez(ə)ntli	adv. 不久；（美）目前
starve	stɑːv	vi. 饿死；挨饿；渴望
razor		vt. 剃，用剃刀刮
fertile	ˈfɜːtaɪl	adj. 富饶的，肥沃的；能生育的
disgust	dɪsˈɡʌst	n. 厌恶，嫌恶
fox	fɒks	vt. [ 口 ] 欺骗(as cunning as a fox)；使变酸
rainy	ˈreɪni	adj. 多雨的；下雨的
niece	niːs	n. 外甥女，侄女
layout		n. 布局；设计；安排；陈列
eighty	ˈeɪti	n. 八十；八十岁；八十年代
hamburger	ˈhæmbɜːɡə(r)	n. 汉堡包，汉堡；牛肉饼，肉饼；碎牛肉
upright		adj. 正直的，诚实的；垂直的，直立的；笔直的；合乎正道的
accidental	ˌæksɪˈdent(ə)l	adj. 意外的；偶然的；附属的； [ 音 ] 临时记号的
lever		n. 杠杆；控制杆
liner	ˈlaɪnə(r)	n. 班轮，班机；衬垫；画线者
librarian	laɪˈbreəriən	n. 图书管理员；图书馆员
liberate	ˈlɪbəreɪt	vt. 解放；释放；放出
inferior	ɪnˈfɪəriə(r)	adj. 下级的，下等的；差的；自卑的
orphan	ˈɔːf(ə)n	adj. 孤儿的；无双亲的
inn	ɪn	n. 旅馆；客栈
colonel		n. 陆军上校
reel		vi. 眩晕；卷；蹒跚地走；退缩
enlarge	ɪnˈlɑːdʒ	vi. 扩大；放大；详述
arbitrary		adj. 任意的；武断的；专制的
outdoors	ˌaʊtˈdɔːz	adv. 在户外
conjunction	kənˈdʒʌŋkʃ(ə)n	n.联合,关联,连接词
tenth	tenθ	num. 第十；十分之一
agony	ˈæɡəni	n. 极大的痛苦；苦恼；临死的挣扎
accustom	əˈkʌstəm	vt. 使习惯于
feasible	ˈfiːzəb(ə)l	adj. 可行的；可能的；可实行的
applicable	əˈplɪkəb(ə)l	adj. 可应用的；可适用的；合适的
reap	riːp	vt. 收割；收获，获得
oneself	wʌnˈself	pron. 自己；亲自
paste	peɪst	vt. 张贴，裱糊；用浆糊粘
premier		adj. 第一的；最初的
reign		vi. 统治；盛行；支配；君临
bruise		n. 擦伤；挫伤；青肿
moan		vi. 呻吟；抱怨，悲叹
procession	prəˈseʃ(ə)n	n. 队伍，行列；一列，一排；列队行进
lessen	ˈles(ə)n	vt. 使 … 变小；使 … 减轻；使 … 变少
conquest		n. 征服，战胜；战利品
fashionable	ˈfæʃ(ə)nəb(ə)l	adj. 流行的；时髦的；上流社会的
cellar	ˈselə(r)	n. 地窖；酒窖；地下室
masterpiece	ˈmɑːstəpiːs	n. 杰作；绝无仅有的人
kindness	ˈkaɪndnəs	n. 仁慈；好意；友好的行为
slippery		adj. 滑的；狡猾的；不稳定的
ache	eɪk	vi. 疼痛；渴望
ornament	ˈɔːnəmənt	n. 装饰；装饰物；教堂用品
orderly	ˈɔːdəli	adj. 整齐的；值班的；有秩序的
pinch	pɪntʃ	vt. 捏；勒索；使苦恼；掐掉某物，修剪
cubic	ˈkjuːbɪk	adj. 立方体的，立方的
tailor		vt. 剪裁；使合适
brighten	ˈbraɪt(ə)n	vi. 变亮；活跃；明亮；快乐高兴
nineteen	ˌnaɪnˈtiːn	num. 十九
comprehension	ˌkɒmprɪˈhenʃ(ə)n	n. 包含；理解
amongst		prep. 在 … 之中；在 … 当中（等于 among ）
geometry	dʒiˈɒmətri	n. 几何学
knob		n. 把手；瘤；球形突出物
tablet		n. 碑；药片；小块；写字板
seventy	ˈsev(ə)nti	n. 七十；七十个；七十岁；七十年代
evaporate		vt. 使 …… 蒸发；使 …… 脱水；使 …… 消失
volleyball		n. 排球
coarse	kɔːs	adj. 粗糙的；下等的；粗俗的
radioactive	ˌreɪdiəʊˈæktɪv	adj. 放射性的；有辐射的
graph	ɡrɑːf	n. [ 数 ] 图表；曲线图
        """,
        """
spelling	ˈspelɪŋ	n. 拼法；拼字；拼写
gulf	ɡʌlf	n. 海湾；深渊；漩涡；分歧
hawk		vt. 捕捉；咳出；兜售，沿街叫卖
aviation	ˌeɪviˈeɪʃ(ə)n	n. 航空；飞行术；飞机制造业
Mercury	ˈmɜːkjəri	n. 水银；水银柱；精神
decay	dɪˈkeɪ	vi. 腐烂，腐朽；衰退，衰减
hen	hen	n. 母鸡；雌禽；女人
hopeless	ˈhəʊpləs	adj. 绝望的；不可救药的
fasten	ˈfɑːs(ə)n	vt. 扎牢；使固定；集中于；强加于
specially	ˈspeʃəli	adv. 特别地；专门地
durable		adj. 耐用的，持久的
compass	ˈkʌmpəs	n. 指南针，罗盘；圆规
outset		n. 开始；开端
Eve	iːv	n. 前夕；傍晚；重大事件关头
bound	baʊnd	adj. 受约束的；装有封面的；有义务的
acceleration	əkˌseləˈreɪʃ(ə)n	n. 加速，促进；加速度
whirl		n. 旋转，回旋；昏乱；一连串的事；短暂的旅行
wavelength		n. 波长
delete	dɪˈliːt	vt. 删除
successive	səkˈsesɪv	adj. 连续的；继承的；依次的；接替的
carpenter	ˈkɑːpəntə(r)	n. 木匠，木工
conversely	ˈkɒnvɜːsli	adv. 相反地
thunder	ˈθʌndə(r)	n. 雷；轰隆声；恐吓
waterfall	ˈwɔːtəfɔːl	n. 瀑布；瀑布似的东西
accordance	əˈkɔːd(ə)ns	n. 一致；和谐
petroleum	pəˈtrəʊliəm	n. 石油（crude oil）
honeymoon		n. 蜜月；蜜月假期；（新生事物、新建关系等的）短暂的和谐时期
indoors	ˌɪnˈdɔːz	adv. 在室内，在户内
sting	stɪŋ	n. 刺痛；刺毛；讽刺，刺激
idle	ˈaɪd(ə)l	adj. 懒惰的；闲置的；停顿的
scenery	ˈsiːnəri	n. 风景；景色； [ 戏 ] 舞台布景
inventor	ɪnˈventə(r)	n. 发明家；发明人；创造者
famine	ˈfæmɪn	n. 饥荒；饥饿，奇缺
disappoint	ˌdɪsəˈpɔɪnt	vt. 使失望
cassette		n. 盒式磁带；暗盒；珠宝箱；片匣
dwelling	ˈdwelɪŋ	n. 住处；寓所
harden	ˈhɑːd(ə)n	vi. 变硬，变坚固；变坚强；变冷酷
frank	fræŋk	adj. 坦白的，直率的；老实的
abnormal	æbˈnɔːm(ə)l	adj. 反常的，不规则的；变态的
navigation	ˌnævɪˈɡeɪʃ(ə)n	n. 航海；航行
plaster	ˈplɑːstə(r)	n. 灰泥；石膏；膏药
downward	ˈdaʊnwəd	adj. 向下的，下降的
ninety	ˈnaɪnti	n. 九十
superficial	ˌsuːpəˈfɪʃ(ə)l	adj. 表面的；肤浅的
puff		vt. 喷出，张开；夸张；使膨胀；使骄傲自满
misunderstand	ˌmɪsʌndəˈstænd	vt. 误解；误会
microscope	ˈmaɪkrəskəʊp	n. 显微镜
spear	spɪə(r)	n. 矛，枪
liable		adj. 有责任的，有义务的；应受罚的；有 … 倾向的；易 … 的
cucumber	ˈkjuːkʌmbə(r)	n. 黄瓜；胡瓜
overtime		n. 加班时间；延长时间； [ 体 ] 加时赛； adj. 超时的；加班的； vt. 使超过时间； adv. 加班地
dorm	dɔːm	n. 宿舍（等于 dormitory ）
heroine	ˈherəʊɪn	n. 女英雄；女杰
congratulate		vt. 庆贺；恭喜；祝贺
grammar	ˈɡræmə(r)	n. 语法；语法书
respectful	rɪˈspektfl	adj. 恭敬的；有礼貌的
indifferent	ɪnˈdɪfrənt	adj. 漠不关心的；无关紧要的；中性的，中立的
cricket	ˈkrɪkɪt	n. 蟋蟀；板球，板球运动
rotten	ˈrɒt(ə)n	adj. 腐烂的；堕落的；恶臭的；虚弱的； [ 俚 ] 极坏的
deceive		v. 欺骗；行骗
crane	kreɪn	n. 鹤；吊车，起重机
irregular	ɪˈreɡjələ(r)	n. 不规则物；不合规格的产品
crow	krəʊ	v.欢叫，高兴地说；自鸣得意
cannon	ˈkænən	n. 大炮；加农炮；榴弹炮；机关炮
cloak	kləʊk	n. 斗蓬；宽大外衣；托词
knit		vi. 编织；结合；皱眉
lag		n. 迟延；防护套；囚犯；桶板；落后
nylon		n. 尼龙，聚酰胺纤维；尼龙袜
enclose	ɪnˈkləʊz	vt. 围绕；装入；放入封套
flare	fleə(r)	vt. 使张开；使闪耀；用发光信号发出；使外倾
imprison	ɪmˈprɪzn	vt. 监禁；关押；使 … 下狱
simplify	ˈsɪmplɪfaɪ	vt. 简化；使单纯；使简易
envy	ˈenvi	n. 羡慕；嫉妒，妒忌
noticeable	ˈnəʊtɪsəb(ə)l	adj. 显而易见的，显著的；值得注意的
cock	kɒk	n. 公鸡；雄鸟；龙头；头目
handkerchief		n. 手帕；头巾，围巾
radiate	ˈreɪdieɪt	vt. 传播；辐射；广播；发射；流露
skim	skɪm	vt. 撇去 … 的浮物；从 … 表面飞掠而过；去除；略读； [ 俚 ] （为逃税而）隐瞒（部分收入）
pudding		n. 布丁
granddaughter	ˈɡrændɔːtə(r)	n. 外孙女；孙女
        """,
        """
accustomed	əˈkʌstəmd	adj. 通常的；独有的；习惯的
gracious		adj. 亲切的；和蔼的；高尚的；雅致的
freight	freɪt	vt. 运送；装货；使充满
thickness	ˈθɪknəs	n. 厚度；层；含混不清；浓度
lighten	ˈlaɪtn	vi. 减轻；发亮
outward	ˈaʊtwəd	adj. 外面的；向外的；公开的；外服的；肉体的
amaze	əˈmeɪz	vt. 使吃惊
rein		n. 缰绳；驾驭；统治；支配
minus	ˈmaɪnəs	prep. 减，减去
mislead	ˌmɪsˈliːd	vt. 误导；带错
semiconductor		n. 半导体
purity	ˈpjʊərəti	n. 纯净；纯洁；纯粹；纯度
intentional	ɪnˈtenʃən(ə)l	adj. 故意的；策划的；蓄意的
exterior	ɪkˈstɪəriə(r)	adj. 外部的；表面的；外在的
slipper	ˈslɪpə(r)	n. 拖鞋
quiz	kwɪz	n. 考查；恶作剧；课堂测验
timetable	ˈtaɪmteɪb(ə)l	n. 时间表；课程表；时刻表
lest	lest	conj. 唯恐，以免；担心
beginner	bɪˈɡɪnə(r)	n. 初学者；新手；创始人
rake		vi. 搜索；用耙子耙；掠过，擦过
quotation	kwəʊˈteɪʃ(ə)n	n. 引用语；引证；报价单
greedy	ˈɡriːdi	adj. 贪婪的；贪吃的；渴望的
shepherd	ˈʃepəd	vt. 带领；看管；牧羊；指导
scissors	ˈsɪzəz	n. 剪刀；剪式跳法
trumpet	ˈtrʌmpɪt	n. 喇叭；喇叭声
verb	vɜːb	n. 动词；动词词性；动词性短语或从句
indispensable	ˌɪndɪˈspensəb(ə)l	adj. 不可缺少的；绝对必要的；责无旁贷的
dye	daɪ	n. 染料；染色
revolt	rɪˈvəʊlt	vi. 反抗；反叛；反感，厌恶
oriental	ˌɔːriˈent(ə)l	adj. 东方人的；东方的
clasp		n. 扣子，钩子；握手
swarm	swɔːm	vi. 挤满；成群浮游；云集
interruption		n. 中断；干扰；中断之事
peninsula		n. 半岛
despise	dɪˈspaɪz	vt. 轻视，鄙视
doubtful	ˈdaʊtf(ə)l	adj. 可疑的；令人生疑的；疑心的；不能确定的
frost	frɒst	vt. 结霜于；冻坏
tutor		vt. 约束；辅导
furnace		n. 火炉，熔炉
midday		n. 中午；正午
barber		vt. 为 … 理发；修整
brisk		adj. 敏锐的，活泼的，轻快的；凛冽的
solemn		adj. 庄严的，严肃的；隆重的，郑重的
dread	dred	n. 恐惧；可怕的人（或物）
clumsy	ˈklʌmzi	adj. 笨拙的
alphabet	ˈælfəbet	n. 字母表，字母系统；入门，初步
pollute	pəˈluːt	vt. 污染；玷污；败坏
singular	ˈsɪŋɡjələ(r)	adj. [ 语 ] 单数的；单一的；非凡的；异常的
captive	ˈkæptɪv	adj. 被俘虏的；被迷住的
coil	kɔɪl	vt. 盘绕，把 … 卷成圈
infer	ɪnˈfɜː(r)	vt. 推断；推论
germ	dʒɜːm	n. 细菌；胚芽，萌芽
shriek	ʃriːk	vi. 尖叫；促人注意
sponge		vt. 抹掉；用海绵擦拭；讨得
needless	ˈniːdləs	adj. 多余的，无用的；不必要的，不需要的
breadth	bredθ	n. 宽度，幅度；宽宏
cripple	ˈkrɪp(ə)l	vt. 削弱；使残废；使跛
radius	ˈreɪdiəs	n. 半径，半径范围；辐射光线；有效航程；桡骨
broom	bruːm	n. 金雀花；扫帚
undertaking	ˌʌndəˈteɪkɪŋ	n. 事业；企业；保证；殡仪业
hasten	ˈheɪs(ə)n	vt. 催促；使赶紧；加速
digest	daɪˈdʒest	vt. 消化；吸收；融会贯通
attentive	əˈtentɪv	adj. 留意的，注意的
donkey	ˈdɒŋki	n. 驴子；傻瓜(as stupid as a donkey)；顽固的人
vanity		n. 虚荣心；浮华；空虚；无价值的东西
kite	kaɪt	n. 风筝
compress	kəmˈpres	vi. 受压缩小
mortal	ˈmɔːt(ə)l	adj. 终有一死的；致死的(a mortal wound)；凡人的；不共戴天的
reed		n. 芦苇；簧片；牧笛；不可依靠的人
robber	ˈrɒbə(r)	n. 强盗；盗贼
plentiful	ˈplentɪf(ə)l	adj. 丰富的；许多的；丰饶的
amplify		vt. 放大，扩大；增强；详述
shear	ʃɪə(r)	vt. 修剪；剪；剥夺
snowy	ˈsnəʊi	adj. 下雪的，多雪的；被雪覆盖的；洁白无瑕的
rot	rɒt	n. 腐烂；腐败；腐坏
slum	slʌm	vi. （为猎奇或救济等）去贫民窟
tan		vt. 晒成褐色；鞣（革）
inefficient		adj. 无效率的，效率低的；无能的
thermometer	θəˈmɒmɪtə(r)	n. 温度计；体温计
vibrate	vaɪˈbreɪt	vi. 振动；颤动；摇摆；踌躇
        """,
        """
underline	ˌʌndəˈlaɪn	vt. 强调；在 … 下面划线；预告
sly		adj. 狡猾的；淘气的；诡密的
stitch	stɪtʃ	n. 针脚，线迹；一针
grieve	ɡriːv	vt. 使悲伤，使苦恼
voltage	ˈvəʊltɪdʒ	n. [ 电 ] 电压
heading	ˈhedɪŋ	n. 标题；信头；（足球）头球
stiffen		vi. 变猛烈；变硬；变粘
harness	ˈhɑːnɪs	vt. 披上甲胄；套；驾驭；治理
pessimistic	ˌpesɪˈmɪstɪk	adj. 悲观的，厌世的；悲观主义的
turbulent	ˈtɜːbjələnt	adj. 骚乱的，混乱的(the turbulent times)；狂暴的；吵闹的
wreath	riːθ	n. 花冠；圈状物
troublesome	ˈtrʌb(ə)lsəm	adj. 麻烦的；讨厌的；使人苦恼的
gloomy	ˈɡluːmi	adj. 黑暗的；沮丧的；阴郁的
typewriter	ˈtaɪpraɪtə(r)	n. 打字机
annoy	əˈnɔɪ	vt. 骚扰；打搅；惹恼
humorous	ˈhjuːmərəs	adj. 诙谐的，幽默的；滑稽的，可笑的
melon		n. 瓜；甜瓜；大肚子；圆鼓鼓像瓜似的东西
racket		n. 球拍；吵闹，喧闹
anyhow	ˈenihaʊ	adv. 无论如何；不管怎样；总之
traitor		n. 叛徒；卖国贼；背信弃义的人
lame	leɪm	adj. 僵痛的；跛足的；不完全的；无说服力的
continual	kənˈtɪnjuəl	adj. 频繁的；持续不断的
stern	stɜːn	n. 船尾；末端
statesman		n. 国务活动家；政治家
Marxist		n. 马克思主义者
poisonous	ˈpɔɪzənəs	adj. 有毒的；恶毒的；讨厌的
shampoo		n. 洗发；洗发精
vain		adj. 徒劳的；虚荣的；空虚的
metric		adj. 公制的；米制的；公尺的
zeal	ziːl	n. 热心；热诚；热情
brittle		adj. 易碎的，脆弱的；易生气的
cloudy	ˈklaʊdi	adj. 多云的；阴天的；愁容满面的
scold	skəʊld	vi. 责骂；叱责（长辈对晚辈）
subtract	səbˈtrækt	vt. 减去；扣掉
Portuguese		adj. 葡萄牙的；葡萄牙人的；葡萄牙语的
unbearable		adj. 难以忍受的；承受不住的
adjective	ˈædʒɪktɪv	adj. 形容词的；从属的
sow	səʊ	vt. 播种；散布；使密布
stoop		vi. 弯腰；堕落；屈服
proportional	prəˈpɔːʃən(ə)l	adj. 比例的，成比例的；相称的，均衡的
tedious		adj. 冗长乏味的；沉闷的
preferable	ˈprefrəb(ə)l	adj. 更好的，更可取的；更合意的
steward	ˈstjuːəd	n. 乘务员；膳务员；管家；工会管事
sparkle	ˈspɑːk(ə)l	n. 活力；火花；闪耀
whichever	wɪtʃˈevə(r)	pron. 无论哪个；任何一个
yawn	jɔːn	n. 哈欠；裂口
humid	ˈhjuːmɪd	adj. 潮湿的；湿润的；多湿气的
rejoice	rɪˈdʒɔɪs	vi. 高兴；庆祝
dialect		n. 方言，土话；同源语；行话；个人用语特征
saucer		n. 茶托，浅碟；浅碟形物； [ 口 ] 眼睛
clothe	kləʊð	vt. 给 … 穿衣；覆盖；赋予
exceedingly	ɪkˈsiːdɪŋli	adv. 非常；极其；极端；极度地
tame	teɪm	adj. 驯服的；乏味的；顺从的；平淡的
thirst	θɜːst	n. 口渴；渴望；热望
learned	ˈlɜːnɪd	adj. 博学的；有学问的；学术上的
pane	peɪn	n. 窗格；窗格玻璃；边；面；嵌板
mute	mjuːt	adj. 哑的；沉默的；无声的
rouse	raʊz	vt. 唤醒；激起，使振奋；惊起
dormitory	ˈdɔːmətri	n. 宿舍，学生宿舍
upwards	ˈʌpwədz	adv. 向上；在上部；向上游
disable	dɪsˈeɪb(ə)l	vt. 使残废；使失去能力；使无资格
throng	θrɒŋ	n. 人群；众多
bristle		n. 猪鬃；刚毛
refute		vt. 反驳，驳斥；驳倒
sympathize	ˈsɪmpəθaɪz	vi. 同情，怜悯；支持
submerge	səbˈmɜːdʒ	vt. 淹没；把 … 浸入；沉浸
pastime		n. 娱乐，消遣
pebble	ˈpeb(ə)l	n. 卵石；水晶透镜
thorn	θɔːn	n. 刺；荆棘
inward	ˈɪnwəd	adj. 向内的；内部的；精神的；本质上的； [ 古 ] 熟悉的
quarrel		vi. 吵架；争论；挑剔
Arabian		adj. 阿拉伯的；阿拉伯人的
economical	ˌiːkəˈnɒmɪk(ə)l	adj. 经济的；节约的；合算的
further	ˈfɜːðə(r)	adv. 更远地；进一步地；而且
timid	ˈtɪmɪd	adj. 羞怯的；胆小的
vice	vaɪs	n. 缺点；恶习；老虎钳；卖淫
ax	æks	vt. 削减；用斧修整；解雇
slit		vt. 撕裂；使有狭缝
arithmetic		n. 算术，算法
measurable	ˈmeʒərəbl	adj. 可测量的；重要的；重大的
        """,
        """
quarterly	ˈkwɔːtəli	adj. 季度的，按季度的；一年四次的
liter	ˈliːtə(r)	n. 公升（容量单位）
quicken	ˈkwɪkən	vi. 加快；变活跃；进入胎动期
depress	dɪˈpres	vt. 使沮丧；压低；使萧条
joyful	ˈdʒɔɪfl	adj. 欢喜的；令人高兴的
coward	ˈkaʊəd	n. 懦夫，懦弱的人
calculator	ˈkælkjuleɪtə(r)	n. 计算器；计算者
relativity	ˌreləˈtɪvəti	n. 相对性；相对论；相关性
victorious		adj. 胜利的；凯旋的
swan	swɒn	n. 天鹅；天鹅星座;优雅（as graceful as a swan）
spaceship	ˈspeɪsʃɪp	n. ［航］宇宙飞船
violet		n. 紫罗兰；堇菜；羞怯的人
stony	ˈstəʊni	adj. 多石的；无情的；石头的
stormy	ˈstɔːmi	adj. 暴风雨的；猛烈的；暴躁的
queer		adj. 奇怪的；不舒服的；心智不平衡的；同性恋的
riddle	ˈrɪd(ə)l	vt. 解谜；给 ... 出谜；充满于
Englishman	ˈɪŋɡlɪʃmən	n. 英国人
fruitful	ˈfruːtf(ə)l	adj. 多产的；果实结得多的；富有成效的
torrent	ˈtɒrənt	n. 奔流；倾注；迸发；连续不断
locomotive		adj. 运动的；移动的；火车头的
utmost	ˈʌtməʊst	n. 极限；最大可能
alloy		vt. 使减低成色；使成合金
elastic		adj. 有弹性的；易伸缩的；灵活的
ore		n. 矿石；矿
childish	ˈtʃaɪldɪʃ	adj. 幼稚的，孩子气的
glitter	ˈɡlɪtə(r)	vi. 闪烁；闪光
approximate		vt. 使 … 接近；近似；粗略估计
up-to-date		adj. 最新的，最近的；现代的，新式的
brute	bruːt	adj. 无理性的；残忍的
fluent	ˈfluːənt	adj. 流畅的，流利的；液态的；畅流的
scorn	skɔːn	n. 轻蔑；嘲笑；藐视的对象
waterproof	ˈwɔːtəpruːf	adj. 防水的，不透水的
fable		n. 寓言；无稽之谈
doubtless	ˈdaʊtləs	adv. 无疑地；确定地；大概，多半
feeble		adj. 微弱的，无力的；虚弱的；薄弱的
skillful	ˈskɪlf(ə)l	adj. 熟练的；巧妙的
brim	brɪm	n. 边；边缘
refresh	rɪˈfreʃ	vt. 使 … 恢复；更新；使 … 清新；消除 … 的疲劳
westward	ˈwestwəd	adj. 向西的；西方的
rotary	ˈrəʊtəri	adj. 旋转的，转动的；轮流的
twelfth	twelfθ	adj. 第十二的，第十二个的；十二分之一的
naughty	ˈnɔːti	adj. 顽皮的，淘气的；不听话的；没规矩的；不适当的；下流的
bosom	ˈbʊzəm	n. 胸；胸襟；胸怀；中间；内部；内心；乳房
hasty	ˈheɪsti	adj. 匆忙的；轻率的；草率的；性怠的
incline	ɪnˈklaɪn	vi. 倾斜；倾向；易于
overcoat		n. 大衣，外套
idiom	ˈɪdiəm	n. 成语，习语；土话
workman		n. 工匠；技工；男工
shopkeeper	ˈʃɒpkiːpə(r)	n. 店主，老板
analyse	ˈænəlaɪz	vt. 分析；分解；细察
twinkle	ˈtwɪŋk(ə)l	vi. 闪烁；发亮
materialism	məˈtɪəriəlɪzəm	n. 唯物主义；唯物论；物质主义
cunning	ˈkʌnɪŋ	adj. 狡猾的；可爱的；巧妙的
treason		n. 叛国罪；不忠
oppress	əˈpres	vt. 压迫，压抑；使 …… 烦恼；使 …… 感到沉重
transistor		n. [ 电子 ] 晶体管（收音机）
jolly		adj. 愉快的；欢乐的；宜人的
mouthful		n. 一口，满口
father-in-law		n. 公公；岳父； [ 英 ] 继父
deduce	dɪˈdjuːs	vt. 推论，推断；演绎出
postage	ˈpəʊstɪdʒ	n. 邮资，邮费
steamer	ˈstiːmə(r)	n. 轮船；蒸汽机；蒸笼
preface	ˈprefəs	n. 前言；引语
haste	heɪst	n. 匆忙；急忙；轻率
spade	speɪd	n. 铁锹，铲子
tortoise	ˈtɔːtəs	n. 迟缓的人；龟，乌龟（等于 testudo ）
eleventh	ɪˈlev(ə)nθ	n. 第十一；十一分之一
habitual	həˈbɪtʃuəl	adj. 惯常的；习惯的；习以为常的
snowstorm	ˈsnəʊstɔːm	n. 暴风雪；雪暴
purify	ˈpjʊərɪfaɪ	vt. 净化；使纯净
automation		n. 自动化；自动操作
unlucky	ʌnˈlʌki	adj. 不幸的；倒霉的；不吉利的
condense		vi. 浓缩；凝结
mechanically	məˈkænɪkli	adv. 机械地；呆板地；物理上地
zebra	ˈzebrə	n. 斑马
indefinite	ɪnˈdefɪnət	adj. 不确定的；模糊的；无限的
auxiliary		n. 助动词；辅助者，辅助物；附属机构
manly	ˈmænli	adj. 强壮的；适于男人的；男子气概的
Marxism		n. 马克思主义
sparrow		n. 麻雀；矮小的人
        """,
        """
diligent		adj. 勤勉的；用功的，费尽心血的
pail		n. 桶，提桶
springtime		n. 春季；春天；初期
cordial		adj. 兴奋的；热忱的，诚恳的
oar		n. 桨；橹；桨手
refreshment	rɪˈfreʃmənt	n. 点心；起提神作用的东西；精力恢复
hare		n. 野兔
dew		n. 露水；清新；珠，滴
sportsman	ˈspɔːtsmən	n. 运动员；运动家；冒险家
hateful	ˈheɪtfl	adj. 可恨的；可憎的；可恶的
brood		vt. 沉思；孵
judgement	ˈdʒʌdʒmənt	n. 审判；判断力；意见；评价
labour	ˈleɪbə(r)	n. 劳动力，人工（child labour）；分娩
eyesight		n. 视力；目力
panda	ˈpændə	n. 熊猫；猫熊
ripen	ˈraɪpən	vt. 使成熟
indignant	ɪnˈdɪɡnənt	adj. 义愤的；愤愤不平的
unsatisfactory	ˌʌnˌsætɪsˈfæktəri	adj. 不令人满意的；不满足的；不符合要求的
tram		n. 电车轨道；煤车
radish	ˈrædɪʃ	n. 萝卜，小萝卜
weld		vt. 焊接；使结合；使成整体
turnip	ˈtɜːnɪp	n. 芜菁甘蓝，大头菜；萝卜
pronunciation	prəˌnʌnsiˈeɪʃ(ə)n	n. 发音；读法
telegraph	ˈtelɪɡrɑːf	n. 电报机，电报
canteen		n. 水壶；食堂，小卖部
surname		n. 姓，姓氏；绰号，别名
deceit		n. 欺骗；谎言；欺诈手段
circumference		n. 圆周；周长；胸围
bough		n. 大树枝
zealous	ˈzeləs	adj. 热心的，热情的，积极的
aural		adj. 耳的；听觉的；气味的；先兆的
dialog		n. 对话；会话
trifle	ˈtraɪf(ə)l	n. 琐事；少量；蛋糕
peep	piːp	n. 偷看；隐约看见，瞥见
repent		vi. 忏悔；后悔
unsuitable		adj. 不适合的；不相称的；不适宜的
pronoun	ˈprəʊnaʊn	n. [ 语 ] 代词
gunpowder		n. 有烟火药；火药
ice-cream		adj. 乳白色的；冰淇淋的
mister		n. 先生；平民
considerate	kənˈsɪdərət	adj. 体谅的；考虑周到的；体贴的
seaman	ˈsiːmən	n. 海员，水手；水兵
unkind		adj. 不仁慈的，不厚道的；无情的
landlady	ˈlændleɪdi	n. 女房东；女地主；女店主
vex		vt. 使烦恼；使困惑；使恼怒
recollect	ˌrekəˈlekt	v. 回忆，想起
gaseous		adj. 气态的，气体的；无实质的
negro	ˈniːɡrəʊ	n. 黑人
upside-down		adj. 乱七八糟的；颠倒的
grammatical	ɡrəˈmætɪkl	adj. 符合语法规则的；文法的
reproach	rɪˈprəʊtʃ	n. 责备；耻辱
soluble		adj. 可溶的，可溶解的；可解决的
advisable		adj. 明智的，可取的，适当的
porridge		n. 粥，糊；麦片粥
plural	ˈplʊərəl	adj. 复数的
retell		vt.重讲
preposition	ˌprepəˈzɪʃ(ə)n	n.前置词
kilogram	ˈkɪləɡræm	n.[物]千克,公斤
        """
    )
}
