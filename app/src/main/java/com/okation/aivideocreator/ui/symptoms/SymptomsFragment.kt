package com.okation.aivideocreator.ui.symptoms

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentSymptomsBinding
import com.okation.aivideocreator.util.LoadingState
import com.okation.aivideocreator.util.SaveState
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
    private lateinit var saveState: SaveState

    private var symptomId: Int? = null
    private var isObservingSymptom: Boolean? = null
    private lateinit var itemDate: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSymptomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            btnBack.setOnClickListener {
                viewModel.setIsObservingSymptom(false)
                findNavController().navigateUp()
               // viewModel.clearSymptomsAndTime()

            }

            vSymptoms.setOnClickListener {
                findNavController().navigate(R.id.action_symptomsFragment_to_symptomsDetailFragment)
                viewModel.updateSelectedTime(tvSymptomsTime.text.toString())
            }

            vTime.setOnClickListener {
                timePicker.showTimePickerDialog(tvSymptomsTime)
                timePicker.updateTimeTextView(tvSymptomsTime)
            }

            saveState = SaveState(tvSymptomsTime, tvSymptoms, btnSaveSymptoms)
            tvSymptomsTime.addTextChangedListener(saveState.textWatcher)
            tvSymptoms.addTextChangedListener(saveState.textWatcher)


            viewModel.isObservingSymptom.observe(viewLifecycleOwner) { isObserving ->
                isObservingSymptom = isObserving
                observeSymptom()
            }


            btnSaveSymptoms.setOnClickListener {
                if (symptomId == null) {
                    saveSymptomItem()
                } else {
                    updateSymptomItem()
                }
                viewModel.setIsObservingSymptom(false)
               // viewModel.clearSymptomsAndTime()

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

    private fun observeSymptom() {
        binding.apply {
            if (isObservingSymptom == true) {
                viewModel.symptom.observe(viewLifecycleOwner) { symptom ->
                    symptom?.let {
                        tvSymptoms.text = symptom.symptomName
                        tvSymptomsTime.text = symptom.time
                        etNote.text = Editable.Factory.getInstance().newEditable(symptom.note)
                        symptomId = symptom.id
                        itemDate = symptom.date
                    }
                }
            } else {
                tvSymptoms.text = null
                tvSymptomsTime.text = null
                etNote.text = null
                symptomId = null
            }
        }

    }

    private fun updateSymptomItem() {
        binding.apply {
            val time = tvSymptomsTime.text.toString()
            val symptoms = tvSymptoms.text.toString()
            val note = etNote.text.toString()
            val date = itemDate
            val id = symptomId

            loadingState = LoadingState(vLoading, progressBar, tvSaved, findNavController())
            loadingState.showLoadingState()

            viewModel.updateSymptom(id!!, time, symptoms, note, date)
        }
    }

    private fun saveSymptomItem() {
        binding.apply {
            val time = tvSymptomsTime.text.toString()
            val symptoms = tvSymptoms.text.toString()
            val note = etNote.text.toString()
            val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)

            viewModel.saveSymptoms(time, symptoms, note, formattedDate)

            loadingState = LoadingState(vLoading, progressBar, tvSaved, findNavController())
            loadingState.showLoadingState()
        }
    }
}