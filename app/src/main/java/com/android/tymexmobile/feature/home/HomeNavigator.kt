package com.android.tymexmobile.feature.home

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.android.model.User
import com.android.tymexmobile.base.BaseFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigator
import com.android.tymexmobile.base.HostFragmentNavigatorImpl
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface HomeNavigator : HostFragmentNavigator {

    fun navigateHomeToDetailUser(user: User)

}

class HomeNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    HomeNavigator {
    override fun navigateHomeToDetailUser(user: User) {
        val bundle = bundleOf("user" to user)
        navigate(
            HomeFragmentDirections.actionHomeToDetail(),
            BaseFragmentNavigator.NavigateAnimation.SLIDE,
            bundle
        )
    }

}