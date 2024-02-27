package com.chaeda.chaeda.presentation.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivitySettingBinding
import com.chaeda.domain.entity.Academy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity
    : BindingActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private lateinit var academyAdapter : AcademyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            llBack.setOnSingleClickListener { finish() }
        }
    }

    private fun initAcademyList() {
        academyAdapter.setItems(listOf(
            Academy("일등 수학학원", "모의고사 대비반", "홍길동"),
            Academy("최고 국어학원", "내신 준비반", "김길동")
        ))
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SettingActivity::class.java)
    }
}