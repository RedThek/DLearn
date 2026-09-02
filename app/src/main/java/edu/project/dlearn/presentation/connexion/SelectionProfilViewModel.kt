package edu.project.dlearn.presentation.connexion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SelectionProfilEvenement {
    data class ProfilSelectionne(val role: Role) : SelectionProfilEvenement
}

@HiltViewModel
class SelectionProfilViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectionProfilUiState())
    val uiState: StateFlow<SelectionProfilUiState> = _uiState.asStateFlow()

    private val _evenements = MutableSharedFlow<SelectionProfilEvenement>()
    val evenements: SharedFlow<SelectionProfilEvenement> = _evenements

    init {
        chargerProfils()
    }

    private fun chargerProfils() {
        viewModelScope.launch {
            _uiState.update { it.copy(enChargement = true) }
            val profils = authRepository.recupererProfilsLocaux()
            _uiState.update { it.copy(profils = profils, enChargement = false) }
        }
    }

    fun onSelectionnerProfil(utilisateur: Utilisateur) {
        viewModelScope.launch {
            authRepository.connecterAuto(utilisateur)
            _evenements.emit(SelectionProfilEvenement.ProfilSelectionne(utilisateur.role))
        }
    }
}
