package com.tp2.magasin

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.tp2.magasin.model.Item
import com.tp2.magasin.ui.panier.PanierViewModel

class MagasinAdapter(panier: PanierViewModel, private val context: Context, private val article: List<Item>) :
    RecyclerView.Adapter<MagasinAdapter.ViewHolder>(){


    // TODO : si menu admin fonction showAdminContextMenu()

        private var panier: PanierViewModel
        init {
            this.panier = panier
        }

    // TODO  : les clics pour ajouter au panier

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvName: TextView = itemView.findViewById(R.id.tv_name)
            var tvDescription: TextView = itemView.findViewById(R.id.tv_description)
            var tvPrix: TextView = itemView.findViewById(R.id.tv_prix)
            var imgCategorie: ImageView = itemView.findViewById(R.id.img_item)

            // Méthode pour créer une nouvelle ligne
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rangee, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item: Item = article[position]
                holder.tvName.text = item.name
                holder.tvDescription.text = item.description
                holder.tvPrix.text = Integer.toString(item.prix)
                val categorie = item.categorie
                val resources = context.resources
                val resourceId = resources.getIdentifier(categorie, "drawable", context.packageName)
                if (resourceId != 0){
                    val icon = resources.getDrawable(resourceId, context.theme)
                    holder.imgCategorie.setImageResource(resourceId)
                }else {
                    resources.getDrawable(android.R.drawable.ic_menu_help, context.theme)
                }
            }

            override fun getItemCount(): Int {
                return article.size
            }
        }
}