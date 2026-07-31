package com.personalieltscoach.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.data.local.entity.WordItemEntity
import com.personalieltscoach.domain.service.WordPresentation
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.CoachScaffold
import com.personalieltscoach.ui.component.PrimaryButton
import com.personalieltscoach.ui.component.SectionCard
import com.personalieltscoach.ui.component.SpeechController
import com.personalieltscoach.ui.component.SpokenEnglishText
import com.personalieltscoach.ui.component.rememberSpeechController

private enum class WordMode(val label: String) {
    EN_TO_ZH("英文选中文"),
    ZH_TO_EN("中文输入英文"),
    CLOZE("例句填空")
}

@Composable
fun VocabularyScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val words by viewModel.newWords.collectAsStateWithLifecycle()
    val allWords by viewModel.allWords.collectAsStateWithLifecycle()
    var mode by remember { mutableStateOf(WordMode.EN_TO_ZH) }
    val word = words.firstOrNull()
    val speech = rememberSpeechController()

    LaunchedEffect(Unit) { viewModel.loadNewWords() }

    CoachScaffold("学习新单词", onBack) {
        WordModeSelector(selected = mode, onSelected = { mode = it })
        if (word == null) {
            SectionCard("今天的新词已完成") {
                Text("做得漂亮。现在可以去复习旧词，或用新词写几个句子。")
            }
        } else {
            WordExerciseCard(
                word = word,
                mode = mode,
                allWords = allWords,
                speech = speech,
                answer = { correct ->
                    viewModel.answerWord(
                        word = word,
                        correct = correct,
                        review = false,
                        reloadSession = false
                    )
                },
                onNext = viewModel::loadNewWords
            )
            Text("剩余 ${words.size} 个", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun WordModeSelector(
    selected: WordMode,
    onSelected: (WordMode) -> Unit
) {
    val shape = RoundedCornerShape(28.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .border(1.dp, MaterialTheme.colorScheme.outline, shape)
    ) {
        WordMode.entries.forEachIndexed { index, item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        if (selected == item) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    )
                    .clickable { onSelected(item) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    item.label,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    fontSize = 12.sp,
                    fontWeight = if (selected == item) FontWeight.Bold else FontWeight.Medium
                )
            }
            if (index < WordMode.entries.lastIndex) {
                VerticalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
private fun WordExerciseCard(
    word: WordItemEntity,
    mode: WordMode,
    allWords: List<WordItemEntity>,
    speech: SpeechController,
    answer: (Boolean) -> Unit,
    onNext: () -> Unit
) {
    var input by remember(word.id, mode) { mutableStateOf("") }
    var revealed by remember(word.id, mode) { mutableStateOf(false) }
    var result by remember(word.id, mode) { mutableStateOf<Boolean?>(null) }
    var selectedOption by remember(word.id, mode) { mutableStateOf<String?>(null) }
    val options = remember(word.id, allWords.size) {
        (allWords.filter { it.id != word.id }.shuffled().take(3).map { it.meaning } + word.meaning).shuffled()
    }
    fun submit(correct: Boolean) {
        if (result != null) return
        revealed = true
        result = correct
        answer(correct)
    }
    SectionCard(
        when (mode) {
            WordMode.EN_TO_ZH -> "选择正确中文"
            WordMode.ZH_TO_EN -> "输入英文单词"
            WordMode.CLOZE -> "补全例句"
        }
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    if (mode == WordMode.EN_TO_ZH) {
                        word.word
                    } else {
                        WordPresentation.meaningWithChineseType(word.meaning)
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                if (mode == WordMode.EN_TO_ZH) Text(word.phonetic)
            }
            IconButton(onClick = { speech.speak(word.word) }) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "发音")
            }
        }
        when (mode) {
            WordMode.EN_TO_ZH -> options.forEach { option ->
                OutlinedButton(
                    onClick = {
                        selectedOption = option
                        submit(option == word.meaning)
                    },
                    enabled = result == null && !revealed,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        WordPresentation.meaningWithChineseType(option),
                        fontWeight = if (selectedOption == option) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
            WordMode.ZH_TO_EN -> {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("英文") },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = result == null && !revealed
                )
                PrimaryButton("检查答案", enabled = input.isNotBlank() && result == null && !revealed) {
                    submit(input.trim().equals(word.word, ignoreCase = true))
                }
            }
            WordMode.CLOZE -> {
                Text(word.example.replace(Regex("\\b${Regex.escape(word.word)}\\b", RegexOption.IGNORE_CASE), "_____"))
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("缺少的单词") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = result == null && !revealed
                )
                PrimaryButton("检查答案", enabled = input.isNotBlank() && result == null && !revealed) {
                    submit(input.trim().equals(word.word, ignoreCase = true))
                }
            }
        }
        if (revealed) {
            HorizontalDivider()
            result?.let { correct ->
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = if (correct) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (correct) "回答正确 · Good job!" else "再记一次，下次会更稳",
                        modifier = Modifier.padding(12.dp),
                        fontWeight = FontWeight.Bold,
                        color = if (correct) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            Text("${word.word} ${word.phonetic}", fontWeight = FontWeight.Bold)
            Text(WordPresentation.meaningWithChineseType(word.meaning))
            SpokenEnglishText(
                text = word.example,
                speech = speech,
                showHint = true
            )
            Text(word.exampleTranslation, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (result == null) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = { submit(false) },
                        modifier = Modifier.weight(1f)
                    ) { Text("还没记住") }
                    Button(
                        onClick = { submit(true) },
                        modifier = Modifier.weight(1f)
                    ) { Text("我记住了") }
                }
            } else {
                PrimaryButton("下一个单词", onClick = onNext)
            }
        } else {
            TextButton(onClick = { revealed = true }) { Text("查看单词详情") }
        }
    }
}

