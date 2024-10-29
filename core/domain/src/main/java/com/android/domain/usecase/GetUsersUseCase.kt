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
class GetUsersUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    operator fun invoke(page: Int): Flow<UseCaseResult<List<User>>> {
        return flow {
            when (val result = testRepository.getUsers(page)) {
                is ResponseResult.Success -> {
                    emit(GetUsersUseResult.GetUsersUseResultSuccess(result.data))
                }

                else -> emit(result.asUseCaseResult())
            }
        }.flowOn(Dispatchers.IO)
    }
}

sealed class GetUsersUseResult<out T> : UseCaseResult.Feature() {
    class GetUsersUseResultSuccess(val data: List<User>?) : GetUsersUseResult<Nothing>()
}