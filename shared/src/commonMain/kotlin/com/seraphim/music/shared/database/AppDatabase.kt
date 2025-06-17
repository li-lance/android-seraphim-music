package com.seraphim.music.shared.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.seraphim.music.shared.database.converter.ListStringConverter
import com.seraphim.music.shared.database.dao.PlaylistsDao
import com.seraphim.music.shared.database.dao.UserDao
import com.seraphim.music.shared.database.entity.FeaturedPlayEntity
import com.seraphim.music.shared.database.entity.UserEntity

@Database(entities = [UserEntity::class, FeaturedPlayEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(value = [ListStringConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun playlistsDao(): PlaylistsDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val DB_FILE_NAME = "seraphim-spotify.db"