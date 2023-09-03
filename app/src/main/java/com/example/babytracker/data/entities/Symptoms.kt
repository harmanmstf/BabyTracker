package com.example.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class Symptoms(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: String,
    val symptomName: String,
    val note: String,
    val date: String
)