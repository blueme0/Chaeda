package com.chaeda.chaeda.presentation.homework.result.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.chaeda.databinding.ItemSubmitPhotoBinding

class LoadImagesAdapter() :
    ListAdapter<String, LoadImagesAdapter.ItemViewHolder>(
        ItemListDiffCallback
    ) {

    private lateinit var binding: ItemSubmitPhotoBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding = ItemSubmitPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ItemViewHolder(
        private val binding: ItemSubmitPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String) {
            binding.ivSubmitItemImage.load(item) {
//                transformations(RoundedCornersTransformation(4f))
            }
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}