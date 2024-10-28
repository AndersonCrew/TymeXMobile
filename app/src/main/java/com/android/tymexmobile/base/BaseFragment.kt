package com.android.tymexmobile.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

/**
 * Created by BM Anderson on 28/10/2024.
 */
abstract class BaseFragment : Fragment() {
    open var enableBackPressedDispatcher: Boolean = false
    private fun setupBackPressedDispatcher() {
        val callback = object : OnBackPressedCallback(enableBackPressedDispatcher) {
            override fun handleOnBackPressed() {
                if (enableBackPressedDispatcher) {
                    // do stuff
                } else {
                    isEnabled = false
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }
}