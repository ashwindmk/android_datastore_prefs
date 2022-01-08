package com.ashwin.android.datastoreprefs

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val SUB_TAG = "PreferencesManager"

private const val USER_PREFS_FILE_NAME = "user_prefs"

private val Context.userPrefs by preferencesDataStore(name = USER_PREFS_FILE_NAME)

class PreferencesManager(private val context: Context) {
    private object PreferencesKey {
        val COUNTER = intPreferencesKey("counter")
        val THEME = stringPreferencesKey("theme")
    }

    val getCounter: Flow<Int> = context.userPrefs.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(Constant.APP_TAG, "$SUB_TAG: Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKey.COUNTER] ?: 0
        }

    suspend fun incrementCounter() {
        context.userPrefs.edit { mutablePreferences ->
            val currentCounterValue = mutablePreferences[PreferencesKey.COUNTER] ?: 0
            mutablePreferences[PreferencesKey.COUNTER] = currentCounterValue + 1
        }
    }

    val getTheme: Flow<Theme> = context.userPrefs.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(Constant.APP_TAG, "$SUB_TAG: Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val theme = Theme.valueOf(
                preferences[PreferencesKey.THEME] ?: Theme.DEFAULT.name
            )
            theme
        }

    suspend fun updateTheme(theme: Theme) {
        context.userPrefs.edit { mutablePreferences ->
            mutablePreferences[PreferencesKey.THEME] = theme.name
        }
    }
}
