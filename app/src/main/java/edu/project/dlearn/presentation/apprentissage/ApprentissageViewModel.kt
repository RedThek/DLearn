package edu.project.dlearn.presentation.apprentissage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.model.ExtraitAvecGlossaire
import edu.project.dlearn.domain.model.UniteApprentissage
import edu.project.dlearn.domain.usecase.EnregistrerResultatFlashcardUseCase
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import edu.project.dlearn.domain.usecase.GetExtraitAvecGlossaireUseCase
import edu.project.dlearn.domain.usecase.GetFlashcardsUseCase
import edu.project.dlearn.domain.usecase.ValiderReponseExerciceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApprentissageViewModel @Inject constructor(
    private val getAllUnites: GetAllUnitesUseCase,
    private val getExtrait: GetExtraitAvecGlossaireUseCase,
    private val getFlashcards: GetFlashcardsUseCase,
    private val enregistrerFlashcard: EnregistrerResultatFlashcardUseCase,
    private val validerReponse: ValiderReponseExerciceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ApprentissageUiState>(ApprentissageUiState.Chargement)
    val uiState: StateFlow<ApprentissageUiState> = _uiState.asStateFlow()

    init { chargerBibliotheque() }

    private fun chargerBibliotheque() {
        viewModelScope.launch {
            getAllUnites().collect { unites ->
                _uiState.value = ApprentissageUiState.Bibliotheque(unites = unites)
            }
        }
    }

    fun onOuvrirUnite(unite: UniteApprentissage) {
        viewModelScope.launch {
            _uiState.value = ApprentissageUiState.Chargement
            val extrait = getExtrait(unite.id)
            _uiState.value = ApprentissageUiState.LectureUnite(
                unite   = unite,
                extrait = extrait
            )
        }
    }

    fun onRetourBibliotheque() { chargerBibliotheque() }

    fun onReponseFlashcard(vocabulaireId: Long, connu: Boolean) {
        viewModelScope.launch { enregistrerFlashcard(vocabulaireId, connu) }
    }
}
