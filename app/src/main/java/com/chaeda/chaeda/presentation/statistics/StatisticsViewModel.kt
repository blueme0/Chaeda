package com.chaeda.chaeda.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.ChapterDetail
import com.chaeda.domain.entity.ConceptDetail
import com.chaeda.domain.entity.WrongCountWithConcept
import com.chaeda.domain.repository.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<StatisticsScreenState>(StatisticsScreenState.Main)
    val screenState: StateFlow<StatisticsScreenState> = _screenState.asStateFlow()

    private val _statisticsState = MutableStateFlow<StatisticsState>(StatisticsState.Init)
    val statisticsState: StateFlow<StatisticsState> = _statisticsState.asStateFlow()

    fun getSolvedCountByDate(date: String) {
        viewModelScope.launch {
            statisticsRepository.getSolvedCountByDate(date)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetCountByDateSuccess(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getSolvedCountByWeek(date: String) {
        viewModelScope.launch {
            statisticsRepository.getSolvedCountByWeek(date)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetCountByWeekSuccess(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getSolvedCountByMonth(date: String) {
        viewModelScope.launch {
            statisticsRepository.getSolvedCountByMonth(date)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetCountByMonthSuccess(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getWrongRateByWeek(date: String) {
        viewModelScope.launch {
            statisticsRepository.getWrongRateByWeek(date)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetWrongByWeekSuccess(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getWrongRateByMonth(date: String) {
        viewModelScope.launch {
            statisticsRepository.getWrongRateByMonth("${date}-01")
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetWrongByMonthSuccess(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getAccumulatedStatisticsByType(subConcept: String) {
        viewModelScope.launch {
            statisticsRepository.getAccumulatedStatisticsByType(subConcept)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetAccumulatedStatisticsDetail(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getMonthlyStatisticsByType(subConcept: String) {
        viewModelScope.launch {
            statisticsRepository.getMonthlyStatisticsByType(subConcept)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetMontlyStatisticsDetail(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getWeeklyStatisticsByType(subConcept: String) {
        viewModelScope.launch {
            statisticsRepository.getWeeklyStatisticsByType(subConcept)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetWeeklyStatisticsDetail(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getChapterListBySubject(subject: String) {
        viewModelScope.launch {
            statisticsRepository.getChapterListBySubject(subject)
                .onSuccess {
                    _statisticsState.value = StatisticsState.GetChapterListSuccess(it)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

    fun getWrongCountByChapter(chapter: String) {
        viewModelScope.launch {
            statisticsRepository.getWrongCountByChapter(chapter)
                .onSuccess {
                    val map = mutableMapOf<String, ConceptDetail>()
                    val list = it
                    for (i in list) {
                        map[i.subConcept] = i
                    }
                    _statisticsState.value = StatisticsState.GetWrongByChapterSuccess(map)
                }
                .onFailure {
                    _statisticsState.value = StatisticsState.Failure(it.message!!)
                }
        }
    }

}

enum class StatisticsScreenState {
    Main, Count, Wrong, Chapter
}

sealed interface StatisticsState {
    object Init: StatisticsState
    data class GetCountByDateSuccess(val map: Map<String, Int>): StatisticsState
    data class GetCountByWeekSuccess(val map: Map<String, Int>): StatisticsState
    data class GetCountByMonthSuccess(val map: Map<String, Int>): StatisticsState
    data class GetWrongByWeekSuccess(val list: List<WrongCountWithConcept>): StatisticsState
    data class GetWrongByMonthSuccess(val list: List<WrongCountWithConcept>): StatisticsState
    data class GetAccumulatedStatisticsDetail(val concept: ConceptDetail): StatisticsState
    data class GetMontlyStatisticsDetail(val concept: ConceptDetail): StatisticsState
    data class GetWeeklyStatisticsDetail(val concept: ConceptDetail): StatisticsState
    data class GetChapterListSuccess(val list: List<ChapterDetail>): StatisticsState
    data class GetWrongByChapterSuccess(val map: Map<String, ConceptDetail>): StatisticsState
    data class Failure(val msg: String): StatisticsState
}