package com.chaeda.chaeda.presentation.review.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddProblemPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProblemPhotoActivity
    : BindingActivity<ActivityAddProblemPhotoBinding>(R.layout.activity_add_problem_photo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {

        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, AddProblemPhotoActivity::class.java)
    }
}