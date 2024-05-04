package com.chaeda.chaeda.presentation.homework.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.homework.AssignmentState
import com.chaeda.domain.entity.AssignmentResultDTO
import com.chaeda.domain.repository.HomeworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val hwRepository: HomeworkRepository
) : ViewModel() {

    private var _assignmentState = MutableStateFlow<AssignmentState>(AssignmentState.Init)
    val assignmentState: StateFlow<AssignmentState> = _assignmentState.asStateFlow()

    private var _problemRange = MutableStateFlow<HashMap<Int, List<String>>>(HashMap())
    val problemRange: StateFlow<HashMap<Int, List<String>>> = _problemRange.asStateFlow()

    // HashMap으로 페이지 별 문항들 저장하기
    fun getProblemRanges(id: Int) {
        viewModelScope.launch {
            hwRepository.getProblemRangeWithPage(id)
                .onSuccess {
                    val list = it
                    _problemRange.value.clear()
                    for (dto in list) {
                        _problemRange.value[dto.pageNum] = dto.problemNumbers
                    }
                    _assignmentState.value = AssignmentState.GetRangeSuccess
                }
                .onFailure {
                    _assignmentState.value = AssignmentState.Failure(it.message!!)
                }
        }
    }

    private var _currentPage = MutableStateFlow<Int>(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    fun updateCurrentPage(page: Int) {
        _currentPage.value = page
    }

    private var _assignmentResult = MutableStateFlow<HashMap<Int, HashMap<String, String>>>(HashMap())
    val assignmentResult: StateFlow<HashMap<Int, HashMap<String, String>>> = _assignmentResult.asStateFlow()

    fun updateRecords(page: Int, problem: String, answer: String?) {
        if (!_assignmentResult.value.containsKey(page))
            _assignmentResult.value[page] = HashMap<String, String>()
        if (answer == null) _assignmentResult.value[page]?.remove(problem)
        else _assignmentResult.value[page]?.set(problem, answer)
    }

    fun findWrong(page: Int, problem: String): String? {
        return _assignmentResult.value[page]?.get(problem)
    }

    fun getPage(page: Int): List<String>? {
        return _problemRange.value[page]
    }

    fun postAssignmentResult(id: Int) {
        val results = mutableListOf<AssignmentResultDTO>()
        for (one in _assignmentResult.value) {
            results.add(AssignmentResultDTO(one.key, one.value))
        }
        viewModelScope.launch {
            hwRepository.postAssignmentResult(id, results)
                .onSuccess {
                    _assignmentState.value = AssignmentState.SubmitSuccess
                }
                .onFailure {
                    _assignmentState.value = AssignmentState.Failure(it.message!!)
                }
        }
    }
}
