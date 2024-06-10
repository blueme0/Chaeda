package com.chaeda.chaeda.presentation.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentSettingBinding
import com.chaeda.chaeda.presentation.login.LoginActivity
import com.chaeda.domain.entity.Member
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment
    : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel by activityViewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        observe()
    }

    private fun initView() {
        viewModel.getMember()
    }

    private fun initListener() {
        with(binding) {
            tvLogout.setOnSingleClickListener {
                viewModel.logout()
            }
        }
    }

    private fun setProfileUi(member: Member) {
        with(binding) {
            tvHomeName.text = member.name
            tvHomeTeacher.text = member.email
            tvHomeAcademy.text = member.schoolName
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.settingState.collect { state ->
                when (state) {
                    is SettingState.GetMemberSuccess -> {
                        setProfileUi(state.member)
                    }
                    is SettingState.LogoutSuccess -> {
                        startActivity(LoginActivity.getIntent(requireContext()))
                        requireActivity().finish()
                    }
                    else -> { }
                }
            }
        }
    }
}