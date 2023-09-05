package com.example.babytracker.ui.symptoms.symptomslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.babytracker.data.entities.Symptoms
import com.example.babytracker.databinding.SymptomsListItemBinding


class SymptomsListAdapter : ListAdapter<Symptoms, SymptomsListAdapter.SymptomsViewHolder>(
    SymptomsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SymptomsListItemBinding.inflate(inflater, parent, false)
        return SymptomsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptomsViewHolder, position: Int) {
        val currentSymptoms = getItem(position)
        holder.bind(currentSymptoms)
    }

    class SymptomsViewHolder(private val binding: SymptomsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(symptoms: Symptoms) {
            binding.tvTime.text = "time: ${symptoms.time}"
            binding.tvSymptoms.text = "symptoms: ${symptoms.symptomName}"
            binding.tvNote.text = "note:${symptoms.note}"
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