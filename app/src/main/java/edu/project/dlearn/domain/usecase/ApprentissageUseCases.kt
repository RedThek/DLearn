package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.ExerciceTexteATrous
import edu.project.dlearn.domain.model.Vocabulaire
import edu.project.dlearn.domain.repository.ApprentissageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Convention Clean Architecture : un fichier par use case en general.
// Regroupes ici uniquement pour la lisibilite de cette livraison.

class GetFlashcardsUseCase @Inject constructor(
    private val repository: ApprentissageRepository
) {
    operator fun invoke(niveau: String): Flow<List<Vocabulaire>> =
        repository.getFlashcardsDues(niveau)
}

class GetExercicesTexteATrousUseCase @Inject constructor(
    private val repository: ApprentissageRepository
) {
    operator fun invoke(niveau: String): Flow<List<ExerciceTexteATrous>> =
        repository.getExercicesTexteATrous(niveau)
}

class EnregistrerResultatFlashcardUseCase @Inject constructor(
    private val repository: ApprentissageRepository
) {
    suspend operator fun invoke(vocabulaireId: Long, connu: Boolean) =
        repository.enregistrerResultatFlashcard(vocabulaireId, connu)
}

class ValiderReponseExerciceUseCase @Inject constructor(
    private val repository: ApprentissageRepository
) {
    suspend operator fun invoke(exerciceId: Long, reponse: String, attendue: String) {
        val estCorrecte = reponse.trim().equals(attendue.trim(), ignoreCase = true)
        repository.enregistrerResultatExercice(exerciceId, reponse, estCorrecte)
    }
}
