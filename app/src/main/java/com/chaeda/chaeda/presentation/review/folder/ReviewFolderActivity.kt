package com.chaeda.chaeda.presentation.review.folder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityReviewFolderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFolderActivity
    : BindingActivity<ActivityReviewFolderBinding>(R.layout.activity_review_folder){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ReviewFolderActivity::class.java)
    }
}