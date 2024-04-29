package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class MemberEntity (
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val phoneNumber: String,
    val address: String?,
    val profileUrl: String?,
    val schoolName: String?,
    val grade: String?,
    val role: String,
    val parentPhoneNum: String?,
    val homePhoneNum: String?,
    val subject: String?,
    val notes: String?,
    val imageId: Long?
)