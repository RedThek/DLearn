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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/*
data class ProfilUiState(
    val nomEleve: String = "Eleve",
    val niveauCECR: String = "A1",
    val ttsActif: Boolean = true,
    val themeSombre: Boolean = false
)

// TODO: persister ces preferences via DataStore (androidx.datastore.preferences),
// compatible offline-first et plus adapte que Room pour des cles/valeurs simples.
@HiltViewModel
class ProfilViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ProfilUiState())
    val uiState: StateFlow<ProfilUiState> = _uiState.asStateFlow()

    fun onToggleTts(actif: Boolean) = _uiState.update { it.copy(ttsActif = actif) }
    fun onToggleThemeSombre(actif: Boolean) = _uiState.update { it.copy(themeSombre = actif) }
} */

sealed interface ProfilEvenement {
    data object Deconnecte : ProfilEvenement
}

// TODO: remplacer les valeurs codées en dur par une lecture du profil élève réel
// (utilisateur connecté via AuthRepository.utilisateurConnecte() + table de badges dédiée).
@HiltViewModel
class ProfilViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfilUiState(
            nomComplet = "Aïcha N.",
            classe = "Classe de 3e",
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
