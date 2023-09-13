package com.okation.aivideocreator.ui.symptoms.symptomslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.data.entities.Symptoms
import com.okation.aivideocreator.databinding.SymptomsListItemBinding


class SymptomsListAdapter(
    private val onItemClicked: (Symptoms) -> Unit
) : ListAdapter<Symptoms, SymptomsListAdapter.SymptomsViewHolder>(
    SymptomsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SymptomsListItemBinding.inflate(inflater, parent, false)
        return SymptomsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptomsViewHolder, position: Int) {
        val currentSymptoms = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentSymptoms)
        }
        holder.bind(currentSymptoms)
    }

    class SymptomsViewHolder(private val binding: SymptomsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(symptoms: Symptoms) {
            binding.tvTime.text = "Time: ${symptoms.time}"
            binding.tvSymptoms.text = "Symptoms: ${symptoms.symptomName}"
            binding.tvNote.text = "Note:${symptoms.note}"
        }
    }
}

class SymptomsDiffCallback : DiffUtil.ItemCallback<Symptoms>() {
    override fun areItemsTheSame(oldItem: Symptoms, newItem: Symptoms): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Symptoms, newItem: Symptoms): Boolean {
        return oldItem == newItem
    }
}