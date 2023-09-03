package com.example.babytracker.ui.symptoms.symptomsdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.babytracker.databinding.SymptomsDetailItemBinding
import com.example.babytracker.model.SymptomsDetail


class SymptomsDetailAdapter(
    private val context: SymptomsDetailFragment,
    private val onItemClicked: (SymptomsDetail) -> Unit,
) : ListAdapter<SymptomsDetail, SymptomsDetailAdapter.SymptomsViewHolder>(SymptomsDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        val binding =
            SymptomsDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SymptomsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptomsViewHolder, position: Int) {
        val member = getItem(position)
        holder.bind(member)
    }

    inner class SymptomsViewHolder(private val binding: SymptomsDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SymptomsDetail) {
            binding.imgSymptom.setImageResource(item.imageSymptom)
            binding.tvSymptomsName.text = context.resources.getString(item.nameSymptom)

            binding.cardView.isChecked = item.isSelected

            binding.root.setOnClickListener {
                binding.cardView.isChecked = item.isSelected
                onItemClicked(item)
            }

        }
    }

    class SymptomsDiffCallback : DiffUtil.ItemCallback<SymptomsDetail>() {
        override fun areItemsTheSame(oldItem: SymptomsDetail, newItem: SymptomsDetail): Boolean {
            return oldItem.nameSymptom == newItem.nameSymptom
        }

        override fun areContentsTheSame(oldItem: SymptomsDetail, newItem: SymptomsDetail): Boolean {
            return oldItem == newItem
        }
    }
}