package com.seraphim.music.shared.network.bff

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BffErrorResponse(
    @SerialName("error")
    val error: String?
)