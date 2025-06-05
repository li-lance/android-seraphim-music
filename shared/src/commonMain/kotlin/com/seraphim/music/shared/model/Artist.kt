package com.seraphim.music.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistSearchResponse(
    val artists: List<Artist>
)

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val disambiguation: String? = null
)

@Serializable
data class ArtistDetailsResponse(
    val id: String,
    val name: String,
    val type: String? = null,
    val country: String? = null
)