package com.example.babytracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.babytracker.data.entities.Feeding
import com.example.babytracker.data.entities.Sleep
import com.example.babytracker.data.entities.Symptoms


@Dao
interface BabyTrackerDao {
    @Query("SELECT * FROM feeding WHERE date = :selectedDate")
    fun getFeedings(selectedDate: String): LiveData<List<Feeding>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeeding(feeding: Feeding)

    @Query("SELECT * FROM sleep WHERE date = :selectedDate")
    fun getSleeps(selectedDate: String): LiveData<List<Sleep>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSleep(sleep: Sleep)

    @Query("SELECT * FROM symptoms WHERE date = :selectedDate")
    fun getSymptoms(selectedDate: String): LiveData<List<Symptoms>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSymptoms(symptoms: Symptoms)
}