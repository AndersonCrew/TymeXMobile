package com.android.tymexmobile.feature.detail_user

import com.android.domain.usecase.GetUserDetailResult
import com.android.domain.usecase.GetUserDetailUseCase
import com.android.model.User
import com.android.tymexmobile.base.BaseViewState
import com.android.tymexmobile.base.HostViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


/**
 * Created by BM Anderson on 29/10/2024.
 */
@HiltViewModel
class DetailUserViewModel @Inject constructor(private val getUserDetailUseCase: GetUserDetailUseCase): HostViewModel() {

    sealed class ViewState : BaseViewState {
        data class GetUserSuccess(val list: User?) : ViewState()
        data object EmptyState: ViewState()
    }

    private val _stateFlow = MutableStateFlow<ViewState>(ViewState.EmptyState)
    val stateFlow: Flow<ViewState> = _stateFlow.asStateFlow()
    fun getUser(userName: String) = execute {
        getUserDetailUseCase(userName).invoke(useCase = {
            if(it is GetUserDetailResult.GetUserDetailSuccess) {
                _stateFlow.emit(ViewState.GetUserSuccess(it.user))
            }
        })
    }
}