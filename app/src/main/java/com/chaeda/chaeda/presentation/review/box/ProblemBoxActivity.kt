package com.chaeda.chaeda.presentation.review.box

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityProblemBoxBinding
import com.chaeda.chaeda.presentation.homework.collection.IncorrectCollectionAdapter
import com.chaeda.chaeda.presentation.review.add.AddProblemPhotoActivity
import com.chaeda.chaeda.presentation.review.add.ReviewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProblemBoxActivity
    : BindingActivity<ActivityProblemBoxBinding>(R.layout.activity_problem_box) {

    private lateinit var photoAdapter: IncorrectCollectionAdapter
    private val viewModel by viewModels<ProblemBoxViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()

        observe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProblemsFromBox()
    }

    private fun initView() {
        photoAdapter = IncorrectCollectionAdapter {
            // click listener
        }

        binding.rvProblems.adapter = photoAdapter
    }

    private fun initListener() {
        with(binding) {
            fab.setOnSingleClickListener {
                startActivity(AddProblemPhotoActivity.getIntent(this@ProblemBoxActivity))
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.reviewState.collect { state ->
                when (state) {
                    is ReviewState.GetProblemsFromBoxSuccess -> {
                        val list = mutableListOf<String>()
                        for (i in state.urls) {
                            list.add(i.presignedUrl)
                        }
                        photoAdapter.setItems(list)
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ProblemBoxActivity::class.java)
    }
}