package com.chaeda.chaeda.presentation.review.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.ReviewFolderDTO
import com.chaeda.domain.entity.ReviewPdfDTO
import com.chaeda.domain.entity.ReviewProblemDTO
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.repository.ReviewRepository
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
class AddProblemViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _dateString = MutableStateFlow<String>("")
    val dateString : StateFlow<String> = _dateString.asStateFlow()

    fun updateDateString(ds: String) {
        _dateString.value = ds
    }

    private val _imageKey = MutableStateFlow<String>("")
    val imageKey : StateFlow<String> = _imageKey.asStateFlow()

    fun updateImageKey(ik: String) {
        _imageKey.value = ik
    }

    private val _answer = MutableStateFlow<String>("")
    val answer : StateFlow<String> = _answer.asStateFlow()

    fun updateAnswer(ans: String) {
        _answer.value = ans
    }

    private val _chapter = MutableStateFlow<Chapter?>(null)
    val chapter : StateFlow<Chapter?> = _chapter.asStateFlow()

    fun updateChapter(chp: Chapter) {
        _chapter.value = chp
    }

    private val _textbookName = MutableStateFlow<String>("")
    val textbookName : StateFlow<String> = _textbookName.asStateFlow()

    fun updateTextbookName(name: String) {
        _textbookName.value = name
    }

    private val _problemNum = MutableStateFlow<String>("")
    val problemNum : StateFlow<String> = _problemNum.asStateFlow()

    fun updateProblemNum(pn: String) {
        _problemNum.value = pn
    }

    private val isNotBlank: StateFlow<Boolean> = combine(
        textbookName,
        problemNum
    ) { tn, pn ->
        tn.isNotBlank() && pn.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    val isValid: StateFlow<Boolean> = combine(
        dateString,
        imageKey,
        answer,
        chapter,
        isNotBlank
    ) { ds, ik, ans, cht, nb ->
        Pattern.matches(DATE_REGEX, ds) && ans.isNotBlank() && cht != null && ik.isNotBlank() && nb
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private var _reviewState = MutableStateFlow<ReviewState>(ReviewState.Init)
    val reviewState: StateFlow<ReviewState> = _reviewState.asStateFlow()

    fun postProblemToBox() {
        viewModelScope.launch {
            reviewRepository.postProblemToBox(
                ReviewProblemDTO(
                    reviewNoteProblemId = null,
                    incorrectDate = dateString.value,
                    imageKey = imageKey.value,
                    presignedUrl = ""
                ),
                fileExtension = "PNG",
                answer = answer.value,
                textbookName = textbookName.value,
                problemNum = problemNum.value,
                chapter = chapter.value!!
            )
                .onSuccess {
                    _reviewState.value = ReviewState.PostProblemSuccess("")

                }
                .onFailure {
                    _reviewState.value = ReviewState.Failure(it.message!!)
                }
        }
    }

    companion object {
        private const val DATE_REGEX = "^(\\d{4})-(\\d{2})-(\\d{2})\$"
        const val IMAGE_TYPE_HOMEWORK_THUMBNAIL = "HOMEWORK_THUMBNAIL"
        const val IMAGE_TYPE_REVIEW_NOTE_PROBLEM = "REVIEW_NOTE_PROBLEM"
    }
}

sealed interface ReviewState {
    object Init: ReviewState
    object Success: ReviewState
    data class PostProblemSuccess(val url: String): ReviewState
    data class GetProblemsFromBoxSuccess(val urls: List<ReviewProblemDTO>): ReviewState
    object PostNewFolderSuccess: ReviewState
    data class GetProblemsInFolderSuccess(val urls: List<ReviewProblemDTO>): ReviewState
    data class GetReviewFolderListSuccess(val list: List<ReviewFolderDTO>): ReviewState
    object PostMakeReviewPdfSuccess: ReviewState
    data class GetReviewPdfListSuccess(val list: List<ReviewPdfDTO>): ReviewState
    data class GetReviewPdfSuccess(val url: String): ReviewState
    data class Failure(val msg: String): ReviewState
}