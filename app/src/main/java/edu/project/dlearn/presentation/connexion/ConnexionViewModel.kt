package edu.project.dlearn.presentation.connexion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.repository.AuthRepository
import edu.project.dlearn.domain.repository.ResultatConnexion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Emis une seule fois vers lecran (pas garde dans le StateFlow) pour piloter la navigation.
sealed interface ConnexionEvenement {
    data class ConnexionReussie(val role: Role) : ConnexionEvenement
}

@HiltViewModel
class ConnexionViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConnexionUiState())
    val uiState: StateFlow<ConnexionUiState> = _uiState.asStateFlow()

    private val _evenements = MutableSharedFlow<ConnexionEvenement>()
    val evenements: SharedFlow<ConnexionEvenement> = _evenements

    fun onChangerRole(role: Role) = _uiState.update { it.copy(roleSelectionne = role, messageErreur = null) }
    fun onChangerIdentifiant(valeur: String) = _uiState.update { it.copy(identifiant = valeur, messageErreur = null) }
    fun onChangerMotDePasse(valeur: String) = _uiState.update { it.copy(motDePasse = valeur, messageErreur = null) }
    fun onToggleVisibiliteMotDePasse() = _uiState.update { it.copy(motDePasseVisible = !it.motDePasseVisible) }

    fun onSeConnecter() {
        val etat = _uiState.value
        if (etat.identifiant.isBlank() || etat.motDePasse.isBlank()) {
            _uiState.update { it.copy(messageErreur = "Identifiant et mot de passe requis.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(enChargement = true, messageErreur = null) }

            when (val resultat = authRepository.connecter(etat.identifiant, etat.motDePasse, etat.roleSelectionne)) {
                is ResultatConnexion.Succes -> {
                    _uiState.update { it.copy(enChargement = false) }
                    _evenements.emit(ConnexionEvenement.ConnexionReussie(resultat.utilisateur.role))
                }
                ResultatConnexion.IdentifiantsInvalides -> {
                    _uiState.update {
                        it.copy(enChargement = false, messageErreur = "Identifiant ou mot de passe incorrect.")
                    }
                }
            }
        }
    }
}
