package com.android.tymexmobile.feature.detail_user

import androidx.fragment.app.Fragment
import com.android.tymexmobile.base.BaseFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigatorImpl
import com.android.tymexmobile.feature.splash.SplashFragmentDirections
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface DetailUserNavigator : HostFragmentNavigator {

}

class DetailUserNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    DetailUserNavigator {

}