package com.seraphim.music.shared.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserResponse(
    @SerialName("country")
    val country: String,
    @SerialName("display_name")
    val displayName: String,
    @SerialName("email")
    val email: String,
    @SerialName("explicit_content")
    val explicitContent: ExplicitContent,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    @SerialName("followers")
    val followers: Followers,
    @SerialName("href")
    val href: String,
    @SerialName("id")
    val id: String,
    @SerialName("images")
    val images: List<Image>,
    @SerialName("product")
    val product: String,
    @SerialName("type")
    val type: String,
    @SerialName("uri")
    val uri: String
) {
    @Serializable
    data class ExplicitContent(
        @SerialName("filter_enabled")
        val filterEnabled: Boolean,
        @SerialName("filter_locked")
        val filterLocked: Boolean
    )

    @Serializable
    data class ExternalUrls(
        @SerialName("spotify")
        val spotify: String
    )

    @Serializable
    data class Followers(
        @SerialName("href")
        val href: String?,
        @SerialName("total")
        val total: Int
    )

    @Serializable
    data class Image(
        @SerialName("height")
        val height: Int,
        @SerialName("url")
        val url: String,
        @SerialName("width")
        val width: Int
    )
}


