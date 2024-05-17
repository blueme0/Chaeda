package com.chaeda.chaeda.presentation.homework.textbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityTextbookListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TextbookListActivity
    : BindingActivity<ActivityTextbookListBinding>(R.layout.activity_textbook_list) {

    private lateinit var listAdapter: TextbookListAdapter
    private val textbookViewModel by viewModels<TextbookViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initItems()
        initListener()
        observe()
    }

    private fun initView() {
        listAdapter = TextbookListAdapter {
            // 리스너 달 거 있음 달기
            textbookViewModel.updateTextbook(it.id, it.name)
            val resultIntent = Intent().apply {
                putExtra("textbookId", textbookViewModel.textbookId.value)
                putExtra("textbookName", textbookViewModel.textbookName.value)
                putExtra("startPage", it.startPage)
                putExtra("lastPage", it.lastPage)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        with(binding.rvTextbook) {
            this.adapter = listAdapter
            layoutManager = LinearLayoutManager(this@TextbookListActivity)
        }
    }

    private fun initItems() {
        textbookViewModel.getTextbookList()
    }

    private fun initListener() {
        with(binding) {
//            fab.setOnSingleClickListener {
//                // 결과를 전달하기 위해 Intent를 생성합니다.
//                val resultIntent = Intent().apply {
//                    putExtra("textbookId", textbookViewModel.textbookId.value)
//                    putExtra("textbookName", textbookViewModel.textbookName.value)
//                }
//                setResult(RESULT_OK, resultIntent)
//                finish()
//            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            textbookViewModel.textbookState.collect { state ->
                when (state) {
                    is TextbookState.GetListSuccess -> {
                        listAdapter.setItems(state.list)
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, TextbookListActivity::class.java)
    }
}