package com.chaeda.chaeda.presentation.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemAcademyInfoBinding
import com.chaeda.domain.entity.Academy

class AcademyListAdapter(private val itemClick: (Academy) -> (Unit))
    : RecyclerView.Adapter<AcademyListAdapter.AcademyListViewHolder>() {

    private val academyList = mutableListOf<Academy>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AcademyListAdapter.AcademyListViewHolder {
        val binding = ItemAcademyInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AcademyListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: AcademyListAdapter.AcademyListViewHolder, position: Int) {
        holder.onBind(academyList[position])
    }

    override fun getItemCount(): Int = academyList.size

    fun setItems(newItems: List<Academy>) {
        academyList.clear()
        academyList.addAll(newItems)
        notifyDataSetChanged()
    }

    class AcademyListViewHolder(
        private val binding: ItemAcademyInfoBinding,
        private val itemClick: (Academy) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Academy) {
            binding.tvHomeAcademy.text = item.name
            binding.tvHomeClass.text = item.`class`
            binding.tvHomeTeacher.text = item.teacher

            binding.root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}