package com.tp2.magasin

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Exemple de boîte de dialogue personnalisée pour modifier le nom d'une personne
 */
class AddItemDialogFragment() : DialogFragment() {
    var position: Int = 0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { AlertDialog.Builder(it) }
        // Layout Inflater : Responsable de l'affichage du layout
        // requireActivity() : Servi par l'activité appelante (ici, MainActivity)
        val inflater = requireActivity().layoutInflater

        // Récupère les arguments passés à la boîte de dialogue depuis l'activité appelante
        arguments?.let {
            val name = it.getString("name")
            val desc = it.getString("desc")
            val prix = it.getInt("prix")
            position = it.getInt("position")
            builder?.setTitle("Modifier le nom de $name")
        }

        // Importe le layout de la boîte de dialogue
        // Le paramètre null est nécessaire car le layout est directement lié à la boîte de dialogue et non ancré dans un parent
        builder?.setView(inflater.inflate(R.layout.fragment_edit, null))
            // Gestion des boutons Ok et Annuler
            ?.setPositiveButton("OK") { dialog, id ->
                val name =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.et_name)?.text.toString()
                val desc =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.et_description)?.text.toString()
                val prix =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.et_price)?.text.toString()
                        .toInt()
                val cat =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.et_cat)?.text.toString()
                // Retourne le nom modifié à l'activité
                (activity as MainActivity).onNameChange(name, desc,prix,cat)
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