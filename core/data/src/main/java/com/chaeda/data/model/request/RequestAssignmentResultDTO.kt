package com.chaeda.data.model.request

import com.chaeda.domain.entity.AssignmentResultDTO
import kotlinx.serialization.Serializable

@Serializable
data class RequestAssignmentResultDTO (
    val wrongProblemListPerPageRequests: List<AssignmentResultDTO>
)