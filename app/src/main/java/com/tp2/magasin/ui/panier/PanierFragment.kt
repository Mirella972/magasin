package com.tp2.magasin.ui.panier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp2.magasin.R
import com.tp2.magasin.databinding.FragmentPanierBinding

class PanierFragment : Fragment() {

    private var _binding: FragmentPanierBinding? = null
    private var panierAdapter: PanierAdapter? = null
    private lateinit var totalTextView : TextView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPanierBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showViewModel =
            ViewModelProvider(requireActivity()).get(PanierViewModel::class.java)

        val recyclerView: RecyclerView = binding.rvPanier
        val context = recyclerView.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        totalTextView = view.findViewById(R.id.tv_total)
        panierAdapter = PanierAdapter(context)
        recyclerView.adapter = panierAdapter

        showViewModel.items.observe(viewLifecycleOwner) { lstItems ->
        panierAdapter!!.setItems(lstItems)
        }
        showViewModel.total.observe(viewLifecycleOwner) { totalValue ->
            totalTextView.text = "Total: $${String.format("%.2f", totalValue)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}