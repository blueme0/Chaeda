package com.chaeda.chaeda.presentation.homework.textbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemClassListBinding
import com.chaeda.domain.entity.TextbookDTO

class TextbookListAdapter (private val itemClick: (TextbookDTO) -> (Unit))
    : RecyclerView.Adapter<TextbookListAdapter.TextbookListViewHolder>() {

    private val textbookList = mutableListOf<TextbookDTO>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextbookListViewHolder {
        val binding = ItemClassListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextbookListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: TextbookListViewHolder, position: Int) {
        val currentItem = textbookList[position]
        holder.onBind(currentItem)
    }

    override fun getItemCount(): Int = textbookList.size

    fun setItems(newItems: List<TextbookDTO>) {
        textbookList.clear()
        textbookList.addAll(newItems)
        notifyDataSetChanged()
    }

    class TextbookListViewHolder(
        private val binding: ItemClassListBinding,
        private val itemClick: (TextbookDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: TextbookDTO) {
            binding.tvName.text = item.name
            binding.tvDetail.text = "${item.targetGrade} - ${item.publisher} ${item.publishYear}년 출판"

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }

}