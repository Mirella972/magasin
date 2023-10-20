package com.tp2.magasin.ui.panier

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tp2.magasin.MagasinAdapter
import com.tp2.magasin.R
import com.tp2.magasin.model.Item
import java.lang.reflect.Type

class PanierAdapter(private val context: Context) :
    RecyclerView.Adapter<PanierAdapter.ViewHolder>() {

    private var panierList: List<Item> = mutableListOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvPrix: TextView = itemView.findViewById(R.id.tv_prix)
        var imgPerson: ImageView = itemView.findViewById(R.id.img_item)
        var quantite: TextView = itemView.findViewById(R.id.tv_quantite)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rangee_panier, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return panierList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Item = panierList[position]

        holder.tvName.setText(item.name)
        holder.tvPrix.setText(item.prix.toString())
        holder.quantite.setText("Quantité: ${item.quantite}")

        var categorie = item.categorie.lowercase()
        val resources = context.resources
        val resourceId = resources.getIdentifier(categorie, "drawable", context.packageName)

        if (resourceId != 0) {
            holder.imgPerson.setImageResource(resourceId)
        } else {
            holder.imgPerson.setImageResource(android.R.drawable.ic_menu_help)
        }


    }
    fun setItems(items: List<Item>) {
         panierList = items
        notifyDataSetChanged()
    }

}