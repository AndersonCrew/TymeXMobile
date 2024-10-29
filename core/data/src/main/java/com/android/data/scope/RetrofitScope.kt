package com.android.data.scope

import javax.inject.Qualifier

/**
 * Created by BM Developer.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderInterceptorModule

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpLoggingInterceptorModule

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpLoggingInterceptorS3Module

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitBuilder

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitS3Builder

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpClientApp

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpClientS3

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderInterceptorS3

