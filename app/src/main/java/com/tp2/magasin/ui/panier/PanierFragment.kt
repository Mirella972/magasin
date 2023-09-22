package com.tp2.magasin.ui.panier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tp2.magasin.databinding.FragmentPanierBinding

class PanierFragment : Fragment() {

    private var _binding: FragmentPanierBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val panierViewModel =
            ViewModelProvider(this).get(PanierViewModel::class.java)

        _binding = FragmentPanierBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPanier
        panierViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}