package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// statutDroits : "texte_original" | "domaine_public" | "autorisation_obtenue"
@Entity(tableName = "extrait_litteraire")
data class ExtraitLitteraireEntity(
    @PrimaryKey val id: String,
    val uniteId: String,
    val texteAllemand: String,
    val auteur: String?,
    val source: String?,
    val statutDroits: String
)
