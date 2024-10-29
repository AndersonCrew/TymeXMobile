package com.android.data

import com.android.model.DataResponse
import com.android.model.ErrorApi
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
open class RepositoryImp @Inject constructor() {

    suspend fun <T: Any> asyncDataSource(
        callback: () -> Deferred<DataResponse<T>>
    ): ResponseResult<T> {
        return try {
            Timber.d("asyncDataSource $callback")
            val data = callback.invoke().await()
            if (data.status == true) {
                ResponseResult.Success(data.data)
            } else {
                ResponseResult.Error(data.error)
            }
        } catch (ex: Throwable) {
            handleError(ex)
        }
    }

    suspend fun <T: Any> asyncDataSourceGitData(
        callback: () -> Deferred<T>
    ): ResponseResult<T> {
        return try {
            Timber.d("asyncDataSource $callback")
            val data = callback.invoke().await()
            ResponseResult.Success(data)
        } catch (ex: Throwable) {
            handleError(ex)
        }
    }

    private fun <T> handleError(error: Throwable): ResponseResult<T> {
        return when (error) {
            is HttpException -> {
                when (error.code()) {
                    NetworkStatus.UnAuthorized.code -> ResourceException.UnAuthorized
                    NetworkStatus.NotFound.code,
                    NetworkStatus.BadRequest.code,
                    NetworkStatus.BadGateway500.code,
                    NetworkStatus.BadGateway502.code -> ResourceException.ServerError
                    else -> {
                        ResponseResult.Error(ErrorApi())
                    }
                }
            }
            is SocketTimeoutException,
            is UnknownHostException,
            is InterruptedIOException -> {
                ResourceException.NetworkConnection
            }
            is TimeoutException -> {
                ResourceException.TimeoutConnection
            }
            else -> ResourceException.UnknownException
        }
    }
}