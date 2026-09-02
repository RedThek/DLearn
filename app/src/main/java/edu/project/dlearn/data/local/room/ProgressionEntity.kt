package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// statut : "NON_COMMENCE" | "EN_COURS" | "TERMINE"
// Index unique (eleveId, uniteId) : un seul enregistrement de progression par élève/unité
@Entity(
    tableName = "progression",
    indices = [Index(value = ["eleveId", "uniteId"], unique = true)]
)
data class ProgressionEntity(
    @PrimaryKey val id: String,
    val eleveId: Long,           // référence à UtilisateurEntity.id
    val uniteId: String,         // référence à UniteApprentissageEntity.id
    val statut: String = "NON_COMMENCE",
    val scoreMoyen: Float? = null,
    val dateMiseAJour: Long = System.currentTimeMillis()
)
