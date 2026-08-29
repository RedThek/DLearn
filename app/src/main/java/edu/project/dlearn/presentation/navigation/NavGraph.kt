package edu.project.dlearn.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.project.dlearn.presentation.accueil.AccueilScreen
import edu.project.dlearn.presentation.apprentissage.ApprentissageScreen
import edu.project.dlearn.presentation.ecriture.EcritureScreen
import edu.project.dlearn.presentation.profil.ProfilScreen
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.presentation.connexion.ConnexionScreen
import edu.project.dlearn.presentation.positionnement.PositionnementScreen

private object Route {
    const val CONNEXION = "connexion"
    const val POSITIONNEMENT = "positionnement"
    const val MAIN = "main"
}

/**
 * Graphe racine de l'application : point d'entrée appelé depuis MainActivity.
 * Flux : Connexion -> (Positionnement, uniquement pour un Élève) -> App principale (5 onglets).
 *
 * Le test de positionnement n'est proposé qu'aux élèves ; un compte Enseignant passe
 * directement à l'app principale (le tableau de bord Enseignant reste à implémenter,
 * cf. README).
 */

@Composable
fun LiteschreibApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.CONNEXION) {
        composable(Route.CONNEXION) {
            ConnexionScreen(
                onConnexionReussie = { role -> onConnexionReussie(navController, role) }
            )
        }
        composable(Route.POSITIONNEMENT) {
            PositionnementScreen(
                onTermine = { /* niveauPropose : TODO le transmettre au profil élève via un repository partagé */
                    navController.navigate(Route.MAIN) {
                        // CONNEXION n'est déjà plus sur la back stack à ce stade (retiré à l'étape
                        // précédente) : popUpTo(0) vide tout le reste plutôt que de cibler une
                        // destination absente, ce qui lèverait une IllegalArgumentException.
                        popUpTo(0)
                    }
                }
            )
        }
        composable(Route.MAIN) {
            MainScreen(
                onDeconnexion = {
                    navController.navigate(Route.CONNEXION) {
                        popUpTo(0) // vide tout le back stack : impossible de "revenir" dans l'app après déconnexion
                    }
                }
            )
        }
    }
}

private fun onConnexionReussie(navController: NavHostController, role: Role) {
    val destination = if (role == Role.ELEVE) Route.POSITIONNEMENT else Route.MAIN
    navController.navigate(destination) {
        popUpTo(Route.CONNEXION) { inclusive = true }
    }
}
