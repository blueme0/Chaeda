package com.chaeda.data.datasoure.remote

import com.chaeda.data.service.MemberService
import javax.inject.Inject

class RemoteMemberDataSource @Inject constructor(
    private val memberService: MemberService,
) {

}