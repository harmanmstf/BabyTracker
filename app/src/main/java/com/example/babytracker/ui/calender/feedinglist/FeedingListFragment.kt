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
val a = viewModel.selectedDate2.value.toString()
        binding.tvDateCurrent.text = a
        // Observe all feedings and update the UI
        viewModel.retrieveItem(a).observe(viewLifecycleOwner) { feedings ->
            feedings?.let {
                feedingAdapter.submitList(feedings)
            }
        }





        return binding.root
    }
}