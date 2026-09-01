package edu.project.dlearn.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.project.dlearn.data.local.room.AppDatabase
import edu.project.dlearn.data.local.room.ApprentissageDao
import edu.project.dlearn.data.local.room.UtilisateurDao
import edu.project.dlearn.data.repository.ApprentissageRepositoryImpl
import edu.project.dlearn.domain.repository.ApprentissageRepository
import edu.project.dlearn.data.repository.AuthRepositoryImpl
import edu.project.dlearn.domain.repository.AuthRepository
import edu.project.dlearn.data.repository.PositionnementRepositoryImpl
import edu.project.dlearn.domain.repository.PositionnementRepository
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
            // TODO(dette-technique): fallbackToDestructiveMigration() à remplacer par des
            // migrations explicites avant la Mission D0 (distribution pilote).
            .fallbackToDestructiveMigration()
            .addCallback(SeedCallback)
            .build()

    @Provides
    fun provideApprentissageDao(database: AppDatabase): ApprentissageDao = database.apprentissageDao()

    @Provides
    fun provideUtilisateurDao(database: AppDatabase): UtilisateurDao = database.utilisateurDao()
}

// Seed 2 comptes de démo pour pouvoir tester l'écran Connexion dès le premier lancement,
// sans attendre le vrai mécanisme de provisionnement enseignant -> élèves (import BYOD, ADR-004).
// Identifiants : eleve.2451 / eleve1234   et   enseignant.100 / enseignant1234
// Hash SHA-256 pré-calculés (voir AuthRepositoryImpl.hacher pour l'algo utilisé).
private object SeedCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL(
            """
            INSERT INTO utilisateur (identifiant, motDePasseHash, nomAffiche, role, classe)
            VALUES ('eleve.2451', '0d62b61ff9b60f8082d22dae0d0a7f7330b7729d323f8401d723511e2e7ca7e8', 'Aïcha N.', 'ELEVE', 'Classe de 3e')
            """.trimIndent()
        )
        db.execSQL(
            """
            INSERT INTO utilisateur (identifiant, motDePasseHash, nomAffiche, role, classe)
            VALUES ('enseignant.100', '34db80eb303963f9ba1ead432c84ffb0f5226d84ea65cb4345930e7b6100eba0', 'M. Fotso', 'ENSEIGNANT', NULL)
            """.trimIndent()
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindApprentissageRepository(
        impl: ApprentissageRepositoryImpl
    ): ApprentissageRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindPositionnementRepository(
        impl: PositionnementRepositoryImpl
    ): PositionnementRepository

    // TODO au fur et à mesure : bindSuiviRepository, bindProfilRepository, bindEcritureRepository
    // dès que leurs interfaces domain + implementations data seront écrites (même patron que ci-dessus).
}
