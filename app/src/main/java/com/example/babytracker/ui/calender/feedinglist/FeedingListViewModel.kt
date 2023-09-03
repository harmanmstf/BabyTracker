package com.example.babytracker.ui.calender.feedinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.babytracker.data.Repository
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.local.BabyTrackerDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeedingListViewModel(private val repository: Repository,) : ViewModel() {

    // LiveData to hold all feedings
    //val allFeedings: Flow<List<Feeding>> = repository.getFeedings()

    fun retrieveItem(date: String): LiveData<List<Feeding>> {
        return repository.getFeedings(date)
    }

    class FeedingViewModelFactory(private val dao: BabyTrackerDao, ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FeedingListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FeedingListViewModel(Repository(dao)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate2: StateFlow<String?> = _selectedDate

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }


}

