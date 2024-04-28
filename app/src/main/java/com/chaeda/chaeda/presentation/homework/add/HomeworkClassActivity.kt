package com.chaeda.chaeda.presentation.homework.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityHomeworkClassBinding
import com.chaeda.domain.entity.ClassItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkClassActivity
    : BindingActivity<ActivityHomeworkClassBinding>(R.layout.activity_homework_class) {

    private lateinit var listAdapter: ClassListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initItems()
        initListener()
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

    private fun initItems() {
        listAdapter.setItems(listOf(
            ClassItem("num1", "1번클래스", "클래스 설명 1"),
            ClassItem("num2", "2번클래스", "클래스 설명 2"),
            ClassItem("num3", "3번클래스", "클래스 설명 3"),
            ClassItem("num4", "4번클래스", "클래스 설명 4")
        ))
    }

    private fun initListener() {
        with(binding) {
            fab.setOnSingleClickListener {
                // 결과를 전달하기 위해 Intent를 생성합니다.
                val resultIntent = Intent().apply {
                    putExtra("className", listAdapter.getSelectedClassName())
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, HomeworkClassActivity::class.java)
    }
}