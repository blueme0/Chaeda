package com.chaeda.chaeda.presentation.homework.result.answer

import android.os.Bundle
import android.view.View
import com.chaeda.base.BindingFragment
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentResultAnswerBinding
import com.chaeda.domain.entity.ResultAnswer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultAnswerFragment
    : BindingFragment<FragmentResultAnswerBinding>(R.layout.fragment_result_answer) {

    private lateinit var answerAdapter: ResultAnswerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAnswerItems()
    }

    private fun initView() {
        answerAdapter = ResultAnswerAdapter {
            // 해당 문제에 대한 startActivity 필요
        }

        binding.rvAnswer.adapter = answerAdapter
    }

    private fun initAnswerItems() {
        answerAdapter.setItems(
            listOf(
                ResultAnswer(1, 1, 2),
                ResultAnswer(2, 2, 4),
                ResultAnswer(3, 5, 3),
            )
        )
    }
}