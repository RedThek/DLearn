package edu.project.dlearn.data.repository

import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.MessageDigest

/**
 * Tests unitaires légers pour AuthRepositoryImpl.
 * L'implémentation Room est testée via des tests d'instrumentation (InstrumentedTest).
 * Ces tests vérifient la logique de hachage et le schéma de retour uniquement.
 */
class AuthRepositoryTest {

    @Test
    fun `hash SHA256 produit une chaine hexadecimale de 64 caracteres`() {
        // SHA-256 produit 32 octets = 64 hexadécimaux
        val hacheur = { valeur: String ->
            MessageDigest.getInstance("SHA-256")
                .digest(valeur.toByteArray())
                .joinToString("") { "%02x".format(it) }
        }
        val hash = hacheur("eleve1234")
        assertEquals(64, hash.length)
        // Le hash de "eleve1234" doit correspondre au SeedCallback
        assertEquals(
            "0d62b61ff9b60f8082d22dae0d0a7f7330b7729d323f8401d723511e2e7ca7e8",
            hash
        )
    }
}
