package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.UtilisateurDao
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import edu.project.dlearn.domain.repository.ResultatConnexion
import java.security.MessageDigest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dao: UtilisateurDao
    // TODO: injecter un SessionManager (DataStore) pour persister lutilisateur connecte
    // entre deux lancements de lapp -> utilisateurConnecte() lit actuellement toujours null.
) : AuthRepository {

    override suspend fun connecter(identifiant: String, motDePasse: String, role: Role): ResultatConnexion {
        val entite = dao.trouverParIdentifiant(identifiant.trim(), role.name) ?: return ResultatConnexion.IdentifiantsInvalides
        val hashSaisi = hacher(motDePasse)
        if (hashSaisi != entite.motDePasseHash) return ResultatConnexion.IdentifiantsInvalides

        return ResultatConnexion.Succes(
            Utilisateur(
                id = entite.id,
                identifiant = entite.identifiant,
                nomAffiche = entite.nomAffiche,
                role = role,
                classe = entite.classe
            )
        )
    }

    override suspend fun deconnecter() {
        // TODO: effacer la session persistee (DataStore) une fois implementee.
    }

    override suspend fun utilisateurConnecte(): Utilisateur? = null

    // TODO production: remplacer SHA-256 simple par un hachage avec sel (ex: PBKDF2 / BCrypt),
    // le SHA-256 nu ici sert uniquement de placeholder pour la demonstration.
    private fun hacher(valeur: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(valeur.toByteArray())
            .joinToString("") { "%02x".format(it) }
}
