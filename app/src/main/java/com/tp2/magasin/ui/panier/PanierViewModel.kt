package com.tp2.magasin.ui.panier

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp2.magasin.model.Item
import java.text.FieldPosition

class PanierViewModel : ViewModel() {

    private val panierItems: MutableLiveData<List<Item>>
    private val panierList: MutableList<Item> = mutableListOf()

    init {
        panierItems = MutableLiveData()
    }

    var items: MutableLiveData<List<Item>>
        get() = panierItems
        set(items) {
            panierItems.setValue(items.value)
        }

    fun addItemToPanier(item: Item) {
        val existingItem = panierList.find { it.id == item.id }

        if (existingItem != null) {
            existingItem.quantite++
        } else {
            panierList.add(item)
        }

        panierItems.value = panierList
    }

}