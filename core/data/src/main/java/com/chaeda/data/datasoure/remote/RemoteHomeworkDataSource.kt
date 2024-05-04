package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.RequestAssignmentDTO
import com.chaeda.data.model.request.RequestAssignmentResultDTO
import com.chaeda.data.service.HomeworkService
import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.ProblemsWithPageDTO
import java.time.LocalDate
import javax.inject.Inject

class RemoteHomeworkDataSource @Inject constructor(
    private val homeworkService: HomeworkService,
) {
    suspend fun getAssignmentById(
        id: Int
    ): AssignmentDTO {
        return homeworkService.getAssignmentById(id)
    }

    suspend fun putAssignmentById(
        id: Int,
        request: RequestAssignmentDTO
    ): AssignmentDTO {
        return homeworkService.putAssignmentById(id, request)
    }

    suspend fun deleteAssignmentById(
        id: Int
    ): Boolean {
        return homeworkService.deleteAssignmentById(id).isSuccessful
    }

    suspend fun getAssignmentsByDate(
        dateString: String,
        date: LocalDate
    ): List<AssignmentDTO> {
        return homeworkService.getAssignmentsByDate(dateString)
    }

    suspend fun postAssignment(
        request: RequestAssignmentDTO
    ): AssignmentDTO {
        return homeworkService.postAssignment(request)
    }

    suspend fun getProblemRangeWithPage(
        id: Int
    ): List<ProblemsWithPageDTO> {
        return homeworkService.getProblemRangeWithPage(id)
    }

    suspend fun postAssignmentResult(
        id: Int,
        results: RequestAssignmentResultDTO
    ): Boolean {
        return homeworkService.postAssignmentResult(id, results).isSuccessful
    }
}