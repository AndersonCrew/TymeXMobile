package com.android.data.database

import android.content.SharedPreferences

/**
 * Created by BM Anderson on 28/10/2024.
 */
abstract class BaseSharedPreferences {

    lateinit var sharedPreferences: SharedPreferences

    inline fun <reified T> set(key: String, value: T) {
        sharedPreferences[key] = value
    }

    inline operator fun <reified T: Any> get(key: String): T? =
        sharedPreferences.getData(key)

    inline fun <reified T: Any> getClass(key: String): T? =
        sharedPreferences.getDataClass(key)

    fun remove(key: String) {
        sharedPreferences.execute { it.remove(key) }
    }

    fun clearAll() {
        sharedPreferences.execute { it.clear() }
    }

}