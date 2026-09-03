package edu.project.dlearn.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.domain.model.Utilisateur
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.sessionDataStore: DataStore<Preferences>
    by preferencesDataStore(name = "liteschreib_session")

/**
 * Persistance de la session utilisateur via DataStore.
 * Survive aux redémarrages de l'application (contrairement à une variable en mémoire).
 * Compatible offline-first (ADR-002) — aucun réseau requis.
 */
@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_USER_ID    = longPreferencesKey("session_user_id")
        private val KEY_USER_ROLE  = stringPreferencesKey("session_user_role")
    }

    /** Flow de l'ID de l'utilisateur en session (null si aucune session active). */
    val utilisateurIdFlow: Flow<Long?> = context.sessionDataStore.data
        .map { prefs -> prefs[KEY_USER_ID] }

    /** Persiste la session de l'utilisateur connecté. */
    suspend fun sauvegarderSession(utilisateur: Utilisateur) {
        context.sessionDataStore.edit { prefs ->
            prefs[KEY_USER_ID]   = utilisateur.id
            prefs[KEY_USER_ROLE] = utilisateur.role.name
        }
    }

    /** Supprime la session (déconnexion). */
    suspend fun effacerSession() {
        context.sessionDataStore.edit { it.clear() }
    }
}
