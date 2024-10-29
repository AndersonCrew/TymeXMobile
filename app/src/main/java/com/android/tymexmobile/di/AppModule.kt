package com.android.tymexmobile.di

import android.content.Context
import com.android.TymeXApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by BM Anderson on 28/10/2024.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesApplicationInstance(@ApplicationContext context: Context): TymeXApplication {
        return context as TymeXApplication
    }
}