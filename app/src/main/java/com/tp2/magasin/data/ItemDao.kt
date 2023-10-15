package com.tp2.magasin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tp2.magasin.model.Item

@Dao
interface ItemDao {
    @Insert
    fun insert(item: Item?)

    @Query("DELETE FROM item_table")
    fun deleteAll()

    // utiliser LiveData
    @Query("SELECT * FROM item_table")
    fun getAllItem(): List<Item>

}