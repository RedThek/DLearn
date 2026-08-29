package edu.project.dlearn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.project.dlearn.data.local.ApprentissageDao
import edu.project.dlearn.data.local.ExerciceEntity
import edu.project.dlearn.data.local.VocabEntity
import edu.project.dlearn.data.local.UtilisateurDao
import edu.project.dlearn.data.local.UtilisateurEntity

@Database(
    entities = [VocabEntity::class, ExerciceEntity::class, UtilisateurEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun apprentissageDao(): ApprentissageDao
  abstract fun utilisateurDao(): UtilisateurDao
}
