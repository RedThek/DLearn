package edu.project.dlearn.data.repository

import android.content.Context
import android.net.Uri
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.project.dlearn.data.local.room.ProductionEcriteDao
import edu.project.dlearn.data.local.room.ProductionEcriteEntity
import edu.project.dlearn.data.local.room.ProgressionDao
import edu.project.dlearn.data.local.room.SyncLogEntity
import edu.project.dlearn.data.local.room.SyncLogDao
import edu.project.dlearn.domain.model.ImportResume
import edu.project.dlearn.domain.repository.SyncRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val progressionDao: ProgressionDao,
    private val productionDao: ProductionEcriteDao,
    private val syncLogDao: SyncLogDao
) : SyncRepository {

    override suspend fun exporterDonnees(eleveId: Long): Result<String> = withContext(Dispatchers.IO) {
        try {
            val progressions = progressionDao.getProgressionByEleve(eleveId).first()
            val productions = productionDao.getProductionsByEleve(eleveId).first()
                .filter { it.statut == "SOUMIS" }

            val json = JSONObject().apply {
                put("versionFichierEchange", 1)
                put("eleveId", eleveId)
                put("dateExport", System.currentTimeMillis())
                put("progression", JSONArray().apply {
                    progressions.forEach { p ->
                        put(JSONObject().apply {
                            put("uniteId", p.uniteId)
                            put("statut", p.statut)
                            put("scoreMoyen", p.scoreMoyen?.toDouble() ?: JSONObject.NULL)
                        })
                    }
                })
                put("productionsEcrites", JSONArray().apply {
                    productions.forEach { pr ->
                        put(JSONObject().apply {
                            put("uniteId", pr.uniteId)
                            put("contenuTexte", pr.contenuTexte)
                            put("autoEvaluationJson", pr.autoEvaluationJson ?: JSONObject.NULL)
                        })
                    }
                })
            }

            val dossier = File(context.getExternalFilesDir(null), "exports").apply { mkdirs() }
            val fichier = File(dossier, "export_eleve_${eleveId}_${System.currentTimeMillis()}.json")
            fichier.writeText(json.toString(2))

            syncLogDao.insert(
                SyncLogEntity(
                    id = UUID.randomUUID().toString(),
                    appareilSource = Build.MODEL,
                    appareilCible = null,
                    canalTransfert = "FICHIER_MANUEL",
                    versionFichierEchange = "1",
                    dateSync = System.currentTimeMillis(),
                    statut = "SUCCES",
                    resumePayload = "${productions.size} production(s), ${progressions.size} progression(s)"
                )
            )

            Result.success(fichier.absolutePath)
        } catch (e: Exception) {
            syncLogDao.insert(
                SyncLogEntity(
                    id = UUID.randomUUID().toString(),
                    appareilSource = Build.MODEL,
                    appareilCible = null,
                    canalTransfert = "FICHIER_MANUEL",
                    versionFichierEchange = "1",
                    dateSync = System.currentTimeMillis(),
                    statut = "ECHEC",
                    resumePayload = e.message
                )
            )
            Result.failure(e)
        }
    }

    /**
     * Importe un fichier d'export élève (ADR-018 : fusion par timestamp au niveau enregistrement,
     * `dateExport` du lot servant de référence — limite connue tant que le format v1 ne porte pas
     * d'horodatage par enregistrement).
     */
    override suspend fun importerDonnees(uriString: String): Result<ImportResume> = withContext(Dispatchers.IO) {
        try {
            val uri = Uri.parse(uriString)
            val texte = context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
                ?: return@withContext Result.failure(IllegalArgumentException("Fichier illisible ou introuvable"))

            val racine = JSONObject(texte)
            val version = racine.optInt("versionFichierEchange", -1)
            if (version != 1) {
                return@withContext Result.failure(
                    IllegalStateException(
                        "Format de fichier v$version non supporté par cette version de l'application (v1 attendu)"
                    )
                )
            }

            val eleveId = racine.getLong("eleveId")
            val dateExport = racine.optLong("dateExport", System.currentTimeMillis())

            var progressionsMaj = 0
            var progressionsIgnorees = 0
            val progressionsJson = racine.getJSONArray("progression")
            for (i in 0 until progressionsJson.length()) {
                val p = progressionsJson.getJSONObject(i)
                val uniteId = p.getString("uniteId")
                val existante = progressionDao.getProgressionForUnite(eleveId, uniteId)
                if (existante == null || dateExport >= existante.dateMiseAJour) {
                    progressionDao.upsertProgression(
                        id = existante?.id ?: UUID.randomUUID().toString(),
                        eleveId = eleveId,
                        uniteId = uniteId,
                        statut = p.getString("statut"),
                        scoreMoyen = if (p.isNull("scoreMoyen")) null else p.getDouble("scoreMoyen").toFloat(),
                        dateMiseAJour = dateExport
                    )
                    progressionsMaj++
                } else {
                    progressionsIgnorees++
                }
            }

            var productionsMaj = 0
            var productionsIgnorees = 0
            val productionsJson = racine.getJSONArray("productionsEcrites")
            for (i in 0 until productionsJson.length()) {
                val pr = productionsJson.getJSONObject(i)
                val uniteId = pr.getString("uniteId")
                val existante = productionDao.getProductionForUnite(eleveId, uniteId)
                if (existante == null || dateExport >= existante.dateModification) {
                    productionDao.insertOrReplace(
                        ProductionEcriteEntity(
                            id = existante?.id ?: UUID.randomUUID().toString(),
                            eleveId = eleveId,
                            uniteId = uniteId,
                            contenuTexte = pr.getString("contenuTexte"),
                            dateModification = dateExport,
                            autoEvaluationJson = if (pr.isNull("autoEvaluationJson")) null else pr.getString("autoEvaluationJson"),
                            statut = "SOUMIS"
                        )
                    )
                    productionsMaj++
                } else {
                    productionsIgnorees++
                }
            }

            syncLogDao.insert(
                SyncLogEntity(
                    id = UUID.randomUUID().toString(),
                    appareilSource = "eleve-$eleveId",
                    appareilCible = Build.MODEL,
                    canalTransfert = "FICHIER_MANUEL",
                    versionFichierEchange = "1",
                    dateSync = System.currentTimeMillis(),
                    statut = "SUCCES",
                    resumePayload = "$progressionsMaj progression(s) maj / $productionsMaj production(s) maj"
                )
            )

            Result.success(
                ImportResume(
                    eleveId = eleveId,
                    progressionsMisesAJour = progressionsMaj,
                    progressionsIgnorees = progressionsIgnorees,
                    productionsMisesAJour = productionsMaj,
                    productionsIgnorees = productionsIgnorees
                )
            )
        } catch (e: Exception) {
            syncLogDao.insert(
                SyncLogEntity(
                    id = UUID.randomUUID().toString(),
                    appareilSource = "import",
                    appareilCible = Build.MODEL,
                    canalTransfert = "FICHIER_MANUEL",
                    versionFichierEchange = "1",
                    dateSync = System.currentTimeMillis(),
                    statut = "ECHEC",
                    resumePayload = e.message
                )
            )
            Result.failure(e)
        }
    }
}
