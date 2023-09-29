package com.tp2.magasin.ui.magasin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.tp2.magasin.databinding.FragmentMagasinBinding

class MagasinFragment : Fragment() {

    private var _binding: FragmentMagasinBinding? = null
    private var magasinAdapter: MagasinAdapter? = null
   //rivate var mItems: List<Item> = ArrayList<Item>(0)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       //agasinViewModel =
        //  ViewModelProvider(requireActivity()).get(MagasinAdapter::class.java)

        _binding = FragmentMagasinBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}