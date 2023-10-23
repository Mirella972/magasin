package com.tp2.magasin

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Gestion de l'edition d'un item
 */
class EditItemDialogFragment(var ajout: Boolean, var position:Int) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { AlertDialog.Builder(it) }
        val inflater = requireActivity().layoutInflater
        var idItem : Int = 0

        // Récupère les arguments passés à la boîte de dialogue depuis l'activité appelante
        arguments?.let {
            idItem = it.getInt("id")
            val name = it.getString("name")
            builder?.setTitle("Modifier les informations de $name")
        }

        // Importe le layout de la boîte de dialogue pour ajout et edition
        builder?.setView(inflater.inflate(R.layout.fragment_edit, null))
            ?.setPositiveButton("OK") { dialog, id ->
                val name =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.et_name)?.text.toString()
                val description =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.et_description)?.text.toString()
                val prix =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.et_price)?.text.toString()
                        .toInt()
                val cat =
                    (dialog as AlertDialog).findViewById<Spinner>(R.id.et_cat)?.selectedItem.toString()
                if (ajout) {
                    (activity as MainActivity).onAjoutItem(name, description, prix, cat)
                } else {
                    (activity as MainActivity).onItemChange(idItem,name,description,prix, cat)
                }


            }
            ?.setNegativeButton("Annuler") { dialog, id ->
                getDialog()?.cancel()
            }
        if (builder != null) {
            return builder.create()
        }
        return super.onCreateDialog(savedInstanceState)
    }
}