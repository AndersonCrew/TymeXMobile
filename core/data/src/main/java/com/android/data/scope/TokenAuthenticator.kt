package com.android.data.scope

import com.android.data.BuildConfig
import com.android.data.database.DataSharedPreferences
import com.android.model.BaseModel
import com.android.model.User
import com.google.gson.Gson
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


/**
 * Created by BM Anderson on 29/10/2024.
 */
class TokenAuthenticator @Inject constructor(private val mPres: DataSharedPreferences) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.d("START REFRESH_TOKEN")
        val baseUrl = BuildConfig.BASE_URL + "auth/renew-access-token"
        var request: Request? = if (refreshToken(baseUrl)) {
            response.request.newBuilder().header("Authorization", "Bearer ${mPres.accessToken}").build()
        } else {
            null
        }

        return request
    }

    @Throws(IOException::class)
    fun refreshToken(url: String): Boolean {
        val refreshUrl = URL(url)
        val urlConnection = refreshUrl.openConnection() as HttpURLConnection
        urlConnection.doInput = true
        urlConnection.requestMethod = "GET"
        urlConnection.setRequestProperty("Authorization", "Bearer ${mPres.refreshToken}")
        urlConnection.useCaches = false
        val responseCode = urlConnection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = BufferedReader(InputStreamReader(urlConnection.inputStream))
            var inputLine: String?
            val response = StringBuffer()
            while (inputStream.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }

            inputStream.close()

            val result: RefreshTokenModel? = Gson().fromJson(
                response.toString(),
                RefreshTokenModel::class.java
            )
            //TODO save it to the sharedpreferences, storage bla bla ...
            //mPres.accessToken = result?.data?.accessToken
           // mPres.refreshToken = result?.data?.refreshToken
            Timber.d("END REFRESH_TOKEN")
            true
        } else {
            //cannot refresh
            false
        }
    }

    class RefreshTokenModel(
        var data: User? = null,
    ) : BaseModel()
}