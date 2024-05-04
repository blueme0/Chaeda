package com.chaeda.chaeda.presentation.homework.result.answer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
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
        return ResultAnswerViewHolder(binding, this, itemClick)
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

    fun setLevel(pos: Int, level: String) {
        answerList[pos].level = level
        notifyItemChanged(pos)
    }

    fun setIncorrect(pos: Int, incorrect: Boolean) {
        answerList[pos].checked = incorrect
        notifyItemChanged(pos)
    }

    class ResultAnswerViewHolder(
        private val binding: ItemAnswerListBinding,
        private val adapter: ResultAnswerAdapter,
        private val itemClick: (ResultAnswer) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ResultAnswer, position: Int) {
            with(binding) {
                tvIndex.text = item.index.toString()

                tvIndex.setOnSingleClickListener {
                    adapter.setIncorrect(position, !item.checked)
                    itemClick(item)
                }
                ivCheck.setOnSingleClickListener {
                    adapter.setIncorrect(position, !item.checked)
                    itemClick(item)
                }
            }

            if (item.checked) {
                with(binding) {
                    tvEasy.text = if (item.level == "하") "O" else ""
                    tvNormal.text = if (item.level == "중") "O" else ""
                    tvHard.text = if (item.level == "상") "0" else ""
                    tvUnsolved.text = if (item.level == "미풀이") "0" else ""
                }
                check(position, item)
            }
            else {
                with(binding) {
                    tvEasy.text = ""
                    tvNormal.text = ""
                    tvHard.text = ""
                    tvUnsolved.text = ""
                }
                uncheck(item)
            }
        }

        private fun check(pos: Int, item: ResultAnswer) {
            with(binding) {
                tvIndex.setBackgroundColor(Color.parseColor("#FADDDD"))
                ivCheck.setImageResource(R.drawable.ic_radio_small_checked)
                tvEasy.setBackgroundColor(Color.TRANSPARENT)
                tvNormal.setBackgroundColor(Color.TRANSPARENT)
                tvHard.setBackgroundColor(Color.TRANSPARENT)
                tvUnsolved.setBackgroundColor(Color.TRANSPARENT)
            }
            addClickListener(pos, item)
        }

        private fun uncheck(item: ResultAnswer) {
            with(binding) {
                tvIndex.setBackgroundColor(Color.TRANSPARENT)
                ivCheck.setImageResource(R.drawable.ic_radio_small_unchecked)
                tvEasy.setBackgroundColor(Color.parseColor("#E8E8E8"))
                tvNormal.setBackgroundColor(Color.parseColor("#E8E8E8"))
                tvHard.setBackgroundColor(Color.parseColor("#E8E8E8"))
                tvUnsolved.setBackgroundColor(Color.parseColor("#E8E8E8"))
            }
            resetClickListener(item)
        }

        private fun addClickListener(pos: Int, item: ResultAnswer) {
            with(binding) {
                tvEasy.setOnSingleClickListener {
                    adapter.setLevel(pos, "하")
                    itemClick(item)
                }
                tvNormal.setOnSingleClickListener {
                    adapter.setLevel(pos, "중")
                    itemClick(item)
                }
                tvHard.setOnSingleClickListener {
                    adapter.setLevel(pos, "상")
                    itemClick(item)
                }
                tvUnsolved.setOnSingleClickListener {
                    adapter.setLevel(pos, "미풀이")
                    itemClick(item)
                }
            }
        }

        private fun resetClickListener(item: ResultAnswer) {
            with(binding) {
                tvEasy.setOnSingleClickListener { itemClick(item) }
                tvNormal.setOnSingleClickListener { itemClick(item) }
                tvHard.setOnSingleClickListener { itemClick(item) }
                tvUnsolved.setOnSingleClickListener { itemClick(item) }
            }
        }
    }
}