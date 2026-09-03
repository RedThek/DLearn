package edu.project.dlearn.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    /** Destination initiale résolue une seule fois au démarrage (routing intelligent). */
    private val _destinationInitiale = MutableStateFlow<String?>(null)
    val destinationInitiale: StateFlow<String?> = _destinationInitiale.asStateFlow()

    /**
     * Utilisateur actuellement en session — réactif.
     * Mis à jour automatiquement à chaque connexion/déconnexion via SessionManager (D-01).
     * Utilisé par NavGraph pour propager le rôle réel dans MainScreen.
     */
    val utilisateurConnecte: StateFlow<Utilisateur?> = authRepository
        .utilisateurConnecteFlow()
        .stateIn(
            scope   = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    init {
        determinerDestination()
    }

    private fun determinerDestination() {
        viewModelScope.launch {
            val connecte = authRepository.utilisateurConnecte()
            if (connecte != null) {
                _destinationInitiale.value = Route.MAIN
                return@launch
            }

            val profils = authRepository.recupererProfilsLocaux()
            when {
                profils.isEmpty() -> _destinationInitiale.value = Route.CONNEXION
                profils.size == 1 -> {
                    authRepository.connecterAuto(profils.first())
                    _destinationInitiale.value = Route.MAIN
                }
                else -> _destinationInitiale.value = Route.SELECTION_PROFIL
            }
        }
    }
}
