package com.chaeda.chaeda.presentation.review

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
class ReviewViewModel @Inject constructor(
    private val repository: ReviewRepository
) : ViewModel() {

    private var _reviewState = MutableStateFlow<ReviewState>(ReviewState.Init)
    val reviewState: StateFlow<ReviewState> = _reviewState.asStateFlow()

    fun getReviewFolderList() {
        viewModelScope.launch {
            repository.getReviewFolderList()
                .onSuccess {
                    _reviewState.value = ReviewState.GetReviewFolderListSuccess(it)
                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }

}