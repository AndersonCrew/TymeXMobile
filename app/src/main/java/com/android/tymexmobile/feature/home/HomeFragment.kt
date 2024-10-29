package com.android.tymexmobile.feature.home

import androidx.fragment.app.viewModels
import com.android.tymexmobile.base.BaseFragment
import com.android.tymexmobile.base.BaseViewEvent
import com.android.tymexmobile.base.BaseViewState
import com.android.tymexmobile.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by BM Anderson on 28/10/2024.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    override var enableBackPressedDispatcher = true
    override fun setupView() {
        super.setupView()
    }

    override fun bindShareFlow(viewEvent: BaseViewEvent) {

    }

    override fun bindStateFlow(viewState: BaseViewState) {

    }

}