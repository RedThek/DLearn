package edu.project.dlearn.domain.model

enum class CibleAssignation { ELEVE, CLASSE }

data class Assignation(
    val id: String,
    val enseignantId: Long,
    val cibleType: CibleAssignation,
    val cibleId: String,
    val uniteId: String,
    val dateAssignation: Long
)
