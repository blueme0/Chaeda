package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteAssignmentDataSource
import com.chaeda.data.model.request.RequestAssignmentDTO
import com.chaeda.data.model.request.RequestAssignmentResultDTO
import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.AssignmentResultDTO
import com.chaeda.domain.entity.ProblemsWithPageDTO
import com.chaeda.domain.repository.AssignmentRepository
import java.time.LocalDate
import javax.inject.Inject

class AssignmentRepositoryImpl @Inject constructor(
    private val remoteAssignmentDataSource: RemoteAssignmentDataSource
) : AssignmentRepository {
    override suspend fun getAssignmentById(id: Long): Result<AssignmentDTO> {
        return runCatching {
            remoteAssignmentDataSource.getAssignmentById(id)
        }
    }

    override suspend fun putAssignmentById(
        id: Long,
        assignment: AssignmentDTO,
        textbookId: Int
    ): Result<AssignmentDTO> {
        return runCatching {
            remoteAssignmentDataSource.putAssignmentById(
                id,
                RequestAssignmentDTO(
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
    ): Result<List<AssignmentDTO>> {
        return runCatching {
            remoteAssignmentDataSource.getAssignmentsByDate(dateString, date)
        }
    }

    override suspend fun postAssignment(
        assignment: AssignmentDTO,
        textbookId: Int
    ): Result<AssignmentDTO> {
        return runCatching {
            remoteAssignmentDataSource.postAssignment(
                RequestAssignmentDTO(
                    assignment.title,
                    assignment.startPage,
                    assignment.endPage,
                    assignment.targetDate,
                    textbookId
                )
            )
        }
    }

    override suspend fun getProblemRangeWithPage(assignmentId: Long): Result<List<ProblemsWithPageDTO>> {
        return runCatching {
            remoteAssignmentDataSource.getProblemRangeWithPage(assignmentId)
        }
    }

    override suspend fun postAssignmentResult(
        assignmentId: Long,
        results: List<AssignmentResultDTO>
    ): Result<Unit> {
        return runCatching {
            remoteAssignmentDataSource.postAssignmentResult(
                assignmentId,
                RequestAssignmentResultDTO(results)
            )
        }
    }
}