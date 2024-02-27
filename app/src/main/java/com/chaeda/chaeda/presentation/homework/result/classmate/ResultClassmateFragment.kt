package com.chaeda.chaeda.presentation.homework.result.classmate

import android.os.Bundle
import android.view.View
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentResultClassmateBinding
import com.chaeda.chaeda.presentation.homework.result.detail.AnswerDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultClassmateFragment
    : BindingFragment<FragmentResultClassmateBinding>(R.layout.fragment_result_classmate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        /**
         * problem에 순서대로 오답 많은 top 1, top 2, top 3 문제들의 index 들어갈 것
         */
        val problem = listOf(1, 2, 3)
        with(binding) {
            ivTop1.setOnSingleClickListener {
                startActivity(AnswerDetailActivity.getIntent(requireContext(), problem[0]))
            }
            ivTop2.setOnSingleClickListener {
                startActivity(AnswerDetailActivity.getIntent(requireContext(), problem[1]))
            }
            ivTop3.setOnSingleClickListener {
                startActivity(AnswerDetailActivity.getIntent(requireContext(), problem[2]))
            }
        }
    }
}