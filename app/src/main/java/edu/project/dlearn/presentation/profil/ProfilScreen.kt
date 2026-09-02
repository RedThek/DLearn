package edu.project.dlearn.presentation.profil

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilScreen(
    onDeconnexion: () -> Unit,
    viewModel: ProfilViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.evenements.collect { evenement ->
            when (evenement) {
                ProfilEvenement.Deconnecte -> onDeconnexion()
            }
        }
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
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Settings, contentDescription = "Paramètres")
                    }
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

            // Progression Niveau
            item {
                DlearnSectionHeader(title = "Objectif actuel")
            }

            item {
                ProgressCard(
                    title = "Vers le niveau ${etat.niveauCible}",
                    progress = 0.72f, // Placeholder
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
                            supportingContent = { Text("Lecture audio activée") },
                            leadingContent = { Icon(Icons.Default.VolumeUp, contentDescription = null) },
                            trailingContent = { Switch(checked = true, onCheckedChange = { }) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ListItem(
                            headlineContent = { Text("Taille du texte") },
                            supportingContent = { Text("Standard") },
                            leadingContent = { Icon(Icons.Default.FormatSize, contentDescription = null) },
                            trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ListItem(
                            headlineContent = { Text("Accessibilité") },
                            supportingContent = { Text("Options d'affichage") },
                            leadingContent = { Icon(Icons.Default.Accessibility, contentDescription = null) },
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
                            modifier = Modifier.clickable(onClick = viewModel::onDeconnexion)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
