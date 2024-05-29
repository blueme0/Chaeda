package com.chaeda.chaeda.presentation.review.pdf

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityReviewPdfListBinding
import com.chaeda.chaeda.presentation.review.add.ReviewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL


@AndroidEntryPoint
class ReviewPdfListActivity
    : BindingActivity<ActivityReviewPdfListBinding>(R.layout.activity_review_pdf_list) {

    private val viewModel by viewModels<ReviewPdfListViewModel>()
    private lateinit var pdfAdapter: PdfListAdapter

    private var mDownloadManager: DownloadManager? = null
    private var mDownloadQueueId: Long = 0L
    private val outputFilePath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/chaeda")
            .toString() + "/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
        initView()
        observe()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    urlDownloading(TEST_DOWNLOAD_PDF)
                } else {
                    Toast.makeText(this, "Write External Storage permission is required to download files", Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                // Optional: Handle notification permission request result if needed.
            }
        }
    }

    private fun initListener() {
        with(binding) {
            tvTitle.setOnSingleClickListener {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
                    } else {
                        Log.d("chaeda-pdf", "permission post is okay")
                        urlDownloading(TEST_DOWNLOAD_PDF)

                    }
                } else {
                    Log.d("chaeda-pdf", "version is okay")

                    urlDownloading(TEST_DOWNLOAD_PDF)
                }
            }
        }
    }

    private fun initView() {
        pdfAdapter = PdfListAdapter {
            viewModel.getReviewPdf(it.id)
        }

        binding.rvPdf.adapter = pdfAdapter

//        viewModel.getReviewPdfList()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.reviewState.collect { state ->
                when (state) {
                    is ReviewState.GetReviewPdfListSuccess -> {
                        pdfAdapter.setItems(state.list)
                    }
                    is ReviewState.GetReviewPdfSuccess -> {
                        urlDownloading(state.url)
                    }
                    else -> { }
                }
            }
        }
    }

    private fun getFileName(urlString: String): String {
        // Parse the URL
        val url = URL(urlString)
        val path = url.path

        // Get the file name
        return path.substring(path.lastIndexOf("/") + 1)
    }

    private val downloadCompleteReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (mDownloadQueueId == reference) {
                val query = DownloadManager.Query() // 다운로드 항목 조회에 필요한 정보 포함
                query.setFilterById(reference)
                val cursor: Cursor = mDownloadManager!!.query(query)
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val columnReason: Int = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
                val status: Int = cursor.getInt(columnIndex)
                cursor.close()
                when (status) {
                    DownloadManager.STATUS_SUCCESSFUL -> Toast.makeText(
                        this@ReviewPdfListActivity,
                        "다운로드를 완료하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    DownloadManager.STATUS_PAUSED -> Toast.makeText(
                        this@ReviewPdfListActivity,
                        "다운로드가 중단되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    DownloadManager.STATUS_FAILED -> Toast.makeText(
                        this@ReviewPdfListActivity,
                        "다운로드가 취소되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun urlDownloading(url: String) {
        if (mDownloadManager == null) {
            mDownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        }
        val outputFile = File(outputFilePath + getFileName(url))
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs()
        }
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setTitle(getFileName(url))
        request.setDestinationUri(Uri.fromFile(outputFile))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setAllowedOverMetered(true)
        mDownloadQueueId = mDownloadManager!!.enqueue(request)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReviewPdfList()
        val completeFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(downloadCompleteReceiver, completeFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(downloadCompleteReceiver, completeFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(downloadCompleteReceiver)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ReviewPdfListActivity::class.java)
        private const val TEST_DOWNLOAD_PDF = "https://s3-fullaccel.s3.ap-northeast-2.amazonaws.com/s3test.pdf?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEFkaDmFwLW5vcnRoZWFzdC0yIkcwRQIgQeJMXDrTVUmXe8zZe8FYa9%2BczXT4Cl0t3ExgrrP9Q0kCIQC%2FLaQmcKxHthqjv%2F2ZNFTs1CtH9aZNAZbefGfbO2kAYCrxAgjS%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F8BEAAaDDc2NzM5NzkyNjYyNyIM9f%2FAyWxfC027oKpOKsUCgk6D0JnmC%2Fbx048z%2B7ZKXwc63q7Ee72jpzxCn4thqMX0dnPL2Dp3T46VJyHHhxwpXZs4WDfCztgD9YxRPXEAp6aVGGH%2FsO76lVT6igvjBdzr1aZ531njYubJZR4ISVXa%2ByoFT4WE%2FkhxbP14M88GvVZRHjMtGO03oJ4ur3ZaeLB04f4qMoisLcaEGKTjXfnmp9F8audPE%2FXXmJJwdwNTGDQHKcIREUK%2BMTgMSQFXyen%2FsXBq1NTBJfNw6v97sW6u1MoMJh%2BuVhMSMLx0bb7w%2BXOyMvXscu4850zEpMM8uRogZl%2B2e4ATbFqWx98TGK%2FP9Cvvv9NO0HBpmIadMdlYf9k2%2BfYwuvnWjX3xZhvZ6x7%2FDAMkYJ56Elay2StfRaXHXlzLVuy2%2FEALzlomgIhlreZ8c%2FrZmCzqWZY2OPgo2b0RblFQ7DDsjNGyBjqzAtE4E968FGp%2B4oip%2FrQKNFqQoubt%2FXx2ub9sigWWAnK6yUAnZL1S25BNNFYTuuOJ0g5LOz2s0RNdAQHYrnh29HtRjhadq6TJF022eYc8yUKb9a80WXdLfrGl%2FT8kl4b9HaNESLYUcQ6dMdufDN9ka%2Fg5Nx22G6vBm540bS3P3fmWBLvvDHWCUwY%2F%2BPRrEZgHYP7Kw80y3Js69tZGYJzJdo%2FSRQuoiq3RX7yCXbfxJbVWbQOXRRnC%2FO6X4sJuhTL6KQLUhkNcBBaSRjyZlyEM95gmAyPxqDjtvCe1R3CfBv%2B2BIfspcuv4QterAX3oD09n0nrT8%2BEzzoy7xrY0SUY1U6%2Fwf2CpxQIbWbndYNOh5e6dVZzb5t%2FOP0SG7cS8rgVX%2FdcnTGIXQVb80x5uBXwkn5cXIs%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240527T094435Z&X-Amz-SignedHeaders=host&X-Amz-Expires=7200&X-Amz-Credential=ASIA3FLD3C3RXLOIJY7W%2F20240527%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=35adc1687804d93e07dbf18aa6c4df4601d3f0ec15ca675283cdc2f5d35b490a"
    }
}