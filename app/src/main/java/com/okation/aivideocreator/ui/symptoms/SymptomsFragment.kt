package com.okation.aivideocreator.ui.symptoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentSymptomsBinding
import com.okation.aivideocreator.util.LoadingState
import com.okation.aivideocreator.util.TimePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class SymptomsFragment : Fragment() {

    private var _binding: FragmentSymptomsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SymptomsViewModel by activityViewModels()

    private val timePicker: TimePicker by lazy { TimePicker(requireContext()) }
    private val calendar = Calendar.getInstance()

    private lateinit var loadingState: LoadingState


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSymptomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            btnBack.setOnClickListener {
                findNavController().navigateUp()
                viewModel.clearSymptomsAndTime()
            }

            vSymptoms.setOnClickListener {
                findNavController().navigate(R.id.action_symptomsFragment_to_symptomsDetailFragment)
                viewModel.updateSelectedTime(tvSymptomsTime.text.toString())
            }


            vTime.setOnClickListener {
                timePicker.showTimePickerDialog(tvSymptomsTime)
                timePicker.updateTimeTextView(tvSymptomsTime)

            }


            btnSaveSymptoms.setOnClickListener {
                val time = tvSymptomsTime.text.toString()
                val symptoms = tvSymptoms.text.toString()
                val note = etNote.text.toString()
                val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)

                if (time.isEmpty() || symptoms.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill in both time and symptoms",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.saveSymptoms(time, symptoms, note, formattedDate)
                    loadingState = LoadingState(progressBar, tvSaved, findNavController())
                    loadingState.showLoadingState()
                    viewModel.clearSymptomsAndTime()
                }
            }


            viewModel.symptoms.observe(viewLifecycleOwner) { symptoms ->
                val selectedSymptoms = symptoms.filter {
                    it.isSelected
                }

                tvSymptoms.text = selectedSymptoms.joinToString(", ") {
                    requireContext().getString(it.nameSymptom)
                }
            }

            viewModel.selectedTime.observe(viewLifecycleOwner) { time ->
                tvSymptomsTime.text = time
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}