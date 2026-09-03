package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "glossaire_entree")
data class GlossaireEntreeEntity(
    @PrimaryKey val id: String,
    val extraitId: String,
    val motAllemand: String,
    val traductionFr: String
)
