package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.ExerciceTexteATrous
import edu.project.dlearn.domain.model.Vocabulaire
import kotlinx.coroutines.flow.Flow

interface ApprentissageRepository {
    fun getFlashcardsDues(niveau: String): Flow<List<Vocabulaire>>
    fun getExercicesTexteATrous(niveau: String): Flow<List<ExerciceTexteATrous>>
    suspend fun enregistrerResultatFlashcard(vocabulaireId: Long, connu: Boolean)
    suspend fun enregistrerResultatExercice(exerciceId: Long, reponseDonnee: String, estCorrecte: Boolean)
}
