package com.seraphim.music.shared.repository

import com.seraphim.music.shared.network.ApiService
import com.seraphim.music.shared.network.bff.toFlow

class UserRepository(private val api:ApiService) {
    suspend fun user() = api.user().toFlow()
}