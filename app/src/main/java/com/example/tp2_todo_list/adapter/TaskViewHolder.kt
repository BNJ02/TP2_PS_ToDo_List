package com.example.tp2_todo_list.adapter // Déclaration du package de l'adapteur

import android.view.View // Importation de la classe View pour définir la vue de l'élément de la liste
import android.widget.Button
import android.widget.CheckBox // Importation de la classe CheckBox pour définir le bouton de sélection de l'élément de la liste
import android.widget.ImageButton
import android.widget.TextView // Importation de la classe TextView pour définir le texte de l'élément de la liste
import androidx.recyclerview.widget.RecyclerView // Importation de la classe RecyclerView pour gérer la liste de tâches
import com.example.tp2_todo_list.R // Importation de la classe R pour accéder aux ressources de l'application
import com.example.tp2_todo_list.model.Task // Importation de la classe Task pour définir une tâche

class TaskViewHolder( // Déclaration de la classe TaskViewHolder qui hérite de la classe RecyclerView.ViewHolder
    private val itemView: View, // Vue de l'élément de la liste
    private val onCheckClickTVH: (Int) -> Unit, // Gestionnaire de clics pour le bouton de sélection de l'élément de la liste
    private val onUpdateClickTVH: (Int) -> Unit, // Gestionnaire de clics pour le bouton de mise à jour de l'élément de la liste
    private val onDeleteClickTVH: (Int) -> Unit // Gestionnaire de clics pour le bouton de suppression de l'élément de la liste
) : RecyclerView.ViewHolder(itemView) {

    val elementTextView: TextView = itemView.findViewById(R.id.textView) // Définition du texte de l'élément de la liste
    val elementCheckBox: CheckBox = itemView.findViewById(R.id.checkBox) // Définition du bouton de sélection de l'élément de la liste
    val elementUpdateButton: ImageButton = itemView.findViewById(R.id.buttonUpdate) // Définition du bouton de mise à jour de l'élément de la liste
    val elementDeleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete) // Définition du bouton de suppression de l'élément de la liste

    init { // Bloc de code exécuté lors de la création de la classe TaskViewHolder
        elementCheckBox.setOnClickListener() { // Configuration du gestionnaire de clics pour le bouton de sélection de l'élément de la liste
            onCheckClickTVH(adapterPosition) // Appel du gestionnaire de clics avec la position de l'élément de la liste
        }
        elementUpdateButton.setOnClickListener() { // Configuration du gestionnaire de clics pour le bouton de mise à jour de l'élément de la liste
            onUpdateClickTVH(adapterPosition) // Appel du gestionnaire de clics avec la position de l'élément de la liste
        }
        elementDeleteButton.setOnClickListener {
            onDeleteClickTVH(adapterPosition)
        }
    }

    fun bind(t: Task) { // Méthode pour lier une tâche à l'élément de la liste
        elementTextView.text = t.description // Définition du texte de l'élément de la liste avec la description de la tâche
        elementCheckBox.isChecked = t.isDone // Définition de l'état de sélection du bouton de sélection de l'élément de la liste avec l'état de la tâche

    }
}
