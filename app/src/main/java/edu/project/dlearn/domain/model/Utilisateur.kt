package edu.project.dlearn.domain.model

enum class Role { ELEVE, ENSEIGNANT }

data class Utilisateur(
    val id: Long,
    val identifiant: String,
    val nomAffiche: String,
    val role: Role,
    val classe: String? = null // ex: "Classe de 3e", pertinent uniquement pour un Eleve
)
