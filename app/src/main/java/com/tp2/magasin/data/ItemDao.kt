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

    // !!! Attention, dans cette version, retourne une liste et non un observable LiveData
    @Query("SELECT * FROM item_table")
    fun getAllItem(): List<Item>

}