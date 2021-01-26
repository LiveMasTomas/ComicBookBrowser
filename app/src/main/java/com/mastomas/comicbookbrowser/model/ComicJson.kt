package com.mastomas.comicbookbrowser.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicJson(
    @SerialName("attributionHTML")
    val attributionHTML: String?,
    @SerialName("attributionText")
    val attributionText: String?,
    @SerialName("code")
    val code: Int?,
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
        val count: Int?,
        @SerialName("limit")
        val limit: Int?,
        @SerialName("offset")
        val offset: Int?,
        @SerialName("results")
        val results: List<Result?>?,
        @SerialName("total")
        val total: Int?
    ) {
        @Serializable
        data class Result(
            @SerialName("characters")
            val characters: Characters?,
            @SerialName("collectedIssues")
            val collectedIssues: List<CollectedIssue?>?,
            @SerialName("collections")
            val collections: List<Collection?>?,
            @SerialName("creators")
            val creators: Creators?,
            @SerialName("dates")
            val dates: List<Date?>?,
            @SerialName("description")
            val description: String?,
            @SerialName("diamondCode")
            val diamondCode: String?,
            @SerialName("digitalId")
            val digitalId: Int?,
            @SerialName("ean")
            val ean: String?,
            @SerialName("events")
            val events: Events?,
            @SerialName("format")
            val format: String?,
            @SerialName("id")
            val id: Int?,
            @SerialName("images")
            val images: List<Image?>?,
            @SerialName("isbn")
            val isbn: String?,
            @SerialName("issn")
            val issn: String?,
            @SerialName("issueNumber")
            val issueNumber: Double?,
            @SerialName("modified")
            val modified: String?,
            @SerialName("pageCount")
            val pageCount: Int?,
            @SerialName("prices")
            val prices: List<Price?>?,
            @SerialName("resourceURI")
            val resourceURI: String?,
            @SerialName("series")
            val series: Series?,
            @SerialName("stories")
            val stories: Stories?,
            @SerialName("textObjects")
            val textObjects: List<TextObject?>?,
            @SerialName("thumbnail")
            val thumbnail: Thumbnail?,
            @SerialName("title")
            val title: String?,
            @SerialName("upc")
            val upc: String?,
            @SerialName("urls")
            val urls: List<Url?>?,
            @SerialName("variantDescription")
            val variantDescription: String?,
            @SerialName("variants")
            val variants: List<Variant?>?
        ) {
            @Serializable
            data class Characters(
                @SerialName("available")
                val available: Int?,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int?
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
            data class CollectedIssue(
                @SerialName("name")
                val name: String?,
                @SerialName("resourceURI")
                val resourceURI: String?
            )

            @Serializable
            data class Collection(
                @SerialName("name")
                val name: String?,
                @SerialName("resourceURI")
                val resourceURI: String?
            )

            @Serializable
            data class Creators(
                @SerialName("available")
                val available: Int?,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int?
            ) {
                @Serializable
                data class Item(
                    @SerialName("name")
                    val name: String?,
                    @SerialName("resourceURI")
                    val resourceURI: String?,
                    @SerialName("role")
                    val role: String?
                )
            }

            @Serializable
            data class Date(
                @SerialName("date")
                val date: String?,
                @SerialName("type")
                val type: String?
            )

            @Serializable
            data class Events(
                @SerialName("available")
                val available: Int?,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int?
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
            data class Image(
                @SerialName("extension")
                val extension: String?,
                @SerialName("path")
                val path: String?
            )

            @Serializable
            data class Price(
                @SerialName("price")
                val price: Double?,
                @SerialName("type")
                val type: String?
            )

            @Serializable
            data class Series(
                @SerialName("name")
                val name: String?,
                @SerialName("resourceURI")
                val resourceURI: String?
            )

            @Serializable
            data class Stories(
                @SerialName("available")
                val available: Int?,
                @SerialName("collectionURI")
                val collectionURI: String?,
                @SerialName("items")
                val items: List<Item?>?,
                @SerialName("returned")
                val returned: Int?
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
            data class TextObject(
                @SerialName("language")
                val language: String?,
                @SerialName("text")
                val text: String?,
                @SerialName("type")
                val type: String?
            )

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

            @Serializable
            data class Variant(
                @SerialName("name")
                val name: String?,
                @SerialName("resourceURI")
                val resourceURI: String?
            )
        }
    }
}