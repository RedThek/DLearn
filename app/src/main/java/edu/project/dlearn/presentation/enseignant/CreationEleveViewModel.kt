package edu.project.dlearn.presentation.enseignant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.usecase.CreerEleveUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CreationEleveEvenement {
    data class Cree(val utilisateur: Utilisateur) : CreationEleveEvenement
}

data class CreationEleveUiState(
    val enChargement: Boolean = false,
    val messageErreur: String? = null
)

@HiltViewModel
class CreationEleveViewModel @Inject constructor(
    private val creerEleve: CreerEleveUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreationEleveUiState())
    val uiState: StateFlow<CreationEleveUiState> = _uiState.asStateFlow()

    private val _evenements = MutableSharedFlow<CreationEleveEvenement>()
    val evenements: SharedFlow<CreationEleveEvenement> = _evenements

    fun onCreerEleve(nomComplet: String, classe: String, niveau: String) {
        if (nomComplet.isBlank() || classe.isBlank()) {
            _uiState.update { it.copy(messageErreur = "Le nom et la classe sont obligatoires.") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(enChargement = true, messageErreur = null) }
            val utilisateur = creerEleve(nomComplet, classe, niveau)
            _uiState.update { it.copy(enChargement = false) }
            _evenements.emit(CreationEleveEvenement.Cree(utilisateur))
        }
    }
}
