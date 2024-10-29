package com.android.tymexmobile.base

import androidx.fragment.app.Fragment
import com.android.tymexmobile.R

interface HostFragmentNavigator : BaseFragmentNavigator {

}

abstract class HostFragmentNavigatorImpl(fragment: Fragment) :
    HostFragmentNavigator,
    BaseFragmentNavigatorImpl(fragment) {

    override val navHostFragmentId: Int
        get() = R.id.fcvHostNavHostFragment
}