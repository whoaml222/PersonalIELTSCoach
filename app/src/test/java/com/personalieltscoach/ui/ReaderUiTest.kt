package com.personalieltscoach.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import com.personalieltscoach.CoachApplication
import com.personalieltscoach.ui.component.SpokenEnglishText
import com.personalieltscoach.ui.component.rememberSpeechController
import com.personalieltscoach.ui.screen.ReadingScreen
import com.personalieltscoach.ui.screen.FreeReadingScreen
import com.personalieltscoach.ui.screen.HomeScreen
import androidx.test.core.app.ApplicationProvider
import org.junit.Rule
import org.junit.rules.ExternalResource
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.Shadows.shadowOf
import com.personalieltscoach.reading.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry

/** Real Compose layout/interaction checks on the JVM; no network pronunciation is invoked. */
@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
@Config(sdk = [28], application = UiTestApplication::class, qualifiers = "w360dp-h800dp")
class ReaderUiTest {
    // Close only after the activity rule disposes Compose and clears its ViewModelStore.
    @get:Rule(order = 0) val databaseLifetime = object : ExternalResource() {
        override fun before() {
            // Each Robolectric sandbox has its own main Looper. Do not retain a handler
            // from an earlier SDK's sandbox when the full upgrade matrix runs first.
            Dispatchers.setMain(android.os.Handler(android.os.Looper.getMainLooper()).asCoroutineDispatcher())
        }
        override fun after() {
            try { ApplicationProvider.getApplicationContext<CoachApplication>().container.database.close() }
            finally { Dispatchers.resetMain() }
        }
    }
    @get:Rule(order = 1) val compose = createAndroidComposeRule<ComponentActivity>()

    private fun coach(): CoachViewModel {
        val coach = compose.runOnIdle {
            val app = compose.activity.application as CoachApplication
            // AndroidViewModelFactory.getInstance caches the first Application, but
            // Robolectric creates a new one per test. Use a fresh factory and the same
            // Activity store that the screen's default viewModel() lookup will use.
            ViewModelProvider(compose.activity, ViewModelProvider.AndroidViewModelFactory(app))[ReaderViewModel::class.java]
            ViewModelProvider(compose.activity, CoachViewModelFactory(app.container))[CoachViewModel::class.java]
        }
        // MainActivity also waits for initial content migration before opening the learning UI.
        compose.waitUntil(20_000) {
            // No Compose content is mounted yet, so semantics queries cannot pump the
            // paused Android Looper for the database transaction's continuation.
            shadowOf(android.os.Looper.getMainLooper()).idle()
            coach.ready.value
        }
        return coach
    }

    private fun dumpUi() {
        val roots = compose.onAllNodes(isRoot(), useUnmergedTree = true)
        roots.fetchSemanticsNodes().indices.forEach { println(roots[it].printToString(maxDepth = 25)) }
    }

    private fun awaitText(text: String) {
        try {
            compose.waitUntil(20_000) {
                // Asset/Room work resumes on the Android Handler, outside Compose's
                // frame clock. Drain the paused Robolectric Looper as well as frames.
                shadowOf(android.os.Looper.getMainLooper()).idle()
                compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
            }
        } catch (error: androidx.compose.ui.test.ComposeTimeoutException) {
            dumpUi()
            throw error
        }
    }

    @Test fun tappingSentenceWordOpensOfflineMeaningAndGrammar() {
        val sentence = "This knife is too blunt to cut bread."
        compose.setContent { MaterialTheme { SpokenEnglishText(sentence, rememberSpeechController()) } }
        val textNode = compose.onNodeWithText(sentence)
        val action = textNode.fetchSemanticsNode().config[SemanticsActions.CustomActions].single { it.label == "查词 blunt" }
        compose.runOnIdle { check(action.action()) }
        awaitText("为什么这样用")
        compose.onNodeWithText("blunt").assertIsDisplayed()
        compose.onNodeWithText("钝的；刀刃不锋利").assertExists()
        compose.onNodeWithContentDescription("单独朗读 blunt").assertExists()
        compose.onNodeWithText("回到句子").performClick()
        compose.onNodeWithText("为什么这样用").assertDoesNotExist()
    }

    @Test fun bookshelfShowsLocalImportAndSeparateVocabularyAtPhoneWidth() {
        val coach = coach()
        compose.setContent { MaterialTheme { ReadingScreen(coach) {} } }
        awaitText("导入个人课程包")
        compose.onNodeWithText("导入个人课程包").assertIsDisplayed()
        compose.onNodeWithText("阅读生词").performClick()
        compose.onNodeWithText("暂时没有到期词，可以学习已收藏的新词。").assertExists()
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithText("导入个人课程包").assertExists()
    }

