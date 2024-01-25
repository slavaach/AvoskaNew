package ru.yandex.slavaach.nullapplicatoin.core.data.network.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("data")
    val data: Any?,
)
