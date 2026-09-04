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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.presentation.accueil.AccueilScreen
import edu.project.dlearn.presentation.apprentissage.ApprentissageScreen
import edu.project.dlearn.presentation.ecriture.EcritureScreen
import edu.project.dlearn.presentation.exercice.ExerciceScreen
import edu.project.dlearn.presentation.enseignant.EnseignantDashboardScreen
import edu.project.dlearn.presentation.profil.ProfilScreen
import edu.project.dlearn.presentation.suivi.SuiviScreen

/**
 * App principale (post-connexion / post-positionnement) : Scaffold + navigation par onglets,
 * inchangée depuis la première livraison. Nichée sous la route "main" du [RootNavGraph].
 */
@Composable
fun MainScreen(
    role: Role = Role.ELEVE,
    onNaviguerVersCreationEleve: () -> Unit = {},
    onDeconnexion: () -> Unit
) {
    if (role == Role.ENSEIGNANT) {
        EnseignantDashboardScreen(onCreerEleve = onNaviguerVersCreationEleve)
        // TODO Sprint 4+ : le dashboard enseignant aura sa propre BottomBar (Classe/Contenus/Corrections)
        return
    }

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { LiteschreibBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Accueil.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Accueil.route) { 
                AccueilScreen(onOuvrirLecture = { navController.navigate(BottomNavItem.Apprentissage.route) }) 
            }
            composable(BottomNavItem.Apprentissage.route) {
                ApprentissageScreen(
                    onCommencerExercices = { uniteId ->
                        navController.navigate("exercices/$uniteId")
                    }
                )
            }
            composable(
                route = "exercices/{uniteId}",
                arguments = listOf(navArgument("uniteId") { type = NavType.StringType })
            ) {
                ExerciceScreen(onTermine = { navController.popBackStack() })
            }
            composable(BottomNavItem.Ecriture.route) { EcritureScreen() }
            composable(BottomNavItem.Suivi.route) { 
                SuiviScreen(onCommencerApprentissage = { navController.navigate(BottomNavItem.Apprentissage.route) }) 
            }
            composable(BottomNavItem.Profil.route) { ProfilScreen(onDeconnexion = onDeconnexion) }
        }
    }
}

@Composable
private fun LiteschreibBottomBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        BottomNavItem.items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
