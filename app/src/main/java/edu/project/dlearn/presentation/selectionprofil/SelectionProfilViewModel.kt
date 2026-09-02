package edu.project.dlearn.presentation.selectionprofil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SelectionProfilEvenement {
    data class ProfilSelectionne(val role: Role) : SelectionProfilEvenement
}

@HiltViewModel
class SelectionProfilViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val uiState: StateFlow<SelectionProfilUiState> = authRepository
        .getAllProfils()
        .map { profils ->
            if (profils.isEmpty()) SelectionProfilUiState.SansProfil
            else SelectionProfilUiState.AvecProfils(
                profils.map {
                    ProfilResume(
                        id         = it.id,
                        nomAffiche = it.nomAffiche,
                        role       = it.role,
                        classe     = it.classe
                    )
                }
            )
        }
        .stateIn(
            scope   = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SelectionProfilUiState.Chargement
        )

    private val _evenements = MutableSharedFlow<SelectionProfilEvenement>()
    val evenements: SharedFlow<SelectionProfilEvenement> = _evenements

    fun onSelectionnerProfil(profil: ProfilResume) {
        viewModelScope.launch {
            // Pas de vérification de mot de passe pour la sélection sur device partagé :
            // tous les comptes présents sur l'appareil sont déjà considérés comme autorisés.
            // TODO (Sprint A5) : si profil.codeAcces != null → afficher dialog PIN avant navigation.
            _evenements.emit(SelectionProfilEvenement.ProfilSelectionne(profil.role))
        }
    }
}
