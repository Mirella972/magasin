package com.tp2.magasin.ui.magasin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
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
import android.content.SharedPreferences
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.tp2.magasin.AddItemDialogFragment
import com.tp2.magasin.MainActivity
import com.tp2.magasin.R
import java.util.*


class MagasinFragment : Fragment() {

    private var _binding: FragmentMagasinBinding? = null
    private var magasinAdapter: MagasinAdapter? = null
    private lateinit var mItems: LiveData<List<Item>>


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


        val itemDao: ItemDao? = ItemRoomDB.getDatabase(context)?.ItemDao()

//        thread { itemDao?.deleteAll() }.join()

        thread { mItems = itemDao?.getAllItem()!! }.join()
        mItems.observe(requireActivity()) { lst ->
            magasinAdapter = MagasinAdapter(panier, context, lst)
            recyclerView.adapter = magasinAdapter
            Log.d("Clic", "onItemClick: ")

            val onItemClickListener:MagasinAdapter.OnItemClickListenerInterface=
                object : MagasinAdapter.OnItemClickListenerInterface {

                    override fun onItemClick(itemView: View?, position: Int) {
                        Log.d("Clic", "onItemClick: ")

                    }

                    override fun onClickEdit(itemView: View, position: Int) {
                        TODO("Not yet implemented")
                    }

                    override fun onClickDelete(position: Int) {
                        TODO("Not yet implemented")
                    }
                }

            magasinAdapter?.setOnItemClickListener(onItemClickListener)

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}