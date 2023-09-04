package com.example.babytracker.ui.sleep.sleeplist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentSleepListBinding
import com.example.babytracker.ui.feeding.feedinglist.FeedingListAdapter
import com.example.babytracker.ui.sleep.SleepViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SleepListFragment : Fragment() {

    private var _binding: FragmentSleepListBinding? = null
    private val binding get() = _binding!!

    private lateinit var sleepAdapter: SleepListAdapter
    private val viewModel: SleepViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSleepListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        sleepAdapter = SleepListAdapter()
        binding.rvSleep.adapter = sleepAdapter

        binding.rvSleep.layoutManager = LinearLayoutManager(requireContext())
        viewModel.sleeps.observe(viewLifecycleOwner) { sleeps ->
            sleeps?.let {
                sleepAdapter.submitList(sleeps)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}