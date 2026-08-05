package com.personalieltscoach.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.CoachScaffold
import com.personalieltscoach.ui.component.SectionCard
import com.personalieltscoach.ui.navigation.Routes

@Composable
fun HomeScreen(viewModel: CoachViewModel, navigate: (String) -> Unit) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val plan by viewModel.plan.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val progress = if ((plan?.totalCount ?: 0) == 0) 0f
    else (plan?.completedCount ?: 0).toFloat() / (plan?.totalCount ?: 1)

    CoachScaffold(
        title = "今日学习",
        actions = {
            IconButton(onClick = { navigate(Routes.Settings) }) {
                Icon(Icons.Default.Settings, contentDescription = "设置")
            }
        }
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.size(86.dp),
                        strokeWidth = 8.dp,
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)
                    )
                    Text(
                        "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        if (progress >= 1f) "今日目标已完成" else "保持节奏，稳步前进",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        "完成 ${plan?.completedCount ?: 0} / ${plan?.totalCount ?: 0} 项学习任务",
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.76f)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        InfoPill(profile?.currentLevel ?: "A0-A1")
                        InfoPill("连续 ${profile?.streakDays ?: 1} 天")
                    }
                }
            }
        }

        SectionCard("今日计划") {
            tasks.forEachIndexed { index, task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (task.completed) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Icon(
                            if (task.completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp).size(20.dp),
                            tint = if (task.completed) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline
                        )
                    }
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            "${index + 1}. ${task.title}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            task.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        "${task.completedCount}/${task.targetCount}",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (task.completed) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (index < tasks.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }

        Button(
            onClick = { navigate(Routes.Vocabulary) },
            modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.PlayArrow, null)
            Spacer(Modifier.width(8.dp))
            Text(if (progress > 0f) "继续今日学习" else "开始今日学习")
        }

        Text("专项练习", style = MaterialTheme.typography.titleLarge)
        val buttons = listOf(
            Triple("碎片句子", Icons.Default.Bolt, Routes.SentencePack),
            Triple("单词复习", Icons.Default.Refresh, Routes.Review),
            Triple("句子精读", Icons.AutoMirrored.Filled.MenuBook, Routes.Sentence),
            Triple("阅读器", Icons.AutoMirrored.Filled.Article, Routes.Reading),
            Triple("写作练习", Icons.Default.Edit, Routes.Writing),
            Triple("错词本", Icons.Default.ErrorOutline, Routes.WrongWords),
            Triple("学习报告", Icons.Default.Insights, Routes.Progress)
        )
        buttons.chunked(2).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { (label, icon, route) ->
                    QuickActionCard(
                        label = label,
                        icon = icon,
                        modifier = Modifier.weight(1f),
                        onClick = { navigate(route) }
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoPill(label: String) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.68f)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 1
        )
    }
}

@Composable
private fun QuickActionCard(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .height(92.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(
                label,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ProgressScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val stats by viewModel.dailyStats.collectAsStateWithLifecycle()
    val mastered by viewModel.masteredCount.collectAsStateWithLifecycle()
    val learning by viewModel.learningCount.collectAsStateWithLifecycle()
    val wrong by viewModel.wrongCount.collectAsStateWithLifecycle()
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val plan by viewModel.plan.collectAsStateWithLifecycle()

    CoachScaffold("学习报告", onBack) {
        SectionCard("今日学习") {
            StatRow("学习时长", "${stats.minutes} 分钟")
            StatRow("新学单词", "${stats.newWords} 个")
            StatRow("复习单词", "${stats.reviewedWords} 个")
            StatRow("错词", "${stats.wrongWords} 个")
            StatRow("精读句子", "${stats.sentences} 句")
            StatRow("阅读字数", "${stats.readingWords}")
            StatRow("写作句子", "${stats.writingSentences} 个")
        }
        SectionCard("累计数据") {
            StatRow("已掌握单词", "$mastered 个")
            StatRow("学习中单词", "$learning 个")
            StatRow("易错单词", "$wrong 个")
            StatRow("连续学习", "${profile?.streakDays ?: 1} 天")
            StatRow("当前阶段", profile?.currentLevel ?: "A0-A1")
        }
        SectionCard("明日建议") {
            val suggestion = when {
                stats.wrongWords >= 5 -> "今天错词较多，明天先复习错词，再学习新词。"
                (plan?.completedCount ?: 0) < (plan?.totalCount ?: 1) -> "明天优先补齐今天未完成的学习类型，保持节奏比一次学很多更重要。"
                else -> "今天完成得很好。明天保持同样节奏，并尝试用新词写 3 个简单句。"
            }
            Text(suggestion)
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value)
    }
}
