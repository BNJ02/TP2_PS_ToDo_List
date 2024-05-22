package com.example.tp2_todo_list.adapter // Déclaration du package de l'adapteur

import android.view.LayoutInflater // Importation de la classe LayoutInflater pour gonfler le layout de l'élément de la liste
import android.view.ViewGroup // Importation de la classe ViewGroup pour définir le parent de l'élément de la liste
import androidx.recyclerview.widget.RecyclerView // Importation de la classe RecyclerView pour gérer la liste de tâches
import com.example.tp2_todo_list.databinding.ItemLayoutBinding // Importation de la classe ItemLayoutBinding pour gonfler le layout de l'élément de la liste
import com.example.tp2_todo_list.model.Task // Importation de la classe Task pour définir une tâche

class TaskAdapter( // Déclaration de la classe TaskAdapter qui hérite de la classe RecyclerView.Adapter
    private var tasksList : List<Task>, // Liste de tâches à afficher dans la liste
    private val onCheckClickTA : (Task) -> Unit, // Gestionnaire de clics pour les éléments de la liste
    private val onEditClickTA: (Task) -> Unit, // Nouvelle fonction de rappel pour gérer le clic sur le bouton "éditer"
    private val onDeleteClickTA: (Task) -> Unit // Nouvelle fonction de rappel pour gérer le clic sur le bouton "supprimer"
) : RecyclerView.Adapter<TaskViewHolder>() { // Définition du type de ViewHolder pour la liste de tâches (TaskViewHolder)

    // Méthode appelée lorsque l'adapteur crée un nouveau ViewHolder pour la liste de tâches
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)) // Gonflage du layout de l'élément de la liste à l'aide de la classe ItemLayoutBinding
        return TaskViewHolder( // Création d'un nouveau ViewHolder pour la liste de tâches
            itemView = binding.root, // Définition de la vue racine de l'élément de la liste
            onCheckClickTVH = { it -> onCheckClickTA(tasksList[it]) }, // Gestionnaire de clics pour les éléments de la liste
            onUpdateClickTVH = { it -> onEditClickTA(tasksList[it]) }, // Appeler la fonction onEditClick avec la tâche correspondante
            onDeleteClickTVH = { it -> onDeleteClickTA(tasksList[it]) } // Appeler la fonction onEditClick avec la tâche correspondante
        )
    }

    // Méthode appelée lorsque l'adapteur réutilise un ViewHolder existant pour la liste de tâches
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = tasksList[position] // Récupération de la tâche à afficher à partir de la liste de tâches
        holder.bind(item) // Liaison de la tâche à afficher avec le ViewHolder de la liste de tâches
    }

    // Méthode qui renvoie le nombre d'éléments dans la liste de tâches
    override fun getItemCount() : Int {
        return tasksList.size // Renvoi du nombre d'éléments dans la liste de tâches
    }

    // Méthode pour mettre à jour la liste de tâches dans l'adapteur
    fun swapAdapter(newTasksList: List<Task>) {
        tasksList = newTasksList // Mise à jour de la liste de tâches dans l'adapteur
        notifyDataSetChanged() // Notification à la liste de tâches que les données ont changé et qu'elle doit être mise à jour
    }
}
