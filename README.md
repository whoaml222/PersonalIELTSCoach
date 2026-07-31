# Personal IELTS Coach

一个面向英语零基础学习者的原生 Android MVP。应用使用 Kotlin、Jetpack Compose、MVVM、Room、DataStore、WorkManager、OkHttp 与 Android TextToSpeech。

## 已实现

- 首次启动说明与 30 题水平测试
- A0-A1 / A1-A2 每日计划生成与任务进度
- 100 个内置高频词、三种新词练习、TTS 发音
- 1 / 3 / 7 天复习排程与错词本
- GPT 句子拆解、写作批改、结构化 JSON 结果
- 本地 AI 结果缓存和每日调用次数限制
- 本地分句阅读器、5 篇示例短文、生词加入单词本
- 学习报告、Room 持久化、安全本地 API Key
- WorkManager 每日计划维护

## 构建

要求：

- JDK 17
- Android SDK 34
- Gradle 8.7（或使用项目 Gradle Wrapper）

```powershell
.\gradlew.bat testDebugUnitTest assembleDebug
```

APK 输出：

```text
app/build/outputs/apk/debug/app-debug.apk
```

首次安装后先完成水平测试。当前更新引导版为 `1.1.0`。需要使用 AI 功能时，在“设置”中填写 OpenAI API Key；默认模型为 `gpt-5.4-mini`，也可选择 `gpt-5.4-nano` 或 `gpt-5.4`。

应用支持通过 GitHub Releases 自动检查、下载并覆盖更新。首次配置与发布流程见 [docs/APP_UPDATES.md](docs/APP_UPDATES.md)。

API Key 通过 Android 加密偏好存储保存在本机，不会写入源代码或 Room 学习数据库。
