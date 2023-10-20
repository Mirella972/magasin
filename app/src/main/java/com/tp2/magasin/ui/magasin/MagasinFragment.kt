package com.tp2.magasin.ui.magasin

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
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.tp2.magasin.EditItemDialogFragment
import com.tp2.magasin.MainActivity


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

        val recyclerView: RecyclerView = binding.rvMagasin

        val context = recyclerView.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        // Récupérer les items
        val itemDao: ItemDao? = ItemRoomDB.getDatabase(context)?.ItemDao()
        thread { mItems = itemDao?.getAllItem()!! }.join()
        mItems.observe(requireActivity()) { lst ->
            magasinAdapter = MagasinAdapter(panier, context, lst, MainActivity.admin)
            recyclerView.adapter = magasinAdapter

            val onItemClickListener =
                object : MagasinAdapter.OnItemClickListenerInterface {

                    override fun onItemClick(itemView: View?, position: Int) {
                        Log.d("Clic", "onItemClick: ")


                    }
                    override fun onClickEdit(itemView: View, position: Int) {
//                        val item = magasinAdapter?.getItemId(position) as Item
//                        if (item != null){
//                            val dialog = EditItemDialogFragment()
//                            val args = Bundle()
//                            args.putString("name", item.name)
//                            args.putString("description", item.description)
//                            args.putInt("prix", item.prix)
//
//                            dialog.arguments = args
//                            val fm: FragmentManager = supportFragmentManager
//                            dialog.show(fm, "fragment_edit_item")
//                        }

                    }
                    override fun onClickDelete(position: Int) {
                        val item = magasinAdapter?.getItemId(position) as Item
                        val itemDao: ItemDao? = ItemRoomDB.getDatabase(context)?.ItemDao()
                        itemDao?.deleteItem(item)
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