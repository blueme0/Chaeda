package com.chaeda.chaeda.presentation.homework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentHomeworkBinding
import com.chaeda.chaeda.presentation.homework.calendar.WeekFragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkFragment
    : BindingFragment<FragmentHomeworkBinding>(R.layout.fragment_homework) {

    private val viewModel by activityViewModels<HomeworkViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initViewPager()
    }

    private fun initListener() {
//        // activity 시작
//        binding.tvPresign.setOnSingleClickListener {
//            startActivity(Intent(requireContext(), ConfirmSubmitActivity::class.java))
//        }
    }

    private fun initViewPager() {
        val fragmentStateAdapter = WeekFragmentStateAdapter(requireActivity())
        binding.vp.adapter = fragmentStateAdapter
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        fragmentStateAdapter.apply {
            binding.vp.setCurrentItem(this.firstFragmentPosition, false)
        }
    }
}