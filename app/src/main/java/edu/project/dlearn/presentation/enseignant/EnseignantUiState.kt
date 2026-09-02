package edu.project.dlearn.presentation.enseignant

import edu.project.dlearn.domain.model.UniteApprentissage

data class EleveResume(
    val id: Long,
    val nomAffiche: String,
    val classe: String?,
    val niveauGer: String?,
    val unitesTerminees: Int,
    val scoreMoyen: Int   // en %
)

data class EnseignantUiState(
    val enseignantNom: String = "",
    val eleves: List<EleveResume> = emptyList(),
    val unitesDisponibles: List<UniteApprentissage> = emptyList(),
    val enChargement: Boolean = true,
    val ongletActif: OngletEnseignant = OngletEnseignant.CLASSE
)

enum class OngletEnseignant { CLASSE, CONTENUS, CORRECTIONS }
