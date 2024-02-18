package com.chaeda.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.chaeda.domain.ChaedaDataStore
import javax.inject.Inject

class ChaedaDataStoreImpl @Inject constructor(
    private val userPref: SharedPreferences
) : ChaedaDataStore {
    override var userToken: String
        get() = userPref.getString(PREF_USER_TOKEN, "") ?: ""
        set(value) = userPref.edit { putString(PREF_USER_TOKEN, value) }

    override fun clearLocalPref() = userPref.edit { clear() }

    companion object {
        private const val PREF_USER_TOKEN = "USER_TOKEN"
    }
}
