package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.datasource.SessionManager
import edu.project.dlearn.data.local.room.UtilisateurDao
import edu.project.dlearn.domain.model.QuestionPositionnement
import edu.project.dlearn.domain.model.ResultatPositionnement
import edu.project.dlearn.domain.repository.PositionnementRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Contenu fixe (10 questions) : pas besoin de table Room dédiée.
 * enregistrerResultat() persiste le niveauPropose dans le profil de l'élève connecté (D-04).
 */
class PositionnementRepositoryImpl @Inject constructor(
    private val dao: UtilisateurDao,
    private val sessionManager: SessionManager
) : PositionnementRepository {

    override suspend fun getQuestions(): List<QuestionPositionnement> = listOf(
        QuestionPositionnement(1, "Que signifie « Guten Morgen » ?",
            listOf("Bonjour", "Bonsoir", "Au revoir", "Merci"), 0),
        QuestionPositionnement(2, "Comment dit-on « merci » en allemand ?",
            listOf("Bitte", "Danke", "Tschüss", "Ja"), 1),
        QuestionPositionnement(3, "Que signifie « Wie geht es dir? » ?",
            listOf("Comment tu t'appelles ?", "Comment vas-tu ?", "Où habites-tu ?", "Quel âge as-tu ?"), 1),
        QuestionPositionnement(4, "Complétez : « Ich ___ Schüler. »",
            listOf("bin", "bist", "ist", "sind"), 0),
        QuestionPositionnement(5, "Que signifie « das Buch » ?",
            listOf("La table", "Le livre", "La porte", "La chaise"), 1),
        QuestionPositionnement(6, "Comment dit-on « au revoir » de façon familière ?",
            listOf("Hallo", "Bitte", "Tschüss", "Danke"), 2),
        QuestionPositionnement(7, "Quel article correspond à « Mädchen » (fille) ?",
            listOf("der", "die", "das", "den"), 2),
        QuestionPositionnement(8, "Que signifie « Ich verstehe nicht » ?",
            listOf("Je ne comprends pas", "Je ne sais pas", "Je n'ai pas le temps", "Je suis désolé"), 0),
        QuestionPositionnement(9, "Complétez : « Wir ___ nach Hause. »",
            listOf("gehe", "gehst", "gehen", "geht"), 2),
        QuestionPositionnement(10, "Que signifie « die Freundschaft » ?",
            listOf("La famille", "L'amitié", "Le travail", "Le voyage"), 1)
    )

    /**
     * Persiste le niveau proposé dans le profil Room de l'élève connecté.
     * Si aucune session active, l'opération est silencieusement ignorée.
     */
    override suspend fun enregistrerResultat(resultat: ResultatPositionnement) {
        val userId = sessionManager.utilisateurIdFlow.first() ?: return
        dao.updateNiveau(userId, resultat.niveauPropose)
    }
}
