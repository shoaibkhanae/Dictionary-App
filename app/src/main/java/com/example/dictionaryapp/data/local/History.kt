package com.example.dictionaryapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
)
