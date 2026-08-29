package edu.project.dlearn.data.repository

import edu.project.dlearn.domain.model.QuestionPositionnement
import edu.project.dlearn.domain.model.ResultatPositionnement
import edu.project.dlearn.domain.repository.PositionnementRepository
import javax.inject.Inject

// Contenu fixe (10 questions) : pas besoin de table Room dediee pour un test qui ne change
// pas dynamiquement. A migrer en base si le corpus doit un jour etre edite par un enseignant.
class PositionnementRepositoryImpl @Inject constructor() : PositionnementRepository {

    override suspend fun getQuestions(): List<QuestionPositionnement> = listOf(
        QuestionPositionnement(1, "Que signifie « Guten Morgen » ?", listOf("Bonjour", "Bonsoir", "Au revoir", "Merci"), 0),
        QuestionPositionnement(2, "Comment dit-on « merci » en allemand ?", listOf("Bitte", "Danke", "Tschüss", "Ja"), 1),
        QuestionPositionnement(3, "Que signifie « Wie geht es dir? » ?", listOf("Comment tu tappelles ?", "Comment vas-tu ?", "Ou habites-tu ?", "Quel age as-tu ?"), 1),
        QuestionPositionnement(4, "Complétez : « Ich ___ Schüler. »", listOf("bin", "bist", "ist", "sind"), 0),
        QuestionPositionnement(5, "Que signifie « das Buch » ?", listOf("La table", "Le livre", "La porte", "La chaise"), 1),
        QuestionPositionnement(6, "Comment dit-on « au revoir » de façon familière ?", listOf("Hallo", "Bitte", "Tschüss", "Danke"), 2),
        QuestionPositionnement(7, "Quel article correspond à « Mädchen » (fille) ?", listOf("der", "die", "das", "den"), 2),
        QuestionPositionnement(8, "Que signifie « Ich verstehe nicht » ?", listOf("Je ne comprends pas", "Je ne sais pas", "Je nai pas le temps", "Je suis desole"), 0),
        QuestionPositionnement(9, "Complétez : « Wir ___ nach Hause. »", listOf("gehe", "gehst", "gehen", "geht"), 2),
        QuestionPositionnement(10, "Que signifie « die Freundschaft » ?", listOf("La famille", "Lamitie", "Le travail", "Le voyage"), 1)
    )

    override suspend fun enregistrerResultat(resultat: ResultatPositionnement) {
        // TODO: persister le resultat (Room) pour préremplir le niveau CECR du profil élève
        // et exclure ce test dune eventuelle repasse ulterieure.
    }
}
