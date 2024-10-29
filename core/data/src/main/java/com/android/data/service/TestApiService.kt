package com.android.data.service

import com.android.model.DataResponse
import com.android.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by BM Anderson on 29/10/2024.
 */
interface TestApiService {
    @GET("users?per_page=20&since=1")
    fun getUsers(
        @Query("per_page") limit: Int = 20,
        @Query("since") since: Int = 1
    ): Deferred<DataResponse<List<User>>>

    @GET("users/{userName}")
    fun getUserDetail(
        @Path("userName") userName: String
    ): Deferred<DataResponse<User>>
}