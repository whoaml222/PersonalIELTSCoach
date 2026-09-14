package com.personalieltscoach.data.seed

/** Editorial content, not a part-of-speech sentence generator. Keep each collocation explicit. */
internal object ReviewedExamples {
    data class Example(val sentence: String, val translation: String)
    val all: Map<String, Example> by lazy {
        val seen = mutableSetOf<String>()
        rows.asSequence().flatMap { it.trimIndent().lineSequence() }.filter { it.isNotBlank() }.associate { row ->
            val parts = row.split('|')
            require(parts.size == 3) { "Invalid reviewed example" }
            require(seen.add(parts[0])) { "Duplicate reviewed word: ${parts[0]}" }
            parts[0] to Example(parts[1], parts[2])
        }
    }
    fun find(word: String): Example? = all[word.lowercase()]
    private val rows = listOf("""
handbag|I've left my handbag on the bus.|我把手提包落在公交车上了。
thank|I'd like to thank you for your help.|我想感谢你的帮助。
very|It's very cold outside today.|今天外面很冷。
pen|Can I borrow your pen for a moment?|能借你的钢笔用一下吗？
pencil|Write your answer in pencil.|用铅笔写下你的答案。
book|I'm reading a book about Australia.|我正在看一本关于澳大利亚的书。
watch|My watch is five minutes slow.|我的手表慢了五分钟。
coat|Take your coat; it might get cold.|带上外套，可能会冷。
dress|That dress looks great on you.|你穿那条连衣裙很好看。
skirt|This skirt is a bit too long.|这条裙子有点太长了。
shirt|Do you have this shirt in blue?|这件衬衫有蓝色的吗？
car|My car won't start this morning.|我的车今天早上发动不起来了。
house|Our house is close to the station.|我们家离车站很近。
thank you|Thank you for picking me up.|谢谢你来接我。
umbrella|You can share my umbrella.|你可以跟我一起撑这把伞。
please|Could you speak more slowly, please?|请你说慢一点好吗？
ticket|Is this ticket valid tomorrow?|这张票明天还能用吗？
number|Could I have your phone number?|能给我你的电话号码吗？
five|I'll be there in five minutes.|我五分钟后到。
sorry|I'm sorry I'm late.|对不起，我迟到了。
cloakroom|You can leave your coat in the cloakroom.|你可以把外套放在衣帽间。
school|What time does your school finish?|你们学校几点放学？
teacher|Our teacher explains things really clearly.|我们的老师讲得很清楚。
son|My son has just started school.|我儿子刚开始上学。
daughter|Her daughter wants to be a nurse.|她女儿想当护士。
good|You did a really good job.|你做得真好。
morning|I go for a walk every morning.|我每天早晨都去散步。
new|How's your new job going?|你的新工作怎么样？
student|I'm a student at the local college.|我在本地的学院读书。
french|Do you speak French?|你会说法语吗？
german|My neighbour is German.|我的邻居是德国人。
nice|It was nice to meet your family.|很高兴见到了你的家人。
japanese|We're learning Japanese together.|我们在一起学日语。
korean|There's a Korean restaurant near my flat.|我公寓附近有家韩国餐馆。
chinese|Could you explain that in Chinese?|你能用中文解释一下吗？
make|What make is your car?|你的车是什么牌子的？
sweden|My sister lives in Sweden.|我姐姐住在瑞典。
england|He's moving to England next month.|他下个月要搬去英格兰。
english|I'm practising my English every day.|我每天都在练习英语。
america|Have you ever been to America?|你去过美国吗？
american|An American friend taught me this recipe.|一位美国朋友教了我这道菜的做法。
italy|We spent a week in Italy.|我们在意大利待了一个星期。
swedish|This shop sells Swedish furniture.|这家店卖瑞典家具。
italian|Let's have Italian food tonight.|我们今晚吃意大利菜吧。
volvo|My dad drives a Volvo.|我爸爸开沃尔沃。
peugeot|Is that your Peugeot outside?|外面那辆标致是你的吗？
mercedes|She bought a second-hand Mercedes.|她买了一辆二手梅赛德斯。
toyota|How long have you had your Toyota?|你的丰田开了多久了？
daewoo|We used to have a Daewoo.|我们以前有辆大宇汽车。
mini|That Mini is easy to park.|那辆迷你汽车很好停。
ford|The garage is fixing my Ford.|修理厂正在修我的福特。
fiat|We're renting a Fiat for the weekend.|我们周末要租一辆菲亚特。
am|I am ready to start.|我准备好开始了。
name|How do you spell your name?|你的名字怎么拼？
nationality|What nationality are you?|你是哪国人？
job|I'm looking for a part-time job.|我在找一份兼职工作。
keyboard|This keyboard isn't working.|这个键盘坏了。
operator|Ask the machine operator for help.|找机器操作员帮忙。
engineer|We'll need an engineer to check the design.|我们需要工程师检查一下设计。
policeman|A policeman helped us find the station.|一名警察帮我们找到了车站。
policewoman|The policewoman asked for my address.|那位女警察问了我的地址。
taxi driver|The taxi driver knows a quicker route.|出租车司机知道一条更快的路线。
air hostess|My aunt used to work as an air hostess.|我姑姑以前是空乘人员。
postman|The postman left a parcel for you.|邮递员给你留了个包裹。
nurse|The nurse will check your temperature.|护士会给你量体温。
mechanic|A mechanic is looking at my brakes.|一位修理工正在检查我的刹车。
hairdresser|I've got an appointment with the hairdresser.|我预约了理发师。
housewife|My grandmother was a housewife for many years.|我奶奶做了很多年的家庭主妇。
housework|We share the housework.|我们分担家务。
milkman|The milkman delivers milk before breakfast.|送奶员在早饭前把牛奶送来。
fine|I'm feeling fine now, thanks.|我现在感觉很好，谢谢。
see|I can't see the sign from here.|我在这里看不清那个标志。
fat|Our cat is getting a bit fat.|我们的猫有点发胖了。
woman|The woman in the blue coat is my aunt.|穿蓝色外套的女士是我姑姑。
thin|This jacket is too thin for winter.|这件夹克太薄了，不适合冬天穿。
tall|He's too tall for that bed.|他太高了，那张床不够长。
short|The walk is quite short.|这段路走起来很短。
dirty|Your hands are dirty; wash them first.|你手脏了，先洗一洗。
clean|Are these cups clean?|这些杯子干净吗？
hot|Careful, the soup is hot.|小心，汤很烫。
cold|Would you like a cold drink?|你想喝点冷饮吗？
old|How old is your dog?|你的狗几岁了？
young|She's too young to drive.|她年龄太小，还不能开车。
busy|Are you busy this afternoon?|你今天下午忙吗？
lazy|Let's have a lazy Sunday at home.|我们周日待在家里悠闲地休息吧。
blue|I prefer the blue one.|我更喜欢蓝色的那个。
white|Wear a white shirt for the interview.|面试时穿件白衬衫吧。
father|My father taught me to cook.|我爸爸教会了我做饭。
mother|I'll call my mother after dinner.|晚饭后我会给妈妈打电话。
blouse|That blouse goes well with your skirt.|那件女式衬衫很配你的裙子。
sister|My sister is coming to stay.|我姐姐要来住几天。
tie|Do I need to wear a tie?|我需要打领带吗？
brother|My brother works night shifts.|我哥哥上夜班。
colour|What colour would you like?|你想要什么颜色？
color|What's your favorite color?|你最喜欢什么颜色？
green|The light has turned green.|交通灯变绿了。
come|Can you come over after work?|你下班后能过来吗？
smart|You look smart in that suit.|你穿那套西装很精神。
hat|Don't forget your sun hat.|别忘了带遮阳帽。
lovely|It's a lovely day for a walk.|今天天气真好，适合散步。
case|Can you help me carry this case?|你能帮我提这个箱子吗？
carpet|Please take your shoes off on the carpet.|走上地毯时请脱鞋。
blanket|Would you like another blanket?|你想再要一条毯子吗？
dog|I need to take the dog for a walk.|我得去遛狗了。
puppy|Their new puppy chews everything.|他们刚养的小狗什么都咬。
officer|The officer checked my passport.|那位官员检查了我的护照。
girl|The girl next door plays the piano.|隔壁的女孩会弹钢琴。
danish|His wife is Danish.|他的妻子是丹麦人。
denmark|They're visiting Denmark this summer.|他们今年夏天要去丹麦旅游。
friend|I'm meeting a friend for lunch.|我要和一个朋友一起吃午饭。
norwegian|Our tour guide was Norwegian.|我们的导游是挪威人。
passport|Keep your passport somewhere safe.|把护照放在安全的地方。
brown|Are those brown shoes yours?|那双棕色的鞋是你的吗？
tourist|A tourist asked me for directions.|一位游客向我问路。
russian|She has a Russian friend at work.|她有位俄罗斯同事朋友。
russia|He studied in Russia for a year.|他在俄罗斯学习过一年。
dutch|My Dutch neighbours have just moved in.|我的荷兰邻居刚搬来。
holland|We took a ferry to Holland.|我们坐渡轮去了荷兰。
red|Your face is turning red.|你的脸变红了。
grey|The sky's looking grey.|天空看起来灰蒙蒙的。
yellow|Look for the yellow sign.|找那个黄色的标志。
black|I'll have a black coffee, please.|请给我一杯黑咖啡。
orange|Would you like an orange?|你想吃个橙子吗？
employee|Every employee gets a lunch break.|每位员工都有午休时间。
hard-working|She's one of our most hard-working staff members.|她是我们最勤奋的员工之一。
sales reps|Our sales reps spend a lot of time travelling.|我们的销售代表经常出差。
sale|These shoes are on sale.|这些鞋正在打折。
man|Ask the man at the desk.|问问服务台那位男士。
office|I'll be in the office until six.|我会在办公室待到六点。
assistant|The shop assistant helped me choose a size.|店员帮我选了尺码。
matter|What's the matter with your phone?|你的手机怎么了？
children|The children are playing outside.|孩子们正在外面玩。
child|Does your child have any allergies?|你的孩子有什么过敏的吗？
kid|I loved camping when I was a kid.|我小时候很喜欢露营。
tired|I'm too tired to cook tonight.|今晚我太累了，不想做饭。
boy|That boy has lost his parents in the crowd.|那个男孩在人群中跟父母走散了。
thirsty|Are you thirsty after that walk?|走了那么久，你渴吗？
mum|Mum, where are my trainers?|妈妈，我的运动鞋在哪儿？
right|Is this the right bus for the airport?|这是去机场的那班公交车吗？
ice cream|Let's get some ice cream.|我们买点冰淇淋吧。
ice|Would you like ice in your drink?|你想在饮料里加冰吗？
cream|Would you like cream with your cake?|蛋糕要配奶油吗？
big|This bag is too big to carry on.|这个包太大，不能随身带上飞机。
small|Do you have a small table for two?|你们有两个人坐的小桌吗？
open|Is the supermarket still open?|超市还开着吗？
shut|Keep the gate shut, please.|请把大门关好。
light|This suitcase is surprisingly light.|这个行李箱出奇地轻。
heavy|That box looks heavy; let me help.|那个箱子看起来很重，我来帮你。
long|How long is your lunch break?|你的午休有多长？
shoe|There's a stone in my shoe.|我鞋里有颗小石头。
grandfather|My grandfather grows his own vegetables.|我爷爷自己种菜。
grandmother|I'm visiting my grandmother this weekend.|我这周末要去看奶奶。
grand|The repairs cost over a grand.|维修花了一千多块钱。
empty|The petrol tank is nearly empty.|油箱快空了。
full|I'm full; I couldn't eat another thing.|我饱了，什么都吃不下了。
large|Could I have a large coffee?|请给我一大杯咖啡好吗？
little|There's a little shop around the corner.|拐角处有家小店。
sharp|Be careful; that knife is sharp.|小心，那把刀很锋利。
blunt|This knife is too blunt to cut bread.|这把刀太钝了，切不了面包。
box|Put the spare parts in this box.|把备用零件放进这个箱子里。
glass|Could I have a glass of water?|请给我一杯水好吗？
cup|Would you like a cup of tea?|你想喝杯茶吗？
bottle|I've brought a bottle of water.|我带了一瓶水。
tin|Open a tin of beans for lunch.|午饭开一罐豆子吃吧。
knife|Can you pass me the bread knife?|能把面包刀递给我吗？
fork|I've dropped my fork on the floor.|我的叉子掉到地上了。
chopsticks|Would you prefer chopsticks or a fork?|你想用筷子还是叉子？
glasses|I can't find my reading glasses.|我找不到老花镜了。
shelf|The sugar is on the top shelf.|糖在最上面的架子上。
desk|I've left a note on your desk.|我在你的书桌上留了张便条。
table|Have you booked a table for tonight?|你订好今晚的餐桌了吗？
plate|Careful, that plate is hot.|小心，那个盘子很烫。
dish|What's your favourite dish here?|这里哪道菜是你最喜欢的？
cupboard|The mugs are in that cupboard.|马克杯在那个橱柜里。
cigarette|Please don't smoke a cigarette in here.|请不要在这里抽烟。
television|Could you turn the television down?|你能把电视声音调小一点吗？
floor|Mind the wet floor.|小心地滑。
dressing table|Her hairbrush is on the dressing table.|她的发梳在梳妆台上。
dressing|This salad needs a little dressing.|这份沙拉需要加一点调味汁。
magazine|Can I have a look at your magazine?|我能看看你的杂志吗？
""", """
bed|I'm going to bed early tonight.|我今晚要早点上床睡觉。
newspaper|There's a newspaper on the kitchen table.|厨房桌上有份报纸。
stereo|Could you turn the stereo down a bit?|能把音响声音调小一点吗？
radio|I listen to the radio on my way to work.|我上班路上听收音机。
kitchen|The kitchen smells wonderful.|厨房里真香。
refrigerator|Put the milk back in the refrigerator.|把牛奶放回冰箱。
electric|We're thinking of buying an electric car.|我们在考虑买一辆电动汽车。
left|Take the next turning on the left.|在下一个路口向左转。
cooker|Have you turned the cooker off?|你关炉子了吗？
chef|The chef made a special dessert for us.|厨师为我们做了一份特别的甜点。
cook|Shall we cook at home tonight?|我们今晚在家做饭吧？
room|Does the room have a private bathroom?|这个房间有独立浴室吗？
living room|We're watching a film in the living room.|我们正在客厅看电影。
near|Is there a cash machine near here?|这附近有取款机吗？
window|Would you mind opening the window?|你介意把窗户打开吗？
armchair|Grandad fell asleep in his armchair.|爷爷坐在扶手椅上睡着了。
door|Someone's knocking at the door.|有人在敲门。
picture|Who painted that picture?|那幅画是谁画的？
image|The image on my screen is blurry.|我屏幕上的图像很模糊。
photo|Could you take a photo of us?|你能给我们拍张照吗？
wall|Let's put the sofa against that wall.|我们把沙发靠着那面墙放吧。
the great wall|I'd love to visit the Great Wall.|我很想去长城看看。
trousers|These trousers need ironing.|这条长裤需要熨一下。
shorts|It's warm enough to wear shorts.|天气够暖和，可以穿短裤了。
jeans|Are jeans OK for the party?|穿牛仔裤去聚会可以吗？
pants|I need some clean pants.|我需要干净的内裤。
bedroom|The bedroom gets plenty of light.|这间卧室采光很好。
untidy|Sorry, the flat's a bit untidy.|不好意思，公寓有点乱。
tidy|Your room looks nice and tidy.|你的房间看起来很整洁。
air|Let's air the room before the guests arrive.|客人到之前，给房间通通风吧。
put|Where should I put these bags?|这些包我该放在哪里？
clothes|Your clothes are still wet.|你的衣服还没干。
wardrobe|There's plenty of space in the wardrobe.|衣柜里有很多空间。
closet|I'll hang my jacket in the closet.|我会把夹克挂进衣橱。
dust|Could you dust the shelves while I vacuum?|我吸尘时，你能掸掸架子上的灰吗？
read|Have you read this book before?|你以前读过这本书吗？
put on|Put on your shoes; we're leaving.|穿上鞋，我们要走了。
turn off|Please turn off the lights when you leave.|离开时请关灯。
garden|We're having lunch in the garden.|我们在花园里吃午饭。
tree|Let's sit under that tree.|我们坐在那棵树下吧。
grass|The grass needs cutting.|草需要修剪了。
cat|Our cat sleeps on the sofa.|我们的猫睡在沙发上。
letter|I've had a letter from the bank.|我收到了一封银行的信。
basket|Put the dirty washing in the basket.|把脏衣服放进篮子里。
basketball|Do you fancy playing basketball after work?|下班后想打篮球吗？
eat|What would you like to eat?|你想吃什么？
bone|Watch out for the bone in that piece of fish.|小心那块鱼里的鱼刺。
tooth|I've got a loose tooth.|我有颗牙松了。
milk|We've run out of milk.|我们没牛奶了。
meal|Thanks for a lovely meal.|谢谢你招待这么好吃的一顿饭。
drink|Don't forget to drink some water.|别忘了喝点水。
tap|The kitchen tap keeps dripping.|厨房的水龙头一直在滴水。
day|How was your day?|你今天过得怎么样？
cloud|That cloud looks like a dog.|那朵云看起来像只狗。
sky|The sky is clear tonight.|今晚天空很晴朗。
sun|Let's get out of the sun for a bit.|我们去避一会儿太阳吧。
family|My family is visiting next week.|我的家人下周要来。
bridge|You need to cross the bridge first.|你得先过桥。
boat|We hired a boat for the afternoon.|我们租了一条船，下午用。
ship|The ship leaves at nine tomorrow morning.|轮船明天早上九点出发。
river|There's a path along the river.|河边有条小路。
aeroplane|The aeroplane is about to land.|飞机就要降落了。
airplane|Our airplane arrived an hour late.|我们的飞机晚到了一个小时。
asleep|The baby's finally asleep.|宝宝终于睡着了。
wash|I'll wash the dishes if you dry them.|我来洗碗，你来擦干，好吗？
wait|Could you wait here for a moment?|你能在这里等一下吗？
photograph|That's a lovely photograph of your family.|那张你们家的合照真好看。
village|There's only one shop in our village.|我们村里只有一家商店。
valley|You can see the whole valley from here.|从这里能看到整个山谷。
country|Which country are you from?|你来自哪个国家？
hill|The walk up the hill is quite steep.|上山的路挺陡的。
mountain|There's still snow on the mountain.|山上还有积雪。
another|Could I have another cup of tea?|能再给我一杯茶吗？
wife|My wife will pick us up.|我妻子会来接我们。
bank|We sat on the river bank to eat lunch.|我们坐在河岸上吃午饭。
water|Could you water the plants while I'm away?|我不在时，你能给植物浇水吗？
building|Which building is your office in?|你的办公室在哪栋楼里？
park|Shall we take a walk in the park?|我们去公园走走吧？
parking|Is there free parking here?|这里可以免费停车吗？
enter|Please knock before you enter.|进来前请先敲门。
work|What time do you finish work?|你几点下班？
bookcase|That bookcase won't fit through the door.|那个书柜搬不过这扇门。
hammer|Pass me the hammer, please.|请把锤子递给我。
pink|She's painted her bedroom pink.|她把卧室刷成了粉红色。
homework|Have you finished your homework yet?|你做完作业了吗？
front|I'll meet you at the front of the building.|我在大楼前面等你。
careful|Be careful with that hot pan.|小心那个烫锅。
be careful|Please be careful on the stairs.|上下楼梯时请小心。
vase|That vase would look nice by the window.|那个花瓶放在窗边会很好看。
flower|What kind of flower is this?|这是什么花？
send|Could you send me the address?|你能把地址发给我吗？
take|Don't forget to take your keys.|别忘了带钥匙。
bring|Shall I bring something to drink?|我要不要带点喝的？
cheese|Would you like cheese on your toast?|你想在吐司上加奶酪吗？
bread|This bread is still warm.|这面包还是热的。
soap|There's no soap in the bathroom.|浴室里没有肥皂了。
chocolate|I saved you a piece of chocolate.|我给你留了一块巧克力。
sugar|Do you take sugar in your tea?|你喝茶加糖吗？
candy|The children brought home lots of candy.|孩子们带了很多糖果回家。
coffee|Let's stop for a coffee.|我们停下来喝杯咖啡吧。
caffeine|Does this drink contain caffeine?|这种饮料含咖啡因吗？
tea|I've just made a pot of tea.|我刚泡了一壶茶。
tobacco|This shop doesn't sell tobacco.|这家店不卖烟草。
smoking|Smoking isn't allowed inside.|室内不许吸烟。
loaf|Could you pick up a loaf of bread?|你能顺便买一条面包吗？
bird|There's a bird outside the window.|窗外有只鸟。
any|Have you got any questions?|你有什么问题吗？
some|Would you like some more rice?|你想再吃点米饭吗？
kettle|I'll put the kettle on.|我来烧壶水。
teapot|Could you warm the teapot first?|你能先把茶壶温一下吗？
oil|Heat a little oil in the pan.|在锅里热一点油。
pot|Put a lid on the pot.|给锅盖上盖子。
hot pot|Let's have hot pot this weekend.|我们这周末吃火锅吧。
can|Can you give me a hand?|你能帮我一下吗？
boss|I've asked my boss for Friday off.|我向老板请了星期五的假。
minute|Give me a minute to get ready.|给我一分钟准备一下。
second|Hang on a second; I'll get my bag.|等一下，我去拿包。
hour|The journey takes about an hour.|这段路程大约要一个小时。
ask|Can I ask you a quick question?|我能问你一个简单的问题吗？
handwriting|I can't read his handwriting.|我看不懂他写的字。
terrible|The traffic was terrible this morning.|今天早上堵车很严重。
lift|Can you help me lift this box?|你能帮我抬这个箱子吗？
cake|There's some cake left if you'd like a slice.|还有蛋糕，你想吃一块吗？
biscuit|Would you like a biscuit with your tea?|你想配着茶吃块饼干吗？
like|I'd like a table by the window.|我想要靠窗的桌子。
fresh|These strawberries smell really fresh.|这些草莓闻起来很新鲜。
egg|How would you like your egg cooked?|你的鸡蛋想怎么做？
butter|Let the butter soften before you use it.|用之前先让黄油软化。
honey|Try adding a little honey.|试着加一点蜂蜜。
ripe|These bananas aren't ripe yet.|这些香蕉还没熟。
banana|I'll have a banana before we leave.|出发前我吃根香蕉。
jam|There's strawberry jam in the fridge.|冰箱里有草莓酱。
sweet|This coffee is too sweet for me.|这杯咖啡对我来说太甜了。
scotch whisky|He bought a bottle of Scotch whisky as a gift.|他买了一瓶苏格兰威士忌送礼。
whisky|Would you like ice with your whisky?|你的威士忌要加冰吗？
apple|Would you like me to cut up an apple?|要我切个苹果吗？
wine|Shall we bring a bottle of wine?|我们要不要带一瓶葡萄酒？
beer|I'll have an alcohol-free beer, please.|请给我一杯无醇啤酒。
blackboard|I can't read what's on the blackboard.|我看不清黑板上写的内容。
liquor|They sell liquor at that shop.|那家店卖烈性酒。
alcohol|I don't drink alcohol.|我不喝酒。
butcher|The butcher recommended these sausages.|肉店老板推荐了这些香肠。
meat|Is there any meat in this soup?|这汤里有肉吗？
mince|We need some mince for the sauce.|做这个酱需要一些肉末。
beef|Would you prefer chicken or beef?|你想吃鸡肉还是牛肉？
steak|How would you like your steak cooked?|你的牛排要几分熟？
lamb|We're having roast lamb on Sunday.|我们周日吃烤羊肉。
mutton|This curry is made with mutton.|这道咖喱是用羊肉做的。
husband|Her husband works at the hospital.|她丈夫在医院工作。
chicken|The chicken needs another ten minutes in the oven.|鸡肉还需要再烤十分钟。
tell|Can you tell me what happened?|你能告诉我发生了什么吗？
truth|Please tell me the truth.|请告诉我实情。
tomato|Slice a tomato for the sandwich.|切一个西红柿放进三明治。
potato|Would you like a baked potato?|你想吃个烤土豆吗？
cabbage|We have some cabbage left in the fridge.|我们冰箱里还剩一些卷心菜。
lettuce|Wash the lettuce before making the salad.|做沙拉前先洗生菜。
pea|There's a pea on your sleeve.|你的袖子上有颗豌豆。
bean|What kind of bean is this?|这是什么豆子？
pear|This pear is really juicy.|这个梨水分真多。
grape|Try a grape; they're really sweet.|尝颗葡萄吧，很甜的。
peach|This peach is ready to eat.|这个桃子可以吃了。
greece|We're thinking of going to Greece next summer.|我们在考虑明年夏天去希腊。
climate|What's the climate like where you live?|你住的地方气候怎么样？
pleasant|It was a pleasant walk home.|走回家的这一路很舒服。
weather|What's the weather going to be like tomorrow?|明天天气怎么样？
spring|The garden looks lovely in spring.|花园春天很好看。
windy|It's too windy for an umbrella.|风太大，没法撑伞。
warm|This soup will keep you warm.|喝这汤能让你暖和起来。
summer|Are you going away this summer?|你今年夏天要出去度假吗？
autumn|I love the colours of autumn.|我喜欢秋天的色彩。
winter|It gets dark early in winter.|冬天天黑得早。
january|My course starts in January.|我的课程一月份开始。
february|We're moving house in February.|我们二月份搬家。
march|Her birthday is in March.|她的生日在三月。
april|I'm taking a week off in April.|我四月份要休一星期假。
june|The wedding is at the end of June.|婚礼在六月底。
july|July is usually quite busy here.|这里七月通常很忙。
august|Are you free in August?|你八月份有空吗？
september|The children go back to school in September.|孩子们九月份返校。
october|We're expecting visitors in October.|我们十月份会有客人来。
""", """
november|The shop closes for repairs in November.|这家店十一月份停业维修。
december|Are you coming home in December?|你十二月份回家吗？
the u.s.|My cousin has moved to the U.S.|我表哥搬到美国去了。
brazil|She's just got back from Brazil.|她刚从巴西回来。
france|We're taking the train to France.|我们要坐火车去法国。
germany|This parcel came from Germany.|这个包裹是从德国寄来的。
norway|I'd love to see the mountains in Norway.|我很想看看挪威的群山。
spain|He's spending the winter in Spain.|他要在西班牙过冬。
mild|We've had quite a mild winter.|这个冬天我们这里不太冷。
north|My parents live in the north of England.|我父母住在英格兰北部。
south|The beach is south of the town.|海滩在镇子南边。
east|The sun rises in the east.|太阳从东方升起。
west|The airport is west of the city.|机场在城市西边。
wet|Your socks are wet; change them.|你袜子湿了，换一双吧。
season|What's your favourite season?|你最喜欢哪个季节？
night|I didn't sleep well last night.|我昨晚没睡好。
set|Let's watch the sun set over the sea.|我们看海上日落吧。
early|We'd better leave early to avoid the traffic.|我们最好早点走，避开堵车。
interesting|That sounds like an interesting job.|那听起来是份有意思的工作。
subject|Can we change the subject?|我们能换个话题吗？
conversation|We had a long conversation about work.|我们聊了很久工作的事。
australia|I'd like to work in Australia one day.|我希望有一天能去澳大利亚工作。
australian|I've made an Australian friend at work.|我在工作中交了个澳大利亚朋友。
austria|We went walking in Austria last year.|我们去年去奥地利徒步了。
austrian|The owner of the café is Austrian.|这家咖啡馆的老板是奥地利人。
canada|My brother is studying in Canada.|我哥哥正在加拿大读书。
canadian|She's married to a Canadian.|她嫁给了一位加拿大人。
china|How long have you lived in China?|你在中国住了多久了？
finland|They're flying to Finland tomorrow.|他们明天飞往芬兰。
finnish|Do you know any Finnish words?|你知道什么芬兰语单词吗？
india|This tea comes from India.|这茶来自印度。
indian|There's a good Indian takeaway nearby.|附近有家不错的印度外卖店。
japan|When are you going to Japan?|你什么时候去日本？
nigeria|Her family lives in Nigeria.|她的家人住在尼日利亚。
nigerian|Our new colleague is Nigerian.|我们的新同事是尼日利亚人。
turkey|We changed planes in Turkey.|我们在土耳其转了机。
turkish|Have you tried Turkish coffee?|你喝过土耳其咖啡吗？
korea|He spent a year teaching in Korea.|他在韩国教过一年书。
polish|My Polish neighbour lent me some tools.|我的波兰邻居借给了我一些工具。
poland|We're visiting friends in Poland.|我们要去波兰看朋友。
thai|Let's try that Thai restaurant.|我们试试那家泰国餐馆吧。
thailand|The flight to Thailand takes about twelve hours.|飞往泰国大约需要十二个小时。
live|Do you live near your workplace?|你住得离工作地点近吗？
living|The cost of living has gone up.|生活费用上涨了。
stay|You're welcome to stay with us.|欢迎来我们家住。
say|What did you say your name was?|你刚才说你叫什么名字来着？
home|I'll call you when I get home.|我到家后给你打电话。
lunch|Have you had lunch yet?|你吃过午饭了吗？
afternoon|I'm free all afternoon.|我整个下午都有空。
evening|Would you like to meet this evening?|你想今晚见面吗？
arrive|What time does your train arrive?|你的火车几点到？
o'clock|Let's meet at six o'clock.|我们六点钟见吧。
clock|The clock in the kitchen has stopped.|厨房里的钟停了。
shop|There's a little shop at the end of the road.|这条路的尽头有一家小店。
mall|We spent the afternoon at the mall.|我们在商场逛了一下午。
store|Is there a grocery store within walking distance?|附近步行能到的地方有杂货店吗？
moment|I'll be with you in a moment.|我马上就来。
envelope|Write your address on the back of the envelope.|把地址写在信封背面。
writing paper|Have you got any writing paper?|你有信纸吗？
writing|I'd like some help with my writing.|我希望有人帮我提高写作。
paper|Could I have a piece of paper?|能给我一张纸吗？
page|The answer is on the next page.|答案在下一页。
shop assistant|Ask the shop assistant if they have your size.|问问店员有没有你的尺码。
size|What size do you usually wear?|你平常穿多大码？
pad|Keep a pad by the phone for messages.|在电话旁放本便笺簿，用来记留言。
glue|You'll need some glue to fix that.|修那个需要一点胶水。
chalk|There's no chalk left by the blackboard.|黑板旁没粉笔了。
change|Keep the change.|零钱不用找了。
feel|I feel much better after a shower.|洗完澡后我感觉好多了。
call|Could you call a doctor, please?|请叫个医生来好吗？
doctor|I'd like to make an appointment with the doctor.|我想预约看医生。
telephone|Is there a telephone I can use?|有没有我可以用的电话？
mouth|Cover your mouth when you cough.|咳嗽时请捂住嘴。
tongue|I burnt my tongue on the soup.|我喝汤烫到了舌头。
bad|I've got a bad cold.|我得了重感冒。
news|Have you heard the news?|你听说那个消息了吗？
headache|I've had a headache all afternoon.|我整个下午都头痛。
aspirin|Can I ask the pharmacist about aspirin?|我能向药剂师咨询阿司匹林吗？
earache|My daughter has earache.|我女儿耳朵疼。
toothache|This toothache is keeping me awake.|牙痛让我睡不着。
dentist|I'm seeing the dentist tomorrow.|我明天要去看牙医。
stomach ache|I've got a stomach ache.|我肚子痛。
medicine|Have you taken your medicine today?|你今天吃药了吗？
temperature|Could you check the baby's temperature?|你能给宝宝量一下体温吗？
flu|He's off work with flu.|他因为流感请假没上班。
measles|The doctor said it was measles.|医生说那是麻疹。
mumps|I had mumps when I was a child.|我小时候得过腮腺炎。
pharmacy|Is there a pharmacy open nearby?|附近有开门的药店吗？
better|Do you feel any better this morning?|你今天早上感觉好些了吗？
food|The food here is really good.|这里的食物真的很好吃。
remain|Please remain seated until the bus stops.|公交车停稳前请坐好。
play|Do you want to play cards?|你想打牌吗？
match|Have you got a match to light the candle?|你有点蜡烛用的火柴吗？
library|I've got to return this book to the library.|我得把这本书还给图书馆。
break|Be careful not to break that glass.|小心别把那个玻璃杯打碎了。
dad|Dad, can you give me a lift?|爸爸，你能开车送我一程吗？
key|This key doesn't fit the lock.|这把钥匙跟锁不配。
baby|The baby has just woken up.|宝宝刚醒。
hear|I can't hear you very well.|我听不太清你说话。
greengrocer|The greengrocer has lovely fresh strawberries.|蔬果店老板那里有很好的新鲜草莓。
grocer|Our local grocer delivers to the house.|我们附近的杂货店老板送货上门。
absent|She was absent from class yesterday.|她昨天没来上课。
monday|I'll be back at work on Monday.|我星期一回去上班。
tuesday|Are you free for lunch on Tuesday?|你星期二有空一起吃午饭吗？
wednesday|The bins are collected on Wednesday.|垃圾桶里的垃圾星期三清运。
thursday|My next day off is Thursday.|我的下一个休息日是星期四。
friday|Shall we go out on Friday evening?|我们星期五晚上出去吧？
saturday|The market is open every Saturday.|市场每周六都开。
sunday|I like having a lie-in on Sunday.|我喜欢周日睡个懒觉。
weekend|Have you got any plans for the weekend?|你周末有什么计划吗？
weekday|I catch the same train every weekday.|我每个工作日都坐同一班火车。
lucky|We were lucky to find a parking space.|我们很幸运，找到了一个停车位。
church|Turn right just past the church.|过了教堂就右转。
dairy|We buy our milk from the local dairy.|我们从本地乳品店买牛奶。
baker|The baker starts work before dawn.|面包师天没亮就开始工作。
year|We've lived here for a year.|我们在这里住了一年了。
race|Who's taking part in the race?|谁要参加比赛？
town|I'm going into town to do some shopping.|我要去镇中心买点东西。
down town|Shall we walk down town?|我们走路去市中心吧？
crowd|I couldn't see her in the crowd.|我在人群中找不到她。
exciting|Starting a new job is exciting.|开始一份新工作很令人兴奋。
exit|Where's the nearest exit?|最近的出口在哪里？
finish|That was an exciting finish to the race.|比赛的结尾真精彩。
done|Have you done the washing-up yet?|你洗完碗了吗？
winner|The winner gets a free meal.|获胜者可以免费吃一顿饭。
way|Is this the quickest way to the station?|这是去车站最快的路吗？
stationer|The stationer sells notebooks and pens.|那家文具店卖笔记本和笔。
awful|There's an awful smell in the fridge.|冰箱里有股很难闻的味道。
time|I've been there more than one time.|我去过那里不止一次。
last|When did you last see her?|你上次见她是什么时候？
phone|My phone is nearly out of battery.|我的手机快没电了。
week|I'm working from home this week.|我这周在家办公。
london|How long are you staying in London?|你要在伦敦待多久？
bus stop|I'll wait for you at the bus stop.|我会在公交车站等你。
bus|We'd better hurry or we'll miss the bus.|我们最好快点，不然要错过公交车了。
stop|Could you stop here, please?|请在这里停一下好吗？
understand|Sorry, I don't quite understand.|不好意思，我不太明白。
speak|Could I speak to the manager?|我能和经理说句话吗？
hand|Let me give you a hand with that.|让我来帮你弄那个吧。
pocket|There's a hole in my pocket.|我的口袋破了个洞。
phrasebook|This phrasebook has been really useful on holiday.|这本会话手册在度假时很有用。
phrase|What does this phrase mean?|这个短语是什么意思？
slowly|Please drive slowly past the school.|开车经过学校时请慢一点。
cut|Could you cut the bread into slices?|你能把面包切成片吗？
go|What time do we need to go?|我们得几点出发？
pair|I need a new pair of work boots.|我需要一双新的工作靴。
fashion|Those shoes are back in fashion.|那种鞋又流行起来了。
uncomfortable|These shoes are really uncomfortable.|这双鞋穿着真不舒服。
comfortable|Make yourself comfortable.|随便坐，放松点。
wear|What should I wear to the interview?|面试时我该穿什么？
appointment|I'd like to change my appointment.|我想改一下预约时间。
booking|I've got a booking for two nights.|我预订了两晚的住宿。
date|We've set a date for the wedding.|我们定好婚期了。
til|This recipe calls sesame seeds til.|这份食谱把芝麻叫作 til。
urgent|Is it urgent, or can it wait until tomorrow?|事情急吗，还是能等到明天？
shopping|I'll do the shopping on my way home.|我回家路上去买东西。
list|Have you made a shopping list?|你列好购物清单了吗？
vegetable|Which vegetable would you like with your fish?|你的鱼想配哪种蔬菜？
need|Do you need any help with your bags?|你的包需要我帮忙拿吗？
thing|There's one more thing I need to ask.|我还有一件事要问。
money|I'm saving money for a training course.|我在攒钱报一个培训课程。
groceries|Can you help me carry the groceries inside?|你能帮我把买的食品杂货拿进屋吗？
fruit|Would you like some fresh fruit?|你想吃点新鲜水果吗？
stationery|We need more stationery for the office.|我们办公室需要添些文具。
newsagent|The newsagent opens at six in the morning.|报刊店老板早上六点就开门。
chemist|My sister works as a chemist in a laboratory.|我姐姐在实验室当化学师。
bath|I'm going to have a hot bath.|我要去泡个热水澡。
birth|What's your date of birth?|你的出生日期是什么？
ready|Are you ready to order?|你准备好点菜了吗？
dinner|Would you like to come round for dinner?|你想来我家吃晚饭吗？
supper|There's some soup left for supper.|还剩了一些汤，晚餐可以喝。
restaurant|Have you tried the new restaurant in town?|你吃过镇上新开的餐馆吗？
roast|We're having roast chicken for lunch.|我们午饭吃烤鸡。
grill|Let's cook the fish under the grill.|我们用烤架烤鱼吧。
barbecue|We're having a barbecue on Saturday.|我们星期六要办烧烤聚餐。
breakfast|What do you usually have for breakfast?|你早餐通常吃什么？
fast|He's planning to fast tomorrow.|他打算明天禁食。
haircut|Your new haircut looks great.|你的新发型真好看。
party|Thanks for inviting us to the party.|谢谢你邀请我们参加聚会。
holiday|When are you going on holiday?|你什么时候去度假？
""", """
vacation|How was your vacation?|你的假期过得怎么样？
mess|Sorry about the mess; we're decorating.|不好意思这么乱，我们正在装修。
disorderly|The police asked the disorderly crowd to leave.|警方要求喧闹混乱的人群离开。
suitcase|I haven't packed my suitcase yet.|我还没收拾行李箱。
leave|What time do you need to leave?|你得几点走？
paris|We're spending the weekend in Paris.|我们要去巴黎过周末。
cinema|What's on at the cinema tonight?|电影院今晚放什么电影？
film|Have you seen this film before?|你以前看过这部电影吗？
movie|Let's watch a movie at home.|我们在家看部电影吧。
beautiful|What a beautiful view!|景色真美！
beauty|I love the natural beauty of this place.|我喜欢这个地方的自然美景。
city|Is this your first visit to the city?|你是第一次来这个城市吗？
attendant|The car park attendant showed us where to park.|停车场管理员告诉了我们停在哪里。
garage|I've booked the car into the garage.|我预约了把车送到修理厂。
crash|There was a crash on the way to work.|上班路上发生了一起撞车事故。
lamp-post|Meet me by the lamp-post outside the station.|我们在车站外面的灯杆旁见。
repair|Can you repair this zip?|你能修这条拉链吗？
lamp|I need a lamp for my desk.|我需要一盏放在书桌上的灯。
tire|We've got a flat tire.|我们的一个轮胎瘪了。
cost|How much will the repairs cost?|维修要花多少钱？
pound|Do you have a pound for the locker?|你有一英镑硬币用来开储物柜吗？
penny|I found a penny on the pavement.|我在人行道上捡到了一枚一便士硬币。
cent|The change was short by one cent.|找的钱少了一分钱。
move|We're hoping to move closer to work.|我们希望搬到离工作地点近一点的地方。
neighbour|Our neighbour feeds the cat when we're away.|我们不在时，邻居帮忙喂猫。
neighbor|My neighbor has offered to help us move.|我的邻居主动提出帮我们搬家。
person|You're the first person I've told.|你是我第一个告诉的人。
people|How many people are coming to dinner?|有多少人来吃晚饭？
poor|The poor dog was shaking with cold.|那只可怜的狗冷得直发抖。
pilot|The pilot said we'd land in twenty minutes.|飞行员说我们二十分钟后降落。
return|When will you return the keys?|你什么时候还钥匙？
new york|She's got a job in New York.|她在纽约找到了一份工作。
tokyo|The flight stops in Tokyo.|这个航班在东京经停。
madrid|We changed trains in Madrid.|我们在马德里换了火车。
athens|Are you staying in Athens for long?|你会在雅典待很久吗？
berlin|My friend has invited me to Berlin.|我的朋友邀请我去柏林。
bahrain|He's working in Bahrain at the moment.|他目前在巴林工作。
bombay|My grandfather still calls Mumbai Bombay.|我爷爷现在仍把孟买叫作 Bombay。
geneva|We're catching a train to Geneva.|我们要赶火车去日内瓦。
moscow|The parcel is going to Moscow.|这个包裹要寄往莫斯科。
rome|You can walk to many places in Rome.|在罗马，很多地方步行就能到。
seoul|How was your trip to Seoul?|你的首尔之行怎么样？
stockholm|We found a lovely café in Stockholm.|我们在斯德哥尔摩找到了一家很不错的咖啡馆。
sydney|I'd like to visit Sydney before moving there.|我想在搬到悉尼前先去看看。
train|Does this train stop at the airport?|这班火车在机场停吗？
platform|Which platform does the train leave from?|火车从哪个站台发车？
station|I'll pick you up outside the station.|我会在车站外接你。
plenty|Don't rush; we've got plenty of time.|别急，我们有的是时间。
bar|Let's meet at the bar next to the hotel.|我们在酒店旁边的酒吧见吧。
metro|It's quicker to take the metro.|坐地铁更快。
zip|The zip on my jacket is stuck.|我夹克上的拉链卡住了。
label|Check the washing instructions on the label.|看看标签上的洗涤说明。
handle|The handle on this mug is loose.|这个马克杯的把手松了。
address|Could you write down the address for me?|你能把地址写给我吗？
pence|It's fifty pence more than I expected.|这比我预想的贵五十便士。
back|My back hurts after sitting all day.|坐了一整天，我背疼。
help|Thanks for offering to help.|谢谢你主动帮忙。
sure|Are you sure this is the right address?|你确定地址对吗？
x-ray|The dentist wants to take an X-ray.|牙医想拍张 X 光片。
scotland|We're going camping in Scotland.|我们要去苏格兰露营。
card|I'll send you a card from the seaside.|我会从海边给你寄张明信片。
youth|There's a youth club in our neighbourhood.|我们小区有个青少年俱乐部。
youngster|The youngster next door is learning to ride a bike.|隔壁那个小孩正在学骑自行车。
hostel|Does the hostel have a shared kitchen?|这家青年旅舍有公共厨房吗？
hotel|Is breakfast included at the hotel?|这家酒店的房费包含早餐吗？
association|I've joined the local residents' association.|我加入了当地的居民协会。
write|Could you write that down for me?|你能把那个写下来给我吗？
exam|Good luck with your exam tomorrow.|祝你明天考试顺利。
test|I've booked my driving test.|我预约了驾照考试。
text|The text is too small to read on my phone.|这些文字太小了，我在手机上看不清。
pass|I'm hoping to pass the exam this time.|我希望这次能通过考试。
mathematics|She's studying mathematics at university.|她在大学学数学。
compute|The app can compute the total for you.|这个应用能帮你算出总数。
math|Can you help me with my math homework?|你能帮我做数学作业吗？
question|Could you repeat the question?|你能再说一遍问题吗？
easy|The instructions are easy to follow.|这些说明很容易照着做。
failure|The lights went out during the power failure.|停电时，灯都灭了。
response|Have you had a response to your email?|你的邮件收到回复了吗？
mark|What mark did you get in the test?|你测验得了多少分？
score|Do you know the final score?|你知道最后的比分吗？
rest|I'll finish the rest after lunch.|剩下的我午饭后做完。
difficult|Was it difficult to find the place?|这个地方难找吗？
low|My phone battery is running low.|我手机电量快用完了。
guy|Who's the guy sitting next to your brother?|坐在你哥哥旁边的那个男的是谁？
top|Your keys are on top of the fridge.|你的钥匙在冰箱顶上。
clever|That was a clever way to solve the problem.|那样解决问题真聪明。
intelligent|She's intelligent and picks things up quickly.|她很聪明，学东西很快。
stupid|That was a stupid mistake; I'm sorry.|那是个很蠢的错误，对不起。
foolish|It would be foolish to drive in this weather.|这种天气开车可不明智。
fool|I felt like a fool when I forgot her name.|我忘了她的名字，觉得自己像个傻瓜。
cheap|Do you know somewhere cheap to eat?|你知道哪里吃饭便宜吗？
expensive|That's a bit too expensive for me.|那个对我来说有点太贵了。
inexpensive|We're looking for an inexpensive hotel.|我们在找一家不贵的酒店。
stale|This bread has gone stale.|这面包已经不新鲜了。
sour|These apples taste a bit sour.|这些苹果吃起来有点酸。
""", """
loud|Could you turn it down? It's too loud.|能调小声一点吗？太吵了。
high|The shelf is too high for me to reach.|架子太高了，我够不着。
soft|This pillow is lovely and soft.|这个枕头软软的，很舒服。
mistake|I think there's a mistake on the bill.|我觉得账单上有个错误。
error|The screen keeps showing an error message.|屏幕一直显示错误提示。
fault|It wasn't your fault that the train was late.|火车晚点不是你的错。
inaccuracy|There's an inaccuracy in the address on this form.|这张表上的地址有一处不准确。
present|Have you bought Mum a birthday present yet?|你给妈妈买生日礼物了吗？
gift|This scarf was a gift from my sister.|这条围巾是我姐姐送的礼物。
dictionary|Look it up in a dictionary if you're not sure.|不确定的话就查一下词典。
madam|Can I take your coat, madam?|女士，需要我帮您拿外套吗？
pretty|What a pretty little garden!|多漂亮的小花园啊！
idea|I've got an idea for dinner tonight.|我想到今晚吃什么了。
teaspoonful|Add a teaspoonful of sugar to the mixture.|往混合物里加满满一茶匙糖。
spoon|Could you get me a clean spoon?|能给我拿把干净的勺子吗？
a few|I've got a few things to do first.|我得先做几件事。
pity|It's a pity you can't come with us.|你不能和我们一起去，真可惜。
advice|Could I ask you for some advice?|我能请你给点建议吗？
voice|I recognised her voice straight away.|我一下就听出了她的声音。
model|Which model of phone have you got?|你的手机是什么型号的？
afraid|Don't be afraid to ask for help.|别怕向别人求助。
deposit|How much is the deposit for the flat?|这套公寓的押金是多少？
instalment|The first instalment is due next Friday.|第一笔分期付款下周五到期。
price|Does the price include breakfast?|这个价格含早餐吗？
millionaire|You don't have to be a millionaire to travel.|不是非得成为百万富翁才能去旅行。
billionaire|Apparently, a billionaire bought that island.|听说有个亿万富翁买下了那座岛。
pay|Can I pay by card?|可以刷卡吗？
payment|Your payment hasn't come through yet.|你的款项还没有到账。
conductor|The conductor checked our tickets on the train.|列车员在火车上检查了我们的车票。
fare|How much is the bus fare to town?|坐公交车去市区多少钱？
note|I've left you a note on the fridge.|我在冰箱上给你留了张便条。
coin|You need a pound coin for this locker.|用这个储物柜需要一枚一英镑硬币。
passenger|The passenger next to me fell asleep.|我旁边的乘客睡着了。
tramp|The old film is about a tramp looking for work.|这部老电影讲的是一个流浪汉找工作的故事。
vagrant|The article describes him as a vagrant.|那篇文章把他称为流浪者。
quiet|Please keep quiet; the baby's asleep.|请小点声，宝宝睡着了。
impossible|It's impossible to hear you with all this noise.|这么吵，我根本听不清你说话。
lemonade|Would you like some ice in your lemonade?|你的柠檬水要加冰吗？
awake|Are you still awake?|你还醒着吗？
alive|I thought the plant was dead, but it's still alive.|我以为这株植物死了，可它还活着。
every|I walk past that shop every morning.|我每天早上都走过那家店。
no one|No one answered the door.|没有人来开门。
someone|There's someone waiting for you outside.|外面有人在等你。
back door|Could you lock the back door before bed?|睡觉前能把后门锁好吗？
dining room|We've put the extra chairs in the dining room.|我们把多出来的椅子放到餐厅了。
toilet|Excuse me, where's the toilet?|请问，厕所在哪里？
bathroom|I'll be out of the bathroom in a minute.|我马上就从浴室出来。
restroom|Is there a restroom on this floor?|这一层有洗手间吗？
washroom|You can wash your hands in the washroom upstairs.|你可以到楼上的洗手间洗手。
loo|I'm just going to the loo.|我去一下厕所。
men's room|The men's room is at the end of the corridor.|男厕所在走廊尽头。
ladies' room|The ladies' room is next to the stairs.|女厕所在楼梯旁边。
story|Tell me the rest of the story.|把故事的后半段讲给我听吧。
thief|The thief took my bag while I was paying.|我付款时，小偷拿走了我的包。
dark|Let's get home before it gets dark.|我们天黑前回家吧。
torch|Have you got a torch? I can't see anything.|你有手电筒吗？我什么都看不见。
parrot|Their parrot can say hello.|他们家的鹦鹉会说你好。
exercise book|Write the date in your exercise book.|在练习本上写下日期。
customer|There's a customer waiting at the till.|有位顾客正在收银台等着。
manager|I'll ask the manager if we can change it.|我问问经理能不能换。
counter|You can collect your order at the counter.|你可以在柜台取餐。
road|Be careful when you cross this road.|过这条马路时小心点。
street|We live on the same street.|我们住在同一条街上。
gentleman|The gentleman by the window was here first.|窗边那位先生先来的。
trip|How was your trip to New Zealand?|你去新西兰玩得怎么样？
beard|My brother looks different with a beard.|我哥哥留了胡子，看起来不一样了。
kitten|The kitten is hiding under the sofa.|小猫躲在沙发下面。
dry|Are these towels dry yet?|这些毛巾干了吗？
nuisance|What a nuisance! The lift's broken again.|真麻烦！电梯又坏了。
mean|What do you mean by that?|你那么说是什么意思？
surprise|Don't tell her; it's a surprise.|别告诉她，这是个惊喜。
famous|This town is famous for its cheese.|这座小镇以奶酪闻名。
actress|Who's the actress in that advert?|那条广告里的女演员是谁？
actor|He's my favourite actor.|他是我最喜欢的演员。
track|Let's do one more lap of the track.|我们沿跑道再跑一圈吧。
mile|The station is about a mile from here.|车站离这里大约一英里。
speed|Watch your speed; the limit here is thirty.|注意车速，这里限速三十。
sign|The sign says the shop is closed today.|牌子上写着这家店今天不营业。
driving licence|You'll need your driving licence to hire a car.|租车需要带驾驶执照。
charge|Is there an extra charge for delivery?|送货需要另外付费吗？
darling|Are you ready to go, darling?|亲爱的，你准备好出门了吗？
egypt|My aunt sent me a postcard from Egypt.|我姑妈从埃及寄来了一张明信片。
egyptian|My new colleague is Egyptian.|我的新同事是埃及人。
problem|Is there a problem with your order?|你的订单有什么问题吗？
reporter|A reporter asked me what had happened.|一位记者问我发生了什么。
sensational|The headline is sensational, but the story isn't very interesting.|标题很耸人听闻，内容却没什么意思。
mink coat|She inherited a mink coat from her grandmother.|她继承了祖母的一件貂皮大衣。
mink|Is that real mink or fake fur?|那是真貂皮还是人造皮草？
journalist|My cousin works as a journalist.|我的表姐是记者。
report|Could you send me a copy of the report?|能给我发一份报告吗？
future|Have you made any plans for the future?|你对未来有什么打算吗？
feature|My favourite feature is the built-in timer.|我最喜欢的功能是内置定时器。
get married|They're planning to get married next spring.|他们打算明年春天结婚。
description|The flat doesn't match the description in the advert.|这套公寓和广告里描述的不一样。
married|How long have you been married?|你结婚多久了？
football|Do you fancy a game of football after work?|下班后想踢场足球吗？
soccer|My daughter plays soccer on Saturdays.|我女儿每周六踢足球。
pool|The hotel has an indoor pool.|这家酒店有室内游泳池。
world|I'd love to travel around the world one day.|我很想有一天去环游世界。
word|What's the English word for this?|这个用英语怎么说？
depend|We depend on the bus to get to work.|我们靠公交车上下班。
overseas|Have you ever worked overseas?|你在国外工作过吗？
engineering|She's studying engineering at university.|她在大学学工程。
company|How long have you been with this company?|你在这家公司待多久了？
line|The line's bad; can you call me back?|线路不太好，你能重新打给我吗？
excited|I'm really excited about my first day at work.|想到第一天上班，我特别兴奋。
middle-aged|A middle-aged man came in asking for you.|一个中年男子进来找你。
opposite|The pharmacy is opposite the supermarket.|药店在超市对面。
funny|That video was really funny.|那个视频真的很好笑。
powder|I need a little face powder before we go out.|出门前我得扑一点粉。
compact|She checked her lipstick in her compact.|她对着粉盒里的镜子检查口红。
ugly|I don't like that ugly old carpet.|我不喜欢那块难看的旧地毯。
amused|Dad wasn't amused when he saw the mess.|爸爸看到这一团糟，可笑不出来。
embarrassed|I was embarrassed when I forgot his name.|我忘了他的名字，当时很尴尬。
worried|I'm worried about the interview tomorrow.|我有点担心明天的面试。
round|We need a round table for this corner.|这个角落得放一张圆桌。
wood|We walked through a small wood by the river.|我们穿过了河边的一片小树林。
forest|There's a walking trail through the forest.|森林里有条步行小路。
jungle|We saw monkeys during our trip through the jungle.|我们穿越丛林时看到了猴子。
beauty spot|This lake is a popular local beauty spot.|这个湖是当地很受欢迎的风景点。
spot|This looks like a good spot for a picnic.|这里看起来挺适合野餐。
hundred|There were about a hundred people at the wedding.|婚礼上大约有一百人。
visitor|We have a visitor from our Sydney office today.|今天悉尼办事处有位同事来访。
guest|We've got a guest staying this weekend.|这周末有位客人来我们家住。
litter|Please take your litter home with you.|请把垃圾带走。
litter basket|There's a litter basket beside the park gate.|公园门旁有个废纸篓。
rubbish|Could you take the rubbish out?|能把垃圾拿出去吗？
garbage|The garbage truck comes on Monday mornings.|垃圾车每周一早上来。
cover|Cover the bowl and put it in the fridge.|把碗盖好，放进冰箱。
piece|Would you like another piece of cake?|你想再吃一块蛋糕吗？
tyre|I think we've got a flat tyre.|我觉得我们有个轮胎没气了。
rusty|This old bike chain is really rusty.|这条旧自行车链条锈得很厉害。
""", """
ability|This job needs the ability to work well with others.|这份工作要求能与人良好合作。
able|Will you be able to come tomorrow?|你明天能来吗？
accept|Do you accept cash?|你们收现金吗？
account|I'd like to open a bank account.|我想开一个银行账户。
act|Try to act naturally in front of the camera.|面对镜头时尽量表现得自然些。
action|We need to take action before it gets worse.|我们得在情况恶化前采取行动。
activity|Swimming is my favourite weekend activity.|游泳是我周末最喜欢的活动。
add|Can you add my name to the list?|能把我的名字加到名单上吗？
additional|Is there an additional fee for parking?|停车要另外收费吗？
age|We're about the same age.|我们年龄差不多。
agree|I agree with you on that.|这点我同意你的看法。
all|Have you packed all your things?|你的东西都收拾好了吗？
allow|Do they allow dogs in this café?|这家咖啡馆允许带狗进去吗？
alone|I don't like walking home alone at night.|我不喜欢晚上一个人走回家。
analysis|Could you explain the results of your analysis?|能解释一下你的分析结果吗？
angry|Are you still angry with me?|你还在生我的气吗？
annual|Our annual staff dinner is next Friday.|我们的年度员工聚餐在下周五。
appear|Your name doesn't appear on the booking.|预订记录上没有你的名字。
apply|I'd like to apply for this job.|我想申请这份工作。
approach|Let's try a different approach.|我们换个方法试试吧。
appropriate|Are jeans appropriate for the interview?|穿牛仔裤去面试合适吗？
area|Do you know this area well?|你熟悉这一带吗？
argue|I don't want to argue about it.|我不想为这件事争吵。
art|We went to an art gallery yesterday.|我们昨天去了一家美术馆。
article|Have you read this article about sleep?|你看过这篇关于睡眠的文章吗？
assumption|I made the wrong assumption about the meeting time.|我把开会时间想错了。
attach|Don't forget to attach your CV to the email.|别忘了在邮件里附上简历。
attend|Will you be able to attend the meeting?|你能来参加会议吗？
attention|Pay attention to the signs at the entrance.|注意看入口处的标志。
attitude|I like her positive attitude towards work.|我喜欢她积极的工作态度。
available|Are any tables available for tonight?|今晚还有空桌吗？
average|On average, I spend an hour travelling to work.|我上班路上平均要花一个小时。
avoid|Let's leave early to avoid the traffic.|我们早点出发，避开拥堵吧。
aware|I wasn't aware that the time had changed.|我不知道时间改了。
ball|Could you throw the ball back?|能把球扔回来吗？
basic|I'll show you the basic steps first.|我先给你演示基本步骤。
beat|I can never beat my brother at chess.|我下国际象棋从来赢不了哥哥。
been|Have you been to this restaurant before?|你以前来过这家餐厅吗？
begin|What time does the film begin?|电影几点开始？
behavior|His behavior at dinner was quite rude.|他吃饭时的举止挺没礼貌的。
being|Stop being so hard on yourself.|别对自己这么苛刻。
benefit|A big benefit of this job is the flexible hours.|这份工作的一大好处是时间灵活。
billion|The project will cost over a billion dollars.|这个项目将耗资超过十亿美元。
body|My whole body aches after that long walk.|走了那么远的路，我全身都酸痛。
both|Both of my parents work here.|我父母都在这里工作。
brain|My brain needs a break from all these numbers.|看了这么多数字，我得让脑子歇歇。
budget|That's a bit over our budget.|那有点超出我们的预算了。
business|My sister runs a small cleaning business.|我姐姐经营一家小型保洁公司。
campaign|They campaign for safer roads near schools.|他们开展活动，呼吁改善学校附近的道路安全。
capital|What's the capital of New Zealand?|新西兰的首都是什么？
career|I'm thinking about a career in engineering.|我在考虑从事工程行业。
cause|Do you know the cause of the delay?|你知道延误的原因吗？
center|The hotel is right in the city center.|酒店就在市中心。
central|We're looking for a flat in a central location.|我们在找一套位于中心地段的公寓。
certain|Are you certain this is the right address?|你确定地址没错吗？
challenge|Learning to drive was a real challenge for me.|学开车对我来说真是个挑战。
chance|Is there any chance of an earlier appointment?|有可能预约早一点的时间吗？
character|Who's your favourite character in the film?|你最喜欢电影里的哪个角色？
check|Let me check my diary first.|我先查一下日程。
claim|Can I claim the cost of my train ticket?|我的火车票费用能报销吗？
class|I've got an English class this evening.|我今天晚上有英语课。
clear|Is that clear, or shall I explain it again?|听明白了吗，还是需要我再解释一遍？
collect|What time can I collect the keys?|我几点可以来取钥匙？
college|We met when we were at college.|我们上大学时认识的。
common|It's a common mistake, so don't worry.|这种错误很常见，别担心。
community|There's a free class at the community centre.|社区中心有一门免费课程。
compare|Let's compare the prices before we buy anything.|买之前我们先比比价格吧。
complete|Is this the complete set?|这是完整的一套吗？
complex|The instructions are more complex than I expected.|说明比我想的要复杂。
computer|My computer keeps freezing.|我的电脑总是卡住。
concern|These changes don't concern our department.|这些变动与我们部门无关。
condition|The bike is second-hand but in good condition.|这辆自行车是二手的，但车况很好。
conference|She's at a conference until Thursday.|她在参加一个会议，要到周四才结束。
conflict|There's a conflict between the two bookings.|这两项预订的时间有冲突。
connect|How do I connect to the Wi-Fi?|怎么连接无线网络？
connection|We've lost our internet connection.|我们断网了。
consider|Would you consider working part-time?|你愿意考虑做兼职吗？
contain|Does this sauce contain nuts?|这个酱里有坚果吗？
content|I like the content, but the text is too small.|内容我喜欢，就是字太小了。
context|That sentence makes more sense in context.|结合上下文，那句话就更好理解了。
continue|Shall we continue after lunch?|我们午饭后继续好吗？
contract|Read the contract carefully before you sign it.|签字前仔细阅读合同。
control|I've lost control of the volume on my phone.|我调不了手机的音量了。
couple|I'll be ready in a couple of minutes.|我再过几分钟就好了。
course|I'm taking a course in basic plumbing.|我在上一门基础水暖课程。
creative|That's a creative way to use the space.|这样利用空间很有创意。
culture|I'd like to learn more about the local culture.|我想多了解一些当地文化。
current|What's your current address?|你现在的地址是什么？
cute|Your puppy is so cute!|你的小狗真可爱！
damage|Be careful not to damage the floor.|小心别弄坏地板。
dangerous|It's dangerous to use your phone while driving.|开车时用手机很危险。
dare|I wouldn't dare ask him that.|我可不敢那么问他。
data|I've nearly used up my mobile data.|我的手机流量快用完了。
dead|My phone's dead; can I use yours?|我手机没电了，能用一下你的吗？
deal|How do you deal with difficult customers?|你怎么应对难缠的顾客？
decide|Have you had time to decide?|你有时间考虑好了吗？
decision|It's a big decision, so take your time.|这可是个大决定，慢慢考虑。
deep|The water is too deep to stand in.|水太深了，站不到底。
demand|You can't demand a reply straight away.|你不能要求别人立刻回复。
democratic|Let's take a vote; that's the democratic way.|我们投票吧，这样比较民主。
detail|Could you explain that in a bit more detail?|能再详细解释一下吗？
develop|I'd like to develop my speaking skills.|我想提高口语能力。
did|What did you have for lunch?|你午饭吃了什么？
die|These plants will die without water.|这些植物没有水会死的。
difference|What's the difference between these two tickets?|这两种票有什么区别？
different|Could I try a different size?|我能试试别的尺码吗？
digital|Do I need a printed ticket or a digital one?|我需要纸质票还是电子票？
direct|Is there a direct flight to Auckland?|有直飞奥克兰的航班吗？
direction|I think we're going in the wrong direction.|我觉得我们走错方向了。
discuss|Can we discuss this somewhere quieter?|我们能找个安静点的地方谈吗？
divide|Let's divide the bill equally.|我们平摊账单吧。
do|What do you do for a living?|你是做什么工作的？
document|Could you sign this document here?|你能在这份文件的这里签字吗？
does|What time does your shift start?|你几点开始上班？
draw|Could you draw me a quick map?|能给我简单画个路线图吗？
drug|Ask the pharmacist how to take this drug.|问问药剂师这种药怎么服用。
due|The rent is due on Friday.|租金周五到期。
each|These apples cost fifty pence each.|这些苹果每个五十便士。
economic|Have the economic changes affected your business?|经济上的变化影响到你的生意了吗？
economy|The local economy depends a lot on tourism.|当地经济很大程度上依靠旅游业。
effect|The new curtains have a lovely effect.|新窗帘装上去效果很不错。
effective|This is a simple but effective way to practise.|这是个简单却有效的练习方法。
effort|Thanks for all the effort you've put into this.|谢谢你为这件事付出的努力。
election|Are you going to vote in the next election?|下次选举你准备去投票吗？
employer|My employer pays for my training.|我的雇主支付我的培训费用。
""", """
encourage|We try to encourage the children to read.|我们尽量鼓励孩子们阅读。
end|The toilets are at the end of the hall.|厕所在大厅尽头。
energy|I haven't got the energy to cook tonight.|我今晚没精力做饭了。
environment|I prefer a quiet working environment.|我更喜欢安静的工作环境。
equal|Cut the cake into six equal pieces.|把蛋糕切成六块一样大的。
equipment|Do I need to bring any equipment?|我需要带什么设备吗？
even|I haven't even had breakfast yet.|我连早饭都还没吃。
event|Are you going to the work event on Friday?|周五的公司活动你去吗？
evidence|Do you have any evidence to support that?|你有什么证据支持那个说法吗？
exact|I can't remember the exact date.|我记不清具体日期了。
examine|The mechanic will examine the engine tomorrow.|修理工明天会检查发动机。
example|Could you give me an example?|能给我举个例子吗？
excellent|The food here is excellent.|这里的菜非常好吃。
exist|Does that shop still exist?|那家店还在吗？
expect|What time do you expect to arrive?|你预计几点到？
experience|Do you have any experience working in a café?|你有在咖啡馆工作的经验吗？
explain|Could you explain how this works?|能解释一下这个怎么用吗？
explanation|Thanks, that explanation really helped.|谢谢，那番解释确实帮到我了。
express|I find it hard to express myself in English.|我觉得用英语表达自己的想法挺难。
eye|I've got something in my eye.|我眼睛里进东西了。
face|You've got a bit of chocolate on your face.|你脸上沾了一点巧克力。
fact|I didn't know that; it's an interesting fact.|我以前不知道，那挺有意思的。
fair|That doesn't seem fair to the other people waiting.|那对其他等着的人似乎不公平。
false|It was a false alarm; nothing was wrong.|那是虚惊一场，什么事都没有。
familiar|Your name sounds familiar. Have we met before?|你的名字很耳熟，我们以前见过吗？
feeling|I've got a feeling I've forgotten something.|我总觉得忘了什么东西。
few|There are very few buses on Sundays.|周日的公交车班次很少。
field|There's a field of sheep behind the house.|房子后面有片牧场，里面养着羊。
fight|Please don't fight over the remote.|别为遥控器吵架了。
figure|That figure seems too high. Can you check it?|那个数字似乎太高了，能核对一下吗？
fill|Could you fill this bottle with water?|你能把这个瓶子装满水吗？
final|Is that your final decision?|那是你最后的决定吗？
fire|We sat by the fire with a cup of tea.|我们端着茶坐在炉火旁。
fit|These shoes don't fit me properly.|这双鞋不太合脚。
fix|Do you know anyone who can fix a washing machine?|你认识会修洗衣机的人吗？
flat|The path is quite flat, so it's an easy walk.|这条小路挺平坦，走起来不费力。
follow|Just follow me; I know the way.|跟着我就行，我认识路。
force|Don't use too much force on that handle.|别太用力扳那个把手。
foreign|I'm still getting used to speaking a foreign language.|我还在慢慢适应用外语交流。
form|Please fill in this form before your appointment.|请在赴约前填好这张表。
formal|Do I need to wear formal clothes?|我需要穿正装吗？
forward|Could you move forward a little?|你能往前挪一点吗？
free|Are you free for lunch tomorrow?|你明天中午有空一起吃饭吗？
freedom|I like having the freedom to choose my hours.|我喜欢能自由选择工作时间。
gain|You can gain useful experience in this role.|你能在这个岗位上积累有用的经验。
game|Who won the game last night?|昨晚的比赛谁赢了？
general|In general, I prefer working in the morning.|总的来说，我更喜欢上午工作。
get|What time do you get home from work?|你下班几点到家？
global|The company sells its products to a global market.|这家公司面向全球市场销售产品。
goal|My goal is to feel confident speaking English.|我的目标是能自信地说英语。
government|Is this a government website?|这是政府网站吗？
great|It was great to see you again.|很高兴再次见到你。
ground|Don't leave your bag on the wet ground.|别把包放在湿地上。
group|Would you like to join our study group?|你想加入我们的学习小组吗？
growth|There's been a lot of growth in this area.|这一带发展很快。
hair|Have you had your hair cut?|你剪头发了吗？
half|I'll have half now and save the rest.|我现在吃一半，剩下的留着。
hang|You can hang your coat here.|你可以把外套挂在这里。
happy|I'm happy with the way it turned out.|我对结果挺满意的。
has|Has anyone seen my glasses?|有人看到我的眼镜了吗？
have|Have you got a minute?|你有空聊一下吗？
head|Watch your head; the door is quite low.|小心碰头，门挺矮的。
health|I'm trying to take better care of my health.|我在努力更好地照顾自己的健康。
heart|My heart was racing before the interview.|面试前我心跳得特别快。
history|Do you know anything about the history of this building?|你了解这栋建筑的历史吗？
hit|I hit my knee on the table.|我的膝盖撞到桌子上了。
hold|Could you hold the door for me?|能帮我扶一下门吗？
hospital|Which hospital does your sister work at?|你姐姐在哪家医院工作？
huge|That's a huge suitcase for a weekend trip!|就去个周末，带这么大的行李箱啊！
human|It's only human to make mistakes.|人都会犯错的。
identify|Can you identify which cable is causing the problem?|你能确定是哪根电缆出问题了吗？
ill|I'm feeling ill, so I'll stay home today.|我不舒服，今天就待在家里了。
imagine|I can't imagine living somewhere that cold.|我无法想象住在那么冷的地方。
immediate|Does this need immediate attention, or can it wait?|这件事得马上处理，还是可以等等？
impact|Will the roadworks impact our deliveries?|道路施工会影响我们送货吗？
important|It's important to ask if you don't understand.|不懂就问，这很重要。
improve|I'd like to improve my pronunciation.|我想改善发音。
include|Does the rent include electricity?|房租包含电费吗？
income|My income changes from month to month.|我每个月的收入不一样。
increase|There's been an increase in our electricity bill.|我们的电费涨了。
independent|My daughter is becoming more independent.|我女儿越来越独立了。
indicate|The arrows indicate which way to go.|箭头指明了该往哪边走。
individual|Can we get individual bills, please?|我们可以各自结账吗？
industry|How did you get into the construction industry?|你是怎么进入建筑行业的？
inform|Please inform reception if you're expecting a delivery.|如果有东西要送来，请告知前台。
information|Where can I find more information about the course?|在哪里能找到更多课程信息？
inside|Let's wait inside; it's getting cold.|我们到里面等吧，外面越来越冷了。
interest|I've always had an interest in cars.|我一直对汽车感兴趣。
international|Is this the queue for international flights?|这是国际航班的队伍吗？
interview|I've got a job interview on Monday.|我周一有个工作面试。
investment|A good pair of work boots is a worthwhile investment.|买双好工作靴很值得。
involve|Does the job involve much travelling?|这份工作需要经常出差吗？
issue|There's an issue with the online booking system.|网上预订系统出了点问题。
item|One item is missing from my order.|我的订单少了一件东西。
join|Would you like to join us for dinner?|你想和我们一起吃晚饭吗？
kill|Too much water can kill this plant.|浇太多水会让这株植物死掉。
kind|What kind of music do you like?|你喜欢哪种音乐？
know|Do you know where I left my keys?|你知道我把钥匙放哪了吗？
knowledge|You don't need any previous knowledge to take this class.|上这门课不需要任何基础。
land|We could see land from the boat.|我们从船上能看见陆地。
language|What language do you speak at home?|你在家说什么语言？
laugh|That story always makes me laugh.|那个故事总能把我逗笑。
law|Is there a law about parking here?|这里停车有什么法律规定吗？
lead|Where's the lead for the dog?|狗的牵引绳在哪里？
leader|Our team leader is away this week.|我们的组长这周不在。
learn|I'd like to learn how to cook.|我想学做饭。
legal|Is it legal to park here overnight?|在这里停一整晚合法吗？
let|Let me know when you get home.|到家后告诉我一声。
level|Which class would suit my level of English?|哪门课适合我的英语水平？
life|What's life like in your new town?|你在新城镇生活得怎么样？
likely|Is the train likely to be on time?|火车大概能准时到吗？
limited|Parking is limited, so try to arrive early.|停车位有限，尽量早点来。
literature|She's studying English literature at university.|她在大学学习英国文学。
local|Is there a local bus to the beach?|当地有去海滩的公交车吗？
location|Could you send me your location?|能把你的位置发给我吗？
lonely|I felt lonely when I first moved here.|刚搬来时我觉得很孤独。
lose|Be careful not to lose your ticket.|小心别把票弄丢了。
lot|Thanks a lot for helping me move.|非常感谢你帮我搬家。
main|The main entrance is on the other side.|正门在另一边。
major|We've had a major problem with the heating.|我们的供暖出了大问题。
management|I'll pass your comments on to management.|我会把你的意见转达给管理层。
market|I bought these vegetables at the market.|这些蔬菜是我在市场买的。
material|What material is this jacket made of?|这件夹克是什么料子的？
measure|Let's measure the space before we buy a sofa.|买沙发前我们先量一下空间。
media|I try not to spend too long on social media.|我尽量不在社交媒体上花太多时间。
""", """
medical|Do you need any medical help?|你需要医疗帮助吗？
meeting|Can we move the meeting to Thursday?|我们能把会议改到周四吗？
member|Do you have to be a member to use the pool?|使用泳池一定得是会员吗？
memory|I have a terrible memory for names.|我特别记不住人名。
mental|I need a proper break for my mental health.|为了心理健康，我需要好好休息一下。
mention|Did she mention what time she'd be back?|她有没有说几点回来？
message|Could you leave him a message for me?|能帮我给他留个言吗？
method|Your method is much quicker than mine.|你的方法比我的快多了。
midnight|We didn't get home until midnight.|我们直到午夜才到家。
might|I might be a few minutes late.|我可能会迟到几分钟。
military|My grandfather used to fly military planes.|我爷爷以前开军用飞机。
million|That house costs over a million pounds.|那套房子售价超过一百万英镑。
mind|Would you mind opening the window?|你介意把窗户打开吗？
modern|The kitchen is small but modern.|厨房虽小，装修却很现代。
month|I've been here for just over a month.|我来这里刚一个多月。
movement|I noticed some movement behind the curtains.|我注意到窗帘后面有什么在动。
music|Could we put some music on?|我们放点音乐好吗？
nation|The whole nation was watching the final.|全国都在看决赛。
national|Is Monday a national holiday?|周一是全国性的假日吗？
natural|Try to use a natural speaking voice.|尽量用自然的声音说话。
nature|I love spending time in nature.|我喜欢到大自然里走走。
necessary|Is it necessary to book in advance?|必须提前预订吗？
negative|Try not to be so negative about everything.|尽量别对所有事都那么消极。
network|Which mobile network do you use?|你用哪家手机运营商的网络？
nine|I'll meet you outside at nine.|我九点在外面和你碰面。
noon|The parcel should arrive before noon.|包裹应该会在中午前到。
normal|Is that noise normal for this machine?|这台机器发出那种声音正常吗？
notice|There's a notice on the door about opening times.|门上有张营业时间通知。
obvious|It wasn't obvious which door we should use.|当时看不出我们该走哪扇门。
official|Have you had the official confirmation yet?|你收到正式确认了吗？
operation|My aunt is recovering from an operation.|我姑妈正在术后恢复中。
opportunity|Thanks for giving me the opportunity to try.|谢谢你给我尝试的机会。
option|Taking the bus is probably our best option.|坐公交车可能是我们最好的选择。
order|I'd like to change my order, please.|我想改一下订单。
organization|She works for a charity organization.|她在一家慈善机构工作。
organize|Who's going to organize the leaving party?|谁来组织欢送会？
original|Do you need the original or will a copy do?|你需要原件，还是复印件也行？
outside|I'll wait outside the main entrance.|我会在正门外面等。
parent|A parent needs to sign this form.|这张表需要一位家长签字。
part|Which part of the instructions don't you understand?|说明的哪一部分你不明白？
particular|Is there a particular time that suits you?|你有没有什么特别方便的时间？
partner|My partner is picking me up after work.|我的伴侣下班后来接我。
passage|Read the passage and answer the questions below.|阅读这段文章，然后回答下面的问题。
past|Things were different in the past.|过去的情况不一样。
patient|Thanks for being so patient with me.|谢谢你对我这么有耐心。
pattern|I love the pattern on those curtains.|我喜欢那些窗帘上的图案。
perfect|That time is perfect for me.|那个时间对我来说正合适。
perform|My daughter is going to perform in the school play.|我女儿要参加学校话剧演出。
performance|Did you enjoy the performance last night?|你喜欢昨晚的演出吗？
period|Is there a trial period before I sign up?|正式报名之前有试用期吗？
permanent|Are you looking for a permanent job?|你在找长期工作吗？
permission|Do we need permission to park here?|我们在这里停车需要许可吗？
personal|I'd rather not share my personal details.|我不太想透露个人信息。
physical|This job involves a lot of physical work.|这份工作有很多体力活。
pick|You can pick whichever colour you like.|你可以挑自己喜欢的颜色。
plain|Do you have a plain white T-shirt?|你们有纯白色的T恤吗？
plan|What's the plan for tomorrow?|明天有什么安排？
plant|Could you water this plant while I'm away?|我不在时，你能给这株植物浇水吗？
player|We need one more player for our team.|我们队还差一名队员。
point|I see your point, but I'm not sure I agree.|我明白你的意思，但不一定赞同。
police|Someone has already called the police.|已经有人报警了。
policy|What's your policy on returns?|你们的退货政策是什么？
political|I'd rather not get into a political argument.|我不太想卷入政治争论。
popular|This café is popular with people who work nearby.|这家咖啡馆很受附近上班族欢迎。
population|What's the population of your hometown?|你家乡有多少人口？
position|Is the part-time position still available?|那个兼职岗位还招人吗？
positive|I'm trying to stay positive about the move.|对于搬家这件事，我在尽量保持乐观。
possible|Would it be possible to check in early?|可以提前办理入住吗？
potential|This place has potential, but it needs a lot of work.|这地方有潜力，但需要好好整修。
power|The power went off while I was cooking.|我做饭时停电了。
powerful|This vacuum cleaner is surprisingly powerful.|这台吸尘器的吸力出乎意料地强。
practical|Those shoes look nice, but are they practical for work?|那双鞋好看，但上班穿实用吗？
practice|I need more practice speaking on the phone.|我需要多练习打电话交流。
prepare|How should I prepare for the interview?|我该怎么准备面试？
press|Press this button to open the door.|按这个按钮开门。
pressure|I'm under a lot of pressure at work.|我工作压力很大。
prevent|How can we prevent this from happening again?|我们怎样才能防止这事再次发生？
primary|My son starts primary school in September.|我儿子九月开始上小学。
private|Could we have a private chat?|我们能单独聊聊吗？
produce|Does this factory produce car parts?|这家工厂生产汽车零件吗？
product|Can I return this product if it doesn't work?|如果这件产品不能用，我能退货吗？
production|Production has stopped while they repair the machine.|他们修机器期间，生产暂停了。
professional|You did a very professional job.|你做得很专业。
profit|Did you make a profit when you sold the car?|你把车卖掉时赚到钱了吗？
program|Which program do you use to edit photos?|你用哪个程序修照片？
progress|I feel like I'm making progress with my English.|我感觉我的英语在进步。
project|How's your project going?|你的项目进展怎么样？
promote|We're using posters to promote the school fair.|我们在用海报宣传学校义卖会。
proper|I haven't had a proper meal all day.|我一整天都没好好吃顿饭。
property|Who owns the property next door?|隔壁那处房产是谁的？
prove|Can you prove that you bought it here?|你能证明是在这里买的吗？
provide|Do you provide any training for new staff?|你们给新员工提供培训吗？
public|Is this garden open to the public?|这个花园对公众开放吗？
pull|Pull the door towards you.|把门朝你这边拉。
purpose|What's the purpose of this button?|这个按钮有什么用？
push|You'll need to push the door a bit harder.|你得再用点力推门。
quality|The quality is better than I expected.|质量比我预想的好。
quantity|Can I order a smaller quantity?|我能少订一点吗？
quick|Have we got time for a quick coffee?|我们有时间喝杯咖啡吗？
raise|Could you raise your voice a little?|你能稍微说大声一点吗？
range|They have a good range of work clothes.|他们的工作服种类挺齐全。
rate|What's the hourly rate for this job?|这份工作的时薪是多少？
reach|I can't reach the cups on the top shelf.|我够不着最上层架子上的杯子。
reaction|What was her reaction when you told her?|你告诉她时，她是什么反应？
real|Is that a real plant or a plastic one?|那是真植物还是塑料的？
realize|I didn't realize you were waiting for me.|我没意识到你在等我。
reason|Is there a reason why the appointment was cancelled?|预约取消有什么原因吗？
recent|Do you have a recent photo of yourself?|你有最近拍的个人照片吗？
reception|Leave the keys at reception when you check out.|退房时把钥匙留在前台。
recognition|She deserves more recognition for her work.|她的工作值得得到更多肯定。
record|Can I record this so I can listen again later?|我能录下来，之后再听一遍吗？
reference|Keep this email for future reference.|留着这封邮件，以后可以查阅。
reflect|The windows reflect the light into the room.|窗户把光反射进房间里。
regular|I'd like to get into a regular study routine.|我想养成规律学习的习惯。
relationship|I have a good relationship with my neighbours.|我和邻居相处得很好。
release|When will they release the next episode?|他们什么时候发布下一集？
relevant|Do you have any experience relevant to this job?|你有与这份工作相关的经验吗？
religious|Are there any religious festivals you celebrate?|你过哪些宗教节日吗？
remote|They live in a remote village in the mountains.|他们住在山里一个偏僻的村庄。
replace|We need to replace the batteries.|我们需要换电池了。
represent|Who's going to represent our team at the meeting?|谁代表我们团队去开会？
requirement|Is previous experience a requirement for this role?|这个岗位必须有相关经验吗？
research|I've done some research on places to stay.|我查了一些住宿的地方。
resource|The library is a useful resource for language learners.|图书馆是语言学习者的实用资源。
responsible|Who's responsible for locking up tonight?|今晚谁负责锁门？
""", """
result|When will you get your test result?|你什么时候能拿到考试结果？
reveal|Don't reveal the ending; I haven't seen it yet.|别透露结局，我还没看呢。
revenue|Most of our revenue comes from repeat customers.|我们大部分收入来自回头客。
review|I left a review of the hotel online.|我在网上给那家酒店写了评价。
ride|Can you ride a bike?|你会骑自行车吗？
role|What's your role in the team?|你在团队里负责什么？
roll|Roll your sleeves up before you start.|开始之前先把袖子卷起来。
rough|The road gets quite rough after the bridge.|过了桥之后，路就挺颠簸的。
rule|Is there a rule about using phones here?|这里对使用手机有什么规定吗？
sad|I was sad to hear that you're leaving.|听说你要走了，我很难过。
safe|Is it safe to swim here?|在这里游泳安全吗？
save|Don't forget to save your work.|别忘了保存你做的东西。
scale|What's the scale on this map?|这张地图的比例尺是多少？
science|I enjoyed science when I was at school.|我上学时很喜欢科学课。
sea|You can see the sea from our window.|从我们家的窗户能看见海。
section|You'll find it in the frozen food section.|你会在冷冻食品区找到它。
security|We have to go through security before boarding.|登机前我们得过安检。
seem|You seem a bit tired today.|你今天看起来有点累。
sense|Does that make sense to you?|你能理解那个意思吗？
series|Have you watched the new series everyone's talking about?|你看过大家都在讨论的那部新剧吗？
serious|Are you serious about moving abroad?|你是认真打算搬到国外吗？
service|Is service included in the bill?|账单里包含服务费吗？
several|I've called several times, but nobody answers.|我打了好几次电话，但都没人接。
share|Shall we share a taxi?|我们合乘一辆出租车好吗？
shoot|We're going to shoot a short video in the park.|我们要在公园拍一段短视频。
sick|I feel sick when I read in the car.|我在车里看书会恶心。
side|Which side of the road should we wait on?|我们该在哪边路旁等？
significant|There's a significant difference in price.|价格差得挺大。
similar|I've got a jacket very similar to yours.|我有一件和你那件很像的夹克。
simple|Can you show me a simple way to do it?|你能教我一种简单的做法吗？
sing|I like to sing along when this song comes on.|每次放这首歌，我都喜欢跟着唱。
single|I'd like a single room for two nights.|我想订一间单人房，住两晚。
sit|Is it OK if I sit here?|我坐这里可以吗？
situation|It's an awkward situation, but we can sort it out.|情况有点尴尬，但我们能解决。
slow|The internet is really slow this evening.|今天晚上的网速特别慢。
social|I'm trying to have more of a social life.|我想多参加一些社交活动。
society|Everyone should feel welcome in our society.|社会应该让每个人都感到被接纳。
solution|Have you found a solution to the problem?|你找到解决这个问题的办法了吗？
sound|Does that sound like a good plan?|你觉得这个计划怎么样？
space|Is there enough space for another chair?|还有地方再放一把椅子吗？
special|Are you doing anything special for your birthday?|你生日有什么特别安排吗？
specific|Is there a specific entrance we should use?|我们应该从某个指定入口进去吗？
sport|What's your favourite sport to watch?|你最喜欢看什么体育运动？
spread|Spread a little butter on the toast.|在吐司上抹一点黄油。
staff|The staff here are always helpful.|这里的员工总是很乐于帮忙。
star|You can see a bright star just above the trees.|你能看到树梢上方有一颗亮星。
start|What time would you like to start?|你想几点开始？
state|Which state do you live in?|你住在哪个州？
step|What's the next step?|下一步是什么？
strange|There's a strange smell coming from the fridge.|冰箱里传来一股怪味。
strategy|What's your strategy for remembering new words?|你用什么方法记新单词？
strength|I haven't got the strength to lift it alone.|我一个人没力气把它抬起来。
stress|Walking helps me deal with stress.|散步能帮我缓解压力。
strong|This coffee is a bit too strong for me.|这杯咖啡对我来说有点太浓了。
structure|The structure of the course is easy to follow.|这门课程的结构很清晰，容易跟上。
study|Where do you usually study?|你通常在哪里学习？
succeed|You'll need practice if you want to succeed.|要想成功，你就得练习。
success|The party was a big success.|聚会办得很成功。
successful|Was your application successful?|你的申请通过了吗？
such|Thanks for being such a good friend.|谢谢你一直这么够朋友。
suggest|What would you suggest for a quick dinner?|想快点做顿晚饭，你有什么建议？
supply|The water supply will be off this afternoon.|今天下午会停水。
support|Thanks for offering to support me during the move.|谢谢你愿意在搬家期间帮我。
surface|Put it on a clean, dry surface.|把它放在干净、干燥的表面上。
survey|Would you mind filling in a short survey?|你介意填一份简短的问卷吗？
system|The heating system isn't working properly.|供暖系统运转不正常。
tax|Does that price include tax?|那个价格含税吗？
teach|Could you teach me how to make this?|你能教我怎么做这个吗？
team|Welcome to the team!|欢迎加入团队！
technology|I'm not very confident with new technology.|面对新技术，我不太有把握。
term|When does the new school term begin?|新学期什么时候开始？
theory|It sounds good in theory, but will it work?|理论上听起来不错，但行得通吗？
thick|Take a thick jumper; it'll be cold tonight.|带件厚毛衣，今晚会冷。
think|What do you think of this colour?|你觉得这个颜色怎么样？
those|Are those your shoes by the door?|门旁那些鞋是你的吗？
thousand|The repairs will cost about a thousand dollars.|修理费大概是一千美元。
three|We've got three days left to finish.|我们还剩三天时间完成。
tight|These trousers are a bit tight around the waist.|这条裤子的腰有点紧。
tomorrow|Can I get back to you tomorrow?|我能明天再给你答复吗？
total|What's the total cost, including delivery?|加上运费总共多少钱？
touch|Please don't touch the paint; it's still wet.|请别碰油漆，还没干。
trade|I'd like to learn a trade, perhaps plumbing.|我想学门手艺，比如水暖。
transfer|Can I pay by bank transfer?|我可以通过银行转账付款吗？
transport|Is there any public transport near the hotel?|酒店附近有公共交通吗？
treat|Please treat my tools with care.|请爱惜我的工具。
treatment|How is your treatment going?|你的治疗进展怎么样？
trial|Can I have a free trial before I subscribe?|订阅前我能免费试用吗？
true|Is it true that you're moving to Australia?|听说你要搬去澳大利亚，是真的吗？
trust|I trust you to keep this to yourself.|我相信你会替我保密。
turn|Turn left at the next set of traffic lights.|到下一个红绿灯左转。
two|Could we have a table for two?|能给我们安排一张两人桌吗？
typical|What's a typical working day like for you?|你平常一天的工作是怎样的？
unique|Each piece of handmade pottery is unique.|每件手工陶器都是独一无二的。
unit|Let's go through the first unit together.|我们一起学第一单元吧。
upper|Our seats are on the upper deck.|我们的座位在上层。
use|Can I use your charger for a minute?|我能用一下你的充电器吗？
used|I'm getting used to the early starts.|我正慢慢习惯一大早开始工作。
useful|Thanks, that's really useful to know.|谢谢，知道这个真的很有用。
usual|I'll see you at the usual place.|我在老地方见你。
value|This lunch is really good value.|这份午餐真划算。
various|I've tried various ways to learn vocabulary.|我尝试过各种学词汇的方法。
vehicle|Is this your vehicle parked outside?|外面停的是你的车吗？
version|Have you installed the latest version of the app?|你安装最新版应用了吗？
view|There's a lovely view from the balcony.|从阳台望出去风景很好。
visit|Thanks for coming to visit us.|谢谢你来看我们。
volume|Could you turn the volume up a bit?|你能把音量调大一点吗？
vote|Let's have a vote on where to eat.|我们投票决定去哪儿吃吧。
war|My grandmother was a child during the war.|战争时期，我奶奶还是个孩子。
warn|I should warn you, the steps are slippery.|我得提醒你，台阶很滑。
weak|My legs feel weak after that long climb.|爬了那么久，我的腿都发软了。
website|You can book an appointment on our website.|你可以在我们的网站上预约。
weight|What's the maximum weight for this bag?|这个行李包的重量上限是多少？
were|Where were you when I called?|我打电话时你在哪儿？
whatever|Choose whatever you like.|你喜欢什么就选什么。
whole|I've been waiting the whole morning.|我等了一整个上午。
wide|How wide is the doorway?|门口有多宽？
wild|We saw wild rabbits in the park.|我们在公园里看到了野兔。
wish|I wish I could stay a bit longer.|真希望我能再多待一会儿。
wonder|I wonder if the shop is still open.|不知道那家店是不是还开着。
worker|Ask a worker in the shop where the batteries are.|问问店员电池在哪里。
yesterday|I meant to call you yesterday.|我昨天本来想给你打电话的。
zero|The temperature dropped below zero last night.|昨晚气温降到零度以下了。
customs|Do we need to declare this at customs?|这个需要向海关申报吗？
snow|Do you think it'll snow tonight?|你觉得今晚会下雪吗？
shine|Look, the sun is starting to shine.|看，太阳开始出来了。
daily|What's your daily routine like?|你每天一般是怎么安排的？
pure|Is this jumper made of pure wool?|这件毛衣是纯羊毛的吗？
depart|What time does the next train depart?|下一班火车几点出发？
fall|Be careful, that shelf might fall.|小心，那个架子可能会掉下来。
thirstily|The dog drank thirstily from its bowl.|那只狗口渴地喝着碗里的水。
cheer|Let's get some ice cream to cheer you up.|我们去吃点冰激凌，让你开心点。
prosecute|Will they prosecute him for stealing the car?|他们会起诉他偷车吗？
thus|The train was cancelled; thus, we had to drive.|火车取消了，因此我们只好开车去。
whom|To whom should I send this letter?|这封信我应该寄给谁？
hi|Hi, have you got time for a coffee?|嗨，你有空喝杯咖啡吗？
""")
}
