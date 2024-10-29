package com.android.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
open class DataResponse<T>(
    @property:Json(name = "statusCode") var statusCode: Int? = null,
    @property:Json(name = "status") var status: Boolean? = null,
    @property:Json(name = "message") var message: String? = null,
    @property:Json(name = "data") var data: T? = null,
    @property:Json(name = "error") var error: ErrorApi? = null
)

data class ErrorApi(
    var messages: String? = null,
    var code: String? = null,
    var codeName: String? = null,
    var moduleName: String? = null,
    var timestamp: String? = null
)

data class MetaDataBase<T>(
    var items: T? = null,
    var meta: MetaData?= null
)
class MetaData: BaseModel() {
    var itemsPerPage: Int? = null
    var totalItems: Int? = null
    var currentPage: Int? = null
    var totalPages: Int? = null
    var displayEntertainmentCategories: String? = null
    var displayTags: Array<String>? = null
}