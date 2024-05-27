package com.chaeda.chaeda.presentation.review.box

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemIncorrectPhotoBinding
import com.chaeda.domain.entity.ReviewProblemDTO
import timber.log.Timber

class ProblemBoxAdapter(private val forChoice: Boolean, private val itemClick: (ReviewProblemDTO) -> (Unit))
    : RecyclerView.Adapter<ProblemBoxAdapter.ProblemBoxViewHolder>() {

    private val problemList = mutableListOf<ReviewProblemDTO>()
    private val selectedMap = mutableMapOf<Long, ReviewProblemDTO>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProblemBoxAdapter.ProblemBoxViewHolder {
        val binding = ItemIncorrectPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProblemBoxViewHolder(binding, itemClick, this)
    }

    override fun onBindViewHolder(
        holder: ProblemBoxAdapter.ProblemBoxViewHolder,
        position: Int
    ) {
        holder.onBind(problemList[position])
    }

    override fun getItemCount(): Int = problemList.size

    fun setItems(newItems: List<ReviewProblemDTO>) {
        problemList.clear()
        problemList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun fixSelectedList(item: ReviewProblemDTO) {
        if (selectedMap.containsKey(item.reviewNoteProblemId)) selectedMap.remove(item.reviewNoteProblemId)
        else selectedMap.put(item.reviewNoteProblemId!!, item)
    }

    fun getItems(): MutableMap<Long, ReviewProblemDTO> = selectedMap

    class ProblemBoxViewHolder(
        private val binding: ItemIncorrectPhotoBinding,
        private val itemClick: (ReviewProblemDTO) -> Unit,
        private val adapter: ProblemBoxAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ReviewProblemDTO) {
            binding.ivPhoto.load(item.presignedUrl) {
                // transformations(RoundedCornersTransformation(4f))
            }

            if (adapter.forChoice) {
                binding.root.setOnSingleClickListener {
                    Timber.tag("chaeda-item").d("selected: ${adapter.selectedMap}")
                    Timber.tag("chaeda-item").d("id: ${item.reviewNoteProblemId}")
                    adapter.fixSelectedList(item)

                    if (adapter.selectedMap.containsKey(item.reviewNoteProblemId)) {
                        binding.ivChecked.visibility = View.VISIBLE
                    } else {
                        binding.ivChecked.visibility = View.INVISIBLE
                    }
                    itemClick(item)
                }
            }
        }
    }
}