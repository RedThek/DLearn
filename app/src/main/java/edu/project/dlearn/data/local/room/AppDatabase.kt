package edu.project.dlearn.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

// TODO(dette-technique, priorité: avant-pilote D0) : version=3 avec fallbackToDestructiveMigration()
// est un placeholder de développement. Implémenter Migration(2,3) avant distribution APK pilote
// pour préserver la progression élève entre deux versions de l'app.
// Voir 14-charte-versionnage-contenu.md et NFR-22.
@Database(
    entities = [
        // Existants
        VocabEntity::class,
        UtilisateurEntity::class,
        // Contenus pédagogiques (Mission A4 — Sprint 2)
        UniteApprentissageEntity::class,
        ExtraitLitteraireEntity::class,
        GlossaireEntreeEntity::class,
        ExerciceEntity::class,
        OptionExerciceEntity::class,
        // Usage élève (Mission A4 — Sprint 2)
        ProgressionEntity::class,
        ProductionEcriteEntity::class,
        ReponseEleveEntity::class,
        // Technique
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
}
