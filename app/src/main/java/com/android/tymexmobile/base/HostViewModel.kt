package com.android.tymexmobile.base

import com.android.data.UseCaseResult
import com.android.data.database.DataSharedPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
abstract class HostViewModel : BaseViewModel() {

    @Inject
    lateinit var prefs: DataSharedPreferences

    private var _uiState: MutableSharedFlow<UseCaseResult<*>> = MutableSharedFlow()
    val uiState = _uiState.asSharedFlow()

    fun <T> Flow<UseCaseResult<T>>.invoke(
        useCase: (suspend (UseCaseResult.Feature) -> Unit)? = null,
        onCompletion: (suspend () -> Unit)? = null,
        hasLoading: Boolean? = true,
        isDismissLoadingWhenSuccess: Boolean? = true
    ) = execute {

        onCompletion { onCompletion?.invoke() }.onStart {
            delay(300)
            if (hasLoading == true) emit(UseCaseResult.Loading)
        }.collect { result ->
                when (result) {
                    is UseCaseResult.Feature -> {
                        delay(500)
                        if (isDismissLoadingWhenSuccess == true) {
                            _uiState.emit(UseCaseResult.UnLoading)
                        }

                        useCase?.invoke(result)
                    }

                    else -> {
                        _uiState.emit(result)
                    }
                }
            }
    }

    suspend fun <T> forceEmitResult(result: UseCaseResult<T>) {
        _uiState.emit(result)
    }
}