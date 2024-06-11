package com.chaeda.chaeda.presentation.assignment.result.review

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ItemAnswerReviewListBinding
import com.chaeda.domain.entity.ResultAnswer

class ResultReviewAdapter(private val itemClick: (ResultAnswer) -> (Unit))
    : RecyclerView.Adapter<ResultReviewAdapter.ResultReviewViewHolder>() {

    private val reviewList = mutableListOf<ResultAnswer>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultReviewAdapter.ResultReviewViewHolder {
        val binding = ItemAnswerReviewListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultReviewViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ResultReviewAdapter.ResultReviewViewHolder,
        position: Int
    ) {
        holder.onBind(reviewList[position])
    }

    override fun getItemCount(): Int = reviewList.size

    fun setItems(newItems: List<ResultAnswer>) {
        reviewList.clear()
        reviewList.addAll(newItems)
        notifyDataSetChanged()
    }

    class ResultReviewViewHolder(
        private val binding: ItemAnswerReviewListBinding,
        private val itemClick: (ResultAnswer) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ResultAnswer) {
            with(binding) {
                tvIndex.text = item.index
                if (item.checked) {
                    tvIndex.setBackgroundColor(Color.parseColor("#FADDDD"))
                    ivCheck.setImageResource(R.drawable.ic_radio_small_checked)
                    root.setOnSingleClickListener {
                        itemClick(item)
                    }
                    tvAdd.text = "추가하기"
                    tvAdd.setBackgroundColor(Color.TRANSPARENT)
                }
                else {
                    tvIndex.setBackgroundColor(Color.TRANSPARENT)
                    ivCheck.setImageResource(R.drawable.ic_radio_small_unchecked)
                    root.setOnSingleClickListener {  }
                    tvAdd.text = ""
                    tvAdd.setBackgroundColor(Color.parseColor("#E8E8E8"))
                }
            }
        }
    }
}