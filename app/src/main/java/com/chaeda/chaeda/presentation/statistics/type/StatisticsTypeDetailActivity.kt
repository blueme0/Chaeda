package com.chaeda.chaeda.presentation.statistics.type

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.stringExtra
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityStatisticsTypeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsTypeDetailActivity
    : BindingActivity<ActivityStatisticsTypeDetailBinding>(R.layout.activity_statistics_type_detail) {

    private var mode = MODE_ALL
    private val type by stringExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {
        with(binding) {
            tvConcept.text = type
            tvSubject.text = "미적분"
            tvChapter.text = "미분법"
            tvSolved.text = "30문제"
            tvWrong.text = "20문제"
            tvEasy.text = "2문제"
            tvNormal.text = "7문제"
            tvHard.text = "9문제"
        }
    }

    private fun initListener() {
        with(binding) {
            llCheckAll.setOnSingleClickListener {
                ivCheckAll.setImageResource(R.drawable.ic_radio_checked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_unchecked)
                mode = MODE_ALL
            }

            llCheckMonth.setOnSingleClickListener {
                ivCheckAll.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_checked)
                mode = MODE_MONTH
            }
        }
    }

    companion object {
        private const val MODE_ALL = "mode_all"
        private const val MODE_MONTH = "mode_month"
        fun getIntent(context: Context, type: String) = Intent(context, StatisticsTypeDetailActivity::class.java).apply {
            putExtra("type", type)
        }
    }
}