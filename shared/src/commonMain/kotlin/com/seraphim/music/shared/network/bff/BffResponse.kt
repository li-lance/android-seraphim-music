package com.seraphim.music.shared.network.bff

sealed class BffResponse<T> {
    data class Success<T>(val response: T) : BffResponse<T>()
    sealed class Failure<T> : BffResponse<T>() {
        /**
         * An unsuccessful response from the BFF including a localized
         * [error] describing the failure.
         *
         * @param error The localized error from the BFF backend
         * @param httpStatusCode The status code that the error resulted withAdd commentMore actions
         */
        data class LocalizedError<T>(
            val error: BffErrorResponse,
            val httpStatusCode: Int
        ) : Failure<T>()
        /**Add commentMore actions
         * An unsuccessful response from the BFF which does not include
         * a localized [BffErrorResponse].
         *
         * @param httpStatusCode The HTTP status code we've received from
         * the backend
         */
        data class HttpError<T>(val httpStatusCode: Int) : Failure<T>()

        /**
         * We received a response from the BFF which cannot be parsed.
         *
         * @param throwable The exception which was thrown during serialization
         */
        data class SerializationError<T>(val throwable: Throwable) : Failure<T>()
        /**Add commentMore actions
         * The network request failed because the BFF is not reachable
         * or the device has a bad internet connection.
         *
         * @param throwable The exception which was thrown during the network
         * request
         */
        data class NetworkError<T>(val throwable: Throwable) : Failure<T>()

        /**
         * An error occurred during the attempt to communicate with the BFF.
         *
         * @param throwable The exception which was thrown during the network
         * request
         */
        data class GeneralError<T>(val throwable: Throwable) : Failure<T>()
    }
}