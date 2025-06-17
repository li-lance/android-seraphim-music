package com.seraphim.music.shared.di

import com.seraphim.music.shared.database.AppDatabase
import com.seraphim.music.shared.database.DB_FILE_NAME
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual class Factory {
    actual fun createRoomDatabase(): AppDatabase {
        val dbFile = "${fileDirectory()}/$DB_FILE_NAME"
        return Room
            .databaseBuilder<AppDatabase>(
                name = dbFile,
            ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path!!
    }

}