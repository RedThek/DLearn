package edu.project.dlearn.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.project.dlearn.data.local.room.AppDatabase
import edu.project.dlearn.data.local.room.ApprentissageDao
import edu.project.dlearn.data.local.room.AssignationDao
import edu.project.dlearn.data.local.room.ContenuDao
import edu.project.dlearn.data.local.room.ProductionEcriteDao
import edu.project.dlearn.data.local.room.ProgressionDao
import edu.project.dlearn.data.local.room.SyncLogDao
import edu.project.dlearn.data.local.room.UtilisateurDao
import edu.project.dlearn.data.repository.ApprentissageRepositoryImpl
import edu.project.dlearn.domain.repository.ApprentissageRepository
import edu.project.dlearn.data.repository.AuthRepositoryImpl
import edu.project.dlearn.domain.repository.AuthRepository
import edu.project.dlearn.data.repository.ContenuRepositoryImpl
import edu.project.dlearn.domain.repository.ContenuRepository
import edu.project.dlearn.data.repository.EcritureRepositoryImpl
import edu.project.dlearn.domain.repository.EcritureRepository
import edu.project.dlearn.data.repository.PositionnementRepositoryImpl
import edu.project.dlearn.domain.repository.PositionnementRepository
import edu.project.dlearn.data.repository.ProgressionRepositoryImpl
import edu.project.dlearn.domain.repository.ProgressionRepository
import edu.project.dlearn.data.repository.ExerciceRepositoryImpl
import edu.project.dlearn.domain.repository.ExerciceRepository
import edu.project.dlearn.data.repository.AssignationRepositoryImpl
import edu.project.dlearn.domain.repository.AssignationRepository
import edu.project.dlearn.data.repository.SyncRepositoryImpl
import edu.project.dlearn.domain.repository.SyncRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "liteschreib.db")
            .fallbackToDestructiveMigration()
            .addMigrations(AppDatabase.MIGRATION_3_4, AppDatabase.MIGRATION_4_5)
            .addCallback(SeedCallback)
            .build()

    @Provides
    fun provideApprentissageDao(database: AppDatabase): ApprentissageDao = database.apprentissageDao()

    @Provides
    fun provideUtilisateurDao(database: AppDatabase): UtilisateurDao = database.utilisateurDao()

    @Provides
    fun provideContenuDao(database: AppDatabase): ContenuDao = database.contenuDao()

    @Provides
    fun provideProgressionDao(database: AppDatabase): ProgressionDao = database.progressionDao()

    @Provides
    fun provideProductionEcriteDao(database: AppDatabase): ProductionEcriteDao = database.productionEcriteDao()

    @Provides
    fun provideAssignationDao(database: AppDatabase): AssignationDao = database.assignationDao()

    @Provides
    fun provideSyncLogDao(database: AppDatabase): SyncLogDao = database.syncLogDao()
}

// Seed 2 comptes de démo pour pouvoir tester l'écran Connexion dès le premier lancement,
// sans attendre le vrai mécanisme de provisionnement enseignant -> élèves (import BYOD, ADR-004).
private object SeedCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL(
            """
            INSERT INTO utilisateur (identifiant, motDePasseHash, nomAffiche, role, classe, niveau)
            VALUES ('eleve.2451', '0d62b61ff9b60f8082d22dae0d0a7f7330b7729d323f8401d723511e2e7ca7e8', 'Aïcha N.', 'ELEVE', '3ème', 'A1')
            """.trimIndent()
        )
        db.execSQL(
            """
            INSERT INTO utilisateur (identifiant, motDePasseHash, nomAffiche, role, classe, niveau)
            VALUES ('enseignant.100', '34db80eb303963f9ba1ead432c84ffb0f5226d84ea65cb4345930e7b6100eba0', 'M. Fotso', 'ENSEIGNANT', NULL, NULL)
            """.trimIndent()
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindApprentissageRepository(impl: ApprentissageRepositoryImpl): ApprentissageRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindPositionnementRepository(impl: PositionnementRepositoryImpl): PositionnementRepository

    @Binds
    @Singleton
    abstract fun bindContenuRepository(impl: ContenuRepositoryImpl): ContenuRepository

    @Binds
    @Singleton
    abstract fun bindProgressionRepository(impl: ProgressionRepositoryImpl): ProgressionRepository

    @Binds
    @Singleton
    abstract fun bindEcritureRepository(impl: EcritureRepositoryImpl): EcritureRepository

    @Binds
    @Singleton
    abstract fun bindExerciceRepository(impl: ExerciceRepositoryImpl): ExerciceRepository

    @Binds
    @Singleton
    abstract fun bindAssignationRepository(impl: AssignationRepositoryImpl): AssignationRepository

    @Binds
    @Singleton
    abstract fun bindSyncRepository(impl: SyncRepositoryImpl): SyncRepository
}
