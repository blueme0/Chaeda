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
                Homework("$HOMEWORK_TITLE 1", HOMEWORK_CONTENT, listOf("https://i.namu.wiki/i/nXtowZQG8EcHp9eGH8M7yP5a43Ho01PXm97UT5iah1vsvRNqyL8DuokA46-Gh85bBeP0uREHRWYVHsZgtzLScQ.webp")),
                Homework("$HOMEWORK_TITLE 2", HOMEWORK_CONTENT, listOf("https://lh3.googleusercontent.com/proxy/NBaH02XGRMm5kIHjLaA2ej1ms-o-Vjzm0ccu46r-W76x0saLswinVJfY0puvEDdaTpSaZ-1uO6fxo8fXO2RzG_xooZoen4XgJExJp-siW1ie6anDj1BJPNqlnT22s73xco8aks2Ie9bFM4gZQ4weveuv8yi9tA")),
                Homework("$HOMEWORK_TITLE 2", HOMEWORK_CONTENT, listOf("https://blog.kakaocdn.net/dn/IwJkz/btqV355Nt9X/ehKnbamCdVPKTjmaKxVIj0/img.png"))
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