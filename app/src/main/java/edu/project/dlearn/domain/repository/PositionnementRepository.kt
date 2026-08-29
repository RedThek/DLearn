package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.QuestionPositionnement
import edu.project.dlearn.domain.model.ResultatPositionnement

interface PositionnementRepository {
    suspend fun getQuestions(): List<QuestionPositionnement>
    suspend fun enregistrerResultat(resultat: ResultatPositionnement)
}
