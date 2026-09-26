package edu.project.dlearn.presentation.accueil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.*

@Composable
fun AccueilScreen(
    onOuvrirLecture: () -> Unit = {},
    viewModel: AccueilViewModel = hiltViewModel(),
) {
    val etat by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = etat.prenom,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            InitialsAvatar(etat.prenom, taille = 40.dp)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Hero Card
            item {
                HeroCard(
                    lectureEnCours = etat.lectureEnCours,
                    progression = etat.progressionGlobale,
                    onContinuerClick = onOuvrirLecture
                )
            }

            // Statistiques rapides
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatItem(
                        value = etat.serieJours,
                        label = "Série",
                        icon = Icons.Default.LocalFireDepartment,
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        value = etat.unitesTerminees,
                        label = "Unités",
                        icon = Icons.Default.TaskAlt,
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        value = etat.tempsEtude,
                        label = "Temps",
                        icon = Icons.Default.Schedule,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // À faire aujourd'hui
            item {
                DlearnSectionHeader(
                    title = "À faire aujourd'hui",
                    subtitle = "Tes activités recommandées"
                )
            }

            val lecture = etat.lectureEnCours
            if (lecture != null) {
                item {
                    ActivityCard(
                        title = lecture.titre,
                        typeLabel = "LECTURE",
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        metadata = "Page ${lecture.pageActuelle} sur ${lecture.pageTotale}",
                        onClick = onOuvrirLecture
                    )
                }
            } else {
                item {
                    EmptyStateCard(
                        title = "Aucune activité",
                        message = "Ton parcours commence ici. Lance ta première leçon.",
                        actionLabel = "Commencer",
                        onActionClick = onOuvrirLecture
                    )
                }
            }

            // Assigné par l'enseignant
            if (etat.assignations.isNotEmpty()) {
                item {
                    DlearnSectionHeader(
                        title    = "Assigné par ton enseignant",
                        subtitle = "${etat.assignations.size} unité(s) à faire"
                    )
                }
                items(etat.assignations, key = { it.uniteId }) { assignation ->
                    ActivityCard(
                        title     = assignation.titre,
                        typeLabel = "ASSIGNÉ",
                        icon      = Icons.Default.AutoAwesome,
                        metadata  = "Niveau ${assignation.niveauGer}",
                        onClick   = onOuvrirLecture
                    )
                }
            }

            // Ma progression
            item {
                DlearnSectionHeader(
                    title = "Ma progression",
                    subtitle = "Niveau ${etat.niveau}"
                )
            }

            item {
                ProgressCard(
                    title = "Objectif global",
                    progress = etat.progressionGlobale,
                    supportingText = "Continue comme ça pour atteindre le niveau suivant !"
                )
            }

            // Conseil du jour
            item {
                DlearnSectionHeader(title = "Conseil du jour")
            }

            item {
                ActivityCard(
                    title = "La répétition est la clé",
                    typeLabel = "CONSEIL",
                    icon = Icons.Default.AutoAwesome,
                    metadata = "Réviser 10 minutes chaque jour est plus efficace qu'une heure par semaine."
                )
            }

            // Bottom spacer for navigation bar
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun HeroCard(
    lectureEnCours: LectureEnCours?,
    progression: Float,
    onContinuerClick: () -> Unit
) {
    val titreLecture = lectureEnCours?.titre
    val subtitleText = titreLecture ?: "Lance ta première leçon."
    val buttonText = if (titreLecture != null) "Continuer · $titreLecture" else "Commencer"

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = "Prêt pour 10 minutes d'allemand ?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = subtitleText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progression },
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f)
                )
                Text(
                    text = "${(progression * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Button(
                onClick = onContinuerClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = buttonText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
