package com.example.babytracker.ui.calender.feedinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentFeedingListBinding
import com.example.babytracker.ui.feeding.FeedingViewModel
import kotlinx.coroutines.launch


class FeedingListFragment : Fragment() {


    private lateinit var feedingAdapter: FeedingListAdapter
    private val viewModel: FeedingListViewModel by activityViewModels {
        FeedingListViewModel.FeedingViewModelFactory((activity?.application as BabyTrackerApplication).feedingDatabase.itemDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedingListBinding.inflate(inflater, container, false)



        // Initialize RecyclerView and Adapter
        feedingAdapter = FeedingListAdapter()
        binding.rvFeeding.adapter = feedingAdapter

        binding.rvFeeding.layoutManager = LinearLayoutManager(requireContext())

        // Observe all feedings and update the UI
        lifecycleScope.launch {
            viewModel.allFeedings.collect { feedings ->
                feedings?.let {
                    feedingAdapter.submitList(feedings)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.selectedDate.collect { selectedDate ->
                // Use the selected date as needed in your fragment
                // For example, you can update UI elements with the selected date
                binding.tvDateCurrent.text = selectedDate ?: "No date selected"
            }
        }

        return binding.root
    }
}