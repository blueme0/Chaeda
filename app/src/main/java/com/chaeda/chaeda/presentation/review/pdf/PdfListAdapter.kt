package com.chaeda.chaeda.presentation.review.pdf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemHomeHomeworkBinding
import com.chaeda.domain.entity.ReviewPdfDTO

class PdfListAdapter (private val itemClick: (ReviewPdfDTO) -> (Unit))
    : RecyclerView.Adapter<PdfListAdapter.PdfListViewHolder>() {

    private val pdfList = mutableListOf<ReviewPdfDTO>()

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

    fun setItems(newItems: List<ReviewPdfDTO>) {
        pdfList.clear()
        pdfList.addAll(newItems)
        notifyDataSetChanged()
    }

    class PdfListViewHolder(
        private val binding: ItemHomeHomeworkBinding,
        private val itemClick: (ReviewPdfDTO) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ReviewPdfDTO) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.createdDateTime
            binding.ivThumbnail.load("")

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }

}