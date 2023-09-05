package com.example.babytracker.di

import android.content.Context
import com.example.babytracker.data.repository.Repository
import com.example.babytracker.data.local.BabyTrackerDao
import com.example.babytracker.data.local.ItemRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        ItemRoomDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideBabyTrackerDao(db: ItemRoomDatabase) = db.itemDao()

    @Singleton
    @Provides
    fun provideRepository(
        localDataSource: BabyTrackerDao,
    ) =
        Repository(localDataSource)
}