package com.tp2.magasin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tp2.magasin.model.Item

@Dao
interface ItemDao {
    @Insert
    fun insert(item: Item?)

    @Update
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    // utiliser LiveData
    @Query("SELECT * FROM item_table")
    fun getAllItem(): LiveData<List<Item>>

    @Query("DELETE FROM item_table")
    fun deleteAll()

}