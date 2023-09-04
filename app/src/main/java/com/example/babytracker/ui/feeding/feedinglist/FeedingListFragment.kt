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

        viewModel.feedings.observe(viewLifecycleOwner) { feedings ->
            feedings?.let {
                feedingAdapter.submitList(feedings)
            }
        }





        return binding.root
    }
}