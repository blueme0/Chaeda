package com.chaeda.chaeda.presentation.homework.calendar

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentWeekCalendarBinding
import com.chaeda.chaeda.presentation.homework.HomeworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class WeekCalendarFragment
    : BindingFragment<FragmentWeekCalendarBinding>(R.layout.fragment_week_calendar) {

    private val TAG = javaClass.simpleName
    private val viewModel by activityViewModels<HomeworkViewModel>()

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
        Timber.tag("chaeda-cal").d(dateList.toString())
        Timber.tag("chaeda-cal").d("${viewModel.selectedDate.value}")
        setSelectedDate(viewModel.selectedDate.value)
        super.onResume()
    }

    private fun initView() {
        pageIndex -= (Int.MAX_VALUE / 2)

        // 현재 날짜 설정
        val date = Calendar.getInstance().apply {
            add(Calendar.WEEK_OF_YEAR, pageIndex)
        }

        // 현재 연도와 월 설정
        val yearMonthFormat = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
        requireActivity().findViewById<TextView>(R.id.tv_weekly_month).text = yearMonthFormat.format(date.time)

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
                    1 -> llDay7.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                    2 -> llDay1.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                    3 -> llDay2.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                    4 -> llDay3.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                    5 -> llDay4.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                    6 -> llDay5.setBackgroundResource(R.drawable.shape_rect16_fill_date)
                    7 -> llDay6.setBackgroundResource(R.drawable.shape_rect16_fill_date)
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

