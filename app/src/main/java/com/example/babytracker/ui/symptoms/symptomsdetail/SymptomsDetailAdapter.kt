package com.example.babytracker.ui.symptoms.symptomsdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.babytracker.R
import com.example.babytracker.databinding.SymptomsDetailItemBinding
import com.example.babytracker.model.SymptomsDetail


class SymptomsDetailAdapter : ListAdapter<SymptomsDetail, SymptomsDetailAdapter.MemberViewHolder>(MemberDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = SymptomsDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = getItem(position)
        holder.bind(member)
    }

    inner class MemberViewHolder(private val binding: SymptomsDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SymptomsDetail) {
            binding.imgSymptom.setImageResource(item.imageSymptom)
            binding.tvSymptomsName.text = item.nameSymptom.toString()
        }
    }
}

class MemberDiffCallback : DiffUtil.ItemCallback<SymptomsDetail>() {
    override fun areItemsTheSame(oldItem: SymptomsDetail, newItem: SymptomsDetail): Boolean {
        return oldItem.nameSymptom == newItem.nameSymptom
    }

    override fun areContentsTheSame(oldItem: SymptomsDetail, newItem: SymptomsDetail): Boolean {
        return oldItem == newItem
    }
}