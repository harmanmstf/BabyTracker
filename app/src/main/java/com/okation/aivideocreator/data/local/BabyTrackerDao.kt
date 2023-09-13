package com.okation.aivideocreator.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.okation.aivideocreator.data.entities.Feeding
import com.okation.aivideocreator.data.entities.Sleep
import com.okation.aivideocreator.data.entities.Symptoms


@Dao
interface BabyTrackerDao {
    @Query("SELECT * FROM feeding WHERE date = :selectedDate")
    fun getFeedings(selectedDate: String): LiveData<List<Feeding>>

    @Query("SELECT * from feeding WHERE id = :id")
    fun getFeeding(id: Int): LiveData<Feeding>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeding(feeding: Feeding)

    @Update
    suspend fun updateFeeding(feeding: Feeding)


    @Query("SELECT * FROM sleep WHERE date = :selectedDate")
    fun getSleeps(selectedDate: String): LiveData<List<Sleep>>

    @Query("SELECT * from sleep WHERE id = :id")
    fun getSleep(id: Int): LiveData<Sleep>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSleep(sleep: Sleep)

    @Update
    suspend fun updateSleep(sleep: Sleep)


    @Query("SELECT * FROM symptoms WHERE date = :selectedDate")
    fun getSymptoms(selectedDate: String): LiveData<List<Symptoms>>

    @Query("SELECT * from symptoms WHERE id = :id")
    fun getSymptom(id: Int): LiveData<Symptoms>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSymptoms(symptoms: Symptoms)

    @Update
    suspend fun updateSymptom(symptoms: Symptoms)
}