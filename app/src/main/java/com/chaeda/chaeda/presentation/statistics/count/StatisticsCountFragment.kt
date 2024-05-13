package com.chaeda.chaeda.presentation.statistics.count

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentStatisticsCountBinding
import com.chaeda.chaeda.presentation.statistics.StatisticsFragment
import com.chaeda.chaeda.presentation.statistics.dialog.DateSelectDialog
import com.chaeda.chaeda.presentation.statistics.dialog.DateSelectDialogInterface
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class StatisticsCountFragment
    : BindingFragment<FragmentStatisticsCountBinding>(R.layout.fragment_statistics_count), DateSelectDialogInterface{

    private var date = LocalDate.now()
    private var mode = MODE_DATE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initGraph()
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

    private fun initGraph() {
        binding.anychart.setProgressBar(binding.progressBar)
        val cartesian = AnyChart.line()
        cartesian.animation(true)
        cartesian.padding(10, 20, 5, 20)
        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true)
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("")
        cartesian.yAxis(0).title("문제 수")
        cartesian.xAxis(0).labels().padding(5, 5, 5, 5)

        val seriesData = ArrayList<DataEntry>()
        seriesData.add(SolvedCount("5/7", 1))
        seriesData.add(SolvedCount("5/8", 3))
        seriesData.add(SolvedCount("5/9", 2))
        seriesData.add(SolvedCount("5/10", 4))
        seriesData.add(SolvedCount("5/11", 7))
        seriesData.add(SolvedCount("5/12", 5))
        seriesData.add(SolvedCount("5/13", 2))
        val set = Set.instantiate()
        set.data(seriesData)
        val mapping = set.mapAs("{ x: 'date', value: 'count' }")
        val series1 = cartesian.line(mapping)
        series1.name("문제 수")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5)
            .offsetY(5)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13)
        cartesian.legend().padding(0, 0, 0, 0)
        binding.anychart.setChart(cartesian)

    }

    private fun initView() {
        with(binding) {
            ivBack.setOnSingleClickListener {
                navigateTo<StatisticsFragment>()
            }
            tvStandardText.text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 (${DAY_OF_WEEK[date.dayOfWeek.value - 1]})"
        }
    }

    private fun initListener() {
        with(binding) {
            tvStandardSelect.setOnSingleClickListener {
                val dialog = DateSelectDialog(this@StatisticsCountFragment, date, mode)
                dialog.isCancelable = false
                dialog.show(requireActivity().supportFragmentManager, "DateSelectDialog")
            }

            ivCheckDate.setOnSingleClickListener {
                ivCheckDate.setImageResource(R.drawable.ic_radio_checked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_unchecked)
                mode = MODE_DATE
                setStandardText()
                tvStandardTitle.text = "기준 날짜"
                tvComment.text = getString(R.string.statistics_count_date_comment)
            }

            ivCheckWeek.setOnSingleClickListener {
                ivCheckDate.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_checked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_unchecked)
                mode = MODE_WEEK
                setStandardText()
                tvStandardTitle.text = "기준 주차"
                tvComment.text = getString(R.string.statistics_count_week_comment)

            }

            ivCheckMonth.setOnSingleClickListener {
                ivCheckDate.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_checked)
                mode = MODE_MONTH
                setStandardText()
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
        setStandardText()
    }

    private fun setStandardText() {
        val endOfWeek = date.plusDays(6)
        binding.tvStandardText.text = when (mode) {
            MODE_DATE -> "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 (${DAY_OF_WEEK[date.dayOfWeek.value - 1]})"
            MODE_WEEK -> "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 ~ ${endOfWeek.year}년 ${endOfWeek.monthValue}월 ${endOfWeek.dayOfMonth}일"
            MODE_MONTH -> "${date.year}년 ${date.monthValue}월"
            else -> ""
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        requireActivity().supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.java.canonicalName)
        }
    }

    companion object {
        private const val MODE_DATE = "mode_date"
        private const val MODE_WEEK = "mode_week"
        private const val MODE_MONTH = "mode_month"
        private val DAY_OF_WEEK = arrayOf("월", "화", "수", "목", "금", "토", "일")
    }
}

data class SolvedCount(val date: String, val count: Int): ValueDataEntry(date, count)

