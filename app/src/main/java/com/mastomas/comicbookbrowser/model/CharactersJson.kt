package com.mastomas.comicbookbrowser.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersJson(
    @SerialName("attributionHTML")
    val attributionHTML: String?,
    @SerialName("attributionText")
    val attributionText: String?,
    @SerialName("code")
    val code: Int,
    @SerialName("copyright")
    val copyright: String?,
    @SerialName("data")
    val `data`: Data?,
    @SerialName("etag")
    val etag: String?,
    @SerialName("status")
    val status: String?
) {
    @Serializable
    data class Data(
        @SerialName("count")
        val count: Int,
        @SerialName("limit")
        val limit: Int,
        @SerialName("offset")
        val offset: Int,
        @SerialName("results")
        val results: List<Result?>?,
        @SerialName("total")
        val total: Int
    ) {
        @Serializable
        data class Result(
            @SerialName("comics")
            val comics: Comics?,
            @SerialName("description")
            val description: String?,
            @SerialName("events")
            val events: Events?,
            @SerialName("id")
            val id: Int,
            @SerialName("modified")
            val modified: String?,
            @SerialName("name")
            val name: String?,
            @SerialName("resourceURI")
            val resourceURI: String?,
            @SerialName("series")
            val series: Series?,
            @SerialName("stories")
            val stories: Stories?,
            @SerialName("thumbnail")
            val thumbnail: Thumbnail?,
            @SerialName("urls")
            val urls: List<Url?>?
        ) {
            @Serializable
            data class Comics(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int
            ) {
                @Serializable
                data class Item(
                    @SerialName("name")
                    val name: String?,
                    @SerialName("resourceURI")
                    val resourceURI: String?
                )
            }

            @Serializable
            data class Events(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int
            ) {
                @Serializable
                data class Item(
                    @SerialName("name")
                    val name: String?,
                    @SerialName("resourceURI")
                    val resourceURI: String?
                )
            }

            @Serializable
            data class Series(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int
            ) {
                @Serializable
                data class Item(
                    @SerialName("name")
                    val name: String?,
                    @SerialName("resourceURI")
                    val resourceURI: String?
                )
            }

            @Serializable
            data class Stories(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int
            ) {
                @Serializable
                data class Item(
                    @SerialName("name")
                    val name: String?,
                    @SerialName("resourceURI")
                    val resourceURI: String?,
                    @SerialName("type")
                    val type: String?
                )
            }

            @Serializable
            data class Thumbnail(
                @SerialName("extension")
                val extension: String?,
                @SerialName("path")
                val path: String?
            )

            @Serializable
            data class Url(
                @SerialName("type")
                val type: String?,
                @SerialName("url")
                val url: String?
            )
        }
    }
}