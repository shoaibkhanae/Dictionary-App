package com.example.dictionaryapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(define: Define)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: History)

    @Delete
    suspend fun delete(define: Define)

    @Delete
    suspend fun delete(history: History)

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<Define>>

    @Query("SELECT * FROM history")
    fun getAllHistory(): Flow<List<History>>
}