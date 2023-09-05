package com.example.babytracker.ui.symptoms.symptomsdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.babytracker.databinding.FragmentSymptomsDetailBinding
import com.example.babytracker.ui.symptoms.SymptomsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SymptomsDetailFragment : Fragment() {

    private var _binding: FragmentSymptomsDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SymptomsViewModel by activityViewModels ()


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

        binding.btnSaveSymptomsDetail.setOnClickListener {
            findNavController().navigateUp()
        }


        val adapter = SymptomsDetailAdapter(this) { viewModel.setSymptomSelectStatus(it) }
        binding.rvSymptoms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSymptoms.adapter = adapter

        viewModel.symptoms.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}