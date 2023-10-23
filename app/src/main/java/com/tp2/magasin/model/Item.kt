package com.tp2.magasin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
class Item
    (
    @field:ColumnInfo(name = "name_col") var name: String,
    @field:ColumnInfo(name = "description_col") var description: String,
    @field:ColumnInfo(name= "prix_col") var prix: Int,
    @field:ColumnInfo(name= "categorie_col") var categorie: String,
    @field:ColumnInfo(name= "quantite_col") var quantite: Int =1
) {
    // clé primaire
    @PrimaryKey(autoGenerate = true)
    var id = 0

}
