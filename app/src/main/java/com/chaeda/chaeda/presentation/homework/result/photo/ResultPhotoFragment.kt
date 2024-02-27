package com.chaeda.chaeda.presentation.homework.result.photo

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentResultPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultPhotoFragment
    : BindingFragment<FragmentResultPhotoBinding>(R.layout.fragment_result_photo) {

    private val imagesAdapter = LoadImagesAdapter()
    private val viewpagerList = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPagerItems()
        initViewPager()
        initListener()
    }

    private fun initViewPagerItems() {
        viewpagerList.add("")
        viewpagerList.add("")
        viewpagerList.add("")
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
        }
    }
}