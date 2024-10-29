package com.android.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.android.data.repository.TestRepository
import com.android.data.repository.TestRepositoryImpl
import com.android.data.service.TestApiService

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideAuthRepository(
        apiService: TestApiService
    ): TestRepository =
        TestRepositoryImpl(apiService)
}