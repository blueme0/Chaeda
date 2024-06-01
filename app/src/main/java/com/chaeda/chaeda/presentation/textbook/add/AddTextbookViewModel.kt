package com.chaeda.chaeda.presentation.textbook.add

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.presentation.textbook.TextbookState
import com.chaeda.domain.entity.Textbook
import com.chaeda.domain.repository.ImageRepository
import com.chaeda.domain.repository.TextbookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddTextbookViewModel @Inject constructor(
    private val repository: TextbookRepository,
    private val fileRepository: ImageRepository
) : ViewModel() {

    private val _textbookState = MutableStateFlow<TextbookState>(TextbookState.Init)
    val textbookState: StateFlow<TextbookState> = _textbookState.asStateFlow()

    private val _textbookFile = MutableStateFlow<File?>(null)
    val textbookFile: StateFlow<File?> = _textbookFile.asStateFlow()

    fun updateFile(file: File?) {
        _textbookFile.value = file
    }
    /**
     * {
     *   "name": "쎈_고등수학상_2024",
     *   "startPageNum": 11,
     *   "lastPageNum": 224,
     *   "publisher": "좋은책 신사고",
     *   "targetGrade": "고1",
     *   "subject": "수학1",
     *   "publishYear": 2024,
     *   "fileExtension": "pdf"
     * }
     */

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name.asStateFlow()

    fun updateName(str: String) {
        _name.value = str
    }

    private val _publisher = MutableStateFlow<String>("")
    val publisher: StateFlow<String> = _publisher.asStateFlow()

    fun updatePublisher(str: String) {
        _publisher.value = str
    }

    private val _publishedYear = MutableStateFlow<String>("")
    val publishedYear: StateFlow<String> = _publishedYear.asStateFlow()

    fun updatePublishedYear(year: String) {
        _publishedYear.value = year
    }

    private val _grade = MutableStateFlow<String>("")
    val grade: StateFlow<String> = _grade.asStateFlow()

    fun updateGrade(gr: String) {
        _grade.value = gr
    }

    val isVaildTextbook: StateFlow<Boolean> = combine(
        textbookFile,
        name,
        publisher,
        publishedYear,
        grade
    ) { tf, nm, pub, py, gd ->
        tf != null && nm.isNotBlank() && pub.isNotBlank()
                && py.isNotBlank() && py.isDigitsOnly() && py.toInt() >= 1950 && py.toInt() <= LocalDateTime.now().year
                && gd.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _subject = MutableStateFlow<String>("")
    val subject: StateFlow<String> = _subject.asStateFlow()

    fun updateSubject(sub: String) {
        _subject.value = sub
    }

    private val _startRange = MutableStateFlow<Int>(0)
    val startRange: StateFlow<Int> = _startRange.asStateFlow()

    private val _endRange = MutableStateFlow<Int>(0)
    val endRange: StateFlow<Int> = _endRange.asStateFlow()

    fun updateRange(start: Int, end: Int) {
        _startRange.value = start
        _endRange.value = end
    }

    val isValidInput: StateFlow<Boolean> = combine(
        isVaildTextbook,
        subject,
        startRange,
        endRange
    ) { itb, sj, sr, er ->
        itb && sj.isNotBlank() && sr <= er && sr > 0
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    fun getPresignedUrlForTextbook() {
        if (!isValidInput.value) {
            _textbookState.value = TextbookState.Failure("교재 정보를 다시 확인해 주세요.")
            return
        }
        val textbook = Textbook(
            null,
            name.value,
            null,
            grade.value,
            subject.value,
            publishedYear.value.toInt(),
            publisher.value,
            startRange.value,
            endRange.value
        )
        viewModelScope.launch {
            repository.getPresignedUrlForTextbook(textbook)
                .onSuccess {
                    _textbookState.value = TextbookState.TextbookGetPresignedUrlSuccess(it)
                    postPdfToS3(it, textbook)
                }
                .onFailure {
                    _textbookState.value = TextbookState.Failure(it.message!!)
                }
        }
    }

    fun postPdfToS3(url: String, textbook: Textbook) {
        if (textbookFile.value == null) {
            _textbookState.value = TextbookState.Failure("교재 파일을 다시 확인해 주세요.")
            return
        }
        viewModelScope.launch {
            fileRepository.putFileToUrl(
                url,
                "application/pdf",
                textbookFile.value!!
            )
                .onSuccess {
                    _textbookState.value = TextbookState.PostTextbookFileSuccess(it)
                    notifyUploadCompleted(textbook)
                }
                .onFailure {
                    _textbookState.value = TextbookState.Failure(it.message!!)
                }
        }
    }

    fun notifyUploadCompleted(textbook: Textbook) {
        viewModelScope.launch {
            repository.postTextbookUploaded(textbook)
                .onSuccess {
                    _textbookState.value = TextbookState.UploadTextbookSuccess(it.toString())
                }
                .onFailure {
                    _textbookState.value = TextbookState.Failure(it.message!!)
                }
        }
    }

}