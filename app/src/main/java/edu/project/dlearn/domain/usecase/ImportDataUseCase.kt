package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.ImportResume
import edu.project.dlearn.domain.repository.SyncRepository
import javax.inject.Inject

class ImportDataUseCase @Inject constructor(
    private val repository: SyncRepository
) {
    suspend operator fun invoke(uriString: String): Result<ImportResume> = repository.importerDonnees(uriString)
}
