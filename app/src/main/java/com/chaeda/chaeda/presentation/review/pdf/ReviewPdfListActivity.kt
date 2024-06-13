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
import com.chaeda.domain.entity.ReviewPdf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
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
            llBack.setOnSingleClickListener {
                finish()
            }

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
//            viewModel.getReviewPdf(it.id)
            urlDownloading(TEST_DOWNLOAD_PDF)
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
                        pdfAdapter.setItems(listOf(ReviewPdf(1L, "review", "2024-06-12")))
                    }
                    is ReviewState.GetReviewPdfSuccess -> {
//                        urlDownloading(state.url)
                        urlDownloading(TEST_DOWNLOAD_PDF)
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
                Timber.tag("chaeda-download").d("columnReason : $columnReason")
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
        Timber.tag("chaeda-download").d("urlDownloading: $url")
        if (mDownloadManager == null) {
            mDownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        }
//        val outputFile = File(outputFilePath + getFileName(url))
        val outputFile = File(outputFilePath + "review.pdf")
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs()
        }
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
//        request.setTitle(getFileName(url))
        request.setTitle("review.pdf")
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
//        private const val TEST_DOWNLOAD_PDF = "https://drive.google.com/file/d/1wHbsqcfI9E9NRMuLgh4DYYcDHaba3xqN/view?usp=drive_link"
//        private const val TEST_DOWNLOAD_PDF = "https://bigfile.mail.naver.com/download?fid=PqR0W6k9WzU9KAujK3ejFAMlKxEjKogZFAgrKxUmKAg/KxvjKxbZKAblFoKla3e4KoMZKrMqKzumK4UmMouXp6MdF6pSK6JCF6UrpzMdM2=="
        private const val TEST_DOWNLOAD_PDF = "https://bigfile.mail.naver.com/download?fid=P9R0W6k9WzU9aAujK3ejaxu9FAbjKogZFAgrKxUmKxUwKAujKxbZKAblFoKla3YrKogqKxU/KA3Sp6tmFrKrFrElK4ulax3CFo2lFxK9pt=="
    }
}