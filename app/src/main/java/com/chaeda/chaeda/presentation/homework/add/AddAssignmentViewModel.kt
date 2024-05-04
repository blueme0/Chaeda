package com.chaeda.chaeda.presentation.homework.add

import androidx.lifecycle.ViewModel
import com.chaeda.domain.repository.HomeworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddAssignmentViewModel @Inject constructor(
    private val homeworkRepository: HomeworkRepository
) : ViewModel() {

    /**
     * private val _loginId = MutableStateFlow<String>("")
     *     val loginId: StateFlow<String> = _loginId.asStateFlow()
     */

    private val _textbook = MutableStateFlow<String>("")
    val textbook: StateFlow<String> = _textbook.asStateFlow()

    fun updateTextbook(name: String) {
        _textbook.value = name
    }

    private val _startRange = MutableStateFlow<Int>(0)
    val startRange: StateFlow<Int> = _startRange.asStateFlow()

    private val _endRange = MutableStateFlow<Int>(0)
    val endRange: StateFlow<Int> = _endRange.asStateFlow()

    fun updateRange(start: Int, end: Int) {
        _startRange.value = start
        _endRange.value = end
    }

    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> = _title.asStateFlow()

    fun updateTitle(name: String) {
        _title.value = name
    }

    private val _content = MutableStateFlow<String>("")
    val content: StateFlow<String> = _content.asStateFlow()

    fun updateContent(text: String) {
        _content.value = text
    }

    private val _due = MutableStateFlow<String>("")
    val due: StateFlow<String> = _due.asStateFlow()

    fun updateDue(year: Int, month: Int, date: Int, hour: Int, minute: Int) {
        _due.value = "$year.$month.$date. $hour:$minute"
    }

}