package com.chaeda.chaeda.presentation.homework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentHomeworkBinding
import com.chaeda.chaeda.presentation.home.TodayHomeworkAdapter
import com.chaeda.chaeda.presentation.homework.calendar.WeekFragmentStateAdapter
import com.chaeda.chaeda.presentation.homework.detail.HomeworkDetailActivity
import com.chaeda.domain.entity.Homework
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkFragment
    : BindingFragment<FragmentHomeworkBinding>(R.layout.fragment_homework) {

    private val viewModel by activityViewModels<HomeworkViewModel>()
    private lateinit var homeworkAdapter: TodayHomeworkAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initViewPager()
        initView()
        initHomeworkItems()
    }

    private fun initListener() {
//        // activity 시작
//        binding.tvPresign.setOnSingleClickListener {
//            startActivity(Intent(requireContext(), ConfirmSubmitActivity::class.java))
//        }
    }

    private fun initView() {
        homeworkAdapter = TodayHomeworkAdapter {
            // click listener
            startActivity(HomeworkDetailActivity.getIntent(requireContext(), 0))
        }

        binding.rvHomework.adapter = homeworkAdapter
    }

    private fun initHomeworkItems() {
        homeworkAdapter.setItems(
            listOf(
                Homework(HOMEWORK_TITLE, HOMEWORK_CONTENT, listOf("")),
                Homework(HOMEWORK_TITLE, HOMEWORK_CONTENT, listOf("")),
                Homework(HOMEWORK_TITLE, HOMEWORK_CONTENT, listOf(""))
            )
        )
    }

    private fun initViewPager() {
        val fragmentStateAdapter = WeekFragmentStateAdapter(requireActivity())
        binding.vp.adapter = fragmentStateAdapter
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        fragmentStateAdapter.apply {
            binding.vp.setCurrentItem(this.firstFragmentPosition, false)
        }
    }

    companion object {
        private const val HOMEWORK_TITLE = "문제집 이름"
        private const val HOMEWORK_CONTENT = "선생님이 작성한 본문 내용이 여기에 표시됩니다."
    }
}