package com.chaeda.chaeda.presentation.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _loginId = MutableStateFlow<String>("")
    val loginId: StateFlow<String> = _loginId.asStateFlow()

    fun updateId(id: String) {
        _loginId.value = id
    }

    val idValid: StateFlow<Boolean> = combine(
        loginId
    ) { values ->
        val id = values[0] as String
        Pattern.matches(EMAIL_REGEX, id)
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updatePassword(pw: String) {
        _password.value = pw
    }

    private val _passwordCheck = MutableStateFlow<String>("")
    val passwordCheck: StateFlow<String> = _passwordCheck.asStateFlow()

    fun updatePasswordCheck(pw: String) {
        _passwordCheck.value = pw
    }

    val passwordValid: StateFlow<Boolean> = combine(
        password,
        passwordCheck
    ) { pw, check ->
        Pattern.matches(PW_REGEX, pw) && pw == check
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    fun updateUserName(name: String) {
        _userName.value = name
    }

    val nameValid: StateFlow<Boolean> = combine(
        userName
    ) { values ->
        val name = values[0] as String
        Pattern.matches(NAME_REGEX, name)
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _gender = MutableStateFlow<String>("")
    val gender: StateFlow<String> = _gender.asStateFlow()

    fun updateGender(gd: Int) {
        if (gd == 1) _gender.value = "MALE"
        else if (gd == 2) _gender.value = "FEMALE"
    }

    private val _phoneNumber = MutableStateFlow<String>("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    fun updatePhoneNumber(num: String) {
        _phoneNumber.value = num
    }

    val phoneValid: StateFlow<Boolean> = combine(
        phoneNumber
    ) { values ->
        val num = values[0] as String
        Pattern.matches(PHONE_REGEX, num)
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _schoolName = MutableStateFlow<String>("")
    val schoolName: StateFlow<String> = _schoolName.asStateFlow()

    fun updateSchoolName(name: String) {
        _schoolName.value = name
    }

    val schoolValid: StateFlow<Boolean> = combine(
        schoolName
    ) { values ->
        val school = values[0] as String
        Pattern.matches(SCHOOL_REGEX, school)
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _grade = MutableStateFlow<String>("")
    val grade: StateFlow<String> = _grade.asStateFlow()

    fun updateGrade(gr: String) {
        _grade.value = gr
    }

    val inputsValid1: StateFlow<Boolean> = combine(
        idValid,
        passwordValid,
        nameValid,
    ) { id, pw, name ->
        id && pw && name
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    val inputsValid2: StateFlow<Boolean> = combine(
        inputsValid1,
        gender,
        phoneValid,
        schoolValid,
        grade
    ) { valid, gd, phone, school, gr ->
        valid && (gd == "FEMALE" || gd == "MALE") && phone && school && gr.isNotEmpty()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private var _signUpState = MutableStateFlow<SignUpUiState>(SignUpUiState.Init)
    val signUpState: StateFlow<SignUpUiState> = _signUpState.asStateFlow()

    fun postSignUp() {
        viewModelScope.launch {
            memberRepository.signUp(
                loginId = loginId.value,
                password = password.value,
                name = userName.value,
                email = loginId.value,
                gender = gender.value,
                phoneNumber = phoneNumber.value,
                schoolName = schoolName.value,
                grade = grade.value,
                role = "STUDENT"
            )
                .onSuccess {
                    _signUpState.value = SignUpUiState.Success
                }
                .onFailure {
                    _signUpState.value = SignUpUiState.Failure("회원가입 실패")
                }
        }
    }

    fun findInvalid() {
        Log.d("chaeda-signup", "idValid: ${loginId.value} -> ${idValid.value}")
        Log.d("chaeda-signup", "passwordValid: ${password.value} & ${passwordCheck.value} -> ${passwordValid.value}")
        Log.d("chaeda-signup", "nameValid: ${userName.value} -> ${nameValid.value}")
        Log.d("chaeda-signup", "gender: ${gender.value}")
        Log.d("chaeda-signup", "phoneValid: ${phoneNumber.value} -> ${phoneValid.value}")
        Log.d("chaeda-signup", "schoolValid: ${schoolName.value} -> ${schoolValid.value}")
        Log.d("chaeda-signup", "grade: ${grade.value}")
    }

    companion object {
        private const val PW_REGEX = "^(?=.*[a-z])(?=.*\\d)(?!.*\\s).{8,20}\$"
        private const val EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        private const val PHONE_REGEX = "^010-\\d{4}-\\d{4}\$"
        private const val NAME_REGEX = "^[가-힣]{2,8}\$"
        private const val SCHOOL_REGEX = "^[a-zA-Z가-힣]{3,12}\$"
    }
}

sealed interface SignUpUiState {
    object Init: SignUpUiState
    object Success: SignUpUiState
    data class Failure(val msg: String): SignUpUiState
}