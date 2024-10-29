package com.android.data.database

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.android.model.User

/**
 * Created by BM Anderson on 28/10/2024.
 */
class DataSharedPreferences(
    private val context: Context,
    storageName: String
) : BaseSharedPreferences() {

    init {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            storageName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var deviceToken: String?
        get() = this[DEVICE_TOKEN]
        set(value) = this.set(DEVICE_TOKEN, value)

    var accessToken: String?
        get() = this[PREFS_ACCESS_TOKEN]
        set(value) = this.set(PREFS_ACCESS_TOKEN, value)

    var refreshToken: String?
        get() = this[PREFS_REFRESH_TOKEN]
        set(value) = this.set(PREFS_REFRESH_TOKEN, value)

    var currentUser: User?
        get() = this[USER_INFORMATION]
        set(value) = this.set(USER_INFORMATION, value)



    fun logout() {
        accessToken = null
        refreshToken = null
        currentUser = null

        NotificationManagerCompat.from(context).cancelAll()
    }

    companion object {
        const val PREFS_ACCESS_TOKEN = "accessToken"
        const val PREFS_REFRESH_TOKEN = "refreshToken"
        const val DEVICE_TOKEN = "DEVICE_TOKEN"
        const val USER_INFORMATION = "USER_INFORMATION"
    }

}