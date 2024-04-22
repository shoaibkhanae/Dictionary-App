package com.example.dictionaryapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.data.local.Define
import com.example.dictionaryapp.data.local.History
import com.example.dictionaryapp.data.model.Definition
import com.example.dictionaryapp.data.repository.Repository
import com.example.dictionaryapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val definition: LiveData<Resource<Definition>>
        get() = repository.definition

    val allWords = repository.allWords.asLiveData()

    val allHistory = repository.allHistory.asLiveData()

    fun searchWord(word: String) {
        viewModelScope.launch {
            repository.getDefinition(word)
        }
    }

    fun insert(define: Define) {
        viewModelScope.launch {
            repository.insert(define)
        }
    }

    fun insert(history: History) {
        viewModelScope.launch {
            repository.insert(history)
        }
    }

    fun delete(history: History) {
        viewModelScope.launch {
            repository.delete(history)
        }
    }

    fun delete(define: Define) {
        viewModelScope.launch {
            repository.delete(define)
        }
    }
}