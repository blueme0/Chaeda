package com.chaeda.chaeda.presentation.assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ItemAssignmentBinding
import com.chaeda.domain.entity.Assignment

class AssignmentAdapter(private val itemClick: (Assignment) -> (Unit))
    : RecyclerView.Adapter<AssignmentAdapter.TodayHomeworkViewHolder>() {

    private val assignmentList = mutableListOf<Assignment>()
    private var newItemClick: (Assignment) -> (Unit) = itemClick

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayHomeworkViewHolder {
        val binding = ItemAssignmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodayHomeworkViewHolder(binding,itemClick, newItemClick)
    }

    fun setAddItemClick(itemClick: (Assignment) -> (Unit)) {
        newItemClick = itemClick
    }

    override fun onBindViewHolder(holder: TodayHomeworkViewHolder, position: Int) {
        holder.onBind(assignmentList[position])
    }

    override fun getItemCount(): Int = assignmentList.size

    fun setItems(newItems: List<Assignment>?) {
        assignmentList.clear()
        assignmentList.add(Assignment(null, "", 0, 0, "", null, null))
        if (newItems != null) assignmentList.addAll(newItems)
        notifyDataSetChanged()
    }

    class TodayHomeworkViewHolder(
        private val binding: ItemAssignmentBinding,
        private val itemClick: (Assignment) -> Unit,
        private val newItemClick: (Assignment) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Assignment) {
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