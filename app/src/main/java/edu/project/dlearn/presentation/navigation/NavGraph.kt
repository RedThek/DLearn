package edu.project.dlearn.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur
import edu.project.dlearn.presentation.connexion.ConnexionScreen
import edu.project.dlearn.presentation.enseignant.CreationEleveScreen
import edu.project.dlearn.presentation.enseignant.ResultatCreationEleveScreen
import edu.project.dlearn.presentation.positionnement.PositionnementScreen
import edu.project.dlearn.presentation.selectionprofil.SelectionProfilScreen

/**
 * Graphe de navigation racine — Liteschreib IKII.
 * Détermine la route initiale via [NavViewModel]
 * (session persistée → MAIN, multi-profil → SELECTION_PROFIL, vide → CONNEXION).
 */
@Composable
fun LiteschreibApp() {
    val navViewModel: NavViewModel = hiltViewModel()
    val destinationInitiale by navViewModel.destinationInitiale.collectAsState()
    val utilisateurConnecte by navViewModel.utilisateurConnecte.collectAsState()
    val navController = rememberNavController()

    // Écran de démarrage pendant la résolution de la session
    if (destinationInitiale == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    NavHost(
        navController    = navController,
        startDestination = destinationInitiale!!
    ) {
        composable(Route.CONNEXION) {
            ConnexionScreen(
                onConnexionReussie = { role ->
                    naviguerApresConnexion(navController, role)
                },
                onNaviguerVersSelectionProfil = {
                    navController.navigate(Route.SELECTION_PROFIL)
                },
                onDemanderCompte = {
                    navController.navigate(Route.CREATION_ELEVE)
                }
            )
        }

        composable(Route.SELECTION_PROFIL) {
            SelectionProfilScreen(
                onProfilSelectionne = { _ ->
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
            val role = utilisateurConnecte?.role ?: Role.ELEVE
            MainScreen(role = role) {
                navController.navigate(Route.CONNEXION) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }

        composable(Route.CREATION_ELEVE) {
            CreationEleveScreen(
                onBack = { navController.popBackStack() },
                onCreateStudent = { _, _, _ ->
                    navController.navigate(Route.RESULTAT_CREATION_ELEVE)
                }
            )
        }

        composable(Route.RESULTAT_CREATION_ELEVE) {
            val mockUtilisateur = Utilisateur(
                id          = 999L,
                identifiant = "eleve.demo",
                nomAffiche  = "Divine K.",
                role        = Role.ELEVE,
                classe      = "6e A",
                niveau      = "A1",
                motDePasse  = "ikii.1234"
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
