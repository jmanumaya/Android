package com.example.appelhostalbien.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appelhostalbien.data.entities.BookingEntity
import com.example.appelhostalbien.data.entities.ClientEntity
import com.example.appelhostalbien.data.entities.RoomEntity

@Database(
    entities = [ClientEntity::class, RoomEntity::class, BookingEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun roomDao(): RoomDao
    abstract fun bookingDao(): BookingDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Si vienes de una versión anterior sin isOwner, podrías necesitar esto.
                // Pero como estamos empezando, fallbackToDestructiveMigration en MainActivity es suficiente.
            }
        }
    }
}
