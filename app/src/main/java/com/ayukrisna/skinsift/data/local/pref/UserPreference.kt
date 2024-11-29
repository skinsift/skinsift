package com.ayukrisna.skinsift.data.local.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference (private val dataStore: DataStore<Preferences>) {

    private var INSTANCE: UserPreference? = null

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val ID_KEY = stringPreferencesKey("id")
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[ID_KEY] = user.id
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERNAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[ID_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}