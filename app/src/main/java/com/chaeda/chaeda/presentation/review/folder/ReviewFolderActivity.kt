package com.chaeda.chaeda.presentation.review.folder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.longExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.stringExtra
import com.chaeda.base.util.extension.toast
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityReviewFolderBinding
import com.chaeda.chaeda.presentation.review.add.ReviewState
import com.chaeda.chaeda.presentation.review.box.ProblemBoxActivity
import com.chaeda.chaeda.presentation.review.box.ProblemBoxAdapter
import com.chaeda.domain.entity.ReviewProblem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ReviewFolderActivity
    : BindingActivity<ActivityReviewFolderBinding>(R.layout.activity_review_folder){

    private lateinit var photoAdapter: ProblemBoxAdapter

    private val viewModel by viewModels<ReviewFolderViewModel>()
    private val id by longExtra(-1L)
    private val title by stringExtra()
    private val description by stringExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
        setTextChangedListener()
        observe()
    }

    private fun initView() {
        photoAdapter = ProblemBoxAdapter(false) {
            // click listener
        }

        binding.rvProblems.adapter = photoAdapter
        if (id != -1L) viewModel.getProblemsInFolder(id)
    }

    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener {
                finish()
            }

            if (id != -1L) {
                tvFab.text = "오답 폴더 수정하기"
                etTitle.text.insert(0, title)
                etContent.text.insert(0, description)
                tvDownload.visibility = View.VISIBLE
                empty.visibility = View.GONE
                tvDelete.visibility = View.VISIBLE

                tvDownload.setOnSingleClickListener {
                    viewModel.postMakeReviewPdf(id)
                    toast("생성 중")
                }

                fab.setOnSingleClickListener {

                }
            } else {
                tvFab.text = "오답 폴더 만들기"
                tvDownload.visibility = View.GONE
                tvDelete.visibility = View.INVISIBLE
                empty.visibility = View.VISIBLE

                fab.setOnSingleClickListener {
                    viewModel.postNewFolder()
                }
            }

            tvPhoto.setOnSingleClickListener {
                startActivityForResult(
                    ProblemBoxActivity.getIntent(this@ReviewFolderActivity, true),
                    REQUEST_CODE_PROBLEM_BOX_ACTIVITY
                )
            }
        }
    }

    private fun setTextChangedListener() {
        with(binding) {
            etTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateTitle(p0.toString())
                }
            })

            etContent.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateDescription(p0.toString())
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (id != -1L) viewModel.getProblemsInFolder(id)
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.reviewState.collect { state ->
                when (state) {
                    is ReviewState.PostNewFolderSuccess -> {
                        finish()
                    }
                    is ReviewState.GetProblemsInFolderSuccess -> {
                        photoAdapter.setItems(state.urls)
                    }
                    is ReviewState.PostMakeReviewPdfSuccess -> {
                        toast("PDF 생성이 완료되었습니다.\nPDF 저장소에서 확인해 주세요.")
                        // 아니면 액티비티로 이동해서 확인해도 ㄱㅊ을듯?
                    }
                    else -> { }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PROBLEM_BOX_ACTIVITY && resultCode == RESULT_OK) {
            data?.let {
                val arr = it.getSerializableExtra("selectedItems") as? Array<ReviewProblem>
                if (arr != null) {
                    val list = mutableListOf<Long>()
                    for (i in arr) {
                        list.add(i.reviewNoteProblemId!!)
                    }
                    viewModel.setProblemIds(list)
                    Timber.tag("chaeda-result").d("list: ${arr.toList()}")
                    photoAdapter.setItems(arr.toList())
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PROBLEM_BOX_ACTIVITY = 1001
        fun getIntent(context: Context, id: Long?, title: String?, description: String?) = Intent(context, ReviewFolderActivity::class.java).apply {
            putExtra("id", id)
            putExtra("title", title)
            putExtra("description", description)
        }
    }
}