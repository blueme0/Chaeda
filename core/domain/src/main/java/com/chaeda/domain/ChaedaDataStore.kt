package com.chaeda.domain

interface ChaedaDataStore {
    var userToken: String

    fun clearLocalPref()
}
