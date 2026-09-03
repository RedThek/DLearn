package edu.project.dlearn.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Version 4 : ajout colonne isValidated (ADR-015).
 * Migration 3→4 : ALTER TABLE — conserve toutes les données existantes.
 *
 * TODO(dette-technique, priorité: avant-pilote D0) :
 * Supprimer fallbackToDestructiveMigration() et implémenter Migration(2,3) avant D0.
 * Voir 14-charte-versionnage-contenu.md et NFR-22.
 */
@Database(
    entities = [
        VocabEntity::class,
        UtilisateurEntity::class,
        UniteApprentissageEntity::class,
        ExtraitLitteraireEntity::class,
        GlossaireEntreeEntity::class,
        ExerciceEntity::class,
        OptionExerciceEntity::class,
        ProgressionEntity::class,
        ProductionEcriteEntity::class,
        ReponseEleveEntity::class,
        SyncLogEntity::class,
    ],
    version = 4,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apprentissageDao(): ApprentissageDao
    abstract fun utilisateurDao(): UtilisateurDao
    abstract fun contenuDao(): ContenuDao
    abstract fun progressionDao(): ProgressionDao
    abstract fun productionEcriteDao(): ProductionEcriteDao

    companion object {
        /**
         * ADR-015 : ajout du champ isValidated dans unite_apprentissage.
         * DEFAULT 0 = false (tous les brouillons existants sont non validés).
         */
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE unite_apprentissage ADD COLUMN isValidated INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
    }
}
