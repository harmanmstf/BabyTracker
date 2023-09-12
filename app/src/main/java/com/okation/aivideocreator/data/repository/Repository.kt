package com.okation.aivideocreator.data.repository

import androidx.lifecycle.LiveData
import com.okation.aivideocreator.data.entities.Feeding
import com.okation.aivideocreator.data.entities.Sleep
import com.okation.aivideocreator.data.entities.Symptoms
import com.okation.aivideocreator.data.local.BabyTrackerDao
import javax.inject.Inject

class Repository @Inject constructor(private val datasource: BabyTrackerDao) {

    suspend fun insertFeeding(feeding: Feeding) {
        datasource.insertFeeding(feeding)
    }

    fun getFeedings(selectedDate: String): LiveData<List<Feeding>> {
        return datasource.getFeedings(selectedDate)
    }

    fun getFeeding(id: Int): LiveData<Feeding> {
        return datasource.getFeeding(id)
    }

    suspend fun updateFeeding(feeding: Feeding) {
        datasource.updateFeeding(feeding)
    }

    fun insertSleep(sleep: Sleep) {
        datasource.insertSleep(sleep)
    }

    fun getSleeps(selectedDate: String): LiveData<List<Sleep>> {
        return datasource.getSleeps(selectedDate)
    }

    fun insertSymptoms(symptoms: Symptoms) {
        datasource.insertSymptoms(symptoms)
    }

    fun getSymptoms(selectedDate: String): LiveData<List<Symptoms>> {
        return datasource.getSymptoms(selectedDate)
    }
}