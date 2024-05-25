package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteReviewDataSource
import com.chaeda.data.model.request.RequestReviewProblemDTO
import com.chaeda.domain.entity.ReviewFolderDTO
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

    override suspend fun postNewFolder(reviewFolder: ReviewFolderDTO): Result<Long> {
        return runCatching {
            remoteReviewDataSource.postNewFolder(reviewFolder)
        }
    }

    override suspend fun getProblemsFromBox(): Result<List<ReviewProblemDTO>> {
        return runCatching {
            remoteReviewDataSource.getProblemsFromBox()
        }
    }
}