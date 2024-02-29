package com.chaeda.data.datasoure.remote

import com.chaeda.data.service.HomeworkService
import javax.inject.Inject

class RemoteHomeworkDataSource @Inject constructor(
    private val homeworkService: HomeworkService,
) {

}