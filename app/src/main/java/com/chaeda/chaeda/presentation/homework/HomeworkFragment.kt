package com.chaeda.chaeda.presentation.homework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingFragment
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.FragmentHomeworkBinding
import com.chaeda.chaeda.presentation.home.TodayHomeworkAdapter
import com.chaeda.chaeda.presentation.homework.calendar.WeekFragmentStateAdapter
import com.chaeda.chaeda.presentation.homework.collection.IncorrectCollectionActivity
import com.chaeda.chaeda.presentation.homework.detail.HomeworkDetailActivity
import com.chaeda.domain.entity.Homework
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeworkFragment
    : BindingFragment<FragmentHomeworkBinding>(R.layout.fragment_homework) {

    private val viewModel by activityViewModels<HomeworkViewModel>()
    private lateinit var homeworkAdapter: TodayHomeworkAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initViewPager()
        initView()
        initHomeworkItems()
    }

    private fun initListener() {
//        // activity 시작
//        binding.tvPresign.setOnSingleClickListener {
//            startActivity(Intent(requireContext(), ConfirmSubmitActivity::class.java))
//        }
        binding.tvFalse.setOnSingleClickListener {
            startActivity(IncorrectCollectionActivity.getIntent(requireContext()))
        }
    }

    private fun initView() {
        homeworkAdapter = TodayHomeworkAdapter {
            // click listener
            Timber.tag("chaeda-hw").d("homework: $it")
            startActivity(HomeworkDetailActivity.getIntent(requireContext(), 0, it.isDone))
        }

        binding.rvHomework.adapter = homeworkAdapter
    }

    private fun initHomeworkItems() {
        homeworkAdapter.setItems(
            listOf(
                Homework("$HOMEWORK_TITLE 1", HOMEWORK_CONTENT, listOf("https://i.namu.wiki/i/nXtowZQG8EcHp9eGH8M7yP5a43Ho01PXm97UT5iah1vsvRNqyL8DuokA46-Gh85bBeP0uREHRWYVHsZgtzLScQ.webp"), isDone = false),
                Homework("$HOMEWORK_TITLE 2", HOMEWORK_CONTENT, listOf("https://lh3.googleusercontent.com/proxy/NBaH02XGRMm5kIHjLaA2ej1ms-o-Vjzm0ccu46r-W76x0saLswinVJfY0puvEDdaTpSaZ-1uO6fxo8fXO2RzG_xooZoen4XgJExJp-siW1ie6anDj1BJPNqlnT22s73xco8aks2Ie9bFM4gZQ4weveuv8yi9tA"), isDone = true),
                Homework("$HOMEWORK_TITLE 3", HOMEWORK_CONTENT, listOf("https://blog.kakaocdn.net/dn/IwJkz/btqV355Nt9X/ehKnbamCdVPKTjmaKxVIj0/img.png"))
            )
        )
    }

    private fun initViewPager() {
        val fragmentStateAdapter = WeekFragmentStateAdapter(requireActivity())
        binding.vp.adapter = fragmentStateAdapter
        binding.vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        fragmentStateAdapter.apply {
            binding.vp.setCurrentItem(this.firstFragmentPosition, false)
        }
    }

    companion object {
        private const val HOMEWORK_TITLE = "문제집 이름"
        private const val HOMEWORK_CONTENT = "선생님이 작성한 본문 내용이 여기에 표시됩니다."
    }
}