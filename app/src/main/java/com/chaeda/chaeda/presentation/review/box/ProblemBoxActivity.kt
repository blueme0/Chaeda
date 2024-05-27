package com.chaeda.chaeda.presentation.review.box

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.boolExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityProblemBoxBinding
import com.chaeda.chaeda.presentation.review.add.AddProblemPhotoActivity
import com.chaeda.chaeda.presentation.review.add.ReviewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProblemBoxActivity
    : BindingActivity<ActivityProblemBoxBinding>(R.layout.activity_problem_box) {

    private lateinit var photoAdapter: ProblemBoxAdapter
    private val viewModel by viewModels<ProblemBoxViewModel>()

    private val forChoice by boolExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (forChoice) {
            initViewForChoice()
            initListenerForChoice()
        }
        else {
            initView()
            initListener()
        }


        observe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProblemsFromBox()
    }

    private fun initViewForChoice() {
        photoAdapter = ProblemBoxAdapter(true) {
        }
        binding.rvProblems.adapter = photoAdapter
    }

    private fun initListenerForChoice() {
        with(binding) {
            fab.setOnSingleClickListener {
                Timber.tag("chaeda-box").d("selectedItems: ${photoAdapter.getItems()}")
                val resultIntent = Intent().apply {
                    putExtra("selectedItems", photoAdapter.getItems().values.toTypedArray())
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun initView() {
        photoAdapter = ProblemBoxAdapter(false) {
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
                        photoAdapter.setItems(state.urls)
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context, forChoice: Boolean) = Intent(context, ProblemBoxActivity::class.java).apply {
            putExtra("forChoice", forChoice)
        }
    }
}