package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.response.statistics.toConceptDetail
import com.chaeda.data.service.StatisticsService
import com.chaeda.domain.entity.ChapterDetail
import com.chaeda.domain.entity.ConceptDetail
import com.chaeda.domain.entity.WrongCountWithConcept
import javax.inject.Inject

class RemoteStatisticsDataSource @Inject constructor(
    private val statisticsService: StatisticsService,
) {
    suspend fun getSolvedCountByDate(
        date: String
    ): Map<String, Int> {
        return statisticsService.getSolvedCountByDate(date)
    }

    suspend fun getSolvedCountByWeek(
        date: String
    ): Map<String, Int> {
        return statisticsService.getSolvedCountByWeek(date)
    }

    suspend fun getSolvedCountByMonth(
        date: String
    ): Map<String, Int> {
        return statisticsService.getSolvedCountByMonth(date)
    }

    suspend fun getWrongRateByWeek(
        date: String
    ): List<WrongCountWithConcept> {
        return statisticsService.getWrongRateByWeek(date)
    }

    suspend fun getWrongRateByMonth(
        date: String
    ): List<WrongCountWithConcept> {
        return statisticsService.getWrongRateByMonth(date)
    }

    suspend fun getAccumulatedStatisticsByType(
        subConcept: String
    ): ConceptDetail {
        return statisticsService.getAccumulatedStatisticsByType(subConcept).toConceptDetail()
    }

    suspend fun getMonthlyStatisticsByType(
        subConcept: String
    ): ConceptDetail {
        return statisticsService.getMonthlyStatisticsByType(subConcept).toConceptDetail()
    }

    suspend fun getWeeklyStatisticsByType(
        subConcept: String
    ): ConceptDetail {
        return statisticsService.getWeeklyStatisticsByType(subConcept).toConceptDetail()
    }

    suspend fun getChapterListBySubject(
        subject: String
    ): List<ChapterDetail> {
        return statisticsService.getChapterListBySubject(subject)
    }

    suspend fun getWrongCountByChapter(
        chapter: String
    ): List<ConceptDetail> {
        val list = mutableListOf<ConceptDetail>()
        statisticsService.getWrongCountByChapter(chapter).forEach {
            list.add(it.toConceptDetail())
        }
        return list
    }
}