package com.example.babytracker.ui.feeding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babytracker.data.Repository
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.local.BabyTrackerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FeedingViewModel(private val repository: Repository) : ViewModel() {

    private val _selectedDate = MutableLiveData<String?>(null)


    val selectedDate2: LiveData<String?> = _selectedDate

    fun setSelectedDate(date: String?) {
        _selectedDate.value = date
    }

    fun saveFeeding(time: String, amount: String, note: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val feeding = Feeding(time = time, amount = amount, note = note, date = date)
            repository.insertFeeding(feeding)
        }
    }

    fun retrieveItem(date: String): LiveData<List<Feeding>> {
        return repository.getFeedings(date)
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