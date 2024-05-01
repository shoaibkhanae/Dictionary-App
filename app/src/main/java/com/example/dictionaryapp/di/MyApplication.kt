package com.example.dictionaryapp.di

import android.app.Application
import com.example.dictionaryapp.data.local.WordDatabase
import com.example.dictionaryapp.data.remote.DictionaryApiService
import com.example.dictionaryapp.data.remote.RetrofitHelper
import com.example.dictionaryapp.data.repository.Repository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application()