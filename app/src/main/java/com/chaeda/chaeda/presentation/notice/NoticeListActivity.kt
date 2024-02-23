package com.chaeda.chaeda.presentation.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityNoticeListBinding
import com.chaeda.chaeda.presentation.home.RecentNoticeAdapter
import com.chaeda.chaeda.presentation.notice.detail.NoticeDetailActivity
import com.chaeda.domain.entity.Notice
import timber.log.Timber

class NoticeListActivity
    : BindingActivity<ActivityNoticeListBinding>(R.layout.activity_notice_list) {

    private lateinit var noticeAdapter: RecentNoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initNoticeItems()
        initListener()
    }

    private fun initView() {
        noticeAdapter = RecentNoticeAdapter {
            // click listener
            startActivity(NoticeDetailActivity.getIntent(this@NoticeListActivity, 0))
        }

        binding.rvNotice.adapter = noticeAdapter
    }

    private fun initListener() {
        with(binding) {
            ivBack.setOnSingleClickListener { finish() }
        }
    }

    private fun initNoticeItems() {
        Timber.tag("chaeda-items").d("initNoticeItems called")
        noticeAdapter.setItems(
            listOf(
                Notice("전체", "학원 전체 공지사항", "2024-02-23T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-22T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-21T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-20T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-19T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-18T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-17T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-16T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-15T15:30:45"),
                Notice("전체", "학원 전체 공지사항", "2024-02-14T15:30:45")
            )
        )
        Timber.tag("chaeda-items").d("initNoticeItems : ${noticeAdapter.itemCount}")
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, NoticeListActivity::class.java)
    }
}