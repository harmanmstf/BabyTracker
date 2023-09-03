package com.example.babytracker.ui.symptoms.symptomslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentSymptomsListBinding
import com.example.babytracker.ui.feeding.FeedingViewModel
import com.example.babytracker.ui.feeding.feedinglist.FeedingListAdapter
import com.example.babytracker.ui.sleep.sleeplist.SleepListAdapter
import com.example.babytracker.ui.symptoms.SymptomsViewModel

class SymptomsListFragment : Fragment() {

    private var _binding: FragmentSymptomsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var symptomsAdapter: SymptomsListAdapter
    private val viewModel: SymptomsViewModel by activityViewModels {
        SymptomsViewModel.SymptomsViewModelFactory((activity?.application as BabyTrackerApplication).database.itemDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSymptomsListBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialize RecyclerView and Adapter
        symptomsAdapter = SymptomsListAdapter()
        binding.rvSymptoms.adapter = symptomsAdapter

        binding.rvSymptoms.layoutManager = LinearLayoutManager(requireContext())
        viewModel.selectedDate2.observe(viewLifecycleOwner) { selectedDate ->
            if (selectedDate != null) {
                viewModel.retrieveItem(selectedDate).observe(viewLifecycleOwner) { symptoms ->
                    symptoms?.let {
                        symptomsAdapter.submitList(symptoms)
                    }
                }
            }
        }

     //   val x = viewModel.selectedDate2.value.toString()
        // Observe all feedings and update the UI
       // viewModel.retrieveItem(x).observe(viewLifecycleOwner) { symptoms ->
        //    symptoms?.let {
        //        symptomsAdapter.submitList(symptoms)
       //     }
       // }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}