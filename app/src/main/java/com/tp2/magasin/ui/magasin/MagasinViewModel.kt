package com.tp2.magasin.ui.magasin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MagasinViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is magasin Fragment"
    }
    val text: LiveData<String> = _text
}