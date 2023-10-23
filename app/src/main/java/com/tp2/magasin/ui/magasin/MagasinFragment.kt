package com.tp2.magasin.ui.magasin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
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
import com.tp2.magasin.EditItemDialogFragment
import com.tp2.magasin.MainActivity


class MagasinFragment : Fragment() {

    private var _binding: FragmentMagasinBinding? = null
    private var magasinAdapter: MagasinAdapter? = null
    private lateinit var mItems: LiveData<List<Item>>

    private lateinit var itemList: List<Item>
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
        magasinAdapter = MagasinAdapter(panier, context)
        recyclerView.adapter = magasinAdapter

        // Observe si menu admin est actif
        MainActivity.admin.observe(viewLifecycleOwner) { isAdmin ->
            magasinAdapter?.updateAdminStatus(isAdmin)
        }

        // Observe la liste items
        mItems.observe(requireActivity()) { lst ->
        magasinAdapter!!.setItems(lst)
            itemList = lst

            val onItemClickListener =
                object : MagasinAdapter.OnItemClickListenerInterface {

                    override fun onItemClick(itemView: View?, position: Int) {
                        //Log.d("Adminnnn", "${MainActivity.admin}")
                    }

                    override fun onClickEdit(itemView: View, position: Int) {
                        val item = itemList[position]

                        item.let {
                            val dialog = EditItemDialogFragment(false, position).apply {
                                arguments = bundleOf(
                                    "id" to it.id,
                                    "name" to it.name,
                                    "description" to it.description,
                                    "prix" to it.prix
                                )
                            }
                            dialog.show((context as MainActivity).supportFragmentManager, "fragment_edit_item")
                        }
                    }


                    override fun onClickDelete(position: Int) {
                        val item = itemList[position]
                        val itemDao: ItemDao? = ItemRoomDB.getDatabase(context)?.ItemDao()
                        thread { itemDao?.deleteItem(item) }
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