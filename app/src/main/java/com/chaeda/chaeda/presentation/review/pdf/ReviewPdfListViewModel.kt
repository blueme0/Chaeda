package com.chaeda.chaeda.presentation.review.pdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.review.add.ReviewState
import com.chaeda.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewPdfListViewModel @Inject constructor(
    private val repository: ReviewRepository
) : ViewModel() {

    private val _reviewState = MutableStateFlow<ReviewState>(ReviewState.Init)
    val reviewState: StateFlow<ReviewState> = _reviewState.asStateFlow()

    fun getReviewPdfList() {
        viewModelScope.launch {
            repository.getReviewPdfList()
                .onSuccess {
                    _reviewState.value = ReviewState.GetReviewPdfListSuccess(it)
                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }

    fun getReviewPdf(pdfId: Long) {
        viewModelScope.launch {
            repository.getReviewPdf(pdfId)
                .onSuccess {
                    _reviewState.value = ReviewState.GetReviewPdfSuccess(it)
                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }
}