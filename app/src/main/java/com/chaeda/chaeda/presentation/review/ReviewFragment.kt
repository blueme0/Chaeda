package com.chaeda.chaeda.presentation.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentReviewBinding
import com.chaeda.chaeda.presentation.review.box.ProblemBoxActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment
    : BindingFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private val viewModel by activityViewModels<ReviewViewModel>()
//    private lateinit var folderAdapter:

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {
            tvBox.setOnSingleClickListener {
                startActivity(ProblemBoxActivity.getIntent(requireActivity()))
            }
        }
    }
}