package com.seraphim.music.shared.network

import com.seraphim.music.invoker.infrastructure.HttpResponse
import com.seraphim.music.shared.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundRepository<RESULT, REQUEST : Any> {

    fun asFlow() = flow<Resource<RESULT>> {

        // Emit Database content first
        val localData = fetchFromLocal().firstOrNull()
        if (localData is Collection<*> && localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }
        if (localData != null && localData !is Collection<*>) {
            emit(Resource.Success(localData))
        }
        val apiResponse = fetchFromRemote()

        // Parse body
        val remoteData = apiResponse.body()

        // Check for response validation
        if (apiResponse.success) {
            saveRemoteData(remoteData)
        } else {
            // Something went wrong! Emit Error state.
            emit(Resource.Failed(apiResponse.toString()))
        }

        emitAll(
            fetchFromLocal().map {
                Resource.Success<RESULT>(it)
            }
        )
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Failed("Network error!"))
    }

    /**
     * Saves retrieved from remote into the persistence storage.
     */
//    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    /**
     * Retrieves all data from persistence storage.
     */
//    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    /**
     * Fetches [HttpResponse] from the remote end point.
     */
//    @MainThread
    protected abstract suspend fun fetchFromRemote(): HttpResponse<REQUEST>
}