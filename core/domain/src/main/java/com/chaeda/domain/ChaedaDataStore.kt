package com.chaeda.domain

interface ChaedaDataStore {
    var userToken: String
    var refreshToken: String
    var isLogin: Boolean

    fun clearLocalPref()
}
