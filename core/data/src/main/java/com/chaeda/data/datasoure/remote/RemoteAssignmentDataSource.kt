package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.assignment.RequestAssignmentDto
import com.chaeda.data.model.request.assignment.RequestAssignmentResultDto
import com.chaeda.data.service.AssignmentService
import com.chaeda.domain.entity.Assignment
import com.chaeda.domain.entity.ProblemsWithPage
import java.time.LocalDate
import javax.inject.Inject

class RemoteAssignmentDataSource @Inject constructor(
    private val assignmentService: AssignmentService,
) {
    suspend fun getAssignmentById(
        id: Long
    ): Assignment {
        return assignmentService.getAssignmentById(id)
    }

    suspend fun putAssignmentById(
        id: Long,
        request: RequestAssignmentDto
    ): Assignment {
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
    ): List<Assignment> {
        return assignmentService.getAssignmentsByDate(dateString)
    }

    suspend fun postAssignment(
        request: RequestAssignmentDto
    ): Assignment {
        return assignmentService.postAssignment(request)
    }

    suspend fun getProblemRangeWithPage(
        id: Long
    ): List<ProblemsWithPage> {
        return assignmentService.getProblemRangeWithPage(id)
    }

    suspend fun postAssignmentResult(
        id: Long,
        results: RequestAssignmentResultDto
    ): Boolean {
        return assignmentService.postAssignmentResult(id, results).isSuccessful
    }
}