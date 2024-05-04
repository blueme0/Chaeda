package com.chaeda.chaeda.presentation.homework.textbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.TextbookDTO
import com.chaeda.domain.repository.TextbookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextbookViewModel @Inject constructor(
    private val textbookRepository: TextbookRepository
) : ViewModel() {

    private var _textbookId = MutableStateFlow<Int>(0)
    val textbookId: StateFlow<Int> = _textbookId.asStateFlow()

    private var _textbookName = MutableStateFlow<String>("")
    val textbookName: StateFlow<String> = _textbookName.asStateFlow()

    fun updateTextbook(id: Int, name: String) {
        _textbookId.value = id
        _textbookName.value = name
    }

    private var _textbookState = MutableStateFlow<TextbookState>(TextbookState.Init)
    val textbookState: StateFlow<TextbookState> = _textbookState.asStateFlow()

    fun getTextbookList() {
        viewModelScope.launch {
            textbookRepository.getTextbooks()
                .onSuccess {
                    _textbookState.value = TextbookState.GetListSuccess(it)
                }
                .onFailure {
                    _textbookState.value = TextbookState.Failure(it.message!!)
                }
        }
    }
}

sealed interface TextbookState {
    object Init: TextbookState
    data class GetListSuccess(val list: List<TextbookDTO>): TextbookState
    data class Failure(val msg: String): TextbookState
}