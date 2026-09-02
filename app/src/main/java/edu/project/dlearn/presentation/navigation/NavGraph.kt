package edu.project.dlearn.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.presentation.connexion.ConnexionScreen
import edu.project.dlearn.presentation.enseignant.CreationEleveScreen
import edu.project.dlearn.presentation.enseignant.ResultatCreationEleveScreen
import edu.project.dlearn.presentation.positionnement.PositionnementScreen
import edu.project.dlearn.presentation.selectionprofil.SelectionProfilScreen

/**
 * Graphe de navigation racine — Liteschreib IKII.
 *
 * Flux :
 *   CONNEXION ──────────────────────► POSITIONNEMENT (Élève) ──► MAIN
 *             └──(profils existants)► SELECTION_PROFIL ─────────► MAIN
 *   MAIN ──(déconnexion)──────────────────────────────────────► CONNEXION
 */
@Composable
fun LiteschreibApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.CONNEXION) {

        composable(Route.CONNEXION) {
            ConnexionScreen(
                onConnexionReussie = { role ->
                    naviguerApresConnexion(navController, role)
                },
                onNaviguerVersSelectionProfil = {
                    navController.navigate(Route.SELECTION_PROFIL)
                },
                onDemanderCompte = {
                    // Pour la démo, on navigue vers la création d'élève
                    // (Normalement réservé à l'enseignant, mais FR-33/FR-04 permettent d'y accéder)
                    navController.navigate(Route.CREATION_ELEVE)
                }
            )
        }

        composable(Route.SELECTION_PROFIL) {
            SelectionProfilScreen(
                onProfilSelectionne = { role ->
                    navController.navigate(Route.MAIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onAutreCompte = {
                    navController.navigate(Route.CONNEXION) {
                        popUpTo(Route.SELECTION_PROFIL) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.POSITIONNEMENT) {
            PositionnementScreen(
                onTermine = {
                    navController.navigate(Route.MAIN) { popUpTo(0) }
                }
            )
        }

        composable(Route.MAIN) {
            MainScreen(
                role          = Role.ELEVE,   // TODO: lire depuis session
                onDeconnexion = {
                    navController.navigate(Route.CONNEXION) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.CREATION_ELEVE) {
            CreationEleveScreen(
                onBack = { navController.popBackStack() },
                onCreateStudent = { fullName, className, level ->
                    // Navigation simulée vers le résultat pour la démo UI
                    navController.navigate(Route.RESULTAT_CREATION_ELEVE)
                }
            )
        }

        composable(Route.RESULTAT_CREATION_ELEVE) {
            // Mock de l'utilisateur créé pour la démo UI
            val mockUtilisateur = edu.project.dlearn.domain.model.Utilisateur(
                id = 999L,
                identifiant = "eleve.demo",
                nomAffiche = "Divine K.",
                role = Role.ELEVE,
                classe = "6e A",
                niveau = "A1",
                motDePasse = "ikii.1234"
            )
            ResultatCreationEleveScreen(
                utilisateur = mockUtilisateur,
                onDone = {
                    navController.navigate(Route.CONNEXION) {
                        popUpTo(Route.CREATION_ELEVE) { inclusive = true }
                    }
                }
            )
        }
    }
}

private fun naviguerApresConnexion(navController: NavHostController, role: Role) {
    val destination = if (role == Role.ELEVE) Route.POSITIONNEMENT else Route.MAIN
    navController.navigate(destination) {
        popUpTo(Route.CONNEXION) { inclusive = true }
    }
}
