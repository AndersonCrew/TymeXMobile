package com.android.data.di

import com.android.data.scope.RetrofitBuilder
import com.android.data.service.TestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by BM Anderson on 28/10/2024.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun testApiService(@RetrofitBuilder retrofit: Retrofit): TestApiService =
        retrofit.create(TestApiService::class.java)


}