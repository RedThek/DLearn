package edu.project.dlearn.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Version 5 : ajout table `assignation` (FR-26) et colonne `statut` sur `production_ecrite` (correctif B-21).
 * Migration 4→5 testée — voir ADR-017 : à partir de cette version, toute migration DOIT être explicite et testée.
 *
 * TODO(dette-technique, priorité: avant-pilote D0) :
 * Supprimer fallbackToDestructiveMigration() et implémenter Migration(2,3) avant D0 — ADR-017 accepte ce
 * report jusqu'à la version 4 uniquement ; aucune exception au-delà.
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
        AssignationEntity::class,
    ],
    version = 5,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apprentissageDao(): ApprentissageDao
    abstract fun utilisateurDao(): UtilisateurDao
    abstract fun contenuDao(): ContenuDao
    abstract fun progressionDao(): ProgressionDao
    abstract fun productionEcriteDao(): ProductionEcriteDao
    abstract fun assignationDao(): AssignationDao
    abstract fun syncLogDao(): SyncLogDao

    companion object {
        /** ADR-015 : ajout du champ isValidated dans unite_apprentissage. */
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE unite_apprentissage ADD COLUMN isValidated INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

        /** ADR-017 : première migration sous la nouvelle politique — testée par MigrationTest.kt. */
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `assignation` (
                        `id` TEXT NOT NULL,
                        `enseignantId` INTEGER NOT NULL,
                        `cibleType` TEXT NOT NULL,
                        `cibleId` TEXT NOT NULL,
                        `uniteId` TEXT NOT NULL,
                        `dateAssignation` INTEGER NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent()
                )
                database.execSQL(
                    "ALTER TABLE production_ecrite ADD COLUMN statut TEXT NOT NULL DEFAULT 'BROUILLON'"
                )
            }
        }
    }
}
