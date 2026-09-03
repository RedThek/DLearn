package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Retourne l'utilisateur actuellement connecté (lecture unique, suspend).
 * Retourne null si aucune session active.
 */
class GetUtilisateurConnecteUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Utilisateur? = repository.utilisateurConnecte()
}

/**
 * Flow réactif de l'utilisateur connecté.
 * Émet à chaque changement de session (connexion / déconnexion).
 */
class ObserverUtilisateurConnecteUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Utilisateur?> = repository.utilisateurConnecteFlow()
}
