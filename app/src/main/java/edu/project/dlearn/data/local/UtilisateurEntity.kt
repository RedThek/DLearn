package edu.project.dlearn.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Mot de passe stocke en hash (voir AuthRepositoryImpl) : jamais en clair, meme en local.
@Entity(tableName = "utilisateur")
data class UtilisateurEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val identifiant: String,
    val motDePasseHash: String,
    val nomAffiche: String,
    val role: String, // "ELEVE" ou "ENSEIGNANT"
    val classe: String?
)
