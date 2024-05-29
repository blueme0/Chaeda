package com.chaeda.chaeda.presentation.assignment.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WeekFragmentStateAdapter(fragmentActivity: FragmentActivity)
: FragmentStateAdapter(fragmentActivity) {

    val firstFragmentPosition = Int.MAX_VALUE / 2

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val calendarFragment = WeekCalendarFragment()
        calendarFragment.pageIndex = position
        return calendarFragment
    }

}