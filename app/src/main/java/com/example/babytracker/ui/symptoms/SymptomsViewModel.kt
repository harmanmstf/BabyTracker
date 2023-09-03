package com.example.babytracker.ui.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babytracker.data.Repository
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.entities.Symptoms
import com.example.babytracker.data.local.BabyTrackerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SymptomsViewModel(private val repository: Repository) : ViewModel() {

    private val _selectedDate = MutableLiveData<String?>(null)


    val selectedDate2: LiveData<String?> = _selectedDate

    fun setSelectedDate(date: String?) {
        _selectedDate.value = date
    }

    fun saveSymptoms(time: String, symptomName: String, note: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val symptoms = Symptoms(time = time, symptomName = symptomName, note = note, date = date)
            repository.insertSymptoms(symptoms)
        }
    }

    fun retrieveItem(date: String): LiveData<List<Symptoms>> {
        return repository.getSymptoms(date)
    }




    class SymptomsViewModelFactory(private val database: BabyTrackerDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SymptomsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SymptomsViewModel(Repository(database)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}