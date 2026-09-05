package edu.project.dlearn.data.repository

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Test de la règle de décision "faut-il écraser l'existant ?" isolée de Room (ADR-018).
 * La logique réelle vit dans SyncRepositoryImpl.importerDonnees ; ce test fige la règle en dur
 * pour éviter une régression silencieuse si la condition est modifiée par erreur.
 */
class ImportFusionTest {

    private fun doitEcraser(dateImport: Long, dateExistante: Long?): Boolean =
        dateExistante == null || dateImport >= dateExistante

    @Test
    fun `import plus recent qu'existant ecrase`() {
        assertTrue(doitEcraser(dateImport = 2000L, dateExistante = 1000L))
    }

    @Test
    fun `import egal a l'existant ecrase (tolerance meme lot)`() {
        assertTrue(doitEcraser(dateImport = 1000L, dateExistante = 1000L))
    }

    @Test
    fun `import plus ancien qu'existant n'ecrase pas`() {
        assertTrue(!doitEcraser(dateImport = 500L, dateExistante = 1000L))
    }

    @Test
    fun `aucun existant ecrase toujours (creation)`() {
        assertTrue(doitEcraser(dateImport = 1L, dateExistante = null))
    }
}
