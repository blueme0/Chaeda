package com.chaeda.chaeda.presentation.statistics.chapter

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentStatisticsChapterBinding
import com.chaeda.chaeda.presentation.statistics.StatisticsFragment
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.enumSet.Subject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsChapterFragment
    : BindingFragment<FragmentStatisticsChapterBinding>(R.layout.fragment_statistics_chapter) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFD571")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun initListener() {
        with(binding) {
            ivBack.setOnSingleClickListener {
                navigateTo<StatisticsFragment>()
            }

            val subjects = Subject.values()
            var chapters = Subject.Math_high.chapters

            spinnerSubject.adapter = object : ArrayAdapter<Subject>(requireContext(), R.layout.item_spinner, subjects) {
                // getView() 메서드 오버라이드하여 원하는 속성을 표시
                override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    // TextView 가져오기 (안드로이드 기본 레이아웃의 ID는 `android.R.id.text1`)
                    val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                    // 한국어 이름을 텍스트로 설정
                    textView.text = subjects[position].koreanName
                    return view
                }

                // getDropDownView() 메서드 오버라이드하여 드롭다운 메뉴의 레이아웃 설정
                override fun getDropDownView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                    textView.text = subjects[position].koreanName
                    return view
                }
            }

            // Spinner에 OnItemSelectedListener 설정
            spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // 선택된 아이템의 텍스트 가져오기
                    val selectedItem = parent.getItemAtPosition(position) as Subject
                    chapters = selectedItem.chapters
                    resetSpinnerChapter(chapters)
                    spinnerChapter.setSelection(0)

//                    if (selectedItem != "학년 선택하기") signUpViewModel.updateGrade(selectedItem)
//                    else signUpViewModel.updateGrade("")
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // 아무 것도 선택되지 않은 경우 동작 (필요에 따라 구현 가능)
                }
            }

            // Spinner에 OnItemSelectedListener 설정
            spinnerChapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // 선택된 아이템의 텍스트 가져오기
                    val selectedItem = parent.getItemAtPosition(position) as Chapter
                    Toast.makeText(requireActivity(), "${selectedItem.koreanName}", Toast.LENGTH_SHORT).show()
//                    if (selectedItem != "학년 선택하기") signUpViewModel.updateGrade(selectedItem)
//                    else signUpViewModel.updateGrade("")
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // 아무 것도 선택되지 않은 경우 동작 (필요에 따라 구현 가능)
                }
            }
        }
    }

    private fun resetSpinnerChapter(chapters: List<Chapter>) {
        binding.spinnerChapter.adapter = object : ArrayAdapter<Chapter>(requireContext(), R.layout.item_spinner, chapters) {
            // getView() 메서드 오버라이드하여 원하는 속성을 표시
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                // TextView 가져오기 (안드로이드 기본 레이아웃의 ID는 `android.R.id.text1`)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                // 한국어 이름을 텍스트로 설정
                textView.text = chapters[position].koreanName
                return view
            }

            // getDropDownView() 메서드 오버라이드하여 드롭다운 메뉴의 레이아웃 설정
            override fun getDropDownView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                textView.text = chapters[position].koreanName
                return view
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFFFFF")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFD571")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        requireActivity().supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.java.canonicalName)
        }
    }


    companion object {

    }
}