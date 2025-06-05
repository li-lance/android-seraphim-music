package com.seraphim.music.shared.network

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

expect class HttpClientProvider(httpClient: HttpClient) {
    val client: HttpClient
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