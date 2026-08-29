package edu.project.dlearn.domain.model

data class ProgressionStats(
    val motsAppris: Int = 0,
    val streakJours: Int = 0,
    val tauxReussite: Int = 0,
    val competencesParNiveau: Map<String, Float> = emptyMap() // ex: "A1" -> 0.8f (80% maitrise)
)
