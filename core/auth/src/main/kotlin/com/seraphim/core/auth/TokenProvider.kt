package com.seraphim.core.auth

interface TokenProvider {

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun saveAccessToken(token: String)

    fun saveRefreshToken(refreshToken: String)
}