package com.chaeda.chaeda.presentation.assignment.calendar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentWeekCalendarBinding
import com.chaeda.chaeda.presentation.assignment.AssignmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class WeekCalendarFragment
    : BindingFragment<FragmentWeekCalendarBinding>(R.layout.fragment_week_calendar) {

    private val TAG = javaClass.simpleName
    private val viewModel by activityViewModels<AssignmentViewModel>()

    var pageIndex = 0
    private val dateList = mutableListOf<Date>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        observe()
    }

    override fun onResume() {
        setSelectedDate(viewModel.selectedDate.value)

        // 현재 연도와 월 설정
        val yearMonthFormat = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
        var yearMonthText = yearMonthFormat.format(dateList[0])
        if (yearMonthFormat.format(dateList[0]) != yearMonthFormat.format(dateList[6]))
            yearMonthText += " - ${yearMonthFormat.format(dateList[6]).split(" ")[1]}"
        val textView = requireActivity().findViewById<TextView>(R.id.tv_weekly_month)
        if (textView != null) textView.text = yearMonthText
        super.onResume()
    }

    private fun initView() {
        pageIndex -= (Int.MAX_VALUE / 2)

        // 현재 날짜 설정
        val date = Calendar.getInstance().apply {
            add(Calendar.WEEK_OF_YEAR, pageIndex)
        }

//        Timber.tag("chaeda-cal").d("month: ${yearMonthFormat.format(date.time)}")
//        .text = yearMonthFormat.format(date.time)

        val dateFormat = SimpleDateFormat("dd", Locale.KOREA)

        val dayOfWeek = (date.get(Calendar.DAY_OF_WEEK) + 5) % 7
        date.add(Calendar.DAY_OF_YEAR, -dayOfWeek)

        // 각 날짜를 월 - 일 순서로 dateList에 저장
        repeat(7) {
            dateList.add(date.time)
            date.add(Calendar.DAY_OF_YEAR, 1)
        }

        // 각 날짜 텍스트뷰에 날짜 설정
        with(binding) {
            tvDay1.text = dateFormat.format(dateList[0])
            tvDay2.text = dateFormat.format(dateList[1])
            tvDay3.text = dateFormat.format(dateList[2])
            tvDay4.text = dateFormat.format(dateList[3])
            tvDay5.text = dateFormat.format(dateList[4])
            tvDay6.text = dateFormat.format(dateList[5])
            tvDay7.text = dateFormat.format(dateList[6])
        }
    }

    private fun initListener() {
        with(binding) {
            llDay1.setOnClickListener { viewModel.setSelectedDate(dateList[0]) }
            llDay2.setOnClickListener { viewModel.setSelectedDate(dateList[1]) }
            llDay3.setOnClickListener { viewModel.setSelectedDate(dateList[2]) }
            llDay4.setOnClickListener { viewModel.setSelectedDate(dateList[3]) }
            llDay5.setOnClickListener { viewModel.setSelectedDate(dateList[4]) }
            llDay6.setOnClickListener { viewModel.setSelectedDate(dateList[5]) }
            llDay7.setOnClickListener { viewModel.setSelectedDate(dateList[6]) }
        }
    }

    private fun clearBackground() {
        with(binding) {
            llDay1.setBackgroundResource(0)
            llDay2.setBackgroundResource(0)
            llDay3.setBackgroundResource(0)
            llDay4.setBackgroundResource(0)
            llDay5.setBackgroundResource(0)
            llDay6.setBackgroundResource(0)
            llDay7.setBackgroundResource(0)

            tvDay1.setTextColor(Color.parseColor("#1E293B"))
            tvDay2.setTextColor(Color.parseColor("#1E293B"))
            tvDay3.setTextColor(Color.parseColor("#1E293B"))
            tvDay4.setTextColor(Color.parseColor("#1E293B"))
            tvDay5.setTextColor(Color.parseColor("#1E293B"))
            tvDay6.setTextColor(Color.parseColor("#1E293B"))
            tvDay7.setTextColor(Color.parseColor("#1E293B"))

            tvMon.setTextColor(Color.parseColor("#94A3B8"))
            tvTue.setTextColor(Color.parseColor("#94A3B8"))
            tvWed.setTextColor(Color.parseColor("#94A3B8"))
            tvThu.setTextColor(Color.parseColor("#94A3B8"))
            tvFri.setTextColor(Color.parseColor("#94A3B8"))
            tvSat.setTextColor(Color.parseColor("#94A3B8"))
            tvSun.setTextColor(Color.parseColor("#94A3B8"))
        }
    }

    private fun setSelectedDate(date: Date) {
        clearBackground()
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        with(binding) {
            if (dateFormat.format(dateList[(dayOfWeek + 5) % 7]) == dateFormat.format(date)) {
                when (dayOfWeek) {
                    1 -> {
                        llDay7.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                        tvDay7.setTextColor(Color.parseColor("#FFB400"))
                        tvSun.setTextColor(Color.parseColor("#FFB400"))
                    }
                    2 -> {
                        llDay1.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                        tvDay1.setTextColor(Color.parseColor("#FFB400"))
                        tvMon.setTextColor(Color.parseColor("#FFB400"))
                    }
                    3 -> {
                        llDay2.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                        tvDay2.setTextColor(Color.parseColor("#FFB400"))
                        tvTue.setTextColor(Color.parseColor("#FFB400"))
                    }
                    4 -> {
                        llDay3.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                        tvDay3.setTextColor(Color.parseColor("#FFB400"))
                        tvWed.setTextColor(Color.parseColor("#FFB400"))
                    }
                    5 -> {
                        llDay4.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                        tvDay4.setTextColor(Color.parseColor("#FFB400"))
                        tvThu.setTextColor(Color.parseColor("#FFB400"))
                    }
                    6 -> {
                        llDay5.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                        tvDay5.setTextColor(Color.parseColor("#FFB400"))
                        tvFri.setTextColor(Color.parseColor("#FFB400"))
                    }
                    7 -> {
                        llDay6.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                        tvDay6.setTextColor(Color.parseColor("#FFB400"))
                        tvSat.setTextColor(Color.parseColor("#FFB400"))
                    }
                }
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.selectedDate.collect { date ->
                setSelectedDate(date)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    companion object {
        var instance: WeekCalendarFragment? = null
    }
}

