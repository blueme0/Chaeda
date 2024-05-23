package com.chaeda.chaeda.presentation.review.box

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityProblemBoxBinding
import com.chaeda.chaeda.presentation.review.add.AddProblemPhotoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProblemBoxActivity
    : BindingActivity<ActivityProblemBoxBinding>(R.layout.activity_problem_box) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {

    }

    private fun initListener() {
        with(binding) {
            fab.setOnSingleClickListener {
                startActivity(AddProblemPhotoActivity.getIntent(this@ProblemBoxActivity))
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ProblemBoxActivity::class.java)
    }
}