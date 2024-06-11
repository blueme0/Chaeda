package com.chaeda.domain.entity

data class ResultAnswer (
    var checked: Boolean = false,
    val index: String,
    var level: String = "미풀이"
) : java.io.Serializable