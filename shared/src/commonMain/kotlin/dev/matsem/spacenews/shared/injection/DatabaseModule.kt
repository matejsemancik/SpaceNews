package dev.matsem.spacenews.shared.injection

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.matsem.spacenews.shared.data.database.AppDatabase
import dev.matsem.spacenews.shared.data.database.dao.ArticleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformDatabaseModule(): Module

internal fun databaseModule() = module {
    includes(platformDatabaseModule())
    single<AppDatabase> {
        val databaseBuilder = get<RoomDatabase.Builder<AppDatabase>>()
        databaseBuilder
            .fallbackToDestructiveMigration(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single<ArticleDao> { get<AppDatabase>().articleDao() }
}
