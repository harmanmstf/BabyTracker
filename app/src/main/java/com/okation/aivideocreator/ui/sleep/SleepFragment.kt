package com.okation.aivideocreator.ui.sleep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentSleepBinding
import com.okation.aivideocreator.util.LoadingState
import com.okation.aivideocreator.util.SaveState
import com.okation.aivideocreator.util.TimePicker
import dagger.hilt.android.AndroidEntryPoint
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

    private lateinit var loadingState: LoadingState
    private lateinit var saveState: SaveState


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

            saveState = SaveState(tvFellSleep, tvWokeUp, btnSaveSleep)
            tvFellSleep.addTextChangedListener(saveState.textWatcher)
            tvWokeUp.addTextChangedListener(saveState.textWatcher)

            btnSaveSleep.setOnClickListener {
                val fellSleepTime = tvFellSleep.text.toString()
                val wokeUpTime = tvWokeUp.text.toString()
                val note = etNote.text.toString()
                val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)

                viewModel.saveSleep(fellSleepTime, wokeUpTime, note, formattedDate)

                loadingState = LoadingState(vLoading, progressBar, tvSaved, findNavController())
                loadingState.showLoadingState()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}