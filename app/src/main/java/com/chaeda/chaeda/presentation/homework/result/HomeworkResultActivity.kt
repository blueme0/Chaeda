package com.chaeda.chaeda.presentation.homework.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityHomeworkResultBinding
import com.chaeda.chaeda.presentation.homework.result.answer.ResultAnswerFragment
import com.chaeda.chaeda.presentation.homework.result.classmate.ResultClassmateFragment
import com.chaeda.chaeda.presentation.homework.result.photo.ResultPhotoFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeworkResultActivity
    : BindingActivity<ActivityHomeworkResultBinding>(R.layout.activity_homework_result) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTabLayout()
        initListener()
    }

    private fun setTabLayout() {
        supportFragmentManager.findFragmentById(R.id.fcv_result) ?: navigateTo<ResultAnswerFragment>()

        binding.apply {
            tlResult.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) { }
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        when (tab.position) {
                            0 -> navigateTo<ResultAnswerFragment>()
                            1 -> navigateTo<ResultPhotoFragment>()
                            2 -> navigateTo<ResultClassmateFragment>()
                        }
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) { }
            })
        }
    }

    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener { finish() }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, HomeworkResultActivity::class.java).apply {
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_result, T::class.java.canonicalName)
        }
    }

}