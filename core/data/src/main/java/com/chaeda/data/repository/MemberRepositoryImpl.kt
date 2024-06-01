package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteMemberDataSource
import com.chaeda.data.model.request.auth.RequestLoginDto
import com.chaeda.data.model.request.auth.RequestSignUpDto
import com.chaeda.domain.ChaedaDataStore
import com.chaeda.domain.entity.Member
import com.chaeda.domain.entity.TokenEntity
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
            remoteMemberDataSource.signUp(
                RequestSignUpDto(
                loginId, password, name, email, gender, phoneNumber, schoolNumber, grade, "STUDENT"
            )
            )
        }
    }

    override suspend fun logout(): Result<Any> {
        return runCatching {
            remoteMemberDataSource.logout()
        }
    }

    override suspend fun login(loginId: String, password: String): Result<TokenEntity> {
        return runCatching {
            remoteMemberDataSource.login(RequestLoginDto(loginId, password))
        }
    }

    override suspend fun getMember(): Result<Member> {
        return runCatching {
            remoteMemberDataSource.getMember()
        }
    }

    override fun getAutoLogin(): Boolean = dataStore.isLogin

    override fun setAutoLogin(userToken: String, refreshToken: String) {
        dataStore.isLogin = true
        dataStore.userToken = "Bearer $userToken"
        dataStore.refreshToken = "Bearer $refreshToken"
    }

    override fun disableAutoLogin() {
        dataStore.isLogin = false
        dataStore.userToken = ""
        dataStore.refreshToken = ""
    }
}