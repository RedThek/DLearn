package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Retourne un Flow de tous les élèves enregistrés sur l'appareil.
 * Utilisé par EnseignantViewModel pour afficher la liste de la classe (D-06).
 */
class GetElevesUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<List<Utilisateur>> = authRepository.getAllEleves()
}
