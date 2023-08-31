package com.example.babytracker.ui.calender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babytracker.R
import com.example.babytracker.databinding.FragmentCalenderBinding
import com.google.android.material.tabs.TabLayoutMediator

class CalenderFragment : Fragment() {

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CalenderAdapter(this)
        binding.viewPager.adapter = adapter

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.icon = when (position) {

                1 -> resources.getDrawable(R.drawable.btn_feeding_selected)
                2 -> resources.getDrawable(R.drawable.btn_sleep_selected)
                3 -> resources.getDrawable(R.drawable.btn_symptons_selected)
                else -> return@TabLayoutMediator
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}