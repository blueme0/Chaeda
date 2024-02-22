package com.chaeda.chaeda.presentation.home

import android.os.Bundle
import android.view.View
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentHomeBinding
import com.chaeda.domain.entity.Homework
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment
    : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeworkAdapter: TodayHomeworkAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHomeworkItems()
    }

    private fun initView() {
        homeworkAdapter = TodayHomeworkAdapter {
            // click listener
        }

        binding.rvHomeToday.adapter = homeworkAdapter
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

    companion object {
        private const val HOMEWORK_TITLE = "문제집 이름"
        private const val HOMEWORK_CONTENT = "선생님이 작성한 본문 내용이 여기에 표시됩니다."
    }
}