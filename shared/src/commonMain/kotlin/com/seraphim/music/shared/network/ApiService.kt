package com.seraphim.music.shared.network

import com.seraphim.music.shared.model.ArtistDetailsResponse
import com.seraphim.music.shared.model.ArtistSearchResponse
import com.seraphim.music.shared.network.bff.receiveBffResult
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val clientProvider: HttpClientProvider) {


    suspend fun searchArtist(query: String, limit: Int = 10, offset: Int = 0) =
        receiveBffResult<ArtistSearchResponse> {
            clientProvider.client.get("/artist") {
                parameter("query", query)
                parameter("fmt", "json")
                parameter("limit", limit)
                parameter("offset", offset)
            }.body()
        }

    suspend fun getArtistDetails(artistId: String)=receiveBffResult<ArtistDetailsResponse>{
        clientProvider.client.get("/artist/$artistId") {
            parameter("fmt", "json")
        }.body()
    }
}