package com.chaeda.chaeda.presentation.homework.result.submit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.stringExtra
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityResultSubmitBinding
import com.chaeda.chaeda.presentation.homework.AssignmentState
import com.chaeda.chaeda.presentation.homework.dialog.SimpleAlertDialog
import com.chaeda.chaeda.presentation.homework.dialog.SimpleAlertDialogInterface
import com.chaeda.chaeda.presentation.homework.result.ResultViewModel
import com.chaeda.chaeda.presentation.homework.result.answer.ResultAnswerAdapter
import com.chaeda.domain.entity.ResultAnswer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class ResultSubmitActivity
    : BindingActivity<ActivityResultSubmitBinding> (R.layout.activity_result_submit), SimpleAlertDialogInterface {

    private val id by intExtra()
    private val title by stringExtra()
    private val sp by intExtra()
    private val ep by intExtra()
    private val deadline by stringExtra()

    private val resultViewModel by viewModels<ResultViewModel>()
    private lateinit var answerAdapter: ResultAnswerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        initView()
        initAnswerItems()
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
        }
        // 해당 문제에 대한 startActivity 필요
//            startActivity(AnswerDetailActivity.getIntent(requireContext(), it.))
//        }
        with(binding) {
            tvTitle.text = title
            ivLeft.setOnSingleClickListener {
                if (resultViewModel.currentPage.value > sp)
                    resultViewModel.updateCurrentPage(resultViewModel.currentPage.value - 1)
            }
            ivRight.setOnSingleClickListener {
                if (resultViewModel.currentPage.value < ep)
                    resultViewModel.updateCurrentPage(resultViewModel.currentPage.value + 1)
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

    private fun isAfterToday(dateString: String): Boolean {
        val date = LocalDate.parse(dateString)
        val today = LocalDate.now()
        return date.isAfter(today)
    }

    private fun observe() {
        lifecycleScope.launch {
            resultViewModel.assignmentState.collect { state ->
                when (state) {
                    is AssignmentState.SubmitSuccess -> {
                        if (deadline != null && isAfterToday(deadline!!)) {
                            val dialog = SimpleAlertDialog(this@ResultSubmitActivity)
                            dialog.isCancelable = false
                            dialog.show(supportFragmentManager, "SimpleAlertDialog")
                        }
                        else finish()
                    }
                    is AssignmentState.GetRangeSuccess -> {
                        resultViewModel.updateCurrentPage(sp)
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
        finish()
    }

    companion object {
        fun getIntent(context: Context, id: Int, title: String, sp: Int, ep: Int, deadline: String) = Intent(context, ResultSubmitActivity::class.java).apply {
            putExtra("id", id)
            putExtra("title", title)
            putExtra("startPage", sp)
            putExtra("endPage", ep)
            putExtra("deadline", deadline)
        }
    }
}