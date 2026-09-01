package com.personalieltscoach.data.seed

internal data class Paul1000Entry(
    val word: String,
    val phonetic: String,
    val meaning: String
)

/**
 * Offline copy of the public Paul1000 list from https://www.ncego.com/words/topic/Paul1000.
 * The page currently exposes 1045 unique entries despite the topic name.
 */
object Paul1000WordPack {
    const val SOURCE_ENTRY_COUNT = 1045

    internal val entries: List<Paul1000Entry> by lazy {
        rows.lineSequence()
            .map(String::trim)
            .filter(String::isNotBlank)
            .map { row ->
                val parts = row.split('\t')
                require(parts.size == 3) { "Invalid Paul1000 word row" }
                Paul1000Entry(parts[0], parts[1], parts[2])
            }
            .toList()
    }

    private val rows = """
a	ə	art. 一；任一；每一
ability	əˈbɪləti	n. 能力，能耐；才能
able	ˈeɪb(ə)l	adj.能...的,有才能的,能干的,能够的
about	əˈbaʊt	prep. 关于；大约
above	əˈbʌv	prep. 在…上面；在…之上；超过
accept	əkˈsept	vt. 接受；承认；承担；承兑；容纳
account	əˈkaʊnt	n. 帐目，帐单；理由；帐户；解释
across	əˈkrɒs	prep. 穿过；横穿
act	ækt	vt. 扮演；装作，举动像
action	ˈækʃ(ə)n	n. 行动；活动；功能；情节；战斗
activity	ækˈtɪvəti	n. 活动；活跃；行动
actually	ˈæktʃuəli	adv. 实际上；事实上
add	æd	vi. 加；增加；加起来；做加法
additional	əˈdɪʃən(ə)l	adj. 附加的，额外的
affect	əˈfekt	vt. 影响；假装；感动；感染
afraid	əˈfreɪd	adj. 害怕的；担心的；恐怕
after	ˈɑːftə(r)	adv. 后来，以后
afternoon	ˌɑːftəˈnuːn	n. 午后，下午
afterward	ˈɑːftəwəd	adv. 以后，后来
again	əˈɡen	adv. 再一次；又，此外
against	əˈɡenst	prep. 反对，违反；靠；倚；防备
age	eɪdʒ	n. 年龄；时代；阶段；寿命，使用年限
ago	əˈɡəʊ	adv. 以前，以往
agree	əˈɡriː	vt. 同意；赞成；承认
air	eə(r)	n. 空气，大气；曲调；天空；样子
alive	əˈlaɪv	adj. 活着的；活泼的；有生气的
all	ɔːl	adj. 全部的
allow	əˈlaʊ	vt. 允许；认可；给予
almost	ˈɔːlməʊst	adv. 差不多，几乎
alone	əˈləʊn	adj. 单独的；孤独的；独自的
along	əˈlɒŋ	adv. 向前；一起；来到
already	ɔːlˈredi	adv. 已经，早已；先前
also	ˈɔːlsəʊ	adv. 也；同样；而且
although	ɔːlˈðəʊ	conj. 虽然，尽管
always	ˈɔːlweɪz	adv. 总是；永远，一直；常常
among	əˈmʌŋ	prep. 在 … 中间；在 … 之中
an	æn	art.一(在元音字母前代替不定代词a) [域] Netherlands Antilles ,荷兰属地
analysis	əˈnæləsɪs	n. 分析；分解；验定
and	ænd	conj. 和，与；而且；然后；就；但是
angry	ˈæŋɡri	adj. 生气的；愤怒的；狂暴的；（伤口等）发炎的
annual	ˈænjuəl	adj. 年度的；每年的
another	əˈnʌðə(r)	adj. 另外的；不同的；又一，另一
answer	ˈɑːnsə(r)	vt. 符合；回答
any	ˈeni	adj. 任何的；所有的；丝毫
anymore	ˌeniˈmɔː(r)	adv. 再也不，不再
anyone	ˈeniwʌn	pron. 任何人；任何一个
anything	ˈeniθɪŋ	pron. 任何事
anyway	ˈeniweɪ	adv. 无论如何，不管怎样；总之
anywhere	ˈeniweə(r)	adv. 无论何处；在任何地方
apart	əˈpɑːt	adv. 分离着；相距；与众不同地
appear	əˈpɪə(r)	vi. 出现；似乎；显得； [ 法 ] 出庭
apply	əˈplaɪ	vt. 应用；申请；涂，敷
approach	əˈprəʊtʃ	n. 接近；方法；途径
appropriate	əˈprəʊpriət	adj. 适当的
April	ˈeɪprəl	n.四月(略作Apr)
are	ɑː(r)	prep.是,在,公亩
area	ˈeəriə	n. 面积；区域，地区；范围
argue	ˈɑːɡjuː	vi. 争论，辩论；提出理由
around	əˈraʊnd	adv. 到处；大约；在附近
arrive	əˈraɪv	vi. 到达；成功；出生；达成
art	ɑːt	n. 艺术；美术；艺术品
article	ˈɑːtɪk(ə)l	n. 物品；文章；冠词；条款
as	æz	conj. 因为；依照；当 … 时；随着；虽然
ask	ɑːsk	vt. 问，询问；邀请；要求；需要；讨价
asleep	əˈsliːp	adj. 睡着的；麻木的；长眠的
assumption	əˈsʌmpʃn	n. 假定；设想；担任；采取
at	æt	prep. 在（表示存在或出现的地点、场所、位置、空间）；向；朝；因为；忙于；以（某种价格、速度等）
attach	əˈtætʃ	vt. 系上；贴上；使依附；使依恋
attend	əˈtend	vt. 出席；上（大学等）；照料；招待；陪伴
attention	əˈtenʃ(ə)n	n. 注意力；关心；立正！（口令）
attitude	ˈætɪtjuːd	n. 态度；姿势；看法；意见
august	ɔːˈɡʌst	adj.令人敬畏的,威严的n.八月(略作Aug) August 八月
autumn	ˈɔːtəm	n. 秋天；成熟期；渐衰期，凋落期
available	əˈveɪləb(ə)l	adj. 可利用的；有效的，可得的；空闲的
average	ˈævərɪdʒ	n. 平均；平均数； [ 商 ] 海损
avoid	əˈvɔɪd	vt. 避免；避开，躲避；消除
aware	əˈweə(r)	adj. 知道的；意识到的；有 … 方面知识的；懂世故的
away	əˈweɪ	adv. 离去，离开；在远处
back	bæk	n. 背部；后面；靠背；足球等的后卫；书报等的末尾
bad	bæd	adj. 坏的；严重的；劣质的
ball	bɔːl	n. 球；舞会
basic	ˈbeɪsɪk	adj. 基本的；基础的
be	biː	prep. 在，存在；是
beat	biːt	vt. 打；打败
because	bɪˈkəz; bɪˈkɒz	conj. 因为
been	biːn	v. 是，有（be的过去分词）
before	bɪˈfɔː(r)	prep. 在 … 之前，先于
begin	bɪˈɡɪn	vt. 开始
behavior	bɪˈheɪvjə(r)	n. 行为，举止；态度；反应
behind	bɪˈhaɪnd	prep. 支持；落后于；晚于
being	ˈbiːɪŋ	n. 存在；生命；本质；品格
believe	bɪˈliːv	vi. 信任；料想；笃信宗教
below	bɪˈləʊ	adv. 在下面，在较低处；在本页下面
benefit	ˈbenɪfɪt	n. 利益，好处；救济金
beside	bɪˈsaɪd	prep. 在旁边；与 … 相比；和 … 无关
besides	bɪˈsaɪdz	adv. 而且；此外
best	best	n. 最好的人，最好的事物；最佳状态
better	ˈbetə(r)	n. 较好者；打赌的人（等于 bettor ）；长辈
between	bɪˈtwiːn	prep. 在 … 之间
beyond	bɪˈjɒnd	prep. 超过；越过；在 ... 较远的一边；那一边
big	bɪɡ	adj. 大的；重要的；量大的
billion	ˈbɪljən	n. 十亿；大量
black	blæk	adj. 黑色的；黑人的；邪恶的
blue	bluː	adj. 蓝色的；忧郁的，沮丧的；下流的
body	ˈbɒdi	n. 身体；主体；团体；主要部分；大量
book	bʊk	n. 书籍；帐簿；卷；名册；工作簿
both	bəʊθ	adj. 两者的；两个的
boy	bɔɪ	n. 男孩； [ 美口 ] 男人
brain	breɪn	n. 脑袋；头脑，智力
break	breɪk	n. 休息，中断；破裂处
bring	brɪŋ	vt. 带来；引起；促使；使某人处于某种情况或境地
budget	ˈbʌdʒɪt	n. 预算，预算费
build	bɪld	vt. 建筑；建立
business	ˈbɪznəs	n. 商业；生意；交易；事情
busy	ˈbɪzi	adj. 忙碌的；热闹的；正被占用的
but	bʌt	conj. 但是；然而；而是
buy	baɪ	vt. 购买；获得；贿赂
by	baɪ	prep. 被；经由；在 … 之前；在附近；依据；通过
bye	baɪ	int. 再见
call	kɔːl	vi. 呼叫；拜访；叫牌
campaign	kæmˈpeɪn	vi. 作战；参加活动；参加竞选
can	kæn	n. 罐头，一罐；金属容器
capital	ˈkæpɪt(ə)l	n. 首都，省会；大写字母；资金；资本家
car	kɑː(r)	n. 汽车；车厢
card	kɑːd	n. 卡片；纸牌；明信片
career	kəˈrɪə(r)	n. 事业，职业；生涯
careful	ˈkeəf(ə)l	adj. 仔细的，小心的
carry	ˈkæri	vt. 拿，扛；搬运；携带；支持
case	keɪs	n. 情况；实例；箱
catch	kætʃ	vt. 赶上；抓住；感染；了解
cause	kɔːz	n. 原因；事业；目标
center	ˈsentə(r)	n. 中心，中央；中心点；中锋
central	ˈsentrəl	adj. 中心的；主要的；中枢的
certain	ˈsɜːt(ə)n	adj. 某一；确信；无疑的；有把握的；必然的
certainly	ˈsɜːt(ə)nli	adv. 当然；必定；行（用于回答）
challenge	ˈtʃælɪndʒ	n. 挑战；怀疑
chance	tʃɑːns	n. 可能性；机会，际遇；运气，侥幸
change	tʃeɪndʒ	vt. 改变；交换
character	ˈkærəktə(r)	n. 字符；特性；角色；性格，品质
check	tʃek	vi. 证明无误；核对无误；将军（象棋）
child	tʃaɪld	n. 儿童，孩子；子孙；产物
choice	tʃɔɪs	n. 选择；选择权；精选品
choose	tʃuːz	vt. 选择，决定
church	tʃɜːtʃ	n. 教堂；礼拜；教派
city	ˈsɪti	n. 城市，都市
claim	kleɪm	vi. 提出要求
class	klɑːs	n. 班级；阶级；种类
clean	kliːn	adj. 清洁的，干净的；清白的
clear	klɪə(r)	adj. 清楚的；清澈的；晴朗的；无罪的
clearly	ˈklɪəli	adv. 明净地；清晰地；无疑地；明显地
close	kləʊz	adj. 亲密的；亲近的；紧密的
cold	kəʊld	adj. 寒冷的；冷淡的，不热情的；失去知觉的
collect	kəˈlekt	vt. 收集；募捐
college	ˈkɒlɪdʒ	n. 学院；学会；大学
color	ˈkʌlə(r)	n. 颜色；脸色；肤色；颜料
come	kʌm	vi. 来；出现；到达；变成；开始；发生
comfortable	ˈkʌmftəb(ə)l	adj. 舒适的，舒服的
common	ˈkɒmən	adj. 普通的；共同的；通常的；一般的
community	kəˈmjuːnəti	n. 社区；团体；共同体；群落
company	ˈkʌmpəni	n. 公司；陪伴，同伴；连队
compare	kəmˈpeə(r)	vt. 比较；对照；比喻为
complete	kəmˈpliːt	adj. 完全的；完整的；彻底的
completely	kəmˈpliːtli	adv. 完全地，彻底地；完整地
complex	ˈkɒmpleks	adj. 复杂的；合成的
computer	kəmˈpjuːtə(r)	n. 电脑；计算机；电子计算机
concern	kənˈsɜːn	vt. 涉及，关系到；使担心
condition	kənˈdɪʃn	n. 条件；情况；环境；身分
conference	ˈkɒnfərəns	n. 会议；协商；讨论； [ 篮 ] 联盟
conflict	ˈkɒnflɪkt	n. 冲突，矛盾；争执；斗争
connect	kəˈnekt	vt. 连接；联合；关连
connection	kəˈnekʃn	n. 连接；关系；连接件
consider	kənˈsɪdə(r)	vt. 认为；考虑；细想；考虑到
contain	kənˈteɪn	vt. 包含；容纳；控制；牵制（敌军）
content	ˈkɒntent	n. 内容，目录；容量；满足
context	ˈkɒntekst	n. 环境；上下文；来龙去脉
continue	kənˈtɪnjuː	vi. 仍旧，连续；继续，延续
contract	ˈkɒntrækt; kənˈtrækt	vi. 感染；订约；收缩
control	kənˈtrəʊl	n. 控制；管理；抑制；操纵装置
correct	kəˈrekt	vt. 改正；告诫
cost	kɒst	vt. 花费；使付出；使花许多钱
could	kʊd	aux. 能够
count	kaʊnt	vt. 计算；认为
country	ˈkʌntri	n. 国家；故乡
couple	ˈkʌp(ə)l	n. 数个；对；夫妇
course	kɔːs	n. 进程；过程；道路；一道菜
cover	ˈkʌvə(r)	vt. 包括；涉及；采访，报导
create	kriˈeɪt	vt. 造成；创造，创作
creative	kriˈeɪtɪv	adj. 创造性的
culture	ˈkʌltʃə(r)	n. 文化，文明；修养；栽培
current	ˈkʌrənt	adj. 现在的；最近的；草写的；流通的，通用的
currently	ˈkʌrəntli	adv. 当前；一般地
customer	ˈkʌstəmə(r)	n. 顾客（买东西的）； [ 口 ] 家伙
cut	kʌt	n. 伤口；切口；削减；（服装等的）式样； [ 体育 ] 削球；切入； vt. 切割；削减；缩短；刺痛； vi. 切割；相交；切牌；停拍；不出席； adj. 割下的；雕过的；缩减的
cute	kjuːt	adj. 可爱的；聪明的，伶俐的；漂亮的
damage	ˈdæmɪdʒ	vi. 损害；损毁；赔偿金
dangerous	ˈdeɪndʒərəs	adj. 危险的
dare	deə(r)	n. 挑战；挑动
dark	dɑːk	adj. 黑暗的，深色的；无知的；模糊的；忧郁的
data	ˈdeɪtə	n. 资料；数据（ datum 的复数）
day	deɪ	n. 一天；白昼；时期
dead	ded	adj. 无生命的；废弃了的；呆板的
deal	diːl	vt. 发牌；处理；给予；分配；买卖，交易
debate	dɪˈbeɪt	vt. 辩论，争论，讨论
December	dɪˈsembə(r)	n.十二月(略作Dec)
decide	dɪˈsaɪd	vt. 决定；判决；解决
decision	dɪˈsɪʒ(ə)n	n. 决定，决心；决议
deep	diːp	n. 深处；深渊
demand	dɪˈmɑːnd	vt. 要求；需要；查询
democratic	ˌdeməˈkrætɪk	adj. 民主的；民主政治的；大众的
describe	dɪˈskraɪb	vt. 描述，形容；描绘
design	dɪˈzaɪn	vt. 设计；计划；构思
despite	dɪˈspaɪt	prep. 尽管，不管
detail	ˈdiːteɪl	n. 细节，详情
develop	dɪˈveləp	vt. 开发；使成长；进步；使显影
did	dɪd	v.做（do 的过去式）
die	daɪ	vi. 死亡；凋零；熄灭
difference	ˈdɪfrəns	n. 差异；不同；争执
different	ˈdɪfrənt	adj. 不同的；个别的，与众不同的
differently	ˈdɪfrəntli	adv. 差异；各种；不同地
difficult	ˈdɪfɪkəlt	adj. 困难的；不随和的；执拗的
digital	ˈdɪdʒɪt(ə)l	adj. 数字的；手指的
direct	dəˈrekt	adj. 直接的；亲身的；恰好的；直系的
direction	dəˈrekʃ(ə)n	n. 方向；指导；用法说明；趋势
directly	dəˈrektli	adv. 直接地；立即；马上；坦率地；正好地
discuss	dɪˈskʌs	vt. 讨论；论述，辩论
divide	dɪˈvaɪd	vt. 分开；划分；除；使产生分歧
do	duː	n. 要求；规定； C 大调音阶中的第一音
doctor	ˈdɒktə(r)	n. 医生；博士
document	ˈdɒkjumənt	n. 文件，公文；文档；证件
does	dʌz	v.做；工作；有用（do 的第三人称单数）
door	dɔː(r)	n. 门；家，户；门口；通道
down	daʊn	adv. 向下，下去；在下面
draw	drɔː	vt. 画；拉；吸引
drive	draɪv	vi. 开车；猛击；飞跑
drop	drɒp	vt. 滴；使降低；随口漏出；使终止
drug	drʌɡ	n. 毒品；药；滞销货；麻醉药
due	djuː	adj. 到期的；应得的；应付的；预期的
during	ˈdjʊərɪŋ	prep. 在 … 的时候，在 … 的期间
each	iːtʃ	adj. 每；各自的
early	ˈɜːrli	adj. 早期的；早熟的
easily	ˈiːzəli	adv. 容易地；无疑地
east	iːst	n. 东方；东方国家；东风
easy	ˈiːzi	adj. 容易的；舒适的
eat	iːt	vt. 吃，喝；腐蚀；烦扰
economic	ˌiːkəˈnɒmɪk	adj. 经济的，经济上的；经济学的
economy	ɪˈkɒnəmi	n. 节约；经济；理财
effect	ɪˈfekt	n. 效果；作用；影响
effective	ɪˈfektɪv	adj. 有效的，起作用的；给人深刻印象；实际的，实在的
effort	ˈefət	n. 成就；努力
eight	eɪt	num. 八个；八；第八
either	ˈaɪðə(r)	adj. 两者之中任一的；两者之中每一的
election	ɪˈlekʃ(ə)n	n. 选举；当选；选择权； [ 宗 ] 上帝的选拔
else	els	adv. 另外；其他；否则
employee	ɪmˈplɔɪiː	n. 雇员；从业员工
employer	ɪmˈplɔɪə(r)	n. 雇主，老板
empty	ˈempti	adj. 空的；无意义的；徒劳的；无知的
encourage	ɪnˈkʌrɪdʒ	vt. 鼓励，怂恿；激励；支持
end	end	n. 结束；末端；目标；死亡；尽头
energy	ˈenədʒi	n. 精力；能量；活力；精神
enjoy	ɪnˈdʒɔɪ	vt. 喜爱；欣赏，享受；使过得快活
enough	ɪˈnʌf	adv. 足够地，充足地
enter	ˈentə(r)	vt. 进入；开始；参加
environment	ɪnˈvaɪrənmənt	n. 环境，外界
equal	ˈiːkwəl	adj. 相等的；胜任的；平等的
equipment	ɪˈkwɪpmənt	n. 设备，装备；器材
especially	ɪˈspeʃəli	adv. 特别；尤其；格外
establish	ɪˈstæblɪʃ	vt. 建立；安置；创办
even	ˈiːv(ə)n	adj. 偶数的；相等的；平坦的
evening	ˈiːvnɪŋ	n. 傍晚；晚上；后期；（联欢性的）晚会
event	ɪˈvent	n. 事件，大事；结果；项目
eventually	ɪˈventʃuəli	adv. 最后，终于
ever	ˈevə(r)	adv. 曾经；究竟；永远
every	ˈevri	adj. 每一的，每个的；每隔 … 的
everyone	ˈevriwʌn	pron. 人人；每个人
everything	ˈevriθɪŋ	pron. 每件事物；一切事物
everywhere	ˈevriweə(r)	adv. 到处
evidence	ˈevɪdəns	n. 证据，证明；迹象；明显
exact	ɪɡˈzækt	adj. 精确的；准确的，精密的
exactly	ɪɡˈzæktli	adv. 精确地；正确地；正是；恰好地
examine	ɪɡˈzæmɪn	vt. 调查；考试；检查； [ 计算机 ] 检测
example	ɪɡˈzɑːmp(ə)l	n. 例子；榜样
excellent	ˈeksələnt	adj. 极好的；卓越的；杰出的
except	ɪkˈsept	vt. 不计；把 … 除外
exist	ɪɡˈzɪst	vi. 存在；生存；生活；继续存在
expect	ɪkˈspekt	vt. 预料；期望；指望； [ 口 ] 认为
experience	ɪkˈspɪəriəns	n. 经验；经历；体验
explain	ɪkˈspleɪn	v. 说明；解释
explanation	ˌekspləˈneɪʃ(ə)n	n. 说明，解释；辩解
express	ɪkˈspres	vt. 表达；快递
extra	ˈekstrə	adv. 特别地，非常；另外;n.群众演员
eye	aɪ	n. 眼睛；视力；见解，观点；眼光
face	feɪs	n. 脸；面容；表面；面子；威信；外观
fact	fækt	n. 事实；实际；真相
fail	feɪl	vi. 失败，不及格；衰退；缺乏；破产
fair	feə(r)	adj. 公平的；美丽的，白皙的；晴朗的
fall	fɔːl	vi. 落下；变成；来临；减弱
false	fɔːls	adj. 伪造的；错误的；虚伪的
familiar	fəˈmɪliə(r)	adj. 熟悉的；常见的；亲近的
family	ˈfæməli	n. 家庭；家族；家属；亲属；子女；僚属
famous	ˈfeɪməs	adj. 著名的； [ 口 ] 极好的，非常令人满意的
fast	fɑːst	adj. 快速的，迅速的；紧的，稳固的
fat	fæt	adj. 肥的，胖的；丰满的；油腻的
father	ˈfɑːðə(r)	n. 父亲，爸爸；神父；祖先；前辈
feature	ˈfiːtʃə(r)	n. 容貌；特色，特征；特写或专题节目
February	ˈfebruəri	n.二月(略作Feb)
feel	fiːl	vt. 感觉；试探；触摸；认为
feeling	ˈfiːlɪŋ	n. 感觉，触觉；感情，情绪；同情
few	fjuː	adj. 很少的；几乎没有的
field	fiːld	n. 领域；牧场；旷野；战场；运动场
fight	faɪt	vi. 打架；打仗；搏斗，斗争
figure	ˈfɪɡə(r)	n. 图形；数字；（人的）体形；人物；画像；价格
fill	fɪl	vt. 装满，使充满；满足；堵塞；任职
final	ˈfaɪn(ə)l	adj. 最终的；决定性的；不可更改的
finally	ˈfaɪnəli	adv. 终于；最后；决定性地
find	faɪnd	vt. 发现；认为；感到；获得
fine	faɪn	adj. 好的；优良的；晴朗的；健康的；细小的，精美的
finish	ˈfɪnɪʃ	vt. 完成；结束；用完
fire	ˈfaɪə(r)	n. 火；火灾；炉火；热情；炮火；激情；磨难
first	fɜːst	adv. 第一；首先；优先；宁愿
fit	fɪt	vt. 安装；使 … 适应；使 … 合身；与 … 相符
five	faɪv	n. 五，五个； [ 口 ] 五美元钞票
fix	fɪks	vt. 安装；修理；使固定；准备
flat	flæt	adj. 平坦的；单调的；浅的；扁平的
floor	flɔː(r)	n. 地板，地面；楼层；基底；议员席
fly	flaɪ	vi. 飞；飘扬；驾驶飞机
follow	ˈfɒləʊ	vt. 跟随；追求；遵循；密切注意
food	fuːd	n. 食物；养料
for	fɔː(r)	prep. 为，为了；给；因为；对于；适合于；至于
force	fɔːs	n. 力量；武力；魄力；军队
foreign	ˈfɒrən	adj. 外国的； [ 医 ] 异质的；不相关的；外交的
forget	fəˈɡet	vt. 忘记；忽略
form	fɔːm	n. 形式，形状；形态，外形；表格；方式
formal	ˈfɔːm(ə)l	adj. 正式的；拘谨的；有条理的
forward	ˈfɔːwəd	adj. 早的；向前的；迅速的
four	fɔː(r)	num. 四；四个
free	friː	adj. 自由的，不受约束的；免费的；游离的
freedom	ˈfriːdəm	n. 自由，自主；直率
fresh	freʃ	adj. 新鲜的；无经验的；淡水的；清新的
Friday	ˈfraɪdeɪ	n.星期五
friend	frend	n. 朋友；赞助者；助手
from	frɒm	prep. 来自，从；今后；由于
front	frʌnt	n. 前面；前线；正面
full	fʊl	adj. 完全的，完整的；满的，充满的；丰满的；丰富的；详尽的；完美的
funny	ˈfʌni	adj. 有趣的；奇异的；滑稽的
furthermore	ˌfɜːðəˈmɔː(r)	adv. 此外；而且
gain	ɡeɪn	n. 收获；增加；利润
game	ɡeɪm	n. 游戏；比赛
general	ˈdʒen(ə)rəl	adj. 一般的，普通的；综合的；大体的
get	ɡet	vt. 获得；变成；使得；受到
girl	ɡɜːl	n. 女孩，姑娘；女儿；女朋友
give	ɡɪv	vt. 给；授予；让步；产生；举办
glass	ɡlɑːs	n. 玻璃；镜子；玻璃制品
global	ˈɡləʊb(ə)l	adj. 全球的；球形的；总体的
go	ɡəʊ	vi. 走；趋于；达到；运转
goal	ɡəʊl	n. 目标；终点；球门，得分数
good	ɡʊd	adj. 愉快的；好的；虔诚的；优良的
government	ˈɡʌvənmənt	n. 政府；管辖；政体
great	ɡreɪt	adj. 伟大的，重大的；极好的，好的；主要的
green	ɡriːn	adj. 绿色的；青春的
ground	ɡraʊnd	n. 地面；土地；范围；战场
group	ɡruːp	n. 组；团体
grow	ɡrəʊ	vi. 生长；发展；渐渐变得 …
growth	ɡrəʊθ	n. 生长；增长；发展；种植
guess	ɡes	vt. 猜测；推测；猜中； [ 美口 ] 认为
had	hæd	aux. 已经（用于过去完成时态）
hair	heə(r)	n. 头发；毛发；些微
half	hɑːf	n. 一半；半场；半学年
hand	hænd	n. 手，手艺；指针；插手；帮助
hang	hæŋ	vt. 绞死；悬挂，垂下；使悬而未决；装饰
happen	ˈhæpən	vi. 发生；碰巧；偶然遇到
happy	ˈhæpi	adj. 高兴的；幸福的；巧妙的
hard	hɑːd	adj. 困难的；硬的；猛烈的；确实的；努力的；辛苦的；冷酷无情的；严厉的
has	hæz	vbl.have 的第三人称单数现在式
have	hæv	vt. 有；让；从事；允许；拿
he	hiː	n. 它（雄性动物）；男孩，男人
head	hed	n. 头；上端；最前的部分；头痛；理解力
health	helθ	n. 健康；兴旺；卫生；保健
hear	hɪə(r)	vt. 审理；听说；听到，听
heart	hɑːt	n. 心脏；要点；感情；勇气；心形
heavy	ˈhevi	adj. 沉重的；繁重的，巨大的；阴沉的
hello	həˈləʊ	int. 喂；哈罗
help	help	vt. 帮助；治疗；促进；补救
her	hɜː(r)	pron. 她的；她
here	hɪə(r)	adv. 在这里；此时
herself	hɜːˈself	pron. 她自己（ she 的反身代词）；她亲自
high	haɪ	adj. 高的；高级的；高音调的；崇高的
highly	ˈhaɪli	adv. 非常；高度地；非常赞许地
him	hɪm	pron. 他（宾格）
himself	hɪmˈself	pron. 他自己；他亲自，他本人
his	hɪz	pron. 他的
history	ˈhɪst(ə)ri	n. 历史，历史学；历史记录；来历
hit	hɪt	vt. 袭击；碰撞；打击；偶然发现；伤 … 的感情
hold	həʊld	vt. 持有；保存；拥有；拘留；约束或控制
home	həʊm	n. 家，住宅；家乡；产地；避难所
honestly	ˈɒnɪstli	adv. 公正地；真诚地
hope	həʊp	n. 希望；信心；期望
hospital	ˈhɒspɪt(ə)l	n. 医院
hot	hɒt	adj. 热的；辣的；热情的；激动的；紧迫的
hotel	həʊˈtel	n. 旅馆，饭店；客栈
hour	ˈaʊə(r)	n. 小时；钟头；课时； … 点钟
house	haʊs	n. 住宅；家庭；某种用途的建筑物；机构；议会
how	haʊ	adv. 多么；多少；如何
however	haʊˈevə(r)	adv. 无论如何；不管怎样
huge	hjuːdʒ	adj. 巨大的；庞大的；无限的
human	ˈhjuːmən	adj. 人的；人类的
hundred	ˈhʌndrəd	n. 一百；许多
hurt	hɜːt	vt. 使受伤；使疼痛；使痛心； [ 口 ] 损害
I	aɪ	pron. 我
idea	aɪˈdɪə	n. 主意；概念；想法
identify	aɪˈdentɪfaɪ	vt. 识别；确定；使参与；把 … 看成一样
if	ɪf	conj. （表条件）如果；（表假设）假如；是否；即使
ill	ɪl	adj. 坏的；生病的；邪恶的；不吉利的
image	ˈɪmɪdʒ	n. 影像；肖像；想象；偶像
imagine	ɪˈmædʒɪn	vt. 想像；猜想；臆断
immediate	ɪˈmiːdiət	adj. 直接的；立即的；最接近的
immediately	ɪˈmiːdiətli	adv. 直接地；立即，立刻
impact	ˈɪmpækt	vt. 撞击；冲突；影响；压紧
important	ɪmˈpɔːt(ə)nt	adj. 重要的，重大的；有地位的；有权力的
impossible	ɪmˈpɒsəb(ə)l	adj. 不可能的；不可能存在的；难以忍受的；不真实的
improve	ɪmˈpruːv	vt. 改善，增进；提高 … 的价值
in	ɪn	prep. 在 … 之内；从事于；按照（表示方式）
include	ɪnˈkluːd	vt. 包含，包括
income	ˈɪnkʌm	n. 收入，收益；所得
increase	ɪnˈkriːs	n. 增加，增长；提高
independent	ˌɪndɪˈpendənt	adj. 独立的；单独的；不受约束的；无党派的
indicate	ˈɪndɪkeɪt	vt. 指出；表明；象征；预示
individual	ˌɪndɪˈvɪdʒuəl	adj. 个别的；个人的；独特的
industry	ˈɪndəstri	n. 工业；产业；勤勉
inform	ɪnˈfɔːm	vt. 通知；告诉；报告
information	ˌɪnfəˈmeɪʃ(ə)n	n. 信息，资料；通知；情报；知识
inside	ˌɪnˈsaɪd	n. 内部；里面；内脏；内情
instead	ɪnˈsted	adv. 代替；反而
interest	ˈɪntrəst	n. 兴趣，爱好；利息；趣味；同行
interesting	ˈɪntrəstɪŋ	adj. 有趣的；引起兴趣的，令人关注的
international	ˌɪntəˈnæʃ(ə)nəl	n. 国际比赛；国际性组织
interview	ˈɪntəvjuː	n. 面试，面谈；接见，采访
into	ˈɪntuː	prep. 到 … 里；深入 … 之中；成为 … 状况；进入到 … 之内
introduce	ˌɪntrəˈdjuːs	vt. 介绍；引进；采用；提出
invest	ɪnˈvest	vt. 投资；耗费；授予；覆盖；包围
investment	ɪnˈvestmənt	n. 投资；投入； [ 军 ] 封锁
involve	ɪnˈvɒlv	vt. 包含；牵涉；使陷于；潜心于
is	ɪz	prep.是 [域] Iceland ,冰岛 [军] Internal Security,治安
issue	ˈɪʃuː	n. 流出；发行物；问题；期号
it	ɪt	pron. 它
item	ˈaɪtəm	n. 条款，项目；一则
its	ɪts	pron. 它的
itself	ɪtˈself	pron. 它本身；它自己
January	ˈdʒænjuəri	n.一月(略作Jan)
job	dʒɒb	n. 工作；职业
join	dʒɔɪn	vt. 参加；连接；结合
July	dʒʊˈlaɪ	n.七月(略作Jul)
jump	dʒʌmp	n. 跳跃；暴涨；惊跳
June	dʒuːn	n.六月(略作Jun)
just	dʒʌst	adv. 正好，恰好；只是，仅仅；刚才，刚刚；实在；刚要
keep	kiːp	vt. 保持；遵守；经营；饲养
key	kiː	n. 钥匙；关键；（打字机等的）键
kill	kɪl	vt. 杀死；扼杀；使终止；抵消
kind	kaɪnd	n. 种类；性质
know	nəʊ	vt. 知道；认识；懂得
knowledge	ˈnɒlɪdʒ	n. 知识，学问；知道，认识； [ 古 ] 学科
land	lænd	n. 陆地；地面；国土
language	ˈlæŋɡwɪdʒ	n. 语言；语言文字；表达能力
large	lɑːdʒ	adj. 大的；广博的；多数的
largely	ˈlɑːdʒli	adv. 主要地；大量地；大部分
last	lɑːst	n. 末尾，最后；上个；鞋楦（做鞋的模型）
late	leɪt	adj. 迟的；晚的；已故的；最近的
later	ˈleɪtə(r)	adv. 随后；稍后；后来
laugh	lɑːf	n. 笑；引人发笑的事或人
law	lɔː	n. 法律；法学；诉讼；法治；司法界；规律
lead	liːd	n. 铅；领导；导线；榜样；石墨
leader	ˈliːdə(r)	n. 领导者；首领；指挥者
learn	lɜːn	vt. 学习；认识到；得知
leave	liːv	vt. 离开；留下；委托；遗忘
legal	ˈliːɡ(ə)l	adj. 法定的；法律的；合法的
less	les	adv. 较少地；较小地；更小地
let	let	vt. 允许，让；出租；假设；妨碍
letter	ˈletə(r)	n. 字母，文字；信；字面意义；证书；文学，学问
level	ˈlev(ə)l	n. 水平；水平面；标准
lie	laɪ	vi. 说谎；位于；躺；展现
life	laɪf	n. 寿命；生活，生存
light	laɪt	n. 光，光亮；灯
like	laɪk	vt. 喜欢；愿意；想
likely	ˈlaɪkli	adj. 很可能的；合适的；有希望的
limited	ˈlɪmɪtɪd	adj. 有限的
line	laɪn	n. 绳；排；路线，航线
list	lɪst	n. 列表；目录；清单
listen	ˈlɪs(ə)n	vi. 听，倾听；听从，听信
literature	ˈlɪtrətʃə(r); ˈlɪtərətʃə	n. 文学；文艺；文献；著作
little	ˈlɪt(ə)l	adj. 小的；短暂的；很少的；小巧可爱的
live	lɪv	adj. 活的；实况转播的；精力充沛的；生动的
local	ˈləʊk(ə)l	n. 当地居民；本地新闻；局部
location	ləʊˈkeɪʃn	n. 位置（形容词 locational ）；地点；外景拍摄场地
lonely	ˈləʊnli	adj. 寂寞的；偏僻的
long	lɒŋ	n. 长时间；长音节
look	lʊk	vt. 看；期待；注意；面向；看上去像
lose	luːz	vt. 使沉溺于；浪费；遗失；使迷路；错过
lot	lɒt	n. 抽签；命运；一堆；一块地；份额
loud	laʊd	adj. 大声的，高声的；不断的；喧吵的
love	lʌv	n. 恋爱；酷爱；亲爱的；喜爱的事物
low	ləʊ	adj. 低的，浅的；粗俗的；卑贱的；消沉的
lucky	ˈlʌki	adj. 幸运的；侥幸的
main	meɪn	n. 主要部分，要点；体力；总管道
mainly	ˈmeɪnli	adv. 主要地，大体上
major	ˈmeɪdʒə(r)	adj. 主要的；主修的；重要的；较多的
make	meɪk	vt. 制造；构成；使得；获得；引起；进行；布置，准备，整理；认为；安排；形成
man	mæn	n. 雇工；男人；人；人类；丈夫
manage	ˈmænɪdʒ	vt. 管理；经营；设法；控制
management	ˈmænɪdʒmənt	n. 管理；管理部门；经营手段；管理人员；操纵
many	ˈmeni	pron. 许多；许多人
march	mɑːtʃ	n. 进行曲；行军，进军；示威游行
mark	mɑːk	n. 马克；符号；痕迹；标志
market	ˈmɑːkɪt	n. 集市；市场；商店；销路；股票市场；行情；市面
material	məˈtɪəriəl	adj. 物质的，实质性的；重要的；肉体的
matter	ˈmætə(r)	n. 物质；事件；原因
may	may	aux. 可能，可以；愿意
maybe	ˈmeɪbi	adv. 大概；也许；可能
me	miː	pron. 我（宾格）
mean	miːn	adj. 低劣的；平均的；卑鄙的
meanwhile	ˈmiːnwaɪl	adv. 同时，其间
measure	ˈmeʒə(r)	n. 措施；程度；测量；尺寸
media	ˈmiːdiə	n. 媒体；媒质（ medium 的复数）；血管中层；浊塞音；中脉
medical	ˈmedɪk(ə)l	adj. 医学的；内科的；药的
meet	miːt	vt. 遇见；满足；对付
meeting	ˈmiːtɪŋ	n. 会议；集会；会见；汇合点
member	ˈmembə(r)	n. 成员；会员；议员
memory	ˈmeməri	n. 记忆，记忆力；回忆；内存，存储器
mental	ˈment(ə)l	adj. 精神的；脑力的(mental  labor)；疯的
mention	ˈmenʃ(ə)n	vt. 说起；提到，谈到；提及，论及
message	ˈmesɪdʒ	n. 消息；预言；启示；广告词；差使
method	ˈmeθəd	n. 方法；条理； [ 计 ] 类函数
midnight	ˈmɪdnaɪt	n. 午夜，半夜 12 点钟
might	maɪt	n. 力量；势力；威力
military	ˈmɪlətri	adj. 军事的；军人的；适于战争的
million	ˈmɪljən	n. 百万；无数
mind	maɪnd	n. 智力；理智，精神；意见；记忆力
minute	ˈmɪnɪt	n. 片刻，一会儿；分，分钟；备忘录，笔记；会议记录
miss	mɪs	vt. 漏掉；错过；想念
model	ˈmɒd(ə)l	n. 模型；模范；模特儿；典型；样式
modern	ˈmɒd(ə)n	adj. 现代的，近代的；时髦的
moment	ˈməʊmənt	n. 瞬间；重要时刻；指定时刻；重要，契机
Monday	ˈmʌndeɪ	n.星期一
money	ˈmʌni	n. 钱；货币；财富
month	mʌnθ	n. 月，一个月的时间
more	mɔː(r)	adv. 更多；更大程度地；此外
morning	ˈmɔːnɪŋ	n. 早晨； [ 诗 ] 初期；黎明
most	məʊst	adv. 最；最多；非常，极其；几乎
mostly	ˈməʊstli	adv. 主要地；多半地；通常
mother	ˈmʌðə(r)	n. 母亲；大娘；女修道院院长
mouth	maʊθ	n. 口，嘴；河口
move	muːv	n. 迁居；步骤；移动
movement	ˈmuːvmənt	n. 运动；乐章；活动；运转
much	mʌtʃ	adv. 非常，很
music	ˈmjuːzɪk	n. 音乐，乐曲
must	mʌst	aux. 必须，一定；很可能；可以，应当
my	maɪ	pron. 我的
myself	maɪˈself	pron. 我自己；我亲自；我的正常的健康状况和正常情绪
name	neɪm	n. 名称，名字；姓名；名誉
nation	ˈneɪʃ(ə)n	n. 国家；民族；国民
national	ˈnæʃ(ə)nəl	adj. 国家的；国民的；民族的；国立的
natural	ˈnætʃ(ə)rəl	adj. 自然的；不做作的；天生的；物质的
nature	ˈneɪtʃə(r)	n. 自然；性质；种类；本性
near	nɪə(r)	adj. 近的；近似的；亲近的
nearly	ˈnɪəli	adv. 差不多，几乎；密切地
necessary	ˈnesəsəri	adj. 必要的；必然的；必需的
need	niːd	n. 需要，要求；必要之物；缺乏
negative	ˈneɡətɪv	adj. 消极的；否定的；负的；阴性的
neither	ˈnaɪðə(r)	conj. 既不；也不
network	ˈnetwɜːk	n. 网络；网状物；广播网
never	ˈnevə(r)	adv. 决不；从未
nevertheless	ˌnevəðəˈles	adv. 然而，不过；虽然如此
new	njuː	adj. 新的，新鲜的；更新的；初见的
news	njuːz	n. 新闻，消息；新闻报导
next	nekst	adv. 其次；然后；下次
nice	naɪs	adj. 精密的；美好的；细微的；和蔼的
night	naɪt	n. 夜晚，晚上；黑暗，黑夜
nine	naɪn	n. 九，九个
no	nəʊ	adv. 不
nobody	ˈnəʊbədi	pron. 无人，没有人；没有任何人
none	nʌn	pron. 没有人；没有任何东西；一个也没有
noon	nuːn	n. 中午；正午；全盛期
normal	ˈnɔːm(ə)l	adj. 正常的；正规的，标准的
north	nɔːθ	n. 北，北方
not	nɒt	adv. 不（用于否定句）
note	nəʊt	n. 笔记；注解；便笺；照会；音符；调子；票据；纸币
nothing	ˈnʌθɪŋ	neg. 没什么；毫不
notice	ˈnəʊtɪs	n. 通知，布告；注意；公告
November	nəʊˈvembə(r)	n.十一月(略作Nov)
now	naʊ	adv. 现在；立刻；如今
nowhere	ˈnəʊweə(r)	adv. 无处；任何地方都不；毫无结果
number	ˈnʌmbə(r)	n. 号码；数字；数；算术；（杂志等的）期
obvious	ˈɒbviəs	adj. 明显的；显著的；平淡无奇的
October	ɒkˈtəʊbə(r)	n.十月(略作Oct)
of	ɒv	prep. 属于；关于； … 的；由 … 组成的
off	ɒf	prep. 离开；脱落
offer	ˈɒfə(r)	vt. 提供；试图；出价
office	ˈɒfɪs	n. 办公室；营业处；官职；政府机关
officer	ˈɒfɪsə(r)	n. 军官，警官；公务员，政府官员；船长
official	əˈfɪʃ(ə)l	adj. 正式的；官方的；公务的
often	ˈɒf(ə)n	adv. 常常，时常
Oh	əʊ	int. 哦；哎呀（表示惊讶或恐惧等）
okay	əʊˈkeɪ	adv. 可以；对；很好地
old	əʊld	adj. 年老的；陈旧的，古老的
on	ɒn	adv. 向前地；继续着；作用中，行动中
once	wʌns	adv. 一次；曾经
one	wʌn	pron. 任何人；一个人
only	ˈəʊnli	adv. 只，仅仅；不料
open	ˈəʊpən	adj. 营业着的；敞开的；空旷的；公开的；坦率的
operation	ˌɒpəˈreɪʃ(ə)n	n. 操作；手术；经营；运算
opportunity	ˌɒpəˈtjuːnəti	n. 时机，机会
option	ˈɒpʃ(ə)n	n. 选择权；选项； [ 经 ] 买卖的特权
or	ɔː(r)	conj. 或，或者；还是
order	ˈɔːdə(r)	n. 命令；顺序；定单；规则
organization	ˌɔːɡənaɪˈzeɪʃn	n. 组织；机构；团体；体制
organize	ˈɔːɡənaɪz	vt. 组织；使有系统化；给予生机；组织成立工会等
original	əˈrɪdʒən(ə)l	n. 原物；原作；原型；原件
other	ˈʌðə(r)	adj. 其他的，另外的
otherwise	ˈʌðəwaɪz	adv. 另外；在其他方面；否则
our	ˈaʊə(r)	pron. 我们的
ourselves	ɑːˈselvz	pron. 我们自己；我们亲自
out	aʊt	adv. 在外；出现；出声地；不流行地；出局
outside	ˌaʊtˈsaɪd	adj. 外面的，外部的；外来的
over	ˈəʊvə(r)	adv. 结束；越过；从头到尾
own	əʊn	vt. 拥有；承认
page	peɪdʒ	n. 页；大事件，时期；记录；男侍者
paper	ˈpeɪpə	n. 纸；文件；报纸；论文
parent	ˈpeərənt	n. 父母亲；父亲（或母亲）；根源
part	pɑːt	n. 部分；角色；零件
particular	pəˈtɪkjələ(r)	adj. 特别的；独有的；挑剔的；详细的
partner	ˈpɑːtnə(r)	n. 合伙人；伙伴；配偶
party	ˈpɑːti	n. 聚会，派对；政党，党派； [ 律 ] 当事人
pass	pɑːs	n. 及格；经过；护照；途径； [ 体 ] 传球； vi. 经过；传递；变化；终止； vt. 通过；经过；传递
passage	ˈpæsɪdʒ	n. 通路；走廊；一段（文章）
past	pɑːst	n. 过去；往事
patient	ˈpeɪʃ(ə)nt	adj. 有耐性的，能容忍的
pattern	ˈpæt(ə)n	n. 模式；样品；图案
pay	peɪ	vt. 支付，付；偿还，补偿
people	ˈpiːp(ə)l	n. 人；人类；民族；公民
per	pə(r)	prep. 每；每一；经；按照
perfect	ˈpɜːfɪkt	adj. 完美的；最好的；精通的
perform	pəˈfɔːm	vt. 执行；完成；演奏
performance	pəˈfɔːməns	n. 性能；表演；执行；绩效
perhaps	pəˈhæps	adv. 也许；可能
period	ˈpɪəriəd	n. 周期，期间；时期；课时；月经
permanent	ˈpɜːmənənt	adj. 永久的，永恒的；不变的
permission	pəˈmɪʃ(ə)n	n. 允许，许可
person	ˈpɜːs(ə)n	n. 人； [ 语法 ] 人称；身体；容貌，外表
personal	ˈpɜːsən(ə)l	adj. 个人的；身体的；亲自的
phone	fəʊn	n. 电话；耳机，听筒
photograph	ˈfəʊtəɡrɑːf	vt. 为 … 拍照；使深深印入
physical	ˈfɪzɪk(ə)l	adj. 身体的(physical labor)；物质的；物理的
pick	pɪk	vi. 挑选；采摘；挖； vt. 拾取；精选；采摘；掘； n. 选择；鹤嘴锄；挖； [ 篮球 ] 掩护
picture	ˈpɪktʃə(r)	n. 照片，图画；景色；影片；化身
place	pleɪs	n. 地方；住所；座位
plain	pleɪn	adj. 平的；朴素的；简单的；清晰的
plan	plæn	n. 平面图；计划
plant	plɑːnt	n. 工厂，车间；植物；庄稼；设备
play	pleɪ	vt. 演奏；播放；游戏；扮演；同 … 比赛
player	ˈpleɪə(r)	n. 演员；演奏者，表演者；运动员，比赛者；游戏者，做游戏的人
pleasant	ˈplez(ə)nt	adj. 令人愉快的，舒适的；讨人喜欢的，和蔼可亲的
please	pliːz	vt. 使高兴，使满意；使喜欢
point	pɔɪnt	n. 要点；尖端；得分；标点
police	pəˈliːs	n. 警察，警方；治安
policy	ˈpɒləsi	n. 政策，方针；保险单
political	pəˈlɪtɪk(ə)l	adj. 政治的；党派的
poor	pɔː(r)	adj. 可怜的；贫穷的；贫乏的；卑鄙的
popular	ˈpɒpjələ(r)	adj. 流行的，通俗的；受欢迎的；大众的；普及的
population	ˌpɒpjuˈleɪʃ(ə)n	n. 人口；种群，群体；全体居民
position	pəˈzɪʃ(ə)n	n. 位置，方位；职位，工作；姿态；站位； vt. 安置；把 … 放在适当位置
positive	ˈpɒzətɪv	adj. 确定的，肯定的；积极的；正的，阳性的；绝对的；实际的，真实的
possible	ˈpɒsəb(ə)l	adj. 可能的；合理的；合适的
potential	pəˈtenʃ(ə)l	n. 可能性；潜能；电势
power	ˈpaʊə(r)	n. 能力；力量；功率；幂；势力；政权
powerful	ˈpaʊəf(ə)l	adj. 强大的；强有力的
practical	ˈpræktɪkl	adj. 实际的；实用性的
practice	ˈpræktɪs	n. 练习；实践；惯例
prepare	prɪˈpeə(r)	vt. 准备；使适合；装备；起草
present	ˈprez(ə)nt	vt. 呈现；介绍；提出；赠送
press	pres	vt. 压；按；紧抱；逼迫
pressure	ˈpreʃə(r)	n. 压力；压迫，压强
pretty	ˈprɪti	adj. 漂亮的；可爱的；优美的
prevent	prɪˈvent	vt. 预防，防止；阻止
price	praɪs	n. 价格；代价；价值
primary	ˈpraɪməri	adj. 主要的；初级的；基本的
private	ˈpraɪvət	adj. 私人的；私有的；私下的
probably	ˈprɒbəbli	adv. 大概；或许；很可能
problem	ˈprɒbləm	n. 难题；引起麻烦的人
process	ˈprəʊses	vt. 处理；加工
produce	prəˈdjuːs	vt. 生产；引起；创作
product	ˈprɒdʌkt	n. 产品；结果；作品；乘积
production	prəˈdʌkʃ(ə)n	n. 成果；产品；生产；作品
professional	prəˈfeʃən(ə)l	adj. 职业的；专业的；职业性的
profit	ˈprɒfɪt	n. 利润；利益
program	ˈprəʊɡræm	n. 程序；计划；大纲
progress	ˈprəʊɡres	n. 进步，发展；前进
project	ˈprɒdʒekt; prəˈdʒekt	vi. 设计；表达；计划；投射
promote	prəˈməʊt	vt. 促进；提升；发扬；推销
proper	ˈprɒpə(r)	adj. 适当的；特有的；本身的；正派的
property	ˈprɒpəti	n. 财产；性质，性能；所有权
protect	prəˈtekt	vt. 保护，防卫；警戒
prove	pruːv	vt. 证明；检验；显示
provide	prəˈvaɪd	vt. 规定；提供；准备；装备
public	ˈpʌblɪk	adj. 公用的；公众的；公立的；政府的
pull	pʊl	vt. 拉；拖；拔
purpose	ˈpɜːpəs	n. 目的；用途；意志
push	pʊʃ	vt. 推动；推行；逼迫；增加
put	pʊt	vt. 放；表达；移动；安置；赋予
quality	ˈkwɒləti	n. 质量，品质；特性；才能
quantity	ˈkwɒntəti	n. 量，数量；总量；大量
question	ˈkwestʃən	n. 问题，疑问；询问；疑问句
quick	kwɪk	adj.迅速的，快的；持续时间很短的，短暂的
quickly	ˈkwɪkli	adv. 很快地；迅速地
quiet	ˈkwaɪət	adj. 安静的；安定的；温顺的；不动的
quite	kwaɪt	adv. 很；相当；完全
race	reɪs	n. 种族，人种；家庭，门弟；属，种
raise	reɪz	vt. 提高；筹集；养育；升起
range	reɪndʒ	n. 范围；山脉；排；幅度
rate	reɪt	n. 比率，率；速度；等级；价格
rather	ˈrɑːðə(r)	adv. 宁可，宁愿；相当
reach	riːtʃ	vi. 达到；延伸；传开；伸出手
reaction	riˈækʃn	n. 反应，感应；反作用；反动，复古
read	riːd	vt. 阅读；读懂，理解
ready	ˈredi	adj. 准备好；情愿的；现成的；迅速的；快要 … 的
real	ˈriːəl	adj. 真实的；实在的；实际的
realize	ˈriːəlaɪz	vt. 实现；了解；将某物卖得；认识到
really	ˈriːəli	adv. 实际上，事实上；真正地，真实地；真的吗？（表语气）
reason	ˈriːz(ə)n	n. 理由；理性；动机
receive	rɪˈsiːv	vt. 收到；接待；接纳
recent	ˈriːs(ə)nt	adj. 最近的；近代的
recently	ˈriːs(ə)ntli	adv. 最近；新近
reception	rɪˈsepʃ(ə)n	n. 接待；接收；招待会；感受；反应
recognition	ˌrekəɡˈnɪʃn	n. 承认，认出；赞誉；重视；识别；公认
recognize	ˈrekəɡnaɪz	vt. 承认；认出，识别
record	ˈrekɔːd	vt. 记录，记载；将 ... 录音；标明
red	red	n. 红色，红颜料；赤字
reduce	rɪˈdjuːs	vt. 减少；降低；把 … 分解；使处于
reference	ˈrefrəns	n. 参考，参照；涉及，提及；参考书目；介绍信；证明书
reflect	rɪˈflekt	vt. 反射，照出；反省；反映
regular	ˈreɡjələ(r)	adj. 整齐的；定期的；有规律的；合格的
relate	rɪˈleɪt	vt. 叙述；使 …有联系
relationship	rɪˈleɪʃ(ə)nʃɪp	n. 关联；关系
relatively	ˈrelətɪvli	adv. 相对地，比较地；相当地
release	rɪˈliːs	vt. 释放；发射；让与；允许发表
relevant	ˈreləv(ə)nt	adj. 有关的；中肯的；有重大作用的
religious	rɪˈlɪdʒəs	adj. 宗教的；虔诚的；严谨的；修道的
remain	rɪˈmeɪn	vi. 保持；依然；留下；剩余；逗留；残存
remember	rɪˈmembə(r)	vt. 记得；牢记；纪念；代 … 问好
remote	rɪˈməʊt	adj. 遥远的；偏僻的；疏远的
replace	rɪˈpleɪs	vt. 取代，代替；替换，更换；归还，偿还；把 … 放回原处
report	rɪˈpɔːt	n. 报告；报道；成绩单
represent	ˌreprɪˈzent	vt. 表现；描绘；代表；回忆；再赠送
require	rɪˈkwaɪə(r)	vt. 要求；需要；命令
requirement	rɪˈkwaɪəmənt	n. 要求；必需品；必要条件
research	rɪˈsɜːtʃ	n. 研究；调查
resource	rɪˈsɔːs	n. 资源，财力；办法；智谋
respond	rɪˈspɒnd	vi. 回答；作出反应；承担责任
response	rɪˈspɒns	n. 响应；反应；回答
responsible	rɪˈspɒnsəb(ə)l	adj. 有责任的；负责的，可靠的
rest	rest	vt. 使休息，使轻松；把 … 寄托于
result	rɪˈzʌlt	n. 结果；成绩；答案；比赛结果
return	rɪˈtɜːn	vt. 返回；报答； n. 返回；归还；回球； vi. 返回；报答； adj. 报答的；回程的；返回的
reveal	rɪˈviːl	vt. 揭露；显示；透露；泄露
revenue	ˈrevənjuː	n. 税收，国家的收入；收益
review	rɪˈvjuː	n. 评论；回顾；复习；检讨；检阅
rich	rɪtʃ	adj. 富有的；肥沃的；昂贵的
ride	raɪd	vi. 骑马；乘车；漂浮；依靠
right	raɪt	adj. 正确的；右方的；直接的
rise	raɪz	vi. 上升；起立；高耸；增强
road	rəʊd	n. 道路；公路，马路；手段
role	rəʊl	n. 角色；任务
roll	rəʊl	vt. 滚动，转动；卷；辗
room	ruːm	n. 房间；空间；机会；余地
rough	rʌf	adj. 粗糙的；粗野的；未经加工的；粗略的；艰苦的
round	raʊnd	n. 圆形物；一回合；圆；循环
rule	ruːl	vi. 统治；裁定；管辖
run	rʌn	vi. 奔跑；运转；经营
sad	sæd	adj. 悲哀的，令人悲痛的；难过的；凄惨的，阴郁的（形容颜色）
safe	seɪf	adj. 安全的；可靠的；平安的
same	seɪm	adj. 相同的；同一的；无变化的；上述的（通常与 the 连用）
sample	ˈsɑːmp(ə)l	vt. 取样；抽样检查；尝试
Saturday	ˈsætədeɪ	n.星期六
save	seɪv	vt. 节省；保存；解救；储蓄
say	seɪ	vt. 讲；说明；指明；假设；例如；声称
scale	skeɪl	n. 刻度；比例；数值范围；天平；规模；鳞
school	skuːl	n. 学校；学派；学院；鱼群
science	ˈsaɪəns	n. 科学；理科；技术；学科
sea	siː	n. 海洋；海；许多；大量
second	ˈsekənd	n. 秒；瞬间；二等品；第二名
section	ˈsekʃ(ə)n	n. 章节；地区；截面；部门
security	sɪˈkjʊərəti	n. 安全；抵押品；证券；保证
see	siː	vt. 看见；领会；理解
seem	siːm	vi. 似乎；像是；装作
sell	sel	vt. 销售；出卖；推销；欺骗
send	send	vt. 发送，寄；派遣；发射；使进入
sense	sens	n. 感觉，官能；观念；理智；道理
September	sepˈtembə(r)	n.九月(略作Sep)
series	ˈsɪəriːz	n. 系列，连续；丛书(a series of books)； [ 电 ] 串联； [ 数 ] 级数
serious	ˈsɪəriəs	adj. 严肃的，严重的；认真的；庄重的；危急的
seriously	ˈsɪəriəsli	adv. 认真地；严重地，严肃地
serve	sɜːv	vt. 招待，供应；为 … 服务；对 … 有用；可作 … 用
service	ˈsɜːvɪs	n. 服务，服侍；服役；仪式
set	set	n. 集合；一套；布景；装置；趋势
seven	ˈsev(ə)n	num. 七个，七
several	ˈsevrəl	adj. 几个的；各自的
shall	ʃæl	aux. 将；必须；会；应
share	ʃeə(r)	vt. 分享，分担；分配
sharp	ʃɑːp	adj. 急剧的；锋利的；刺耳的；敏捷的；强烈的
she	ʃiː	pron. 她（主格）；它（用来指雌性动物或国家、船舶、地球、月亮等）
shoot	ʃuːt	vt. 拍摄；给 … 注射；射击，射中；发芽；使爆炸
short	ʃɔːt	adj. 短的；不足的；急速的；唐突的
should	ʃʊd	aux. 应该；将要；可能；就
show	ʃəʊ	vt. 显示；演出；展出；说明
sick	sɪk	n. 病人
side	saɪd	n. 方面；侧面；旁边
sign	saɪn	n. 符号；记号；迹象；手势
significant	sɪɡˈnɪfɪkənt	adj. 重大的；有意义的；有效的；值得注意的；意味深长的
similar	ˈsɪmələ(r)	adj. 相似的
simple	ˈsɪmp(ə)l	adj. 简单的；单纯的；天真的
simply	ˈsɪmpli	adv. 简单地；朴素地；简直；仅仅；坦白地
since	sɪns	conj. 既然；因为；由于；自 … 以来；自 … 以后
sing	sɪŋ	vi. 唱歌；鸣叫；歌颂；呼号
single	ˈsɪŋɡ(ə)l	adj. 单一的；单身的；单程的
sit	sɪt	vi. 坐；位于
situation	ˌsɪtʃuˈeɪʃ(ə)n	n. 位置；形势；情况；处境
six	sɪks	num. 六，六个
size	saɪz	n. 大小；尺寸
sleep	sliːp	vi. 睡，睡觉
slow	sləʊ	adj. 慢的；减速的；迟钝的
small	smɔːl	adj. 少的，小的；不重要的；几乎没有的；微弱的；幼小的
smile	smaɪl	vi. 微笑
so	səʊ	adv. 如此，这么；确是如此
social	ˈsəʊʃ(ə)l	adj. 社会的，社交的；群居的
society	səˈsaɪəti	n. 社会；社交界；交往；社团
soft	sɒft	adj. 温柔的，温和的；软的，柔软的；笨的；软弱的
solution	səˈluːʃ(ə)n	n. 解决方案；溶液；溶解；解答
solve	sɒlv	vt. 解决；解答；溶解
some	sʌm	adj. 一些；大约；某一
somebody	ˈsʌmbədi	n. 大人物；重要人物
somehow	ˈsʌmhaʊ	adv. 莫明其妙地；以某种方法
something	ˈsʌmθɪŋ	pron. 某事；某物
sometimes	ˈsʌmtaɪmz	adv. 有时，间或
somewhere	ˈsʌmweə(r)	adv. 在某处；到某处
soon	suːn	adv. 快；立刻；不久，一会儿；宁愿
sorry	ˈsɒri	adj. 对不起的，抱歉的；遗憾的
sound	saʊnd	vt. 使发声；听（诊）；测量，测 … 深；宣告；试探
south	saʊθ	n. 南方，南边；南部
space	speɪs	n. 空间；距离；太空
speak	spiːk	vi. 说话；演讲；陈述；表明
special	ˈspeʃ(ə)l	n. 专车；特刊；特价商品；特色菜；特使，特派人员
specific	spəˈsɪfɪk	adj. 特殊的，特定的；明确的；详细的；具有特效的
speed	spiːd	vi. 加速，迅速前行；超速，加速；兴隆
spend	spend	vt. 花费；度过，消磨（时光）；用尽；浪费
sport	spɔːt	n. 运动；运动会；游戏；娱乐；玩笑
spread	spred	vi. 传播；伸展
spring	sprɪŋ	n. 春天；弹簧；泉水；跳跃；活力
staff	stɑːf	n. 职员；支撑；参谋；棒
stand	stænd	vi. 位于；站立；停滞
star	stɑː(r)	n. 星，恒星；明星；星形物
start	stɑːt	vt. 开始；启动
state	steɪt	n. 情形；国家；州
station	ˈsteɪʃ(ə)n	n. 站；地位；身分；驻地
stay	steɪ	vi. 停留；暂住；停下；坚持
step	step	n. 步，脚步；步伐；步骤；梯级
still	stɪl	adv. 更；仍然；静止地
stop	stɒp	vt. 堵塞；停止；断绝
story	ˈstɔːri	n. 故事；来历；新闻报道；小说； [ 口 ] 假话
strange	streɪndʒ	adj. 陌生的；奇怪的；外行的
strategy	ˈstrætədʒi	n. 战略，策略
street	striːt	n. 街道
strength	streŋθ	n. 力量；力气；长处；兵力
stress	stres	n. 压力；紧张；重要性；强调；重读
strong	strɒŋ	adj. 强壮的；擅长的；坚强的；牢固的
structure	ˈstrʌktʃə(r)	n. 构造；结构；建筑物
student	ˈstjuːd(ə)nt	n. 学生；学者
study	ˈstʌdi	n. 学习，研究；学问；课题；书房
subject	ˈsʌbdʒɪkt	n. 主题；科目；国民；主语
succeed	səkˈsiːd	vi. 成功；继承；继任；兴旺
success	səkˈses	n. 成功，成就；胜利；大获成功的人或事物
successful	səkˈsesf(ə)l	adj. 成功的；一帆风顺的
such	sʌtʃ	adj. 这样的，如此的
suddenly	ˈsʌd(ə)nli	adv. 忽然；突然地
suggest	səˈdʒest	vt. 提议，建议；启发；使人想起
summer	ˈsʌmə(r)	n. 夏季；全盛时期
Sunday	ˈsʌndeɪ	n.星期日
supply	səˈplaɪ	n. 供给，补给；供应品
support	səˈpɔːt	vt. 支持，支撑，支援；赡养，供养；扶持，帮助
sure	ʃʊə(r)	adj. 可靠的；必定的；确信的
surface	ˈsɜːfɪs	n. 表面；外观；表层
survey	ˈsɜːveɪ	n. 调查；测量；纵览；审视
sweet	swiːt	adj. 甜的；悦耳的；芳香的；亲切的； n. 糖果；乐趣；芳香；宝贝；（俚）酷毙了
system	ˈsɪstəm	n. 系统；制度，体制；方法
table	ˈteɪb(ə)l	n. 桌子；表格；平地层
take	teɪk	vt. 拿，取；接受；采取；吃
tax	tæks	vt. 向 … 课税；使负重担
teach	tiːtʃ	vt. 教；教授；教导
teacher	ˈtiːtʃə(r)	n. 教师；导师
team	tiːm	n. 队；组
technology	tekˈnɒlədʒi	n. 工艺；术语；技术
tell	tel	vt. 告诉，说；吩咐；辨别；断定
ten	ten	num. 十个，十
term	tɜːm	n. 学期；术语；条款；期限
terrible	ˈterəb(ə)l	adj. 可怕的；很糟的；令人讨厌的
test	test	n. 检验；试验
than	ðæn	conj. 比（用于形容词、副词的比较级之后）；除 … 外（用于 other 等之后）；与其 … （用于 rather 等之后）；一 … 就（用于 no sooner 等之后）
thank	θæŋk	vt. 感谢
that	ðæt	pron. 那；那个
the	ðə	art. 这；那； adv. 更加（用于比较级，最高级前）
their	ðeə(r)	pron. 他们的，她们的；它们的
them	ðem; ðəm	pron. 他们；她们；它们
themselves	ðəmˈselvz	pron. 他们自己；他们亲自
then	ðen	adv. 当时；然后；那么；于是；此外
theory	ˈθɪəri	n. 理论；原理；学说；推测
there	ðeə(r)	adv. 在那里；在那点上；在那边
therefore	ˈðeəfɔː(r)	adv. 所以；因此
these	ðiːz	pron. 这些
they	ðeɪ	pron. 他们；她们；它们
thick	θɪk	n. 最拥挤部份；活动最多部份；事物的粗大浓密部份
thin	θɪn	adj. 薄的；瘦的；稀薄的；微弱的
think	θɪŋk	vt. 想；认为；想像；想起；打算
third	θɜːd	num. 第三；三分之一
this	ðɪs	pron. 这；这个；这里
those	ðəʊz	adj. 那些的
though	ðəʊ	adv. 可是，虽然；然而；不过
thousand	ˈθaʊz(ə)nd	n. 一千；一千个；许许多多
three	θriː	n. 三，三个
through	θruː	prep. 穿过；通过；凭借
throw	θrəʊ	vt. 抛；投；掷
Thursday	ˈθɜːzdeɪ	n.星期四
thus	ðʌs	adv. 因此；这样；从而；如此
tight	taɪt	adj. 紧的；绷紧的；没空的；密封的；严厉的；麻烦的；吝啬的
time	taɪm	n. 时间；次数；时代；节拍；倍数
tired	ˈtaɪəd	adj. 疲倦的；厌倦的，厌烦的
to	tuː	adv. 向前；（门等）关上
today	təˈdeɪ	adv. 今天；现今
together	təˈɡeðə(r)	adv. 一起；总共；同时；连续地；相互
tomorrow	təˈmɒrəʊ	n. 明天；未来
too	tuː	adv. 也；太；非常；还；过度；很
total	ˈtəʊt(ə)l	adj. 全部的；整个的；完全的
touch	tʌtʃ	vt. 接触；触动；使轻度受害
toward	təˈwɔːd	prep. 向；对于；为了；接近
trade	treɪd	n. 贸易，交易；行业；职业
train	treɪn	n. 火车；行列；长队；裙裾
transfer	trænsˈfɜː(r)	n. 转移；转让；过户；换乘(地铁、公交)
transport	ˈtrænspɔːt	n. 运输；运输机；狂喜；流放犯
travel	ˈtræv(ə)l	vi. 旅行；步行；行进； [ 口 ] 交往
treat	triːt	vt. 对待；治疗；探讨；视为
treatment	ˈtriːtmənt	n. 治疗，疗法；处理；对待
trial	ˈtraɪəl	n. 试验；磨炼；审讯；努力
true	truː	adj. 真实的；正确的
trust	trʌst	n. 信任，信赖； [ 经济学 ] 托拉斯；责任
truth	truːθ	n. 事实；真理；实质；诚实
try	traɪ	vt. 试验；审判；考验；试图，努力
Tuesday	ˈtjuːzdeɪ	n.星期二
turn	tɜːn	vt. 转动，使旋转；转弯；兑换；翻过来
two	tuː	n. 两个
type	taɪp	n. 类型，品种；样式；模范
typical	ˈtɪpɪk(ə)l	adj. 典型的；特有的；象征性的
under	ˈʌndə(r)	prep. 低于，少于；在 ... 之下
understand	ˌʌndəˈstænd	vt. 懂；理解；获悉；推断；省略
unique	juˈniːk	adj. 唯一的，独一无二的；独特的，稀罕的
unit	ˈjuːnɪt	n. 单位，单元；部件；装置；部队
unless	ənˈles	conj. 除非，如果不
until	ʌnˈtɪl	conj. 在 … 以前；直到 … 时
up	ʌp	adv. 向上；上涨；起来
upper	ˈʌpə(r)	adj. 上面的，上部的；较高的
us	ʌs	pron. 我们
use	juːz	n. 使用；用途；发挥
used	juːst	adj. 习惯的；二手的，使用过的
useful	ˈjuːsf(ə)l	adj. 有用的，有益的；有帮助的
usual	ˈjuːʒuəl	adj. 通常的，惯例的；平常的
usually	ˈjuːʒuəli	adv. 通常，经常
value	ˈvæljuː	n. 价值；价格；重要性；值；确切涵义
various	ˈveəriəs	adj. 各种各样的；多方面的
vehicle	ˈviːəkl	n. 车辆（总称）；交通工具；运载工具；媒介物；工具；传播媒介
version	ˈvɜːʃ(ə)n	n. 译文；版本； [ 医 ] 倒转术
very	ˈveri	adj. 十足的；恰好是，正是；甚至；特有的
view	vjuː	n. 风景；意见；视野；观察
visit	ˈvɪzɪt	n. 访问；参观；逗留
voice	vɔɪs	n. 声音；愿望；嗓音；发言权
volume	ˈvɒljuːm	n. 体积；卷；册；音量；大量；量
vote	vəʊt	n. 投票，选举；选票；得票数
wait	weɪt	vt. 等候；推迟；延缓
walk	wɔːk	n. 步行，走；散步
want	wɒnt	vt. 需要；缺少；应该；希望
war	wɔː(r)	n. 战争，斗争；冲突，对抗，竞争；军事，战术
warm	wɔːm	adj. 温暖的；热情的
warn	wɔːn	vt. 警告，提醒；通知
was	wɒz	vt. 是；在（am、is 的过去式）
watch	wɒtʃ	vt. 注视；观察；看守；警戒
water	ˈwɔːtə(r)	n. 水；雨水；海水；海域，大片的水
way	weɪ	n. 道路；方法；方向；习惯；行业
we	wiː; wi	pron. 我们（主格）； [ 古 ] 朕，寡人；笔者，本人（作者或演讲人使用）
weak	wiːk	adj. 疲软的；虚弱的；无力的；不牢固的
wear	weə(r)	vt. 穿着，戴；磨损
website	ˈwebsaɪt	n. 网站（全球资讯网的主机站）
Wednesday	ˈwenzdeɪ	n.星期三
week	wiːk	n. 周，星期
weight	weɪt	n. 重量，重力；砝码；重要性；负担
well	wel	adv. 满意地；适当地；很好地；充分地
were	wɜː(r)	v. 是，在（are 的过去式）
west	west	n. 西；西方；西部
what	wɒt	pron. 什么；多么；多少
whatever	wɒtˈevə(r)	adj. 不管什么样的
when	wen	conj. 既然；当 … 时；考虑到
whenever	wenˈevə(r)	conj. 每当；无论何时
where	weə(r)	adv. 在哪里
wherever	weərˈevə(r)	adv. 无论什么地方；究竟在哪里
whether	ˈweðə(r)	conj. 是否；不论
which	wɪtʃ	pron. 哪 / 那一个；哪 / 那一些
while	waɪl	conj. 当…的时候；虽然；然而
white	waɪt	adj. 白色的；白种的；纯洁的
who	huː	pron. 谁
whole	həʊl	adj. 完整的；纯粹的
whom	huːm	pron. 谁（ who 的宾格）
whose	huːz	pron. 谁的（疑问代词）
why	waɪ	int. 哎呀！什么？
wide	waɪd	adj. 宽的，广阔的；张大的；远离目标的；广泛的
widely	ˈwaɪdli	adv. 广泛地
wild	waɪld	adj. 野生的；野蛮的；狂热的；荒凉的
will	wɪl	n. 意志；意图；心愿；情感；遗嘱
win	wɪn	vt. 赢得；在 … 中获胜；劝诱
winter	ˈwɪntə(r)	n. 冬季；年岁；萧条期
wish	wɪʃ	n. 希望；祝福；心愿
with	wɪð	prep. 用；支持；随着；和 … 在一起
within	wɪˈðɪn	prep. 在 … 之内
without	wɪˈðaʊt	prep. 没有；在 … 外面；超过
woman	ˈwʊmən	n. 女性；妇女；成年女子
wonder	ˈwʌndə(r)	n. 惊奇；奇迹；惊愕
word	wɜːd	n. 单词；话语；消息；诺言；命令
work	wɜːk	n. 工作；职业；产品；行为；文学、音乐或艺术作品；工厂；操作； [ 物 ] 功
worker	ˈwɜːkə(r)	n. 工人；劳动者；职蚁
world	wɜːld	n. 世界；全人类；世俗；领域；宇宙；物质生活
worse	wɜːs	n.(人或事)较坏者,更坏的事,更恶劣的事,败北adj.更坏的,更恶劣的adv.更坏地,更恶劣地
would	wʊd	aux. 将，将要；愿意
write	raɪt	vi. 写，写字；写作，作曲；写信
wrong	rɒŋ	adv. 错误地；邪恶的，不正当地
year	jɪə(r)	n. 年；历年；年度
yellow	ˈjeləʊ	adj. 黄色的；黄皮肤的
yes	jes	adv. 是
yesterday	ˈjestədeɪ	n. 昨天；往昔
yet	jet	adv. 还；但是；已经
you	juː	pron. 你；你们
young	jʌŋ	adj. 年轻的；初期的；没有经验的
your	jɔː(r)	pron. 你的，你们的
yourself	jɔːˈself	pron. 你自己
zero	ˈzɪərəʊ	n. 零点，零度
    """.trimIndent()
}
