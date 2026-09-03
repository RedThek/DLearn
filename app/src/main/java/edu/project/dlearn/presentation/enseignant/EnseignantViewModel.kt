package edu.project.dlearn.presentation.enseignant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnseignantViewModel @Inject constructor(
    private val getAllUnites: GetAllUnitesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnseignantUiState())
    val uiState: StateFlow<EnseignantUiState> = _uiState.asStateFlow()

    init { charger() }

    private fun charger() {
        viewModelScope.launch {
            val unites = getAllUnites().first()
            _uiState.update { it.copy(
                unitesDisponibles = unites,
                // TODO Sprint 3 : lire les vrais élèves depuis UtilisateurDao + ProgressionDao
                eleves = listOf(
                    EleveResume(1L, "Aïcha N.",   "Classe de 3e", "A1", unitesTerminees = 0, scoreMoyen = 0),
                    EleveResume(2L, "Paul K.",    "Classe de 5e", "A1", unitesTerminees = 0, scoreMoyen = 0)
                ),
                enseignantNom = "M. Fotso",
                enChargement  = false
            )}
        }
    }

    fun onChangerOnglet(onglet: OngletEnseignant) {
        _uiState.update { it.copy(ongletActif = onglet) }
    }
}
