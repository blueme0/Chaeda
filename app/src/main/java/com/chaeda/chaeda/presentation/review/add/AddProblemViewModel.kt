package com.chaeda.chaeda.presentation.review.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.enumSet.Concept
import com.chaeda.domain.enumSet.Subject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AddProblemViewModel @Inject constructor(
//    private val repository
) : ViewModel() {
    private val _dateString = MutableStateFlow<String>("")
    val dateString : StateFlow<String> = _dateString.asStateFlow()

    private val _imageUrl = MutableStateFlow<String>("")
    val imageUrl : StateFlow<String> = _imageUrl.asStateFlow()

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
        imageUrl,
        answer,
        concept
    ) { ds, iu, ans, cpt ->
        Pattern.matches(DATE_REGEX, ds) && iu.isNotBlank() && ans.isNotBlank() && cpt != null
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    companion object {
        private const val DATE_REGEX = "^(\\d{4})-(\\d{2})-(\\d{2})\$"
    }
}