package com.chaeda.domain.repository

import com.chaeda.domain.entity.Assignment
import com.chaeda.domain.entity.AssignmentResult
import com.chaeda.domain.entity.ProblemsWithPage
import java.time.LocalDate

interface AssignmentRepository {

    suspend fun getAssignmentById(id: Long): Result<Assignment>
    suspend fun putAssignmentById(id: Long, assignment: Assignment, textbookId: Int): Result<Assignment>
    suspend fun deleteAssignmentById(id: Long): Result<Unit>
    suspend fun getAssignmentsByDate(dateString: String, date: LocalDate): Result<List<Assignment>>
    suspend fun postAssignment(assignment: Assignment, textbookId: Int): Result<Assignment>

    suspend fun getProblemRangeWithPage(assignmentId: Long): Result<List<ProblemsWithPage>>
    suspend fun postAssignmentResult(assignmentId: Long, results: List<AssignmentResult>): Result<Unit>
}