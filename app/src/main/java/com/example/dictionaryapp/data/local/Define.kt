package com.example.dictionaryapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "words")
data class Define(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val definitionOfWord: String
)
