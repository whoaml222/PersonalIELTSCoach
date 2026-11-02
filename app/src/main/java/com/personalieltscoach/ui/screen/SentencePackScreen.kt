package com.personalieltscoach.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.data.repository.CoachRepository
import com.personalieltscoach.data.seed.Cet4WordPack
import com.personalieltscoach.domain.service.WordPresentation
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.*

/** Keep the old navigation destination so an upgrade can restore the open module. */
@Composable
fun SentencePackScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val stats by viewModel.cet4Stats.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val session by viewModel.cet4Session.collectAsStateWithLifecycle()
    val speech = rememberSpeechController()
    val task = tasks.firstOrNull { it.type == "SENTENCE_STUDY" }
    val target = task?.targetCount ?: CoachRepository.CET4_DAILY_GOAL
    val done = task?.completedCount ?: 0
    val remaining = (target - done).coerceAtLeast(0)
    val hasMore = stats.started < stats.total
    val word = session.words.firstOrNull()
    val hasExample = word?.example?.isNotBlank() == true
    var revealed by rememberSaveable(word?.id) { mutableStateOf(false) }

    DisposableEffect(Unit) { onDispose(viewModel::closeCet4Session) }
    LaunchedEffect(word?.id) { speech.stop() }

    CoachScaffold("四级词汇", onBack) {
        when {
            session.loading -> Box(Modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            word != null -> {
                val completed = session.initialCount - session.words.size
                LinearProgressIndicator(
                    progress = { completed.toFloat() / session.initialCount.coerceAtLeast(1) },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("本批 ${completed + 1} / ${session.initialCount}", color = MaterialTheme.colorScheme.primary)
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(word.word, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                                Text(word.phonetic.ifBlank { "音标待补充，可点右侧听发音" }, style = MaterialTheme.typography.bodyLarge)
                                Text(WordPresentation.meaningWithChineseType(word.meaning), style = MaterialTheme.typography.titleLarge)
                            }
                            SpeechButton(word.word, speech, contentDescription = "朗读 ${word.word}")
                        }
                        if (hasExample) {
                            HorizontalDivider()
                            Text("例句用法", color = MaterialTheme.colorScheme.primary)
                            SpokenEnglishText(
                                text = word.example, speech = speech,
                                contextMeanings = WordPresentation.contextMeanings(word),
                                contextPhonetics = WordPresentation.contextPhonetics(word),
                                style = MaterialTheme.typography.titleLarge, showHint = true
                            )
                            if (revealed) Text(word.exampleTranslation, style = MaterialTheme.typography.titleMedium)
                            else TextButton(onClick = { revealed = true }) { Text("显示例句中文") }
                        } else {
                            Text("此词暂未配例句，可先学习释义与发音。", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
                Text("能想起这个单词的意思吗？", style = MaterialTheme.typography.titleMedium)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { speech.stop(); viewModel.answerCurrentCet4Word(false) },
                        enabled = !session.answering, modifier = Modifier.weight(1f).heightIn(min = 52.dp)
                    ) { Text("还没记住") }
                    Button(
                        onClick = { speech.stop(); viewModel.answerCurrentCet4Word(true) },
                        enabled = !session.answering, modifier = Modifier.weight(1f).heightIn(min = 52.dp)
                    ) { Text("记住了") }
                }
                Text("学过的词会按间隔安排复习，不会立刻重复加入新词。", style = MaterialTheme.typography.bodySmall)
                ErrorText(session.error)
            }
            session.completed -> SectionCard(if (hasMore) "本批学习完成" else "四级词汇已全部学完") {
                Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                Text(if (hasMore) "可以先休息，也可以继续下一批。" else "全部新词已接触，接下来请按计划复习。")
                if (hasMore) Button(
                    onClick = { viewModel.startCet4Session(continueAfterGoal = true) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)
                ) { Text("继续学习 30 个") }
            }
            else -> {
                SectionCard("CET4 四级词汇 · ${Cet4WordPack.WORD_COUNT} 个") {
                    Text("每日 30 个，学完可继续。原词库中相同单词的学习进度已保留。")
                    LinearProgressIndicator(
                        progress = { stats.started.toFloat() / stats.total.coerceAtLeast(1) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("已学习 ${stats.started} / ${stats.total} 个 · 已掌握 ${stats.mastered} 个")
                }
                SectionCard("今日学习") {
                    Text("今日已学 $done 个 · 目标 $target 个", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(if (remaining > 0) "先完成今天的目标，之后可自由加学。" else "今日目标已完成，加学不会提高目标。")
                    Button(
                        onClick = { viewModel.startCet4Session(continueAfterGoal = remaining == 0) },
                        enabled = hasMore, modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)
                    ) {
                        Text(when {
                            !hasMore -> "四级词汇已全部学完"
                            remaining > 0 -> "学习今日剩余 $remaining 个"
                            else -> "继续学习 30 个"
                        })
                    }
                }
                Text("词表离线内置；仅展示已收录的例句。首次播放英音需联网，缓存后可离线重听。",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                ErrorText(session.error)
            }
        }
    }
}
