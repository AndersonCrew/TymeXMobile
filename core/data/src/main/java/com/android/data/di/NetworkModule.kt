package com.android.data.di

import com.android.data.BuildConfig
import com.android.data.database.DataSharedPreferences
import com.android.data.scope.HeaderInterceptor
import com.android.data.scope.HeaderInterceptorModule
import com.android.data.scope.HttpLoggingInterceptorModule
import com.android.data.scope.OkHttpClientApp
import com.android.data.scope.RetrofitBuilder
import com.android.data.scope.TokenAuthenticator
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by BM Anderson on 28/10/2024.
 */
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

    @HttpLoggingInterceptorModule
    @Provides
    @Singleton
    fun providerHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
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
    }
}