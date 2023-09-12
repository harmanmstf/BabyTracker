package com.okation.aivideocreator.ui.sleep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.data.repository.Repository
import com.okation.aivideocreator.data.entities.Sleep
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

            val sleep = Sleep(fellSleepTime = fellSleepTime, wokeUpTime = wokeUpTime, note = note, date = date)
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

    fun updateSleep(id: Int, fellSleepTime: String, wokeUpTime: String, note: String, date: String) {
        viewModelScope.launch {

            val sleep = Sleep(id = id, fellSleepTime = fellSleepTime, wokeUpTime = wokeUpTime, note = note, date = date)
            repository.updateSleep(sleep)
        }
    }


    private val _id = MutableLiveData<Int?>(null)
    private val id: LiveData<Int?> = _id

    fun setId(id: Int?) {
        _id.value = id
    }

    private val _sleep = id.switchMap { id ->
        if (id != null) {
            repository.getSleep(id)
        } else {
            MutableLiveData()
        }
    }

    val sleep: LiveData<Sleep> = _sleep

    private val _isObservingSleep = MutableLiveData<Boolean?>(null)
    val isObservingSleep: LiveData<Boolean?> = _isObservingSleep

    fun setIsObservingSleep(value: Boolean?) {
        _isObservingSleep.value = value
    }
}