package edu.project.dlearn.presentation.suivi

import androidx.lifecycle.ViewModel
import edu.project.dlearn.domain.model.ProgressionStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// TODO: remplacer par un repository dedie qui agrege les resultats
// (table historique_reponses + vocabulaire) via une requete Room @Query avec agregation.
@HiltViewModel
class SuiviViewModel @Inject constructor() : ViewModel() {

    private val _stats = MutableStateFlow(
        ProgressionStats(
            motsAppris = 42,
            streakJours = 5,
            tauxReussite = 78,
            competencesParNiveau = mapOf("A1" to 0.9f, "A2" to 0.6f, "B1" to 0.2f)
        )
    )
    val stats: StateFlow<ProgressionStats> = _stats.asStateFlow()
}
