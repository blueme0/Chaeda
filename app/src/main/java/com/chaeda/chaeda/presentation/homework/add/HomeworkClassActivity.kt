package com.chaeda.chaeda.presentation.homework.add

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityHomeworkClassBinding

class HomeworkClassActivity
    : BindingActivity<ActivityHomeworkClassBinding>(R.layout.activity_homework_class) {

    private lateinit var listAdapter: ClassListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        listAdapter = ClassListAdapter {
            // 리스너 달 거 있음 달기
        }
        with(binding.rvClass) {
            this.adapter = listAdapter
            layoutManager = LinearLayoutManager(this@HomeworkClassActivity)
        }
    }
}