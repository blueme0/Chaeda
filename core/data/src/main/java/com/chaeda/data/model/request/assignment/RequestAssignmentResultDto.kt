package com.chaeda.data.model.request.assignment

import com.chaeda.domain.entity.AssignmentResult
import kotlinx.serialization.Serializable

@Serializable
data class RequestAssignmentResultDto (
    val wrongProblemListPerPageRequests: List<AssignmentResult>
)