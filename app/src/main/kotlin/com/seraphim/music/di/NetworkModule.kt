package com.seraphim.music.di

import com.seraphim.core.auth.model.TokenInfo
import com.seraphim.music.shared.environment.BffConfig
import com.seraphim.music.shared.environment.Environment
import com.seraphim.music.shared.mmkv.safeKvGet
import com.seraphim.music.shared.network.json
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module
import java.util.Base64

val networkModule = module {
    single {
        val environment = safeKvGet("AppStage", "dev")
        when (Environment.fromString(environment)) {
            Environment.DEV -> {
                BffConfig(
                    "api.spotify.com/v1"
                )
            }

            Environment.STAGING -> {
                BffConfig(
                    "api.spotify.com/v1"
                )
            }

            Environment.PROD -> {
                BffConfig(
                    "api.spotify.com/v1"
                )
            }
        }

    }
    single {
        val bffConfig = get<BffConfig>()
        val tokenProvider = get<com.seraphim.core.auth.TokenProvider>()
        HttpClient(OkHttp) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    contentType = ContentType.Application.Json,
                    json = json
                )
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            tokenProvider.getAccessToken(),
                            tokenProvider.getRefreshToken()
                        )
                    }
                    refreshTokens {
                        val encodedCredentials =
                            Base64.getEncoder()
                                .encodeToString((bffConfig.clientId + ":dee96dcaa03b427fa189cfe9b8e06e30").toByteArray())
//                        val refreshTokenInfo: TokenInfo = client.submitForm(
//                            url = "https://accounts.spotify.com/api/token",
//                            formParameters = parameters {
//                                append("grant_type", "refresh_token")
//                                append("client_id", bffConfig.clientId)
//                                append("refresh_token", oldTokens?.refreshToken ?: "")
//                            },
//                        ) {
////                            markAsRefreshTokenRequest()
//                            headers.append(
//                                HttpHeaders.Authorization,
//                                "Basic $encodedCredentials"
//                            )
//                        }.body()
                        val refreshTokenInfo: TokenInfo = client.post {
                            url {
                                host = "accounts.spotify.com"
                                path("/api/token")
                            }
                            setBody(FormDataContent(Parameters.build {
                                append("grant_type", "refresh_token")
                                append("client_id", bffConfig.clientId)
                                append("refresh_token", oldTokens?.refreshToken ?: "")
//                                append("client_secret", "dee96dcaa03b427fa189cfe9b8e06e30")
                            }))
                            markAsRefreshTokenRequest()
                        }.body()
                        tokenProvider.saveAccessToken(refreshTokenInfo.accessToken)
                        tokenProvider.saveRefreshToken(refreshTokenInfo.refreshToken)
                        BearerTokens(
                            accessToken = refreshTokenInfo.accessToken,
                            refreshToken = refreshTokenInfo.refreshToken
                        )
                    }
                }
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = bffConfig.baseUrl
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

        }
    }
}