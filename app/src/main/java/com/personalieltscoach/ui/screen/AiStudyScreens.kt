package com.personalieltscoach.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.domain.model.SentenceAnalysisResult
import com.personalieltscoach.domain.service.TextSegmenter
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.*

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
