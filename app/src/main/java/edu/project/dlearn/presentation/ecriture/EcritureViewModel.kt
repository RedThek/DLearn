package edu.project.dlearn.presentation.ecriture

import edu.project.dlearn.core.AppConstants
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import edu.project.dlearn.domain.usecase.GetOrCreateBrouillonUseCase
import edu.project.dlearn.domain.usecase.SauvegarderBrouillonUseCase
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val DEBOUNCE_SAUVEGARDE_MS = 1500L

@HiltViewModel
class EcritureViewModel @Inject constructor(
    private val getAllUnites: GetAllUnitesUseCase,
    private val getOrCreateBrouillon: GetOrCreateBrouillonUseCase,
    private val sauvegarderBrouillon: SauvegarderBrouillonUseCase,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EcritureUiState())
    val uiState: StateFlow<EcritureUiState> = _uiState.asStateFlow()

    private var jobSauvegarde: Job? = null

    init { chargerPremierUnite() }

    private fun chargerPremierUnite() {
        viewModelScope.launch {
            val eleveId = getUtilisateurConnecte()?.id ?: AppConstants.ELEVE_DEMO_ID
            val unites = getAllUnites().first()
            val premiere = unites.firstOrNull() ?: run {
                _uiState.update { it.copy(enChargement = false) }
                return@launch
            }
            val production = getOrCreateBrouillon(eleveId, premiere.id)
            _uiState.update { it.copy(
                unite       = premiere,
                production  = production,
                texteEnCours = production.contenuTexte,
                enChargement = false
            )}
        }
    }

    fun onTexteChange(nouveau: String) {
        _uiState.update { it.copy(texteEnCours = nouveau) }
        // Sauvegarde automatique avec debounce (FR-15)
        jobSauvegarde?.cancel()
        jobSauvegarde = viewModelScope.launch {
            delay(DEBOUNCE_SAUVEGARDE_MS.milliseconds)
            sauvegarderBrouillon(
                _uiState.value.production?.copy(contenuTexte = nouveau) ?: return@launch
            )
        }
    }

    fun onInserterCaractere(caractere: String) {
        val actuel = _uiState.value.texteEnCours
        _uiState.update { it.copy(texteEnCours = actuel + caractere) }
        onTexteChange(_uiState.value.texteEnCours)
    }

    fun onToggleAutoEvaluation() {
        _uiState.update { it.copy(afficherAutoEvaluation = !it.afficherAutoEvaluation) }
    }

    fun onAutoEvaluationChange(critere: String, valeur: Boolean) {
        _uiState.update { etat ->
            val ae = etat.autoEvaluation
            etat.copy(
                autoEvaluation = when (critere) {
                    "longueur"   -> ae.copy(longueurRespectee = valeur)
                    "coherence"  -> ae.copy(coherenceAvecConsigne = valeur)
                    "vocabulaire"-> ae.copy(vocabulaireNiveauGer = valeur)
                    else         -> ae
                }
            )
        }
    }

    fun onSoumettre() {
        viewModelScope.launch {
            val etat = _uiState.value
            val ae = etat.autoEvaluation
            val json = """{"longueur":${ae.longueurRespectee},"coherence":${ae.coherenceAvecConsigne},"vocabulaire":${ae.vocabulaireNiveauGer}}"""
            sauvegarderBrouillon(
                etat.production?.copy(
                    contenuTexte       = etat.texteEnCours,
                    autoEvaluationJson = json
                ) ?: return@launch
            )
            _uiState.update { it.copy(soumis = true) }
        }
    }
}
