package com.example.tp2_todo_list.data // Déclaration du package de la base de données

import android.content.ContentValues // Importation de la classe ContentValues pour gérer les valeurs à insérer ou mettre à jour dans la base de données
import android.content.Context // Importation de la classe Context pour gérer le contexte de l'application
import android.database.sqlite.SQLiteDatabase // Importation de la classe SQLiteDatabase pour gérer la base de données SQLite
import android.database.sqlite.SQLiteOpenHelper // Importation de la classe SQLiteOpenHelper pour gérer la création et la mise à jour de la base de données SQLite
import com.example.tp2_todo_list.model.Task // Importation de la classe Task pour définir une tâche

class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) { // Déclaration de la classe TaskDatabaseHelper qui hérite de la classe SQLiteOpenHelper

    companion object { // Déclaration d'un objet compagnon pour gérer l'instance unique de la classe TaskDatabaseHelper
        private val DB_NAME = "TodoList.db" // Définition du nom de la base de données
        private val DB_VERSION = 1 // Définition de la version de la base de données
        private var instance: TaskDatabaseHelper? = null // Définition de l'instance unique de la classe TaskDatabaseHelper

        fun getInstance(context: Context): TaskDatabaseHelper { // Méthode pour récupérer l'instance unique de la classe TaskDatabaseHelper
            if (instance == null) { // Vérification que l'instance unique n'existe pas encore
                instance = TaskDatabaseHelper(context) // Création de l'instance unique de la classe TaskDatabaseHelper
            }
            return instance!! // Retour de l'instance unique de la classe TaskDatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) { // Méthode appelée lorsque la base de données est créée
        // Création de la table TasksTable dans la base de données
        // SQL: "CREATE TABLE TasksTable(id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT NOT NULL, isDone BOOLEAN)"
        db!!.execSQL("CREATE TABLE TasksTable(id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT NOT NULL, isDone BOOLEAN)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { // Méthode appelée lorsque la base de données est mise à jour
        // Suppression de l'ancienne table TasksTable dans la base de données
        // SQL: "DROP TABLE IF EXISTS TasksTable"
        db!!.execSQL("DROP TABLE IF EXISTS TasksTable")
    }

    fun loadTasks(): List<Task> { // Méthode pour charger les tâches à partir de la base de données
        val db = this.writableDatabase // Récupération de la base de données en mode écriture
        // Récupération de toutes les colonnes de la table TasksTable dans la base de données
        // SQL: "SELECT *"
        val cursor = db.rawQuery("SELECT * FROM TasksTable", null)
        var tasksList: MutableList<Task> = mutableListOf<Task>() // Définition de la liste de tâches à retourner
        while (cursor!!.moveToNext()) { // Boucle sur les résultats de la requête SQL
            val id = cursor.getInt(0) // Récupération de l'ID de la tâche dans la base de données
            val description = cursor.getString(1) // Récupération de la description de la tâche dans la base de données
            val isDone = cursor.getInt(2) // Récupération de l'état de la tâche dans la base de données
            tasksList += Task(id, description, isDone != 0) // Ajout de la tâche à la liste de tâches à retourner
        }
        return tasksList.toList() // Retour de la liste de tâches
    }

    fun insertTask(task: Task) { // Méthode pour insérer une nouvelle tâche dans la base de données
        val db = this.writableDatabase // Récupération de la base de données en mode écriture
        val contentValues = ContentValues() // Définition des valeurs à insérer dans la base de données
        contentValues.put("description", task.description) // Définition de la description de la tâche à insérer dans la base de données
        contentValues.put("isDone", task.isDone) // Définition de l'état de la tâche à insérer dans la base de données
        db.insert("TasksTable", null, contentValues) // Insertion de la nouvelle tâche dans la table TasksTable de la base de données
    }

    fun updateTask(task: Task) { // Méthode pour mettre à jour une tâche existante dans la base de données
        val db = this.writableDatabase // Récupération de la base de données en mode écriture
        val contentValues = ContentValues() // Définition des valeurs à mettre à jour dans la base de données
        contentValues.put("description", task.description) // Définition de la nouvelle description de la tâche à mettre à jour dans la base de données
        contentValues.put("isDone", task.isDone) // Définition du nouveau état de la tâche à mettre à jour dans la base de données
        db.update("TasksTable", contentValues, "id = ?", arrayOf(task.id.toString())) // Mise à jour de la tâche existante dans la table TasksTable de la base de données en fonction de son ID
    }

    fun deleteTask(task: Task) { // Méthode pour supprimer une tâche existante
        val db = this.writableDatabase // Récupération de la base de données en mode écriture
        db.delete(
            "TasksTable",
            "id = ?",
            arrayOf(task.id.toString())
        ) // Suppression de la tâche existante dans la table TasksTable de la base de données
    }
}
