package com.android.tymexmobile.feature.home

import androidx.constraintlayout.motion.utils.ViewState
import com.android.domain.usecase.GetUsersUseCase
import com.android.domain.usecase.GetUsersUseResult
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
class HomeViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase): HostViewModel() {

    private var hasLoadMore = false
    sealed class ViewState : BaseViewState {
        data class GetListUserSuccess(val list: List<User>?) : ViewState()
        data object EmptyState: ViewState()
    }

    private val _stateFlow = MutableStateFlow<ViewState>(ViewState.EmptyState)
    val stateFlow: Flow<ViewState> = _stateFlow.asStateFlow()
    fun getUser(page: Int) = execute {
        getUsersUseCase(page).invoke(useCase = {
           if(it is GetUsersUseResult.GetUsersUseResultSuccess) {
               _stateFlow.emit(ViewState.GetListUserSuccess(it.data))
               hasLoadMore = false
           }
        })
    }

    fun hasLoadMore(): Boolean = hasLoadMore
}