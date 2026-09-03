package edu.project.dlearn.presentation.profil

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.repository.AuthRepository
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

sealed interface ProfilEvenement {
    data object Deconnecte : ProfilEvenement
}

@HiltViewModel
class ProfilViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfilUiState(
            nomComplet = "Élève",
            classe = "—",
            niveauActuel = "A1",
            niveauCible = "A2",
            progressionVersCible = 0.72f,
            langueInterface = "Français",
            modeHorsLigneActif = true,
            notificationsActives = true,
            derniereSynchro = "Il y a 2h",
            badges = listOf(
                Badge("streak", Icons.Filled.LocalFireDepartment, deverrouille = true),
                Badge("lecture", Icons.AutoMirrored.Filled.MenuBook, deverrouille = true),
                Badge("ecriture", Icons.Filled.Edit, deverrouille = false),
                Badge("excellence", Icons.Filled.Star, deverrouille = false)
            )
        )
    )
    val uiState: StateFlow<ProfilUiState> = _uiState.asStateFlow()

    private val _evenements = MutableSharedFlow<ProfilEvenement>()
    val evenements: SharedFlow<ProfilEvenement> = _evenements

    init {
        chargerProfil()
    }

    private fun chargerProfil() {
        viewModelScope.launch {
            val utilisateur = getUtilisateurConnecte()
            if (utilisateur != null) {
                _uiState.update { it.copy(
                    nomComplet = utilisateur.nomAffiche,
                    classe     = utilisateur.classe ?: "—",
                    niveauActuel = utilisateur.niveau ?: "A1"
                )}
            }
            // Badges et autres données mockées conservées jusqu'à Mission B1
        }
    }

    fun onSynchroniserMaintenant() {
        if (_uiState.value.synchronisationEnCours) return
        viewModelScope.launch {
            _uiState.update { it.copy(synchronisationEnCours = true) }
            // TODO: déclencher le vrai flux d'export/import fichier BYOD (ADR-004) ici.
            delay(1200.milliseconds)
            _uiState.update { it.copy(synchronisationEnCours = false, derniereSynchro = "À l'instant") }
        }
    }

    fun onDeconnexion() {
        viewModelScope.launch {
            authRepository.deconnecter()
            _evenements.emit(ProfilEvenement.Deconnecte)
        }
    }
}
