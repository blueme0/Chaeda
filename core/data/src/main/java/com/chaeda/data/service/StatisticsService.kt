package com.chaeda.data.service

import com.chaeda.domain.entity.ChapterDTO
import com.chaeda.domain.entity.ConceptDetailDTO
import com.chaeda.domain.entity.WrongCountWithConceptDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticsService {
    @GET("/")
    suspend fun getSolvedCountByDate(
        @Query("date") date: String
    ): Map<String, Int>

    @GET("/")
    suspend fun getSolvedCountByWeek(
        @Query("date") date: String
    ): Map<String, Int>

    @GET("/")
    suspend fun getSolvedCountByMonth(
        @Query("date") date: String
    ): Map<String, Int>

    @GET("/")
    suspend fun getWrongRateByWeek(
        @Query("date") date: String
    ): List<WrongCountWithConceptDTO>

    @GET("/")
    suspend fun getWrongRateByMonth(
        @Query("date") date: String
    ): List<WrongCountWithConceptDTO>

    @GET("/")
    suspend fun getStatisticsByType(
        @Query("typeId") typeId: Int
    ): ConceptDetailDTO

    @GET("/")
    suspend fun getChapterListBySubject(
        @Query("subject") subject: String
    ): List<ChapterDTO>

    @GET("/")
    suspend fun getWrongRateByChapter(
        @Query("chapterId") chapterId: Int
    ): List<WrongCountWithConceptDTO>
}