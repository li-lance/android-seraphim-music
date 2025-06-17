package com.seraphim.music.shared.database.entity

//q:PrivateUserObject转换成UserEntity
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seraphim.music.model.PrivateUserObject
import kotlinx.serialization.Serializable
@Serializable
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val displayName: String? = null,
    val email: String? = null,
    val externalUrls: String? = null,
    val followers: Int? = null,
    val images: List<String>? = null,
    val type: String? = null,
    val uri: String? = null
) {
    companion object {
        fun from(privateUserObject: PrivateUserObject): UserEntity {
            return UserEntity(
                id = privateUserObject.id.orEmpty(),
                displayName = privateUserObject.displayName,
                email = privateUserObject.email,
                externalUrls = privateUserObject.externalUrls?.spotify,
                followers = privateUserObject.followers?.total,
                images = privateUserObject.images?.map { it.url },
                type = privateUserObject.type,
                uri = privateUserObject.uri
            )
        }
    }
}
