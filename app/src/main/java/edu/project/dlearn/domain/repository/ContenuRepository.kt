package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.ExtraitAvecGlossaire
import edu.project.dlearn.domain.model.UniteApprentissage
import kotlinx.coroutines.flow.Flow

interface ContenuRepository {
    fun getAllUnites(): Flow<List<UniteApprentissage>>
    fun getUnitesByNiveau(niveauGer: String): Flow<List<UniteApprentissage>>
    suspend fun getExtraitAvecGlossaire(uniteId: String): ExtraitAvecGlossaire?

    /** Récupère une unité précise par son ID — utilisé par EcritureViewModel (correctif B-29). */
    suspend fun getUniteById(uniteId: String): UniteApprentissage?
}
