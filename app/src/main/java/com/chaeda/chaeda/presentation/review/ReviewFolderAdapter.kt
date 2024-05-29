package com.chaeda.chaeda.presentation.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ItemAssignmentBinding
import com.chaeda.domain.entity.ReviewFolder

class ReviewFolderAdapter(private val itemClick: (ReviewFolder) -> (Unit))
    : RecyclerView.Adapter<ReviewFolderAdapter.ReviewFolderViewHolder>() {

    private val folderList = mutableListOf<ReviewFolder>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewFolderAdapter.ReviewFolderViewHolder {
        val binding = ItemAssignmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewFolderViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ReviewFolderAdapter.ReviewFolderViewHolder,
        position: Int
    ) {
        holder.onBind(folderList[position])
    }

    override fun getItemCount(): Int = folderList.size

    fun setItems(newItems: List<ReviewFolder>) {
        folderList.clear()
        folderList.addAll(newItems)
        notifyDataSetChanged()
    }

    class ReviewFolderViewHolder(
        private val binding: ItemAssignmentBinding,
        private val itemClick: (ReviewFolder) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ReviewFolder) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.description
            binding.ivThumbnail.setImageResource(R.drawable.ic_empty_thumbnail)

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}