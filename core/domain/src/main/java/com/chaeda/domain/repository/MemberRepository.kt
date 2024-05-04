package com.chaeda.domain.repository

import com.chaeda.domain.entity.MemberEntity
import com.chaeda.domain.entity.TokenDTO

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
    suspend fun logout(): Any
    suspend fun login(loginId: String, password: String): Result<TokenDTO>
    suspend fun getMember(): Result<MemberEntity>

    fun setAutoLogin(userToken: String, refreshToken: String)
    fun disableAutoLogin()
    fun getAutoLogin(): Boolean
}