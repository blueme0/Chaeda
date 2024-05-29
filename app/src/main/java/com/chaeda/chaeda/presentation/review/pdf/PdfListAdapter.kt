package com.chaeda.chaeda.presentation.review.pdf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ItemHomeHomeworkBinding
import com.chaeda.domain.entity.ReviewPdf

class PdfListAdapter (private val itemClick: (ReviewPdf) -> (Unit))
    : RecyclerView.Adapter<PdfListAdapter.PdfListViewHolder>() {

    private val pdfList = mutableListOf<ReviewPdf>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PdfListAdapter.PdfListViewHolder {
        val binding = ItemHomeHomeworkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PdfListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PdfListAdapter.PdfListViewHolder, position: Int) {
        holder.onBind(pdfList[position])
    }

    override fun getItemCount(): Int = pdfList.size

    fun setItems(newItems: List<ReviewPdf>) {
        pdfList.clear()
        pdfList.addAll(newItems)
        notifyDataSetChanged()
    }

    class PdfListViewHolder(
        private val binding: ItemHomeHomeworkBinding,
        private val itemClick: (ReviewPdf) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ReviewPdf) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.createdDateTime
            binding.ivThumbnail.setImageResource(R.drawable.ic_empty_thumbnail)

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }

}