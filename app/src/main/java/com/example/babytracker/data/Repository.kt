package com.example.babytracker.data

import androidx.lifecycle.LiveData
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.entities.Sleep
import com.example.babytracker.data.entities.Symptoms
import com.example.babytracker.data.local.BabyTrackerDao
import com.example.babytracker.data.local.ItemRoomDatabase
import kotlinx.coroutines.flow.Flow

class Repository(private val datasource: BabyTrackerDao) {

    fun insertFeeding(feeding: Feeding) {
        datasource.insertFeeding(feeding)
    }

    fun getFeedings(selectedDate: String): LiveData<List<Feeding>> {
        return datasource.getFeedings(selectedDate)
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