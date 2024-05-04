package com.chaeda.chaeda.presentation.homework.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.homework.AssignmentState
import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.repository.HomeworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AddAssignmentViewModel @Inject constructor(
    private val homeworkRepository: HomeworkRepository
) : ViewModel() {

    private val _textbook = MutableStateFlow<String>("")
    val textbook: StateFlow<String> = _textbook.asStateFlow()

    fun updateTextbook(name: String) {
        _textbook.value = name
    }

    private val _textbookId = MutableStateFlow<Int>(0)
    val textbookId: StateFlow<Int> = _textbookId.asStateFlow()

    fun updateTextbookId(id: Int) {
        _textbookId.value = id
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

    private val _due = MutableStateFlow<String>("")
    val due: StateFlow<String> = _due.asStateFlow()

    fun updateDue(year: Int, month: Int, date: Int) {
        _due.value = "$year-$month-$date"
    }

    fun updateDueDate(date: String) {
        _due.value = date
    }

    val assignmentValid: StateFlow<Boolean> = combine(
        title,
        startRange,
        endRange,
        due,
        textbookId
    ) { ts, sr, er, td, tId ->
        ts.isNotBlank() && sr > 0 && sr <= er && Pattern.matches(DATE_REGEX, td) && tId >= 0
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private var _assignmentState = MutableStateFlow<AssignmentState>(AssignmentState.Init)
    val assignmentState: StateFlow<AssignmentState> = _assignmentState.asStateFlow()

    fun postAssignment() {
        viewModelScope.launch {
            homeworkRepository.postAssignment(
                AssignmentDTO(
                    _title.value,
                    _startRange.value,
                    _endRange.value,
                    _due.value,
                    null
                ),
                _textbookId.value
            )
                .onSuccess {
                    _assignmentState.value = AssignmentState.UploadSuccess(it)
                }
                .onFailure {
                    _assignmentState.value = AssignmentState.Failure(it.message!!)
                }
        }
    }

    companion object {
        private const val DATE_REGEX = "^(\\\\d{4})-(\\\\d{2})-(\\\\d{2})\$"
    }
}