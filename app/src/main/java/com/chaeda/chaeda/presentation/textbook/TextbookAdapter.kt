package com.chaeda.chaeda.presentation.textbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemAssignmentBinding
import com.chaeda.domain.entity.Textbook

class TextbookAdapter(private val itemClick: (Textbook) -> (Unit))
    : RecyclerView.Adapter<TextbookAdapter.TextbookViewHolder>() {

    private val textbookList = mutableListOf<Textbook>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextbookAdapter.TextbookViewHolder {
        val binding = ItemAssignmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextbookViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: TextbookAdapter.TextbookViewHolder, position: Int) {
        holder.onBind(textbookList[position])
    }

    override fun getItemCount(): Int = textbookList.size

    fun setItems(newItems: List<Textbook>) {
        textbookList.clear()
        textbookList.addAll(newItems)
        notifyDataSetChanged()
    }

    class TextbookViewHolder(
        private val binding: ItemAssignmentBinding,
        private val itemClick: (Textbook) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Textbook) {
            binding.tvTitle.text = item.name
            binding.tvContent.text = "${item.targetGrade}, ${item.subject}, ${item.publisher}"
            binding.ivThumbnail.load(item.imageUrl)

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}