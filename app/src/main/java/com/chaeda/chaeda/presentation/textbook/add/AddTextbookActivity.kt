package com.chaeda.chaeda.presentation.textbook.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddTextbookBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTextbookActivity
    : BindingActivity<ActivityAddTextbookBinding>(R.layout.activity_add_textbook) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
        observe()
    }

    private fun initView() {

    }

    private fun initListener() {
        with(binding) {
            tvAddFile.setOnSingleClickListener {

            }
        }

    }

    private fun observe() {

    }

    companion object {
        fun getIntent(context: Context) = Intent(context, AddTextbookActivity::class.java)
    }
}