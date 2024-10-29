package com.android.tymexmobile.feature.splash

import androidx.fragment.app.Fragment
import com.android.tymexmobile.base.BaseFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigatorImpl
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface SplashNavigator : HostFragmentNavigator {

    fun navigateSplashToHome()

    fun navigateSplashToLogin()

}

class SplashNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    SplashNavigator {
    override fun navigateSplashToHome() {
        navigate(
            SplashFragmentDirections.actionSplashToHome(),
            BaseFragmentNavigator.NavigateAnimation.FADE
        )
    }

    override fun navigateSplashToLogin() {
        navigate(
            SplashFragmentDirections.actionSplashToHome(),
            BaseFragmentNavigator.NavigateAnimation.FADE
        )
    }
}