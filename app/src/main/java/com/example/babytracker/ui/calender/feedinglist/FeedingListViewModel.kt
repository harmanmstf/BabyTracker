package com.example.babytracker.ui.calender.feedinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babytracker.data.Repository
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.local.BabyTrackerDao
import kotlinx.coroutines.flow.Flow

class FeedingListViewModel(private val repository: Repository) : ViewModel() {

    // LiveData to hold all feedings
    val allFeedings: Flow<List<Feeding>> = repository.getFeedings()

    class FeedingViewModelFactory(private val dao: BabyTrackerDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FeedingListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FeedingListViewModel(Repository(dao)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

