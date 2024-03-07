package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteMemberDataSource
import com.chaeda.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val remoteMemberDataSource: RemoteMemberDataSource
) : MemberRepository {

}