package com.example.babytracker.ui.symptoms

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
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentSymptomsBinding
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
class SymptomsFragment : Fragment() {

    private var _binding: FragmentSymptomsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SymptomsViewModel by activityViewModels()

    private val timePicker: TimePicker by lazy { TimePicker(requireContext()) }
    private val calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSymptomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.vSymptoms.setOnClickListener {
            findNavController().navigate(R.id.action_symptomsFragment_to_symptomsDetailFragment)
        }

        // val selectedSymptoms = findNavController().previousBackStackEntry?.savedStateHandle?.get<String>("symptomsNames")
        //  if (selectedSymptoms != null) {
        //    binding.tvSymptoms.text = selectedSymptoms.toString()}

        binding.vTime.setOnClickListener {
            timePicker.showTimePickerDialog(binding.tvSymptomsTime)
            timePicker.updateTimeTextView(binding.tvSymptomsTime)
        }


        binding.btnSaveFeeding.setOnClickListener {
            val time = binding.tvSymptomsTime.text.toString()
            val symptoms = binding.tvSymptoms.text.toString()
            val note = binding.etNote.text.toString()


            val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)
            viewModel.saveSymptoms(time, symptoms, note, formattedDate)

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


        viewModel.symptoms.observe(viewLifecycleOwner) { symptoms ->
            val selectedSymptoms = symptoms.filter {
                it.isSelected
            }

            binding.tvSymptoms.text = selectedSymptoms.joinToString(", ") {
                requireContext().getString(it.nameSymptom)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}