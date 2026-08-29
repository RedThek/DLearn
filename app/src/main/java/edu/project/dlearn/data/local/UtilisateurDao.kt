package edu.project.dlearn.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.project.dlearn.data.local.UtilisateurEntity

@Dao
interface UtilisateurDao {

    @Query("SELECT * FROM utilisateur WHERE identifiant = :identifiant AND role = :role LIMIT 1")
    suspend fun trouverParIdentifiant(identifiant: String, role: String): UtilisateurEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insererUtilisateurs(utilisateurs: List<UtilisateurEntity>)
}
