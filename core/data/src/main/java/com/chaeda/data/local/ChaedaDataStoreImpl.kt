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

    override var refreshToken: String
        get() = userPref.getString(PREF_REFRESH_TOKEN, "") ?: ""
        set(value) = userPref.edit { putString(PREF_REFRESH_TOKEN, value) }

    override var isLogin: Boolean
        get() = userPref.getBoolean(PREF_IS_LOGIN, false)
        set(value) = userPref.edit { putBoolean(PREF_IS_LOGIN, value) }

    override fun clearLocalPref() = userPref.edit { clear() }

    companion object {
        private const val PREF_USER_TOKEN = "USER_TOKEN"
        private const val PREF_REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val PREF_IS_LOGIN = "IS_LOGIN"
    }
}
