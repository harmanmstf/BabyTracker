package com.okation.aivideocreator.ui.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.okation.aivideocreator.data.repository.Repository
import com.okation.aivideocreator.data.SymptomsDataSource
import com.okation.aivideocreator.data.entities.Symptoms
import com.okation.aivideocreator.model.SymptomsDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SymptomsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val dataSource = SymptomsDataSource()
    private val _symptoms = MutableLiveData(dataSource.loadSymptoms())
    val symptoms: LiveData<List<SymptomsDetail>> = _symptoms

    fun setSymptomSelectStatus(item: SymptomsDetail) {
        _symptoms.value = symptoms.value?.map {
            if (item == it) it.copy(isSelected = it.isSelected.not())
            else it
        }
    }

    fun clearSymptomsAndTime() {
        _symptoms.value = emptyList()
        _selectedTime.value = ""
    }


    private val _selectedDate = MutableLiveData<String?>(null)
    private val selectedDate: LiveData<String?> = _selectedDate

    fun setSelectedDate(date: String?) {
        _selectedDate.value = date
    }


    fun saveSymptoms(time: String, symptomName: String, note: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val symptoms = Symptoms(
                time = time,
                symptomName = symptomName,
                note = note,
                date = date
            )
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

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String> = _selectedTime

    fun updateSelectedTime(time: String) {
        _selectedTime.value = time
    }
}