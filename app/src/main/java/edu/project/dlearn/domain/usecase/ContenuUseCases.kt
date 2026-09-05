package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.ExtraitAvecGlossaire
import edu.project.dlearn.domain.model.UniteApprentissage
import edu.project.dlearn.domain.repository.ContenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnitesParNiveauUseCase @Inject constructor(
    private val repository: ContenuRepository
) {
    operator fun invoke(niveauGer: String): Flow<List<UniteApprentissage>> =
        repository.getUnitesByNiveau(niveauGer)
}

class GetAllUnitesUseCase @Inject constructor(
    private val repository: ContenuRepository
) {
    operator fun invoke(): Flow<List<UniteApprentissage>> =
        repository.getAllUnites()
}

class GetExtraitAvecGlossaireUseCase @Inject constructor(
    private val repository: ContenuRepository
) {
    suspend operator fun invoke(uniteId: String): ExtraitAvecGlossaire? =
        repository.getExtraitAvecGlossaire(uniteId)
}

class GetUniteByIdUseCase @Inject constructor(
    private val repository: ContenuRepository
) {
    suspend operator fun invoke(uniteId: String): UniteApprentissage? = repository.getUniteById(uniteId)
}
