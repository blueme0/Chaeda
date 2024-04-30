package com.chaeda.data.service

import com.chaeda.data.model.request.RequestLogin
import com.chaeda.data.model.request.RequestSignUp
import com.chaeda.domain.entity.MemberEntity
import com.chaeda.domain.entity.TokenDTO
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemberService {
    @POST("/member/teacher/signUp")
    suspend fun signUpTeacher(
        @Body member: RequestSignUp
    )

    @POST("/member/student/signUp")
    suspend fun signUpStudent(
        @Body member: RequestSignUp
    )

    @POST("/member/logout")
    suspend fun logout(): ResponseBody

    @POST("/member/login")
    suspend fun login(
        @Body info: RequestLogin
    ): TokenDTO

    @GET("/member/student")
    suspend fun getMember() : MemberEntity
}