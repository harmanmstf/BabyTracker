package com.example.babytracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.babytracker.data.entities.Feeding
import kotlinx.coroutines.flow.Flow


@Dao
interface BabyTrackerDao {
    @Query("SELECT * FROM feeding")
    fun getFeedings(): Flow<List<Feeding>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insertFeeding(feeding: Feeding)
}