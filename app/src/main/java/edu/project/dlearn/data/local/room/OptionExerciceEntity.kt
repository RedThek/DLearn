package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "option_exercice")
data class OptionExerciceEntity(
    @PrimaryKey val id: String,
    val exerciceId: String,
    val texteOption: String,
    val estCorrecte: Boolean
)
