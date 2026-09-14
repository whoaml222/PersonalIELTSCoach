package com.personalieltscoach.data.seed

/** Corrections to source labels and senses used by the editorial examples. */
internal object ReviewedMeanings {
    fun forWord(word: String, original: String): String = corrections[word.lowercase()] ?: original
    private val corrections = """
air|n. 空气；v. 给……通风
bank|n. 河岸；银行
beauty|n. 美丽；美人
being|v. 是，处于（be 的 -ing 形式）；n. 存在，生物
charge|n. 费用；v. 收费；充电
compute|v. 计算
cream|n. 奶油；护肤霜
every|det. 每个，每一
even|adv. 甚至，连……都；adj. 平坦的，偶数的
fault|n. 过错；故障
finish|v. 完成，结束；n. 结尾
football|n. 足球（英）；美式橄榄球（美）
soccer|n. 足球（美式英语和澳新常用称呼）
french|n. 法语；法国人；adj. 法国的
english|n. 英语；adj. 英国的，英语的
future|n. 未来；adj. 将来的
garbage|n. 垃圾，废物
get married|v. 结婚（动词短语）
grand|n. 一千英镑或美元（口语）；adj. 宏大的
make|n. 品牌，型号；v. 制作，使
pants|n. 内裤（英）；裤子（美）
pool|n. 游泳池；共同使用的资金或资源
roast|adj. 烤的；v. 烤；n. 烤肉
seoul|n. 首尔（旧称汉城）
speed|n. 速度；v. 快速移动
time|n. 时间；次数
a few|det. 一些，几个（修饰可数名词复数）
no one|pron. 没有人
amused|adj. 觉得好笑的，被逗乐的
conductor|n. 列车员，售票员；指挥；导体
deposit|n. 押金，订金；存款
spoon|n. 勺子
customer|n. 顾客
contract|n. 合同；v. 签约，收缩
dare|v. 敢于；n. 激将，挑战
deep|adj. 深的；adv. 深深地
do|v. 做；aux. 用于疑问句、否定句或强调
does|v. 做（do 的第三人称单数）；aux. 用于疑问句、否定句
did|v. 做（do 的过去式）；aux. 用于过去时疑问句、否定句
has|v. 有（have 的第三人称单数）；aux. 构成现在完成时
have|v. 有，吃，进行；aux. 构成完成时
gain|v. 获得，增加；n. 收获
forward|adv. 向前；adj. 前面的
inside|adv. 在里面；prep. 在……里面；n. 内部
international|adj. 国际的
lead|n. 牵引绳，导线 /liːd/；v. 带领 /liːd/；n. 铅 /led/
local|adj. 当地的；n. 当地人
main|adj. 主要的
material|n. 材料，布料；adj. 物质的
measure|v. 测量；n. 措施，尺寸
might|aux. 可能，也许（表示不确定）
mind|v. 介意；n. 头脑，想法
outside|adv. 在外面；prep. 在……外面；adj. 外部的
project|n. 项目，课题
rule|n. 规则；v. 统治
sick|adj. 恶心的；生病的
sound|v. 听起来；n. 声音
special|adj. 特别的，特殊的；n. 特价品，特色菜
study|v. 学习，研究；n. 学习，书房
tax|n. 税；v. 征税
thick|adj. 厚的，浓的
trust|v. 信任；n. 信任
use|v. 使用 /juːz/；n. 用途 /juːs/
visit|v. 访问，看望；n. 访问
wish|v. 希望，祝愿；n. 愿望
wonder|v. 想知道，琢磨；n. 惊奇
laugh|v. 笑；n. 笑声
fast|v. 禁食；adj. 快的；adv. 快速地
three|num. 三
nine|num. 九
two|num. 二
hundred|num. 一百
thousand|num. 一千
million|num. 一百万
billion|num. 十亿
zero|num. 零
whatever|pron. 无论什么；det. 无论哪一种
each|det. 每个；pron. 各个；adv. 每个地
such|det. 这样的，如此的
those|det. 那些；pron. 那些
this|det. 这个；pron. 这
your|det. 你的，你们的（形容词性物主代词）
my|det. 我的（形容词性物主代词）
his|det. 他的；pron. 他的东西
her|det. 她的；pron. 她（宾格）
our|det. 我们的（形容词性物主代词）
their|det. 他们的，她们的，它们的
its|det. 它的（不是 it is 的缩写）
he|pron. 他（主格）
we|pron. 我们（主格）
too|adv. 太，过于；也
very much|adv. 非常，很（副词短语）
much|det. 许多（不可数）；pron. 很多；adv. 很，非常
many|det. 许多（可数复数）；pron. 许多人或事物
more|det. 更多的；pron. 更多；adv. 更
other|det. 另外的，其他的；pron. 其他人或事物
off|adv. 离开；关闭；prep. 离开……
over|adv. 过来；结束；prep. 在……上方，越过
scotch|n. 苏格兰威士忌；adj. 苏格兰的（常用于 whisky 等固定搭配）
either|det. 两者中的任一个；pron. 任一个；adv. 也（用于否定句）
neither|det. 两者都不；pron. 两者都不；adv. 也不
may|aux. 可能；可以（许可）；n. 五月（May）
late|adj. 迟的，晚的；adv. 迟，晚
enjoy|v. 喜欢，享受……的乐趣
keep|v. 保留；保持；继续
spend|v. 花费（时间或金钱）；度过
just|adv. 刚刚；只是；恰好
awfully|adv. 非常，极其（口语）；可怕地
smile|n. 微笑；v. 微笑
subway|n. 地铁（美）；地下人行通道（英）
enough|det. 足够的；adv. 足够地；pron. 足够的数量
fail|v. 失败，未通过；未能做到
salt|n. 盐；v. 给……加盐
a little|det. 少量，一点（不可数）；adv. 稍微
less|det. 较少的（不可数）；adv. 更少；pron. 更少的量
most|det. 大多数；最多的；adv. 最
get off|v. 下车（动词短语）
get on|v. 上车；进展；相处（动词短语）
invite|v. 邀请
serve|v. 服务，接待；供应（食物）
recognize|v. 认出，识别；承认
dream|v. 做梦；梦想；n. 梦，梦想
kindly|adv. 好心地，友善地
an|art. 一个，一位（用于起始读音为元音的词前）
currently|adv. 目前，当前
differently|adv. 不同地
establish|v. 建立；查明，证实
had|v. 有，吃，进行（have 的过去式）；aux. 构成过去完成时
however|adv. 不过，然而；无论怎样
relate|v. 叙述；联系；理解，产生共鸣（relate to）
before|prep. 在……之前；conj. 在……之前；adv. 以前
wherever|conj. 无论在哪里
sorry|adj. 抱歉的，难过的
thus|adv. 因此，这样（较正式，日常口语常用 so）
furthermore|adv. 此外，而且（较正式，日常口语常用 also）
nevertheless|adv. 尽管如此，然而（较正式）
whom|pron. 谁（who 的宾格，较正式）
wed|v. 结婚（较正式或用于新闻，日常常说 get married）
    """.trimIndent().lineSequence().filter(String::isNotBlank).associate { row ->
        val (word, meaning) = row.split('|', limit = 2)
        word to meaning
    }
}
