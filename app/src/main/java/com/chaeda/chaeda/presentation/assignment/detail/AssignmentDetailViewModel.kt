package com.chaeda.chaeda.presentation.assignment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.assignment.AssignmentState
import com.chaeda.domain.entity.Textbook
import com.chaeda.domain.repository.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentDetailViewModel @Inject constructor(
    private val assignmentRepository: AssignmentRepository
) : ViewModel() {

    private var _assignmentState = MutableStateFlow<AssignmentState>(AssignmentState.Init)
    val assignmentState: StateFlow<AssignmentState> = _assignmentState.asStateFlow()

    private var _textbook = MutableStateFlow<Textbook?>(null)
    val textbook: StateFlow<Textbook?> = _textbook.asStateFlow()

    fun updateTextbook(tb: Textbook) {
        _textbook.value = tb
    }

    fun getAssignmentById(id: Long) {
        viewModelScope.launch {
            assignmentRepository.getAssignmentById(id)
                .onSuccess {
                    _assignmentState.value = AssignmentState.GetByIdSuccess(it)
                }
                .onFailure {
                    _assignmentState.value = AssignmentState.Failure(it.message!!)
                }
        }
    }

    fun deleteAssignmentById(id: Long) {
        viewModelScope.launch {
            assignmentRepository.deleteAssignmentById(id)
                .onSuccess {
                    _assignmentState.value = AssignmentState.DeleteByIdSuccess
                }
                .onFailure {
                    _assignmentState.value = AssignmentState.Failure(it.message!!)
                }
        }
    }
}