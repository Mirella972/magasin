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
import com.tp2.magasin.databinding.FragmentMagasinBinding
import com.tp2.magasin.model.Item
import com.tp2.magasin.ui.panier.PanierViewModel

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
        magasinAdapter = MagasinAdapter(panier)
        recyclerView.adapter = magasinAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}