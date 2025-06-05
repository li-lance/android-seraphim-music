package com.seraphim.music.shared.network

import com.seraphim.music.shared.environment.BffConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual class HttpClientProvider actual constructor(bffConfig: BffConfig){
    actual val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            configureClient(bffConfig = bffConfig)
        }
    }
}