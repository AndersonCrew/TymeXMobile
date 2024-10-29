package com.android.data.scope

import okhttp3.Interceptor
import okhttp3.Response

class S3HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().apply {
            addHeader("content-type", "multipart/form-data;")
            addHeader("cache-control", "no-cache")
        }.build())
    }
}