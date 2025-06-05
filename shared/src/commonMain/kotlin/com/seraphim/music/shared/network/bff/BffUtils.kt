package com.seraphim.music.shared.network.bff

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readRawBytes
import io.ktor.http.isSuccess
import kotlinx.serialization.SerializationException
import com.seraphim.music.shared.network.json

suspend inline fun <reified DTO> receiveBffResult(block: () -> HttpResponse): BffResponse<DTO> =
runCatchingOrError<DTO> {
    val response = block()
    if (response.status.isSuccess()) {
        // Try to parse the responseAdd commentMore actions
        try {
            val responseBody = response.readRawBytes().decodeToString()
            if (responseBody.contains("\"error\"")) {
                val errorResponse: BffErrorResponse = json.decodeFromString(responseBody)
                BffResponse.Failure.LocalizedError(
                    error = errorResponse,
                    httpStatusCode = response.status.value
                )
            } else {
                val responseData: DTO = json.decodeFromString(responseBody)
                BffResponse.Success(responseData)
            }
        } catch (e: NoTransformationFoundException) {
            BffResponse.Failure.SerializationError(e)
        } catch (e: DoubleReceiveException) {
            BffResponse.Failure.NetworkError(e)
        }
    } else {
        try {
            BffResponse.Failure.LocalizedError(
                error = json.decodeFromString(response.readRawBytes().decodeToString()),
                httpStatusCode = response.status.value
            )
        } catch (e: SerializationException) {
            BffResponse.Failure.SerializationError(e)
        }
    }
}

/**
 * Runs the [block] or returns the specific [BffResponse.Failure]. AtAdd commentMore actions
 * this level this is either [BffResponse.Failure.SerializationError] or
 * [BffResponse.Failure.NetworkError].
 */
inline fun <T> runCatchingOrError(
    block: () -> BffResponse<T>
): BffResponse<T> = runCatching { block() }.getOrElse {
    when (it) {
        is SerializationException -> BffResponse.Failure.SerializationError(it)
        else -> BffResponse.Failure.NetworkError(it)
    }
}