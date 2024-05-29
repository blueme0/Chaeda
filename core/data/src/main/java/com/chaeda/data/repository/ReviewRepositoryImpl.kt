package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteReviewDataSource
import com.chaeda.data.model.request.review.RequestReviewProblemDto
import com.chaeda.domain.entity.ReviewFolder
import com.chaeda.domain.entity.ReviewPdf
import com.chaeda.domain.entity.ReviewProblem
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val remoteReviewDataSource: RemoteReviewDataSource
) : ReviewRepository {

    override suspend fun postProblemToBox(
        reviewProblem: ReviewProblem,
        fileExtension: String,
        answer: String,
        textbookName: String,
        problemNum: String,
        chapter: Chapter
    ): Result<Unit> {
        return runCatching {
            remoteReviewDataSource.postProblemToBox(
                RequestReviewProblemDto(
                    reviewProblem.incorrectDate,
                    reviewProblem.imageKey,
                    fileExtension,
                    answer,
                    textbookName,
                    problemNum,
                    chapter.name
                )
            )
        }
    }

    override suspend fun postNewFolder(
        problemsIds: List<Long>,
        reviewFolder: ReviewFolder
    ): Result<Long> {
        return runCatching {
            remoteReviewDataSource.postNewFolder(problemsIds, reviewFolder)
        }
    }

    override suspend fun getProblemsFromBox(): Result<List<ReviewProblem>> {
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

    override suspend fun getReviewPdfList(): Result<List<ReviewPdf>> {
        return runCatching {
            remoteReviewDataSource.getReviewPdfList()
        }
    }

    override suspend fun getReviewFolderList(): Result<List<ReviewFolder>> {
        return runCatching {
            remoteReviewDataSource.getReviewFolderList()
        }
    }

    override suspend fun getProblemsInFolder(folderId: Long): Result<List<ReviewProblem>> {
        return runCatching {
            remoteReviewDataSource.getProblemsInFolder(folderId)
        }
    }
}