package com.chaeda.chaeda.presentation.review.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.ReviewProblemDTO
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.enumSet.Concept
import com.chaeda.domain.enumSet.Subject
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

    private val _imageUri = MutableStateFlow<Uri>(Uri.parse(""))
    val imageUri : StateFlow<Uri> = _imageUri.asStateFlow()

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    private val _answer = MutableStateFlow<String>("")
    val answer : StateFlow<String> = _answer.asStateFlow()

    private val _subject = MutableStateFlow<Subject?>(null)
    val subject : StateFlow<Subject?> = _subject.asStateFlow()

    private val _chapter = MutableStateFlow<Chapter?>(null)
    val chapter : StateFlow<Chapter?> = _chapter.asStateFlow()

    private val _concept = MutableStateFlow<Concept?>(null)
    val concept : StateFlow<Concept?> = _concept.asStateFlow()

    val isValid: StateFlow<Boolean> = combine(
        dateString,
        imageUri,
        answer,
        concept
    ) { ds, iu, ans, cpt ->
        Pattern.matches(DATE_REGEX, ds) && ans.isNotBlank() && cpt != null
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private var _reviewState = MutableStateFlow<ReviewState>(ReviewState.Init)
    val reviewState: StateFlow<ReviewState> = _reviewState.asStateFlow()

    fun postProblemToBox() {
        viewModelScope.launch {
            reviewRepository.postProblemToBox(
                ReviewProblemDTO(
                    reviewNoteProblemId = null,
                    incorrectDate = "",
                    imageKey = "",
                    presignedUrl = ""
                ),
                imageFileExtension = "",
                answer = "",
                textbookId = 0,
                problemNum = "",
                chapter = Chapter.Plane_Vectors
            )
                .onSuccess {

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
    data class Failure(val msg: String): ReviewState
}