package com.example.babytracker.data

import androidx.lifecycle.LiveData
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.local.BabyTrackerDao
import com.example.babytracker.data.local.ItemRoomDatabase
import kotlinx.coroutines.flow.Flow

class Repository(private val datasource: BabyTrackerDao) {

    // Function to insert a feeding record into the database
     fun insertFeeding(feeding: Feeding) {
        datasource.insertFeeding(feeding)
    }

    // Function to get feedings by date
    fun getFeedings(): Flow<List<Feeding>> {
        return datasource.getFeedings()
    }
}