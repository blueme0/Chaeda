package com.chaeda.chaeda.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.ChaedaDataStore
import com.chaeda.domain.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val dataStore: ChaedaDataStore
) : ViewModel() {

    private val _loginId = MutableStateFlow<String>("")
    val loginId: StateFlow<String> = _loginId.asStateFlow()

    fun updateId(id: String) {
        _loginId.value = id
    }

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updatePw(pw: String) {
        _password.value = pw
    }

    private var _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Init)
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    fun postLogin() {
        viewModelScope.launch {
            memberRepository.login(
                loginId = loginId.value,
                password = password.value
            )
                .onSuccess {
                    dataStore.userToken = it.accessTokenDto
                    dataStore.refreshToken = it.refreshTokenDto
                    dataStore.isLogin = true
                    _loginState.value = LoginUiState.Success
                }
                .onFailure {
                    Log.d("chaeda-login", "${it.message}")
                    _loginState.value = LoginUiState.Failure("로그인 실패")
                }
        }
    }

    companion object {
    }
}

sealed interface LoginUiState {
    object Init: LoginUiState
    object Success: LoginUiState
    data class Failure(val msg: String): LoginUiState
}