package com.chaeda.chaeda.presentation.setting

import android.os.Bundle
import android.view.View
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentSettingBinding
import com.chaeda.domain.entity.Academy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment
    : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private lateinit var academyAdapter : AcademyListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initAcademyList()
    }

    private fun initView() {
        academyAdapter = AcademyListAdapter {
            // click listener
        }

        binding.rvAcademy.adapter = academyAdapter
    }

    private fun initListener() {
        with(binding) {
        }
    }

    private fun initAcademyList() {
        academyAdapter.setItems(listOf(
            Academy("일등 수학학원", "모의고사 대비반", "홍길동"),
            Academy("최고 국어학원", "내신 준비반", "김길동")
        ))
    }
}