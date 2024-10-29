package com.android.model



open class DataResponse<T>(
    var statusCode: Int? = null,
    var status: Boolean? = null,
    var message: String? = null,
    var data: T? = null,
    var error: ErrorApi? = null
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