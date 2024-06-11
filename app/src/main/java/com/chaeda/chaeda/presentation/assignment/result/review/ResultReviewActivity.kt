package com.chaeda.chaeda.presentation.assignment.result.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.stringExtra
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityResultReviewBinding
import com.chaeda.chaeda.presentation.review.add.AddProblemPhotoActivity
import com.chaeda.domain.entity.ResultAnswer
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ResultReviewActivity
    : BindingActivity<ActivityResultReviewBinding>(R.layout.activity_result_review) {

    private val startPage by intExtra()
    private val endPage by intExtra()
    private val tname by stringExtra()
    private val tsubject by stringExtra()
    private var answers = mutableMapOf<Int, List<ResultAnswer>>()

    private var currentPage = 0
    private lateinit var reviewAdapter: ResultReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arr = intent.extras?.get("answers") as Array<ReviewAnswer>
        arr.forEach {
            answers[it.page] = it.answers
        }
        Timber.tag("chaeda-review").d("sp - ep : $startPage - $endPage")
        initView()
        initItems()
    }

    private fun initView() {
        currentPage = startPage
        reviewAdapter = ResultReviewAdapter {
            startActivity(AddProblemPhotoActivity.getIntent(
                this@ResultReviewActivity,
                it.index,
                tname!!,
                tsubject!!
            ))
        }
        with(binding) {
            tvCount.text = "${currentPage}p"
            ivLeft.setOnSingleClickListener {
                if (currentPage > startPage) {
                    currentPage--
                    answers[currentPage]?.let { it1 -> reviewAdapter.setItems(it1) }
                }
                tvCount.text = "${currentPage}p"
            }
            ivRight.setOnSingleClickListener {
                if (currentPage < endPage) {
                    currentPage++
                    answers[currentPage]?.let { it1 -> reviewAdapter.setItems(it1) }
                }
                tvCount.text = "${currentPage}p"
            }
            fab.setOnSingleClickListener {
                finish()
            }
        }
        binding.rvAnswer.adapter = reviewAdapter
    }

    private fun initItems() {
        answers[currentPage]?.let { reviewAdapter.setItems(it) }
    }

    companion object {
        fun getIntent(context: Context, arr: Array<ReviewAnswer>, sp: Int, ep: Int, tname: String, tsubject: String) = Intent(context, ResultReviewActivity::class.java).apply {
            putExtra("answers", arr)
            putExtra("startPage", sp)
            putExtra("endPage", ep)
            putExtra("tname", tname)
            putExtra("tsubject", tsubject)
        }
    }
}

data class ReviewAnswer(
    val page: Int,
    val answers: List<ResultAnswer>
) : java.io.Serializable