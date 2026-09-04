package edu.project.dlearn.presentation.exercice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.core.AppConstants
import edu.project.dlearn.domain.usecase.EnregistrerReponseExerciceUseCase
import edu.project.dlearn.domain.usecase.GetExercicesByUniteUseCase
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciceViewModel @Inject constructor(
    private val getExercicesByUnite: GetExercicesByUniteUseCase,
    private val enregistrerReponse: EnregistrerReponseExerciceUseCase,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val uniteId: String = checkNotNull(savedStateHandle["uniteId"]) {
        "ExerciceViewModel requiert un argument de navigation 'uniteId'"
    }

    private val _uiState = MutableStateFlow<ExerciceUiState>(ExerciceUiState.Chargement)
    val uiState: StateFlow<ExerciceUiState> = _uiState.asStateFlow()

    private var bonnesReponses = 0

    init {
        viewModelScope.launch {
            val exercices = getExercicesByUnite(uniteId).first()
            _uiState.value = if (exercices.isEmpty()) {
                ExerciceUiState.Vide
            } else {
                ExerciceUiState.EnCours(exercices = exercices, indexActuel = 0)
            }
        }
    }

    fun onSelectionnerReponse(reponse: String) {
        val etat = _uiState.value as? ExerciceUiState.EnCours ?: return
        if (etat.resultat != null) return // déjà validé, ne rien changer
        _uiState.value = etat.copy(reponseSelectionnee = reponse)
    }

    fun onValider() {
        val etat = _uiState.value as? ExerciceUiState.EnCours ?: return
        val reponse = etat.reponseSelectionnee ?: return
        viewModelScope.launch {
            val eleveId = getUtilisateurConnecte()?.id ?: AppConstants.ELEVE_DEMO_ID
            val estCorrecte = enregistrerReponse(eleveId, etat.exerciceActuel, reponse)
            if (estCorrecte) bonnesReponses++
            _uiState.value = etat.copy(resultat = estCorrecte)
        }
    }

    fun onSuivant() {
        val etat = _uiState.value as? ExerciceUiState.EnCours ?: return
        val indexSuivant = etat.indexActuel + 1
        _uiState.value = if (indexSuivant < etat.exercices.size) {
            etat.copy(indexActuel = indexSuivant, reponseSelectionnee = null, resultat = null)
        } else {
            ExerciceUiState.Termine(bonnesReponses = bonnesReponses, total = etat.exercices.size)
        }
    }
}
