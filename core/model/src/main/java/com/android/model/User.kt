package com.android.model

import com.google.gson.annotations.SerializedName

/**
 * Created by BM Anderson on 29/10/2024.
 */

data class User(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    val login: String,
): BaseModel()