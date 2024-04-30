package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.RequestLogin
import com.chaeda.data.model.request.RequestSignUp
import com.chaeda.data.service.MemberService
import com.chaeda.domain.entity.MemberEntity
import com.chaeda.domain.entity.TokenDTO
import javax.inject.Inject

class RemoteMemberDataSource @Inject constructor(
    private val memberService: MemberService,
) {
    suspend fun signUp(
        member: RequestSignUp
    ): Any {
//        if (member.role == "TEACHER") return memberService.signUpTeacher(member)
        return memberService.signUpStudent(member)
    }

    suspend fun logout(): Any {
        return memberService.logout()
    }

    suspend fun login(
        info: RequestLogin
    ): TokenDTO {
        return memberService.login(info)
    }

    suspend fun getMember(): MemberEntity {
        return memberService.getMember()
    }
}