package com.chaeda.chaeda.presentation.review.box

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.databinding.ItemIncorrectPhotoBinding
import com.chaeda.domain.entity.ReviewProblem
import timber.log.Timber

class ProblemBoxAdapter(private val forChoice: Boolean, private val itemClick: (ReviewProblem) -> (Unit))
    : RecyclerView.Adapter<ProblemBoxAdapter.ProblemBoxViewHolder>() {

    private val problemList = mutableListOf<ReviewProblem>()
    private val selectedMap = mutableMapOf<Long, ReviewProblem>()

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

    fun setItems(newItems: List<ReviewProblem>) {
        problemList.clear()
        problemList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun fixSelectedList(item: ReviewProblem) {
        if (selectedMap.containsKey(item.reviewNoteProblemId)) selectedMap.remove(item.reviewNoteProblemId)
        else selectedMap.put(item.reviewNoteProblemId!!, item)
    }

    fun getItems(): MutableMap<Long, ReviewProblem> = selectedMap

    class ProblemBoxViewHolder(
        private val binding: ItemIncorrectPhotoBinding,
        private val itemClick: (ReviewProblem) -> Unit,
        private val adapter: ProblemBoxAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ReviewProblem) {
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