    @Test fun homeRemovesRetiredModulesButKeepsPaulAndReader() {
        val coach = coach()
        runBlocking { (compose.activity.application as CoachApplication).container.coachRepository
            .savePlacement(com.personalieltscoach.domain.model.PlacementResult("A0-A1", 300, "词汇", "基础路线")) }
        compose.setContent { MaterialTheme { HomeScreen(coach) {} } }
        awaitText("完成 0 / 4 项学习任务")
        compose.onNodeWithText("句子精读").assertDoesNotExist()
        compose.onNodeWithText("写作练习", substring = true).assertDoesNotExist()
        compose.onNodeWithText("Paul1000单词").assertExists()
        compose.onNodeWithText("阅读器").performScrollTo().assertIsDisplayed()
    }

    private fun retiredRouteReturnsHome(route: String) {
        val coach = coach()
        compose.setContent { MaterialTheme { com.personalieltscoach.ui.navigation.CoachApp(coach, route) } }
        awaitText("今日学习")
        compose.onNodeWithText("句子精读").assertDoesNotExist()
        compose.onNodeWithText("写作练习", substring = true).assertDoesNotExist()
    }

    @Test fun retiredWritingDestinationRedirectsHome() = retiredRouteReturnsHome("writing")
    @Test fun retiredSentenceDestinationRedirectsHome() = retiredRouteReturnsHome("sentence")

    @Test fun heartWordSheetExplainsCompletePastContinuousStructure() {
        val sentence = "My heart was racing before the interview."
        compose.setContent { MaterialTheme { SpokenEnglishText(sentence, rememberSpeechController()) } }
        val action = compose.onNodeWithText(sentence).fetchSemanticsNode().config[SemanticsActions.CustomActions]
            .single { it.label == "查词 was" }
        compose.runOnIdle { check(action.action()) }
        awaitText("整句时态与结构")
        compose.onNodeWithText("主句 · 过去进行时").assertExists()
        compose.onNodeWithText("My heart［主语］ + was racing［谓语动词组］").assertExists()
        compose.onNodeWithText("race → racing", substring = true).assertExists()
        compose.onNodeWithText("不是状语从句", substring = true).assertExists()
        compose.onNodeWithText("回到句子").performClick()
    }

    @Test fun importedCourseOpensAndResumesTheSavedSentence() {
        val app = ApplicationProvider.getApplicationContext<CoachApplication>()
        val course = ReadingCourse(id = "ui-course", title = "测试阅读课", lessons = listOf(
            ReadingLesson("d001", "在厨房", 1, listOf(ReadingSentence("s001", "This knife is blunt.", "这把刀钝了。"),
                ReadingSentence("s002", "Let's use another one.", "换一把吧。")), translation = "这把刀钝了。换一把吧。")))
        val bytes = ByteArrayOutputStream().also { output ->
            ZipOutputStream(output).use { zip ->
                zip.putNextEntry(ZipEntry("course.json")); zip.write(Json.encodeToString(course).toByteArray()); zip.closeEntry()
            }
        }.toByteArray()
        val uri = android.net.Uri.parse("content://reader-ui/course")
        shadowOf(app.contentResolver).registerInputStream(uri, bytes.inputStream())
        runBlocking { app.container.readingRepository.importCourse(uri) }
        val coach = coach()
        assertSame("Activity and importer must use the same Application", app, compose.activity.application)
        val reader = compose.runOnIdle { ViewModelProvider(compose.activity)[ReaderViewModel::class.java] }
        assertSame("Reader must use the imported course repository", app.container.readingRepository, reader.repository)
        runBlocking { assertEquals(1, reader.repository.courses.first().size) }
        compose.setContent { MaterialTheme { ReadingScreen(coach) {} } }
        awaitText("打开课程")
        compose.onNodeWithText("打开课程").performScrollTo().performClick()
        awaitText("开始阅读")
        compose.onNodeWithText("开始阅读").performClick()
        compose.onNodeWithText("第 1 / 2 句").assertExists()
        compose.onNodeWithText("查看中文参考").performScrollTo().performClick()
        compose.onNodeWithText("这把刀钝了。").assertExists()
        compose.onNodeWithText("换一把吧。").assertDoesNotExist()
        compose.onNodeWithText("全文参考：", substring = true).assertDoesNotExist()
        compose.onNodeWithText("下一句").performScrollTo().performClick()
        compose.onNodeWithText("这把刀钝了。").assertDoesNotExist()
        compose.onNodeWithText("换一把吧。").assertDoesNotExist()
        compose.onNodeWithText("查看中文参考").performScrollTo().performClick()
        compose.onNodeWithText("换一把吧。").assertExists()
        compose.waitUntil(10_000) {
            runBlocking { app.container.database.readingDao().progress("ui-course", "d001")?.sentenceIndex == 1 }
        }
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithText("接着上次读").assertExists()
        compose.onNodeWithText("开始阅读").performClick()
        compose.onNodeWithText("第 2 / 2 句").assertExists()
        compose.onNodeWithText("Let's use another one.").assertExists()
    }

