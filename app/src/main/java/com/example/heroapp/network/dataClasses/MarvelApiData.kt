package com.example.heroapp.network.dataClasses

import com.google.gson.annotations.SerializedName

data class MarvelApiData(
    @SerializedName("offset")
    val offset: Int,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("total")
    val total: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("results")
    var results: List<CharacterRestModel>
)

