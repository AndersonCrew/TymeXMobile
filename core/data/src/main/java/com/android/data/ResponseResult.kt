package com.android.data

import com.android.model.ErrorApi

/**
 * Created by BM Anderson on 29/10/2024.
 */
sealed interface ResponseResult<out T> {
    data class Success<T>(val data: T?) : ResponseResult<T>
    data class Error(val error: ErrorApi?) : ResponseResult<Nothing>
    abstract class Exception : ResponseResult<Nothing>
}

sealed class ResourceException: ResponseResult.Exception() {
    // When refreshToken fail
    // Use this object to back to login screen
    data object UnAuthorized : ResourceException()
    data object NetworkConnection: ResourceException()
    data object TimeoutConnection: ResourceException()
    data object ServerError: ResourceException()
    data object UnknownException: ResourceException()
}

sealed interface UseCaseResult<out T> {

    data object Loading : UseCaseResult<Nothing>
    data object UnLoading : UseCaseResult<Nothing>
    data class Success<T>(val data: T?) : UseCaseResult<T>
    data class Error<T>(val error: ErrorApi?) : UseCaseResult<T>
    data class Exception(val ex: ResponseResult.Exception) : UseCaseResult<Nothing>

    abstract class Feature : UseCaseResult<Nothing>
}

fun <T> ResponseResult<T>.asUseCaseResult(): UseCaseResult<T> {
    return when (this) {
        is ResponseResult.Success -> {
            UseCaseResult.Success(data)
        }
        is ResponseResult.Error -> {
            UseCaseResult.Error(this.error)
        }
        is ResponseResult.Exception -> {
            UseCaseResult.Exception(this)
        }
    }
}
