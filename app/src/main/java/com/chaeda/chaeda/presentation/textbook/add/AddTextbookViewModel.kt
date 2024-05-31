package com.chaeda.chaeda.presentation.textbook.add

import androidx.lifecycle.ViewModel
import com.chaeda.chaeda.presentation.textbook.TextbookState
import com.chaeda.domain.repository.ImageRepository
import com.chaeda.domain.repository.TextbookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
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

    private val _publisher = MutableStateFlow<String>("")
    val publisher: StateFlow<String> = _publisher.asStateFlow()

    private val _publishedYear = MutableStateFlow<String>("")
    val publishedYear: StateFlow<String> = _publishedYear.asStateFlow()


    private val _grade = MutableStateFlow<String>("")
    val grade: StateFlow<String> = _grade.asStateFlow()

    fun updateGrade(gr: String) {
        _grade.value = gr
    }

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
    


}