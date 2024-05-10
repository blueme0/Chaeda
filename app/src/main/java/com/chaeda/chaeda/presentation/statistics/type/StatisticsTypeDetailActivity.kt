package com.chaeda.chaeda.presentation.statistics.type

import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityStatisticsTypeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsTypeDetailActivity
    : BindingActivity<ActivityStatisticsTypeDetailBinding>(R.layout.activity_statistics_type_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {

    }

    private fun initListener() {

    }
}