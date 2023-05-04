package com.example.phonebook.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {
    override fun <Activity : ViewModel> create(modelClass: Class<Activity>): Activity {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as Activity
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}