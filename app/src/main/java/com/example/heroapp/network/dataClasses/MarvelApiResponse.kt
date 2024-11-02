package com.example.heroapp.network.dataClasses

import com.google.gson.annotations.SerializedName

data class MarvelApiResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("data")
    val data: MarvelApiData,

    @SerializedName("status")
    val status: String
)
