package com.example.tp2_todo_list // Déclaration du package de l'application

import android.os.Bundle // Importation de la classe Bundle pour gérer les données entre les activités
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar // Importation de la classe Snackbar pour afficher des messages d'erreur
import androidx.appcompat.app.AppCompatActivity // Importation de la classe AppCompatActivity pour utiliser l'API AppCompat
import com.example.tp2_todo_list.databinding.ActivityEditTaskBinding // Importation de la classe ActivityEditTaskBinding pour gonfler le layout de l'activité
import com.example.tp2_todo_list.data.TaskDatabaseHelper // Importation de la classe TaskDatabaseHelper pour gérer la base de données SQLite
import com.example.tp2_todo_list.model.Task // Importation de la classe Task pour définir une tâche

class EditTaskActivity : AppCompatActivity() { // Déclaration de la classe EditTaskActivity qui hérite de la classe AppCompatActivity
    private lateinit var binding: ActivityEditTaskBinding // Déclaration d'une variable lateinit pour gonfler le layout de l'activité
    private lateinit var taskDatabaseHelper: TaskDatabaseHelper // Déclaration d'une variable lateinit pour gérer la base de données SQLite

    override fun onCreate(savedInstanceState: Bundle?) { // Méthode appelée lorsque l'activité est créée
        super.onCreate(savedInstanceState) // Appel de la méthode onCreate() de la classe parente

        binding = ActivityEditTaskBinding.inflate(layoutInflater) // Gonflage du layout de l'activité à l'aide de la classe ActivityEditTaskBinding
        setContentView(binding.root) // Définition du layout de l'activité

        taskDatabaseHelper = TaskDatabaseHelper.getInstance(baseContext) // Initialisation de la base de données SQLite

        // Récupération de l'ID de la tâche à partir de l'intent
        val taskId = intent.getIntExtra("task_id", -1)
        val taskDescription = intent.getStringExtra("task_description")
        val taskIsDone = intent.getBooleanExtra("task_isDone", false)

        if (taskId != -1) {
            // Pré-remplir le formulaire avec les informations de la tâche
            binding.editText.setText(taskDescription)
        }

        // Configuration du bouton de validation (validateButton) pour ajouter une nouvelle tâche à la base de données SQLite
        binding.validateButton.setOnClickListener() {
            // Récupération de la description de la tâche à partir du champ de texte (editText)
            val description = binding.editText.text.toString().trim()

            // Vérification que la description de la tâche n'est pas vide
            if (description.isNotEmpty()) {
                if (taskId != -1) {
                    // Si l'ID de la tâche est différent de -1, cela signifie que l'activité est appelée pour modifier une tâche existante
                    val task = Task(taskId, description, taskIsDone)
                    taskDatabaseHelper.updateTask(task)
                } else {
                    // Si l'ID de la tâche est égal à -1, cela signifie que l'activité est appelée pour ajouter une nouvelle tâche
                    // Création d'une nouvelle instance de la classe Task avec la description de la tâche
                    val task = Task(0, description, false)
                    taskDatabaseHelper.insertTask(task)
                }

                // Fermeture de l'activité EditTaskActivity pour revenir à l'activité précédente
                finish()
            } else {
                // Affichage d'un message d'erreur si la description de la tâche est vide
                Snackbar.make(binding.root, "La description de la tâche ne peut pas être vide", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
