package com.android.data

/**
 * Created by BM Anderson on 29/10/2024.
 */

sealed class NetworkStatus(val code: Int) {
    object Success: NetworkStatus(200)
    object BadRequest: NetworkStatus(400)
    object UnAuthorized : NetworkStatus(401)
    object NotFound : NetworkStatus(404)
    object BadGateway500 : NetworkStatus(500)
    object BadGateway502 : NetworkStatus(502)
    object BadGateway503 : NetworkStatus(503)
}