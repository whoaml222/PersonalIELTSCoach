package com.personalieltscoach.ui

import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import com.personalieltscoach.CoachApplication
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.local.entity.WordSource
import com.personalieltscoach.domain.service.WordPresentation
import com.personalieltscoach.ui.component.VocabularyRecallCard
import com.personalieltscoach.ui.component.CoachScaffold
import com.personalieltscoach.ui.component.rememberSpeechController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
@Config(sdk = [28], application = UiTestApplication::class, qualifiers = "w360dp-h800dp")
class VocabularyRecallCardTest {
    @get:Rule(order = 0) val databaseLifetime = object : ExternalResource() {
        override fun before() { Dispatchers.setMain(Handler(Looper.getMainLooper()).asCoroutineDispatcher()) }
        override fun after() {
            try { ApplicationProvider.getApplicationContext<CoachApplication>().container.database.close() }
            finally { Dispatchers.resetMain() }
        }
    }
    @get:Rule(order = 1) val compose = createAndroidComposeRule<ComponentActivity>()
    private val word = WordItemEntity(id = 901, word = "pressure", phonetic = "/ˈpreʃə/", meaning = "n. 压力",
        example = "Check the pressure before you start.", exampleTranslation = "开始前检查压力。",
        level = "CET4", source = WordSource.CET4, createdAt = 0, updatedAt = 0)
    private val words = listOf(word,
        word.copy(id = 902, word = "window", meaning = "n. 窗户"),
        word.copy(id = 903, word = "bread", meaning = "n. 面包"),
        word.copy(id = 904, word = "warm", meaning = "adj. 温暖的"))

    private fun assertChineseHidden() {
        words.forEach {
            compose.onAllNodesWithText(WordPresentation.meaningWithChineseType(it.meaning), useUnmergedTree = true)
                .assertCountEquals(0)
        }
        compose.onAllNodesWithText(word.exampleTranslation, useUnmergedTree = true).assertCountEquals(0)
    }

    @Test fun meaningsStayOutOfSemanticsUntilRequestedAndNextWordConcealsAgain() {
        val answers = mutableListOf<Pair<Boolean, Boolean>>()
        var current by mutableStateOf(word)
        compose.setContent {
            MaterialTheme {
              CoachScaffold("词汇测试") {
                VocabularyRecallCard(current, words, rememberSpeechController(),
                    answer = { correct, independent, saved -> answers += correct to independent; saved(true) },
                    onNext = { current = words[1] })
              }
            }
        }
        assertChineseHidden()
        compose.onNodeWithText("下一个单词").assertDoesNotExist()
        compose.onNodeWithText("想不起来，看中文选项").performScrollTo().performClick()
        words.forEach { compose.onNodeWithText(WordPresentation.meaningWithChineseType(it.meaning)).assertExists() }
        compose.onNodeWithText(WordPresentation.meaningWithChineseType(word.meaning)).performScrollTo().performClick()
        compose.onNodeWithText(word.exampleTranslation).assertExists()
        assertEquals(listOf(true to false), answers)
        compose.onNodeWithText("下一个单词").assertDoesNotExist()
        compose.onNodeWithText("遮住中文，再回忆一次").performScrollTo().performClick()
        assertChineseHidden()
        compose.onNodeWithText("下一个单词").performScrollTo().performClick()
        compose.onNodeWithText("window").assertExists()
        assertChineseHidden()
        compose.onNodeWithText("想起来了，核对意思").assertExists()
        compose.onNodeWithText("下一个单词").assertDoesNotExist()
        assertEquals(1, answers.size)
    }

