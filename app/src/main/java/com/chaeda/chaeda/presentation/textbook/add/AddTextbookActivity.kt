package com.chaeda.chaeda.presentation.textbook.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.toast
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddTextbookBinding
import com.chaeda.chaeda.presentation.textbook.TextbookState
import com.chaeda.domain.enumSet.Subject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddTextbookActivity
    : BindingActivity<ActivityAddTextbookBinding>(R.layout.activity_add_textbook) {

    private val viewModel by viewModels<AddTextbookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
        setTextChangedListener()
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
                viewModel.updateFile(file)
                binding.tvTextbookFile.text = file.name
            }
        }
    }

    private fun initListener() {
        with(binding) {
            tvAddFile.setOnSingleClickListener {
                openFilePicker()
            }

            val grades = listOf<String>(
                "학년 선택하기", "N수", "고1", "고2", "고3", "중1", "중2", "중3", "초1", "초2", "초3", "초4", "초5", "초6"
            )

            binding.spinnerGrade.adapter = ArrayAdapter<String>(this@AddTextbookActivity, R.layout.item_spinner, grades)
            // Spinner에 OnItemSelectedListener 설정
            binding.spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                    // 선택된 아이템의 텍스트 가져오기
                    val selectedItem = parent.getItemAtPosition(position) as String
                    if (selectedItem != "학년 선택하기") viewModel.updateGrade(selectedItem)
                    else viewModel.updateGrade("")
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // 아무 것도 선택되지 않은 경우 동작 (필요에 따라 구현 가능)
                }
            }

            val subjects = Subject.values()
            val koreanSubjects = mutableListOf<String>()
            koreanSubjects.add("과목 선택하기")
            for (sub in subjects) {
                koreanSubjects.add(sub.koreanName)
            }

            binding.spinnerSubject.adapter = ArrayAdapter<String>(this@AddTextbookActivity, R.layout.item_spinner, koreanSubjects)
            binding.spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selectedItem = p0?.getItemAtPosition(p2) as String
                    if (selectedItem != "과목 선택하기") viewModel.updateSubject(selectedItem)
                    else viewModel.updateSubject("")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

            fab.setOnSingleClickListener {
                viewModel.getPresignedUrlForTextbook()
                showLoading()
            }
        }

    }

    private fun setTextChangedListener() {
        with(binding) {
            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateName(p0.toString())
                }
            })

            etPublisher.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updatePublisher(p0.toString())
                }
            })

            etYear.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updatePublishedYear(p0.toString())
                }
            })

            etRangeStart.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.toString().isDigitsOnly())
                        viewModel.updateRange(p0.toString().toInt(), viewModel.endRange.value)
                }
            })

            etRangeEnd.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.toString().isDigitsOnly())
                        viewModel.updateRange(viewModel.startRange.value, p0.toString().toInt())
                }
            })
        }
    }

    private fun showLoading() {
        with(binding) {
            bgLoading.visibility = View.VISIBLE
            tvLoading.visibility = View.VISIBLE
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.textbookState.collect { state ->
                when (state) {
                    is TextbookState.UploadTextbookSuccess -> {
                        toast("교재를 업로드 했습니다.")
                        finish()
                    }
                    is TextbookState.Failure -> {
                        Timber.tag("chaeda-pdf").d(state.msg)
                        toast(state.msg)
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        private const val PICK_PDF_FILE = 2
        fun getIntent(context: Context) = Intent(context, AddTextbookActivity::class.java)
    }
}