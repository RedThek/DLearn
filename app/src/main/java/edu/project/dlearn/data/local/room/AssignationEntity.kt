package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// cibleType : "ELEVE" | "CLASSE"
// cibleId : identifiant élève (Long en String) si ELEVE, valeur de UtilisateurEntity.classe si CLASSE
@Entity(tableName = "assignation")
data class AssignationEntity(
    @PrimaryKey val id: String,
    val enseignantId: Long,
    val cibleType: String,
    val cibleId: String,
    val uniteId: String,
    val dateAssignation: Long = System.currentTimeMillis()
)
