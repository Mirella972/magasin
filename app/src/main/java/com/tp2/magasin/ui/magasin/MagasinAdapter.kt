package com.tp2.magasin


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tp2.magasin.model.Item
import com.tp2.magasin.ui.panier.PanierViewModel

class MagasinAdapter(
    private var panier: PanierViewModel,
    private val context: Context
) :
    RecyclerView.Adapter<MagasinAdapter.ViewHolder>() {

    private var items: List<Item> = mutableListOf()
    private var isAdmin: Boolean = false

    init {
        this.panier = panier
    }

    //Interface pour gérer les clics sur un item de la liste
    interface OnItemClickListenerInterface {
        fun onItemClick(itemView: View?, position: Int)
        fun onClickEdit(itemView: View, position: Int)
        fun onClickDelete(position: Int)
    }

    lateinit var listener: OnItemClickListenerInterface

    fun setOnItemClickListener(listener: OnItemClickListenerInterface) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        var tvPrix: TextView = itemView.findViewById(R.id.tv_prix)
        var imgCategorie: ImageView = itemView.findViewById(R.id.img_item)

        init {

            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->

                if (isAdmin) {
                    val position = adapterPosition
                    Toast.makeText(context, "Item clicked. isAdmin: $isAdmin", Toast.LENGTH_SHORT)
                        .show()

                    val edit: android.view.MenuItem = menu.add(0, v.id, 0, R.string.action_edit)
                    val delete: android.view.MenuItem = menu.add(0, v.id, 0, R.string.action_delete)
                    // Ajoute un écouteur d'événement sur les items du menu contextuel
                    edit.setOnMenuItemClickListener {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickEdit(itemView, position)
                        }
                        false
                    }
                    delete.setOnMenuItemClickListener {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickDelete(position)
                        }
                        false
                    }
                }

            }
        }
    }

    // Méthode pour créer une nouvelle ligne
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rangee, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Item = items[position]
        item.quantite = 1
        holder.tvName.text = item.name
        holder.itemView.tag = position
        holder.tvDescription.text = item.description
        holder.tvPrix.text = Integer.toString(item.prix)
        var categorie = item.categorie.lowercase()

        val resources = context.resources
        val resourceId = resources.getIdentifier(categorie, "drawable", context.packageName)

        if (resourceId != 0) {
            holder.imgCategorie.setImageResource(resourceId)
        } else {
            holder.imgCategorie.setImageResource(android.R.drawable.ic_menu_help)
        }

        // gestion du clic
        holder.itemView.setOnClickListener {
            panier.addItemToPanier(item)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(lst_items: List<Item>) {
        items = lst_items
        notifyDataSetChanged()
    }

    fun updateAdminStatus(admin: Boolean) {
        isAdmin = admin
        notifyDataSetChanged()
    }

}