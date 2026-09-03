package edu.project.dlearn.presentation.suivi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuiviScreen(
    onCommencerApprentissage: () -> Unit = {},
    viewModel: SuiviViewModel = hiltViewModel()
) {
    val stats by viewModel.stats.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Mon suivi",
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (stats.motsAppris == 0 && stats.streakJours == 0 && stats.competencesParNiveau.isEmpty()) {
                item {
                    EmptyStateCard(
                        title = "Ton parcours commence ici",
                        message = "Ton historique apparaîtra ici après ta première activité.",
                        actionLabel = "Commencer à apprendre",
                        onActionClick = onCommencerApprentissage
                    )
                }
            } else {
                // Résumé Global
                item {
                    ProgressCard(
                        title = "Progression globale",
                        progress = 0.65f, // Placeholder if not in ProgressionStats
                        supportingText = "${stats.motsAppris} mots appris · Niveau A2 en cours"
                    )
                }

                // Statistiques
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatItem(
                            value = "${stats.streakJours}j",
                            label = "Série",
                            icon = Icons.Default.LocalFireDepartment,
                            modifier = Modifier.weight(1f)
                        )
                        StatItem(
                            value = "${stats.tauxReussite}%",
                            label = "Réussite",
                            icon = Icons.Default.TrendingUp,
                            modifier = Modifier.weight(1f)
                        )
                        StatItem(
                            value = "8h",
                            label = "Temps",
                            icon = Icons.Default.Timeline,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Filtre temporel
                item {
                    val options = listOf("7 jours", "30 jours", "Tout")
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                                onClick = { /* TODO: Filtrer dans le ViewModel */ },
                                selected = index == 0,
                                label = { Text(label, style = MaterialTheme.typography.labelMedium) }
                            )
                        }
                    }
                }

                // Progression par niveau
                item {
                    DlearnSectionHeader(
                        title = "Compétences par niveau",
                        subtitle = "Détail de ton parcours CECR"
                    )
                }

                items(stats.competencesParNiveau.toList()) { (niveau, progression) ->
                    ProgressCard(
                        title = "Niveau $niveau",
                        progress = progression,
                        supportingText = "${(progression * 100).toInt()}% maîtrisé",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                // Historique récent
                item {
                    DlearnSectionHeader(
                        title = "Historique récent",
                        actionLabel = "Voir tout",
                        onActionClick = { }
                    )
                }

                item {
                    ActivityCard(
                        title = "Vocabulaire : La ville",
                        typeLabel = "EXERCICE",
                        icon = Icons.Default.School,
                        metadata = "Aujourd'hui · Score 85%",
                        onClick = { }
                    )
                }

                item {
                    ActivityCard(
                        title = "Lecture : Berlin",
                        typeLabel = "LECTURE",
                        icon = Icons.Default.History,
                        metadata = "Hier · 12 min",
                        onClick = { }
                    )
                }

                // Message d'encouragement
                item {
                    EmptyStateCard(
                        title = "Bel effort !",
                        message = "Tu as progressé de 15% cette semaine. Continue comme ça !",
                        actionLabel = "Lancer un défi",
                        onActionClick = { }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
