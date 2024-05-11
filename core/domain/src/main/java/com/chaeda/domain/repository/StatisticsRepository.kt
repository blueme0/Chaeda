package com.chaeda.domain.repository

import com.chaeda.domain.entity.ChapterDTO
import com.chaeda.domain.entity.ConceptDetailDTO
import com.chaeda.domain.entity.WrongCountWithConceptDTO

interface StatisticsRepository {
    suspend fun getSolvedCountByDate(date: String): Result<Map<String, Int>>
    suspend fun getSolvedCountByWeek(date: String): Result<Map<String, Int>>
    suspend fun getSolvedCountByMonth(date: String): Result<Map<String, Int>>
    suspend fun getWrongRateByWeek(date: String): Result<List<WrongCountWithConceptDTO>>
    suspend fun getWrongRateByMonth(month: String): Result<List<WrongCountWithConceptDTO>>
    suspend fun getStatisticsByType(typeId: Int): Result<ConceptDetailDTO>
    suspend fun getChapterListBySubject(subject: String): Result<List<ChapterDTO>>
    suspend fun getWrongRateByChapter(chapterId: Int): Result<List<WrongCountWithConceptDTO>>
}