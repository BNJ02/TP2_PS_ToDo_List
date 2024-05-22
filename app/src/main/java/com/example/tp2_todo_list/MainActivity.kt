package com.example.tp2_todo_list                                   // Déclaration du package de l'application

import android.content.Intent                                       // Importation de la classe Intent pour lancer une nouvelle activité
import android.os.Bundle                                            // Importation de la classe Bundle pour gérer les données entre les activités
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity                     // Importation de la classe AppCompatActivity pour utiliser l'API AppCompat
import com.example.tp2_todo_list.adapter.TaskAdapter                // Importation de la classe TaskAdapter pour gérer la liste de tâches
import com.example.tp2_todo_list.databinding.ActivityMainBinding    // Importation de la classe ActivityMainBinding pour gonfler le layout de l'activité
import com.example.tp2_todo_list.model.Task                         // Importation de la classe Task pour définir une tâche
import com.example.tp2_todo_list.data.TaskDatabaseHelper            // Importation de la classe TaskDatabaseHelper pour gérer la base de données SQLite

class MainActivity : AppCompatActivity() { // Déclaration de la classe MainActivity qui hérite de la classe AppCompatActivity
    private lateinit var binding : ActivityMainBinding // Déclaration d'une variable lateinit pour gonfler le layout de l'activité
    private lateinit var taskDatabaseHelper: TaskDatabaseHelper // Déclaration d'une variable lateinit pour gérer la base de données SQLite
    private lateinit var recyclerAdapter: TaskAdapter // Déclaration d'une variable lateinit pour gérer la liste de tâches

    override fun onCreate(savedInstanceState: Bundle?) { // Méthode appelée lorsque l'activité est créée
        super.onCreate(savedInstanceState) // Appel de la méthode onCreate() de la classe parente

        binding = ActivityMainBinding.inflate(layoutInflater) // Gonflage du layout de l'activité à l'aide de la classe ActivityMainBinding
        setContentView(binding.root) // Définition du layout de l'activité

        // Initialisation de la base de données SQLite
        taskDatabaseHelper = TaskDatabaseHelper.getInstance(baseContext)

        // Récupération de la liste de tâches à partir de la base de données SQLite
        var tasksList = taskDatabaseHelper.loadTasks()

        // Création de l'adapteur pour la liste de tâches
        recyclerAdapter = TaskAdapter(
            tasksList = tasksList, // Liste de tâches à afficher
            onCheckClickTA = { t: Task -> this.onCheckClick(t)}, // Gestionnaire de clics pour les éléments de la liste
            onEditClickTA = { t: Task -> this.onEditClick(t)}, // Gestionnaire de clics pour les éléments de la liste
            onDeleteClickTA = { t: Task -> this.onDeleteClick(t)} // Gestionnaire de clics long pour les éléments de la liste
        )

        // Définition de l'adapteur pour la RecyclerView
        binding.recyclerView1.adapter = recyclerAdapter

        // Configuration du bouton flottant (FloatingActionButton) pour lancer une nouvelle activité (EditTaskActivity)
        binding.floatingActionButton.setOnClickListener() {
            val myIntent = Intent(this, EditTaskActivity::class.java) // Création d'une nouvelle Intent pour lancer l'activité EditTaskActivity
            startActivity(myIntent) // Lancement de l'activité EditTaskActivity
        }
    }

    override fun onResume() { // Méthode appelée lorsque l'activité est à nouveau affichée à l'utilisateur
        super.onResume() // Appel de la méthode onResume() de la classe parente

        // Récupération de la liste de tâches mise à jour à partir de la base de données SQLite
        val updatedTasksList = taskDatabaseHelper.loadTasks()

        // Mise à jour de l'adapteur avec la nouvelle liste de tâches
        recyclerAdapter.swapAdapter(updatedTasksList)
    }

    // Méthode appelée lorsque l'utilisateur clique sur un élément de la liste de tâches
    fun onCheckClick(task: Task) { // Méthode pour gérer les clics sur les éléments de la liste de tâches
        task.isDone = !task.isDone // Inversion de la propriété isDone de la tâche
        taskDatabaseHelper.updateTask(task) // Mise à jour de la tâche dans la base de données SQLite
    }

    // Méthode appelée lorsque l'adapteur a besoin de mettre à jour le ViewHolder de la liste de tâches
    fun onEditClick(task: Task) {
        // Création d'une nouvelle Intent pour lancer l'activité EditTaskActivity
        val myEditIntent = Intent(this, EditTaskActivity::class.java)
        myEditIntent.putExtra("task_id", task.id)
        myEditIntent.putExtra("task_description", task.description)
        myEditIntent.putExtra("task_isDone", task.isDone)
        this.startActivity(myEditIntent)
    }

    // Méthode appelée lorsque l'adapteur a besoin de supprimer un élément de la liste de tâches
    fun onDeleteClick(task: Task) {
        // Suppression de la tâche de la base de données SQLite
        taskDatabaseHelper.deleteTask(task)
        // Récupération de la liste de tâches mise à jour à partir de la base de données SQLite
        val updatedTasksList = taskDatabaseHelper.loadTasks()
        // Mise à jour de l'adapteur avec la nouvelle liste de tâches
        recyclerAdapter.swapAdapter(updatedTasksList)
    }
}
