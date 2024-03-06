package com.chaeda.domain.repository

interface SampleRepository {
    suspend fun getSample(): MutableList<Any>
}