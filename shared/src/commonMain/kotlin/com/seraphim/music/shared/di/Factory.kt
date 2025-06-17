package com.seraphim.music.shared.di

import com.seraphim.music.shared.database.AppDatabase

expect class Factory {
    fun createRoomDatabase(): AppDatabase
}