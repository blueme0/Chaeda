package com.chaeda.data.service

import com.chaeda.data.model.request.auth.RequestLoginDto
import com.chaeda.data.model.request.auth.RequestSignUpDto
import com.chaeda.domain.entity.Member
import com.chaeda.domain.entity.TokenEntity
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemberService {
    @POST("/member/teacher/signUp")
    suspend fun signUpTeacher(
        @Body member: RequestSignUpDto
    )

    @POST("/member/student/signUp")
    suspend fun signUpStudent(
        @Body member: RequestSignUpDto
    )

    @POST("/member/logout")
    suspend fun logout(): ResponseBody

    @POST("/member/login")
    suspend fun login(
        @Body info: RequestLoginDto
    ): TokenEntity

    @GET("/member/student")
    suspend fun getMember() : Member
}