package com.chaeda.chaeda.presentation.homework.result

import androidx.lifecycle.ViewModel
import com.chaeda.domain.repository.HomeworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val hwRepository: HomeworkRepository
) : ViewModel() {

    // HashMap으로 페이지 별 문항들 저장하기
}
