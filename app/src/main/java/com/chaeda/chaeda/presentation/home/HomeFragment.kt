package com.chaeda.chaeda.presentation.home

import android.os.Bundle
import android.view.View
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentHomeBinding
import com.chaeda.chaeda.presentation.homework.detail.HomeworkDetailActivity
import com.chaeda.chaeda.presentation.notice.NoticeListActivity
import com.chaeda.chaeda.presentation.notice.detail.NoticeDetailActivity
import com.chaeda.chaeda.presentation.setting.SettingActivity
import com.chaeda.domain.entity.Homework
import com.chaeda.domain.entity.Notice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment
    : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeworkAdapter: TodayHomeworkAdapter
    private lateinit var noticeAdapter: RecentNoticeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHomeworkItems()
        initNoticeItems()
        initListener()
    }

    private fun initView() {
        homeworkAdapter = TodayHomeworkAdapter {
            // click listener
            startActivity(HomeworkDetailActivity.getIntent(requireContext(), 0, it.isDone))
        }

        noticeAdapter = RecentNoticeAdapter {
            // click listener
            startActivity(NoticeDetailActivity.getIntent(requireContext(), 0))
        }

        binding.rvHomeToday.adapter = homeworkAdapter
        binding.rvHomeNotice.adapter = noticeAdapter
    }

    private fun initListener() {
        with(binding) {
            ivHomeMorenotice.setOnSingleClickListener {
                startActivity(NoticeListActivity.getIntent(requireContext()))
            }
            ivHomeSetting.setOnSingleClickListener {
                startActivity(SettingActivity.getIntent(requireContext()))
            }
        }
    }

    private fun initHomeworkItems() {
        homeworkAdapter.setItems(
            listOf(
                Homework(HOMEWORK_TITLE, HOMEWORK_CONTENT, listOf("")),
                Homework(HOMEWORK_TITLE, HOMEWORK_CONTENT, listOf("")),
                Homework(HOMEWORK_TITLE, HOMEWORK_CONTENT, listOf(""))
            )
        )
    }

    private fun initNoticeItems() {
        noticeAdapter.setItems(
            listOf(
                Notice("전체", "학원 전체 공지사항", "2024-02-22T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-21T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-20T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-19T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-18T15:30:45")
            )
        )
    }

    companion object {
        private const val HOMEWORK_TITLE = "문제집 이름"
        private const val HOMEWORK_CONTENT = "선생님이 작성한 본문 내용이 여기에 표시됩니다."
    }
}