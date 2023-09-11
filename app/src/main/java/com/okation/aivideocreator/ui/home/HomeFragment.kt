package com.okation.aivideocreator.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentHomeBinding
import com.okation.aivideocreator.ui.inapp.PremiumViewModel

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

        binding.apply {
            btnSettings.setOnClickListener { navigateToDestination(R.id.action_homeFragment_to_settingsFragment) }
            btnFeeding.setOnClickListener { navigateToDestination(R.id.action_homeFragment_to_feedingFragment) }
            btnSleep.setOnClickListener { navigateToDestination(R.id.action_homeFragment_to_sleepFragment) }
            btnSymptoms.setOnClickListener { navigateToDestination(R.id.action_homeFragment_to_symptomsFragment) }
            btnCalender.setOnClickListener { navigateToDestination(R.id.action_homeFragment_to_calenderFragment) }
        }
    }

    private fun navigateToDestination(destinationId: Int) {
        findNavController().navigate(destinationId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}