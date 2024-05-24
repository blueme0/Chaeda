package com.chaeda.chaeda.presentation.review.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddProblemPhotoBinding
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.enumSet.Concept
import com.chaeda.domain.enumSet.Subject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProblemPhotoActivity
    : BindingActivity<ActivityAddProblemPhotoBinding>(R.layout.activity_add_problem_photo) {

    private val viewModel by viewModels<AddProblemViewModel>()

    private val subjects = Subject.values()
    private var subject: Subject? = null
//    private var chapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {
            ivBack.setOnSingleClickListener {
                finish()
            }

            resetSpinnerChapter(null)
            resetSpinnerConcept(null)

            spinnerSubject.adapter = object : ArrayAdapter<Subject>(this@AddProblemPhotoActivity, R.layout.item_spinner, Subject.values()) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                    textView.text = Subject.values()[position].koreanName
                    return view
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                    textView.text = Subject.values()[position].koreanName
                    return view
                }
            }

            spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
//                    subject = parent.getItemAtPosition(position) as Subject
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    private fun resetSpinnerChapter(subject: Subject?) {
        binding.spinnerChapter.isEnabled = subject != null
        if (subject == null) return
        binding.spinnerChapter.adapter = object : ArrayAdapter<Chapter>(this, R.layout.item_spinner, subject.chapters) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                textView.text = subject.chapters[position].koreanName
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                textView.text = subject.chapters[position].koreanName
                return view
            }
        }

    }

    private fun resetSpinnerConcept(chapter: Chapter?) {
        binding.spinnerConcept.isEnabled = chapter != null
        if (chapter == null) return
        binding.spinnerConcept.adapter = object : ArrayAdapter<Concept>(this, R.layout.item_spinner, chapter.concepts) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                textView.text = chapter.concepts[position].koreanName
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                textView.text = chapter.concepts[position].koreanName
                return view
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, AddProblemPhotoActivity::class.java)
    }
}