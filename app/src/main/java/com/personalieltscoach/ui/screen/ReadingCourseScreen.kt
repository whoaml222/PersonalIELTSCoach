package com.personalieltscoach.ui.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel as composeViewModel
import com.personalieltscoach.reading.*
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.component.*
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

@Composable
fun ReadingScreen(viewModel: CoachViewModel, onBack: () -> Unit) {
    val reader: ReaderViewModel = composeViewModel()
    val library by reader.library.collectAsStateWithLifecycle()
    val progress by reader.progress.collectAsStateWithLifecycle()
    val progressLoaded by reader.progressLoaded.collectAsStateWithLifecycle()
    val importing by reader.importing.collectAsStateWithLifecycle()
    val message by reader.message.collectAsStateWithLifecycle()
    var free by rememberSaveable { mutableStateOf(false) }
    var review by rememberSaveable { mutableStateOf(false) }
    var courseId by rememberSaveable { mutableStateOf<String?>(null) }
    var lessonId by rememberSaveable { mutableStateOf<String?>(null) }
    val courseEntity = library.books.firstOrNull { it.entity.id == courseId }?.entity
    var course by remember(courseEntity) { mutableStateOf<ReadingCourse?>(null) }
    var loadingCourse by remember(courseEntity) { mutableStateOf(courseEntity != null) }
    LaunchedEffect(courseEntity) {
        try { course = courseEntity?.let { reader.repository.loadCourse(it) } }
        catch (cancelled: CancellationException) { throw cancelled }
        catch (_: Exception) { course = null }
        finally { loadingCourse = false }
    }
    val lesson = course?.lessons?.firstOrNull { it.id == lessonId }
    val picker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> uri?.let(reader::importCourse) }
    fun back() {
        when { free -> free = false; review -> review = false; lessonId != null -> lessonId = null
            courseId != null -> courseId = null; else -> onBack() }
    }
    BackHandler(enabled = free || review || courseId != null, onBack = ::back)
    if (free) { FreeReadingScreen(viewModel, ::back); return }
    if (review) { ReadingVocabularyScreen(reader, ::back); return }
    if (!progressLoaded || !library.loaded || loadingCourse) {
        CoachScaffold("阅读书架", ::back) { CircularProgressIndicator(); Text("正在恢复阅读位置…") }
        return
    }
    if (courseId != null && course == null) {
        CoachScaffold("课程暂时无法打开", ::back) {
            Text("课程文件可能不完整。请返回书架重新导入同一课程包；已有学习记录会保留。")
        }
        return
    }
    val loadedCourse = course
    if (loadedCourse != null && courseEntity != null && lesson != null) {
        val saved = progress.firstOrNull { it.courseId == loadedCourse.id && it.lessonId == lesson.id }
        key(loadedCourse.id, lesson.id) { LessonReader(reader, courseEntity, lesson, saved, ::back) }
        return
    }
    CoachScaffold(loadedCourse?.title ?: "阅读书架", ::back) {
        library.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        message?.let { SectionCard("提示") { Text(it); TextButton(onClick = reader::clearMessage) { Text("知道了") } } }
        if (loadedCourse == null) {
            SectionCard("每天一小篇，随时接着读") {
                Text("导入后可离线阅读和听原音频。阅读位置会自动保存，不必一次读完。")
                Button(onClick = { picker.launch(arrayOf("*/*")) }, enabled = !importing,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text(if (importing) "正在校验并导入…" else "导入个人课程包") }
                if (importing) LinearProgressIndicator(Modifier.fillMaxWidth())
                Text("选择整理后的 .ieltscourse 文件，不是原始 PDF 压缩包。资料只保存在本机，不会上传。",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = { review = true }, modifier = Modifier.weight(1f)) { Text("阅读生词") }
                OutlinedButton(onClick = { free = true }, modifier = Modifier.weight(1f)) { Text("粘贴文章") }
            }
            library.books.forEach { book ->
                val entity = book.entity
                SectionCard(entity.title) {
                    val read = progress.count { it.courseId == entity.id && it.completed }
                    Text("已完成 $read / ${book.outline.readyCount} 篇可读正文 · 共 ${book.outline.lessonCount} 篇")
                    LinearProgressIndicator(progress = { (read.toFloat() / book.outline.readyCount.coerceAtLeast(1)).coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth())
                    Button(onClick = { courseId = entity.id }) { Text("打开课程") }
                }
            }
        } else {
            val course = loadedCourse
            var showSource by rememberSaveable(course.id) { mutableStateOf(false) }
            TextButton(onClick = { showSource = !showSource }) { Text(if (showSource) "收起课程说明" else "课程来源与使用说明") }
            if (showSource) {
                Text(course.description, style = MaterialTheme.typography.bodySmall)
                if (course.attribution.isNotBlank()) Text(course.attribution, style = MaterialTheme.typography.bodySmall)
            }
            val unfinished = progress.filter { it.courseId == course.id && !it.completed }.maxByOrNull { it.updatedAt }
            val recommended = course.lessons.firstOrNull { it.id == unfinished?.lessonId && it.ready }
                ?: course.lessons.sortedBy { it.order }.firstOrNull { candidate -> candidate.ready &&
                    progress.none { it.courseId == course.id && it.lessonId == candidate.id && it.completed } }
            recommended?.let { next -> SectionCard(if (unfinished?.lessonId == next.id) "接着上次读" else "今日推荐 · 不限完成时间") {
                Text("${next.order}. ${next.title}", style = MaterialTheme.typography.titleLarge)
                Text("先读懂一小段，再选 5–10 个需要记忆的词。")
                Button(onClick = { lessonId = next.id }, modifier = Modifier.fillMaxWidth()) { Text("开始阅读") }
            } }
            var query by rememberSaveable(course.id) { mutableStateOf("") }
            var page by rememberSaveable(course.id) { mutableStateOf(0) }
            OutlinedTextField(query, { query = it; page = 0 }, label = { Text("搜索标题或课文编号") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            val filtered = course.lessons.sortedBy { it.order }.filter { query.isBlank() || it.title.contains(query, true) || it.order.toString() == query }
            filtered.drop(page * 12).take(12).forEach { item ->
                val itemProgress = progress.firstOrNull { it.courseId == course.id && it.lessonId == item.id }
                OutlinedCard(onClick = { lessonId = item.id }, modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("${item.order}. ${item.title}", style = MaterialTheme.typography.titleMedium)
                        Text(when { !item.ready -> "正文待补充"; itemProgress?.completed == true -> "已完成"; itemProgress != null -> "阅读中"; else -> "未开始" } +
                            " · ${item.sentences.sumOf { com.personalieltscoach.domain.service.TextSegmenter.words(it.text).size }} 词 · " + if (item.audio != null) "有原音频" else "无原音频",
                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = { page-- }, enabled = page > 0) { Text("上一页") }
                Text("${page + 1} / ${((filtered.size + 11) / 12).coerceAtLeast(1)}")
                TextButton(onClick = { page++ }, enabled = (page + 1) * 12 < filtered.size) { Text("下一页") }
            }
        }
    }
}

@Composable
private fun LessonReader(reader: ReaderViewModel, course: CourseEntity, lesson: ReadingLesson,
                         saved: ReadingProgressEntity?, onBack: () -> Unit) {
    var index by rememberSaveable { mutableStateOf((saved?.sentenceIndex ?: 0).coerceIn(0, lesson.sentences.lastIndex.coerceAtLeast(0))) }
    var full by rememberSaveable { mutableStateOf(false) }
    var translation by rememberSaveable(index, full) { mutableStateOf(false) }
    val speech = rememberSpeechController()
    val saving by reader.saving.collectAsStateWithLifecycle()
    val message by reader.message.collectAsStateWithLifecycle()
    val audioFile = remember(course.folder, lesson.id) { reader.repository.audioFile(course, lesson) }
    val meanings = remember(lesson.id) { lesson.vocabulary.associate { it.word.lowercase() to it.meaning } }
    LaunchedEffect(index) { if (lesson.ready) reader.position(course.id, lesson.id, sentence = index) }
    CoachScaffold("${lesson.order}. ${lesson.title}", onBack) {
        message?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
        if (audioFile != null) SectionCard("听课文") {
            LocalReadingAudio(audioFile, saved?.audioPosition ?: 0) { reader.position(course.id, lesson.id, audio = it) }
        } else Text("本篇没有原录音。点句子右侧可使用在线英音词典；点词查询不联网。", style = MaterialTheme.typography.bodySmall)
        if (lesson.note.isNotBlank()) Text(lesson.note, style = MaterialTheme.typography.bodySmall)
        if (!lesson.ready || lesson.sentences.isEmpty()) {
            SectionCard("正文待补充") { Text("源资料缺少可核对正文，暂不计入已完成。可以先听现有原录音、查看词表。") }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = !full, onClick = { full = false }, label = { Text("逐句精读") })
                FilterChip(selected = full, onClick = { full = true }, label = { Text("全文阅读") })
            }
            SectionCard(if (full) "课文" else "第 ${index + 1} / ${lesson.sentences.size} 句") {
                val displayed = if (full) lesson.sentences else listOf(lesson.sentences[index])
                displayed.forEach { sentence ->
                    SpokenEnglishText(sentence.text, speech, showHint = !full, contextMeanings = meanings, grammar = sentence.grammar,
                        onCollect = { reader.collect(course.id, lesson.id, it.lemma, it.meaning, sentence.text) })
                    if (translation && sentence.translation.isNotBlank()) Text(sentence.translation, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                TextButton(onClick = { translation = !translation }) { Text(if (translation) "隐藏中文" else "查看中文参考") }
                if (translation && (full || lesson.sentences[index].translation.isBlank())) {
                    Text(if (lesson.translation.isNotBlank()) "全文参考：\n${lesson.translation}" else "这一段暂没有校对后的中文。先点词查看已有释义。")
                }
                if (!full) Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedButton(onClick = { speech.stop(); index-- }, enabled = index > 0) { Text("上一句") }
                    Button(onClick = { speech.stop(); index++ }, enabled = index < lesson.sentences.lastIndex) { Text("下一句") }
                }
            }
            if (full || index == lesson.sentences.lastIndex) Button(onClick = { reader.complete(course.id, lesson) },
                enabled = !saving && saved?.completed != true, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) {
                Text(if (saved?.completed == true) "本篇已完成" else "我已读完本篇")
            }
        }
        if (lesson.vocabulary.isNotEmpty()) SectionCard("本篇词汇 · 按需收藏") {
            Text("收藏不等于掌握。每天挑 5–10 个需要的词即可，不影响原有两套词库的复习比例。", style = MaterialTheme.typography.bodySmall)
            lesson.vocabulary.forEach { gloss ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(Modifier.weight(1f)) { Text(gloss.word, style = MaterialTheme.typography.titleMedium); Text(gloss.meaning) }
                    SpeechButton(gloss.word, speech)
                    TextButton(onClick = {
                        val sentence = lesson.sentences.firstOrNull { Regex("(?i)(?<![a-z])${Regex.escape(gloss.word)}(?![a-z])").containsMatchIn(it.text) }?.text.orEmpty()
                        reader.collect(course.id, lesson.id, gloss.word, gloss.meaning, sentence)
                    }, enabled = !saving) { Text("收藏") }
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun ReadingVocabularyScreen(reader: ReaderViewModel, onBack: () -> Unit) {
    val words by reader.vocabulary.collectAsStateWithLifecycle()
    val saving by reader.saving.collectAsStateWithLifecycle()
    val message by reader.message.collectAsStateWithLifecycle()
    val speech = rememberSpeechController()
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) { while (true) { now = System.currentTimeMillis(); delay(30_000) } }
    val dayStart = java.time.Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault()).toLocalDate()
        .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val due = words.filter { it.status != "NEW" && it.dueAt <= now && it.lastStudiedAt < dayStart }
    val fresh = words.filter { it.status == "NEW" }
    var learnNew by rememberSaveable { mutableStateOf(false) }
    val word = (if (learnNew) fresh else due).firstOrNull()
    var stage by remember(word?.id, learnNew) { mutableIntStateOf(if (learnNew) 0 else 1) }
    CoachScaffold("阅读生词", onBack) {
        Text("独立复习，不占新概念与 Paul1000 的各半名额。")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(!learnNew, { learnNew = false }, label = { Text("到期复习 ${due.size}") })
            FilterChip(learnNew, { learnNew = true }, label = { Text("待学 ${fresh.size}") })
        }
        message?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
        if (word == null) SectionCard("本轮已完成") { Text(if (learnNew) "没有待学阅读生词，读文章时可以按需收藏。" else "暂时没有到期词，可以学习已收藏的新词。") }
        else SectionCard(if (learnNew) "先学习，再回忆" else "先回忆，再看答案") {
            Row { Text(word.word, modifier = Modifier.weight(1f), style = MaterialTheme.typography.headlineMedium); SpeechButton(word.word, speech) }
            SpokenEnglishText(word.sentence, speech)
            if (stage != 1) Text(word.meaning)
            if (stage < 2) Button(onClick = { stage++ }) { Text(if (stage == 0) "遮住释义，试着回忆" else "显示答案") }
            else Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { reader.rate(word.id, false) }, enabled = !saving) { Text("没想起来") }
                Button(onClick = { reader.rate(word.id, true) }, enabled = !saving) { Text("记得了") }
            }
        }
    }
}
