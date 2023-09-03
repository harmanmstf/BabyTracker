package com.example.babytracker.ui.symptoms.symptomsdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.R
import com.example.babytracker.data.SymptomsDataSource
import com.example.babytracker.databinding.FragmentSymptomsDetailBinding
import com.example.babytracker.ui.symptoms.SymptomsViewModel

class SymptomsDetailFragment : Fragment() {

    private var _binding: FragmentSymptomsDetailBinding? = null
    private val binding get() = _binding!!

    private val dataSource = SymptomsDataSource()


    private val viewModel: SymptomsViewModel by activityViewModels {
        SymptomsViewModel.SymptomsViewModelFactory((activity?.application as BabyTrackerApplication).database.itemDao())
    }


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

        binding.btnSaveFeeding.setOnClickListener {
            // Get the selected symptoms from the ViewModel
            val selectedSymptoms = viewModel.selectedSymptoms.value ?: emptyList<String>()

            // Perform any desired actions with the selected symptoms
            // For example, you can save them to a database or pass them to the previous fragment.

            // Once you've performed the actions, navigate back to the previous fragment
            findNavController().navigateUp()
        }


         val adapter = SymptomsDetailAdapter(this, viewModel)
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