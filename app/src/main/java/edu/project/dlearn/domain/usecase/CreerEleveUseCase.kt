package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Crée un compte élève local et retourne l'Utilisateur avec le mot de passe en clair.
 * À utiliser uniquement depuis l'interface enseignant (CreationEleveViewModel).
 */
class CreerEleveUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        nomComplet: String,
        classe: String,
        niveau: String
    ): Utilisateur = authRepository.creerEleve(nomComplet, classe, niveau)
}
