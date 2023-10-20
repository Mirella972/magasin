package com.tp2.magasin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
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
        var admin: Boolean = false
        lateinit var item: Item
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initiation de la Toolbar : menu Admin
        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        //TODO : ajouter un logo application voir demo cours-06-cours

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

        if (admin) {
            binding.btnAjout.show()
        } else {
            binding.btnAjout.hide()
        }

        binding.btnAjout.setOnClickListener {
            val dialog = EditItemDialogFragment()
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
                binding.btnAjout.show()
            } else {
                binding.btnAjout.hide()
            }
        }
        return true
    }

    //Gestion du clic sur les items du menu admin
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.admin_switch -> {
                // Voir MagasinAdapter
                // TODO : faire menu contextuel Admin permet la modif et sup des items
                //showAdminContextMenu()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun onAjoutItem(name: String, desc: String, prix: Int, cat: String) {
        val itemDao: ItemDao? = ItemRoomDB.getDatabase(this)?.ItemDao()

        item = Item(name, desc, prix, cat, 0)

        thread { itemDao?.insert(item) }.join()
    }
}