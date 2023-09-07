package com.okation.aivideocreator.ui.feeding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentFeedingBinding
import com.okation.aivideocreator.util.LoadingState
import com.okation.aivideocreator.util.TimePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FeedingFragment : Fragment() {

    private var _binding: FragmentFeedingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedingViewModel by activityViewModels()

    private val timePicker: TimePicker by lazy { TimePicker(requireContext()) }
    private val calendar = Calendar.getInstance()

    private lateinit var loadingState: LoadingState


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFeedingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }


            vTime.setOnClickListener {
                timePicker.showTimePickerDialog(tvFeedingTime)
                timePicker.updateTimeTextView(tvFeedingTime)
            }


            btnSaveFeeding.setOnClickListener {
                val time = tvFeedingTime.text.toString()
                val amount = etAmount.text.toString()
                val note = etNote.text.toString()
                val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)

                if (time.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill in both time and amount.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.saveFeeding(time, amount, note, formattedDate)

                    loadingState = LoadingState(progressBar, tvSaved, findNavController())
                    loadingState.showLoadingState()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
