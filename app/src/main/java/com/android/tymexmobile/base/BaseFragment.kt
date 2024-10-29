package com.android.tymexmobile.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.android.data.database.DataSharedPreferences
import javax.inject.Inject

/**
 * Created by BM Anderson on 28/10/2024.
 */
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var mPref: DataSharedPreferences

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