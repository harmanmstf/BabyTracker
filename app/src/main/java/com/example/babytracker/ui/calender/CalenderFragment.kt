package com.example.babytracker.ui.calender

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.babytracker.BabyTrackerApplication
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentCalenderBinding
import com.example.babytracker.ui.calender.feedinglist.FeedingListViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class CalenderFragment : Fragment() {

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

    private val viewModel: FeedingListViewModel by activityViewModels {
        FeedingListViewModel.FeedingViewModelFactory((activity?.application as BabyTrackerApplication).feedingDatabase.itemDao())
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        updateDateTitle()

        // Open DatePickerDialog when tvDateTitle is clicked
        binding.tvDateTitle.setOnClickListener {
            showDatePickerDialog()
        }

        val adapter = CalenderAdapter(this)
        binding.viewPager.adapter = adapter

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
             when (position) {
                0 -> tab.text = "All"
                1 -> tab.icon= ContextCompat.getDrawable(tabLayout.context, R.drawable.btn_feeding_selected)
                2 -> tab.icon= ContextCompat.getDrawable(tabLayout.context, R.drawable.btn_sleep_selected)
                3 -> tab.icon=ContextCompat.getDrawable(tabLayout.context, R.drawable.btn_symptons_selected)
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDatePickerDialog() {

        val today = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Update the calendar with the selected date
                calendar.set(year, month, dayOfMonth)
                // Update the tvDateTitle with the selected date
                updateDateTitle()


            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = today.timeInMillis
        datePickerDialog.show()
    }

    private fun updateDateTitle() {
        val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        binding.tvDateTitle.text = formattedDate

        viewModel.setSelectedDate(formattedDate)
    }

}