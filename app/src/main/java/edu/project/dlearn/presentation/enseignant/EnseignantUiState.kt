package edu.project.dlearn.presentation.enseignant

import edu.project.dlearn.domain.model.UniteApprentissage

data class EleveResume(
    val id: Long,
    val nomAffiche: String,
    val classe: String?,
    val niveauGer: String?,
    val unitesTerminees: Int,
    val scoreMoyen: Int
)

data class ProductionACorriger(
    val productionId: String,
    val eleveNom: String,
    val uniteTitre: String,
    val extrait: String,
    val dateModification: Long
)

data class ImportUiResume(
    val progressionsMisesAJour: Int,
    val productionsMisesAJour: Int,
    val ignorees: Int
)

data class EnseignantUiState(
    val enseignantId: Long = 0L,
    val enseignantNom: String = "",
    val eleves: List<EleveResume> = emptyList(),
    val unitesDisponibles: List<UniteApprentissage> = emptyList(),
    val productionsACorriger: List<ProductionACorriger> = emptyList(),
    val enChargement: Boolean = true,
    val ongletActif: OngletEnseignant = OngletEnseignant.CLASSE
)

enum class OngletEnseignant { CLASSE, CONTENUS, CORRECTIONS }
