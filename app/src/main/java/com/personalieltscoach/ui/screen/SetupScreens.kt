package com.personalieltscoach.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.domain.model.PlacementResult
import com.personalieltscoach.domain.service.PlacementEvaluator
import com.personalieltscoach.R
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.PrimaryButton

@Composable
fun OnboardingScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_art),
            contentDescription = "Personal IELTS Coach",
            modifier = Modifier
                .size(92.dp)
                .clip(RoundedCornerShape(22.dp))
        )
        Spacer(Modifier.height(24.dp))
        Text(
            "你的私人英语教练",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "从零基础开始，每天自动安排单词、句子、阅读和写作，逐步走向 IELTS 7.0。",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
        ElevatedCard {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("开始前先完成一次约 5 分钟的水平测试。")
                Text("• 30 道固定题目\n• 测出大致等级和词汇量\n• 自动生成今天的学习计划")
            }
        }
        Spacer(Modifier.height(28.dp))
        PrimaryButton("开始测试", onClick = onStart)
    }
}

@Composable
fun PlacementTestScreen(viewModel: CoachViewModel, onFinished: () -> Unit) {
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    var index by rememberSaveable { mutableIntStateOf(0) }
    var correct by rememberSaveable { mutableIntStateOf(0) }
    var selected by rememberSaveable { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<PlacementResult?>(null) }

    LaunchedEffect(Unit) { viewModel.loadPlacementQuestions() }

    if (result != null) {
        val current = result!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("测试完成", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(20.dp))
            ElevatedCard {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("当前水平：${current.level}", style = MaterialTheme.typography.titleLarge)
                    Text("估计词汇量：${current.estimatedVocabulary}")
                    Text("主要弱项：${current.weakSkills}")
                    Text("建议路线：${current.route}")
                    Text("答对 $correct / ${questions.size} 题")
                }
            }
            Spacer(Modifier.height(24.dp))
            PrimaryButton("生成今日计划") {
                viewModel.completePlacement(correct, onFinished)
            }
        }
        return
    }

    if (questions.isEmpty()) {
        Box(
            Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val question = questions[index]
    val options = remember(question.id) { viewModel.decodeOptions(question.options) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        LinearProgressIndicator(
            progress = { (index + 1f) / questions.size },
            modifier = Modifier.fillMaxWidth()
        )
        Text("第 ${index + 1} / ${questions.size} 题", color = MaterialTheme.colorScheme.primary)
        Text(question.question, style = MaterialTheme.typography.headlineSmall)
        options.forEach { option ->
            FilterChip(
                selected = selected == option,
                onClick = { selected = option },
                label = { Text(option, modifier = Modifier.padding(vertical = 8.dp)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.height(8.dp))
        PrimaryButton(
            text = if (index == questions.lastIndex) "查看结果" else "下一题",
            enabled = selected != null
        ) {
            if (selected == question.answer) correct++
            if (index == questions.lastIndex) {
                result = PlacementEvaluator.evaluate(correct)
            } else {
                index++
                selected = null
            }
        }
    }
}