    @Test fun independentRecallIsSavedOnceAndRecheckingDoesNotAddMastery() {
        val answers = mutableListOf<Pair<Boolean, Boolean>>()
        compose.setContent {
            MaterialTheme {
              CoachScaffold("词汇测试") {
                VocabularyRecallCard(word, words, rememberSpeechController(),
                    answer = { correct, independent, saved -> answers += correct to independent; saved(true) }, onNext = {})
              }
            }
        }
        assertChineseHidden()
        compose.onNodeWithText("想起来了，核对意思").performScrollTo().performClick()
        compose.onNodeWithText(WordPresentation.meaningWithChineseType(word.meaning)).assertExists()
        compose.onNodeWithText("我想对了").performScrollTo().performClick()
        assertEquals(listOf(true to true), answers)
        compose.onNodeWithText("遮住中文，再回忆一次").performScrollTo().performClick()
        assertChineseHidden()
        compose.onNodeWithText("再核对一次").performScrollTo().performClick()
        compose.onNodeWithText("我想对了").assertDoesNotExist()
        compose.onNodeWithText("遮住中文，再回忆一次").performScrollTo().performClick()
        assertChineseHidden()
        assertEquals(1, answers.size)
    }

    @Test fun hidingOptionsAndRestoringStateDoesNotEraseHintUsage() {
        val restoration = StateRestorationTester(compose)
        val answers = mutableListOf<Pair<Boolean, Boolean>>()
        restoration.setContent {
            MaterialTheme {
              CoachScaffold("词汇测试") {
                VocabularyRecallCard(word, words, rememberSpeechController(),
                    answer = { correct, independent, saved -> answers += correct to independent; saved(true) }, onNext = {})
              }
            }
        }
        compose.onNodeWithText("想不起来，看中文选项").performScrollTo().performClick()
        compose.onNodeWithText("先遮住选项，再想想").performScrollTo().performClick()
        restoration.emulateSavedInstanceStateRestore()
        assertChineseHidden()
        compose.onNodeWithText("想起来了，核对意思").performScrollTo().performClick()
        compose.onNodeWithText("我想对了").performScrollTo().performClick()
        assertEquals(listOf(true to false), answers)
    }

    @Test fun pendingOrFailedSaveCannotAdvanceOrSubmitRepeatedly() {
        var saveCallback: ((Boolean) -> Unit)? = null
        var submissions = 0
        compose.setContent {
            MaterialTheme {
              CoachScaffold("词汇测试") {
                VocabularyRecallCard(word, words, rememberSpeechController(),
                    answer = { _, _, saved -> submissions++; saveCallback = saved }, onNext = {})
              }
            }
        }
        compose.onNodeWithText("想起来了，核对意思").performScrollTo().performClick()
        compose.onNodeWithText("我想对了").performScrollTo().performClick()
        compose.onNodeWithText("我想对了").assertDoesNotExist()
        compose.onNodeWithText("下一个单词").assertDoesNotExist()
        assertEquals(1, submissions)
        compose.runOnIdle { saveCallback!!(false) }
        compose.onNodeWithText("重新保存这次回答").performScrollTo().performClick()
        assertEquals(2, submissions)
        compose.runOnIdle { saveCallback!!(true) }
        compose.onNodeWithText("遮住中文，再回忆一次").performScrollTo().performClick()
        assertChineseHidden()
        compose.onNodeWithText("下一个单词").assertExists()
    }

    @Test fun restoredRevealStateCannotExposeTheNextWordsMeaning() {
        val restoration = StateRestorationTester(compose)
        // Plain flag changes only when the content is recreated, simulating
        // a reloaded queue whose first word differs from the saved Activity.
        var queueReloaded = false
        restoration.setContent {
            MaterialTheme {
                CoachScaffold("词汇测试") {
                    VocabularyRecallCard(if (queueReloaded) words[1] else word, words, rememberSpeechController(),
                        answer = { _, _, saved -> saved(true) }, onNext = {})
                }
            }
        }
        compose.onNodeWithText("想起来了，核对意思").performScrollTo().performClick()
        compose.onNodeWithText(WordPresentation.meaningWithChineseType(word.meaning)).assertExists()
        queueReloaded = true
        restoration.emulateSavedInstanceStateRestore()
        compose.onNodeWithText("window").assertExists()
        assertChineseHidden()
        compose.onNodeWithText("想起来了，核对意思").assertExists()
    }
}
