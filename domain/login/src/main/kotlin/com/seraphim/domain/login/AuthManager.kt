package com.seraphim.domain.login

import android.content.Context
import androidx.core.net.toUri
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues

class AuthManager(context: Context) {
    private val serviceConfig = AuthorizationServiceConfiguration(
        "https://accounts.spotify.com/authorize".toUri(), // 授权端点
        "https://accounts.spotify.com/api/token".toUri()  // 令牌端点
    )
    private val clientId = "7a85e8b3a95a4760952cd23ff5770f54"
    private val redirectUri = "seraphim://seraphim.music.com".toUri()
    private val authRequest = AuthorizationRequest.Builder(
        serviceConfig,
        clientId,
        ResponseTypeValues.CODE,
        redirectUri
    ).setScope("user-read-private user-read-email") // 请求的权限范围
        .build()
    private val authService = AuthorizationService(context)
    fun getAuthRequestIntent(): android.content.Intent {
        return authService.getAuthorizationRequestIntent(authRequest)
    }

    fun exchangeToken(
        authResponse: AuthorizationResponse,
        callback: AuthorizationService.TokenResponseCallback
    ) {
        val tokenRequest = authResponse.createTokenExchangeRequest()
        authService.performTokenRequest(tokenRequest, callback)
    }
}