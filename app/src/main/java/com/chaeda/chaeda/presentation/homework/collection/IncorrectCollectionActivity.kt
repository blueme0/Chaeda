package com.chaeda.chaeda.presentation.homework.collection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityIncorrectCollectionBinding

class IncorrectCollectionActivity
    : BindingActivity<ActivityIncorrectCollectionBinding>(R.layout.activity_incorrect_collection) {

    private lateinit var photoAdapter: IncorrectCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
        initView()
    }

    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener { finish() }
        }
    }

    private fun initView() {
        photoAdapter = IncorrectCollectionAdapter {
            // click listener
        }

        binding.rvIncorrect.adapter = photoAdapter

        val bookList = ArrayList<String>()
        bookList.addAll(listOf(
            "문제집 선택하기",
            "쎈 중등 3-1",
            "쎈 중등 3-2",
            "쎈 고등 1-1"
        ))
        binding.spinnerBook.adapter = ArrayAdapter<String>(this, R.layout.item_spinner, bookList)

        val chapterList = ArrayList<String>()
        chapterList.addAll(listOf(
            "단원명 선택하기",
            "1단원",
            "2단원"
        ))
        binding.spinnerChapter.adapter = ArrayAdapter<String>(this, R.layout.item_spinner, chapterList)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, IncorrectCollectionActivity::class.java)
    }
}