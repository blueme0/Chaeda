package com.chaeda.chaeda.presentation.homework.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityHomeworkDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkDetailActivity
    : BindingActivity<ActivityHomeworkDetailBinding>(R.layout.activity_homework_detail) {

    private val id by intExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener {
                finish()
            }
        }
    }

    companion object {
        fun getIntent(context: Context, id: Int) = Intent(context, HomeworkDetailActivity::class.java).apply {
            putExtra("id", id)
        }
    }
}