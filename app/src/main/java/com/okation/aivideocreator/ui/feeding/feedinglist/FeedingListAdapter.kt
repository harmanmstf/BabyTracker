package com.okation.aivideocreator.ui.feeding.feedinglist

import android.annotation.SuppressLint
import com.okation.aivideocreator.data.entities.Feeding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.databinding.FeedingListItemBinding


class FeedingListAdapter(
    private val onItemClicked: (Feeding) -> Unit
) : ListAdapter<Feeding, FeedingListAdapter.FeedingViewHolder>(
    FeedingDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeedingListItemBinding.inflate(inflater, parent, false)
        return FeedingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedingViewHolder, position: Int) {
        val currentFeeding = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentFeeding)
        }
        holder.bind(currentFeeding)
    }

    class FeedingViewHolder(private val binding: FeedingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(feeding: Feeding) {
            binding.tvTime.text = "Time: ${feeding.time}"
            binding.tvAmount.text = "Amount: ${feeding.amount}"
            binding.tvNote.text = "Note: ${feeding.note}"
        }
    }
}

class FeedingDiffCallback : DiffUtil.ItemCallback<Feeding>() {
    override fun areItemsTheSame(oldItem: Feeding, newItem: Feeding): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Feeding, newItem: Feeding): Boolean {
        return oldItem == newItem
    }
}

