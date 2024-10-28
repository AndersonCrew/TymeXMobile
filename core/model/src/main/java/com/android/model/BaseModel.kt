package com.android.model

import java.io.Serializable


/**
 * Created by BM Anderson on 28/10/2024.
 */
open class BaseModel (
    open var id:Long? = null
): Serializable {

    open fun createDummyList() : List<BaseModel> {
        return listOf()
    }

    open fun mapFields() : BaseModel {
        return BaseModel()
    }
}