package edu.project.dlearn.data.room

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import edu.project.dlearn.data.local.room.AppDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    private val TEST_DB = "liteschreib_migration_test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java
    )

    @Test
    @Throws(IOException::class)
    fun migration3a4_ajoute_isValidated() {
        // Créer la DB à la version 3
        helper.createDatabase(TEST_DB, 3).apply { close() }

        // Appliquer la migration 3→4
        val db = helper.runMigrationsAndValidate(
            TEST_DB, 4, true, AppDatabase.MIGRATION_3_4
        )

        // Vérifier que la colonne existe
        val cursor = db.query(
            "SELECT isValidated FROM unite_apprentissage LIMIT 1"
        )
        // Si la colonne n'existe pas, la requête lève une exception — test réussi si elle passe
        cursor.close()
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun migration4a5_ajoute_assignation_et_statut() {
        helper.createDatabase(TEST_DB, 4).apply { close() }

        val db = helper.runMigrationsAndValidate(
            TEST_DB, 5, true, AppDatabase.MIGRATION_4_5
        )

        val cursorStatut = db.query("SELECT statut FROM production_ecrite LIMIT 1")
        cursorStatut.close()

        db.execSQL(
            "INSERT INTO assignation (id, enseignantId, cibleType, cibleId, uniteId, dateAssignation) " +
            "VALUES ('test-1', 1, 'ELEVE', '1', 'U-6E-01', 1000)"
        )
        val cursorAssignation = db.query("SELECT * FROM assignation WHERE id = 'test-1'")
        assert(cursorAssignation.count == 1)
        cursorAssignation.close()
        db.close()
    }
}
