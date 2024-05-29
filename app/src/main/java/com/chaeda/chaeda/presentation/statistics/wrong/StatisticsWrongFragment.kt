package com.chaeda.chaeda.presentation.statistics.wrong

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Pie
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentStatisticsWrongBinding
import com.chaeda.chaeda.presentation.statistics.StatisticsFragment
import com.chaeda.chaeda.presentation.statistics.StatisticsState
import com.chaeda.chaeda.presentation.statistics.StatisticsViewModel
import com.chaeda.chaeda.presentation.statistics.dialog.DateSelectDialog
import com.chaeda.chaeda.presentation.statistics.dialog.DateSelectDialogInterface
import com.chaeda.chaeda.presentation.statistics.type.StatisticsTypeDetailActivity
import com.chaeda.domain.entity.WrongCountWithConcept
import com.chaeda.domain.enumSet.Concept
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class StatisticsWrongFragment
    : BindingFragment<FragmentStatisticsWrongBinding>(R.layout.fragment_statistics_wrong),
    DateSelectDialogInterface {

    private val viewModel by activityViewModels<StatisticsViewModel>()

    private var date = LocalDate.now()
    private var mode = MODE_WEEK

    private lateinit var pie: Pie

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
        initGraph()
        observe()
    }

    private fun initGraph() {
        binding.anychart.setProgressBar(binding.progressBar)
        pie = AnyChart.pie()
        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf<String>("x", "value")) {
            override fun onClick(event: Event) {
//                Toast.makeText(
//                    requireActivity(),
//                    event.data["x"] + ":" + event.data["value"],
//                    Toast.LENGTH_SHORT
//                ).show()
                var englishName = event.data["x"].toString()
                for (item in Concept.values()) {
                    if (item.koreanName == englishName) {
                        englishName = item.name
                        break
                    }
                }
                startActivity(StatisticsTypeDetailActivity.getIntent(requireActivity(), englishName, mode))
            }
        })

        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("이차방정식", 10))
        data.add(ValueDataEntry("원의 방정식", 7))
        data.add(ValueDataEntry("무리식과 무리함수", 14))
        data.add(ValueDataEntry("여러 가지 함수의 미분", 20))
        data.add(ValueDataEntry("여러 가지 미분법", 7))
        data.add(ValueDataEntry("조건부확률", 2))
        data.add(ValueDataEntry("확률분포", 6))
        data.add(ValueDataEntry("이차곡선", 5))
        data.add(ValueDataEntry("이차곡선과 직선", 7))
        data.add(ValueDataEntry("벡터의 연산", 2))

        pie.data(data)
        pie.title("")
        pie.labels().position("outside")
//        pie.labels().setOnClickListener(object : ListenersInterface.OnClickListener(arrayOf<String>("name")) {
//            override fun onClick(event: Event?) {
//                Log.d("chaeda-pie", "event: ${event?.data}")
//                if (event != null) {
//                    startActivity(StatisticsTypeDetailActivity.getIntent(requireActivity(), event.data["name"].toString()))
//                }
//
//            }
//        })
        pie.legend().title().enabled(true)
        pie.legend().title()
            .text("세부개념명")
            .padding(0, 0, 10, 0)
        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)
        binding.anychart.setChart(pie)
    }

    private fun initView() {
        with(binding) {
            ivBack.setOnSingleClickListener {
                navigateTo<StatisticsFragment>()
            }
            val endOfWeek = date.plusDays(6)
            tvStandardText.text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 ~ ${endOfWeek.year}년 ${endOfWeek.monthValue}월 ${endOfWeek.dayOfMonth}일"
        }
        requestWrongCount()
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
                setStandardText()
                requestWrongCount()
                tvStandardTitle.text = "기준 주차"
                tvComment.text = getString(R.string.statistics_count_week_comment)

            }

            ivCheckMonth.setOnSingleClickListener {
                ivCheckWeek.setImageResource(R.drawable.ic_radio_unchecked)
                ivCheckMonth.setImageResource(R.drawable.ic_radio_checked)
                mode = MODE_MONTH
                setStandardText()
                requestWrongCount()
                tvStandardTitle.text = "기준 월"
                tvComment.text = getString(R.string.statistics_count_month_comment)
            }
        }
    }

    private fun setGraph(list: List<WrongCountWithConcept>) {
        val data = ArrayList<DataEntry>()

        for (item in list) {
            Timber.tag("chaeda-wrong").d("setGraph: ${item.toString()}")
            data.add(ValueDataEntry(Concept.valueOf(item.subConcept).koreanName, item.wrongCount))
        }
        pie.data(data)
        binding.anychart.invalidate()
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
        requestWrongCount()
    }

    private fun setStandardText() {
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

    fun formatDate(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDate.format(formatter)
    }

    fun formatMonth(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return localDate.format(formatter)
    }

    private fun requestWrongCount() {
        when (mode) {
            MODE_WEEK -> {
                viewModel.getWrongRateByWeek(formatDate(date))
            }
            MODE_MONTH -> {
                viewModel.getWrongRateByMonth(formatMonth(date))
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.statisticsState.collect {state ->
                when (state) {
                    is StatisticsState.GetWrongByMonthSuccess -> {
                        setGraph(state.list)
                    }
                    is StatisticsState.GetWrongByWeekSuccess -> {
                        setGraph(state.list)
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        private const val MODE_WEEK = "mode_week"
        private const val MODE_MONTH = "mode_month"
    }
}