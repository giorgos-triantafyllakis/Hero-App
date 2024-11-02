package com.example.heroapp.network.dataClasses


import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("path")
    var path: String,

    @SerializedName("extension")
    var extension: String
){
    fun getUrl(): String {
        if (path.isBlank() && extension.isBlank()) {
            return ""
        }

        val url = "$path.$extension"
        val resolvedUrl = when {
            url.startsWith("http://") -> url.replace("http://", "https://")
            url.startsWith("https://") -> url
            !path.contains("http") -> url
            path.isBlank() -> ".$extension"
            extension.isBlank() -> path
            else -> "https://$url"
        }

        return resolvedUrl.removeSuffix(".")
    }
}