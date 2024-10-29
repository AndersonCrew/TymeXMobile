package com.android.tymexmobile.feature.home

import androidx.fragment.app.Fragment
import com.android.tymexmobile.base.BaseFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigatorImpl
import com.android.tymexmobile.feature.splash.SplashFragmentDirections
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface HomeNavigator : HostFragmentNavigator {

    fun navigateHomeToDetailUser()

}

class HomeNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    HomeNavigator {
    override fun navigateHomeToDetailUser() {
        navigate(
            SplashFragmentDirections.actionSplashToHome(),
            BaseFragmentNavigator.NavigateAnimation.FADE
        )
    }

}