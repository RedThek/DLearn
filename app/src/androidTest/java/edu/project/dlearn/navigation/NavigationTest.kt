package edu.project.dlearn.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import edu.project.dlearn.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule(order = 0) val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1) val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    /** Scénario 1 : démarrage → ConnexionScreen visible (aucune session persistée). */
    @Test
    fun demarrage_sans_session_affiche_ecran_connexion() {
        // "Liteschreib IKII" apparaît dans ConnexionScreen ET SelectionProfilScreen
        composeRule.onNodeWithText("Liteschreib IKII").assertIsDisplayed()
    }

    /** Scénario 2 : connexion élève de démo → PositionnementScreen. */
    @Test
    fun connexion_eleve_demo_navigue_vers_positionnement() {
        // S'assurer qu'on est sur ConnexionScreen
        composeRule.waitUntil(3_000) {
            composeRule.onAllNodes(
                hasText("Liteschreib IKII")
            ).fetchSemanticsNodes().isNotEmpty()
        }

        // Sélectionner le rôle Élève
        composeRule.onNodeWithText("Élève").performClick()

        // Saisir l'identifiant via testTag
        composeRule.onNodeWithTag("champ_identifiant").performTextInput("eleve.2451")

        // Saisir le mot de passe via testTag
        composeRule.onNodeWithTag("champ_mot_de_passe").performTextInput("eleve1234")

        // Cliquer sur Se connecter
        composeRule.onNodeWithTag("bouton_connexion").performClick()
        composeRule.waitForIdle()

        // Après connexion Élève → PositionnementScreen
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodes(
                hasText("Test de positionnement")
            ).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Test de positionnement · Question 1/10")
            .assertIsDisplayed()
    }

    /** Scénario 3 : connexion enseignant → MainScreen sans positionnement. */
    @Test
    fun connexion_enseignant_demo_navigue_directement_vers_dashboard() {
        composeRule.waitUntil(3_000) {
            composeRule.onAllNodes(
                hasText("Liteschreib IKII")
            ).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Enseignant").performClick()
        composeRule.onNodeWithTag("champ_identifiant").performTextInput("enseignant.100")
        composeRule.onNodeWithTag("champ_mot_de_passe").performTextInput("enseignant1234")
        composeRule.onNodeWithTag("bouton_connexion").performClick()
        composeRule.waitForIdle()

        // Enseignant → EnseignantDashboardScreen directement
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodes(
                hasText("Classe")
            ).fetchSemanticsNodes().isNotEmpty()
        }
    }

    /** Scénario 4 : navigation 5 onglets élève sans crash. */
    @Test
    fun navigation_cinq_onglets_eleve_sans_crash() {
        // TODO Sprint 4 : automatiser la connexion puis vérifier les 5 onglets.
        // Dépend de la session DataStore persistée entre les tests.
        // Pour l'instant : vérifier que le test compile (aucune assertion).
    }

    /**
     * Scénario 5 (Sprint 3, B-20) : l'écran de connexion ne propose plus de créer un
     * compte élève — seul un enseignant authentifié peut le faire depuis son dashboard.
     */
    @Test
    fun ecran_connexion_ne_propose_plus_creation_compte_eleve() {
        composeRule.waitUntil(3_000) {
            composeRule.onAllNodes(
                hasText("Liteschreib IKII")
            ).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onAllNodes(hasText("Demander un compte élève"))
            .fetchSemanticsNodes()
            .also { nodes -> assert(nodes.isEmpty()) { "Le bouton de création de compte ne doit plus être visible sur ConnexionScreen (B-20)" } }
    }
}
