package com.okation.aivideocreator.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep")
data class Sleep(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var fellSleepTime: String,
    var wokeUpTime: String,
    var note: String,
    var date: String
)