package com.android.domain.usecase

import com.android.data.ResponseResult
import com.android.data.UseCaseResult
import com.android.data.asUseCaseResult
import com.android.data.repository.TestRepository
import com.android.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
class GetUserDetailUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    operator fun invoke(userName: String): Flow<UseCaseResult<User>> {
        return flow {
            when (val result = testRepository.getUserDetail(userName)) {
                is ResponseResult.Success -> {
                    emit(GetUserDetailResult.GetUserDetailSuccess(result.data))
                }

                else -> emit(result.asUseCaseResult())
            }
        }.flowOn(Dispatchers.IO)
    }
}

sealed class GetUserDetailResult<out T> : UseCaseResult.Feature() {
    class GetUserDetailSuccess(val user: User?) : GetUserDetailResult<Nothing>()
}