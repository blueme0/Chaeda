package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.auth.RequestLoginDto
import com.chaeda.data.model.request.auth.RequestSignUpDto
import com.chaeda.data.service.MemberService
import com.chaeda.domain.entity.Member
import com.chaeda.domain.entity.TokenEntity
import javax.inject.Inject

class RemoteMemberDataSource @Inject constructor(
    private val memberService: MemberService,
) {
    suspend fun signUp(
        member: RequestSignUpDto
    ): Any {
//        if (member.role == "TEACHER") return memberService.signUpTeacher(member)
        return memberService.signUpStudent(member)
    }

    suspend fun logout(): Any {
        return memberService.logout()
    }

    suspend fun login(
        info: RequestLoginDto
    ): TokenEntity {
        return memberService.login(info)
    }

    suspend fun getMember(): Member {
        return memberService.getMember()
    }
}