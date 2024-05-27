package com.chaeda.chaeda.presentation.review.pdf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityReviewPdfListBinding
import com.chaeda.chaeda.presentation.review.add.ReviewState
import kotlinx.coroutines.launch

class ReviewPdfListActivity
    : BindingActivity<ActivityReviewPdfListBinding>(R.layout.activity_review_pdf_list) {

    private val viewModel by viewModels<ReviewPdfListViewModel>()
    private lateinit var pdfAdapter: PdfListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
        initView()
        observe()
    }

    private fun initListener() {
        with(binding) {

        }
    }

    private fun initView() {
        pdfAdapter = PdfListAdapter {
            viewModel.getReviewPdf(it.id)
        }

        binding.rvFiles.adapter = pdfAdapter

        viewModel.getReviewPdfList()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.reviewState.collect { state ->
                when (state) {
                    is ReviewState.GetReviewPdfListSuccess -> {
                        pdfAdapter.setItems(state.list)
                    }
                    is ReviewState.GetReviewPdfSuccess -> {

                    }
                    else -> { }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReviewPdfList()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ReviewPdfListActivity::class.java)
    }
}