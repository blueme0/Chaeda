package com.chaeda.chaeda.presentation.homework.result.answer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemAnswerListBinding
import com.chaeda.domain.entity.ResultAnswer

class ResultAnswerAdapter(private val itemClick: (ResultAnswer) -> (Unit))
    : RecyclerView.Adapter<ResultAnswerAdapter.ResultAnswerViewHolder>() {

    private val answerList = mutableListOf<ResultAnswer>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultAnswerAdapter.ResultAnswerViewHolder {
        val binding = ItemAnswerListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultAnswerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ResultAnswerAdapter.ResultAnswerViewHolder,
        position: Int
    ) {
        holder.onBind(answerList[position])
    }

    override fun getItemCount(): Int = answerList.size

    fun setItems(newItems: List<ResultAnswer>) {
        answerList.clear()
        answerList.addAll(newItems)
        notifyDataSetChanged()
    }

    class ResultAnswerViewHolder(
        private val binding: ItemAnswerListBinding,
        private val itemClick: (ResultAnswer) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ResultAnswer) {
            binding.tvIndex.text = item.index.toString()
            binding.tvWritten.text = item.written.toString()
            binding.tvCorrect.text = item.correct.toString()

            binding.ivDetail.setOnSingleClickListener {
                itemClick(item)
            }

            if (item.written == item.correct) {
                binding.tvIndex.setBackgroundColor(Color.parseColor("#E4FADD"))
            }
            else {
                binding.tvIndex.setBackgroundColor(Color.parseColor("#FADDDD"))
            }
        }
    }
}