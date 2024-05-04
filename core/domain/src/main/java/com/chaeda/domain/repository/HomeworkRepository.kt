package com.chaeda.domain.repository

import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.AssignmentResultDTO
import com.chaeda.domain.entity.ProblemsWithPageDTO

interface HomeworkRepository {

    suspend fun getAssignmentById(id: Int): Result<AssignmentDTO>
    suspend fun putAssignmentById(id: Int, assignment: AssignmentDTO, textbookId: Int): Result<AssignmentDTO>
    suspend fun deleteAssignmentById(id: Int): Result<Unit>
    suspend fun getAssignmentsByDate(year: Int, month: Int, date: Int): Result<List<AssignmentDTO>>
    suspend fun postAssignment(assignment: AssignmentDTO, textbookId: Int): Result<AssignmentDTO>

    suspend fun getProblemRangeWithPage(assignmentId: Int): Result<List<ProblemsWithPageDTO>>
    suspend fun postAssignmentResult(assignmentId: Int, results: List<AssignmentResultDTO>): Result<Unit>
}