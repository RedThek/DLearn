package edu.project.dlearn.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// canalTransfert : "NEARBY_SHARE" | "BLUETOOTH" | "FICHIER_MANUEL"
// statut : "SUCCES" | "ECHEC" | "PARTIEL"
@Entity(tableName = "sync_log")
data class SyncLogEntity(
    @PrimaryKey val id: String,
    val appareilSource: String,
    val appareilCible: String?,
    val canalTransfert: String,
    val versionFichierEchange: String,
    val dateSync: Long = System.currentTimeMillis(),
    val statut: String,
    val resumePayload: String?
)
