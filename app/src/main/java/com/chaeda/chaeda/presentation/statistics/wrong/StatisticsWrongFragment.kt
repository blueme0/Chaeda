package com.chaeda.chaeda.presentation.statistics.wrong

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentStatisticsWrongBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsWrongFragment
    : BindingFragment<FragmentStatisticsWrongBinding>(R.layout.fragment_statistics_wrong) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFD571")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun initListener() {

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

    companion object {

    }
}