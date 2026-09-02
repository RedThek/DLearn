package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur

sealed interface ResultatConnexion {
    data class Succes(val utilisateur: Utilisateur) : ResultatConnexion
    data object IdentifiantsInvalides : ResultatConnexion
}

interface AuthRepository {
    // Authentification 100% locale : les comptes eleve/enseignant sont provisionnes
    // par lenseignant (import via le meme mecanisme dechange de fichiers que le
    // BYOD sync, cf. ADR-004), pas dappel reseau.
    suspend fun connecter(identifiant: String, motDePasse: String, role: Role): ResultatConnexion
    suspend fun connecterAuto(utilisateur: Utilisateur)
    suspend fun deconnecter()
    suspend fun utilisateurConnecte(): Utilisateur?
    suspend fun recupererProfilsLocaux(): List<Utilisateur>

    /**
     * Flux de tous les profils locaux (Élève + Enseignant).
     * Utilisé par le sélecteur multi-profil (FR-04, FR-33, ADR-009).
     */
    fun getAllProfils(): kotlinx.coroutines.flow.Flow<List<edu.project.dlearn.domain.model.Utilisateur>>
}
