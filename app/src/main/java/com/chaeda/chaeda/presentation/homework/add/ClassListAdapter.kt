package com.chaeda.chaeda.presentation.homework.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemClassListBinding
import com.chaeda.domain.entity.ClassItem

class ClassListAdapter (private val itemClick: (ClassItem) -> (Unit))
    : RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder>() {

    private val classList = mutableListOf<ClassItem>()
    private var selectedClass: ClassItem = ClassItem("", "기본", "")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassListViewHolder {
        val binding = ItemClassListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassListViewHolder(binding, this, itemClick)
    }

    override fun onBindViewHolder(holder: ClassListViewHolder, position: Int) {
        val currentItem = classList[position]
        holder.onBind(currentItem, currentItem.id == selectedClass.id)
    }

    override fun getItemCount(): Int = classList.size

    fun setSelectedItem(item: ClassItem) {
        selectedClass = item
        notifyDataSetChanged()
    }

    fun getSelectedClassName(): String {
        return selectedClass.name
    }

    fun setItems(newItems: List<ClassItem>) {
        classList.clear()
        classList.addAll(newItems)
        notifyDataSetChanged()
    }

    class ClassListViewHolder(
        private val binding: ItemClassListBinding,
        private val adapter: ClassListAdapter,
        private val itemClick: (ClassItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ClassItem, isSelected: Boolean) {
            binding.tvName.text = item.name
            binding.tvDetail.text = item.detail
            binding.ivCheck.isSelected = isSelected

            binding.root.setOnSingleClickListener {
                itemClick(item)
                adapter.setSelectedItem(item)
            }
        }
    }

}