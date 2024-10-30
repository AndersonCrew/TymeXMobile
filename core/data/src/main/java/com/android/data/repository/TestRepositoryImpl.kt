package com.android.data.repository

import com.android.data.RepositoryImp
import com.android.data.ResponseResult
import com.android.data.service.TestApiService
import com.android.model.User
import javax.inject.Inject

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface TestRepository {
    suspend fun getUsers(page: Int): ResponseResult<List<User>>
    suspend fun getUserDetail(userName: String): ResponseResult<User>
}
class TestRepositoryImpl @Inject constructor(
    private val testApiService: TestApiService,
) : TestRepository, RepositoryImp() {

    override suspend fun getUsers(page: Int): ResponseResult<List<User>> {
        return asyncDataSourceGitData(callback = {
            testApiService.getUsers(since = page)
        })
    }

    override suspend fun getUserDetail(userName: String): ResponseResult<User> {
        return asyncDataSourceGitData(callback = {
            testApiService.getUserDetail(userName)
        })
    }
}