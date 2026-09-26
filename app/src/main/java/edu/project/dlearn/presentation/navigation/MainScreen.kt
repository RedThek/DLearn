package edu.project.dlearn.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
 * avec transitions fluides et masquage de la barre sur les sessions d'exercices.
 */
@Composable
fun MainScreen(
    role: Role = Role.ELEVE,
    onNaviguerVersCreationEleve: () -> Unit = {},
    onDeconnexion: () -> Unit
) {
    if (role == Role.ENSEIGNANT) {
        EnseignantDashboardScreen(onCreerEleve = onNaviguerVersCreationEleve)
        return
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""
    val showBottomBar = !currentRoute.startsWith("exercices")

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = fadeIn(tween(LiteschreibTransitions.DUREE_MS)) + slideInVertically(tween(LiteschreibTransitions.DUREE_MS)) { it },
                exit = fadeOut(tween(LiteschreibTransitions.DUREE_MS)) + slideOutVertically(tween(LiteschreibTransitions.DUREE_MS)) { it }
            ) {
                LiteschreibBottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Accueil.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { LiteschreibTransitions.enterFade },
            exitTransition = { LiteschreibTransitions.exitFade },
            popEnterTransition = { LiteschreibTransitions.enterFade },
            popExitTransition = { LiteschreibTransitions.exitFade }
        ) {
            composable(BottomNavItem.Accueil.route) { 
                AccueilScreen(onOuvrirLecture = { navController.navigate(BottomNavItem.Apprentissage.route) }) 
            }
            composable(BottomNavItem.Apprentissage.route) {
                ApprentissageScreen(
                    onCommencerExercices = { uniteId ->
                        navController.navigate("exercices/$uniteId")
                    },
                    onCommencerEcriture = { uniteId ->
                        navController.navigate("ecriture?uniteId=$uniteId")
                    }
                )
            }
            composable(
                route = "exercices/{uniteId}",
                arguments = listOf(navArgument("uniteId") { type = NavType.StringType }),
                enterTransition = { LiteschreibTransitions.enterSlide },
                exitTransition = { LiteschreibTransitions.exitSlide },
                popEnterTransition = { LiteschreibTransitions.enterFade },
                popExitTransition = { LiteschreibTransitions.exitSlide }
            ) {
                ExerciceScreen(onTermine = { navController.popBackStack() })
            }
            composable(
                route = "ecriture?uniteId={uniteId}",
                arguments = listOf(
                    navArgument("uniteId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { EcritureScreen() }
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
