package com.android.data.scope

import javax.inject.Qualifier

/**
 * Created by BM Anderson on 29/10/2024.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderInterceptorModule

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpLoggingInterceptorModule

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitBuilder

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpClientApp

