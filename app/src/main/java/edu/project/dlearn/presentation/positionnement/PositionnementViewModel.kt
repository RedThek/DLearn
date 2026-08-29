package edu.project.dlearn.presentation.positionnement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import  edu.project.dlearn.domain.model.ResultatPositionnement
import  edu.project.dlearn.domain.repository.PositionnementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Seuil simplifie : >= 6/10 bonnes reponses -> A2, sinon A1.
// A affiner pedagogiquement (ponderation par competence testee, etc).
private const val SEUIL_NIVEAU_A2 = 6

@HiltViewModel
class PositionnementViewModel @Inject constructor(
    private val repository: PositionnementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PositionnementUiState>(PositionnementUiState.Chargement)
    val uiState: StateFlow<PositionnementUiState> = _uiState.asStateFlow()

    private var bonnesReponses = 0

    init {
        viewModelScope.launch {
            val questions = repository.getQuestions()
            _uiState.value = PositionnementUiState.EnCours(questions = questions, indexQuestion = 0)
        }
    }

    fun onSelectionnerOption(index: Int) {
        val etat = _uiState.value as? PositionnementUiState.EnCours ?: return
        _uiState.value = etat.copy(indexOptionSelectionnee = index)
    }

    fun onSuivant() {
        val etat = _uiState.value as? PositionnementUiState.EnCours ?: return
        val choix = etat.indexOptionSelectionnee ?: return

        if (choix == etat.question.indexReponseCorrecte) bonnesReponses++

        val indexSuivant = etat.indexQuestion + 1
        if (indexSuivant < etat.questions.size) {
            _uiState.value = etat.copy(indexQuestion = indexSuivant, indexOptionSelectionnee = null)
        } else {
            val niveau = if (bonnesReponses >= SEUIL_NIVEAU_A2) "A2" else "A1"
            viewModelScope.launch {
                repository.enregistrerResultat(
                    ResultatPositionnement(score = bonnesReponses, total = etat.questions.size, niveauPropose = niveau)
                )
            }
            _uiState.value = PositionnementUiState.Termine(
                niveauPropose = niveau,
                score = bonnesReponses,
                total = etat.questions.size
            )
        }
    }
}
