package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteAssignmentDataSource
import com.chaeda.data.model.request.assignment.RequestAssignmentDto
import com.chaeda.data.model.request.assignment.RequestAssignmentResultDto
import com.chaeda.domain.entity.Assignment
import com.chaeda.domain.entity.AssignmentResult
import com.chaeda.domain.entity.ProblemsWithPage
import com.chaeda.domain.repository.AssignmentRepository
import java.time.LocalDate
import javax.inject.Inject

class AssignmentRepositoryImpl @Inject constructor(
    private val remoteAssignmentDataSource: RemoteAssignmentDataSource
) : AssignmentRepository {
    override suspend fun getAssignmentById(id: Long): Result<Assignment> {
        return runCatching {
            remoteAssignmentDataSource.getAssignmentById(id)
        }
    }

    override suspend fun putAssignmentById(
        id: Long,
        assignment: Assignment,
        textbookId: Int
    ): Result<Assignment> {
        return runCatching {
            remoteAssignmentDataSource.putAssignmentById(
                id,
                RequestAssignmentDto(
                    assignment.title,
                    assignment.startPage,
                    assignment.endPage,
                    assignment.targetDate,
                    textbookId
                )
            )
        }
    }

    override suspend fun deleteAssignmentById(id: Long): Result<Unit> {
        return runCatching {
            remoteAssignmentDataSource.deleteAssignmentById(id)
        }
    }

    override suspend fun getAssignmentsByDate(
        dateString: String,
        date: LocalDate
    ): Result<List<Assignment>> {
        return runCatching {
            remoteAssignmentDataSource.getAssignmentsByDate(dateString, date)
        }
    }

    override suspend fun postAssignment(
        assignment: Assignment,
        textbookId: Int
    ): Result<Assignment> {
        return runCatching {
            remoteAssignmentDataSource.postAssignment(
                RequestAssignmentDto(
                    assignment.title,
                    assignment.startPage,
                    assignment.endPage,
                    assignment.targetDate,
                    textbookId
                )
            )
        }
    }

    override suspend fun getProblemRangeWithPage(assignmentId: Long): Result<List<ProblemsWithPage>> {
        return runCatching {
            remoteAssignmentDataSource.getProblemRangeWithPage(assignmentId)
        }
    }

    override suspend fun postAssignmentResult(
        assignmentId: Long,
        results: List<AssignmentResult>
    ): Result<Unit> {
        return runCatching {
            remoteAssignmentDataSource.postAssignmentResult(
                assignmentId,
                RequestAssignmentResultDto(results)
            )
        }
    }
}