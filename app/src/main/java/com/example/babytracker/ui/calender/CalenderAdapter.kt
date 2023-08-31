package com.example.babytracker.ui.calender

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.babytracker.ui.calender.alllist.AllListFragment
import com.example.babytracker.ui.calender.feedinglist.FeedingListFragment
import com.example.babytracker.ui.calender.sleeplist.SleepListFragment
import com.example.babytracker.ui.calender.symptomslist.SymptomsListFragment

class CalenderAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllListFragment()
            1 -> FeedingListFragment()
            2 -> SleepListFragment()
            3 -> SymptomsListFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}