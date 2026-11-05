package com.personalieltscoach.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.local.entity.WordSource
import com.personalieltscoach.domain.service.WordPresentation
import kotlin.random.Random

private enum class RecallPhase { RECALL, CHOICES, ANSWER, RECALL_AGAIN }

/** Chinese is not composed at all while concealed, including accessibility text. */
@Composable
fun VocabularyRecallCard(
    word: WordItemEntity,
    allWords: List<WordItemEntity>,
    speech: SpeechController,
    answer: (correct: Boolean, independentRecall: Boolean, onSaved: (Boolean) -> Unit) -> Unit,
    onNext: () -> Unit
) {
    // A saved reveal state must never attach to a different word after an
    // Activity/process restart reloads the remaining study queue.
    key(word.id) { RecallWordContent(word, allWords, speech, answer, onNext) }
}

@Composable
private fun RecallWordContent(
    word: WordItemEntity,
    allWords: List<WordItemEntity>,
    speech: SpeechController,
    answer: (Boolean, Boolean, (Boolean) -> Unit) -> Unit,
    onNext: () -> Unit
) {
    var phase by rememberSaveable(word.id) { mutableStateOf(RecallPhase.RECALL) }
    var hintUsed by rememberSaveable(word.id) { mutableStateOf(false) }
    var result by rememberSaveable(word.id) { mutableStateOf<Boolean?>(null) }
    var submittedCorrect by rememberSaveable(word.id) { mutableStateOf<Boolean?>(null) }
    // An in-flight callback belongs to the old composition after restoration;
    // allow an idempotent retry rather than restoring a permanently busy button.
    var saving by remember(word.id) { mutableStateOf(false) }
    var saveFailed by remember(word.id) { mutableStateOf(false) }
    val options = remember(word.id, allWords.size) {
        val displayedAnswer = WordPresentation.meaningWithChineseType(word.meaning)
        val distractors = allWords.asSequence()
            .filter { it.id != word.id && it.meaning.isNotBlank() }
            .filter { WordPresentation.meaningWithChineseType(it.meaning) != displayedAnswer }
            .distinctBy { WordPresentation.meaningWithChineseType(it.meaning) }
            .map { it.meaning }.toList().shuffled(Random(word.id.hashCode())).take(3)
        (distractors + word.meaning).shuffled(Random(word.id.hashCode() xor 31))
    }
    LaunchedEffect(word.id) { speech.stop() }

    fun submit(correct: Boolean) {
        if (saving || result != null) return
        submittedCorrect = correct
        saveFailed = false
        saving = true
        speech.stop()
        answer(correct, !hintUsed) { saved ->
            saving = false
            saveFailed = !saved
            if (saved) result = correct
        }
    }

    SectionCard(if (phase == RecallPhase.RECALL_AGAIN) "遮住中文，再回忆一次" else "先回忆，再看中文") {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(word.word, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                Text(word.phonetic.ifBlank { "音标待补充，可点右侧听发音" })
                Text(WordSource.label(word.source), style = MaterialTheme.typography.labelMedium)
                if (WordSource.isNewConcept(word.source)) Text(word.level, style = MaterialTheme.typography.labelMedium)
            }
            SpeechButton(word.word, speech, contentDescription = "朗读 ${word.word}")
        }
        if (word.example.isNotBlank()) {
            SpokenEnglishText(
                text = word.example, speech = speech,
                contextMeanings = WordPresentation.contextMeanings(word),
                contextPhonetics = WordPresentation.contextPhonetics(word),
                onWordLookup = {
                    if (phase == RecallPhase.RECALL || phase == RecallPhase.CHOICES) hintUsed = true
                }
            )
        } else {
            Text("此词暂未配例句，可先回忆意思，再听单词发音。", style = MaterialTheme.typography.bodySmall)
        }

        when (phase) {
            RecallPhase.RECALL -> {
                Text("先用 2–3 秒回忆意思，不急着看中文。能直接理解英文也可以。")
                PrimaryButton("想起来了，核对意思") { phase = RecallPhase.ANSWER }
                OutlinedButton(
                    onClick = { hintUsed = true; phase = RecallPhase.CHOICES },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                ) { Text("想不起来，看中文选项") }
            }
            RecallPhase.CHOICES -> {
                Text("中文选项已展开。认出来也是进步，明天再试着独立回忆。")
                options.forEach { option ->
                    OutlinedButton(
                        onClick = {
                            phase = RecallPhase.ANSWER
                            submit(option == word.meaning)
                        },
                        enabled = !saving,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                    ) { Text(WordPresentation.meaningWithChineseType(option)) }
                }
                TextButton(onClick = { phase = RecallPhase.RECALL }) { Text("先遮住选项，再想想") }
            }
            RecallPhase.ANSWER -> {
                HorizontalDivider()
                Text(WordPresentation.meaningWithChineseType(word.meaning), style = MaterialTheme.typography.titleLarge)
                if (word.exampleTranslation.isNotBlank()) Text(word.exampleTranslation)
                when {
                    saving -> Text("正在保存…", style = MaterialTheme.typography.bodySmall)
                    saveFailed || (submittedCorrect != null && result == null) -> {
                        Text("回答尚未保存，请重试。", color = MaterialTheme.colorScheme.error)
                        PrimaryButton("重新保存这次回答") { submittedCorrect?.let(::submit) }
                    }
                    result == null -> {
                        Text("和你刚才想的一样吗？", style = MaterialTheme.typography.titleMedium)
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = { submit(false) }, modifier = Modifier.weight(1f).heightIn(min = 52.dp)
                            ) { Text("没想对") }
                            Button(
                                onClick = { submit(true) }, modifier = Modifier.weight(1f).heightIn(min = 52.dp)
                            ) { Text("我想对了") }
                        }
                    }
                    else -> {
                        Text(
                            when {
                                result != true -> "没想对也没关系，已安排明天再复习。"
                                hintUsed -> "提示后认对了，明天再独立回忆；这次不计入独立掌握。"
                                else -> "独立回忆正确，已安排下一次间隔复习。"
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                        PrimaryButton("遮住中文，再回忆一次") {
                            speech.stop()
                            phase = RecallPhase.RECALL_AGAIN
                        }
                    }
                }
            }
            RecallPhase.RECALL_AGAIN -> {
                Text("看着英文，再在心里回忆一遍意思或画面。刚看过答案后的回忆，不会额外增加掌握次数。")
                PrimaryButton("下一个单词", enabled = result != null) { speech.stop(); onNext() }
                TextButton(onClick = { phase = RecallPhase.ANSWER }) { Text("再核对一次") }
            }
        }
    }
}
