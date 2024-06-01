package com.chaeda.chaeda.presentation.textbook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.Textbook
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

    private var _textbookList = MutableStateFlow<List<Textbook>>(listOf())
    val textbookList: StateFlow<List<Textbook>> = _textbookList.asStateFlow()

    fun getTextbookList() {
        viewModelScope.launch {
            textbookRepository.getTextbooks()
                .onSuccess {
                    _textbookList.value = it
                    Log.d("chaeda-tb", "getTextbookList Success")
                    _textbookState.value = TextbookState.GetListSuccess(it)
                }
                .onFailure {
                    Log.d("chaeda-tb", "getTextbookList Failure ${it.message}")
                    _textbookState.value = TextbookState.Failure(it.message!!)
                }
        }
    }

    fun updateSearch(str: String) {
        Log.d("chaeda-tb", "updateSearch called with str: $str")

        val list = mutableListOf<Textbook>()
        for (tb in _textbookList.value) {
            if (tb.name.lowercase().contains(str.lowercase())) list.add(tb)
        }
        Log.d("chaeda-tb", "list: ${list}")

        _textbookState.value = TextbookState.TextbookFilterSuccess(list)
    }
}

sealed interface TextbookState {
    object Init: TextbookState
    data class GetListSuccess(val list: List<Textbook>): TextbookState
    data class TextbookFilterSuccess(val list: List<Textbook>): TextbookState
    data class TextbookGetPresignedUrlSuccess(val url: String): TextbookState
    data class PostTextbookFileSuccess(val result: String): TextbookState // success for uploading pdf file to s3
    data class UploadTextbookSuccess(val result: String): TextbookState // success for posting textbook to spring
    data class Failure(val msg: String): TextbookState
}