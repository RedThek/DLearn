package edu.project.dlearn.domain.model

/**
 * Familles d'exercices supportées (voir 11-schema-donnees-room.md, ExerciceEntity.type).
 * Les valeurs doivent rester alignées avec la colonne `type` de la table `exercice`
 * (String brut en base, mappé ici via TypeExercice.valueOf()).
 */
enum class TypeExercice { QCM, TEXTE_A_TROUS, VRAI_FAUX, PRODUCTION_GUIDEE }

data class OptionExercice(
    val id: String,
    val texte: String,
    val estCorrecte: Boolean
)

/**
 * Modèle de domaine générique couvrant les 4 familles d'exercices d'une unité
 * (contrairement à ExerciceTexteATrous dans Vocabulaire.kt, qui reste dédié au module
 * flashcards/répétition espacée — les deux pipelines restent volontairement séparés
 * ce sprint, voir bugs-pre-sprint3.md, note de conception).
 */
data class Exercice(
    val id: String,
    val uniteId: String,
    val type: TypeExercice,
    val enonce: String,
    val correctionAttendue: String?,
    val options: List<OptionExercice> = emptyList()
)
