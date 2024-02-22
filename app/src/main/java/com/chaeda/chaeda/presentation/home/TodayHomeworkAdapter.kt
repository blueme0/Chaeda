package com.chaeda.chaeda.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemHomeHomeworkBinding
import com.chaeda.domain.entity.Homework

class TodayHomeworkAdapter(private val itemClick: (Homework) -> (Unit))
    : RecyclerView.Adapter<TodayHomeworkAdapter.TodayHomeworkViewHolder>() {

    private val homeworkList = mutableListOf<Homework>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayHomeworkViewHolder {
        val binding = ItemHomeHomeworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodayHomeworkViewHolder(binding,itemClick)
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
        private val itemClick: (Homework) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Homework) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.range

            binding.ivThumbnail.load(item.photoUrls.first()) {
//                transformations(RoundedCornersTransformation(4f))
            }

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}