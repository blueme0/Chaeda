package com.chaeda.chaeda.presentation.homework.result

import androidx.lifecycle.ViewModel
import com.chaeda.domain.repository.HomeworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val hwRepository: HomeworkRepository
) : ViewModel() {

}
