package com.example.dictionaryapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dictionaryapp.data.local.Define
import com.example.dictionaryapp.data.local.History
import com.example.dictionaryapp.data.local.WordDao
import com.example.dictionaryapp.data.remote.DictionaryApiService
import com.example.dictionaryapp.data.model.Definition
import com.example.dictionaryapp.utils.Resource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val service: DictionaryApiService,
    private val wordDao: WordDao
    ) {

    private val _definitions = MutableLiveData<Resource<Definition>>()
    val definition: LiveData<Resource<Definition>> = _definitions

    val allWords: Flow<List<Define>> = wordDao.getAllWords()
    val allHistory: Flow<List<History>> = wordDao.getAllHistory()

    suspend fun getDefinition(word: String) {
        try {
            _definitions.postValue(Resource.Loading())
            val result = service.getDefinition(word)
            if (result.body() != null) {
                _definitions.postValue(Resource.Success(result.body()))
            }
        } catch (e: Exception) {
           _definitions.postValue(Resource.Error(e.message.toString()))
        }
    }

    suspend fun insert(define: Define) {
        wordDao.insert(define)
    }

    suspend fun insert(history: History) {
        wordDao.insert(history)
    }

    suspend fun delete(history: History) {
        wordDao.delete(history)
    }

    suspend fun delete(define: Define) {
        wordDao.delete(define)
    }
}