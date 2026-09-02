package edu.project.dlearn.presentation.ecriture

import edu.project.dlearn.domain.model.ProductionEcrite
import edu.project.dlearn.domain.model.UniteApprentissage

data class AutoEvaluation(
    val longueurRespectee: Boolean? = null,
    val coherenceAvecConsigne: Boolean? = null,
    val vocabulaireNiveauGer: Boolean? = null
)

data class EcritureUiState(
    val unite: UniteApprentissage? = null,
    val production: ProductionEcrite? = null,
    val texteEnCours: String = "",
    val autoEvaluation: AutoEvaluation = AutoEvaluation(),
    val afficherAutoEvaluation: Boolean = false,
    val soumis: Boolean = false,
    val enChargement: Boolean = true,
    val sauvegardeBrouillonEnCours: Boolean = false
)
