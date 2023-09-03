package com.example.babytracker.ui.feeding.feedinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.databinding.FragmentFeedingListBinding
import com.example.babytracker.ui.feeding.FeedingViewModel


class FeedingListFragment : Fragment() {


    private lateinit var feedingAdapter: FeedingListAdapter
    private val viewModel: FeedingViewModel by activityViewModels {
        FeedingViewModel.FeedingViewModelFactory((activity?.application as BabyTrackerApplication).database.itemDao())
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
        viewModel.selectedDate2.observe(viewLifecycleOwner) { selectedDate ->
            // Use the selected date as needed in your fragment
            // For example, you can update UI elements with the selected date
            binding.tvDateCurrent.text = selectedDate ?: "No date selected"
        }

        val x = viewModel.selectedDate2.value.toString()
        // Observe all feedings and update the UI
        viewModel.retrieveItem(x).observe(viewLifecycleOwner) { feedings ->
            feedings?.let {
                feedingAdapter.submitList(feedings)
            }
        }





        return binding.root
    }
}