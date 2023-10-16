package com.tp2.magasin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.tp2.magasin.model.Item

/*
Objet BD
    abstract, ce n'est pas une classe d'implémentation
    l'implémentation est fournie par le room
 */
// on peut lister les entités si il y a plusieurs tables...
@Database(entities = [Item::class], version = 1)
abstract class ItemRoomDB : RoomDatabase() {
    // DAO
    abstract fun ItemDao(): ItemDao?

    companion object {
        // Singleton
        var INSTANCE: ItemRoomDB? = null
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