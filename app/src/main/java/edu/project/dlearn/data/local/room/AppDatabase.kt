package edu.project.dlearn.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

// TODO(dette-technique, priorité: avant-pilote) : version=2 avec fallbackToDestructiveMigration()
// est un placeholder de développement. Implémenter Migration(1,2) et Migration(2,3) etc.
// avant toute distribution d'APK aux élèves (Mission D0), pour préserver la progression
// déjà enregistrée lors de mises à jour de l'application (voir 14-charte-versionnage-contenu.md).
@Database(
    entities = [VocabEntity::class, ExerciceEntity::class, UtilisateurEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apprentissageDao(): ApprentissageDao
    abstract fun utilisateurDao(): UtilisateurDao
}
