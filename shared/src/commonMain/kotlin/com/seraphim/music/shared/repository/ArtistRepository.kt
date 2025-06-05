package com.seraphim.music.shared.repository

import com.seraphim.music.shared.network.ApiService
import com.seraphim.music.shared.network.bff.toFlow

class ArtistRepository(private val api:ApiService) {
    suspend fun searchArtist(query:String) = api.searchArtist(query).toFlow()
}