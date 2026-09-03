package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.UtilisateurDao
import edu.project.dlearn.data.local.room.UtilisateurEntity
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import edu.project.dlearn.domain.repository.ResultatConnexion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dao: UtilisateurDao,
    // TODO: injecter SessionManager (DataStore) pour persister l'utilisateur connecté
    // entre deux lancements → utilisateurConnecte() retourne null en attendant.
) : AuthRepository {

    override suspend fun connecter(
        identifiant: String,
        motDePasse: String,
        role: Role
    ): ResultatConnexion {
        val entite = dao.trouverParIdentifiant(identifiant.trim(), role.name)
            ?: return ResultatConnexion.IdentifiantsInvalides
        if (hacher(motDePasse) != entite.motDePasseHash) return ResultatConnexion.IdentifiantsInvalides
        return ResultatConnexion.Succes(entite.toDomain())
    }

    override suspend fun connecterAuto(utilisateur: Utilisateur) {
        // Implementation dependante de SessionManager (DataStore)
    }

    override fun getAllProfils(): Flow<List<Utilisateur>> =
        dao.getAllUtilisateurs().map { list -> list.map { it.toDomain() } }

    override suspend fun deconnecter() {
        // TODO: effacer la session persistée (DataStore) une fois implémentée.
    }

    override suspend fun utilisateurConnecte(): Utilisateur? = null

    override suspend fun recupererProfilsLocaux(): List<Utilisateur> {
        // Cette methode pourrait etre marquee deprecated au profit de getAllProfils() (Flow)
        // Mais gardons-la pour la compatibilite si besoin, ou deleguons.
        return emptyList() 
    }

    // --- Helpers privés ---

    private fun UtilisateurEntity.toDomain() = Utilisateur(
        id       = id,
        identifiant = identifiant,
        nomAffiche  = nomAffiche,
        role     = Role.valueOf(role),
        classe   = classe,
        niveau   = niveau
    )

    // TODO production : remplacer SHA-256 nu par PBKDF2/BCrypt avec sel.
    private fun hacher(valeur: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(valeur.toByteArray())
            .joinToString("") { "%02x".format(it) }
}
