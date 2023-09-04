package com.example.babytracker

import android.app.Application
import com.example.babytracker.data.local.ItemRoomDatabase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class BabyTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
