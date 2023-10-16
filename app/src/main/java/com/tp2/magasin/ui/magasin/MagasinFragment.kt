package com.tp2.magasin.ui.magasin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp2.magasin.MagasinAdapter
import com.tp2.magasin.data.ItemDao
import com.tp2.magasin.data.ItemRoomDB
import com.tp2.magasin.databinding.FragmentMagasinBinding
import com.tp2.magasin.model.Item
import com.tp2.magasin.ui.panier.PanierViewModel
import kotlin.concurrent.thread

class MagasinFragment : Fragment() {

    private var _binding: FragmentMagasinBinding? = null
    private var magasinAdapter: MagasinAdapter? = null
    private var mItems: List<Item> = ArrayList<Item>(0)

    private val binding get() = _binding!!

    private lateinit var panier: PanierViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        panier = ViewModelProvider(requireActivity()).get(PanierViewModel::class.java)
        _binding = FragmentMagasinBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding!!.rvMagasin
        val context = recyclerView.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        magasinAdapter = MagasinAdapter(panier, context, mItems)
        recyclerView.adapter = magasinAdapter

        // Création de l'écouteur d'événements pour le RecyclerView - interface OnItemClickListenerInterface
        /*
        val onItemClickListener : MagasinAdapter.OnItemClickListenerInterface =
            object : MagasinAdapter.OnItemClickListenerInterface {
                override fun onItemClick(itemView: View?, position: Int) {
                    val item = article[position]
                    panier.addItemToPanier(item)
                }
            }

         */

        val itemDao : ItemDao? = ItemRoomDB.getDatabase(context)?.ItemDao()
        var item : Item? = Item("Article","Desc Article",500,"meubles",3)
        thread { itemDao?.deleteAll() }.join()
        thread { itemDao?.insert(item) }.join()

        thread { mItems = itemDao?.getAllItem()!! }.join()
        magasinAdapter?.setItems(mItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}