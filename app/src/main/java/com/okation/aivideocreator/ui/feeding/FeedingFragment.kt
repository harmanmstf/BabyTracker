package com.okation.aivideocreator.ui.feeding

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var feedingId: Int? = null
    private var isObservingFeeding: Boolean? = null


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
                viewModel.setIsObservingFeeding(false)
                findNavController().navigateUp()
            }

            vTime.setOnClickListener {
                timePicker.showTimePickerDialog(tvFeedingTime)
                timePicker.updateTimeTextView(tvFeedingTime)
            }

            saveState = SaveState(tvFeedingTime, etAmount, btnSaveFeeding)
            tvFeedingTime.addTextChangedListener(saveState.textWatcher)
            etAmount.addTextChangedListener(saveState.textWatcher)

            tvMl.isVisible = !etAmount.text.isNullOrEmpty()

            viewModel.isObservingFeeding.observe(viewLifecycleOwner) { isObserving ->
                isObservingFeeding = isObserving
                observeFeeding()
            }


            btnSaveFeeding.setOnClickListener {
                if (feedingId == null) {
                    saveFeedingItem()
                } else {
                    updateFeedingItem()
                }
                viewModel.setIsObservingFeeding(false)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFeeding() {
        binding.apply {
            if (isObservingFeeding == true) {
                viewModel.feeding.observe(viewLifecycleOwner) { feeding ->
                    feeding?.let {
                        etAmount.text = Editable.Factory.getInstance().newEditable(feeding.amount)
                        tvFeedingTime.text = feeding.time
                        etNote.text = Editable.Factory.getInstance().newEditable(feeding.note)
                        feedingId = feeding.id
                    }
                }
            } else {
                etAmount.text = null
                tvFeedingTime.text = null
                etNote.text = null
                feedingId = null
            }
        }

    }

    private fun updateFeedingItem() {
        binding.apply {
            val time = tvFeedingTime.text.toString()
            val amount = etAmount.text.toString()
            val note = etNote.text.toString()
            val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)
            val id = feedingId

            loadingState = LoadingState(vLoading, progressBar, tvSaved, findNavController())
            loadingState.showLoadingState()

            viewModel.updateFeeding(id!!, time, amount, note, formattedDate)
        }
    }

    private fun saveFeedingItem() {
        binding.apply {
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
