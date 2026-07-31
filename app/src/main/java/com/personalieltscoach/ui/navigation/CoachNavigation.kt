package com.personalieltscoach.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.screen.*

object Routes {
    const val Onboarding = "onboarding"
    const val Placement = "placement"
    const val Home = "home"
    const val Vocabulary = "vocabulary"
    const val Review = "review"
    const val WrongWords = "wrong_words"
    const val Sentence = "sentence"
    const val Reading = "reading"
    const val Writing = "writing"
    const val Progress = "progress"
    const val Settings = "settings"
}

@Composable
fun CoachApp(viewModel: CoachViewModel, startDestination: String) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val message by viewModel.message.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }
    LaunchedEffect(updateState.error) {
        updateState.error?.let { snackbarHostState.showSnackbar(it) }
    }

    Box(Modifier.fillMaxSize()) {
        CoachNavHost(navController, viewModel, startDestination)
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(12.dp)
        )
    }
    UpdateDialog(
        state = updateState,
        onDownload = viewModel::downloadUpdate,
        onInstall = viewModel::installDownloadedUpdate,
        onDismiss = viewModel::dismissUpdate,
        onCancelDownload = viewModel::cancelUpdateDownload
    )
}

@Composable
private fun UpdateDialog(
    state: com.personalieltscoach.update.UpdateUiState,
    onDownload: () -> Unit,
    onInstall: () -> Unit,
    onDismiss: () -> Unit,
    onCancelDownload: () -> Unit
) {
    when {
        state.downloadedFile != null -> AlertDialog(
            onDismissRequest = {},
            title = { Text("更新已下载") },
            text = { Text("安装时 Android 会要求你确认覆盖更新，学习数据会保留。") },
            confirmButton = {
                Button(onClick = onInstall) { Text("安装更新") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("稍后") }
            }
        )
        state.downloading -> AlertDialog(
            onDismissRequest = {},
            title = { Text("正在下载更新") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    LinearProgressIndicator(
                        progress = { (state.downloadProgress ?: 0) / 100f },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        state.downloadProgress?.let { "$it%" } ?: "正在获取下载进度…"
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = onCancelDownload) { Text("取消下载") }
            }
        )
        state.available != null -> {
            val update = state.available
            AlertDialog(
                onDismissRequest = onDismiss,
                title = { Text("发现新版本 ${update.version}") },
                text = {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 360.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(update.title, style = MaterialTheme.typography.titleMedium)
                        Text(update.releaseNotes)
                        if (update.assetSize > 0) {
                            Text(
                                "安装包约 ${update.assetSize / 1024 / 1024} MB",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = onDownload) { Text("立即更新") }
                },
                dismissButton = {
                    TextButton(onClick = onDismiss) { Text("稍后") }
                }
            )
        }
    }
}

@Composable
private fun CoachNavHost(
    nav: NavHostController,
    viewModel: CoachViewModel,
    startDestination: String
) {
    NavHost(navController = nav, startDestination = startDestination) {
        composable(Routes.Onboarding) {
            OnboardingScreen { nav.navigate(Routes.Placement) }
        }
        composable(Routes.Placement) {
            PlacementTestScreen(
                viewModel = viewModel,
                onFinished = {
                    nav.navigate(Routes.Home) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Home) {
            HomeScreen(viewModel = viewModel, navigate = nav::navigate)
        }
        composable(Routes.Vocabulary) { VocabularyScreen(viewModel) { nav.popBackStack() } }
        composable(Routes.Review) { ReviewScreen(viewModel) { nav.popBackStack() } }
        composable(Routes.WrongWords) { WrongWordsScreen(viewModel) { nav.popBackStack() } }
        composable(Routes.Sentence) { SentenceStudyScreen(viewModel) { nav.popBackStack() } }
        composable(Routes.Reading) { ReadingScreen(viewModel) { nav.popBackStack() } }
        composable(Routes.Writing) { WritingPracticeScreen(viewModel) { nav.popBackStack() } }
        composable(Routes.Progress) { ProgressScreen(viewModel) { nav.popBackStack() } }
        composable(Routes.Settings) {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { nav.popBackStack() },
                onReset = {
                    nav.navigate(Routes.Onboarding) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                }
            )
        }
    }
}
