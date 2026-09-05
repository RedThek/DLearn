package edu.project.dlearn.presentation.ecriture

import androidx.lifecycle.SavedStateHandle
import edu.project.dlearn.core.AppConstants
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import edu.project.dlearn.domain.usecase.GetOrCreateBrouillonUseCase
import edu.project.dlearn.domain.usecase.GetUniteByIdUseCase
import edu.project.dlearn.domain.usecase.GetUnitesParNiveauUseCase
import edu.project.dlearn.domain.usecase.SauvegarderBrouillonUseCase
import edu.project.dlearn.domain.usecase.SoumettreProductionUseCase
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
    private val getUnitesParNiveau: GetUnitesParNiveauUseCase,
    private val getUniteById: GetUniteByIdUseCase,
    private val getOrCreateBrouillon: GetOrCreateBrouillonUseCase,
    private val sauvegarderBrouillon: SauvegarderBrouillonUseCase,
    private val soumettreProduction: SoumettreProductionUseCase,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /** Non null si on arrive depuis Apprentissage (bouton "Rédiger") ; null depuis l'onglet Écriture direct. */
    private val uniteIdArgument: String? = savedStateHandle["uniteId"]

    private val _uiState = MutableStateFlow(EcritureUiState())
    val uiState: StateFlow<EcritureUiState> = _uiState.asStateFlow()

    private var jobSauvegarde: Job? = null

    init { chargerUnite() }

    /**
     * Résolution de l'unité à afficher (correctif B-29) :
     * 1. Si un uniteId a été passé en navigation (depuis Apprentissage) → on l'utilise directement.
     * 2. Sinon, on cherche la première unité correspondant au niveau GeR réel de l'élève connecté.
     * 3. En dernier recours (aucune unité pour ce niveau, ou élève inconnu) → première unité du catalogue.
     */
    private fun chargerUnite() {
        viewModelScope.launch {
            val utilisateur = getUtilisateurConnecte()
            val eleveId = utilisateur?.id ?: AppConstants.ELEVE_DEMO_ID

            val unite = when {
                uniteIdArgument != null -> getUniteById(uniteIdArgument)
                utilisateur?.niveau != null ->
                    getUnitesParNiveau(utilisateur.niveau).first().firstOrNull()
                        ?: getAllUnites().first().firstOrNull()
                else -> getAllUnites().first().firstOrNull()
            }

            if (unite == null) {
                _uiState.update { it.copy(enChargement = false) }
                return@launch
            }

            val production = getOrCreateBrouillon(eleveId, unite.id)
            _uiState.update { it.copy(
                unite        = unite,
                production   = production,
                texteEnCours = production.contenuTexte,
                enChargement = false
            )}
        }
    }

    fun onTexteChange(nouveau: String) {
        _uiState.update { it.copy(texteEnCours = nouveau) }
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
                    "longueur"    -> ae.copy(longueurRespectee = valeur)
                    "coherence"   -> ae.copy(coherenceAvecConsigne = valeur)
                    "vocabulaire" -> ae.copy(vocabulaireNiveauGer = valeur)
                    else          -> ae
                }
            )
        }
    }

    fun onSoumettre() {
        viewModelScope.launch {
            val etat = _uiState.value
            val production = etat.production ?: return@launch
            val ae = etat.autoEvaluation
            val json = """{"longueur":${ae.longueurRespectee},"coherence":${ae.coherenceAvecConsigne},"vocabulaire":${ae.vocabulaireNiveauGer}}"""
            soumettreProduction(production.id, etat.texteEnCours, json)
            _uiState.update { it.copy(soumis = true) }
        }
    }
}
