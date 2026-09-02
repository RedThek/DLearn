package edu.project.dlearn.domain.model

data class UniteApprentissage(
    val id: String,
    val niveauGer: String,
    val titre: String,
    val objectifsApprentissage: String,
    val ordreAffichage: Int
)

data class ExtraitAvecGlossaire(
    val id: String,
    val uniteId: String,
    val texteAllemand: String,
    val auteur: String?,
    val glossaire: List<EntreeGlossaire>
)

data class EntreeGlossaire(
    val motAllemand: String,
    val traductionFr: String
)

data class ProductionEcrite(
    val id: String,
    val eleveId: Long,
    val uniteId: String,
    val contenuTexte: String,
    val dateModification: Long,
    val autoEvaluationJson: String? = null
)

// StatutProgression et ProgressionStats sont déjà dans ProgressionStats.kt
// Ajouter StatutProgression :
enum class StatutProgression { NON_COMMENCE, EN_COURS, TERMINE }
