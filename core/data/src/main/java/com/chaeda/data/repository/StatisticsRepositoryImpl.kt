package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteStatisticsDataSource
import com.chaeda.domain.entity.ChapterDTO
import com.chaeda.domain.entity.ConceptDetailDTO
import com.chaeda.domain.entity.WrongCountWithConceptDTO
import com.chaeda.domain.repository.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val remoteStatisticsDataSource: RemoteStatisticsDataSource
) : StatisticsRepository {
    override suspend fun getSolvedCountByDate(date: String): Result<Map<String, Int>> {
        return runCatching {
            remoteStatisticsDataSource.getSolvedCountByDate(date)
        }
    }

    override suspend fun getSolvedCountByWeek(date: String): Result<Map<String, Int>> {
        return runCatching {
            remoteStatisticsDataSource.getSolvedCountByWeek(date)
        }
    }

    override suspend fun getSolvedCountByMonth(date: String): Result<Map<String, Int>> {
        return runCatching {
            remoteStatisticsDataSource.getSolvedCountByMonth(date)
        }
    }

    override suspend fun getWrongRateByWeek(date: String): Result<List<WrongCountWithConceptDTO>> {
        return runCatching {
            remoteStatisticsDataSource.getWrongRateByWeek(date)
        }
    }

    override suspend fun getWrongRateByMonth(month: String): Result<List<WrongCountWithConceptDTO>> {
        return runCatching {
            remoteStatisticsDataSource.getWrongRateByMonth(month)
        }
    }

    override suspend fun getStatisticsByType(typeId: Int): Result<ConceptDetailDTO> {
        return runCatching {
            remoteStatisticsDataSource.getStatisticsByType(typeId)
        }
    }

    override suspend fun getChapterListBySubject(subject: String): Result<List<ChapterDTO>> {
        return runCatching {
            remoteStatisticsDataSource.getChapterListBySubject(subject)
        }
    }

    override suspend fun getWrongRateByChapter(chapterId: Int): Result<List<WrongCountWithConceptDTO>> {
        return runCatching {
            remoteStatisticsDataSource.getWrongRateByChapter(chapterId)
        }
    }
}