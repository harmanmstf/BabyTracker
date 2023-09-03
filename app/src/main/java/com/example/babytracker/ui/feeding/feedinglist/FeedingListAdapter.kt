package com.example.babytracker.ui.feeding.feedinglist

import com.example.babytracker.data.entities.Feeding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.babytracker.databinding.FeedingListItemBinding



class FeedingListAdapter : ListAdapter<Feeding, FeedingListAdapter.FeedingViewHolder>(
    FeedingDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeedingListItemBinding.inflate(inflater, parent, false)
        return FeedingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedingViewHolder, position: Int) {
        val currentFeeding = getItem(position)
        holder.bind(currentFeeding)
    }

     class FeedingViewHolder(private val binding: FeedingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(feeding: Feeding) {
            binding.tvTime.text = feeding.time
            binding.tvAmount.text = "${feeding.amount} ml"
            binding.tvNote.text = feeding.note
            binding.tvDate.text = feeding.date
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