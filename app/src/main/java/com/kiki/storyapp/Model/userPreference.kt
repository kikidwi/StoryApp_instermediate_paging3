package com.kiki.storyapp.Model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class userPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<userModel> {
        return dataStore.data.map { preferences ->
            userModel(
                preferences[UID] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[STATE_KEY] ?: false,
                token = preferences[TOKEN_KEY] ?: ""
            )
        }
    }

    suspend fun saveUser(user: userModel) {
        dataStore.edit { preferences ->
            preferences[UID] = user.uId
            preferences[NAME_KEY] = user.name
            preferences[STATE_KEY] = user.isLogin
            preferences[TOKEN_KEY] = user.token
        }
    }

    suspend fun login(token: String) {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
            preferences[TOKEN_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: userPreference? = null

        private val UID = stringPreferencesKey("uId")
        private val NAME_KEY = stringPreferencesKey("name")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val TOKEN_KEY = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): userPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = userPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }



}