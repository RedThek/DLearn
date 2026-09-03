package edu.project.dlearn.core

import edu.project.dlearn.data.local.datasource.ContentDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Exécute les tâches d'initialisation une seule fois au démarrage de l'application.
 * Appelé depuis DLearnApplication.onCreate() après que Hilt a tout câblé.
 */
@Singleton
class AppInitializer @Inject constructor(
    private val contentDataSource: ContentDataSource
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun initialiser() {
        scope.launch {
            try {
                contentDataSource.peupler()
            } catch (e: Exception) {
                // Log silencieux — ne pas crasher l'app si le seed échoue
                android.util.Log.e("AppInitializer", "Erreur seed contenu", e)
            }
        }
    }
}
