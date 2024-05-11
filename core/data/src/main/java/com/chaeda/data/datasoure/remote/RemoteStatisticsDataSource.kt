package com.chaeda.data.datasoure.remote

import com.chaeda.data.service.StatisticsService
import com.chaeda.domain.entity.ChapterDTO
import com.chaeda.domain.entity.ConceptDetailDTO
import com.chaeda.domain.entity.WrongCountWithConceptDTO
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
    ): List<WrongCountWithConceptDTO> {
        return statisticsService.getWrongRateByWeek(date)
    }

    suspend fun getWrongRateByMonth(
        date: String
    ): List<WrongCountWithConceptDTO> {
        return statisticsService.getWrongRateByMonth(date)
    }

    suspend fun getStatisticsByType(
        typeId: Int
    ): ConceptDetailDTO {
        return statisticsService.getStatisticsByType(typeId)
    }

    suspend fun getChapterListBySubject(
        subject: String
    ): List<ChapterDTO> {
        return statisticsService.getChapterListBySubject(subject)
    }

    suspend fun getWrongRateByChapter(
        chapterId: Int
    ): List<WrongCountWithConceptDTO> {
        return statisticsService.getWrongRateByChapter(chapterId)
    }
}