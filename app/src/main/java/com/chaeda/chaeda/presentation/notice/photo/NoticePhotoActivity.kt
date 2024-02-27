package com.chaeda.chaeda.presentation.notice.photo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityNoticePhotoBinding
import com.chaeda.chaeda.presentation.homework.result.photo.LoadImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticePhotoActivity
    : BindingActivity<ActivityNoticePhotoBinding>(R.layout.activity_notice_photo)  {

    private val viewpagerList = ArrayList<String>()
    private val imagesAdapter = LoadImagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent.getStringArrayListExtra("images")
        if (!intent.isNullOrEmpty()) viewpagerList.addAll(intent)
        initViewPagerItems()
        initViewPager()
        initListener()
    }

    private fun initViewPager() {
        imagesAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = imagesAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tvCount.text = "${position + 1}/${viewpagerList.size}장"
                }
            })
        }
    }

    private fun initListener() {
        with(binding) {
            ivLeft.setOnSingleClickListener {
                val currentIndex = tvCount.text.split("/")[0].toInt() - 1
                vp.setCurrentItem(currentIndex - 1, true)
            }
            ivRight.setOnSingleClickListener {
                val currentIndex = tvCount.text.split("/")[0].toInt() - 1
                vp.setCurrentItem(currentIndex + 1, true)
            }
            llBack.setOnSingleClickListener { finish() }
        }
    }

    private fun initViewPagerItems() {
        viewpagerList.add("a")
        viewpagerList.add("b")
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, NoticePhotoActivity::class.java)
        fun getIntent(context: Context, images: ArrayList<String>) = Intent(context, NoticePhotoActivity::class.java).apply {
            putExtra("images", images)
        }
    }
}