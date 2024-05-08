package com.chaeda.chaeda.presentation.statistics.count

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
        }
    }

    private fun initListener() {
        with(binding) {
            tvStandardSelect.setOnSingleClickListener {
                val dialog = DateSelectDialog(this@StatisticsCountFragment, date, mode)
                dialog.isCancelable = false
                dialog.show(requireActivity().supportFragmentManager, "DateSelectDialog")
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
    }
}