package com.ashwin.android.datastoreprefs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesManager = PreferencesManager(application)

    val counter = preferencesManager.getCounter.asLiveData()

    val theme = preferencesManager.getTheme.asLiveData()

    fun incrementCounter() = viewModelScope.launch(Dispatchers.IO) {
        preferencesManager.incrementCounter()
    }

    fun updateTheme(theme: Theme) = viewModelScope.launch(Dispatchers.IO) {
        preferencesManager.updateTheme(theme)
    }
}
