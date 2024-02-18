package com.chaeda.chaeda.util.extension

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Parcelable> Bundle.getCompatibleParcelableExtra(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> getParcelable(key) as? T
}

inline fun <reified T : Serializable> Bundle.getCompatibleSerializableExtra(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> getSerializable(key) as? T
}
