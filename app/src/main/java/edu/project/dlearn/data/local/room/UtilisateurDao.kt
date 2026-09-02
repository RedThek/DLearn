package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UtilisateurDao {

    @Query("SELECT * FROM utilisateur WHERE identifiant = :identifiant AND role = :role LIMIT 1")
    suspend fun trouverParIdentifiant(identifiant: String, role: String): UtilisateurEntity?

    @Query("SELECT * FROM utilisateur")
    suspend fun recupererTous(): List<UtilisateurEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insererUtilisateurs(utilisateurs: List<UtilisateurEntity>)

    /** Retourne tous les comptes locaux, triés rôle enseignant d'abord. */
    @Query("SELECT * FROM utilisateur ORDER BY role DESC")
    fun getAllUtilisateurs(): kotlinx.coroutines.flow.Flow<List<UtilisateurEntity>>
}
