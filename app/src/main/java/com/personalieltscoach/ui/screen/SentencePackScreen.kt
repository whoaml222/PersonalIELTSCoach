package com.personalieltscoach.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.domain.service.SentenceChunkCodec
import com.personalieltscoach.domain.service.SentenceRating
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.CoachScaffold
import com.personalieltscoach.ui.component.ErrorText
import com.personalieltscoach.ui.component.SectionCard
import com.personalieltscoach.ui.component.SpokenEnglishText
import com.personalieltscoach.ui.component.rememberSpeechController

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentencePackScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val stats by viewModel.sentencePackStats.collectAsStateWithLifecycle()
    val session by viewModel.sentencePackSession.collectAsStateWithLifecycle()
    val speech = rememberSpeechController()
    var selectedMinutes by rememberSaveable { mutableIntStateOf(5) }
    val card = session.cards.firstOrNull()
    var revealed by rememberSaveable(card?.id) { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose(viewModel::closeSentencePackSession)
    }

    CoachScaffold("碎片句子", onBack) {
        when {
            session.loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            card != null -> {
                val completed = session.initialCount - session.cards.size
                LinearProgressIndicator(
                    progress = { completed.toFloat() / session.initialCount.coerceAtLeast(1) },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "本轮 ${completed + 1} / ${session.initialCount} · 约 ${session.minutes} 分钟",
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(card.level, fontWeight = FontWeight.Bold)
                            Text(card.category)
                        }
                        SpokenEnglishText(
                            text = card.sentence,
                            speech = speech,
                            style = MaterialTheme.typography.headlineSmall,
                            showHint = true
                        )
                    }
                }

                if (!revealed) {
                    Text(
                        "先猜整句意思，再查看拆分。不要急着逐字翻译。",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = { revealed = true },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)
                    ) { Text("显示中文和词组拆分") }
                } else {
                    SectionCard("自然中文") {
                        Text(card.translation, style = MaterialTheme.typography.titleMedium)
                    }
                    SectionCard("词组拆分") {
                        SentenceChunkCodec.decode(card.chunks).forEach { chunk ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { speech.speak(chunk.english) }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "朗读 ${chunk.english}"
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    Text(chunk.english, fontWeight = FontWeight.SemiBold)
                                    Text(
                                        chunk.chinese,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                    SectionCard("本句要点") {
                        Text(card.note)
                    }
                    Text(
                        "这句话你记得怎么样？",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.rateCurrentSentence(SentenceRating.FORGOT) },
                            enabled = !session.answering,
                            modifier = Modifier.weight(1f)
                        ) { Text("忘记了") }
                        FilledTonalButton(
                            onClick = { viewModel.rateCurrentSentence(SentenceRating.FUZZY) },
                            enabled = !session.answering,
                            modifier = Modifier.weight(1f)
                        ) { Text("有点模糊") }
                        Button(
                            onClick = { viewModel.rateCurrentSentence(SentenceRating.REMEMBERED) },
                            enabled = !session.answering,
                            modifier = Modifier.weight(1f)
                        ) { Text("记住了") }
                    }
                }
                ErrorText(session.error)
            }
            session.completed -> {
                SectionCard("本轮完成") {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(42.dp)
                    )
                    Text("很好，短时间学习也算数。", style = MaterialTheme.typography.titleLarge)
                    Text("模糊和忘记的句子会按照间隔复习再次出现。")
                    Button(
                        onClick = { viewModel.startSentencePackSession(selectedMinutes) },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("再来一轮") }
                }
            }
            else -> {
                SectionCard("澳新工作英语 · 300句试用包") {
                    Text("从 A1 日常英语开始，逐步进入求职、安全、电工、制冷空调和 IELTS General 表达。")
                    LinearProgressIndicator(
                        progress = { stats.started.toFloat() / stats.total.coerceAtLeast(1) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("已接触 ${stats.started} / ${stats.total} 句 · 已掌握 ${stats.mastered} 句")
                }
                SectionCard("选择这次的空闲时间") {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(3, 5, 10, 20).forEach { minutes ->
                            FilterChip(
                                selected = selectedMinutes == minutes,
                                onClick = { selectedMinutes = minutes },
                                label = { Text("$minutes 分钟") },
                                leadingIcon = if (selectedMinutes == minutes) {
                                    { Icon(Icons.Default.Schedule, contentDescription = null) }
                                } else null
                            )
                        }
                    }
                    Button(
                        onClick = { viewModel.startSentencePackSession(selectedMinutes) },
                        enabled = stats.total > 0,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp)
                    ) { Text("开始学习") }
                }
                Text(
                    "技工句子用于语言学习，不代替当地安全培训、技术规范或持证人员指导。",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ErrorText(session.error)
            }
        }
    }
}
