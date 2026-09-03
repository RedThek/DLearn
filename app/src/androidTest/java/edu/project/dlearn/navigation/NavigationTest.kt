package edu.project.dlearn.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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

    @Test
    fun demarrage_affiche_ecran_connexion_ou_selection_profil() {
        // L'app démarre sur ConnexionScreen OU SelectionProfilScreen (selon les profils en base).
        // Le texte "Liteschreib IKII" est présent dans les deux cas.
        composeRule.onNodeWithText("Liteschreib IKII").assertIsDisplayed()
    }

    @Test
    fun connexion_eleve_demo_navigue_vers_positionnement() {
        // Utilise le compte de démonstration seedé dans AppModule.SeedCallback
        composeRule.onNodeWithText("Élève").performClick()
        composeRule.onNodeWithText("Identifiant").performClick()
        composeRule.onNodeWithText("ex : eleve.2451").performTextInput("eleve.2451")
        
        // Saisie mot de passe (on suppose qu'il y a un champ mot de passe)
        // En M3 OutlinedTextField n'a pas forcément de label "Mot de passe" simple si non configuré, 
        // mais ici il est dans ConnexionScreen.
        composeRule.onNodeWithText("Se connecter").performClick()
        composeRule.waitForIdle()
        
        // Après connexion élève → PositionnementScreen
        composeRule.onNodeWithText("Test de positionnement · Question 1/10").assertIsDisplayed()
    }

    @Test
    fun connexion_enseignant_demo_navigue_vers_main_sans_positionnement() {
        composeRule.onNodeWithText("Enseignant").performClick()
        composeRule.onNodeWithText("ex : eleve.2451").performTextInput("enseignant.100")
        composeRule.onNodeWithText("Se connecter").performClick()
        composeRule.waitForIdle()
        // Vérifier que l'app principale (MainScreen) est affichée (onglets visibles)
        composeRule.onNodeWithText("Accueil").assertIsDisplayed()
    }

    @Test
    fun navigation_cinq_onglets_eleve_sans_crash() {
        // Simuler une navigation de base
        val labels = listOf("Accueil", "Apprentissage", "Écriture", "Suivi", "Profil")
        // Ce test nécessite d'être sur MainScreen
    }

    @Test
    fun ecritureScreen_affiche_clavier_allemand() {
        // Nécessite d'être sur MainScreen onglet Écriture
        // TODO Sprint 3 : automatiser la connexion de démo avant ce test
        // Pour l'instant : test manuel uniquement
    }
}
