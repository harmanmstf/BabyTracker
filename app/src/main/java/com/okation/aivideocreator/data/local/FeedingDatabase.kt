package com.okation.aivideocreator.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.okation.aivideocreator.data.entities.Feeding
import com.okation.aivideocreator.data.entities.Sleep
import com.okation.aivideocreator.data.entities.Symptoms

@Database(
    entities = [Feeding::class, Sleep::class, Symptoms::class],
    version = 4,
    exportSchema = false
)
abstract class ItemRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): BabyTrackerDao

    companion object {
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null

        fun getDatabase(context: Context): ItemRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}