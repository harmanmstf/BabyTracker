package com.example.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feeding")
data class Feeding(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: String,
    val amount: String,
    val note: String,
    val date: String
)