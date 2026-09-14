package com.personalieltscoach.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.personalieltscoach.domain.service.ContextDictionary
import com.personalieltscoach.domain.service.WordContext
import com.personalieltscoach.domain.service.ContextGrammar
import com.personalieltscoach.domain.service.GrammarToken
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordContextSheet(sentence: String, offset: Int, speech: SpeechController,
                     onDismiss: () -> Unit, contextualMeaning: String = "",
                     grammar: List<GrammarToken> = emptyList(),
                     onCollect: ((WordContext) -> Unit)? = null) {
    val application = LocalContext.current.applicationContext
    var context by remember(sentence, offset, contextualMeaning, grammar) { mutableStateOf<WordContext?>(null) }
    LaunchedEffect(sentence, offset, contextualMeaning, grammar) {
        val annotations = if (grammar.isNotEmpty()) grammar else try { ContextGrammar.lookup(application, sentence) }
            catch (cancelled: CancellationException) { throw cancelled }
            catch (_: Exception) { emptyList() }
        val lexicon = try { ContextGrammar.dictionary(application) }
            catch (cancelled: CancellationException) { throw cancelled }
            catch (_: Exception) { emptyMap() }
        context = withContext(Dispatchers.Default) { ContextDictionary.explain(sentence, offset, contextualMeaning, annotations, lexicon) }
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
      Column(Modifier.fillMaxWidth().fillMaxHeight(.85f).navigationBarsPadding()) {
        // Stable height during asynchronous dictionary loading avoids shifting sheet anchors.
        Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)) {
            context?.let { entry ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(Modifier.weight(1f)) {
                        Text(entry.word, style = MaterialTheme.typography.headlineMedium)
                        if (entry.phonetic.isNotBlank()) Text(if (entry.word.equals(entry.lemma, true)) entry.phonetic else "对应词音标 ${entry.phonetic}")
                        if (!entry.word.equals(entry.lemma, true)) Text("对应词：${entry.lemma}", style = MaterialTheme.typography.labelLarge)
                    }
                    SpeechButton(entry.word, speech, contentDescription = "单独朗读 ${entry.word}")
                }
                Text(if (entry.contextual) "此处含义" else "词库释义 · 请结合上下文", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                Text(entry.meaning, style = MaterialTheme.typography.titleMedium)
                HorizontalDivider()
                Text(sentence, style = MaterialTheme.typography.bodyLarge)
                Text("为什么这样用", style = MaterialTheme.typography.titleMedium)
                Text(entry.explanation)
                if (onCollect != null && !entry.meaning.startsWith("本地词库暂无")) {
                    OutlinedButton(onClick = { onCollect(entry) }) { Text("收藏到阅读生词") }
                }
                Text("释义与规则在本机查询；只有点击发音才会请求在线词典，已缓存的音频无需联网。",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } ?: CircularProgressIndicator(Modifier.size(24.dp))
        }
        TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("回到句子") }
      }
    }
}
