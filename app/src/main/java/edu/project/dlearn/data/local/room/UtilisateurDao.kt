package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UtilisateurDao {

    @Query("SELECT * FROM utilisateur WHERE identifiant = :identifiant AND role = :role LIMIT 1")
    suspend fun trouverParIdentifiant(identifiant: String, role: String): UtilisateurEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insererUtilisateurs(utilisateurs: List<UtilisateurEntity>)
}
