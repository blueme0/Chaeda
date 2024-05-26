package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteReviewDataSource
import com.chaeda.data.model.request.RequestReviewProblemDTO
import com.chaeda.domain.entity.ReviewFolderDTO
import com.chaeda.domain.entity.ReviewPdfDTO
import com.chaeda.domain.entity.ReviewProblemDTO
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val remoteReviewDataSource: RemoteReviewDataSource
) : ReviewRepository {

    override suspend fun postProblemToBox(
        reviewProblem: ReviewProblemDTO,
        imageFileExtension: String,
        answer: String,
        textbookId: Int,
        problemNum: String,
        chapter: Chapter
    ): Result<Unit> {
        return runCatching {
            remoteReviewDataSource.postProblemToBox(
                RequestReviewProblemDTO(
                    reviewProblem.incorrectDate,
                    reviewProblem.imageKey,
                    imageFileExtension,
                    answer,
                    textbookId,
                    problemNum,
                    chapter.name
                )
            )
        }
    }

    override suspend fun postNewFolder(
        problemsIds: Set<Long>,
        reviewFolder: ReviewFolderDTO
    ): Result<Long> {
        return runCatching {
            remoteReviewDataSource.postNewFolder(problemsIds, reviewFolder)
        }
    }

    override suspend fun getProblemsFromBox(): Result<List<ReviewProblemDTO>> {
        return runCatching {
            remoteReviewDataSource.getProblemsFromBox()
        }
    }

    override suspend fun postMakeReviewPdf(folderId: Long): Result<Long> {
        return runCatching {
            remoteReviewDataSource.postMakeReviewPdf(folderId)
        }
    }

    override suspend fun getReviewPdf(pdfId: Long): Result<String> {
        return runCatching {
            remoteReviewDataSource.getReviewPdf(pdfId)
        }
    }

    override suspend fun getReviewPdfList(): Result<List<ReviewPdfDTO>> {
        return runCatching {
            remoteReviewDataSource.getReviewPdfList()
        }
    }

    override suspend fun getReviewFolderList(): Result<List<ReviewFolderDTO>> {
        return runCatching {
            remoteReviewDataSource.getReviewFolderList()
        }
    }

    override suspend fun getProblemsInFolder(folderId: Long): Result<List<ReviewProblemDTO>> {
        return runCatching {
            remoteReviewDataSource.getProblemsInFolder(folderId)
        }
    }
}