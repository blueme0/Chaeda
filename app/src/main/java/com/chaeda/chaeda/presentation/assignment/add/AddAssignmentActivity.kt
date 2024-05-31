package com.chaeda.chaeda.presentation.assignment.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddAssignmentBinding
import com.chaeda.chaeda.presentation.assignment.AssignmentState
import com.chaeda.chaeda.presentation.textbook.list.TextbookListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAssignmentActivity
    : BindingActivity<ActivityAddAssignmentBinding>(R.layout.activity_add_assignment) {

    private val addAssignmentViewModel by viewModels<AddAssignmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        initListener()
        setTextChangedListener()
        observe()
    }

    private fun initListener() {
        with(binding) {
            fab.setOnSingleClickListener {
                Log.d("chaeda-post", "title: ${addAssignmentViewModel.title.value}")
                Log.d("chaeda-post", "startRange: ${addAssignmentViewModel.startRange.value}")
                Log.d("chaeda-post", "endRange: ${addAssignmentViewModel.endRange.value}")
                Log.d("chaeda-post", "due: ${addAssignmentViewModel.due.value}")
                Log.d("chaeda-post", "textbookId: ${addAssignmentViewModel.textbookId.value}")

                if (addAssignmentViewModel.assignmentValid.value) {
                    addAssignmentViewModel.postAssignment()
                }
                else {
                    Toast.makeText(this@AddAssignmentActivity, "입력을 완료해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            etBook.setOnSingleClickListener {
                startActivityForResult(TextbookListActivity.getIntent(this@AddAssignmentActivity), REQUEST_CODE_TEXTBOOK_LIST_ACTIVITY)
            }
        }
    }

    private fun setTextChangedListener() {
        with(binding) {
            etTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    addAssignmentViewModel.updateTitle(p0.toString())
                }
            })

            etDeadline.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    addAssignmentViewModel.updateDueDate(p0.toString())
                }
            })

            etRangeStart.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var p0Int = 0
                    try {
                        p0Int = p0.toString().toInt()
                    } catch (e: NumberFormatException) {
                        p0Int = addAssignmentViewModel.startRange.value
                    } finally {
                        addAssignmentViewModel.updateRange(
                            p0Int,
                            addAssignmentViewModel.endRange.value
                        )
                    }
                }
            })

            etRangeEnd.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    var p0Int = 0
                    try {
                        p0Int = p0.toString().toInt()
                    } catch (e: NumberFormatException) {
                        p0Int = addAssignmentViewModel.endRange.value
                    } finally {
                        addAssignmentViewModel.updateRange(
                            addAssignmentViewModel.startRange.value,
                            p0Int
                        )
                    }
                }
            })
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            addAssignmentViewModel.assignmentState.collect { state ->
                when (state) {
                    is AssignmentState.UploadSuccess -> {
                        finish()
                    }
                    else -> { }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_TEXTBOOK_LIST_ACTIVITY && resultCode == RESULT_OK) {
            data?.let {
                val bookId = it.getIntExtra("textbookId", 0)
                val bookName = it.getStringExtra("textbookName")
                if (bookName != null) {
                    addAssignmentViewModel.updateTextbookId(bookId)
                    addAssignmentViewModel.updateTextbook(bookName)
                    with(binding) {
                        etBook.text = bookName
                    }
                }
                val startPage = it.getIntExtra("startPage", 0)
                val lastPage = it.getIntExtra("lastPage", 0)
                if (startPage != 0 && lastPage != 0) {
                    with(binding) {
                        addAssignmentViewModel.updatePageLimit(startPage, lastPage)
                        tvRangeInformNum.text = "${startPage}p ~ ${lastPage}p"
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_TEXTBOOK_LIST_ACTIVITY = 1001
        fun getIntent(context: Context) = Intent(context, AddAssignmentActivity::class.java)
    }
}