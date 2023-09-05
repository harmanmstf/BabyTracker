package com.example.babytracker.ui.sleep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.babytracker.data.repository.Repository
import com.example.babytracker.data.entities.Sleep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _selectedDate = MutableLiveData<String?>(null)
    private val selectedDate: LiveData<String?> = _selectedDate

    fun setSelectedDate(date: String?) {
        _selectedDate.value = date
    }

    fun saveSleep(fellSleepTime: String, wokeUpTime: String, note: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val sleep = Sleep(
                fellSleepTime = fellSleepTime,
                wokeUpTime = wokeUpTime,
                note = note,
                date = date
            )
            repository.insertSleep(sleep)
        }
    }

    private val _sleeps = selectedDate.switchMap { selectedDate ->
        if (selectedDate != null) {
            repository.getSleeps(selectedDate)
        } else {
            MutableLiveData()
        }
    }

    val sleeps: LiveData<List<Sleep>> = _sleeps

}