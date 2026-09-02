package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// type : "QCM" | "TEXTE_A_TROUS" | "VRAI_FAUX" | "PRODUCTION_GUIDEE"
// correctionAttendue : null pour QCM (voir OptionExerciceEntity) et PRODUCTION_GUIDEE
//                      "stehe|auf" pour TEXTE_A_TROUS (séparateur | pour les trous multiples)
//                      "VRAI" | "FAUX" pour VRAI_FAUX
@Entity(tableName = "exercice")
data class ExerciceEntity(
    @PrimaryKey val id: String,
    val uniteId: String,
    val type: String,
    val enonce: String,
    val correctionAttendue: String?
)
