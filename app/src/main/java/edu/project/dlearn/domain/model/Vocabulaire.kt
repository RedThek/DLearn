package edu.project.dlearn.domain.model

data class Vocabulaire(
    val id: Long,
    val motAllemand: String,
    val motFrancais: String,
    val niveauCECR: String, // ex: "A1", "A2", "B1"
    val exemplePhrase: String? = null,
    // Champs utilises pour la repetition espacee (compatible FSRS, cf. Phase 3) :
    val prochainRappel: Long = System.currentTimeMillis(),
    val facteurDifficulte: Float = 2.5f
)

data class ExerciceTexteATrous(
    val id: Long,
    val phraseAvecTrou: String, // ex: "Ich ___ nach Hause." (verbe manquant)
    val reponseCorrecte: String,
    val niveauCECR: String,
    val indice: String? = null
)
