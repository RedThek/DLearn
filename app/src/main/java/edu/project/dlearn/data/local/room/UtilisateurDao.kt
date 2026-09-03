package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilisateurDao {

    @Query("SELECT * FROM utilisateur WHERE identifiant = :identifiant AND role = :role LIMIT 1")
    suspend fun trouverParIdentifiant(identifiant: String, role: String): UtilisateurEntity?

    @Query("SELECT * FROM utilisateur WHERE id = :id LIMIT 1")
    suspend fun trouverParId(id: Long): UtilisateurEntity?

    @Query("SELECT * FROM utilisateur")
    suspend fun recupererTous(): List<UtilisateurEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insererUtilisateurs(utilisateurs: List<UtilisateurEntity>)

    /** Retourne tous les comptes locaux, triés rôle enseignant d'abord. */
    @Query("SELECT * FROM utilisateur ORDER BY role DESC")
    fun getAllUtilisateurs(): kotlinx.coroutines.flow.Flow<List<UtilisateurEntity>>

    /**
     * Met à jour le niveau GeR de l'élève suite au test de positionnement (D-04).
     * Appelé par PositionnementRepositoryImpl.enregistrerResultat().
     */
    @Query("UPDATE utilisateur SET niveau = :niveau WHERE id = :utilisateurId")
    suspend fun updateNiveau(utilisateurId: Long, niveau: String)

    /** Retourne tous les comptes de rôle ELEVE, triés par nom affiché. */
    @Query("SELECT * FROM utilisateur WHERE role = 'ELEVE' ORDER BY nomAffiche ASC")
    fun getEleves(): Flow<List<UtilisateurEntity>>
}
