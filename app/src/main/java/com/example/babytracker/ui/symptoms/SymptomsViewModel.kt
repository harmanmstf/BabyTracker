package com.example.babytracker.ui.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import com.example.babytracker.data.Repository
import com.example.babytracker.data.SymptomsDataSource
import com.example.babytracker.data.entities.Sleep
import com.example.babytracker.data.entities.Symptoms
import com.example.babytracker.data.local.BabyTrackerDao
import com.example.babytracker.model.SymptomsDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SymptomsViewModel@Inject constructor(
    private val repository: Repository) : ViewModel() {

    private val dataSource = SymptomsDataSource()
    private val _selectedDate = MutableLiveData<String?>(null)

    private val _symptoms = MutableLiveData(dataSource.loadSymptoms())

    val symptoms: LiveData<List<SymptomsDetail>> = _symptoms

    private val selectedDate: LiveData<String?> = _selectedDate

    fun setSelectedDate(date: String?) {
        _selectedDate.value = date
    }

    fun setSymptomSelectStatus(item: SymptomsDetail) {
        _symptoms.value = symptoms.value?.map {
            if (item == it) it.copy(isSelected = it.isSelected.not())
            else it
        }
    }


    fun saveSymptoms(time: String, symptomName: String, note: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val symptoms = Symptoms(time = time, symptomName = symptomName, note = note, date = date)
            repository.insertSymptoms(symptoms)

        }
    }



    private val _symptomsList = selectedDate.switchMap { selectedDate ->
        if (selectedDate != null) {
            repository.getSymptoms(selectedDate)
        } else {
            MutableLiveData()
        }
    }

    val symptomsList: LiveData<List<Symptoms>> = _symptomsList

}