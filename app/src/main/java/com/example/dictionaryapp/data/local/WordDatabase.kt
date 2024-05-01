package com.example.dictionaryapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Define::class,History::class], version = 3, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

}