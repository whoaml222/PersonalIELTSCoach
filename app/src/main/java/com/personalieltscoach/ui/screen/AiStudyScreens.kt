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
fun ReadingScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val readings by viewModel.readings.collectAsStateWithLifecycle()
    val aiState by viewModel.sentenceResult.collectAsStateWithLifecycle()
    var text by rememberSaveable { mutableStateOf("") }
    var sentences by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedSentence by remember { mutableStateOf<String?>(null) }
    var selectedWord by remember { mutableStateOf<Pair<String, WordItemEntity?>?>(null) }
    var readingRecorded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val speech = rememberSpeechController()

    CoachScaffold("阅读器", onBack) {
        Text("粘贴文章后先在本地分句。只有你点中的句子才会调用 AI。")
        if (readings.isNotEmpty()) {
            Text("示例短文", style = MaterialTheme.typography.titleSmall)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                readings.forEach { reading ->
                    SuggestionChip(
                        onClick = {
                            text = reading.content
                            sentences = TextSegmenter.sentences(reading.content)
                            readingRecorded = false
                        },
                        label = { Text(reading.title) }
                    )
                }
            }
        }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                sentences = emptyList()
                selectedSentence = null
                readingRecorded = false
            },
            label = { Text("英文文章") },
            minLines = 6,
            modifier = Modifier.fillMaxWidth()
        )
        PrimaryButton("按句子开始阅读", enabled = text.isNotBlank()) {
            sentences = TextSegmenter.sentences(text)
            selectedSentence = null
            readingRecorded = false
        }
        if (sentences.isNotEmpty()) {
            Text(
                "共 ${TextSegmenter.words(text).size} 词 · ${sentences.size} 句",
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedButton(
                onClick = {
                    viewModel.recordReading(text)
                    readingRecorded = true
                },
                enabled = !readingRecorded,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (readingRecorded) "本次阅读已记录" else "完成阅读并记录")
            }
        }
        sentences.forEachIndexed { index, item ->
            ElevatedCard(
                onClick = { selectedSentence = item },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = if (selectedSentence == item) MaterialTheme.colorScheme.primaryContainer
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
                    showHint = true
                )
                Text("点击下方单词可发音并查看本地词义：")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    TextSegmenter.words(selected).forEach { word ->
                        AssistChip(
                            onClick = {
                                speech.speak(word)
                                scope.launch {
                                    selectedWord = word to viewModel.findWord(word)
                                }
                            },
                            label = { Text(word) }
                        )
                    }
                }
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

    selectedWord?.let { (raw, found) ->
        AlertDialog(
            onDismissRequest = { selectedWord = null },
            title = { Text(raw) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (found != null) {
                        Text(found.phonetic)
                        Text(WordPresentation.meaningWithChineseType(found.meaning))
                        if (found.example.isNotBlank()) {
                            SpokenEnglishText(
                                text = found.example,
                                speech = speech,
                                showHint = true
                            )
                        }
                    } else {
                        Text("本地词库暂无释义，可以先加入单词本，之后补充学习。")
                    }
                }
            },
            confirmButton = {
                if (found == null) {
                    TextButton(onClick = {
                        viewModel.addUnknownWord(raw)
                        selectedWord = null
                    }) { Text("加入单词本") }
                } else {
                    TextButton(onClick = { selectedWord = null }) { Text("知道了") }
                }
            },
            dismissButton = {
                TextButton(onClick = { speech.speak(raw) }) { Text("朗读单词") }
            }
        )
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
