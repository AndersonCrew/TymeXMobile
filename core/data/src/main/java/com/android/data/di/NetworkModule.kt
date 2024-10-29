package com.android.data.di

import com.android.data.database.DataSharedPreferences
import com.android.data.scope.HeaderInterceptor
import com.android.data.scope.HeaderInterceptorModule
import com.android.data.scope.HeaderInterceptorS3
import com.android.data.scope.S3HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**HeaderInterceptor **/
    @HeaderInterceptorModule
    @Provides
    @Singleton
    fun providerHeaderInterceptor(
        prefs: DataSharedPreferences
    ): Interceptor = HeaderInterceptor(prefs)

    @HeaderInterceptorS3
    @Provides
    @Singleton
    fun providerHeaderS3Interceptor(): Interceptor = S3HeaderInterceptor()

   /* *//** LoggingInterceptor **//*
    @HttpLoggingInterceptorModule
    @Provides
    @Singleton
    fun providerHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

    @HttpLoggingInterceptorS3Module
    @Provides
    @Singleton
    fun providerHttpLoggingInterceptorS3(): Interceptor = HttpLoggingInterceptor().apply {
        level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.HEADERS
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

    @OkHttpClientApp
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @HeaderInterceptorModule header: Interceptor,
        @HttpLoggingInterceptorModule logging: Interceptor,
        authenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(45L, TimeUnit.SECONDS)
            .readTimeout(45L, TimeUnit.SECONDS)
            .writeTimeout(45L, TimeUnit.SECONDS)
            .authenticator(authenticator)
            .addInterceptor(header)
            .addInterceptor(logging)
            .build()
    }

    @RetrofitBuilder
    @Singleton
    @Provides
    fun provideRetrofit(@OkHttpClientApp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }*/
}