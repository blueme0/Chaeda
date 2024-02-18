package com.chaeda.chaeda.presentation.homework

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentHomeworkBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class HomeworkFragment
    : BindingFragment<FragmentHomeworkBinding>(R.layout.fragment_homework) {

    private val viewModel by viewModels<HomeworkViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        sendFileObserver()
    }

    private fun initListener() {
        binding.tvPresign.setOnSingleClickListener {
            viewModel.getPresignedUrl(4)
        }
    }

    private fun testPostHomeworkImage(url: String) {
        // Example usage
        val resourceId = R.drawable.test_paper // Replace with your actual resource ID
        val bitmap = resourceToBitmap(requireContext(), resourceId)
        bitmap?.let {
            val fileName = "1.png" // Replace with your desired file name
            val file = bitmapToFile(requireContext(), it, fileName)
            // Now 'file' is your File object containing the PNG image
//            val tempImageFiles : ArrayList<File> = ArrayList<File>()
            if (file != null) {
//                tempImageFiles.add(file)
                viewModel.putFileToUrl(url, "image/png", file)
            }
        }
    }

    private fun sendFileObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.urlState.collect { state ->
                when (state) {
                    is FileState.UrlSuccess -> {
                        testPostHomeworkImage(state.url)
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