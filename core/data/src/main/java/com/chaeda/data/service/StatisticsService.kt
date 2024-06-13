package com.chaeda.data.service

import com.chaeda.data.model.response.statistics.ConceptStatisticsDTO
import com.chaeda.domain.entity.ChapterDetail
import com.chaeda.domain.entity.ConceptDetail
import com.chaeda.domain.entity.WrongCountWithConcept
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StatisticsService {
    @GET("/statistics/solvedCount/7days")
    suspend fun getSolvedCountByDate(
        @Query("date") date: String
    ): Map<String, Int>

    @GET("/statistics/solvedCount/8weeks")
    suspend fun getSolvedCountByWeek(
        @Query("date") date: String
    ): Map<String, Int>

    @GET("/statistics/solvedCount/6months")
    suspend fun getSolvedCountByMonth(
        @Query("date") date: String
    ): Map<String, Int>

    @GET("/statistics/wrongCount/weeklyTopSubconcepts")
    suspend fun getWrongRateByWeek(
        @Query("date") date: String
    ): List<WrongCountWithConcept>

    @GET("/statistics/wrongCount/monthlyTopSubconcepts")
    suspend fun getWrongRateByMonth(
        @Query("date") date: String
    ): List<WrongCountWithConcept>

    @GET("/statistics/statistics/accumulated/{subConcept}")
    suspend fun getAccumulatedStatisticsByType(
        @Path("subConcept") subConcept: String
    ): ConceptStatisticsDTO

    @GET("/statistics/statistics/monthly/{subConcept}")
    suspend fun getMonthlyStatisticsByType(
        @Path("subConcept") subConcept: String
    ): ConceptStatisticsDTO

    @GET("/statistics/statistics/weekly/{subConcept}")
    suspend fun getWeeklyStatisticsByType(
        @Path("subConcept") subConcept: String
    ): ConceptStatisticsDTO

    // not in use
    @GET("/statistics/chapter/list")
    suspend fun getChapterListBySubject(
        @Query("subject") subject: String
    ): List<ChapterDetail>

    @GET("/statistics/statistics/accumulated/{chapter}/list")
    suspend fun getWrongCountByChapter(
        @Path("chapter") chapter: String
    ): List<ConceptStatisticsDTO>
}