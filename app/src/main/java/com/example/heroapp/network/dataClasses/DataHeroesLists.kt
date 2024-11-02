package com.example.heroapp.network.dataClasses


import com.google.gson.annotations.SerializedName

data class StoryList(
    @SerializedName("available")
    var available: Int,
    @SerializedName("returned")
    var returned: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: Array<StorySummary> = emptyArray()
)
//----------------------------------------------------------------------
data class StorySummary(
    @SerializedName("resourceURI")
    var resourceURI: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("type")
    var type: String
)
//----------------------------------------------------------------------
data class EventList(

    @SerializedName("available")
    var available: Int,
    @SerializedName("returned")
    var returned: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: Array<EventSummary> = emptyArray()

)
//----------------------------------------------------------------------
data class EventSummary(
    @SerializedName("resourceURI")
    var resourceURI: String,
    @SerializedName("name")
    var name: String
)
//----------------------------------------------------------------------
data class SeriesList(

    @SerializedName("available")
    var available: Int,
    @SerializedName("returned")
    var returned: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: Array<SeriesSummary> = emptyArray()

)
//----------------------------------------------------------------------
data class SeriesSummary (
    @SerializedName("resourceURI")
    var resourceURI: String,
    @SerializedName("name")
    var name: String
)




