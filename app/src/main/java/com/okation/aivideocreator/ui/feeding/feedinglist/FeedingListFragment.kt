package com.okation.aivideocreator.ui.feeding.feedinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentFeedingListBinding
import com.okation.aivideocreator.ui.feeding.FeedingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedingListFragment : Fragment() {

    private var _binding: FragmentFeedingListBinding? = null
    private val binding get() = _binding!!

    private lateinit var feedingAdapter: FeedingListAdapter
    private val viewModel: FeedingViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedingAdapter = FeedingListAdapter{
            viewModel.setId(it.id)
            viewModel.setIsObservingFeeding(true)
            findNavController().navigate(R.id.action_calenderFragment_to_feedingFragment)
        }

        binding.rvFeeding.adapter = feedingAdapter
        binding.rvFeeding.layoutManager = LinearLayoutManager(requireContext())

        viewModel.feedings.observe(viewLifecycleOwner) { feedings ->
            feedings?.let {
                feedingAdapter.submitList(feedings)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}