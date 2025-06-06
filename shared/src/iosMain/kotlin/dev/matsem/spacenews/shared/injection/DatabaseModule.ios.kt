package dev.matsem.spacenews.shared.injection

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.matsem.spacenews.shared.data.database.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
internal actual fun platformDatabaseModule(): Module = module {
    factory<RoomDatabase.Builder<AppDatabase>> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )

        val dbFilePath = requireNotNull(documentDirectory?.path) + "/spacenews.db"
        Room.databaseBuilder<AppDatabase>(name = dbFilePath)
    }
}
