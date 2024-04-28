package com.chaeda.chaeda.presentation.homework.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddHomeworkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddHomeworkActivity
    : BindingActivity<ActivityAddHomeworkBinding>(R.layout.activity_add_homework) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {
            fab.setOnSingleClickListener {
                finish()
            }
            etClass.setOnSingleClickListener {
                startActivityForResult(HomeworkClassActivity.getIntent(this@AddHomeworkActivity), REQUEST_CODE_HOMEWORK_CLASS_ACTIVITY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_HOMEWORK_CLASS_ACTIVITY && resultCode == RESULT_OK) {
            data?.let {
                val yourNumberValue = it.getStringExtra("className")
                binding.etClass.text = yourNumberValue
            }
        }
    }

    companion object {
        const val REQUEST_CODE_HOMEWORK_CLASS_ACTIVITY = 1001
        fun getIntent(context: Context) = Intent(context, AddHomeworkActivity::class.java)
    }
}