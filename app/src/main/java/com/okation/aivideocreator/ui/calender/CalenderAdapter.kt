package com.okation.aivideocreator.ui.calender

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.okation.aivideocreator.ui.calender.alllist.AllListFragment
import com.okation.aivideocreator.ui.feeding.feedinglist.FeedingListFragment
import com.okation.aivideocreator.ui.sleep.sleeplist.SleepListFragment
import com.okation.aivideocreator.ui.symptoms.symptomslist.SymptomsListFragment

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