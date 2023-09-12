package com.okation.aivideocreator.ui.sleep.sleeplist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.data.entities.Feeding
import com.okation.aivideocreator.data.entities.Sleep
import com.okation.aivideocreator.databinding.SleepListItemBinding


class SleepListAdapter (
    private val onItemClicked: (Sleep) -> Unit
) : ListAdapter<Sleep, SleepListAdapter.SleepViewHolder>(
    SleepDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SleepListItemBinding.inflate(inflater, parent, false)
        return SleepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val currentSleep = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentSleep)
        }
        holder.bind(currentSleep)
    }

    class SleepViewHolder(private val binding: SleepListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(sleep: Sleep) {
            binding.tvFellSleep.text = "Fell Sleep: ${sleep.fellSleepTime}"
            binding.tvWokeUp.text = "Woke Up: ${sleep.wokeUpTime}"
            binding.tvSleepNote.text = "Note: ${sleep.note }"
        }
    }
}

class SleepDiffCallback : DiffUtil.ItemCallback<Sleep>() {
    override fun areItemsTheSame(oldItem: Sleep, newItem: Sleep): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Sleep, newItem: Sleep): Boolean {
        return oldItem == newItem
    }
}