@Composable
fun ReviewScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val words by viewModel.dueWords.collectAsStateWithLifecycle()
    val word = words.firstOrNull()
    var showAnswer by remember(word?.id) { mutableStateOf(false) }
    var result by remember(word?.id) { mutableStateOf<Boolean?>(null) }
    val speech = rememberSpeechController()

    LaunchedEffect(Unit) { viewModel.loadDueWords() }

    CoachScaffold("单词复习", onBack) {
        if (word == null) {
            SectionCard("复习已完成") {
                Text("目前没有到期单词。新学单词会按照 1、3、7 天的节奏回来。")
            }
        } else {
            SectionCard("先回忆，再看答案") {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(word.word, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text(word.phonetic)
                    }
                    IconButton(onClick = {
                        speech.speak(word.word)
                    }) { Icon(Icons.AutoMirrored.Filled.VolumeUp, "发音") }
                }
                SpokenEnglishText(
                    text = word.example,
                    speech = speech,
                    showHint = true
                )
                if (showAnswer) {
                    HorizontalDivider()
                    Text(
                        WordPresentation.meaningWithChineseType(word.meaning),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(word.exampleTranslation)
                    if (result == null) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    result = false
                                    viewModel.answerWord(word, false, review = true, reloadSession = false)
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("没想起来") }
                            Button(
                                onClick = {
                                    result = true
                                    viewModel.answerWord(word, true, review = true, reloadSession = false)
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("答对了") }
                        }
                    } else {
                        Text(
                            if (result == true) "回答正确，复习间隔已更新。" else "已加入明日复习。",
                            color = if (result == true) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.error
                        )
                        PrimaryButton("下一个单词", onClick = viewModel::loadDueWords)
                    }
                } else {
                    PrimaryButton("显示答案") { showAnswer = true }
                }
            }
            Text("本轮剩余 ${words.size} 个")
        }
    }
}

@Composable
fun WrongWordsScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val words by viewModel.wrongWords.collectAsStateWithLifecycle()
    val speech = rememberSpeechController()
    CoachScaffold("错词本", onBack) {
        if (words.isEmpty()) {
            SectionCard("暂时没有错词") {
                Text("答错过的单词会自动出现在这里。")
            }
        }
        words.forEach { word ->
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("${word.word}  ${word.phonetic}", style = MaterialTheme.typography.titleLarge)
                    Text(WordPresentation.meaningWithChineseType(word.meaning))
                    Text("错误 ${word.wrongCount} 次", color = MaterialTheme.colorScheme.error)
                    if (word.example.isNotBlank()) {
                        SpokenEnglishText(
                            text = word.example,
                            speech = speech,
                            showHint = true
                        )
                        Text(word.exampleTranslation, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    OutlinedButton(onClick = { viewModel.answerWord(word, true, review = true) }) {
                        Text("我已重新记住")
                    }
                }
            }
        }
    }
}
