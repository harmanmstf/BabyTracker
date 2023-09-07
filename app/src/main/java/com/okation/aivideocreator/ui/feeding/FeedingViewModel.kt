package com.okation.aivideocreator.ui.feeding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.okation.aivideocreator.data.repository.Repository
import com.okation.aivideocreator.data.entities.Feeding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedingViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _selectedDate = MutableLiveData<String?>(null)
    private val selectedDate: LiveData<String?> = _selectedDate

    fun setSelectedDate(date: String?) {
        _selectedDate.value = date
    }

    fun saveFeeding(time: String, amount: String, note: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val feeding = Feeding(time = time, amount = amount, note = note, date = date)
            repository.insertFeeding(feeding)

        }
    }

    private val _feedings = selectedDate.switchMap { selectedDate ->
        if (selectedDate != null) {
            repository.getFeedings(selectedDate)
        } else {
            MutableLiveData()
        }
    }

    val feedings: LiveData<List<Feeding>> = _feedings

}