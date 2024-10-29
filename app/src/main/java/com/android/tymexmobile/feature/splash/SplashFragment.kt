package com.android.tymexmobile.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.data.database.DataSharedPreferences
import com.android.tymexmobile.base.BaseFragment
import com.android.tymexmobile.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by BM Anderson on 28/10/2024.
 */

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private var binding: FragmentSplashBinding?= null
    override var enableBackPressedDispatcher: Boolean
        get() = super.enableBackPressedDispatcher
        set(value) {
            true
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var a = mPref.deviceToken
    }
}