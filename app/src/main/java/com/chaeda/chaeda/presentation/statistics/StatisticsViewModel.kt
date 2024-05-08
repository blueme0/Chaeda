package com.chaeda.chaeda.presentation.statistics

import androidx.lifecycle.ViewModel
import com.chaeda.domain.repository.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

}