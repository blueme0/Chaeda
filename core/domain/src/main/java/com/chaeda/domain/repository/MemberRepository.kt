package com.chaeda.domain.repository

import com.chaeda.domain.entity.Member
import com.chaeda.domain.entity.TokenEntity

interface MemberRepository {
//    suspend fun getSample(): MutableList<Any>
    suspend fun signUp(
        loginId: String,
        password: String,
        name: String,
        email: String,
        gender: String,
        phoneNumber: String,
        schoolName: String?,
        grade: String?,
        role: String = "STUDENT"
    ): Result<Unit>
    suspend fun logout(): Result<Any>
    suspend fun login(loginId: String, password: String): Result<TokenEntity>
    suspend fun getMember(): Result<Member>

    fun setAutoLogin(userToken: String, refreshToken: String)
    fun disableAutoLogin()
    fun getAutoLogin(): Boolean
}