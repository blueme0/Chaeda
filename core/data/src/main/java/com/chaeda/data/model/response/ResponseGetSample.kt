package com.chaeda.data.model.response

import com.chaeda.domain.entity.SampleEntity
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetSample(
    val sample: String,
)

//MapperSample
fun ResponseGetSample.toData(): SampleEntity {
    return SampleEntity(sample = sample)
}