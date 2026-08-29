package edu.project.dlearn.presentation.suivi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SuiviScreen(viewModel: SuiviViewModel = hiltViewModel()) {
    val stats by viewModel.stats.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ma progression", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCarte("Mots appris", stats.motsAppris.toString(), Modifier.weight(1f))
            StatCarte("Serie (jours)", stats.streakJours.toString(), Modifier.weight(1f))
            StatCarte("Taux reussite", "${stats.tauxReussite}%", Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))
        Text("Competences par niveau CECR", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        GraphiqueCompetences(stats.competencesParNiveau)
    }
}

@Composable
private fun StatCarte(titre: String, valeur: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(Modifier.padding(12.dp)) {
            Text(valeur, style = MaterialTheme.typography.titleLarge)
            Text(titre, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Mini bar-chart dessine avec Canvas : evite une dependance graphique externe,
// coherent avec la contrainte offline-first (pas de SDK tiers a synchroniser).
@Composable
private fun GraphiqueCompetences(donnees: Map<String, Float>) {
    val couleurBarre = MaterialTheme.colorScheme.primary
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        if (donnees.isEmpty()) return@Canvas
        val largeurBarre = size.width / (donnees.size * 2f)
        donnees.values.forEachIndexed { index, valeur ->
            val hauteurBarre = size.height * valeur.coerceIn(0f, 1f)
            drawRect(
                color = couleurBarre,
                topLeft = Offset(
                    x = index * largeurBarre * 2 + largeurBarre / 2,
                    y = size.height - hauteurBarre
                ),
                size = Size(largeurBarre, hauteurBarre)
            )
        }
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        donnees.keys.forEach { niveau ->
            Text(niveau, style = MaterialTheme.typography.labelLarge)
        }
    }
}
