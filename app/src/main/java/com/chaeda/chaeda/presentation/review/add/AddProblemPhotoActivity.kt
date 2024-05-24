package com.chaeda.chaeda.presentation.review.add

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
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

    private var subjects: Array<Subject>? = null
    private var subject: Subject? = null
    private var chapters: List<Chapter>? = null
    private var chapter: Chapter? = null
    private var concepts: List<Concept>? = null
    private var concept: Concept? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subjects = Subject.values()
        subject = subjects!![0]
        chapters = subject!!.chapters
        chapter = chapters!![0]
        concepts = chapter!!.concepts
        concept = concepts!![0]

        initListener()
        initAddBtn()
    }

    private fun initAddBtn() {
        with(binding) {
            tvPhoto.setOnSingleClickListener {
                if (ContextCompat.checkSelfPermission(this@AddProblemPhotoActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@AddProblemPhotoActivity, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CODE)
                } else {
                    Log.d("chaeda-photo", "called")
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    Log.d("chaeda-photo", "${intent.resolveActivity(packageManager)}")
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_CODE)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as Bitmap
            binding.ivPhoto.load(bitmap)
        }
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
                    if (parent != null) {
                        subject = parent.getItemAtPosition(position) as Subject
                        chapters = subject!!.chapters
                        resetSpinnerChapter(subject)
                        spinnerChapter.setSelection(0)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinnerChapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0 != null) {
                        chapter = p0.getItemAtPosition(p2) as Chapter
                        concepts = chapter!!.concepts
                        resetSpinnerConcept(chapter)
                        spinnerConcept.setSelection(0)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

            spinnerConcept.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0 != null) {
                        concept = p0.getItemAtPosition(p2) as Concept
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
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
        private const val REQUEST_IMAGE_CODE = 101
    }
}