package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.response.ResponseGetSample
import com.chaeda.data.service.SampleService
import javax.inject.Inject

class RemoteSampleDataSource @Inject constructor(
    private val sampleService: SampleService
) {
    suspend fun getRecommendCourse(pageNo: String?): ResponseGetSample =
        sampleService.getSample()
}