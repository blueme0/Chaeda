package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteMemberDataSource
import com.chaeda.data.model.request.RequestLogin
import com.chaeda.data.model.request.RequestSignUp
import com.chaeda.domain.ChaedaDataStore
import com.chaeda.domain.entity.MemberEntity
import com.chaeda.domain.entity.TokenDTO
import com.chaeda.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val remoteMemberDataSource: RemoteMemberDataSource,
    private val dataStore: ChaedaDataStore
) : MemberRepository {
    override suspend fun signUp(
        loginId: String,
        password: String,
        name: String,
        email: String,
        gender: String,
        phoneNumber: String,
        schoolNumber: String?,
        grade: String?,
        role: String
    ): Result<Unit> {
        return runCatching {
            remoteMemberDataSource.signUp(RequestSignUp(
                loginId, password, name, email, gender, phoneNumber, schoolNumber, grade, "STUDENT"
            ))
        }
    }

    override suspend fun logout(): Any {
        return runCatching {
            remoteMemberDataSource.logout()
        }
    }

    override suspend fun login(loginId: String, password: String): Result<TokenDTO> {
        return runCatching {
            remoteMemberDataSource.login(RequestLogin(loginId, password))
        }
    }

    override suspend fun getMember(): Result<MemberEntity> {
        return runCatching {
            remoteMemberDataSource.getMember()
        }
    }
}