package com.seraphim.music.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seraphim.music.shared.database.entity.FeaturedPlayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeaturedPlaylist(playlist: List<FeaturedPlayEntity>)


    @Query("SELECT * FROM featured_playlists LIMIT :offset, :limit")
    fun getFeaturedPlaylistPage(limit: Int, offset: Int): Flow<List<FeaturedPlayEntity>>
}