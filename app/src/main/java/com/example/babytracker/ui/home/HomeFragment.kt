package com.example.babytracker.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentHomeBinding
import com.example.babytracker.databinding.FragmentOnboarding1Binding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.btnFeeding.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_feedingFragment)
        }

        binding.btnSleep.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sleepFragment)
        }

        binding.btnSymptoms.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_symptomsFragment)
        }

        binding.btnCalender.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_calenderFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}