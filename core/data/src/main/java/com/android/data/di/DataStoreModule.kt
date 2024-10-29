package com.android.data.di

import android.content.Context
import com.android.data.database.DataSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by BM Anderson on 28/10/2024.
 */
@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Named("StorageName")
    @Provides
    @Singleton
    fun providesStorageName() = "app_secret_shared_prefs"

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        @Named("StorageName") storageName: String
    ) = DataSharedPreferences(context, storageName)
}