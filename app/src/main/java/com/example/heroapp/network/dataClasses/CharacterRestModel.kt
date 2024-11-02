package com.example.heroapp.network.dataClasses


import com.google.gson.annotations.SerializedName

data class CharacterRestModel(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail,

    @SerializedName("comics")
    var comics: ComicList,

    @SerializedName("stories")
    var stories: StoryList,

    @SerializedName("events")
    var events: EventList,

    @SerializedName("series")
    var series: SeriesList
)
