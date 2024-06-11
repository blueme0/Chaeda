package com.chaeda.data.model.response.statistics

import com.chaeda.domain.entity.ConceptDetail
import kotlinx.serialization.Serializable

@Serializable
data class ConceptStatisticsDTO (
    val subConcept: String,
    val problemCount: Int,
    val wrongCount: Int,
    val easyNum: Int,
    val middleNum: Int,
    val hardNum: Int
)

fun ConceptStatisticsDTO.toConceptDetail(): ConceptDetail {
    return ConceptDetail(null, null, subConcept, problemCount, wrongCount, easyNum, middleNum, hardNum)
}