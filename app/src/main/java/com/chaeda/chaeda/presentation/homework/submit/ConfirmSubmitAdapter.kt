package com.chaeda.chaeda.presentation.homework.submit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chaeda.chaeda.databinding.ItemSubmitPhotoBinding
import timber.log.Timber
import java.io.File

class ConfirmSubmitAdapter() :
    ListAdapter<File, ConfirmSubmitAdapter.ItemViewHolder>(
        ItemListDiffCallback
    ) {
    private lateinit var binding: ItemSubmitPhotoBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding =
            ItemSubmitPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(private val binding: ItemSubmitPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: File) {
            Timber.tag("chaeda-pic").d("file: $item")
            Glide.with(itemView.context)
                .load(item)
                .skipMemoryCache(true) // 메모리 캐시 건너뛰기
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 디스크 캐시 건너뛰기
                .into(binding.ivSubmitItemImage)
        }
    }

    object ItemListDiffCallback : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: File,
            newItem: File
        ): Boolean {
            return oldItem == newItem
        }
    }
}