package com.chaeda.chaeda.presentation.homework.result.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAnswerDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerDetailActivity
    : BindingActivity<ActivityAnswerDetailBinding>(R.layout.activity_answer_detail){

    private val index by intExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {
        with(binding) {
            if (tvCorrect.text == tvWritten.text) tvIndex.setBackgroundColor(Color.parseColor("#E4FADD"))
            else tvIndex.setBackgroundColor(Color.parseColor("#FADDDD"))

            tvIndex.text = index.toString()
        }
    }

    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener { finish() }
        }
    }

    companion object {
        fun getIntent(context: Context, index: Int) = Intent(context, AnswerDetailActivity::class.java).apply {
            putExtra("index", index)
        }
    }
}