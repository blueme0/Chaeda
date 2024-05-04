package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentResultDTO (
    val pageNumber: Int,
    val wrongProblemRecords: HashMap<String, String>
)