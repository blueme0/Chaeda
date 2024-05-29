package com.chaeda.chaeda.presentation.review.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.review.add.ReviewState
import com.chaeda.domain.entity.ReviewFolder
import com.chaeda.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewFolderViewModel @Inject constructor(
    private val repository: ReviewRepository
) : ViewModel() {

    private val _reviewState = MutableStateFlow<ReviewState>(ReviewState.Init)
    val reviewState: StateFlow<ReviewState> = _reviewState.asStateFlow()

    private val _problemIds = MutableStateFlow<MutableList<Long>>(mutableListOf())
    val problemIds: StateFlow<MutableList<Long>> = _problemIds.asStateFlow()

    fun addProblemIds(ids: List<Long>) {
        _problemIds.value.addAll(ids)
    }

    fun setProblemIds(ids: MutableList<Long>) {
        _problemIds.value = ids
    }

    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> = _title.asStateFlow()

    fun updateTitle(str: String) {
        _title.value = str
    }

    private val _description = MutableStateFlow<String>("")
    val description: StateFlow<String> = _description.asStateFlow()

    fun updateDescription(str: String) {
        _description.value = str
    }

    val isValid: StateFlow<Boolean> = combine(
        title,
        description
    ) { tt, des ->
        tt.isNotBlank() && des.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    fun postNewFolder() {
        viewModelScope.launch {
            repository.postNewFolder(
                problemIds.value,
                ReviewFolder(
                    title.value,
                    description.value,
                    null,
                    null
                )
            )
                .onSuccess {
                    _reviewState.value = ReviewState.PostNewFolderSuccess
                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }

    fun getProblemsInFolder(folderId: Long) {
        viewModelScope.launch {
            repository.getProblemsInFolder(folderId)
                .onSuccess {
                    _reviewState.value = ReviewState.GetProblemsInFolderSuccess(it)
                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }

    fun postMakeReviewPdf(folderId: Long) {
        viewModelScope.launch {
            repository.postMakeReviewPdf(folderId)
                .onSuccess {
                    _reviewState.value = ReviewState.PostMakeReviewPdfSuccess
                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }


}