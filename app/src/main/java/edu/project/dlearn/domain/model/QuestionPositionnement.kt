package edu.project.dlearn.domain.model

data class QuestionPositionnement(
    val id: Int,
    val enonce: String,
    val options: List<String>,
    val indexReponseCorrecte: Int
)

data class ResultatPositionnement(
    val score: Int,
    val total: Int,
    val niveauPropose: String // "A1" ou "A2"
)
