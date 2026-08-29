package edu.project.dlearn.presentation.apprentissage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.usecase.EnregistrerResultatFlashcardUseCase
import edu.project.dlearn.domain.usecase.GetExercicesTexteATrousUseCase
import edu.project.dlearn.domain.usecase.GetFlashcardsUseCase
import edu.project.dlearn.domain.usecase.ValiderReponseExerciceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: le niveau CECR devrait venir du profil eleve connecte (DataStore / Room).
// Code en dur ici pour simplifier la demonstration.
private const val NIVEAU_PAR_DEFAUT = "A1"

@HiltViewModel
class ApprentissageViewModel @Inject constructor(
    private val getFlashcards: GetFlashcardsUseCase,
    private val getExercices: GetExercicesTexteATrousUseCase,
    private val enregistrerResultatFlashcard: EnregistrerResultatFlashcardUseCase,
    private val validerReponseExercice: ValiderReponseExerciceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ApprentissageUiState>(ApprentissageUiState.Chargement)
    val uiState: StateFlow<ApprentissageUiState> = _uiState.asStateFlow()

    init {
        chargerDonnees()
    }

    private fun chargerDonnees() {
        viewModelScope.launch {
            combine(
                getFlashcards(NIVEAU_PAR_DEFAUT),
                getExercices(NIVEAU_PAR_DEFAUT)
            ) { flashcards, exercices ->
                ApprentissageUiState.Succes(flashcards = flashcards, exercices = exercices)
            }.collect { nouvelEtat ->
                _uiState.value = nouvelEtat
            }
        }
    }

    fun onReponseFlashcard(connu: Boolean) {
        val etat = _uiState.value as? ApprentissageUiState.Succes ?: return
        val carteActuelle = etat.flashcards.getOrNull(etat.indexFlashcardActuelle) ?: return

        viewModelScope.launch {
            enregistrerResultatFlashcard(carteActuelle.id, connu)
        }

        val indexSuivant = (etat.indexFlashcardActuelle + 1)
            .coerceAtMost(etat.flashcards.lastIndex.coerceAtLeast(0))
        _uiState.value = etat.copy(indexFlashcardActuelle = indexSuivant)
    }

    fun onValiderExercice(exerciceId: Long, reponse: String, attendue: String) {
        viewModelScope.launch {
            validerReponseExercice(exerciceId, reponse, attendue)
        }
    }

    fun onChangerOnglet(modeFlashcard: Boolean) {
        val etat = _uiState.value as? ApprentissageUiState.Succes ?: return
        _uiState.value = etat.copy(ongletFlashcard = modeFlashcard)
    }
}
