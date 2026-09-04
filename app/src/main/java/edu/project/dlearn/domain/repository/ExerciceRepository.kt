package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.Exercice
import kotlinx.coroutines.flow.Flow

interface ExerciceRepository {
    /** Tous les exercices d'une unité, avec leurs options (uniquement pour les QCM). */
    fun getExercicesByUnite(uniteId: String): Flow<List<Exercice>>

    /** Persiste la réponse d'un élève à un exercice donné (table reponse_eleve). */
    suspend fun enregistrerReponse(
        eleveId: Long,
        exerciceId: String,
        reponseDonnee: String,
        estCorrecte: Boolean
    )
}
