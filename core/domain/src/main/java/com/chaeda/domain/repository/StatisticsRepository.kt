package com.chaeda.domain.repository

import com.chaeda.domain.entity.ChapterDetail
import com.chaeda.domain.entity.ConceptDetail
import com.chaeda.domain.entity.WrongCountWithConcept

interface StatisticsRepository {
    suspend fun getSolvedCountByDate(date: String): Result<Map<String, Int>>
    suspend fun getSolvedCountByWeek(date: String): Result<Map<String, Int>>
    suspend fun getSolvedCountByMonth(date: String): Result<Map<String, Int>>
    suspend fun getWrongRateByWeek(date: String): Result<List<WrongCountWithConcept>>
    suspend fun getWrongRateByMonth(month: String): Result<List<WrongCountWithConcept>>
    suspend fun getAccumulatedStatisticsByType(subConcept: String): Result<ConceptDetail>
    suspend fun getMonthlyStatisticsByType(subConcept: String): Result<ConceptDetail>
    suspend fun getWeeklyStatisticsByType(subConcept: String): Result<ConceptDetail>
    suspend fun getChapterListBySubject(subject: String): Result<List<ChapterDetail>>
    suspend fun getWrongCountByChapter(chapter: String): Result<List<ConceptDetail>>
}