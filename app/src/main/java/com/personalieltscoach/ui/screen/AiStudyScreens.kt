package com.personalieltscoach.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.data.seed.SeedData
import com.personalieltscoach.domain.model.SentenceAnalysisResult
import com.personalieltscoach.domain.service.TextSegmenter
import com.personalieltscoach.domain.service.WordPresentation
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentenceStudyScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    var sentence by rememberSaveable { mutableStateOf("") }
    val state by viewModel.sentenceResult.collectAsStateWithLifecycle()
    val requestKey = sentence.trim()
    val speech = rememberSpeechController()

    CoachScaffold("句子精读", onBack) {
        Text("输入或粘贴一句英文。只有点击分析时才会调用 AI，重复句子会优先读取本地缓存。")
        OutlinedTextField(
            value = sentence,
            onValueChange = { sentence = it },
            label = { Text("英文句子") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        if (sentence.isNotBlank()) {
            SpokenEnglishText(
                text = sentence,
                speech = speech,
                showHint = true
            )
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SeedData.sampleSentences.take(5).forEach { sample ->
                SuggestionChip(onClick = { sentence = sample }, label = { Text(sample) })
            }
        }
        Button(
            onClick = { viewModel.analyzeSentence(sentence) },
            enabled = sentence.isNotBlank() && !state.loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.loading) {
                CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Icon(Icons.Default.AutoAwesome, null)
            }
            Spacer(Modifier.width(8.dp))
            Text(if (state.loading) "正在分析…" else "AI 分析句子")
        }
        ErrorText(state.error.takeIf { state.requestKey == requestKey })
        state.value?.takeIf { state.requestKey == requestKey }?.let { result ->
            SentenceAnalysisCard(result, state.fromCache, speech)
            OutlinedButton(
                onClick = { viewModel.saveSentence(sentence) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, null)
                Spacer(Modifier.width(6.dp))
                Text("加入复习")
            }
        }
    }
}

@Composable
private fun SentenceAnalysisCard(
    result: SentenceAnalysisResult,
    fromCache: Boolean,
    speech: SpeechController
) {
    SectionCard("分析结果${if (fromCache) " · 已从缓存读取" else ""}") {
        Text(result.translation, style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()
        Text("单词解释", fontWeight = FontWeight.Bold)
        result.wordExplanation.forEach {
            Text("${it.word} = ${it.meaning}（${it.role}）")
        }
        if (result.phraseExplanation.isNotEmpty()) {
            Text("短语解释", fontWeight = FontWeight.Bold)
            result.phraseExplanation.forEach { Text("${it.phrase} = ${it.meaning}") }
        }
        Text("句子结构", fontWeight = FontWeight.Bold)
        Text(result.sentenceStructure)
        Text("语法点", fontWeight = FontWeight.Bold)
        Text(result.grammarPoint)
        Text("模仿造句", fontWeight = FontWeight.Bold)
        SpokenEnglishText(
            text = result.imitationExample,
            speech = speech,
            showHint = true
        )
        Text(result.imitationExampleTranslation, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FreeReadingScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val readings by viewModel.readings.collectAsStateWithLifecycle()
    val aiState by viewModel.sentenceResult.collectAsStateWithLifecycle()
    var text by rememberSaveable { mutableStateOf("") }
    var readingStarted by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }
    var readingRecorded by rememberSaveable { mutableStateOf(false) }
    var recording by remember { mutableStateOf(false) }
    var contentGeneration by remember { mutableIntStateOf(0) }
    // Save the input and a small selection, not a second copy of every sentence in the Bundle.
    val sentences = remember(text, readingStarted) {
        if (readingStarted) TextSegmenter.sentences(text) else emptyList()
    }
    val selectedSentence = sentences.getOrNull(selectedIndex)
    fun resetReading(start: Boolean) {
        readingStarted = start
        selectedIndex = -1
        readingRecorded = false
        recording = false
        contentGeneration++
    }
    val reader: com.personalieltscoach.reading.ReaderViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val speech = rememberSpeechController()

    CoachScaffold("阅读器", onBack) {
        Text("分句和点词解释均在本地完成。只有点击‘AI 拆解’才会发送选中的句子并产生 API 费用。")
        if (readings.isNotEmpty()) {
            Text("示例短文", style = MaterialTheme.typography.titleSmall)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                readings.forEach { reading ->
                    SuggestionChip(
                        onClick = {
                            text = reading.content
                            resetReading(start = true)
                        },
                        label = { Text(reading.title) }
                    )
                }
            }
        }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it.take(30_000)
                resetReading(start = false)
            },
            label = { Text("英文文章") },
            minLines = 6,
            modifier = Modifier.fillMaxWidth()
        )
        PrimaryButton("按句子开始阅读", enabled = text.isNotBlank()) {
            if (!readingStarted) resetReading(start = true)
        }
        if (sentences.isNotEmpty()) {
            Text(
                "共 ${TextSegmenter.words(text).size} 词 · ${sentences.size} 句",
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedButton(
                onClick = {
                    recording = true
                    val generation = contentGeneration
                    viewModel.recordReading(text) { saved ->
                        // A slow save for the previous article must not mark a new article completed.
                        if (generation == contentGeneration) {
                            recording = false
                            readingRecorded = saved
                        }
                    }
                },
                enabled = !readingRecorded && !recording,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (readingRecorded) "本次阅读已记录" else "完成阅读并记录")
            }
        }
        sentences.forEachIndexed { index, item ->
            ElevatedCard(
                onClick = { selectedIndex = index },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = if (selectedIndex == index) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("${index + 1}. $item", modifier = Modifier.padding(14.dp))
            }
        }
        selectedSentence?.let { selected ->
            SectionCard("当前句子") {
                SpokenEnglishText(
                    text = selected,
                    speech = speech,
                    style = MaterialTheme.typography.titleMedium,
                    showHint = true,
                    onCollect = { reader.collect("free-reading", "saved-sentence", it.lemma, it.meaning, selected) }
                )
                Button(
                    onClick = {
                        viewModel.analyzeSentence(selected)
                    },
                    enabled = !aiState.loading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (aiState.loading && aiState.requestKey == selected.trim()) {
                        CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.AutoAwesome, null)
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (aiState.loading && aiState.requestKey == selected.trim()) "正在分析…"
                        else "AI 拆解这一句"
                    )
                }
            }
            if (aiState.requestKey == selected.trim()) {
                ErrorText(aiState.error)
                aiState.value?.let { SentenceAnalysisCard(it, aiState.fromCache, speech) }
            }
        }
    }

}

