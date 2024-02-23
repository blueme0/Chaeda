package com.chaeda.chaeda.presentation.homework.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemIncorrectPhotoBinding

class IncorrectCollectionAdapter(private val itemClick: (String) -> (Unit))
    : RecyclerView.Adapter<IncorrectCollectionAdapter.IncorrectCollectionViewHolder>() {

    private val stringList = mutableListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncorrectCollectionAdapter.IncorrectCollectionViewHolder {
        val binding = ItemIncorrectPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IncorrectCollectionViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: IncorrectCollectionAdapter.IncorrectCollectionViewHolder,
        position: Int
    ) {
        holder.onBind(stringList[position])
    }

    override fun getItemCount(): Int = stringList.size

    fun setItems(newItems: List<String>) {
        stringList.clear()
        stringList.addAll(newItems)
        notifyDataSetChanged()
    }

    class IncorrectCollectionViewHolder(
        private val binding: ItemIncorrectPhotoBinding,
        private val itemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String) {
            binding.ivPhoto.load(item) {
                // transformations(RoundedCornersTransformation(4f))
            }

            binding.root.setOnSingleClickListener { itemClick(item) }
        }
    }
}