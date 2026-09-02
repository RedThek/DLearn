package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reponse_eleve")
data class ReponseEleveEntity(
    @PrimaryKey val id: String,
    val eleveId: Long,
    val exerciceId: String,
    val reponseDonnee: String,
    val estCorrecte: Boolean,
    val dateReponse: Long = System.currentTimeMillis()
)
