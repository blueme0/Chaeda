package com.chaeda.chaeda.presentation.statistics.wrong

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentStatisticsWrongBinding
import com.chaeda.chaeda.presentation.statistics.StatisticsFragment
import com.chaeda.chaeda.presentation.statistics.dialog.DateSelectDialog
import com.chaeda.chaeda.presentation.statistics.dialog.DateSelectDialogInterface
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class StatisticsWrongFragment
    : BindingFragment<FragmentStatisticsWrongBinding>(R.layout.fragment_statistics_wrong),
    DateSelectDialogInterface {

    private var date = LocalDate.now()
    private var mode = MODE_WEEK

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFD571")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun initView() {
        with(binding) {
            ivBack.setOnSingleClickListener {
                navigateTo<StatisticsFragment>()
            }
            val endOfWeek = date.plusDays(6)
            tvStandardText.text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 ~ ${endOfWeek.year}년 ${endOfWeek.monthValue}월 ${endOfWeek.dayOfMonth}일"
        }
    }

    private fun initListener() {
        with(binding) {
            ivBack.setOnSingleClickListener {
                navigateTo<StatisticsFragment>()
            }

        }

        with(binding) {
            tvStandardSelect.setOnSingleClickListener {
                val dialog = DateSelectDialog(this@StatisticsWrongFragment, date, mode)
                dialog.isCancelable = false
                dialog.show(requireActivity().supportFragmentManager, "DateSelectDialog")
            }

            ivCheckWeek.setOnSingleClickListener {
                ivCheckWeek.setImageResource(R.drawable.ic_radio_checked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_unchecked)
                mode = MODE_WEEK
                tvStandardTitle.text = "기준 주차"
                tvComment.text = getString(R.string.statistics_count_week_comment)

            }

            ivCheckMonth.setOnSingleClickListener {
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_checked)
                mode = MODE_MONTH
                tvStandardTitle.text = "기준 월"
                tvComment.text = getString(R.string.statistics_count_month_comment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFFFFF")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onYesButtonClick(date: LocalDate) {
        this.date = date
        val endOfWeek = date.plusDays(6)
        binding.tvStandardText.text = when (mode) {
            MODE_WEEK -> "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 ~ ${endOfWeek.year}년 ${endOfWeek.monthValue}월 ${endOfWeek.dayOfMonth}일"
            MODE_MONTH -> "${date.year}년 ${date.monthValue}월"
            else -> ""
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFD571")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        requireActivity().supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.java.canonicalName)
        }
    }


    companion object {
        private const val MODE_WEEK = "mode_week"
        private const val MODE_MONTH = "mode_month"
    }
}