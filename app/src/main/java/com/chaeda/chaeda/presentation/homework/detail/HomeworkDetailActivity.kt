package com.chaeda.chaeda.presentation.homework.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.boolExtra
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityHomeworkDetailBinding
import com.chaeda.chaeda.presentation.homework.result.HomeworkResultActivity
import com.chaeda.chaeda.presentation.homework.submit.ConfirmSubmitActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkDetailActivity
    : BindingActivity<ActivityHomeworkDetailBinding>(R.layout.activity_homework_detail) {

    private val id by intExtra()
    private val isDone by boolExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {
            tvFab.text = if (isDone) "오답 확인하기" else "검사 시작하기"
            llBack.setOnSingleClickListener {
                finish()
            }
            fab.setOnSingleClickListener {
                if (isDone) startActivity(HomeworkResultActivity.getIntent(this@HomeworkDetailActivity))
                else startActivity(ConfirmSubmitActivity.getIntent(this@HomeworkDetailActivity))
            }
        }
    }

    companion object {
        fun getIntent(context: Context, id: Int) = Intent(context, HomeworkDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        fun getIntent(context: Context, id: Int, isDone: Boolean) = Intent(context, HomeworkDetailActivity::class.java).apply {
            putExtra("id", id)
            putExtra("isDone", isDone)
        }
    }
}