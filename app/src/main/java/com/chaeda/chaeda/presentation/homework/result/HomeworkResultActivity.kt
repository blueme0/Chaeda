package com.chaeda.chaeda.presentation.homework.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityHomeworkResultBinding
import com.chaeda.chaeda.presentation.homework.result.answer.ResultAnswerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkResultActivity
    : BindingActivity<ActivityHomeworkResultBinding>(R.layout.activity_homework_result) {

    private val id by intExtra()

    private val resultViewModel by viewModels<ResultViewModel>()
    private lateinit var answerAdapter: ResultAnswerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        initView()
        initAnswerItems()
        initListener()
    }

    private fun initView() {
        answerAdapter = ResultAnswerAdapter {

        }
        // 해당 문제에 대한 startActivity 필요
//            startActivity(AnswerDetailActivity.getIntent(requireContext(), it.))
//        }

        binding.rvAnswer.adapter = answerAdapter
    }

    private fun initAnswerItems() {
        answerAdapter.setItems(
            listOf(
            )
        )
    }

    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener { finish() }
        }
    }

    companion object {
        fun getIntent(context: Context, id: Int) = Intent(context, HomeworkResultActivity::class.java).apply {
            putExtra("id", id)
        }
    }

}