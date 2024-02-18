package com.chaeda.chaeda.presentation.homework.submit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityConfirmSubmitBinding
import com.chaeda.chaeda.presentation.homework.FileState
import com.chaeda.chaeda.presentation.homework.HomeworkViewModel
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ConfirmSubmitActivity
    : BindingActivity<ActivityConfirmSubmitBinding>(R.layout.activity_confirm_submit) {

    private val introduceAdapter = ConfirmSubmitAdapter()
    private val viewpagerList = ArrayList<File>()
    private val viewModel by viewModels<HomeworkViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        initListener()
        addFileToViewPager(5)
        sendFileObserver()
    }

    private fun initViewPager() {
        introduceAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = introduceAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun initListener() {
        with(binding) {
            tvSubmit.setOnSingleClickListener {
                viewModel.getPresignedUrl(viewpagerList.size, 4)

            }
        }
    }

    private fun addFileToViewPager(count: Int) {
        repeat(count) {
            addFileToList()
        }
        introduceAdapter.notifyDataSetChanged()
    }


    private fun addFileToList() {
        val resourceId = R.drawable.test_paper // Replace with your actual resource ID
        val bitmap = resourceToBitmap(this, resourceId)
        bitmap?.let {
            val fileName = "1.png" // Replace with your desired file name
            val file = bitmapToFile(this, it, fileName)
            // Now 'file' is your File object containing the PNG image
//            val tempImageFiles : ArrayList<File> = ArrayList<File>()
            if (file != null) {
//                tempImageFiles.add(file)
                viewpagerList.add(file)
            }
        }
    }

    private fun testPostHomeworkImage(url: String, file: File) {
        // Example usage
//        for (file in viewpagerList) {
//        }
        viewModel.putFileToUrl(url, "image/png", file)

    }

    private fun sendFileObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.urlState.collect { state ->
                when (state) {
                    is FileState.UrlSuccess -> {
                        val urlList = state.url
                        for (i in urlList.indices) {
                            testPostHomeworkImage(urlList[i].presigendUrl, viewpagerList[i])
                        }
                    }
                    is FileState.Failure -> {
                    }
                    is FileState.FileSuccess -> {
                        Timber.tag("chaeda-pre").d("FileState is FileSuccess\n${state.url}")
                    }
                    else -> {}
                }
            }
        }
    }

    private fun resourceToBitmap(context: Context, resId: Int): Bitmap? {
        return BitmapFactory.decodeResource(context.resources, resId)
    }

    private fun bitmapToFile(context: Context, bitmap: Bitmap, fileName: String): File? {
        // Get the directory for the app's private pictures directory.
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)

        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return file
    }
}