package com.example.appelhostal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appelhostal.data.entities.BookingEntity
import com.example.appelhostal.data.entities.ClientEntity
import com.example.appelhostal.data.entities.RoomEntity

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
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE clients ADD COLUMN isOwner INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
