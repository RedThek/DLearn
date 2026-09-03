package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// autoEvaluationJson : résultat JSON de la grille FR-17 (longueur, cohérence, vocabulaire)
// Format : {"longueur": true, "coherence": false, "vocabulaire": true}
@Entity(tableName = "production_ecrite")
data class ProductionEcriteEntity(
    @PrimaryKey val id: String,
    val eleveId: Long,
    val uniteId: String,
    val contenuTexte: String,
    val dateCreation: Long = System.currentTimeMillis(),
    val dateModification: Long = System.currentTimeMillis(),
    val autoEvaluationJson: String? = null
)
