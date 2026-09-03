package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabulaire")
data class VocabEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val motAllemand: String,
    val motFrancais: String,
    val niveauCECR: String,
    val exemplePhrase: String?,
    val prochainRappel: Long,
    val facteurDifficulte: Float
)
