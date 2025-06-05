package com.seraphim.music.shared.network
import com.seraphim.music.shared.environment.BffConfig
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect class HttpClientProvider(bffConfig: BffConfig) {
    val client: HttpClient
}

fun HttpClientConfig<*>.configureClient(bffConfig: BffConfig) {
    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = bffConfig.baseUrl//cpmsBff.baseUrl
//            path("fc-unimo-app-bff/")
        }
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
        headers.append("User-Agent", "SeraphimMusic/1.0 (Android; Kotlin)")
    }
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Napier.d(tag = "HttpClient", message = message)
            }
        }
    }
    expectSuccess = false
    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = json)
    }
}

val json = Json {
    //是否使用默认值
    encodeDefaults = true
    //优雅格式化文本
    prettyPrint = true
    //接收不规范的JSON
    isLenient = true
    //类型不对的情况下会引起精度丢失，这里强制转换
    coerceInputValues = true
    //忽略字段变化的严格限制
    ignoreUnknownKeys = true
}