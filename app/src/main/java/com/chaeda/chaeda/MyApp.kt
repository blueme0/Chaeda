package com.chaeda.chaeda

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setUpFlipper()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
        var isForeground = false
    }
}
