package com.chaeda.chaeda.presentation.textbook.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddTextbookBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddTextbookActivity
    : BindingActivity<ActivityAddTextbookBinding>(R.layout.activity_add_textbook) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initListener()
        observe()
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }

    private fun getFileName(uri: Uri): String {
        var name = ""
        val returnCursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        returnCursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                it.moveToFirst()
                name = it.getString(nameIndex)
            }
        }
        return name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                // Handle the PDF file URI
//                uploadPdfToServer(uri)
                val inputStream = contentResolver.openInputStream(uri) ?: return
                val file = File(cacheDir, getFileName(uri))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()

                val fileSizeInBytes = file.length()
                val fileSizeInKb = fileSizeInBytes / 1000

                Timber.tag("chaeda-file").d("name: ${file.name}")
                Timber.tag("chaeda-file").d("size: $fileSizeInBytes bytes")
                Timber.tag("chaeda-file").d("size: $fileSizeInKb kb")

            }
        }
    }

    private fun initView() {

    }

    private fun initListener() {
        with(binding) {
            tvAddFile.setOnSingleClickListener {
                openFilePicker()
            }
        }

    }

    private fun observe() {

    }

    companion object {
        private const val PICK_PDF_FILE = 2
        fun getIntent(context: Context) = Intent(context, AddTextbookActivity::class.java)
    }
}