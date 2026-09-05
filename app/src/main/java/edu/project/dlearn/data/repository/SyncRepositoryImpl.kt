package edu.project.dlearn.data.repository

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.project.dlearn.data.local.room.ProductionEcriteDao
import edu.project.dlearn.data.local.room.ProgressionDao
import edu.project.dlearn.data.local.room.SyncLogEntity
import edu.project.dlearn.data.local.room.SyncLogDao
import edu.project.dlearn.domain.repository.SyncRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID
import javax.inject.Inject

/**
 * Format d'échange v1 (voir 14-charte-versionnage-contenu.md, section 4 — à compléter par l'agent).
 * Écrit dans le répertoire spécifique à l'application (getExternalFilesDir), lisible/partageable sans
 * permission de stockage supplémentaire sur Android 9+ (ADR-005).
 */
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
}
