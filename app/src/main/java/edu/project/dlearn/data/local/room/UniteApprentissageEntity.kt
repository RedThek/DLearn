package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unite_apprentissage")
data class UniteApprentissageEntity(
    @PrimaryKey val id: String,
    val niveauGer: String,
    val chapitreCurriculum: String,
    val titre: String,
    val objectifsApprentissage: String,
    val ordreAffichage: Int,
    val isValidated: Boolean = false
)
