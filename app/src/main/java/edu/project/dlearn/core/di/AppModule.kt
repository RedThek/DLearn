package edu.project.dlearn.core.di

/*
import android.content.Context
import androidx.room.Room
import com.ikii.liteschreib.core.data.local.AppDatabase
import com.ikii.liteschreib.features.apprentissage.data.local.ApprentissageDao
import com.ikii.liteschreib.features.apprentissage.data.repository.ApprentissageRepositoryImpl
import com.ikii.liteschreib.features.apprentissage.domain.repository.ApprentissageRepository
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
            // Aucune migration definie pour l'instant : a remplacer par de vraies
            // migrations (Migration(1, 2) { ... }) des la V2 du schema en production.
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideApprentissageDao(database: AppDatabase): ApprentissageDao = database.apprentissageDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindApprentissageRepository(
        impl: ApprentissageRepositoryImpl
    ): ApprentissageRepository

    // TODO au fur et a mesure : bindSuiviRepository, bindProfilRepository, bindEcritureRepository
    // des que leurs interfaces domain + implementations data seront ecrites (meme patron que ci-dessus).
} */