package edu.project.dlearn.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.presentation.connexion.ConnexionScreen
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
                onDeconnexion = {
                    navController.navigate(Route.CONNEXION) {
                        popUpTo(0) { inclusive = true }
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
