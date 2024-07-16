package com.kkdev.notable.repository

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import com.kkdev.notable.data.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferences(private val context: Context) {

    companion object {
        private val SORT_TYPE_KEY = stringPreferencesKey("sort_type")
        private val Context.dataStore by preferencesDataStore(name = "user_preferences")
    }

    suspend fun saveSortType(sortType: SortType) {
        val sortTypeString = sortType.name
        context.dataStore.edit { preferences ->
            preferences[SORT_TYPE_KEY] = sortTypeString
        }
    }

    fun getSortType(): Flow<SortType> {
        return context.dataStore.data.map { preferences ->
            val sortTypeString = preferences[SORT_TYPE_KEY] ?: SortType.NOTES_TITLE.name
            SortType.valueOf(sortTypeString)
        }
    }
}
