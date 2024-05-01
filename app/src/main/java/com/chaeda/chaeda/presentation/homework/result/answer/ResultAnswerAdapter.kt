package com.chaeda.chaeda.presentation.homework.result.answer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ItemAnswerListBinding
import com.chaeda.domain.entity.ResultAnswer

class ResultAnswerAdapter()
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
        return ResultAnswerViewHolder(binding, this)
    }

    override fun onBindViewHolder(
        holder: ResultAnswerAdapter.ResultAnswerViewHolder,
        position: Int
    ) {
        holder.onBind(answerList[position], position)
    }

    override fun getItemCount(): Int = answerList.size

    fun setItems(newItems: List<ResultAnswer>) {
        answerList.clear()
        answerList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItems(): List<ResultAnswer> = answerList

    fun setLevel(pos: Int, level: Int) {
        answerList[pos].level = level
        notifyItemChanged(pos)
    }

    fun setIncorrect(pos: Int, incorrect: Boolean) {
        answerList[pos].checked = incorrect
        notifyItemChanged(pos)
    }

    class ResultAnswerViewHolder(
        private val binding: ItemAnswerListBinding,
        private val adapter: ResultAnswerAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ResultAnswer, position: Int) {
            with(binding) {
                tvIndex.text = item.index.toString()

                tvIndex.setOnSingleClickListener {
                    adapter.setIncorrect(position, !item.checked)
                }
                ivCheck.setOnSingleClickListener {
                    adapter.setIncorrect(position, !item.checked)
                }
            }

            if (item.checked) {
                with(binding) {
                    tvEasy.text = if (item.level == 1) "O" else ""
                    tvNormal.text = if (item.level == 2) "O" else ""
                    tvHard.text = if (item.level == 3) "0" else ""
                    tvUnsolved.text = if (item.level == 0) "0" else ""
                }
                check(position)
            }
            else {
                with(binding) {
                    tvEasy.text = ""
                    tvNormal.text = ""
                    tvHard.text = ""
                    tvUnsolved.text = ""
                }
                uncheck()
            }
        }

        private fun check(pos: Int) {
            with(binding) {
                tvIndex.setBackgroundColor(Color.parseColor("#FADDDD"))
                ivCheck.setImageResource(R.drawable.ic_radio_small_checked)
                tvEasy.setBackgroundColor(Color.TRANSPARENT)
                tvNormal.setBackgroundColor(Color.TRANSPARENT)
                tvHard.setBackgroundColor(Color.TRANSPARENT)
                tvUnsolved.setBackgroundColor(Color.TRANSPARENT)
            }
            addClickListener(pos)
        }

        private fun uncheck() {
            with(binding) {
                tvIndex.setBackgroundColor(Color.TRANSPARENT)
                ivCheck.setImageResource(R.drawable.ic_radio_small_unchecked)
                tvEasy.setBackgroundColor(Color.parseColor("#E8E8E8"))
                tvNormal.setBackgroundColor(Color.parseColor("#E8E8E8"))
                tvHard.setBackgroundColor(Color.parseColor("#E8E8E8"))
                tvUnsolved.setBackgroundColor(Color.parseColor("#E8E8E8"))
            }
            resetClickListener()
        }

        private fun addClickListener(pos: Int) {
            with(binding) {
                tvEasy.setOnSingleClickListener {
                    adapter.setLevel(pos, 1)
                }
                tvNormal.setOnSingleClickListener {
                    adapter.setLevel(pos, 2)
                }
                tvHard.setOnSingleClickListener {
                    adapter.setLevel(pos, 3)
                }
                tvUnsolved.setOnSingleClickListener {
                    adapter.setLevel(pos, 0)
                }
            }
        }

        private fun resetClickListener() {
            with(binding) {
                tvEasy.setOnSingleClickListener {  }
                tvNormal.setOnSingleClickListener {  }
                tvHard.setOnSingleClickListener {  }
                tvUnsolved.setOnSingleClickListener {  }
            }
        }
    }
}