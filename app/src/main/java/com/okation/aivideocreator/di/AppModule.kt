package com.okation.aivideocreator.di

import android.content.Context
import com.okation.aivideocreator.data.repository.Repository
import com.okation.aivideocreator.data.local.BabyTrackerDao
import com.okation.aivideocreator.data.local.ItemRoomDatabase
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