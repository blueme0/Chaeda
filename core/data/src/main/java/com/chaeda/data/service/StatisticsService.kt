package com.chaeda.data.service

import com.chaeda.domain.entity.ChapterDTO
import com.chaeda.domain.entity.ConceptDetailDTO
import com.chaeda.domain.entity.WrongCountWithConceptDTO
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
    ): List<WrongCountWithConceptDTO>

    @GET("/statistics/wrongCount/monthlyTopSubconcepts")
    suspend fun getWrongRateByMonth(
        @Query("date") date: String
    ): List<WrongCountWithConceptDTO>

    @GET("/statistics/statistics/accumulated/{subConcept}")
    suspend fun getAccumulatedStatisticsByType(
        @Path("subConcept") subConcept: String
    ): ConceptDetailDTO

    @GET("/statistics/statistics/monthly/{subConcept}")
    suspend fun getMonthlyStatisticsByType(
        @Path("subConcept") subConcept: String
    ): ConceptDetailDTO

    @GET("/statistics/statistics/weekly/{subConcept}")
    suspend fun getWeeklyStatisticsByType(
        @Path("subConcept") subConcept: String
    ): ConceptDetailDTO

    // not in use
    @GET("/statistics/chapter/list")
    suspend fun getChapterListBySubject(
        @Query("subject") subject: String
    ): List<ChapterDTO>

    @GET("/statistics/statistics/accumulated/{chapter}")
    suspend fun getWrongCountByChapter(
        @Path("chapter") chapter: String
    ): List<ConceptDetailDTO>
}