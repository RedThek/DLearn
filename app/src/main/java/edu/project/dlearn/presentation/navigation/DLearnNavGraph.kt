// presentation/navigation/DLearnNavGraph.kt
package edu.project.dlearn.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.project.dlearn.presentation.accueil.AccueilScreen

object Routes {
    const val ACCUEIL = "accueil"
    const val CONNEXION = "connexion"
    // routes A2-A5 ajoutées au fil des missions
}

@Composable
fun DLearnNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.ACCUEIL) {
        composable(Routes.ACCUEIL) {
            AccueilScreen(
                onEleveClick = { /* navController.navigate(...) — Mission A2 */ },
                onEnseignantClick = { /* idem */ }
            )
        }
    }
}