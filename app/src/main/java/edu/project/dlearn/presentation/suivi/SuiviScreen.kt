package edu.project.dlearn.presentation.suivi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.*

@Composable
fun SuiviScreen(
    onCommencerApprentissage: () -> Unit = {},
    viewModel: SuiviViewModel = hiltViewModel()
) {
    val stats by viewModel.stats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                "Mon suivi",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
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
                    val progressionReelle = stats.competencesParNiveau.values
                        .let { if (it.isEmpty()) 0f else it.average().toFloat() }
                    ProgressCard(
                        title = "Progression globale",
                        progress = progressionReelle,
                        supportingText = "${stats.motsAppris} mots appris · basé sur ${stats.competencesParNiveau.size} niveau(x) suivi(s)"
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
                    DlearnSectionHeader(title = "Historique récent")
                }
                item {
                    EmptyStateCard(
                        title = "Historique bientôt disponible",
                        message = "Le détail de tes dernières activités sera affiché ici dans une prochaine mise à jour."
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
