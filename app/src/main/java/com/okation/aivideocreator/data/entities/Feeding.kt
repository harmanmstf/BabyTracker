package com.okation.aivideocreator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feeding")
data class Feeding(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var time: String,
    var amount: String,
    var note: String,
    var date: String
)