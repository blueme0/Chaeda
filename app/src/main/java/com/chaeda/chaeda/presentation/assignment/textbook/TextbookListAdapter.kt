package com.chaeda.chaeda.presentation.assignment.textbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemTextbookListBinding
import com.chaeda.domain.entity.Textbook

class TextbookListAdapter (private val itemClick: (Textbook) -> (Unit))
    : RecyclerView.Adapter<TextbookListAdapter.TextbookListViewHolder>() {

    private val textbookList = mutableListOf<Textbook>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextbookListViewHolder {
        val binding = ItemTextbookListBinding.inflate(
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

    fun setItems(newItems: List<Textbook>) {
        textbookList.clear()
        textbookList.addAll(newItems)
        notifyDataSetChanged()
    }

    class TextbookListViewHolder(
        private val binding: ItemTextbookListBinding,
        private val itemClick: (Textbook) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Textbook) {
            binding.tvName.text = item.name
            binding.tvDetail.text = "${item.targetGrade} - ${item.publisher} ${item.publishYear}년 출판"

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }

}