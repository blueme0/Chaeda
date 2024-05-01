package com.chaeda.chaeda.presentation.homework.result.submit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityResultSubmitBinding
import com.chaeda.chaeda.presentation.homework.result.ResultViewModel
import com.chaeda.chaeda.presentation.homework.result.answer.ResultAnswerAdapter
import com.chaeda.domain.entity.ResultAnswer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultSubmitActivity
    : BindingActivity<ActivityResultSubmitBinding> (R.layout.activity_result_submit) {

    private val resultViewModel by viewModels<ResultViewModel>()
    private lateinit var answerAdapter: ResultAnswerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        initView()
        initAnswerItems()
    }

    private fun initView() {
        answerAdapter = ResultAnswerAdapter()
        // 해당 문제에 대한 startActivity 필요
//            startActivity(AnswerDetailActivity.getIntent(requireContext(), it.))
//        }

        binding.rvAnswer.adapter = answerAdapter
    }

    private fun initAnswerItems() {
        answerAdapter.setItems(
            listOf(
                ResultAnswer(index = 1),
                ResultAnswer(index = 2),
                ResultAnswer(index = 3),
            )
        )
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ResultSubmitActivity::class.java)
    }
}