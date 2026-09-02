package edu.project.dlearn.domain.model

enum class Role { ELEVE, ENSEIGNANT }

data class Utilisateur(
    val id: Long,
    val identifiant: String,
    val nomAffiche: String,
    val role: Role,
    val classe: String? = null,
    val niveau: String? = null,
    val motDePasse: String? = null // Uniquement présent lors de la création ou en session locale sécurisée
)
