package com.tp2.magasin.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tp2.magasin.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Item::class], version = 1)
abstract class ItemRoomDB : RoomDatabase() {
    // DAO
    abstract fun ItemDao(): ItemDao

    companion object {
        // Singleton
        private var INSTANCE: ItemRoomDB? = null

        @Synchronized
        fun getDatabase(context: Context): ItemRoomDB? {
            if (INSTANCE == null) {
                // Crée la BD
                INSTANCE = databaseBuilder(
                    context.applicationContext,
                    ItemRoomDB::class.java, "Item_database"
                )
                    .build()
            }
            return INSTANCE
        }
    }
}