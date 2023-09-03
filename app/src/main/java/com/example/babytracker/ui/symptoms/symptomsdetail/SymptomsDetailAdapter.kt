package com.example.babytracker.ui.symptoms.symptomsdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.babytracker.databinding.SymptomsDetailItemBinding
import com.example.babytracker.model.SymptomsDetail
import com.example.babytracker.ui.symptoms.SymptomsViewModel


class SymptomsDetailAdapter(
    private val context: SymptomsDetailFragment,
    private val viewModel: SymptomsViewModel,
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

            // Set up the long click listener to toggle the checked state
            binding.root.setOnClickListener {
                item.isSelected = !item.isSelected
                binding.cardView.isChecked = item.isSelected



                val selectedSymptoms = viewModel.selectedSymptoms.value?.toMutableList() ?: mutableListOf()
                if (item.isSelected) {
                    // Add the selected symptom to the list
                    selectedSymptoms.add(context.resources.getString(item.nameSymptom))
                } else {
                    // Remove the unselected symptom from the list
                    selectedSymptoms.remove(context.resources.getString(item.nameSymptom))
                }
                viewModel.setSelectedSymptoms(selectedSymptoms)
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