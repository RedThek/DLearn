package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur
import kotlinx.coroutines.flow.Flow

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

    /**
     * Flow réactif de l'utilisateur actuellement en session.
     * Émet null si aucune session n'est active.
     * Réagit automatiquement à toute connexion / déconnexion via SessionManager.
     * Utilisé par NavViewModel pour propager le rôle dans le graphe de navigation (D-01, ADR-015).
     */
    fun utilisateurConnecteFlow(): Flow<Utilisateur?>

    /**
     * Crée un nouveau compte élève local, génère son identifiant et son mot de passe.
     * Retourne l'Utilisateur créé avec le mot de passe en clair (pour affichage enseignant).
     * Le mot de passe est ensuite hashé en base — jamais stocké en clair (D-05).
     */
    suspend fun creerEleve(nomComplet: String, classe: String, niveau: String): Utilisateur

    /** Flow de tous les comptes élève (rôle ELEVE), triés par nom. */
    fun getAllEleves(): Flow<List<Utilisateur>>
}
