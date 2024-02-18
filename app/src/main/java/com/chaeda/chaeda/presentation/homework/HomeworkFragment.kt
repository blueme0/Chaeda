package com.chaeda.chaeda.presentation.homework

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentHomeworkBinding
import com.chaeda.chaeda.presentation.homework.submit.ConfirmSubmitActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkFragment
    : BindingFragment<FragmentHomeworkBinding>(R.layout.fragment_homework) {

    private val viewModel by viewModels<HomeworkViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        // activity 시작
        binding.tvPresign.setOnSingleClickListener {
            startActivity(Intent(requireContext(), ConfirmSubmitActivity::class.java))
        }
    }
}