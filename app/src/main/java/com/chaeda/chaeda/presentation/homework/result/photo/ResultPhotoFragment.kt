package com.chaeda.chaeda.presentation.homework.result.photo

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentResultPhotoBinding
import com.chaeda.chaeda.presentation.homework.submit.ConfirmSubmitAdapter
import java.io.File

class ResultPhotoFragment
    : BindingFragment<FragmentResultPhotoBinding>(R.layout.fragment_result_photo) {

    private val introduceAdapter = ConfirmSubmitAdapter()
    private val viewpagerList = ArrayList<File>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initListener()
        initViewPagerItems()
    }

    private fun initViewPagerItems() {
        viewpagerList.add(File(""))
        viewpagerList.add(File(""))
        viewpagerList.add(File(""))
    }

    private fun initViewPager() {
        introduceAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = introduceAdapter
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
                vp.currentItem = currentIndex - 1
            }
            ivRight.setOnSingleClickListener {
                val currentIndex = tvCount.text.split("/")[0].toInt() - 1
                vp.currentItem = currentIndex + 1
            }
        }
    }
}