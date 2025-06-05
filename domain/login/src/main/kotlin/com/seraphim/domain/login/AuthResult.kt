package com.seraphim.domain.login

import android.content.Intent
import com.seraphim.core.auth.TokenProvider
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthResult(private val authManager: AuthManager, private val tokenProvider: TokenProvider) {
    fun onActivityResult(
        data: Intent,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val response = AuthorizationResponse.fromIntent(data)
        val exception = AuthorizationException.fromIntent(data)
        if (response != null) {
            authManager.exchangeToken(response) { tokenResponse, authException ->
                if (tokenResponse != null) {
                    // 成功获取访问令牌
                    val accessToken = tokenResponse.accessToken
                    val refreshToken = tokenResponse.refreshToken
                    tokenProvider.saveAccessToken(accessToken.orEmpty())
                    tokenProvider.saveRefreshToken(refreshToken.orEmpty())
                    onSuccess.invoke()
                } else {
                    // 处理错误
                    authException?.printStackTrace()
                    onError.invoke(
                        authException ?: Exception("Unknown error during token exchange")
                    )
                }
            }
        } else {
            onError.invoke(NullPointerException("Authorization response is null"))
        }
    }
}