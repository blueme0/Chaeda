package com.chaeda.chaeda.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val memberRepository: MemberRepository
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
        if (loginId.value.isEmpty() && memberRepository.getAutoLogin()) {
            _loginState.value = LoginUiState.Success
            return
        }
        viewModelScope.launch {
            memberRepository.login(
                loginId = loginId.value,
                password = password.value
            )
                .onSuccess {
                    memberRepository.setAutoLogin(it.accessTokenDto, it.refreshTokenDto)
                    _loginState.value = LoginUiState.Success
                }
                .onFailure {
                    Log.d("chaeda-login", "${it.message}")
                    _loginState.value = LoginUiState.Failure(it.message!!)
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

/**
 *
 * 이미 예~전부터 쓰던 아이디
 * blueme0@konkuk.ac.kr
 *
 * 평소에는 잘 돼
 *
 * 근데 배포를 새로 한 것 같을 때
 * : 가입되지 않은 아이디입니다 401
 *
 * 그래서 회원가입을 저 아이디로 다시 해
 * : 이미 가입된 이메일입니다 400
 *
 * 다시 뒤로 가서 그 아이디로 로그인을 해
 * : success......
 */