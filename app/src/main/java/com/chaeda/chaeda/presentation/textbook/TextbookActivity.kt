package com.chaeda.chaeda.presentation.textbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityTextbookBinding
import com.chaeda.chaeda.presentation.textbook.add.AddTextbookActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TextbookActivity
    : BindingActivity<ActivityTextbookBinding>(R.layout.activity_textbook){

    private lateinit var listAdapter: TextbookAdapter
    private val viewModel by viewModels<TextbookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
        setTextChangedListener()
        observe()
    }

    private fun initView() {
        listAdapter = TextbookAdapter {

        }
        binding.rvTextbook.adapter = listAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTextbookList()
    }

    private fun initListener() {
        viewModel.getTextbookList()
        with(binding) {
            tvAdd.setOnSingleClickListener {
                startActivity(AddTextbookActivity.getIntent(this@TextbookActivity))
            }
        }
    }

    private fun setTextChangedListener() {
        with(binding) {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateSearch(p0.toString())
                }
            })
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.textbookState.collect { state ->
                when (state) {
                    is TextbookState.GetListSuccess -> {
                        binding.etSearch.text.clear()
                        viewModel.updateSearch("")
                    }
                    is TextbookState.TextbookFilterSuccess -> {
                        Log.d("chaeda-tb", "TextbookFilterSuccess")
                        listAdapter.setItems(state.list)
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, TextbookActivity::class.java)
    }
}