    @Test fun legacyArticleTranslationNeverAppearsAsCurrentSentenceTranslation() {
        val app = ApplicationProvider.getApplicationContext<CoachApplication>()
        val wholeTranslation = "旧课程整篇中文：这把刀钝了，换一把吧。"
        val course = ReadingCourse(id = "legacy-ui", title = "旧课程", lessons = listOf(
            ReadingLesson("d001", "旧版课文", 1, listOf(ReadingSentence("s001", "This knife is blunt."),
                ReadingSentence("s002", "Let's use another one.")), translation = wholeTranslation)))
        val bytes = ByteArrayOutputStream().also { output ->
            ZipOutputStream(output).use { zip ->
                zip.putNextEntry(ZipEntry("course.json")); zip.write(Json.encodeToString(course).toByteArray()); zip.closeEntry()
            }
        }.toByteArray()
        val uri = android.net.Uri.parse("content://reader-ui/legacy-course")
        shadowOf(app.contentResolver).registerInputStream(uri, bytes.inputStream())
        runBlocking { app.container.readingRepository.importCourse(uri) }
        val coach = coach()
        compose.setContent { MaterialTheme { ReadingScreen(coach) {} } }
        awaitText("打开课程")
        compose.onNodeWithText("打开课程").performScrollTo().performClick()
        awaitText("开始阅读")
        compose.onNodeWithText("开始阅读").performClick()
        compose.onNodeWithText("查看中文参考").performScrollTo().performClick()
        compose.onNodeWithText("本句中文待补充。", substring = true).assertExists()
        compose.onNodeWithText(wholeTranslation, substring = true).assertDoesNotExist()
        compose.onNodeWithText("全文阅读").performScrollTo().performClick()
        compose.onNodeWithText("查看中文参考").performScrollTo().performClick()
        compose.onNodeWithText(wholeTranslation, substring = true).assertExists()
    }

    @Test fun freeReadingRestoresSelectedSentenceAndCompletionAfterRecreation() {
        val coach = coach()
        val restoration = StateRestorationTester(compose)
        restoration.setContent { MaterialTheme { FreeReadingScreen(coach) {} } }
        compose.onNodeWithText("英文文章").performTextInput("This knife is blunt. Let's use another one.")
        compose.onNodeWithText("按句子开始阅读").performScrollTo().performClick()
        compose.onNodeWithText("2. Let's use another one.").performScrollTo().performClick()
        compose.onNodeWithText("当前句子").assertExists()
        compose.onNodeWithText("完成阅读并记录").performScrollTo().performClick()
        compose.waitUntil(10_000) { compose.onAllNodesWithText("本次阅读已记录").fetchSemanticsNodes().isNotEmpty() }

        restoration.emulateSavedInstanceStateRestore()

        compose.onNodeWithText("2. Let's use another one.").assertExists()
        compose.onNodeWithText("当前句子").assertExists()
        compose.onNodeWithText("Let's use another one.").assertExists()
        compose.onNodeWithText("本次阅读已记录").assertIsNotEnabled()
        compose.onNodeWithText("英文文章").performScrollTo().performTextReplacement("Where is my coat?")
        compose.onNodeWithText("按句子开始阅读").performScrollTo().performClick()
        compose.onNodeWithText("当前句子").assertDoesNotExist()
        compose.onNodeWithText("完成阅读并记录").assertIsEnabled()
    }
}

/** JVM tests do not run Android's startup providers; initialise a non-executing scheduler. */
class UiTestApplication : CoachApplication() {
    override fun onCreate() {
        if (runCatching { androidx.work.WorkManager.getInstance(this) }.isFailure) {
            androidx.work.WorkManager.initialize(this, androidx.work.Configuration.Builder()
                .setExecutor { }.setTaskExecutor { }.build())
        }
        super.onCreate()
        // UI tests never need a real GitHub update check in the background.
        runBlocking { container.settingsRepository.setAutoCheckUpdates(false) }
    }
}
