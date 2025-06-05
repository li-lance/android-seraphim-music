package com.seraphim.music.shared.network

import com.seraphim.music.shared.model.UserResponse
import com.seraphim.music.shared.network.bff.receiveBffResult
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val clientProvider: HttpClientProvider) {


    suspend fun user() =
        receiveBffResult<UserResponse> {
            clientProvider.client.get("/me") {}.body()
        }
}