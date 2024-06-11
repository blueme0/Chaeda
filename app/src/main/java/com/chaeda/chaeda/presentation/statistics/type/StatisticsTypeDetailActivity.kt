package com.chaeda.chaeda.presentation.statistics.type

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.stringExtra
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityStatisticsTypeDetailBinding
import com.chaeda.chaeda.presentation.statistics.StatisticsState
import com.chaeda.chaeda.presentation.statistics.StatisticsViewModel
import com.chaeda.domain.entity.ConceptDetail
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.enumSet.Concept
import com.chaeda.domain.enumSet.Subject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class StatisticsTypeDetailActivity
    : BindingActivity<ActivityStatisticsTypeDetailBinding>(R.layout.activity_statistics_type_detail) {

    private val viewModel by viewModels<StatisticsViewModel>()

    private val type by stringExtra()
    private val mode by stringExtra()
    private var cur_mode = MODE_ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
        observe()
    }

    private fun initView() {
        cur_mode = mode as String
        with(binding) {
            val conceptEnum = Concept.valueOf(type!!)
            val chapterEnum = Chapter.valueOf(conceptEnum.chapter)
            val subjectEnum = Subject.valueOf(chapterEnum.subject)
            tvConcept.text = conceptEnum.koreanName
            tvChapter.text = chapterEnum.koreanName
            tvSubject.text = subjectEnum.koreanName
        }
        requestStatistics()
    }

    private fun updateView(concept: ConceptDetail) {
        with(binding) {
            tvSolved.text = "${concept.problemCount}문제"
            tvWrong.text = "${concept.wrongCount}문제"
            tvEasy.text = "${concept.easyNum}문제"
            tvNormal.text = "${concept.middleNum}문제"
            tvHard.text = "${concept.hardNum}문제"
        }
    }

    private fun requestStatistics() {
        when (cur_mode) {
            MODE_ALL -> {
                viewModel.getAccumulatedStatisticsByType(type!!)
            }
            MODE_MONTH -> {
                viewModel.getMonthlyStatisticsByType(type!!)
            }
            MODE_WEEK -> {
                viewModel.getWeeklyStatisticsByType(type!!)
            }
        }
    }


    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener {
                finish()
            }

            llCheckAll.setOnSingleClickListener {
                ivCheckAll.setImageResource(R.drawable.ic_radio_checked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                cur_mode = MODE_ALL
                requestStatistics()
            }

            llCheckMonth.setOnSingleClickListener {
                ivCheckAll.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_checked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                cur_mode = MODE_MONTH
                requestStatistics()
            }

            llCheckWeek.setOnSingleClickListener {
                ivCheckAll.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_checked)
                cur_mode = MODE_WEEK
                requestStatistics()
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.statisticsState.collect { state ->
                when (state) {
                    is StatisticsState.GetWeeklyStatisticsDetail -> {
                        Timber.tag("chaeda-detail").d("weekly success - ${state.concept}")
                        updateView(state.concept)
                    }
                    is StatisticsState.GetMontlyStatisticsDetail -> {
                        Timber.tag("chaeda-detail").d("monthly success - ${state.concept}")
                        updateView(state.concept)
                    }
                    is StatisticsState.GetAccumulatedStatisticsDetail -> {
                        Timber.tag("chaeda-detail").d("accumulated success - ${state.concept}")
                        updateView(state.concept)
                    }
                    is StatisticsState.Failure -> {
                        Timber.tag("chaeda-detail").d("failure - ${state.msg}")
                    }
                    else -> {

                    }
                }
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