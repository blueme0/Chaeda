package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentResult (
    val pageNumber: Int,
    val wrongProblemRecords: HashMap<String, String>
)