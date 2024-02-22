package com.chaeda.chaeda.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemRecentNoticeBinding
import com.chaeda.domain.entity.Notice
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecentNoticeAdapter(private val itemClick: (Notice) -> (Unit))
    : RecyclerView.Adapter<RecentNoticeAdapter.RecentNoticeViewHolder>() {

    private val noticeList = mutableListOf<Notice>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentNoticeViewHolder {
        val binding = ItemRecentNoticeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentNoticeViewHolder(binding,itemClick)
    }

    override fun onBindViewHolder(holder: RecentNoticeViewHolder, position: Int) {
        holder.onBind(noticeList[position])
    }

    override fun getItemCount(): Int = noticeList.size

    fun setItems(newItems: List<Notice>) {
        noticeList.clear()
        noticeList.addAll(newItems)
        notifyDataSetChanged()
    }

    class RecentNoticeViewHolder(
        private val binding: ItemRecentNoticeBinding,
        private val itemClick: (Notice) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Notice) {
            binding.tvTitle.text = item.title
            binding.tvType.text = "[${item.type}]"
            binding.tvTime.text = getTimeDifference(item.time)

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }

        private fun getTimeDifference(inputDateTime: String): String {
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val inputTime = LocalDateTime.parse(inputDateTime, formatter)

            val diffInMinutes = java.time.Duration.between(inputTime, currentTime).toMinutes()
            val diffInHours = java.time.Duration.between(inputTime, currentTime).toHours()

            return when {
                diffInMinutes < 60 -> "${diffInMinutes}분 전"
                diffInHours < 24 -> "${diffInHours}시간 전"
                else -> "${inputTime.monthValue}월 ${inputTime.dayOfMonth}일"
            }
        }
    }
}