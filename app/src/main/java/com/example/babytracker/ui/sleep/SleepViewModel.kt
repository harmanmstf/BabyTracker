package com.example.babytracker.ui.sleep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babytracker.data.Repository
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.entities.Sleep
import com.example.babytracker.data.local.BabyTrackerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SleepViewModel(private val repository: Repository) : ViewModel() {

    private val _selectedDate = MutableLiveData<String?>(null)


    val selectedDate2: LiveData<String?> = _selectedDate

    fun setSelectedDate(date: String?) {
        _selectedDate.value = date
    }

    fun saveSleep(fellSleepTime: String, wokeUpTime: String, note: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val sleep = Sleep(fellSleepTime = fellSleepTime, wokeUpTime = wokeUpTime, note = note, date = date)
            repository.insertSleep(sleep)
        }
    }

    fun retrieveItem(date: String): LiveData<List<Sleep>> {
        return repository.getSleeps(date)
    }




    class SleepViewModelFactory(private val database: BabyTrackerDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SleepViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SleepViewModel(Repository(database)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}