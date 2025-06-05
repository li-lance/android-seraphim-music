package com.seraphim.music.shared.network.bff

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> Flow<T>.flowError(): Flow<T> = this.catch { e ->
    throw BffException(0, e.message)
}

fun <T> BffResponse<T>.toFlow(): Flow<T> = flow {
    when (this@toFlow) {
        is BffResponse.Success -> {
            val data = this@toFlow.response
            if (data != null) {
                emit(data)
            } else {
                throw BffException(400,"no data received")
            }
        }

        is BffResponse.Failure.LocalizedError<*> -> {
            throw BffException(400, this@toFlow.error.error)
        }

        is BffResponse.Failure.HttpError -> throw BffException(
            this@toFlow.httpStatusCode,
            "HTTP error with status code"
        )

        is BffResponse.Failure.SerializationError -> throw BffException(
            -1,
            "Serialization error: ${this@toFlow.throwable.message}"
        )

        is BffResponse.Failure.NetworkError -> throw BffException(
            -1,
            "Network error: ${this@toFlow.throwable.message}"
        )

        is BffResponse.Failure.GeneralError -> throw BffException(
            -1,
            "General error: ${this@toFlow.throwable.message}"
        )
    }
}.flowOn(Dispatchers.IO)