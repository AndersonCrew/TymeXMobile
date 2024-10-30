package com.android.tymexmobile.base

import androidx.fragment.app.Fragment
import com.android.tymexmobile.R

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface HostFragmentNavigator : BaseFragmentNavigator {

}

abstract class HostFragmentNavigatorImpl(fragment: Fragment) :
    HostFragmentNavigator,
    BaseFragmentNavigatorImpl(fragment) {

    override val navHostFragmentId: Int
        get() = R.id.fcvHostNavHostFragment
}