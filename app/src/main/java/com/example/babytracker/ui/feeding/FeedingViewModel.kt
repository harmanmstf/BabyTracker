package com.example.babytracker.ui.feeding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.babytracker.data.Repository
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.local.BabyTrackerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedingViewModel(private val repository: Repository) : ViewModel() {

    fun saveFeeding(time: String, amount: String, note: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val feeding = Feeding(time = time, amount = amount, note = note)
            repository.insertFeeding(feeding)
        }
    }

    class FeedingViewModelFactory(private val database: BabyTrackerDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FeedingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FeedingViewModel(Repository(database)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}