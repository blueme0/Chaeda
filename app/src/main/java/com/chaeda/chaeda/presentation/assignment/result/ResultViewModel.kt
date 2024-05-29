package com.chaeda.chaeda.presentation.assignment.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.assignment.AssignmentState
import com.chaeda.domain.entity.AssignmentResultDTO
import com.chaeda.domain.repository.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val hwRepository: AssignmentRepository
) : ViewModel() {

    private var _assignmentState = MutableStateFlow<AssignmentState>(AssignmentState.Init)
    val assignmentState: StateFlow<AssignmentState> = _assignmentState.asStateFlow()

    private var _problemRange = MutableStateFlow<HashMap<Int, List<String>>>(HashMap())
    val problemRange: StateFlow<HashMap<Int, List<String>>> = _problemRange.asStateFlow()

    // HashMap으로 페이지 별 문항들 저장하기
    fun getProblemRanges(id: Long) {
        viewModelScope.launch {
            hwRepository.getProblemRangeWithPage(id)
                .onSuccess {
                    val list = it
                    _problemRange.value.clear()
                    _totalProblemCount.value = 0
                    for (dto in list) {
                        _problemRange.value[dto.pageNum] = dto.problemNumbers
                        _totalProblemCount.value += dto.problemNumbers.size
                    }
                    _assignmentState.value = AssignmentState.GetRangeSuccess
                }
                .onFailure {
                    _assignmentState.value = AssignmentState.Failure(it.message!!)
                }
        }
    }

    private var _totalProblemCount = MutableStateFlow<Int>(0)
    val totalProblemCount: StateFlow<Int> = _totalProblemCount.asStateFlow()

    private var _selectedProblemCount = MutableStateFlow<Int>(0)
    val selectedProblemCount: StateFlow<Int> = _selectedProblemCount.asStateFlow()

    fun plusSelectedProblemCount(isPlus: Boolean) {
        if (isPlus) _selectedProblemCount.value += 1
        else _selectedProblemCount.value -= 1
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
        if (answer == null) {
            _assignmentResult.value[page]?.remove(problem)
            plusSelectedProblemCount(false)
        }
        else {
            if (_assignmentResult.value[page]?.containsKey(problem) != true)
                plusSelectedProblemCount(true)
            _assignmentResult.value[page]?.set(problem, answer)
        }
    }

    fun findWrong(page: Int, problem: String): String? {
        return _assignmentResult.value[page]?.get(problem)
    }

    fun getPage(page: Int): List<String>? {
        return _problemRange.value[page]
    }

    fun postAssignmentResult(id: Long) {
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
