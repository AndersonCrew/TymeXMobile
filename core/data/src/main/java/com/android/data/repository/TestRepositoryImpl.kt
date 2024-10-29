package com.android.data.repository

import com.android.data.RepositoryImp
import com.android.data.ResponseResult
import com.android.data.service.TestApiService
import com.android.model.User
import javax.inject.Inject

/**
 * Created by BM Anderson on 14/08/2023.
 */

interface TestRepository {
    suspend fun getUsers(): ResponseResult<List<User>>
    suspend fun getUserDetail(userName: String): ResponseResult<User>
}
class TestRepositoryImpl @Inject constructor(
    private val testApiService: TestApiService,
) : TestRepository, RepositoryImp() {

    override suspend fun getUsers(): ResponseResult<List<User>> {
        return asyncDataSource(callback = {
            testApiService.getUsers()
        })
    }

    override suspend fun getUserDetail(userName: String): ResponseResult<User> {
        return asyncDataSource(callback = {
            testApiService.getUserDetail(userName)
        })
    }
}