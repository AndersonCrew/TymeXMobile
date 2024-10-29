package com.android.data.scope

import com.android.data.database.DataSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.util.GregorianCalendar
import java.util.concurrent.TimeUnit

/**
 * Created by BM Anderson on 29/10/2024.
 */
class HeaderInterceptor(
    private val prefs: DataSharedPreferences
) : Interceptor {

    private val accessToken: String?
        get() = prefs[DataSharedPreferences.PREFS_ACCESS_TOKEN]

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val useRefreshToken = original.header("use-refresh-token") != null
        return chain.proceed(chain.request().newBuilder().apply {
            addHeader("Accept", "application/json")
            addHeader("os", "ANDROID")
            addHeader(
                "timezone", TimeUnit.HOURS.convert(
                    GregorianCalendar().timeZone.rawOffset.toLong(), TimeUnit.MILLISECONDS
                ).toString()
            )

            if (accessToken?.isEmpty() == false) {
                if (useRefreshToken) {
                    addHeader("Authorization", "Bearer ${prefs.refreshToken}")
                } else {
                    addHeader("Authorization", "Bearer $accessToken")
                }
            }
        }.build())
    }
}