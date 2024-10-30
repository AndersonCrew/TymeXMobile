package com.android.tymexmobile.feature.splash

import androidx.fragment.app.viewModels
import com.android.tymexmobile.base.BaseFragment
import com.android.tymexmobile.base.BaseViewEvent
import com.android.tymexmobile.base.BaseViewState
import com.android.tymexmobile.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by BM Anderson on 28/10/2024.
 */

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()

    override var hasFullScreen: Boolean = true

    override var enableBackPressedDispatcher = true

    @Inject lateinit var navigator: SplashNavigator
    override fun bindViewEvents() {
        super.bindViewEvents()
        //Check checkAuthentication of current user
        viewModel.checkAuthentication()
    }

    override fun bindViewModel(): Unit = with(viewModel) {
        super.bindViewModel()
        shareFlow bindTo ::bindShareFlow
    }

    override fun bindShareFlow(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is SplashViewModel.ViewEvent.NavigateToLogin -> {
                /**UnAuthentication -> Navigate to LoginFragment*/
                navigator.navigateSplashToLogin()
            }

            is SplashViewModel.ViewEvent.NavigateToHome -> {
                /**Authenticated -> Navigate to HomeFragment*/
                navigator.navigateSplashToHome()
            }
        }
    }

    override fun bindStateFlow(viewState: BaseViewState) {

    }
}