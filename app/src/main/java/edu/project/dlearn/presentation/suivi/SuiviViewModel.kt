package edu.project.dlearn.presentation.suivi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.core.AppConstants
import edu.project.dlearn.domain.model.ProgressionStats
import edu.project.dlearn.domain.usecase.GetProgressionStatsUseCase
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SuiviViewModel @Inject constructor(
    private val getProgressionStats: GetProgressionStatsUseCase,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase
) : ViewModel() {

    // TODO Sprint 4 : migrer vers ObserverUtilisateurConnecteUseCase pour réactivité complète.
    // Pour Sprint 3 : lecture unique au démarrage, suffisante pour l'affichage mono-session.
    val stats: StateFlow<ProgressionStats> = flow {
        val eleveId = getUtilisateurConnecte()?.id ?: AppConstants.ELEVE_DEMO_ID
        emitAll(getProgressionStats(eleveId))
    }
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProgressionStats()
        )
}
