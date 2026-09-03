package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.datasource.SessionManager
import edu.project.dlearn.data.local.room.UtilisateurDao
import edu.project.dlearn.data.local.room.UtilisateurEntity
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import edu.project.dlearn.domain.repository.ResultatConnexion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.security.MessageDigest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dao: UtilisateurDao,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun connecter(
        identifiant: String,
        motDePasse: String,
        role: Role
    ): ResultatConnexion {
        val entite = dao.trouverParIdentifiant(identifiant.trim(), role.name)
            ?: return ResultatConnexion.IdentifiantsInvalides
        if (hacher(motDePasse) != entite.motDePasseHash) {
            return ResultatConnexion.IdentifiantsInvalides
        }
        val utilisateur = entite.toDomain()
        sessionManager.sauvegarderSession(utilisateur)
        return ResultatConnexion.Succes(utilisateur)
    }

    override suspend fun connecterAuto(utilisateur: Utilisateur) {
        sessionManager.sauvegarderSession(utilisateur)
    }

    override suspend fun deconnecter() {
        sessionManager.effacerSession()
    }

    override suspend fun utilisateurConnecte(): Utilisateur? {
        val userId = sessionManager.utilisateurIdFlow.first() ?: return null
        return dao.trouverParId(userId)?.toDomain()
    }

    override suspend fun recupererProfilsLocaux(): List<Utilisateur> {
        return dao.recupererTous().map { it.toDomain() }
    }

    override fun getAllProfils(): Flow<List<Utilisateur>> =
        dao.getAllUtilisateurs().map { list -> list.map { it.toDomain() } }

    // --- Helpers privés ---

    private fun UtilisateurEntity.toDomain() = Utilisateur(
        id          = id,
        identifiant = identifiant,
        nomAffiche  = nomAffiche,
        role        = Role.valueOf(role),
        classe      = classe,
        niveau      = niveau
    )

    // TODO production : remplacer SHA-256 nu par PBKDF2/BCrypt avec sel.
    private fun hacher(valeur: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(valeur.toByteArray())
            .joinToString("") { "%02x".format(it) }
}
