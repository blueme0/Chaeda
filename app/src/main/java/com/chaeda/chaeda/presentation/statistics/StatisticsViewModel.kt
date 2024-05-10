package com.chaeda.chaeda.presentation.statistics

import androidx.lifecycle.ViewModel
import com.chaeda.domain.repository.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<StatisticsState>(StatisticsState.Main)
    val screenState: StateFlow<StatisticsState> = _screenState.asStateFlow()

}

enum class StatisticsState {
    Main, Count, Wrong, Chapter
}