package com.seraphim.music.shared.network.bff

class BffException(val code: Int, override val message: String?) : Exception()

fun Throwable?.toBffException(): BffException {
    return when (this) {
        is BffException -> this
        else -> BffException(-1,this?.message ?: "Unknown error")
    }
}