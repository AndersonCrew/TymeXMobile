package com.android.model

import com.google.gson.annotations.SerializedName

/**
 * Created by BM Anderson on 29/10/2024.
 */

data class User(
    @SerializedName("avatar_url") val avatarUrl: String?=null,
    @SerializedName("html_url") val htmlUrl: String?=null,
    val login: String?=null,
    val location: String?=null,
    val name: String?=null,
    val blog: String?=null,
    val followers: Int?=null,
    val following: Int?=null,
): BaseModel()