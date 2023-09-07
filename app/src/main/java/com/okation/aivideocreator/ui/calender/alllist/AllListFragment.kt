package com.okation.aivideocreator.ui.calender.alllist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.okation.aivideocreator.databinding.FragmentAllListBinding
import com.okation.aivideocreator.ui.feeding.FeedingViewModel
import com.okation.aivideocreator.ui.feeding.feedinglist.FeedingListAdapter
import com.okation.aivideocreator.ui.sleep.SleepViewModel
import com.okation.aivideocreator.ui.sleep.sleeplist.SleepListAdapter
import com.okation.aivideocreator.ui.symptoms.SymptomsViewModel
import com.okation.aivideocreator.ui.symptoms.symptomslist.SymptomsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllListFragment : Fragment() {

    private var _binding: FragmentAllListBinding? = null
    private val binding get() = _binding!!

    private lateinit var sleepAdapter: SleepListAdapter
    private val sleepViewModel: SleepViewModel by activityViewModels()
    private lateinit var feedingAdapter: FeedingListAdapter
    private val feedingViewModel: FeedingViewModel by activityViewModels()

    private lateinit var symptomsAdapter: SymptomsListAdapter
    private val symptomsViewModel: SymptomsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAllListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        feedingAdapter = FeedingListAdapter()
        binding.rvFeeding.adapter = feedingAdapter
        binding.rvFeeding.layoutManager = LinearLayoutManager(requireContext())

        feedingViewModel.feedings.observe(viewLifecycleOwner) { feedings ->
            feedings?.let {
                feedingAdapter.submitList(feedings)
            }
        }


        sleepAdapter = SleepListAdapter()
        binding.rvSleep.adapter = sleepAdapter
        binding.rvSleep.layoutManager = LinearLayoutManager(requireContext())

        sleepViewModel.sleeps.observe(viewLifecycleOwner) { sleeps ->
            sleeps?.let {
                sleepAdapter.submitList(sleeps)
            }
        }


        symptomsAdapter = SymptomsListAdapter()
        binding.rvSymptoms.adapter = symptomsAdapter
        binding.rvSymptoms.layoutManager = LinearLayoutManager(requireContext())
        symptomsViewModel.symptomsList.observe(viewLifecycleOwner) { symptoms ->
            symptoms?.let {
                symptomsAdapter.submitList(symptoms)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}