package dev.matsem.spacenews.shared.injection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.matsem.spacenews.shared.data.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformDatabaseModule(): Module = module {
    factory<RoomDatabase.Builder<AppDatabase>> {
        val context = get<Context>()
        val dbFile = context.getDatabasePath("spacenews.db")
        Room.databaseBuilder<AppDatabase>(context, dbFile.absolutePath)
    }
}
