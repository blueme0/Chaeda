package com.chaeda.chaeda.presentation.homework.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity
    : BindingActivity<ActivityTestBinding>(R.layout.activity_test) {

    private var isFirst = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        with(binding) {
            ivTest.setOnSingleClickListener {
                isFirst = !isFirst
                if (!isFirst) ivTest.setImageResource(R.drawable.asm0000)
                else ivTest.setImageResource(R.drawable.asm0200)
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, TestActivity::class.java)
    }
}