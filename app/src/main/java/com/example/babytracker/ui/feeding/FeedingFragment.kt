package com.example.babytracker.ui.feeding

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.databinding.FragmentFeedingBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class FeedingFragment : Fragment() {

    private var _binding: FragmentFeedingBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private val viewModel: FeedingViewModel by activityViewModels {
        FeedingViewModel.FeedingViewModelFactory((activity?.application as BabyTrackerApplication).feedingDatabase.itemDao())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFeedingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        // Set a click listener to show the time picker dialog
        binding.vTime.setOnClickListener {
            showTimePickerDialog()
            updateTimeTextView()
        }


        binding.btnSaveFeeding.setOnClickListener {
            val time = binding.tvFeedingTime.text.toString()
            val amount = binding.etAmount.text.toString()
            val note = binding.etNote.text.toString()



            val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)
            viewModel.saveFeeding(time, amount, note, formattedDate)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Update the calendar with the selected time
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                // Update the TextView with the selected time
                updateTimeTextView()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // 24-hour format (true for 24-hour format)
        )
        timePickerDialog.show()
    }

    private fun updateTimeTextView() {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedTime = timeFormat.format(calendar.time)
        binding.tvFeedingTime.text = formattedTime
    }
}

