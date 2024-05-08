package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteStatisticsDataSource
import com.chaeda.domain.repository.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val remoteStatisticsDataSource: RemoteStatisticsDataSource
) : StatisticsRepository {

}