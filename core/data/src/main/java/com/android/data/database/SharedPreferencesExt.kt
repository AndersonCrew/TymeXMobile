package com.android.data.database

import android.content.SharedPreferences
import com.android.extension.transformJsonToObject
import com.android.extension.transformToJson

/**
 * Created by BM Anderson on 28/10/2024.
 */
fun SharedPreferences.execute(operation: (SharedPreferences.Editor) -> Unit) {
    with(edit()) {
        operation(this)
        apply()
    }
}

inline operator fun <reified T : Any?> SharedPreferences.set(key: String, value: T?) =
    when (value) {
        is String? -> execute { it.putString(key, value) }
        is Int -> execute { it.putInt(key, value) }
        is Boolean -> execute { it.putBoolean(key, value) }
        is Float -> execute { it.putFloat(key, value) }
        is Long -> execute { it.putLong(key, value) }
        is Double -> execute { it.putString(key, value.toString()) }
        else -> execute { it.putString(key, value.transformToJson()) }
    }

inline fun <reified T : Any> SharedPreferences.getData(key: String): T? =
    if (contains(key)) {
        when (T::class) {
            Boolean::class -> getBoolean(key, false) as T?
            String::class -> { getString(key, null) as T? }
            Int::class -> getInt(key, 0) as T?
            Float::class -> getFloat(key, 0f) as T?
            Long::class -> getLong(key, 0L) as T?
            Double::class -> (getString(key, "-1.0")?.toDoubleOrNull() ?: -1.0) as T
            else -> getDataClass(key)
        }
    } else null

inline fun <reified T : Any> SharedPreferences.getDataClass(key: String): T? =
    if (contains(key)) {
        try {
            val arr = getString(key, null)
            if (arr.isNullOrEmpty()) {
                null
            } else {
                arr.transformJsonToObject<T>()
            }
        } catch (ex: Exception) {
            null
        }
    } else null