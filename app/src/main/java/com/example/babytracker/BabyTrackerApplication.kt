package com.example.babytracker

import android.app.Application
import com.example.babytracker.data.local.ItemRoomDatabase

class BabyTrackerApplication: Application() {


        val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
    }
