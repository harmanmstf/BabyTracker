package com.okation.aivideocreator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class Symptoms(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var time: String,
    var symptomName: String,
    var note: String,
    var date: String
)