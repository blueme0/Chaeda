package com.chaeda.chaeda.presentation.assignment.result.submit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.longExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.stringExtra
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityResultSubmitBinding
import com.chaeda.chaeda.presentation.assignment.AssignmentState
import com.chaeda.chaeda.presentation.assignment.dialog.SimpleAlertDialog
import com.chaeda.chaeda.presentation.assignment.dialog.SimpleAlertDialogInterface
import com.chaeda.chaeda.presentation.assignment.result.ResultViewModel
import com.chaeda.chaeda.presentation.assignment.result.answer.ResultAnswerAdapter
import com.chaeda.chaeda.presentation.assignment.result.review.ResultReviewActivity
import com.chaeda.chaeda.presentation.assignment.result.review.ReviewAnswer
import com.chaeda.domain.entity.ResultAnswer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

@AndroidEntryPoint
class ResultSubmitActivity
    : BindingActivity<ActivityResultSubmitBinding> (R.layout.activity_result_submit), SimpleAlertDialogInterface {

    private val id by longExtra()
    private val title by stringExtra()
    private val startPage by intExtra()
    private val endPage by intExtra()
    private val deadline by stringExtra()
    private val tname by stringExtra()
    private val tsubject by stringExtra()

    private val resultViewModel by viewModels<ResultViewModel>()
    private lateinit var answerAdapter: ResultAnswerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        Timber.tag("chaeda-result").d("result submit activity: id: $id, sp: ${startPage}, ep: $endPage")
        initView()
//        initAnswerItems()
        initListeners()
        observe()
    }

    private fun initView() {
        answerAdapter = ResultAnswerAdapter {
            resultViewModel.updateRecords(
                resultViewModel.currentPage.value,
                it.index,
                if (it.checked) it.level else null
            )
            binding.tvCount2.text = "${resultViewModel.selectedProblemCount.value}문제"
        }
        // 해당 문제에 대한 startActivity 필요
//            startActivity(AnswerDetailActivity.getIntent(requireContext(), it.))
//        }
        with(binding) {
            tvTitle.text = title
            ivLeft.setOnSingleClickListener {
                if (resultViewModel.currentPage.value > startPage)
                    resultViewModel.updateCurrentPage(resultViewModel.currentPage.value - 1)
                tvCount.text = "${resultViewModel.currentPage.value}p"
            }
            ivRight.setOnSingleClickListener {
                if (resultViewModel.currentPage.value < endPage)
                    resultViewModel.updateCurrentPage(resultViewModel.currentPage.value + 1)
                tvCount.text = "${resultViewModel.currentPage.value}p"
            }
        }
        binding.rvAnswer.adapter = answerAdapter

        resultViewModel.getProblemRanges(id)
    }

    private fun initAnswerItems() {
        answerAdapter.setItems(
            listOf(
            )
        )
    }

    private fun initListeners() {
        with(binding) {
            fab.setOnSingleClickListener {
                resultViewModel.postAssignmentResult(id)
            }
        }
    }

    private fun isBeforeToday(dateString: String): Boolean {
        val date = LocalDate.parse(dateString.split(" ")[0])
        val today = LocalDate.now()
        return date.isBefore(today)
    }

    private fun moveToReview() {
        if (resultViewModel.selectedProblemCount.value == 0) {
            finish()
            return
        }

        val arr = ArrayList<ReviewAnswer>()
        resultViewModel.problemRange.value.forEach { (i, strings) ->
            Timber.tag("chaeda-check").d("i: $i, strings: ${strings.toList()}")
            Timber.tag("chaeda-check").d("assignmentResult: ${resultViewModel.assignmentResult.value[i]}")
            val list = mutableListOf<ResultAnswer>()
            strings.forEach {
                if (resultViewModel.assignmentResult.value.containsKey(i))
                    list.add(ResultAnswer(resultViewModel.assignmentResult.value[i]?.containsKey(it)!!, it))
                else
                    list.add(ResultAnswer(false, it))
            }
            arr.add(ReviewAnswer(i, list))
        }
        startActivity(ResultReviewActivity.getIntent(
            this@ResultSubmitActivity,
            arr.toTypedArray(),
            startPage,
            endPage,
            tname!!,
            tsubject!!
            ))
        finish()
    }

    private fun observe() {
        lifecycleScope.launch {
            resultViewModel.assignmentState.collect { state ->
                when (state) {
                    is AssignmentState.SubmitSuccess -> {
                        if (deadline != null && isBeforeToday(deadline!!)) {
                            val dialog = SimpleAlertDialog(this@ResultSubmitActivity)
                            dialog.isCancelable = false
                            dialog.show(supportFragmentManager, "SimpleAlertDialog")
                        }
                        else {
                            moveToReview()
                        }
                    }
                    is AssignmentState.GetRangeSuccess -> {
                        Timber.tag("chaeda-getrange").d("AssignmentState GetRangeSuccess called")

                        resultViewModel.updateCurrentPage(startPage)
                        binding.tvCount.text = "${startPage}p"
                        binding.tvCount1.text = "${resultViewModel.totalProblemCount.value}개의 문제 중 "
                    }
                    is AssignmentState.Failure -> {

                    }
                    else -> {

                    }
                }
            }
        }

        lifecycleScope.launch {
            resultViewModel.currentPage.collect { pageNum ->
                val page = resultViewModel.getPage(pageNum)
                val list = mutableListOf<ResultAnswer>()
                page?.forEach { problem ->
                    val result = resultViewModel.findWrong(pageNum, problem)
                    list.add(ResultAnswer(
                        checked = result != null,
                        index = problem,
                        level = result ?: "미풀이"
                    ))
                }
                answerAdapter.setItems(list)
            }
        }
    }

    override fun onYesButtonClick() {
        moveToReview()
    }

    companion object {
        fun getIntent(context: Context, id: Long, title: String, sp: Int, ep: Int, deadline: String, tname: String, tsubject: String) = Intent(context, ResultSubmitActivity::class.java).apply {
            putExtra("id", id)
            putExtra("title", title)
            putExtra("startPage", sp)
            putExtra("endPage", ep)
            putExtra("deadline", deadline)
            putExtra("tname", tname)
            putExtra("tsubject", tsubject)
        }
    }
}