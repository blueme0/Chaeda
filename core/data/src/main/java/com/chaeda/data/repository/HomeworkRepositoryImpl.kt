package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteHomeworkDataSource
import com.chaeda.data.model.request.RequestAssignmentDTO
import com.chaeda.data.model.request.RequestAssignmentResultDTO
import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.AssignmentResultDTO
import com.chaeda.domain.entity.ProblemsWithPageDTO
import com.chaeda.domain.repository.HomeworkRepository
import javax.inject.Inject

class HomeworkRepositoryImpl @Inject constructor(
    private val remoteHomeworkDataSource: RemoteHomeworkDataSource
) : HomeworkRepository {
    override suspend fun getAssignmentById(id: Int): Result<AssignmentDTO> {
        return runCatching {
            remoteHomeworkDataSource.getAssignmentById(id)
        }
    }

    override suspend fun putAssignmentById(
        id: Int,
        assignment: AssignmentDTO,
        textbookId: Int
    ): Result<AssignmentDTO> {
        return runCatching {
            remoteHomeworkDataSource.putAssignmentById(
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

    override suspend fun deleteAssignmentById(id: Int): Result<Unit> {
        return runCatching {
            remoteHomeworkDataSource.deleteAssignmentById(id)
        }
    }

    override suspend fun getAssignmentsByDate(
        year: Int,
        month: Int,
        date: Int
    ): Result<List<AssignmentDTO>> {
        return runCatching {
            remoteHomeworkDataSource.getAssignmentsByDate("$year-$month-$date")
        }
    }

    override suspend fun postAssignment(
        assignment: AssignmentDTO,
        textbookId: Int
    ): Result<AssignmentDTO> {
        return runCatching {
            remoteHomeworkDataSource.postAssignment(
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

    override suspend fun getProblemRangeWithPage(assignmentId: Int): Result<List<ProblemsWithPageDTO>> {
        return runCatching {
            remoteHomeworkDataSource.getProblemRangeWithPage(assignmentId)
        }
    }

    override suspend fun postAssignmentResult(
        assignmentId: Int,
        results: List<AssignmentResultDTO>
    ): Result<Unit> {
        return runCatching {
            remoteHomeworkDataSource.postAssignmentResult(
                assignmentId,
                RequestAssignmentResultDTO(results)
            )
        }
    }
}