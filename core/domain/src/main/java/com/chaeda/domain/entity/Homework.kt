package com.chaeda.domain.entity

data class Homework(
    val title: String,
    val content: String,
    val photoUrls: List<String>,
    val range: String = "79p - 81p",
    val deadline: String = "2024-02-22T15:30:45",
    val isDone: Boolean = false
)
