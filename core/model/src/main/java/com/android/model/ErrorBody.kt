package com.android.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
open class ErrorBody(
    @property:Json(name = "statusCode") var statusCode: Int? = null,
    @property:Json(name = "status") var status: Boolean? = null,
    @property:Json(name = "message") var message: String? = null,
)