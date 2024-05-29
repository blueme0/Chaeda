package com.chaeda.domain.repository

import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.AssignmentResultDTO
import com.chaeda.domain.entity.ProblemsWithPageDTO
import java.time.LocalDate

interface AssignmentRepository {

    suspend fun getAssignmentById(id: Long): Result<AssignmentDTO>
    suspend fun putAssignmentById(id: Long, assignment: AssignmentDTO, textbookId: Int): Result<AssignmentDTO>
    suspend fun deleteAssignmentById(id: Long): Result<Unit>
    suspend fun getAssignmentsByDate(dateString: String, date: LocalDate): Result<List<AssignmentDTO>>
    suspend fun postAssignment(assignment: AssignmentDTO, textbookId: Int): Result<AssignmentDTO>

    suspend fun getProblemRangeWithPage(assignmentId: Long): Result<List<ProblemsWithPageDTO>>
    suspend fun postAssignmentResult(assignmentId: Long, results: List<AssignmentResultDTO>): Result<Unit>
}