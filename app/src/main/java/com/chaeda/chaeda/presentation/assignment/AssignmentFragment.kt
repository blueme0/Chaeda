package com.chaeda.chaeda.presentation.assignment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentAssignmentBinding
import com.chaeda.chaeda.presentation.assignment.add.AddAssignmentActivity
import com.chaeda.chaeda.presentation.assignment.calendar.WeekFragmentStateAdapter
import com.chaeda.chaeda.presentation.assignment.detail.AssignmentDetailActivity
import com.chaeda.chaeda.presentation.textbook.TextbookActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AssignmentFragment
    : BindingFragment<FragmentAssignmentBinding>(R.layout.fragment_assignment) {

    private val viewModel by activityViewModels<AssignmentViewModel>()
    private lateinit var homeworkAdapter: AssignmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initViewPager()
        initView()
        initHomeworkItems()
        observe()

        requireActivity().window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.parseColor("#FFD571")
//            decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun initListener() {
//        // activity 시작
//        binding.tvPresign.setOnSingleClickListener {
//            startActivity(Intent(requireContext(), ConfirmSubmitActivity::class.java))
//        }
        binding.tvFalse.setOnSingleClickListener {
//            startActivity(IncorrectCollectionActivity.getIntent(requireContext()))
            startActivity(TextbookActivity.getIntent(requireContext()))
        }
    }

    private fun initView() {
        homeworkAdapter = AssignmentAdapter {
            // click listener
            Timber.tag("chaeda-hw").d("homework: $it")
            startActivity(AssignmentDetailActivity.getIntent(requireContext(), it.id!!, it.isCompleted!!))
        }
        homeworkAdapter.setAddItemClick {
            startActivity(AddAssignmentActivity.getIntent(requireContext()))
        }

        binding.rvHomework.adapter = homeworkAdapter
    }

    private fun initHomeworkItems() {
        homeworkAdapter.setItems(null)
    }

    private fun initViewPager() {
        val fragmentStateAdapter = WeekFragmentStateAdapter(requireActivity())
        binding.vp.adapter = fragmentStateAdapter
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        fragmentStateAdapter.apply {
            binding.vp.setCurrentItem(this.firstFragmentPosition, false)
        }
    }

    private fun convertDateString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        // Date 객체를 지정된 형식의 문자열로 포맷팅
        return dateFormat.format(date)
    }

    private fun convertDateToLocalDate(date: Date): LocalDate {
        // Date 객체를 Instant 객체로 변환
        val instant = date.toInstant()

        // 한국 시간대 (Asia/Seoul)를 ZoneId로 지정
        val zoneId = ZoneId.of("Asia/Seoul")

        // Instant 객체를 한국 시간대에 맞게 LocalDate 객체로 변환
        return instant.atZone(zoneId).toLocalDate()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.selectedDate.collect { date ->
                viewModel.getAssignmentsByDate(convertDateString(date), convertDateToLocalDate(date))
            }
        }
        
        lifecycleScope.launch { 
            viewModel.assignmentState.collect { state ->
                when (state) {
                    is AssignmentState.GetListSuccess -> {
                        homeworkAdapter.setItems(state.list)
                    }
                    else -> {
                    }
                            
                }
                
            }
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

        // TODO : 과제 등록 시 해당 날짜에 있을 때 화면 refresh 필요 (혹은 notifyDatasetChanged) -> 서버 연결하면 확인 필요
        val date = viewModel.selectedDate.value
        viewModel.getAssignmentsByDate(convertDateString(date), convertDateToLocalDate(date))
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

    companion object {
        private const val HOMEWORK_TITLE = "문제집 이름"
        private const val HOMEWORK_CONTENT = "선생님이 작성한 본문 내용이 여기에 표시됩니다."
    }
}