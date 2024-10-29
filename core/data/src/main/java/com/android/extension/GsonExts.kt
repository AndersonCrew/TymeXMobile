package com.android.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

/**
 * Created by BM Developer.
 */
fun <T> T.transformToJson(): String? {
    return try {
        Gson().toJson(this)
    } catch (ex: Exception) {
        null
    }
}

inline fun <reified T> String.transformJsonToObject(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (ex: Exception) {
        null
    }
}

inline fun <reified T> String?.transformJsonObjectToObject(): T? {
    return try {
        if (this == null) null
        else {
            val jSONObject = JSONObject(this)
            jSONObject.toString().transformJsonToObject<T>()
        }
    } catch (ex: Exception) {
        null
    }
}

inline fun <reified T> Gson.fromJsonList(json: String) =
    this.fromJson<List<T>>(json, object : TypeToken<List<T>>() {}.type)