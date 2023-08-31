package com.example.babytracker.ui.symptoms.symptomsdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.babytracker.R
import com.example.babytracker.data.SymptomsDataSource
import com.example.babytracker.databinding.FragmentSymptomsDetailBinding

class SymptomsDetailFragment : Fragment() {

    private var _binding: FragmentSymptomsDetailBinding? = null
    private val binding get() = _binding!!

    private val dataSource = SymptomsDataSource()
    private val adapter = SymptomsDetailAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSymptomsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("selectedSymptoms")
            ?.observe(viewLifecycleOwner, Observer { selectedSymptoms ->
                // Do something with the selectedSymptoms
                // For example, update UI, show a toast, etc.
            })

        binding.btnSaveFeeding.setOnClickListener {
            val selectedSymptoms = adapter.currentList.filter { it.isSelected }.map { it.nameSymptom }

            // Set the selectedSymptoms in the savedStateHandle
            findNavController().previousBackStackEntry?.savedStateHandle?.set("symptomsNames", selectedSymptoms)

            findNavController().popBackStack()
        }

        binding.rvSymptoms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSymptoms.adapter = adapter

        val symptoms = dataSource.loadSymptoms()
        adapter.submitList(symptoms)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}