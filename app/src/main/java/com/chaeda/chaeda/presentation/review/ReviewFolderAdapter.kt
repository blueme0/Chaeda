package com.chaeda.chaeda.presentation.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemHomeHomeworkBinding
import com.chaeda.domain.entity.ReviewFolderDTO

class ReviewFolderAdapter(private val itemClick: (ReviewFolderDTO) -> (Unit))
    : RecyclerView.Adapter<ReviewFolderAdapter.ReviewFolderViewHolder>() {

    private val folderList = mutableListOf<ReviewFolderDTO>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewFolderAdapter.ReviewFolderViewHolder {
        val binding = ItemHomeHomeworkBinding.inflate(
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

    fun setItems(newItems: List<ReviewFolderDTO>) {
        folderList.clear()
        folderList.addAll(newItems)
        notifyDataSetChanged()
    }

    class ReviewFolderViewHolder(
        private val binding: ItemHomeHomeworkBinding,
        private val itemClick: (ReviewFolderDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ReviewFolderDTO) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.description
            binding.ivThumbnail.load("")

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}