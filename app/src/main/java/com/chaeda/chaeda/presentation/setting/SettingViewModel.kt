package com.chaeda.chaeda.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.MemberEntity
import com.chaeda.domain.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: MemberRepository
) : ViewModel() {

    private val _settingState = MutableStateFlow<SettingState>(SettingState.Init)
    val settingState : StateFlow<SettingState> = _settingState.asStateFlow()

    fun getMember() {
        viewModelScope.launch {
            repository.getMember()
                .onSuccess {
                    _settingState.value = SettingState.GetMemberSuccess(it)
                }
                .onFailure {
                    _settingState.value = SettingState.Failure(it.message!!)
                }
        }
    }
}

sealed interface SettingState {
    object Init: SettingState
    data class GetMemberSuccess(val member: MemberEntity): SettingState
    data class Failure(val msg: String): SettingState
}