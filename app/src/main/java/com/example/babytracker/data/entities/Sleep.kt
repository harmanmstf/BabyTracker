package com.example.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep")
data class Sleep(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fellSleepTime: String,
    val wokeUpTime: String,
    val note: String,
    val date: String
)