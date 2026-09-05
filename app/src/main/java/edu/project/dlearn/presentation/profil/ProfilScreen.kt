package edu.project.dlearn.presentation.profil

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.*
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilScreen(
    onDeconnexion: () -> Unit,
    viewModel: ProfilViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.evenements.collect { evenement ->
            when (evenement) {
                ProfilEvenement.Deconnecte -> onDeconnexion()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fichierExporte.collect { chemin ->
            val fichier = File(chemin)
            val uri = FileProvider.getUriForFile(
                context, "${context.packageName}.fileprovider", fichier
            )
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/json"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Partager mes données"))
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Se déconnecter ?") },
            text = { Text("Tu devras te reconnecter pour accéder à ton parcours.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        viewModel.onDeconnexion()
                    }
                ) {
                    Text("Se déconnecter", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Mon profil",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InitialsAvatar(etat.nomComplet, taille = 88.dp)
                    Text(
                        text = etat.nomComplet,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Élève · ${etat.classe}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    SuggestionChip(
                        onClick = { },
                        label = { Text("Niveau ${etat.niveauActuel}") },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            // Badges
            if (etat.badges.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        etat.badges.forEach { badge ->
                            Surface(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(40.dp),
                                shape = CircleShape,
                                color = if (badge.deverrouille) MaterialTheme.colorScheme.tertiaryContainer 
                                        else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (!badge.deverrouille) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = badge.icone,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = if (badge.deverrouille) MaterialTheme.colorScheme.onTertiaryContainer 
                                               else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Progression Niveau
            item {
                DlearnSectionHeader(title = "Objectif actuel")
            }

            item {
                ProgressCard(
                    title = "Vers le niveau ${etat.niveauCible}",
                    progress = etat.progressionVersCible,
                    supportingText = "Plus que 5 unités pour atteindre le niveau ${etat.niveauCible} !"
                )
            }

            // Préférences
            item {
                DlearnSectionHeader(title = "Préférences")
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column {
                        ListItem(
                            headlineContent = { Text("Audio") },
                            supportingContent = { Text(if (etat.notificationsActives) "Lecture audio activée" else "Lecture audio désactivée") },
                            leadingContent = { Icon(Icons.Default.VolumeUp, contentDescription = null) },
                            trailingContent = { Switch(checked = etat.notificationsActives, onCheckedChange = { }) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ListItem(
                            headlineContent = { Text("Langue de l'interface") },
                            supportingContent = { Text(etat.langueInterface) },
                            leadingContent = { Icon(Icons.Default.Language, contentDescription = null) },
                            trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) }
                        )
                    }
                }
            }

            // Compte Local
            item {
                DlearnSectionHeader(title = "Compte local")
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column {
                        ListItem(
                            headlineContent = { Text("Dernière synchronisation") },
                            supportingContent = { Text(etat.derniereSynchro) },
                            leadingContent = { Icon(Icons.Default.Sync, contentDescription = null) },
                            trailingContent = {
                                if (etat.synchronisationEnCours) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                } else {
                                    IconButton(onClick = viewModel::onSynchroniserMaintenant) {
                                        Icon(Icons.Default.Refresh, contentDescription = "Synchroniser")
                                    }
                                }
                            }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ListItem(
                            headlineContent = { Text("Se déconnecter", color = MaterialTheme.colorScheme.error) },
                            leadingContent = { Icon(Icons.Default.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                            modifier = Modifier.clickable { showLogoutDialog = true }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
