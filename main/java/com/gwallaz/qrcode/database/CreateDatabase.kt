package com.gwallaz.qrcode.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.reflect.KClass


@Database(entities = [TableCreate::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CreateDatabase: RoomDatabase() {
    abstract fun getCreateDao(): CreateDao

    companion object {
        private fun buildDatabase(context: Context): CreateDatabase {
            return Room.databaseBuilder(
                context, CreateDatabase::class.java, "builder.db"
            ).build()
        }

        @Volatile
        private var INSTANCE: CreateDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): CreateDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!

        }
    }
}


