package com.personalieltscoach.reading

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.personalieltscoach.CoachApplication
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ReaderViewModel(application: Application) : AndroidViewModel(application) {
    val repository = (application as CoachApplication).container.readingRepository
    val library = repository.courses.map { rows ->
        ReadingLibrary(loaded = true, books = rows.map { ReadingBook(it, repository.outline(it)) })
    }.flowOn(Dispatchers.IO).catch { error ->
        if (error is CancellationException) throw error
        emit(ReadingLibrary(loaded = true, error = "书架暂时无法读取，请返回后重试。原资料未清空。"))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReadingLibrary())
    private val _progressLoaded = MutableStateFlow(false)
    val progressLoaded = _progressLoaded.asStateFlow()
    val progress = repository.progress.onEach { _progressLoaded.value = true }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val vocabulary = repository.vocabulary.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()
    private val _importing = MutableStateFlow(false)
    val importing = _importing.asStateFlow()
    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()
    fun clearMessage() { _message.value = null }
    fun importCourse(uri: Uri) {
        if (_importing.value) return
        _importing.value = true
        viewModelScope.launch {
            try {
                val course = repository.importCourse(uri)
                _message.value = "已导入 ${course.lessons.size} 篇；原有学习进度已保留"
            } catch (cancelled: CancellationException) { throw cancelled
            } catch (_: Exception) {
                _message.value = "导入失败：请确认选择的是整理后的 .ieltscourse 课程包，文件完整且空间足够。原课程和进度未清空。"
            } finally { _importing.value = false }
        }
    }
    fun position(course: String, lesson: String, sentence: Int? = null, audio: Int? = null) {
        viewModelScope.launch {
            try { repository.savePosition(course, lesson, sentence, audio) }
            catch (cancelled: CancellationException) { throw cancelled }
            catch (_: Exception) { _message.value = "阅读位置暂未保存，请检查剩余存储空间。" }
        }
    }
    fun complete(course: String, lesson: ReadingLesson) = save {
        repository.complete(course, lesson)
        _message.value = "本篇已完成。下次会优先推荐尚未读完的课文。"
    }
    fun collect(course: String, lesson: String, word: String, meaning: String, sentence: String) = save {
        repository.collect(course, lesson, word, meaning, sentence)
        _message.value = "已收藏到阅读生词。先学习，再安排复习；重复收藏不会增加计数。"
    }
    fun rate(id: String, correct: Boolean) = save { repository.rate(id, correct) }
    private fun save(block: suspend () -> Unit) {
        if (_saving.value) return
        _saving.value = true
        viewModelScope.launch {
            try { block() }
            catch (cancelled: CancellationException) { throw cancelled }
            catch (_: Exception) { _message.value = "保存失败，请重试；没有跳过当前学习内容。" }
            finally { _saving.value = false }
        }
    }
}
