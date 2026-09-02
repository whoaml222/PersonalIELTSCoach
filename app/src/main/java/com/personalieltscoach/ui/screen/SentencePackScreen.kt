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
import com.personalieltscoach.data.seed.Paul1000WordPack
import com.personalieltscoach.domain.service.SentenceRating
import com.personalieltscoach.domain.service.WordPresentation
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.CoachScaffold
import com.personalieltscoach.ui.component.ErrorText
import com.personalieltscoach.ui.component.SectionCard
import com.personalieltscoach.ui.component.SpokenEnglishText
import com.personalieltscoach.ui.component.SpeechButton
import com.personalieltscoach.ui.component.rememberSpeechController

@Composable
fun SentencePackScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val stats by viewModel.sentencePackStats.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val session by viewModel.sentencePackSession.collectAsStateWithLifecycle()
    val speech = rememberSpeechController()
    val task = tasks.firstOrNull { it.type == "SENTENCE_STUDY" }
    val dailyTarget = task?.targetCount ?: CoachRepository.PAUL_WORD_DAILY_GOAL
    val dailyCompleted = task?.completedCount ?: 0
    val dailyRemaining = (dailyTarget - dailyCompleted).coerceAtLeast(0)
    val hasMoreWords = stats.started < stats.total
    val card = session.cards.firstOrNull()
    val word = card?.id?.let(session.wordsByCardId::get)
    var revealed by rememberSaveable(card?.id) { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose(viewModel::closeSentencePackSession)
    }

    CoachScaffold("Paul1000单词", onBack) {
        when {
            session.loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            card != null && word != null -> {
                val completed = session.initialCount - session.cards.size
                LinearProgressIndicator(
                    progress = { completed.toFloat() / session.initialCount.coerceAtLeast(1) },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "本批 ${completed + 1} / ${session.initialCount}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    word.word,
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(word.phonetic, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    WordPresentation.meaningWithChineseType(word.meaning),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            SpeechButton(
                                text = word.word,
                                speech = speech,
                                contentDescription = "朗读 ${word.word}"
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Text(
                            "真实口语用法",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        SpokenEnglishText(
                            text = card.sentence,
                            speech = speech,
                            style = MaterialTheme.typography.titleLarge,
                            showHint = true
                        )
                    }
                }

                if (!revealed) {
                    Text(
                        "先根据单词理解例句，再查看中文。",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = { revealed = true },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)
                    ) { Text("显示例句中文") }
                } else {
                    SectionCard("例句中文") {
                        Text(card.translation, style = MaterialTheme.typography.titleMedium)
                        if (card.note.isNotBlank()) {
                            Text(
                                card.note,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Text(
                        "这个单词和用法记得怎么样？",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.rateCurrentPaulWord(SentenceRating.FORGOT) },
                            enabled = !session.answering,
                            modifier = Modifier.weight(1f)
                        ) { Text("忘记了") }
                        FilledTonalButton(
                            onClick = { viewModel.rateCurrentPaulWord(SentenceRating.FUZZY) },
                            enabled = !session.answering,
                            modifier = Modifier.weight(1f)
                        ) { Text("有点模糊") }
                        Button(
                            onClick = { viewModel.rateCurrentPaulWord(SentenceRating.REMEMBERED) },
                            enabled = !session.answering,
                            modifier = Modifier.weight(1f)
                        ) { Text("记住了") }
                    }
                }
                ErrorText(session.error)
            }
            card != null -> {
                SectionCard("内容加载失败") {
                    Text("这个单词没有正确关联到学习卡片，请返回后重试。")
                }
                ErrorText(session.error)
            }
            session.completed -> {
                SectionCard(if (hasMoreWords) "本批学习完成" else "Paul1000 已全部学完") {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(42.dp)
                    )
                    Text(
                        if (hasMoreWords) "今天的学习很扎实。" else "你已接触全部 Paul1000 单词。",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        if (hasMoreWords) {
                            "你可以先休息，也可以继续学习下一批 30 个；加学不会改变今日目标。"
                        } else {
                            "接下来按“单词复习”中的间隔计划继续巩固即可。"
                        }
                    )
                    if (hasMoreWords) {
                        Button(
                            onClick = { viewModel.startPaulWordSession(continueAfterGoal = true) },
                            modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                        ) { Text("继续学习 30 个") }
                    }
                }
            }
            else -> {
                SectionCard("Paul1000 高频词 · ${Paul1000WordPack.SOURCE_ENTRY_COUNT} 个") {
                    Text("单词、英音音标和中文释义单独展示，下面用一条简短真实口语说明用法。")
                    LinearProgressIndicator(
                        progress = { stats.started.toFloat() / stats.total.coerceAtLeast(1) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "已学习 ${stats.started} / ${stats.total} 个 · " +
                            "巩固中 ${(stats.started - stats.mastered).coerceAtLeast(0)} 个 · " +
                            "已掌握 ${stats.mastered} 个"
                    )
                }

                SectionCard("今日学习") {
                    Text(
                        "今日已完成 $dailyCompleted / $dailyTarget 个",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    LinearProgressIndicator(
                        progress = {
                            dailyCompleted.coerceAtMost(dailyTarget).toFloat() /
                                dailyTarget.coerceAtLeast(1)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        if (dailyRemaining > 0) {
                            "每天先学习 30 个新词；完成后由你决定是否继续。"
                        } else {
                            "今天的 30 个已经完成。继续学习不会增加今日目标。"
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = {
                            viewModel.startPaulWordSession(continueAfterGoal = dailyRemaining == 0)
                        },
                        enabled = hasMoreWords,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)
                    ) {
                        Text(
                            when {
                                !hasMoreWords -> "Paul1000 已全部学完"
                                dailyRemaining > 0 -> "学习今日剩余 $dailyRemaining 个"
                                else -> "继续学习 30 个"
                            }
                        )
                    }
                }
                Text(
                    "词表和例句均离线内置；首次播放发音需要联网，播放后可离线重听。",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ErrorText(session.error)
            }
        }
    }
}
