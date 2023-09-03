package com.example.babytracker.ui.sleep

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.databinding.FragmentSleepBinding
import com.example.babytracker.util.TimePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SleepFragment : Fragment() {

    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SleepViewModel by activityViewModels {
        SleepViewModel.SleepViewModelFactory((activity?.application as BabyTrackerApplication).database.itemDao())
    }

    private val timePicker: TimePicker by lazy { TimePicker(requireContext()) }
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSleepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        // Set a click listener to show the time picker dialog
        binding.vFellSleep.setOnClickListener {
            timePicker.showTimePickerDialog(binding.tvFellSleep)
            timePicker.updateTimeTextView(binding.tvFellSleep)
        }

        binding.vWokeUp.setOnClickListener {
            timePicker.showTimePickerDialog(binding.tvWokeUp)
            timePicker.updateTimeTextView(binding.tvWokeUp)
        }


        binding.btnSaveSleep.setOnClickListener {
            val fellSleepTime = binding.tvFellSleep.text.toString()
            val wokeUpTime = binding.tvWokeUp.text.toString()
            val note = binding.etNote.text.toString()



            val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)
            viewModel.saveSleep(fellSleepTime, wokeUpTime, note, formattedDate)

            binding.vLoading.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.tvSaved.visibility = View.VISIBLE

                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigateUp()
                    }, 1000)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}