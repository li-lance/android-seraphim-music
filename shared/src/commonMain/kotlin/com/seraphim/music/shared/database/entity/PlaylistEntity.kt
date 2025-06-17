package com.seraphim.music.shared.database.entity

//PagingFeaturedPlaylistObject转换成FeaturedPlaylistEntity
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seraphim.music.model.SimplifiedPlaylistObject
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "featured_playlists")
data class FeaturedPlayEntity(
    @PrimaryKey val id: String,
    val name: String? = null,
    val description: String? = null,
    val externalUrls: String? = null,
    val images: List<String>? = null,
    val uri: String? = null
) {
    companion object {
        fun from(simplifiedPlaylistObject: SimplifiedPlaylistObject): FeaturedPlayEntity {
            return FeaturedPlayEntity(
                id = simplifiedPlaylistObject.id.orEmpty(),
                name = simplifiedPlaylistObject.name,
                description = simplifiedPlaylistObject.description,
                externalUrls = simplifiedPlaylistObject.externalUrls?.spotify,
                images = simplifiedPlaylistObject.images?.map { it.url },
                uri = simplifiedPlaylistObject.uri
            )
        }
    }
}