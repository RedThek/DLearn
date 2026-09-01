package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.ApprentissageDao
import edu.project.dlearn.data.local.room.ExerciceEntity
import edu.project.dlearn.data.local.room.VocabEntity
import edu.project.dlearn.domain.model.ExerciceTexteATrous
import edu.project.dlearn.domain.model.Vocabulaire
import edu.project.dlearn.domain.repository.ApprentissageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ApprentissageRepositoryImpl @Inject constructor(
    private val dao: ApprentissageDao
) : ApprentissageRepository {

    override fun getFlashcardsDues(niveau: String): Flow<List<Vocabulaire>> =
        dao.getFlashcardsDues(niveau).map { list -> list.map { it.toDomain() } }

    override fun getExercicesTexteATrous(niveau: String): Flow<List<ExerciceTexteATrous>> =
        dao.getExercices(niveau).map { list -> list.map { it.toDomain() } }

    override suspend fun enregistrerResultatFlashcard(vocabulaireId: Long, connu: Boolean) {
        val vocab = dao.getVocabById(vocabulaireId) ?: return
        // Algorithme simplifie inspire de SM-2 : a affiner en Phase 3 avec FSRS (cf. ADR dedie).
        val nouveauFacteur = if (connu) (vocab.facteurDifficulte + 0.1f)
            else (vocab.facteurDifficulte - 0.3f).coerceAtLeast(1.3f)
        val intervalleJours = if (connu) (nouveauFacteur * 2).toLong() else 1L
        val prochainRappel = System.currentTimeMillis() + intervalleJours * 24 * 60 * 60 * 1000

        dao.updateVocab(vocab.copy(facteurDifficulte = nouveauFacteur, prochainRappel = prochainRappel))
    }

    override suspend fun enregistrerResultatExercice(exerciceId: Long, reponseDonnee: String, estCorrecte: Boolean) {
        // TODO: persister l historique de reponses (table dediee) pour alimenter l ecran Suivi.
    }

    private fun VocabEntity.toDomain() = Vocabulaire(
        id = id,
        motAllemand = motAllemand,
        motFrancais = motFrancais,
        niveauCECR = niveauCECR,
        exemplePhrase = exemplePhrase,
        prochainRappel = prochainRappel,
        facteurDifficulte = facteurDifficulte
    )

    private fun ExerciceEntity.toDomain() = ExerciceTexteATrous(
        id = id,
        phraseAvecTrou = phraseAvecTrou,
        reponseCorrecte = reponseCorrecte,
        niveauCECR = niveauCECR,
        indice = indice
    )
}
