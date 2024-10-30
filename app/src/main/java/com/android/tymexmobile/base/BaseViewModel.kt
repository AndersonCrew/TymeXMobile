package com.android.tymexmobile.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by BM Anderson on 29/10/2024.
 */
open class BaseViewModel() : ViewModel() {

    inline fun execute(
        coroutineContext: CoroutineContext = Dispatchers.IO,
        crossinline job: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineContext) {
        job.invoke(this)
    }

    override fun onCleared() {
        super.onCleared()
    }
}