package com.tp2.magasin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tp2.magasin.data.ItemDao
import com.tp2.magasin.data.ItemRoomDB
import com.tp2.magasin.databinding.ActivityMainBinding
import com.tp2.magasin.model.Item
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private val _admin = MutableLiveData<Boolean>().apply { value = false }
        val admin: LiveData<Boolean> get() = _admin

        fun setAdminStatus(status: Boolean) {
            _admin.value = status
        }
        lateinit var item: Item
        var selectedPosition: Int = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initiation de la Toolbar : menu Admin
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title =getString(R.string.title_magasin)
        supportActionBar?.setLogo(R.drawable.logo)

        // Initiation du menu principal
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_magasin, R.id.navigation_panier
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (_admin.value == true) {
            binding.btnAjout.show()
        } else {
            binding.btnAjout.hide()
        }

        binding.btnAjout.setOnClickListener {
            val dialog = EditItemDialogFragment(true, selectedPosition)
            // FragmentManager pour afficher le fragment de dialogue
            val fm: FragmentManager = supportFragmentManager
            dialog.show(fm, "fragment_edit_name")
        }
    }

    // Initiation du menu admin
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)

        val item = menu.findItem(R.id.admin_switch)
        val switchView = item.actionView as SwitchCompat


        switchView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setAdminStatus(true)
                binding.btnAjout.show()
            } else {
                setAdminStatus(false)
                binding.btnAjout.hide()
            }
        }
        return true
    }

    //Gestion du clic sur les items du menu admin
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.admin_switch -> {
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun onAjoutItem(name: String, desc: String, prix: Int, cat: String) {
        val itemDao: ItemDao? = ItemRoomDB.getDatabase(this)?.ItemDao()

        if(selectedPosition < 0) {
            item = Item(name, desc, prix, cat, 0)
            thread { itemDao?.insert(item) }.join()
        }

    }

    fun onItemChange(id: Int, name: String, description: String, prix: Int, cat: String) {
        val itemDao: ItemDao? = ItemRoomDB.getDatabase(this)?.ItemDao()
        thread {
            // Obtenir l'élément existant de la base de données
            val existingItem = itemDao?.getItemById(id)


            if (existingItem != null) {
                existingItem.name = name
                existingItem.categorie = cat
                existingItem.prix = prix
                existingItem.description = description
                itemDao?.updateItem(existingItem)

            }

        }
    }

}