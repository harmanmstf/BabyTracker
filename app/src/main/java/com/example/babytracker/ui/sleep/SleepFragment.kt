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
class SleepFragment : Fragment() {

    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SleepViewModel by activityViewModels()

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

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }


            vFellSleep.setOnClickListener {
                timePicker.showTimePickerDialog(tvFellSleep)
                timePicker.updateTimeTextView(tvFellSleep)
            }

            vWokeUp.setOnClickListener {
                timePicker.showTimePickerDialog(tvWokeUp)
                timePicker.updateTimeTextView(tvWokeUp)
            }


            btnSaveSleep.setOnClickListener {
                val fellSleepTime = tvFellSleep.text.toString()
                val wokeUpTime = tvWokeUp.text.toString()
                val note = etNote.text.toString()

                val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)
                viewModel.saveSleep(fellSleepTime, wokeUpTime, note, formattedDate)

                vLoading.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE

                CoroutineScope(Dispatchers.IO).launch {
                    delay(2000)

                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        tvSaved.visibility = View.VISIBLE

                        Handler(Looper.getMainLooper()).postDelayed({
                            findNavController().navigateUp()
                        }, 1000)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}