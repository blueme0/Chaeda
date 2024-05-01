package com.chaeda.chaeda.presentation.homework.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.boolExtra
import com.chaeda.base.util.extension.intExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityHomeworkDetailBinding
import com.chaeda.chaeda.presentation.homework.result.HomeworkResultActivity
import com.chaeda.chaeda.presentation.homework.result.submit.ResultSubmitActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkDetailActivity
    : BindingActivity<ActivityHomeworkDetailBinding>(R.layout.activity_homework_detail) {

    private val id by intExtra()
    private val isDone by boolExtra()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
        setResultLauncher()
    }

    private fun setResultLauncher() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val isDoneNow = result.data?.getBooleanExtra("isDone", false)
                if (isDoneNow == true) {
                    binding.tvFab.text = "오답 확인하기"
                }
            }
        }
    }

    private fun initListener() {
        with(binding) {
            tvFab.text = if (isDone) "오답 확인하기" else "검사 시작하기"
            llBack.setOnSingleClickListener {
                finish()
            }
            fab.setOnSingleClickListener {
                if (isDone) startActivity(HomeworkResultActivity.getIntent(this@HomeworkDetailActivity))
                else {
//                    resultLauncher.launch(ConfirmSubmitActivity.getIntent(this@HomeworkDetailActivity))
                    startActivity(ResultSubmitActivity.getIntent(this@HomeworkDetailActivity))
                }
            }
        }
    }

    companion object {

        // 요청 코드 정의
        private const val REQUEST_CODE = 204

        // 액티비티 시작
        fun getIntent(context: Context, id: Int) = Intent(context, HomeworkDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        fun getIntent(context: Context, id: Int, isDone: Boolean) = Intent(context, HomeworkDetailActivity::class.java).apply {
            putExtra("id", id)
            putExtra("isDone", isDone)
        }
    }
}