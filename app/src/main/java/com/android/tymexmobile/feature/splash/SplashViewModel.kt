package com.android.tymexmobile.feature.splash

import com.android.data.database.DataSharedPreferences
import com.android.tymexmobile.base.BaseViewEvent
import com.android.tymexmobile.base.HostViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


/**
 * Created by BM Anderson on 29/10/2024.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(private val mPref: DataSharedPreferences): HostViewModel() {
    sealed class ViewEvent : BaseViewEvent{
        data object NavigateToLogin : ViewEvent()
        data object NavigateToHome : ViewEvent()
    }

    private val _shareFlow = MutableSharedFlow<ViewEvent>()
    val shareFlow: SharedFlow<ViewEvent> = _shareFlow.asSharedFlow()
    fun checkAuthentication() = execute {
        delay(1000)
        //Check Authentication
        if(mPref.accessToken.isNullOrEmpty()) {
            //Navigate to Login
            _shareFlow.emit(ViewEvent.NavigateToLogin)
        } else {
            //Navigate to Home
            _shareFlow.emit(ViewEvent.NavigateToHome)
        }
    }
}