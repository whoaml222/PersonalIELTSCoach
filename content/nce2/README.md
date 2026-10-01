# 新概念第二册离线词包

来源：[用户指定的第二册词表](https://www.ncego.com/books/words/nce2)，核对日期 2026-10-01。

共 96 课、877 条课文词条；同册同拼写词合并为 851 张卡，保留全部课号和不同义项。按首次出现的课文顺序学习，不按字母重新排序。

原始 HTML 快照 SHA-256：`fe38bcdf8b5259bd97678842129493230e6d07f33cf1816dd3622ff4587e38a1`。原始网页仅保留在本机忽略目录 `tmp/nce2/`，不作为 APP 运行依赖。

## 文件

- `vocabulary.tsv`：从网页抽取的词目、音标、简短释义和课号快照，不含课文正文、网站例句或音频。
- `examples.tsv`：为本 APP 编写或沿用本 APP 原有例句的 851 组简短中英例句。每个例句包含目标词，最长 18 个空格分隔词；不依赖运行时 AI 请求。
- `corrections.json`：编辑校正，生成时覆盖错误词性、同形异音词音标及容易误导的释义。保留原始词表，方便复核。例句优先展示一项明确用法，不宣称一句覆盖所有义项。
- `../../scripts/generate_nce2_word_pack.py`（仓库根目录 `scripts/`）：校验完整性并生成 `Nce2WordPack.kt`；大字符串分块，避免 JVM 常量长度限制。

校正例子：lift 的“搭便车”为名词；pastry 不是面糊；advertiser 不是报幕员；wind（蜿蜒）、bow（鞠躬）、close（亲密的）按当前义项使用音标。参考 [Cambridge lift](https://dictionary.cambridge.org/dictionary/english/lift)、[Cambridge advertiser](https://dictionary.cambridge.org/us/dictionary/english/advertiser)、[Oxford wind](https://www.oxfordlearnersdictionaries.com/definition/english/wind2_2)、[Oxford pastry](https://www.oxfordlearnersdictionaries.com/definition/english/pastry)。

词表还包含神话人物、课文虚构地名、旧式或专业用语，不能全当成现代高频口语；例句用简短、明确的情境帮助理解。Pilatus Porter 和 Escalopia 的来源音标无法可靠确认，暂不显示音标，并明确标注待核对，不编造标准读法。在线词典是否收录这些专名、是否能消歧同形异音词，仍取决于原语音服务；本次不更换语音引擎。

## 学习和升级规则

1. 新词统一队列：第一册剩余 NEW 词排在第二册之前。同一批可以跨册，例如第一册剩 2 个，则接着安排第二册 18 个。
2. “第一册学完”指新词均已接触，不要求全部 MASTERED；不阻断第一册后续复习。
3. 每日目标仍为 20 个，完成后主动选择“继续学习 20 个”；两册都学完才显示全词库完成。
4. 两册共享复习的“新概念”一半配额，按到期时间排序；Paul1000 占另一半，一边不足时补足。未学新词和今天刚学的词不进入今日复习。
5. 跨册同拼写词不合并进度：第二册可能引入不同词义，例如 play（戏剧）、hand（指针）。第一册、Paul1000 已有学习状态、复习时间、错词、ID 不改变。
6. 数据库沿用 `(word, source)` 唯一键和 INSERT IGNORE；添加 NCE2 不需要重建数据库或清空数据。更新内容字段时明确指定所属词包，重复初始化不会增加重复卡。
7. 单词、例句、中文、点词释义与语法提示离线可用；发音沿用原有在线英音及播放后缓存机制，不将文本全部预先下载为音频。
8. 学习新词、复习和错词本的点词弹窗优先使用当前卡片的目标词释义和音标，避免被第一册同拼写词的另一义项覆盖。

## 重建

在仓库根目录运行：

```powershell
& .\.tools\reader-nlp\Scripts\python.exe scripts/generate_nce2_word_pack.py
.\gradlew.bat testDebugUnitTest --tests '*ContentInventoryTest'
& .\.tools\reader-nlp\Scripts\python.exe scripts/generate_context_grammar.py
.\gradlew.bat testDebugUnitTest lintDebug assembleDebug assembleRelease
```

重新导入指定网页的本地 HTML 快照使用 `--source-html path/to/source.html`；网页数量不符合 877 / 851 / 96 时拒绝静默覆盖。语法生成沿用本地 spaCy 工具，只读取公开 APP 例句，不混入私人阅读材料。

本次随 v1.7.2 功能更新发布，未增加权限、服务器或 API 密钥。私人阅读材料不随公开源码、APK 或 Release 分发。

## 验证结果（2026-10-01）

- 全量 `testDebugUnitTest`：101 项通过，0 失败、0 跳过，含 11 项 Compose/Robolectric 界面测试。
- 专项覆盖：最后两条第一册接上第二册、第二册每日 20 个及加学、两册合并复习配额、次日复习、重复升级保留 ID/错词/日期/学习状态、第二册专属释义与音标。
- 全部第二册例句都有中文，最长 11 个空格分隔词；全体公开例句离线语法库共 2,626 句，词级偏移覆盖校验通过。
- `lintDebug` 无错误；仍有旧配置的 Android targetSdk 和 DataStore 版本升级提示，以及 5 条数值状态装箱建议，本次未扩大范围升级依赖。
- Debug APK 与 unsigned Release APK 构建成功。已检查 Release 包包含离线词典和语法资产，未包含私人课程、密钥库文件。
- UI 结果来自 JVM 模拟环境，未在实体手机上验证。发布版本号为 1.7.2（versionCode 15）；覆盖安装保留原有学习进度，请勿卸载旧版。
