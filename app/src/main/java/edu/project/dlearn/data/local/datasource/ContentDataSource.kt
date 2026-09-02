package edu.project.dlearn.data.local.datasource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.project.dlearn.data.local.room.ContenuDao
import edu.project.dlearn.data.local.room.ExerciceEntity
import edu.project.dlearn.data.local.room.ExtraitLitteraireEntity
import edu.project.dlearn.data.local.room.GlossaireEntreeEntity
import edu.project.dlearn.data.local.room.OptionExerciceEntity
import edu.project.dlearn.data.local.room.UniteApprentissageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

/**
 * Charge le contenu pédagogique depuis assets/content/seed_v1.json
 * et le persiste dans Room au premier lancement (NFR-03 — offline dès le départ).
 *
 * Idempotent : vérifie si des unités existent déjà avant d'insérer.
 * Ne nécessite aucune connexion réseau (ADR-002).
 */
class ContentDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val contenuDao: ContenuDao
) {
    suspend fun peupler() = withContext(Dispatchers.IO) {
        if (contenuDao.countUnites() > 0) return@withContext // déjà peuplé

        val json = context.assets
            .open("content/seed_v1.json")
            .bufferedReader()
            .use { it.readText() }

        val root = JSONObject(json)

        // Unités
        val unites = root.getJSONArray("unites")
        contenuDao.insertUnites((0 until unites.length()).map { i ->
            val u = unites.getJSONObject(i)
            UniteApprentissageEntity(
                id                    = u.getString("id"),
                niveauGer             = u.getString("niveauGer"),
                chapitreCurriculum    = u.getString("chapitreCurriculum"),
                titre                 = u.getString("titre"),
                objectifsApprentissage= u.getString("objectifsApprentissage"),
                ordreAffichage        = u.getInt("ordreAffichage")
            )
        })

        // Extraits
        val extraits = root.getJSONArray("extraits")
        contenuDao.insertExtraits((0 until extraits.length()).map { i ->
            val e = extraits.getJSONObject(i)
            ExtraitLitteraireEntity(
                id            = e.getString("id"),
                uniteId       = e.getString("uniteId"),
                texteAllemand = e.getString("texteAllemand"),
                auteur        = e.optString("auteur").ifBlank { null },
                source        = e.optString("source").ifBlank { null },
                statutDroits  = e.getString("statutDroits")
            )
        })

        // Glossaire
        val glossaire = root.getJSONArray("glossaire")
        contenuDao.insertGlossaire((0 until glossaire.length()).map { i ->
            val g = glossaire.getJSONObject(i)
            GlossaireEntreeEntity(
                id           = g.getString("id"),
                extraitId    = g.getString("extraitId"),
                motAllemand  = g.getString("motAllemand"),
                traductionFr = g.getString("traductionFr")
            )
        })

        // Exercices
        val exercices = root.getJSONArray("exercices")
        contenuDao.insertExercices((0 until exercices.length()).map { i ->
            val ex = exercices.getJSONObject(i)
            ExerciceEntity(
                id                  = ex.getString("id"),
                uniteId             = ex.getString("uniteId"),
                type                = ex.getString("type"),
                enonce              = ex.getString("enonce"),
                correctionAttendue  = if (ex.isNull("correctionAttendue")) null
                                      else ex.getString("correctionAttendue")
            )
        })

        // Options QCM
        val options = root.getJSONArray("options")
        contenuDao.insertOptions((0 until options.length()).map { i ->
            val o = options.getJSONObject(i)
            OptionExerciceEntity(
                id          = o.getString("id"),
                exerciceId  = o.getString("exerciceId"),
                texteOption = o.getString("texteOption"),
                estCorrecte = o.getBoolean("estCorrecte")
            )
        })
    }
}
