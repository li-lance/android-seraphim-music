package com.seraphim.domain.login

import com.seraphim.core.auth.TokenProvider
import com.seraphim.music.shared.mmkv.safeKvGet
import com.seraphim.music.shared.mmkv.safeKvSave
import com.seraphim.music.shared.common.KEY_AUTH_ACCESS_TOKEN
import com.seraphim.music.shared.common.KEY_AUTH_REFRESH_TOKEN

class TokenProviderImpl : TokenProvider {
    override fun getAccessToken(): String {
        return safeKvGet(KEY_AUTH_ACCESS_TOKEN, "")
    }

    override fun getRefreshToken(): String {
        return safeKvGet(KEY_AUTH_REFRESH_TOKEN, "")
    }

    override fun saveAccessToken(token: String) {
        token.safeKvSave(KEY_AUTH_ACCESS_TOKEN)
    }

    override fun saveRefreshToken(refreshToken: String) {
        refreshToken.safeKvSave(KEY_AUTH_REFRESH_TOKEN)
    }
}