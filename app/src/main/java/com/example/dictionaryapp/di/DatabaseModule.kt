package com.example.dictionaryapp.di

import android.content.Context
import androidx.room.Room
import com.example.dictionaryapp.data.local.WordDao
import com.example.dictionaryapp.data.local.WordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideWordDao(database: WordDatabase): WordDao {
        return database.wordDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): WordDatabase {
        return Room.databaseBuilder(
            app,
            WordDatabase::class.java,
            "word_database"
        ).build()
    }
}