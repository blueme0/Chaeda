package com.chaeda.chaeda.presentation.review.box

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.review.add.ReviewState
import com.chaeda.domain.repository.ImageRepository
import com.chaeda.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemBoxViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _reviewState = MutableStateFlow<ReviewState>(ReviewState.Init)
    val reviewState: StateFlow<ReviewState> = _reviewState.asStateFlow()

    fun getProblemsFromBox() {
        viewModelScope.launch {
            repository.getProblemsFromBox()
                .onSuccess {
                    _reviewState.value = ReviewState.GetProblemsFromBoxSuccess(it)
                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }
}