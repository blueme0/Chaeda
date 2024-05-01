package com.chaeda.domain.entity

data class ResultAnswer (
    var checked: Boolean = false,
    val index: Int,
    var level: Int = 0
)