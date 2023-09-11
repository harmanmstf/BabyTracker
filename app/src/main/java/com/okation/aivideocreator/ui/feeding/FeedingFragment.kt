package com.okation.aivideocreator.ui.feeding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentFeedingBinding
import com.okation.aivideocreator.util.LoadingState
import com.okation.aivideocreator.util.SaveState
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
    private lateinit var saveState: SaveState


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

            saveState = SaveState(tvFeedingTime, etAmount, btnSaveFeeding)
            tvFeedingTime.addTextChangedListener(saveState.textWatcher)
            etAmount.addTextChangedListener(saveState.textWatcher)

            btnSaveFeeding.setOnClickListener {
                val time = tvFeedingTime.text.toString()
                val amount = etAmount.text.toString()
                val note = etNote.text.toString()
                val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)

                viewModel.saveFeeding(time, amount, note, formattedDate)

                loadingState = LoadingState(vLoading, progressBar, tvSaved, findNavController())
                loadingState.showLoadingState()
            }
        }
    }

    private fun updateSaveButtonVisibility() {
        val time = binding.tvFeedingTime.text.toString()
        val amount = binding.etAmount.text.toString()
        val isFieldsNotEmpty = time.isNotEmpty() && amount.isNotEmpty()
        binding.btnSaveFeeding.isVisible = isFieldsNotEmpty
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
