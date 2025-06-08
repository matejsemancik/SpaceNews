package dev.matsem.spacenews.shared.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import dev.matsem.spacenews.shared.data.database.dao.ArticleDao
import dev.matsem.spacenews.shared.data.database.entity.ArticleEntity
import dev.matsem.spacenews.shared.data.database.typeConverters.JsonTypeConverters

@Database(
    entities = [ArticleEntity::class],
    version = 2,
)
@TypeConverters(JsonTypeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
