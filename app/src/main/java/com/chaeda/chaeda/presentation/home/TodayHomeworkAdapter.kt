package com.chaeda.chaeda.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ItemHomeHomeworkBinding
import com.chaeda.domain.entity.Homework

class TodayHomeworkAdapter(private val itemClick: (Homework) -> (Unit))
    : RecyclerView.Adapter<TodayHomeworkAdapter.TodayHomeworkViewHolder>() {

    private val homeworkList = mutableListOf<Homework>()
    private var newItemClick: (Homework) -> (Unit) = itemClick

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayHomeworkViewHolder {
        val binding = ItemHomeHomeworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodayHomeworkViewHolder(binding,itemClick, newItemClick)
    }

    fun setAddItemClick(itemClick: (Homework) -> (Unit)) {
        newItemClick = itemClick
    }

    override fun onBindViewHolder(holder: TodayHomeworkViewHolder, position: Int) {
        holder.onBind(homeworkList[position])
    }

    override fun getItemCount(): Int = homeworkList.size

    fun setItems(newItems: List<Homework>) {
        homeworkList.clear()
        homeworkList.addAll(newItems)
        notifyDataSetChanged()
    }

    class TodayHomeworkViewHolder(
        private val binding: ItemHomeHomeworkBinding,
        private val itemClick: (Homework) -> Unit,
        private val newItemClick: (Homework) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Homework) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.range
            if (item.title.isEmpty()) {
                binding.ivThumbnail.setImageResource(R.drawable.ic_homework_add)
                binding.root.setOnSingleClickListener {
                    // 선생님이 추가하기 클릭 시
                    newItemClick(item)
                }

            } else {
                binding.ivThumbnail.load(item.photoUrls.first()) {
//                transformations(RoundedCornersTransformation(4f))
                }
                binding.root.setOnSingleClickListener {
                    itemClick(item)
                }
            }
        }
    }
}