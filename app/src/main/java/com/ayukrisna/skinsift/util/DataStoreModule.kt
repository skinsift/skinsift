package com.ayukrisna.skinsift.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("app_preferences")

fun provideDataStore(context: Context) = context.dataStore