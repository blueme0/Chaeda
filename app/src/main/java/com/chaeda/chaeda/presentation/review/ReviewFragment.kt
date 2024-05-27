package com.chaeda.chaeda.presentation.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentReviewBinding
import com.chaeda.chaeda.presentation.review.add.ReviewState
import com.chaeda.chaeda.presentation.review.box.ProblemBoxActivity
import com.chaeda.chaeda.presentation.review.folder.ReviewFolderActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewFragment
    : BindingFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private val viewModel by activityViewModels<ReviewViewModel>()
    private lateinit var folderAdapter: ReviewFolderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initView()
        observe()
    }

    private fun initListener() {
        with(binding) {
            tvBox.setOnSingleClickListener {
                startActivity(ProblemBoxActivity.getIntent(requireActivity(), false))
            }
            tvCreate.setOnSingleClickListener {
                startActivity(ReviewFolderActivity.getIntent(requireActivity(), -1L, null, null))
            }
            tvPdf.setOnSingleClickListener {

            }

        }
    }

    private fun initView() {
        folderAdapter = ReviewFolderAdapter {
            startActivity(ReviewFolderActivity.getIntent(requireContext(), it.id!!, it.title, it.description))
        }

        binding.rvFolder.adapter = folderAdapter

        viewModel.getReviewFolderList()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.reviewState.collect { state ->
                when (state) {
                    is ReviewState.GetReviewFolderListSuccess -> {
                        folderAdapter.setItems(state.list)
                    }
                    else -> { }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReviewFolderList()
    }
}