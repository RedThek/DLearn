package edu.project.dlearn.presentation.enseignant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import edu.project.dlearn.domain.usecase.GetElevesUseCase
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnseignantViewModel @Inject constructor(
    private val getAllUnites: GetAllUnitesUseCase,
    private val getEleves: GetElevesUseCase,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnseignantUiState())
    val uiState: StateFlow<EnseignantUiState> = _uiState.asStateFlow()

    init { charger() }

    private fun charger() {
        viewModelScope.launch {
            // Nom de l'enseignant connecté
            val enseignant = getUtilisateurConnecte()
            val unites = getAllUnites().first()

            // Collecter les élèves réels depuis Room (D-06)
            getEleves().collect { utilisateurs ->
                _uiState.update { it.copy(
                    enseignantNom    = enseignant?.nomAffiche ?: "Enseignant",
                    unitesDisponibles = unites,
                    eleves = utilisateurs.map { u ->
                        EleveResume(
                            id             = u.id,
                            nomAffiche     = u.nomAffiche,
                            classe         = u.classe,
                            niveauGer      = u.niveau,
                            // TODO Sprint 4 (Mission C2) : calculer depuis ProgressionDao
                            unitesTerminees = 0,
                            scoreMoyen      = 0
                        )
                    },
                    enChargement = false
                )}
            }
        }
    }

    fun onChangerOnglet(onglet: OngletEnseignant) {
        _uiState.update { it.copy(ongletActif = onglet) }
    }
}
