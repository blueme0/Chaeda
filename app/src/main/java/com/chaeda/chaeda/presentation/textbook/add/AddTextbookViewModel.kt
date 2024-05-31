package com.chaeda.chaeda.presentation.textbook.add

import androidx.lifecycle.ViewModel
import com.chaeda.chaeda.presentation.textbook.TextbookState
import com.chaeda.domain.repository.TextbookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddTextbookViewModel @Inject constructor(
    private val repository: TextbookRepository
) : ViewModel() {

    private val _textbookState = MutableStateFlow<TextbookState>(TextbookState.Init)
    val textbookState: StateFlow<TextbookState> = _textbookState.asStateFlow()

    


}