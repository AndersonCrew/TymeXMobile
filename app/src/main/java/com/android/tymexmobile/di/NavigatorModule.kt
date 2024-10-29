package com.android.tymexmobile.di

import com.android.tymexmobile.feature.detail_user.DetailUserNavigator
import com.android.tymexmobile.feature.detail_user.DetailUserNavigatorImpl
import com.android.tymexmobile.feature.home.HomeNavigator
import com.android.tymexmobile.feature.home.HomeNavigatorImpl
import com.android.tymexmobile.feature.splash.SplashNavigator
import com.android.tymexmobile.feature.splash.SplashNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

/**
 * Created by BM Anderson on 29/10/2024.
 */
@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun splashNavigator(navigator: SplashNavigatorImpl): SplashNavigator

    @Binds
    abstract fun homeNavigator(navigator: HomeNavigatorImpl): HomeNavigator

    @Binds
    abstract fun detailUserNavigator(navigator: DetailUserNavigatorImpl): DetailUserNavigator
}