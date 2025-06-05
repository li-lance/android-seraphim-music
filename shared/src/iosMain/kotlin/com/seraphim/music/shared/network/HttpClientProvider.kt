package com.seraphim.music.shared.network

import com.seraphim.music.shared.environment.BffConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual class HttpClientProvider actual constructor(httpClient: HttpClient) {
    actual val client: HttpClient by lazy {
        HttpClient(Darwin) {
//            configureClient(BffConfig(""))
        }
    }
}