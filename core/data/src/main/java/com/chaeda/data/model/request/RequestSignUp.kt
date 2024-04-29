package com.chaeda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUp (
    val loginId: String,
    val password: String,
    val name: String,
    val email: String,
    val gender: String,
    val phoneNumber: String,
    val schoolName: String?,
    val grade: String?,
    val role: String
)

/**
 * {
 *   "loginId": "teacher@gmail.com",
 *   "password": "asdf1234!",
 *   "name": "홍길동",
 *   "email": "teacher@gmail.com",
 *   "gender": "MALE",
 *   "phoneNumber": "010-xxxx-xxxx",
 *   "role": "TEACHER"
 * }
 */