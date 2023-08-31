package com.example.babytracker.ui.symptoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentFeedingBinding
import com.example.babytracker.databinding.FragmentSymptomsBinding

class SymptomsFragment : Fragment() {

    private var _binding: FragmentSymptomsBinding? = null
    private val binding get() = _binding!!

    private val args:SymptomsFragmentArgs by  navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSymptomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.vSymptoms.setOnClickListener {
            findNavController().navigate(R.id.action_symptomsFragment_to_symptomsDetailFragment)
        }

        val selectedSymptoms = findNavController().previousBackStackEntry?.savedStateHandle?.get<String>("symptomsNames")

        // Update UI with selectedSymptoms

        if (selectedSymptoms != null) {
            binding.tvSymptoms.text = selectedSymptoms.toString()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}