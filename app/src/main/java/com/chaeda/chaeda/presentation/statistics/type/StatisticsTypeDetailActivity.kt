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

    private val type by stringExtra()
    private val mode by stringExtra()
    private var cur_mode = MODE_ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {
        cur_mode = mode as String
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
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                cur_mode = MODE_ALL
            }

            llCheckMonth.setOnSingleClickListener {
                ivCheckAll.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_checked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                cur_mode = MODE_MONTH
            }

            llCheckWeek.setOnSingleClickListener {
                ivCheckAll.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_checked)
                cur_mode = MODE_WEEK
            }
        }
    }

    companion object {
        private const val MODE_ALL = "mode_all"
        private const val MODE_MONTH = "mode_month"
        private const val MODE_WEEK = "mode_week"
        fun getIntent(context: Context, type: String, mode: String) = Intent(context, StatisticsTypeDetailActivity::class.java).apply {
            putExtra("type", type)
            putExtra("mode", mode)
        }
    }
}