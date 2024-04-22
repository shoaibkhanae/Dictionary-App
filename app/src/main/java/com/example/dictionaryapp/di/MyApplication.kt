package com.example.dictionaryapp.di

import android.app.Application
import com.example.dictionaryapp.data.local.WordDatabase
import com.example.dictionaryapp.data.remote.DictionaryApiService
import com.example.dictionaryapp.data.remote.RetrofitHelper
import com.example.dictionaryapp.data.repository.Repository

class MyApplication: Application() {
    private val api by lazy { RetrofitHelper.getRetrofitInstance().create(DictionaryApiService::class.java) }
    private val database by lazy { WordDatabase.getDatabase(applicationContext) }
    val repository by lazy { Repository(api,database.wordDao()) }
}