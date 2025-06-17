package com.seraphim.music.shared.repository

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.seraphim.music.api.PlaylistsApi
import com.seraphim.music.invoker.infrastructure.HttpResponse
import com.seraphim.music.model.PagingFeaturedPlaylistObject
import com.seraphim.music.shared.database.dao.PlaylistsDao
import com.seraphim.music.shared.database.entity.FeaturedPlayEntity
import com.seraphim.music.shared.model.Resource
import com.seraphim.music.shared.network.NetworkBoundRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FeaturePlayListPagingSource(private val api: PlaylistsApi, private val dao: PlaylistsDao) :
    PagingSource<Int, FeaturedPlayEntity>() {
    override fun getRefreshKey(state: PagingState<Int, FeaturedPlayEntity>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeaturedPlayEntity> {
        val page = params.key ?: 0
        val limit = params.loadSize

        return try {
            // 使用 NetworkBoundRepository 逻辑
            val repository =
                object :
                    NetworkBoundRepository<List<FeaturedPlayEntity>, PagingFeaturedPlaylistObject>() {
                    override suspend fun saveRemoteData(response: PagingFeaturedPlaylistObject) {
                        val data = response.playlists?.items
                        data?.let { list ->
                            dao.insertFeaturedPlaylist(list.map { FeaturedPlayEntity.from(it) })
                        }
                    }

                    override fun fetchFromLocal(): Flow<List<FeaturedPlayEntity>> {
                        return dao.getFeaturedPlaylistPage(page * limit, limit)
                    }

                    override suspend fun fetchFromRemote(): HttpResponse<PagingFeaturedPlaylistObject> {
                        return api.getFeaturedPlaylists(limit = 20, offset = 1)
                    }
                }

            val data: List<FeaturedPlayEntity> =
                (repository.asFlow()
                    .first() as Resource.Success<List<FeaturedPlayEntity>>).data
            Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}