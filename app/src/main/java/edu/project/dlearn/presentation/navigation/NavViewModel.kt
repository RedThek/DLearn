package edu.project.dlearn.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        determinerDestination()
    }

    private fun determinerDestination() {
        viewModelScope.launch {
            val connecte = authRepository.utilisateurConnecte()
            if (connecte != null) {
                _destinationInitiale.value = NavRoute.MAIN
                return@launch
            }

            val profils = authRepository.recupererProfilsLocaux()
            when {
                profils.isEmpty() -> _destinationInitiale.value = NavRoute.CONNEXION
                profils.size == 1 -> {
                    authRepository.connecterAuto(profils.first())
                    _destinationInitiale.value = NavRoute.MAIN
                }
                else -> _destinationInitiale.value = NavRoute.SELECTION_PROFIL
            }
        }
    }
}
