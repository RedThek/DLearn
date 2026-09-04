package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.Exercice
import edu.project.dlearn.domain.model.TypeExercice
import edu.project.dlearn.domain.repository.ExerciceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExercicesByUniteUseCase @Inject constructor(
    private val repository: ExerciceRepository
) {
    operator fun invoke(uniteId: String): Flow<List<Exercice>> =
        repository.getExercicesByUnite(uniteId)
}

/**
 * Corrige et enregistre la réponse d'un élève à un exercice, quel que soit son type.
 * PRODUCTION_GUIDEE n'a pas de correction automatique en Phase 1 (FR-19 différé) :
 * elle est considérée "vue" (estCorrecte = true) pour ne pas bloquer la progression,
 * conformément à FR-17 (auto-évaluation manuelle, hors correction automatique).
 */
class EnregistrerReponseExerciceUseCase @Inject constructor(
    private val repository: ExerciceRepository
) {
    suspend operator fun invoke(eleveId: Long, exercice: Exercice, reponseDonnee: String): Boolean {
        val estCorrecte = when (exercice.type) {
            TypeExercice.QCM ->
                exercice.options.any { it.estCorrecte && it.id == reponseDonnee }
            TypeExercice.VRAI_FAUX ->
                reponseDonnee.equals(exercice.correctionAttendue, ignoreCase = true)
            TypeExercice.TEXTE_A_TROUS -> {
                val reponsesAttendues = exercice.correctionAttendue?.split("|") ?: emptyList()
                reponsesAttendues.any { it.trim().equals(reponseDonnee.trim(), ignoreCase = true) }
            }
            TypeExercice.PRODUCTION_GUIDEE -> true
        }
        repository.enregistrerReponse(eleveId, exercice.id, reponseDonnee, estCorrecte)
        return estCorrecte
    }
}
