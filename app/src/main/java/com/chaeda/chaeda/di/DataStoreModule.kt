package com.chaeda.chaeda.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.chaeda.chaeda.BuildConfig
import com.chaeda.data.local.ChaedaDataStoreImpl
import com.chaeda.domain.ChaedaDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val APP_PREFERENCES_NAME = "USER_DATA"

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = if (BuildConfig.DEBUG) {
        context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)
    } else {
        EncryptedSharedPreferences.create(
            APP_PREFERENCES_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    @Provides
    @Singleton
    fun provideDataStore(chaedaDataStore: ChaedaDataStoreImpl): ChaedaDataStore = chaedaDataStore
}