@Composable
fun WritingPracticeScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val prompts = listOf(
        "我喜欢读书，因为它让我放松。",
        "我每天学习英语，希望将来能自信地交流。",
        "我在机场工作，英语对我的工作很重要。"
    )
    var promptIndex by rememberSaveable { mutableIntStateOf(0) }
    var text by rememberSaveable { mutableStateOf("") }
    val state by viewModel.writingResult.collectAsStateWithLifecycle()
    val wordCount = TextSegmenter.words(text).size
    val requestKey = "${prompts[promptIndex]}|${text.trim()}"
    val speech = rememberSpeechController()

    CoachScaffold("写作练习", onBack) {
        SectionCard("中文提示 ${promptIndex + 1}/${prompts.size}") {
            Text(prompts[promptIndex], style = MaterialTheme.typography.titleMedium)
            if (promptIndex < prompts.lastIndex) {
                TextButton(onClick = {
                    promptIndex++
                    text = ""
                }) { Text("换下一题") }
            }
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("写一个或几个英文句子") },
            minLines = 4,
            supportingText = { Text("$wordCount / 300 words") },
            isError = wordCount > 300,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.correctWriting(prompts[promptIndex], text) },
            enabled = text.isNotBlank() && wordCount <= 300 && !state.loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.loading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            else Icon(Icons.Default.AutoAwesome, null)
            Spacer(Modifier.width(8.dp))
            Text(if (state.loading) "正在批改…" else "AI 批改")
        }
        ErrorText(state.error.takeIf { state.requestKey == requestKey })
        state.value?.takeIf { state.requestKey == requestKey }?.let { result ->
            SectionCard("批改结果${if (state.fromCache) " · 已从缓存读取" else ""}") {
                Text("正确表达", fontWeight = FontWeight.Bold)
                SpokenEnglishText(
                    text = result.correctedText,
                    speech = speech,
                    style = MaterialTheme.typography.titleMedium,
                    showHint = true
                )
                Text(result.chineseTranslation, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (result.mistakes.isNotEmpty()) {
                    HorizontalDivider()
                    Text("问题说明", fontWeight = FontWeight.Bold)
                    result.mistakes.forEachIndexed { index, mistake ->
                        Text("${index + 1}. ${mistake.original} → ${mistake.corrected}\n${mistake.reason}")
                    }
                }
                Text("更自然的表达", fontWeight = FontWeight.Bold)
                SpokenEnglishText(
                    text = result.betterExpression,
                    speech = speech,
                    showHint = true
                )
                Text("下一步", fontWeight = FontWeight.Bold)
                Text(result.nextPracticeSuggestion)
            }
        }
    }
}
