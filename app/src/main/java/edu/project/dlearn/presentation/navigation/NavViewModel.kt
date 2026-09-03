package edu.project.dlearn.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _destinationInitiale = MutableStateFlow<String?>(null)
    val destinationInitiale = _destinationInitiale.asStateFlow()

    private val _utilisateurLogue = MutableStateFlow<Utilisateur?>(null)
    val utilisateurLogue = _utilisateurLogue.asStateFlow()

    init {
        determinerDestination()
    }

    fun rafraichirSession() {
        determinerDestination()
    }

    private fun determinerDestination() {
        viewModelScope.launch {
            val connecte = authRepository.utilisateurConnecte()
            if (connecte != null) {
                _utilisateurLogue.value = connecte
                _destinationInitiale.value = Route.MAIN
                return@launch
            }

            val profils = authRepository.recupererProfilsLocaux()
            when {
                profils.isEmpty() -> _destinationInitiale.value = Route.CONNEXION
                profils.size == 1 -> {
                    val unique = profils.first()
                    authRepository.connecterAuto(unique)
                    _utilisateurLogue.value = unique
                    _destinationInitiale.value = Route.MAIN
                }
                else -> _destinationInitiale.value = Route.SELECTION_PROFIL
            }
        }
    }
}
