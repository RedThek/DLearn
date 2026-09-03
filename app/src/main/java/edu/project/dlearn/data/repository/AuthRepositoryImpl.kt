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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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

    override fun utilisateurConnecteFlow(): Flow<Utilisateur?> =
        sessionManager.utilisateurIdFlow.flatMapLatest { userId ->
            if (userId == null) flowOf(null)
            else flow { emit(dao.trouverParId(userId)?.toDomain()) }
        }

    override fun getAllEleves(): Flow<List<Utilisateur>> =
        dao.getEleves().map { list -> list.map { it.toDomain() } }

    override suspend fun creerEleve(
        nomComplet: String,
        classe: String,
        niveau: String
    ): Utilisateur {
        val identifiant    = genererIdentifiant(nomComplet)
        val motDePasseClair = genererMotDePasse()
        val entite = UtilisateurEntity(
            identifiant    = identifiant,
            motDePasseHash = hacher(motDePasseClair),
            nomAffiche     = nomComplet.trim(),
            role           = "ELEVE",
            classe         = classe,
            niveau         = niveau
        )
        dao.insererUtilisateurs(listOf(entite))
        val inserted = dao.trouverParIdentifiant(identifiant, "ELEVE")
            ?: error("Insertion échouée pour $identifiant")
        return Utilisateur(
            id          = inserted.id,
            identifiant = identifiant,
            nomAffiche  = nomComplet.trim(),
            role        = Role.ELEVE,
            classe      = classe,
            niveau      = niveau,
            motDePasse  = motDePasseClair   // clair uniquement pour l'affichage enseignant (D-05)
        )
    }

    /** Génère un identifiant de la forme "prenom.i.XXXX" (ex. "divine.k.4821"). */
    private fun genererIdentifiant(nomComplet: String): String {
        val parties = nomComplet.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
        val prenom  = parties.firstOrNull()?.lowercase()
            ?.filter { it.isLetter() }?.take(8) ?: "eleve"
        val initiale = parties.getOrNull(1)?.lowercase()
            ?.filter { it.isLetter() }?.take(1) ?: ""
        val suffixe = (1000..9999).random()
        return if (initiale.isNotEmpty()) "$prenom.$initiale.$suffixe" else "$prenom.$suffixe"
    }

    /** Génère un mot de passe de la forme "ikii.XXXXXX" lisible et mémorisable. */
    private fun genererMotDePasse(): String {
        val chars = "abcdefghjkmnpqrstuvwxyz23456789"
        val corps = (1..6).map { chars.random() }.joinToString("")
        return "ikii.$corps"
    }

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
