package com.example.mobilebank.utils.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object BankingDataStore {
    private const val PREFERENCES_DATA_STORE_NAME = "bank_pref"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATA_STORE_NAME)

    private val BALANCE_VISIBLE_KEY = booleanPreferencesKey("BALANCE_VISIBLE_KEY")

    suspend fun saveIsVisible(context: Context, isVisible: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BALANCE_VISIBLE_KEY] = isVisible
        }
    }

    fun getIsVisible(context: Context): Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[BALANCE_VISIBLE_KEY] ?: true
        }
}