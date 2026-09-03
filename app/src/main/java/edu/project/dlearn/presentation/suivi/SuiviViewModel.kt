package edu.project.dlearn.presentation.suivi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.model.ProgressionStats
import edu.project.dlearn.domain.usecase.GetProgressionStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// TODO Sprint 3 : remplacer ELEVE_ID_DEMO par la session DataStore réelle
private const val ELEVE_ID_DEMO = 1L

@HiltViewModel
class SuiviViewModel @Inject constructor(
    getProgressionStats: GetProgressionStatsUseCase
) : ViewModel() {

    val stats: StateFlow<ProgressionStats> = getProgressionStats(ELEVE_ID_DEMO)
        .stateIn(
            scope          = viewModelScope,
            started        = SharingStarted.WhileSubscribed(5_000),
            initialValue   = ProgressionStats()
        )
}
