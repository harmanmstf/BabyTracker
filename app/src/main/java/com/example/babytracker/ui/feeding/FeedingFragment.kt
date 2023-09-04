package com.example.babytracker.ui.feeding

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
import com.example.babytracker.databinding.FragmentFeedingBinding
import com.example.babytracker.util.TimePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FeedingFragment : Fragment() {

    private var _binding: FragmentFeedingBinding? = null
    private val binding get() = _binding!!


    private val viewModel: FeedingViewModel by activityViewModels ()

    private val timePicker: TimePicker by lazy { TimePicker(requireContext()) }
    private val calendar = Calendar.getInstance()


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
            timePicker.showTimePickerDialog(binding.tvFeedingTime)
            timePicker.updateTimeTextView(binding.tvFeedingTime)
        }


        binding.btnSaveFeeding.setOnClickListener {
            val time = binding.tvFeedingTime.text.toString()
            val amount = binding.etAmount.text.toString()
            val note = binding.etNote.text.toString()


            val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)
            viewModel.saveFeeding(time, amount, note, formattedDate)

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

