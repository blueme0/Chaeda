package com.chaeda.chaeda.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ItemHomeHomeworkBinding
import com.chaeda.domain.entity.AssignmentDTO

class TodayHomeworkAdapter(private val itemClick: (AssignmentDTO) -> (Unit))
    : RecyclerView.Adapter<TodayHomeworkAdapter.TodayHomeworkViewHolder>() {

    private val assignmentList = mutableListOf<AssignmentDTO>()
    private var newItemClick: (AssignmentDTO) -> (Unit) = itemClick

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

    fun setAddItemClick(itemClick: (AssignmentDTO) -> (Unit)) {
        newItemClick = itemClick
    }

    override fun onBindViewHolder(holder: TodayHomeworkViewHolder, position: Int) {
        holder.onBind(assignmentList[position])
    }

    override fun getItemCount(): Int = assignmentList.size

    fun setItems(newItems: List<AssignmentDTO>?) {
        assignmentList.clear()
        assignmentList.add(AssignmentDTO(null, "", 0, 0, "", null, null))
        if (newItems != null) assignmentList.addAll(newItems)
        notifyDataSetChanged()
    }

    class TodayHomeworkViewHolder(
        private val binding: ItemHomeHomeworkBinding,
        private val itemClick: (AssignmentDTO) -> Unit,
        private val newItemClick: (AssignmentDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: AssignmentDTO) {
            binding.tvTitle.text = item.title
            if (item.title.isEmpty()) {
                binding.tvContent.text = ""
                binding.ivThumbnail.setImageResource(R.drawable.ic_homework_add)
                binding.root.setOnSingleClickListener {
                    // 선생님이 추가하기 클릭 시
                    newItemClick(item)
                }

            } else {
                binding.tvContent.text = "${item.startPage}p - ${item.endPage}p"
                binding.ivThumbnail.load(item.textbook!!.imageUrl) {
//                transformations(RoundedCornersTransformation(4f))
                }
                binding.root.setOnSingleClickListener {
                    itemClick(item)
                }
            }
        }
    }
}