package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.RequestAssignmentDTO
import com.chaeda.data.model.request.RequestAssignmentResultDTO
import com.chaeda.data.service.AssignmentService
import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.ProblemsWithPageDTO
import java.time.LocalDate
import javax.inject.Inject

class RemoteAssignmentDataSource @Inject constructor(
    private val assignmentService: AssignmentService,
) {
    suspend fun getAssignmentById(
        id: Long
    ): AssignmentDTO {
        return assignmentService.getAssignmentById(id)
    }

    suspend fun putAssignmentById(
        id: Long,
        request: RequestAssignmentDTO
    ): AssignmentDTO {
        return assignmentService.putAssignmentById(id, request)
    }

    suspend fun deleteAssignmentById(
        id: Long
    ): Boolean {
        return assignmentService.deleteAssignmentById(id).isSuccessful
    }

    suspend fun getAssignmentsByDate(
        dateString: String,
        date: LocalDate
    ): List<AssignmentDTO> {
        return assignmentService.getAssignmentsByDate(dateString)
    }

    suspend fun postAssignment(
        request: RequestAssignmentDTO
    ): AssignmentDTO {
        return assignmentService.postAssignment(request)
    }

    suspend fun getProblemRangeWithPage(
        id: Long
    ): List<ProblemsWithPageDTO> {
        return assignmentService.getProblemRangeWithPage(id)
    }

    suspend fun postAssignmentResult(
        id: Long,
        results: RequestAssignmentResultDTO
    ): Boolean {
        return assignmentService.postAssignmentResult(id, results).isSuccessful
    }
}