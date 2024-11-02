package com.example.heroapp.network.dataClasses

import com.google.gson.annotations.SerializedName

data class ComicList(
    @SerializedName("available")
    var available: Int,
    @SerializedName("returned")
    var returned: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: Array<ComicSummary> = emptyArray(),
)
data class ComicSummary(
    @SerializedName("resourceURI")
    var resourceURI: String,
    @SerializedName("name")
    var name: String,
)
