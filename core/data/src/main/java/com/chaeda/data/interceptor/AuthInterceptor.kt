package com.chaeda.data.interceptor

import android.util.Log
import com.chaeda.data.model.response.auth.ResponseAuthTokenDto
import com.chaeda.domain.ChaedaDataStore
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val json: Json,
    private val dataStore: ChaedaDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("chaeda-intercept", "interceptor called")
        val originalRequest = chain.request()
        val authRequest = if (dataStore.isLogin) {
            originalRequest.newAuthBuilder().build()
        } else {
            originalRequest
        }
        val response = chain.proceed(authRequest)
        Timber.tag("chaeda-intercept").d("response: ${response.code} ${response.body} ${response}")
        when (response.code) {
            CODE_TOKEN_EXPIRED -> {
                try {
                    Log.d("chaeda-intercept", "CODE_TOKEN_EXPIRED")
                    val refreshTokenRequest = originalRequest.newRefreshBuilder().build()
                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)
                    Log.d("chaeda-intercept", "refreshTokenRequest : ${refreshTokenRequest.toString()}")
                    Log.d("chaeda-intercept", "refreshTokenRequest : ${refreshTokenRequest.headers}")
                    Log.d("chaeda-intercept", "refreshTokenRequest : ${refreshTokenRequest.body}")
                    Log.d("chaeda-intercept", "refreshTokenRequest : ${refreshTokenRequest.header(
                        HEADER_AUTHORIZATION)}")
                    Log.d("chaeda-intercept", "refreshTokenRequest : ${refreshTokenRequest.header(
                        HEADER_REFRESH_TOKEN)}")
                    Log.d("chaeda-intercept", "refreshTokenResponse : ${refreshTokenResponse.toString()}")
                    Log.d("chaeda-intercept", "refreshTokenResponse : ${refreshTokenResponse.body}")
                    Log.d("chaeda-intercept", "refreshTokenResponse : ${refreshTokenResponse.headers}")
//                    val refreshTokenRequest = originalRequest.newBuilder().post("".toRequestBody())
//                        .url("${BASE_URL}/auth/reissues")
//                        .addHeader(HEADER_AUTHORIZATION, dataStore.userToken)
//                        .addHeader(HEADER_REFRESH_TOKEN, dataStore.refreshToken)
//                        .build()
//                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                    if (refreshTokenResponse.isSuccessful) {
//                        val responseToken = json.decodeFromString(
//                            refreshTokenResponse.body?.string().toString()
//                        ) as ResponseAuthToken

                        val responseToken = ResponseAuthTokenDto(
                            refreshTokenResponse.header(
                                HEADER_AUTHORIZATION, ""
                            )!!, refreshTokenResponse.header(
                                HEADER_REFRESH_TOKEN, ""
                            )!!
                        )
                        Timber.tag("teum-token").d("responseToken: $responseToken")
                        with(dataStore) {
                            userToken = "Bearer ${responseToken.accessToken}" ?: ""
                            refreshToken = "Bearer ${responseToken.refreshToken}" ?: ""
                        }
                        refreshTokenResponse.close()
                        val newRequest = originalRequest.newAuthBuilder().build()

                        return chain.proceed(newRequest)
                    } else {
//                        with(dataStore) {
//                            isLogin = false
//                            userToken = ""
//                            refreshToken = ""
//                        }
                    }
                } catch (t: Throwable) {
                    Timber.e(t)
                    with(dataStore) {
                        isLogin = false
                        userToken = ""
                        refreshToken = ""
                    }
                }
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(HEADER_AUTHORIZATION, dataStore.userToken)

    private fun Request.newRefreshBuilder() =
        this.newBuilder()
            .addHeader(HEADER_AUTHORIZATION, dataStore.userToken)
            .addHeader(HEADER_REFRESH_TOKEN, dataStore.refreshToken)

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_REFRESH_TOKEN = "RefreshToken"
    }
}