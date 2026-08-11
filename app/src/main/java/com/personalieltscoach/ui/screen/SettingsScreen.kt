package com.personalieltscoach.ui.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.*
import com.personalieltscoach.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: CoachViewModel,
    onBack: () -> Unit,
    onReset: () -> Unit
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val usage by viewModel.todayUsage.collectAsStateWithLifecycle()
    val connection by viewModel.connectionState.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()
    var apiKey by remember(settings.apiKey) { mutableStateOf(settings.apiKey) }
    var updateRepository by remember(settings.updateRepository) {
        mutableStateOf(settings.updateRepository)
    }
    var autoCheckUpdates by remember(settings.autoCheckUpdates) {
        mutableStateOf(settings.autoCheckUpdates)
    }
    var showKey by remember { mutableStateOf(false) }
    var modelMenu by remember { mutableStateOf(false) }
    var confirmReset by remember { mutableStateOf(false) }
    var speechRate by remember(settings.speechRate) { mutableFloatStateOf(settings.speechRate) }
    val models = listOf("gpt-5.4-mini", "gpt-5.4-nano", "gpt-5.4")
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}
    val speech = rememberSpeechController()

    CoachScaffold("设置", onBack) {
        SectionCard("朗读设置") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    Icons.Default.RecordVoiceOver,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(Modifier.weight(1f)) {
                    Text("英音词典发音", style = MaterialTheme.typography.titleMedium)
                    Text(
                        when {
                            speech.status == SpeechStatus.LOADING -> "正在获取英音词典音频…"
                            speech.status == SpeechStatus.PLAYING -> "正在播放词典英音"
                            speech.status == SpeechStatus.ERROR ->
                                speech.lastError ?: "英音词典暂时不可用"
                            else -> speech.selectedVoiceLabel
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            FilledTonalButton(
                onClick = { speech.speak("Welcome to your personal IELTS coach.") },
                enabled = speech.isReady,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (speech.status == SpeechStatus.LOADING) {
                    CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Default.RecordVoiceOver, contentDescription = null)
                }
                Spacer(Modifier.width(8.dp))
                Text(if (speech.status == SpeechStatus.PLAYING) "正在播放" else "试听英音词典")
            }
            Text("语速：${"%.2f".format(speechRate)} 倍")
            Slider(
                value = speechRate,
                onValueChange = { speechRate = it },
                onValueChangeFinished = { viewModel.setSpeechRate(speechRate) },
                valueRange = 0.65f..1.15f,
                steps = 9
            )
            Text(
                "不再使用手机系统朗读。首次播放需要联网，成功播放后会保存在 APP 缓存中，" +
                    "以后可离线重听；不调用 OpenAI、不需要 API Key，也不产生 Token 费用。",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        SectionCard("AI 设置") {
            Text("平台：GPT（其他平台预留，暂未启用）")
            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                label = { Text("OpenAI API Key") },
                visualTransformation = if (showKey) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showKey = !showKey }) {
                        Icon(
                            if (showKey) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (showKey) "隐藏" else "显示"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Text(
                "Key 使用 Android 加密存储，仅保存在本机，不写入代码或学习数据库。",
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedButton(
                onClick = { viewModel.saveApiKey(apiKey) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("保存 API Key") }

            ExposedDropdownMenuBox(expanded = modelMenu, onExpandedChange = { modelMenu = it }) {
                OutlinedTextField(
                    value = settings.model,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("默认模型") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(modelMenu) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = modelMenu, onDismissRequest = { modelMenu = false }) {
                    models.forEach { model ->
                        DropdownMenuItem(
                            text = { Text(model) },
                            onClick = {
                                viewModel.setModel(model)
                                modelMenu = false
                            }
                        )
                    }
                }
            }
            NumberSetting("每日 AI 调用上限", settings.dailyAiLimit, viewModel::setAiLimit)
            Text("今日已调用：$usage / ${settings.dailyAiLimit}")
            Button(
                onClick = viewModel::testConnection,
                enabled = !connection.loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (connection.loading) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                else Text("测试连接")
            }
            connection.value?.let { if (it) Text("连接成功", color = MaterialTheme.colorScheme.primary) }
            ErrorText(connection.error)
            OutlinedButton(onClick = viewModel::clearAiCache, modifier = Modifier.fillMaxWidth()) {
                Text("清除 AI 缓存")
            }
        }

        SectionCard("学习设置") {
            NumberSetting("每日新词数量", settings.dailyNewWords, viewModel::setNewWords)
            NumberSetting("每日复习词数量", settings.dailyReviewWords, viewModel::setReviewWords)
            NumberSetting("每日精读句子数量", settings.dailySentences, viewModel::setSentences)
            Text("当前目标：IELTS 7.0")
            Text(
                "数量修改会从下一次生成每日计划开始生效。",
                style = MaterialTheme.typography.bodySmall
            )
        }

        SectionCard("应用更新") {
            Text("当前版本：${BuildConfig.VERSION_NAME}")
            OutlinedTextField(
                value = updateRepository,
                onValueChange = { updateRepository = it },
                label = { Text("GitHub 仓库") },
                placeholder = { Text("用户名/仓库名") },
                supportingText = {
                    Text("使用公开 GitHub Releases 检查和下载更新")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text("自动检查更新")
                    Text("每天后台检查一次", style = MaterialTheme.typography.bodySmall)
                }
                Switch(
                    checked = autoCheckUpdates,
                    onCheckedChange = { autoCheckUpdates = it }
                )
            }
            Button(
                onClick = {
                    viewModel.saveUpdateSettings(updateRepository, autoCheckUpdates)
                    if (autoCheckUpdates && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("保存并检查更新") }
            OutlinedButton(
                onClick = { viewModel.checkForUpdate(manual = true) },
                enabled = !updateState.checking,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (updateState.checking) {
                    CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                }
                Text(if (updateState.checking) "正在检查…" else "立即检查更新")
            }
            Text(
                "覆盖安装会保留本地学习数据。Android 会要求你确认安装，应用无法静默更新。",
                style = MaterialTheme.typography.bodySmall
            )
        }

        SectionCard("数据设置") {
            OutlinedButton(
                onClick = { confirmReset = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) { Text("清空学习数据") }
            Text("导出学习记录将在后续版本提供。", style = MaterialTheme.typography.bodySmall)
        }
    }

    if (confirmReset) {
        AlertDialog(
            onDismissRequest = { confirmReset = false },
            title = { Text("清空学习数据？") },
            text = { Text("水平测试、单词进度、错词、写作和报告都会被清空。此操作无法撤销。") },
            confirmButton = {
                TextButton(onClick = {
                    confirmReset = false
                    viewModel.clearLearningData(onReset)
                }) { Text("确认清空", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmReset = false }) { Text("取消") }
            }
        )
    }
}

@Composable
private fun NumberSetting(label: String, value: Int, onChange: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilledTonalButton(onClick = { onChange(value - 1) }) { Text("−") }
            Text("$value", modifier = Modifier.padding(top = 10.dp))
            FilledTonalButton(onClick = { onChange(value + 1) }) { Text("+") }
        }
    }
}
